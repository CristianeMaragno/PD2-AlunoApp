package com.cristianerm.pd2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AgendaCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<CustomObject> array = new ArrayList<CustomObject>();
    private Context context;


    public AgendaCustomAdapter(ArrayList<CustomObject> array, Context context) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int pos) {
        return array.get(pos);
    }

    @Override
    public long getItemId(int position) {
        //return array.get(position).getId();
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.agenda_item, null);
        }

        //Handle TextView and display string from your list
        TextView textAgendaDate = (TextView)view.findViewById(R.id.text_agenda_date);
        TextView textAgendaInfo = (TextView)view.findViewById(R.id.text_agenda_info);
        textAgendaDate.setText(array.get(position).getDate());
        textAgendaInfo.setText(array.get(position).getTextInfo());

        return view;
    }

}
