package com.cristianerm.pd2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Button login;
    SharedPreferences sp;
    EditText aluno;
    EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.buttonLogin);
        aluno = (EditText) findViewById(R.id.nomeAluno);
        senha = (EditText) findViewById(R.id.senhaAluno);
        final String alunoCorreto = "EVELLYN RECCO";
        final String senhaCorreta = "123";

        sp = getSharedPreferences("login", MODE_PRIVATE);

        if(sp.getBoolean("logged", false)){
            goToMenuActivity();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alunoDigitado = aluno.getText().toString();
                String senhaDigitada = senha.getText().toString();
                if (alunoDigitado.equals(alunoCorreto) && senhaDigitada.equals(senhaCorreta)){
                    goToMenuActivity();
                    sp.edit().putBoolean("logged",true).apply();
                }else{
                    Toast.makeText(getApplicationContext(), "Aluno ou senha incorreta", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void goToMenuActivity(){
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
    }
}
