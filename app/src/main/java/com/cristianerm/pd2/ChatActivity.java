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

    Button pedagogico;
    Button diretoria;
    Toolbar toolbar_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        pedagogico = (Button) findViewById(R.id.buttonChatPedagogico);
        diretoria = (Button) findViewById(R.id.buttonChatDireção);

        toolbar_chat = (Toolbar) findViewById(R.id.tool_bar_chat);
        setSupportActionBar(toolbar_chat);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_chat.setTitle("");
        toolbar_chat.setSubtitle("");

        toolbar_chat.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i;
                i = new Intent(ChatActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });

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
        
    }

}