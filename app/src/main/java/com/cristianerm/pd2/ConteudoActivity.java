package com.cristianerm.pd2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ConteudoActivity extends AppCompatActivity {

    ImageButton voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteudo);

        voltar = (ImageButton) findViewById(R.id.buttonVoltarConteudo);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(ConteudoActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });
    }
}
