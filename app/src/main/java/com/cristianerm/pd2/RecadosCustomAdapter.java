package com.cristianerm.pd2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecadosCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> recadosList = new ArrayList<String>();
    private Context context;

    private FirebaseDatabase mFirebaseDatase;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private String userID;


    public RecadosCustomAdapter(ArrayList<String> recadosList, Context context) {
        this.recadosList = recadosList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return recadosList.size();
    }

    @Override
    public Object getItem(int pos) {
        return recadosList.get(pos);
    }

    @Override
    public long getItemId(int position) {
        //return recadosList.get(pos).getId();
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        myRef = mFirebaseDatase.getReference().child("recados_lidos");


        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_recados_item, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.textRecados);
        listItemText.setText(recadosList.get(position));

        //Handle buttons and add onClickListeners
        Button buttonVisto = (Button)view.findViewById(R.id.buttonRecadoVisto);

        buttonVisto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String key = myRef.push().getKey();
                myRef.child(key).child("mensagem_lida").setValue(recadosList.get(position));
                myRef.child(key).child("user").setValue(userID);
            }
        });

        return view;
    }
}
