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
    private DatabaseReference myRef3;
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
        myRef = mFirebaseDatase.getReference().child("recados_lidos");
        myRef2 = mFirebaseDatase.getReference().child(userID).child("info_user");
        myRef3 = mFirebaseDatase.getReference().child("recados");

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

        String lido_status = recadosList.get(position).getLidoStatus();
        if(lido_status.equals("Yes")){
            buttonVisto.setEnabled(false);
        }

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserInformation uInfo = new UserInformation();
                    uInfo.setNome(ds.getValue(UserInformation.class).getNome());

                    final String nome_aluno = uInfo.getNome();
                    buttonVisto.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            String key = myRef.push().getKey();
                            myRef.child(key).child("mensagem_lida").setValue(recadosList.get(position));
                            myRef.child(key).child("user").setValue(nome_aluno);

                            myRef3.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        RecadosInformation rInfo = new RecadosInformation();
                                        rInfo.setMensagem(ds.getValue(RecadosInformation.class).getMensagem());

                                        String selected_message = recadosList.get(position).getTextInfo();
                                        String database_message = rInfo.getMensagem();

                                        Log.d("RecadosCustomAdapter", "selected_message: " + selected_message);
                                        Log.d("RecadosCustomAdapter", "database_message: " + database_message);

                                        if(selected_message.equals(database_message)){
                                            String recado_key = ds.getKey();
                                            myRef3.child(recado_key).child("lido").setValue("Yes");
                                        }

                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            buttonVisto.setEnabled(false);

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
