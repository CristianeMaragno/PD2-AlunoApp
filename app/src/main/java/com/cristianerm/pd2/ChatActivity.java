package com.cristianerm.pd2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {

    Toolbar toolbar_chat;
    Button button_pedagogico;
    Button button_diretoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar_chat = (Toolbar) findViewById(R.id.tool_bar_chat);
        setSupportActionBar(toolbar_chat);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_chat.setTitle("");
        toolbar_chat.setSubtitle("");

        button_pedagogico = (Button) findViewById(R.id.button_pedagogico_chat);
        button_diretoria = (Button) findViewById(R.id.button_diretoria_chat);

        toolbar_chat.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i;
                i = new Intent(ChatActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });

        button_pedagogico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setComponent(new  ComponentName("com.whatsapp","com.whatsapp.Conversation"));
                sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("554896303049")+"@s.whatsapp.net");
                startActivity(sendIntent);
            }
        });

        button_diretoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setComponent(new  ComponentName("com.whatsapp","com.whatsapp.Conversation"));
                sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("554896303049")+"@s.whatsapp.net");
                startActivity(sendIntent);
            }
        });

    }

}