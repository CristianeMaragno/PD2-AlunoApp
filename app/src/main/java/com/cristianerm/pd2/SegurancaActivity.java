package com.cristianerm.pd2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SegurancaActivity extends AppCompatActivity {

    ImageButton voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguranca);

        voltar = (ImageButton) findViewById(R.id.buttonVoltarSeguranca);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(SegurancaActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });
    }
}
