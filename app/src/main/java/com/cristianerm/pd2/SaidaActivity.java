package com.cristianerm.pd2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class SaidaActivity extends AppCompatActivity {

    Button saida;
    EditText pessoa_autorizada;
    Button ok_pessoa_autorizada;
   // ImageButton voltar;
    TextView textError;

    private static final String TAG = "Saida Activity";

    private FirebaseDatabase mFirebaseDatase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private DatabaseReference myRef2;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saida);

        saida = (Button) findViewById(R.id.buttonSaidaRapida);
        pessoa_autorizada = (EditText) findViewById(R.id.saidaCampoAutorizacao);
        ok_pessoa_autorizada = (Button) findViewById(R.id.buttonSaidaAutorizar);
       // voltar = (ImageButton) findViewById(R.id.buttonVoltarSaida);
        textError = (TextView) findViewById(R.id.textErrorAutorizar);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        myRef = mFirebaseDatase.getReference().child("solicitar_saida");
        myRef2 = mFirebaseDatase.getReference().child(userID).child("Responsaveis_auto_saida");


        /*voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(SaidaActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });*/

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(SaidaActivity.this, "User sighed in", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SaidaActivity.this, "User not sighed in", Toast.LENGTH_SHORT).show();
                }
            }
        };

        saida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                String nome_aluno = bundle.getString("nome_aluno");

                Date currentTime = Calendar.getInstance().getTime();
                String data_atual = currentTime.toString();
                String solicitacao = "Solicitação de saída rápida de: " + nome_aluno + " Hora: "+data_atual.substring(11,19) + " Data: " + data_atual.substring(4,10);

                String key = myRef.push().getKey();
                myRef.child(key).child("solicitacao").setValue(solicitacao);

                Toast.makeText(SaidaActivity.this, "Solicitação de saída rápida efetuada", Toast.LENGTH_SHORT).show();
            }
        });

        ok_pessoa_autorizada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String autorizar_pessoa = pessoa_autorizada.getText().toString();
                Toast.makeText(SaidaActivity.this, "Autorizar: " + autorizar_pessoa, Toast.LENGTH_LONG).show();

                if(!autorizar_pessoa.equals("")){
                    textError.setText("");
                    Date currentTime = Calendar.getInstance().getTime();
                    String data_atual = currentTime.toString();
                    String hora_data = "Hora: "+data_atual.substring(11,19) + " Data: " + data_atual.substring(4,10);

                    String key = myRef2.push().getKey();
                    myRef2.child(key).child("nome_pessoa_autorizada").setValue(autorizar_pessoa);
                    myRef2.child(key).child("hora_e_data").setValue(hora_data);

                    Toast.makeText(SaidaActivity.this, "Pessoa autorizada", Toast.LENGTH_SHORT).show();
                }else{
                    textError.setText("Você não preencheu todos os campos");
                }
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
