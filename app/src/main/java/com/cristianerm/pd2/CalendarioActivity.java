package com.cristianerm.pd2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CalendarioActivity extends AppCompatActivity {

    ImageButton voltar;
    MaterialCalendarView calendarView;
    ListView datas_calendario_escolar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        voltar = (ImageButton) findViewById(R.id.buttonVoltarCalendario);
        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        datas_calendario_escolar = (ListView) findViewById(R.id.listCalendar);

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                Date data_data = date.getDate();
                String data_mes = data_data.toString();
                Toast.makeText(getApplicationContext(), data_mes.substring(4,7), Toast.LENGTH_LONG).show();
                //pegar eventos no database e atualizar list View com os eventos do mês pedido
            }
        });

        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date data_data_i = new Date();
        String data_mes_i = data_data_i.toString();
        Toast.makeText(getApplicationContext(), data_mes_i.substring(4,7), Toast.LENGTH_LONG).show();
        //pegar eventos no database e atualizar list View com os eventos do mês pedido (Mês atual quando entra na tela)

        ArrayList<String> datas_calendario = new ArrayList<String>();
        datas_calendario.add("20/10 Reunião pedagógica");
        datas_calendario.add("14/03 Dia mundial da água");
        datas_calendario.add("15/07 Festa do milho");
        datas_calendario.add("13/08 Dia dos pais");
        datas_calendario.add("20/10 Reunião pedagógica");
        datas_calendario.add("15/07 Festa do milho");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas_calendario);
        datas_calendario_escolar.setAdapter(arrayAdapter);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(CalendarioActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });
    }
}
