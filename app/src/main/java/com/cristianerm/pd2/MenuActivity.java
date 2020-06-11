package com.cristianerm.pd2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity {

    ImageView agenda;
    ImageView recados;
    ImageView calendario;
    ImageView chat;
    ImageView saida;
    ImageView seguranca;
    ImageView financeiro;
    ImageView diario;
    ImageView conteudo;

    Button logout;
    TextView identificadorAluno;

    private static final String TAG = "MenuActivity";

    private FirebaseDatabase mFirebaseDatase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        agenda = (ImageView) findViewById(R.id.agendaIcon);
        recados = (ImageView) findViewById(R.id.recadosIcon);
        calendario = (ImageView) findViewById(R.id.calendarioIcon);
        chat = (ImageView) findViewById(R.id.chatIcon);
        saida = (ImageView) findViewById(R.id.saidaIcon);
        seguranca = (ImageView) findViewById(R.id.segurancaIcon);
        financeiro = (ImageView) findViewById(R.id.financeiroIcon);
        diario = (ImageView) findViewById(R.id.diarioIcon);
        conteudo = (ImageView) findViewById(R.id.conteudoIcon);

        logout = (Button) findViewById(R.id.buttonLogout);
        identificadorAluno = (TextView) findViewById(R.id.identificadorAluno);

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
                    Toast.makeText(MenuActivity.this, "User sighed in", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuActivity.this, "User not sighed in", Toast.LENGTH_SHORT).show();
                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserInformation uInfo = new UserInformation();
                    uInfo.setNome(ds.getValue(UserInformation.class).getNome());

                    Log.d(TAG, "showData: Nome: " + uInfo.getNome());

                    identificadorAluno.setText(uInfo.getNome());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MenuActivity.this, AgendaActivity.class);
                startActivity(i);
            }
        });

        recados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MenuActivity.this, RecadosActivity.class);
                startActivity(i);
            }
        });

        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MenuActivity.this, CalendarioActivity.class);
                startActivity(i);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MenuActivity.this, ChatActivity.class);
                startActivity(i);
            }
        });

        saida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MenuActivity.this, SaidaActivity.class);
                String nome_aluno = identificadorAluno.getText().toString();
                if(nome_aluno == "Aluno(a)"){
                    Toast.makeText(MenuActivity.this, "Espere um momento, aplicação carregando...", Toast.LENGTH_LONG).show();
                }else{
                    i.putExtra("nome_aluno", nome_aluno);
                    startActivity(i);
                }
            }
        });

        seguranca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MenuActivity.this, SegurancaActivity.class);
                startActivity(i);
            }
        });

        financeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MenuActivity.this, FinanceiroActivity.class);
                String nome_aluno = identificadorAluno.getText().toString();
                if(nome_aluno == "Aluno(a)"){
                    Toast.makeText(MenuActivity.this, "Espere um momento, aplicação carregando...", Toast.LENGTH_LONG).show();
                }else{
                    i.putExtra("nome_aluno", nome_aluno);
                    startActivity(i);
                }
            }
        });

        diario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MenuActivity.this, DiarioActivity.class);
                startActivity(i);
            }
        });

        conteudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MenuActivity.this, ConteudoActivity.class);
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
