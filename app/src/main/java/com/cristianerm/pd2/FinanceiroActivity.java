package com.cristianerm.pd2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class FinanceiroActivity extends AppCompatActivity {

    ListView listViewBoletos;
    Button recibo_anual;
    Toolbar toolbar_financeiro;

    private static final String TAG = "Financeiro Activity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatase;
    private DatabaseReference myRef;
    private DatabaseReference myRef2;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financeiro);

        listViewBoletos = (ListView) findViewById(R.id.listFinanceiro);
        recibo_anual = (Button) findViewById(R.id.buttonImpostoDeRenda);
        toolbar_financeiro = (Toolbar) findViewById(R.id.tool_bar_financeiro);
        setSupportActionBar(toolbar_financeiro);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_financeiro.setTitle("");
        toolbar_financeiro.setSubtitle("");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        int ano = Calendar.getInstance().get(Calendar.YEAR);
        String ano_atual = String.valueOf(ano);

        myRef = mFirebaseDatase.getReference().child(userID).child("boletos").child(ano_atual);
        myRef2 = mFirebaseDatase.getReference().child("sol_recibo_anual");

        toolbar_financeiro.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i;
                i = new Intent(FinanceiroActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    //Toast.makeText(FinanceiroActivity.this, "User sighed in", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(FinanceiroActivity.this, "User not sighed in", Toast.LENGTH_SHORT).show();
                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {

            ArrayList<String> array  = new ArrayList<>();
            FinanceiroCustomAdapter adapter = new FinanceiroCustomAdapter(array, FinanceiroActivity.this);

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                array.clear();
                adapter.notifyDataSetChanged();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    FinanceiroInformation fInfo = new FinanceiroInformation();
                    fInfo.setMes(ds.getValue(FinanceiroInformation.class).getMes());
                    fInfo.setAno(ds.getValue(FinanceiroInformation.class).getAno());

                    Log.d(TAG, "showData: Mes: " + fInfo.getMes());
                    Log.d(TAG, "showData: Ano: " + fInfo.getAno());

                    array.add(fInfo.getMes() + " " + fInfo.getAno());
                }

                listViewBoletos.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listViewBoletos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFromList = (listViewBoletos.getItemAtPosition(position)).toString();

                Bundle bundle = getIntent().getExtras();
                String nome_aluno = bundle.getString("nome_aluno");

                String nome_boleto = nome_aluno + "_" + selectedFromList + ".pdf";

                Toast.makeText(FinanceiroActivity.this, nome_boleto, Toast.LENGTH_SHORT).show();

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference gsReference = storage.getReferenceFromUrl("gs://pd2apps.appspot.com/Boletos/"+nome_boleto);

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
                        Toast.makeText(FinanceiroActivity.this, "Tente novamente mais tarde", Toast.LENGTH_LONG).show();
                    }
                });
            }});

        recibo_anual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                String nome_aluno = bundle.getString("nome_aluno");

                Date currentTime = Calendar.getInstance().getTime();
                String data_atual = currentTime.toString();
                String data_e_hora = "Hora: "+data_atual.substring(11,19) + " Data: " + data_atual.substring(4,10);

                String key = myRef2.push().getKey();
                myRef2.child(key).child("nome").setValue(nome_aluno);
                myRef2.child(key).child("data").setValue(data_e_hora);
                Toast.makeText(FinanceiroActivity.this, "Solicitação efetuada", Toast.LENGTH_LONG).show();
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
