package com.cristianerm.pd2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiarioActivity extends AppCompatActivity {

    Toolbar toolbar_diario;
    RecyclerView mRecyclerView;
    ImageAdapterDiario mAdapter;
    List<DiarioTurmaInformation> mInformation;

    private static final String TAG = "DiarioActivity";

    private FirebaseDatabase mFirebaseDatase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario);

        toolbar_diario = (Toolbar) findViewById(R.id.tool_bar_diario);
        setSupportActionBar(toolbar_diario);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_diario.setTitle("");
        toolbar_diario.setSubtitle("");

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_diario);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mInformation = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        myRef = mFirebaseDatase.getReference().child(userID).child("info_user");

        toolbar_diario.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i;
                i = new Intent(DiarioActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(DiarioActivity.this, "User sighed in", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DiarioActivity.this, "User not sighed in", Toast.LENGTH_SHORT).show();
                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserInformation uInfo = new UserInformation();
                    uInfo.setTurma(ds.getValue(UserInformation.class).getTurma());

                    Log.d(TAG, "showData: Turma: " + uInfo.getTurma());

                    String turma = uInfo.getTurma();
                    DatabaseReference myRef2 = mFirebaseDatase.getReference().child("diario_professor").child(turma);
                    Toast.makeText(DiarioActivity.this, turma, Toast.LENGTH_SHORT).show();

                    myRef2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            mAdapter = new ImageAdapterDiario(DiarioActivity.this, mInformation);

                            mInformation.clear();
                            mAdapter.notifyDataSetChanged();

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                DiarioTurmaInformation information = ds.getValue(DiarioTurmaInformation.class);
                                mInformation.add(information);
                            }
                            Collections.reverse(mInformation);
                            mRecyclerView.setAdapter(mAdapter);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(DiarioActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
