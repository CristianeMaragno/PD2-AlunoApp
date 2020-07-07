package com.cristianerm.pd2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

public class CalendarioActivity extends AppCompatActivity {

    ImageButton voltar;
    MaterialCalendarView calendarView;
    ListView datas_calendario_escolar;
    String mes_atual;
    String ano_atual;
    String data_atual;

    private static final String TAG = "Calendario Activity";

    private FirebaseDatabase mFirebaseDatase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private DatabaseReference myRef2;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        voltar = (ImageButton) findViewById(R.id.buttonVoltarCalendario);
        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        datas_calendario_escolar = (ListView) findViewById(R.id.listCalendar);

        final ArrayList<String> datas_calendario = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(CalendarioActivity.this, android.R.layout.simple_list_item_1, datas_calendario);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatase = FirebaseDatabase.getInstance();

        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date data_data_i = new Date();
        ano_atual = data_data_i.toString().substring(30,34);
        mes_atual = data_data_i.toString().substring(4,7);
        data_atual = mes_atual + "_" + ano_atual;
        Toast.makeText(CalendarioActivity.this, data_atual, Toast.LENGTH_LONG).show();

        myRef = mFirebaseDatase.getReference().child("calendario_pedagogico").child(data_atual);

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                Date data_data = date.getDate();
                ano_atual = data_data.toString().substring(30,34);
                mes_atual = data_data.toString().substring(4,7);
                data_atual = mes_atual + "_" + ano_atual;
                Toast.makeText(CalendarioActivity.this, data_atual, Toast.LENGTH_LONG).show();
                //pegar eventos no database e atualizar list View com os eventos do mês pedido
                myRef2 = mFirebaseDatase.getReference().child("calendario_pedagogico").child(data_atual);
                myRef2.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        datas_calendario.clear();
                        adapter.notifyDataSetChanged();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            CalendarioInformation cInfo = new CalendarioInformation();
                            cInfo.setEvento(ds.getValue(CalendarioInformation.class).getEvento());

                            Log.d(TAG, "showData: Evento " + cInfo.getEvento());

                            datas_calendario.add(cInfo.getEvento());

                        }
                        datas_calendario_escolar.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(CalendarioActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(CalendarioActivity.this, "User sighed in", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CalendarioActivity.this, "User not sighed in", Toast.LENGTH_SHORT).show();
                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                datas_calendario.clear();
                adapter.notifyDataSetChanged();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CalendarioInformation cInfo = new CalendarioInformation();
                    cInfo.setEvento(ds.getValue(CalendarioInformation.class).getEvento());

                    Log.d(TAG, "showData: Evento " + cInfo.getEvento());

                    datas_calendario.add(cInfo.getEvento());

                }

                datas_calendario_escolar.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}