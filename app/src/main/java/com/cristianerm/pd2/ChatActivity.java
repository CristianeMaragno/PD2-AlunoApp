package com.cristianerm.pd2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {

    Button pedagogico;
    Button diretoria;
    //ImageButton voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        pedagogico = (Button) findViewById(R.id.buttonChatPedagogico);
        diretoria = (Button) findViewById(R.id.buttonChatDireção);

        //voltar= (ImageButton) findViewById(R.id.buttonVoltarChat);

        pedagogico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setComponent(new  ComponentName("com.whatsapp","com.whatsapp.Conversation"));
                sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("554896303049")+"@s.whatsapp.net");
                startActivity(sendIntent);
            }
        });

        diretoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setComponent(new  ComponentName("com.whatsapp","com.whatsapp.Conversation"));
                sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("554896303049")+"@s.whatsapp.net");
                startActivity(sendIntent);
            }
        });

        /*voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(ChatActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });*/
    }

}