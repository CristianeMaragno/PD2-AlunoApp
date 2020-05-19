package com.cristianerm.pd2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class RecadosActivity extends AppCompatActivity {

    ListView recados;
    ImageButton voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recados);

        recados = (ListView) findViewById(R.id.listRecados);
        voltar = (ImageButton) findViewById(R.id.buttonVoltarRecados);

        ArrayList<String> recadosList = new ArrayList<String>();
        recadosList.add("Renuão pedagógica semana que vem(05/04) às 18:00 nas dependências da escola. Estaremos esperando a" +
                "presença de todos.\n");
        recadosList.add("Festa Junina será no salão da igreja, com horário a ser confimado\n");
        recadosList.add("Alertamos os pais de um aumento nos casos de virose em nossa comunidade escolar, portanto sugerimos manter " +
                "crianças que apresentam simtomas em casa para não infectar os colegas\n");
        recadosList.add("Encontro de mães semana que vem(10/04) às 18:00 nas dependências da escola. Estaremos esperando a" +
                "presença de todas.\n");
        recadosList.add("Alertamos os pais de um aumento nos casos de virose em nossa comunidade escolar, portanto sugerimos manter " +
                "crianças que apresentam simtomas em casa para não infectar os colegas\n");

        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recadosList);
        //recados.setAdapter(arrayAdapter);

        RecadosCustomAdapter adapter = new RecadosCustomAdapter(recadosList, this);
        recados.setAdapter(adapter);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(RecadosActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });
    }
}
