package com.cristianerm.pd2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class FinanceiroActivity extends AppCompatActivity {

    Button boletos;
    Button recibo_anual;
    ImageButton voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financeiro);

        boletos = (Button) findViewById(R.id.buttonBoletos);
        recibo_anual = (Button) findViewById(R.id.buttonImpostoDeRenda);
        voltar = (ImageButton) findViewById(R.id.buttonVoltarFinanceiro);

        boletos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Boletos ainda não disponiveis", Toast.LENGTH_LONG).show();
            }
        });

        recibo_anual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Recibo anual para declaração do imposto de" +
                        "renda ainda não disponível", Toast.LENGTH_LONG).show();
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
}
