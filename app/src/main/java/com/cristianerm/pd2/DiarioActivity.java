package com.cristianerm.pd2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
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

public class DiarioActivity extends AppCompatActivity {

    ListView diario;
    ImageButton voltar;

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

        diario = (ListView) findViewById(R.id.listDiario);
        voltar = (ImageButton) findViewById(R.id.buttonVoltarDiario);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        myRef = mFirebaseDatase.getReference().child(userID).child("info_user");

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

                        ArrayList<String> posts_diario = new ArrayList<String>();
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DiarioActivity.this, android.R.layout.simple_list_item_1, posts_diario);

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            posts_diario.clear();
                            arrayAdapter.notifyDataSetChanged();

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                DiarioTurmaInformation dInfo = new DiarioTurmaInformation();
                                dInfo.setData(ds.getValue(DiarioTurmaInformation.class).getData());
                                dInfo.setMensagem(ds.getValue(DiarioTurmaInformation.class).getMensagem());

                                Log.d(TAG, "showData: Data: " + dInfo.getData());
                                Log.d(TAG, "showData: Mensagem: " + dInfo.getMensagem());

                                posts_diario.add(dInfo.getData() + "\n" + dInfo.getMensagem());
                            }

                            Collections.reverse(posts_diario);
                            diario.setAdapter(arrayAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(DiarioActivity.this, MenuActivity.class);
                startActivity(i);
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
