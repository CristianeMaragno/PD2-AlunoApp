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
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AgendaActivity extends AppCompatActivity {

    ListView agenda;
    ImageButton voltar;
    ProgressBar progressBar;

    private static final String TAG = "Agenda Activity";

    private FirebaseDatabase mFirebaseDatase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        agenda = (ListView) findViewById(R.id.listAgenda);
        voltar = (ImageButton) findViewById(R.id.buttonVoltarAgenda);
        progressBar = (ProgressBar) findViewById(R.id.progressBarAgenda);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        myRef = mFirebaseDatase.getReference().child(userID).child("agenda_pessoal");


        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(AgendaActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(AgendaActivity.this, "User sighed in", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AgendaActivity.this, "User not sighed in", Toast.LENGTH_SHORT).show();
                }
            }
        };

        myRef.limitToLast(15).addValueEventListener(new ValueEventListener() {

            ArrayList<String> array  = new ArrayList<>();
            AgendaCustomAdapter adapter = new AgendaCustomAdapter(array, AgendaActivity.this);

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                array.clear();
                adapter.notifyDataSetChanged();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserAgendaInformation uInfo = new UserAgendaInformation();
                    uInfo.setData(ds.getValue(UserAgendaInformation.class).getData());
                    uInfo.setAlimento(ds.getValue(UserAgendaInformation.class).getAlimento());
                    uInfo.setSono(ds.getValue(UserAgendaInformation.class).getSono());

                    Log.d(TAG, "showData: Data: " + uInfo.getData());
                    Log.d(TAG, "showData: Alimentação: " + uInfo.getAlimento());
                    Log.d(TAG, "showData: Sono: " + uInfo.getSono());

                    array.add(uInfo.getData() + "\n" + uInfo.getAlimento() + "\n" + uInfo.getSono());

                }

                Collections.reverse(array);
                progressBar.setVisibility(View.GONE);
                agenda.setAdapter(adapter);
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