package com.cristianerm.pd2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    ImageView agenda;
    ImageView recados;
    ImageView calendario;
    ImageView chat;
    ImageView saida;
    ImageView seguranca;
    ImageView financeiro;
    ImageView diario;
    ImageView conteudo;

    Button logout;
    TextView identificadorAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        agenda = (ImageView) findViewById(R.id.agendaIcon);
        recados = (ImageView) findViewById(R.id.recadosIcon);
        calendario = (ImageView) findViewById(R.id.calendarioIcon);
        chat = (ImageView) findViewById(R.id.chatIcon);
        saida = (ImageView) findViewById(R.id.saidaIcon);
        seguranca = (ImageView) findViewById(R.id.segurancaIcon);
        financeiro = (ImageView) findViewById(R.id.financeiroIcon);
        diario = (ImageView) findViewById(R.id.diarioIcon);
        conteudo = (ImageView) findViewById(R.id.conteudoIcon);

        logout = (Button) findViewById(R.id.buttonLogout);
        identificadorAluno = (TextView) findViewById(R.id.identificadorAluno);

        String nomeAluno = "EVELLYN RECCO";
        identificadorAluno.setText(nomeAluno);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();
            }
        });

        agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MenuActivity.this, AgendaActivity.class);
                startActivity(i);
            }
        });

        recados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MenuActivity.this, RecadosActivity.class);
                startActivity(i);
            }
        });

        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MenuActivity.this, CalendarioActivity.class);
                startActivity(i);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MenuActivity.this, ChatActivity.class);
                startActivity(i);
            }
        });

        saida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MenuActivity.this, SaidaActivity.class);
                startActivity(i);
            }
        });

        seguranca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MenuActivity.this, SegurancaActivity.class);
                startActivity(i);
            }
        });

        financeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MenuActivity.this, FinanceiroActivity.class);
                startActivity(i);
            }
        });

        diario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MenuActivity.this, DiarioActivity.class);
                startActivity(i);
            }
        });

        conteudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MenuActivity.this, ConteudoActivity.class);
                startActivity(i);
            }
        });

    }
}
