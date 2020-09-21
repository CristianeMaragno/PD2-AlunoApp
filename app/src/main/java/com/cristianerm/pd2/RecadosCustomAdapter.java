package com.cristianerm.pd2;

import android.content.Context;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecadosCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<CustomObjectRecados> recadosList = new ArrayList<CustomObjectRecados>();
    private Context context;

    private FirebaseDatabase mFirebaseDatase;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private DatabaseReference myRef2;
    private String userID;


    public RecadosCustomAdapter(ArrayList<CustomObjectRecados> recadosList, Context context) {
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
        myRef = mFirebaseDatase.getReference().child(userID).child("info_user");

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_recados_item, null);
        }

        //Handle TextView and display string from your list
        TextView textRecadosDate = (TextView)view.findViewById(R.id.text_recados_date);
        TextView textRecados = (TextView)view.findViewById(R.id.text_recados);
        textRecadosDate.setText(recadosList.get(position).getDate());
        textRecados.setText(recadosList.get(position).getTextInfo());

        //Handle buttons and add onClickListeners
        final Button buttonVisto = (Button)view.findViewById(R.id.buttonRecadoVisto);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserInformation uInfo = new UserInformation();
                    uInfo.setNome(ds.getValue(UserInformation.class).getNome());

                    final String nome_aluno = uInfo.getNome();
                    myRef2 = mFirebaseDatase.getReference().child("recados_lidos").child(nome_aluno);


                    buttonVisto.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            String key = myRef2.push().getKey();
                            String date = recadosList.get(position).getDate();
                            String textInfo = recadosList.get(position).getTextInfo();
                            myRef2.child(key).child("date").setValue(date);
                            myRef2.child(key).child("textInfo").setValue(textInfo);
                        }
                    });

                    myRef2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                RecadosInformation rInfo = new RecadosInformation();
                                rInfo.setDate(ds.getValue(RecadosInformation.class).getDate());
                                rInfo.setTextInfo(ds.getValue(RecadosInformation.class).getTextInfo());

                                String read_date = rInfo.getDate();
                                String read_text = rInfo.getTextInfo();

                                String selected_date = recadosList.get(position).getDate();
                                String selected_text = recadosList.get(position).getTextInfo();

                                Log.d("RecadosCustomAdapter", "read_text: " + read_text);
                                Log.d("RecadosCustomAdapter", "selected_text: " + selected_text);

                                if(read_text != null & selected_text != null & read_date != null & selected_date !=null){
                                    if(read_text.equals(selected_text) & read_date.equals(selected_date)){
                                        buttonVisto.setEnabled(false);
                                    }
                                }

                                //Log.d("RecadosCustomAdapter", "date: " + rInfo.getDate());
                                //Log.d("RecadosCustomAdapter", "text: " + rInfo.getTextInfo());
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }


}
