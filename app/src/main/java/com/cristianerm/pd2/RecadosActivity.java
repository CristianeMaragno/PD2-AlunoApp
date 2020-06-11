package com.cristianerm.pd2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

public class RecadosActivity extends AppCompatActivity {

    ListView recados;
    ImageButton voltar;

    private static final String TAG = "Recados Activity";

    private FirebaseDatabase mFirebaseDatase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recados);

        recados = (ListView) findViewById(R.id.listRecados);
        voltar = (ImageButton) findViewById(R.id.buttonVoltarRecados);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        myRef = mFirebaseDatase.getReference().child("recados");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(RecadosActivity.this, "User sighed in", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RecadosActivity.this, "User not sighed in", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ValueEventListener valueEventListener = myRef.addValueEventListener(new ValueEventListener() {

            ArrayList<String> recadosList = new ArrayList<String>();
            RecadosCustomAdapter adapter = new RecadosCustomAdapter(recadosList, RecadosActivity.this);

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recadosList.clear();
                adapter.notifyDataSetChanged();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    RecadosInformation rInfo = new RecadosInformation();
                    rInfo.setData(ds.getValue(RecadosInformation.class).getData());
                    rInfo.setMensagem(ds.getValue(RecadosInformation.class).getMensagem());

                    Log.d(TAG, "showData: Data: " + rInfo.getData());
                    Log.d(TAG, "showData: Mensagem: " + rInfo.getMensagem());

                    recadosList.add(rInfo.getData() + "\n" + rInfo.getMensagem() + "\n");

                }
                Collections.reverse(recadosList);
                recados.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(RecadosActivity.this, MenuActivity.class);
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
