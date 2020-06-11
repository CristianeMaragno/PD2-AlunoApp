package com.cristianerm.pd2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class FinanceiroActivity extends AppCompatActivity {

    Button boletos;
    Button recibo_anual;
    ImageButton voltar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financeiro);

        boletos = (Button) findViewById(R.id.buttonBoletos);
        recibo_anual = (Button) findViewById(R.id.buttonImpostoDeRenda);
        voltar = (ImageButton) findViewById(R.id.buttonVoltarFinanceiro);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(FinanceiroActivity.this, "User sighed in", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FinanceiroActivity.this, "User not sighed in", Toast.LENGTH_SHORT).show();
                }
            }
        };


        boletos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseStorage storage = FirebaseStorage.getInstance();

                Bundle bundle = getIntent().getExtras();
                String nome_aluno = bundle.getString("nome_aluno");
                int ano = Calendar.getInstance().get(Calendar.YEAR);
                String ano_atual = String.valueOf(ano);
                String boleto_aluno = nome_aluno+"_"+ano_atual+".pdf";

                StorageReference gsReference = storage.getReferenceFromUrl("gs://pd2apps.appspot.com/Boletos/"+boleto_aluno);

                gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(FinanceiroActivity.this, "Download iniciado", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        exception.printStackTrace();// Handle any errors
                        Toast.makeText(FinanceiroActivity.this, "Boletos ainda não disponíveis", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        recibo_anual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseStorage storage = FirebaseStorage.getInstance();

                Bundle bundle = getIntent().getExtras();
                String nome_aluno = bundle.getString("nome_aluno");
                int ano = Calendar.getInstance().get(Calendar.YEAR);
                String ano_atual = String.valueOf(ano);
                String ir_aluno = "IR_"+nome_aluno+"_"+ano_atual+".pdf";

                StorageReference gsReference = storage.getReferenceFromUrl("gs://pd2apps.appspot.com/IR/"+ir_aluno);

                gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(FinanceiroActivity.this, "Download iniciado", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        exception.printStackTrace();// Handle any errors
                        Toast.makeText(FinanceiroActivity.this, "Recibo para IR ainda não disponível", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(FinanceiroActivity.this, MenuActivity.class);
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
