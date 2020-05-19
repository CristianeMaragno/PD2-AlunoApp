package com.cristianerm.pd2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class DiarioActivity extends AppCompatActivity {

    ListView diario;
    ImageButton voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario);

        diario = (ListView) findViewById(R.id.listDiario);
        voltar = (ImageButton) findViewById(R.id.buttonVoltarDiario);

        ArrayList<String> posts_diario = new ArrayList<String>();
        posts_diario.add("Atividade da apostila de matemática Pag. 07");
        posts_diario.add("Trazer fruta para atividade nesta sexta-feira(08/10)");
        posts_diario.add("Atividade da apostila de matemática Pag. 07");
        posts_diario.add("Trazer fruta para atividade nesta sexta-feira(08/10)");
        posts_diario.add("Atividade da apostila de matemática Pag. 07");
        posts_diario.add("Trazer fruta para atividade nesta sexta-feira(08/10)");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, posts_diario);
        diario.setAdapter(arrayAdapter);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(DiarioActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });
    }
}
