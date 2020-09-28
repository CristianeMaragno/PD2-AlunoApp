package com.cristianerm.pd2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SegurancaActivity extends AppCompatActivity {

    Toolbar toolbar_seguranca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguranca);

        toolbar_seguranca = (Toolbar) findViewById(R.id.tool_bar_seguranca);
        setSupportActionBar(toolbar_seguranca);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_seguranca.setTitle("");
        toolbar_seguranca.setSubtitle("");

        toolbar_seguranca.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i;
                i = new Intent(SegurancaActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });
    }
}
