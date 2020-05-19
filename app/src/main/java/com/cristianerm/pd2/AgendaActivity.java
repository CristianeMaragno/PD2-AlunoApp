package com.cristianerm.pd2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class AgendaActivity extends AppCompatActivity {

    ListView agenda;
    ImageButton voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        agenda = (ListView) findViewById(R.id.listAgenda);
        voltar = (ImageButton) findViewById(R.id.buttonVoltarAgenda);

        ArrayList<String> alimetacaoEsono = RecuperarDados();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alimetacaoEsono);
        agenda.setAdapter(arrayAdapter);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(AgendaActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });
    }

    private ArrayList<String> RecuperarDados(){
        ArrayList<String> dados = new ArrayList<String>();
        dados.add("Alimentação-manhã: Boa \n" +
                "Alimentação-tarde: Ruim \n" +
                "Sono: Não");
        dados.add("Alimentação-manhã: Ruim \n" +
                "Alimentação-tarde: Ruim \n" +
                "Sono: Sim");
        dados.add("Alimentação-manhã: Boa \n" +
                "Alimentação-tarde: Boa \n" +
                "Sono: Não");
        dados.add("Alimentação-manhã: Média \n" +
                "Alimentação-tarde: Ruim \n" +
                "Sono: Não");
        dados.add("Alimentação-manhã: Boa \n" +
                "Alimentação-tarde: Média \n" +
                "Sono: Sim");
        dados.add("Alimentação-manhã: Média \n" +
                "Alimentação-tarde: Média \n" +
                "Sono: Sim");
        dados.add("Alimentação-manhã: Ruim \n" +
                "Alimentação-tarde: Ruim \n" +
                "Sono: Sim");
        dados.add("Alimentação-manhã: Boa \n" +
                "Alimentação-tarde: Boa \n" +
                "Sono: Não");
        dados.add("Alimentação-manhã: Boa \n" +
                "Alimentação-tarde: Ruim \n" +
                "Sono: Sim");
        dados.add("Alimentação-manhã: Boa \n" +
                "Alimentação-tarde: Boa \n" +
                "Sono: Não");

        return dados;
    }
}
