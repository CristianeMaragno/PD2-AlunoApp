package com.cristianerm.pd2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SaidaActivity extends AppCompatActivity {

    Button saida;
    EditText pessoa_autorizada;
    Button ok_pessoa_autorizada;
    ImageButton voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saida);

        saida = (Button) findViewById(R.id.buttonSaidaRapida);
        pessoa_autorizada = (EditText) findViewById(R.id.saidaCampoAutorizacao);
        ok_pessoa_autorizada = (Button) findViewById(R.id.buttonSaidaAutorizar);
        voltar = (ImageButton) findViewById(R.id.buttonVoltarSaida);

        saida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ok_pessoa_autorizada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String autorizar_pessoa = pessoa_autorizada.getText().toString();
                Toast.makeText(getApplicationContext(), "Autorizar: " + autorizar_pessoa, Toast.LENGTH_LONG).show();
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(SaidaActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });
    }
}
