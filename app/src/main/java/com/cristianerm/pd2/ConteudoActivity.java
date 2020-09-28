package com.cristianerm.pd2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ConteudoActivity extends AppCompatActivity {

    Toolbar toolbar_conteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteudo);

        toolbar_conteudo = (Toolbar) findViewById(R.id.tool_bar_conteudo);
        setSupportActionBar(toolbar_conteudo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_conteudo.setTitle("");
        toolbar_conteudo.setSubtitle("");

        toolbar_conteudo.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i;
                i = new Intent(ConteudoActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });

    }
}
