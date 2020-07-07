package com.cristianerm.pd2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapterDiario extends RecyclerView.Adapter<ImageAdapterDiario.ImageViewHolder> {
    private Context mContext;
    private List<DiarioTurmaInformation> mDiarioInformation;

    public ImageAdapterDiario(Context context, List<DiarioTurmaInformation> diarioInformation) {
        mContext = context;
        mDiarioInformation = diarioInformation;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_diario_item, parent, false);
        return new ImageViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        DiarioTurmaInformation uploadCurrent = mDiarioInformation.get(position);
        holder.text_mensagem_diario.setText(uploadCurrent.getDate() + "\n \n" + uploadCurrent.getMensagem());

        if(uploadCurrent.getImageUrl() == "No image"){
            holder.image_view_diario.setVisibility(View.GONE);
            //error, espace of the imageview is still there
        }else{
            Picasso.get()
                    .load(uploadCurrent.getImageUrl())
                    .into(holder.image_view_diario);
        }

    }
    @Override
    public int getItemCount() {
        return mDiarioInformation.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView text_mensagem_diario;
        public ImageView image_view_diario;
        public ImageViewHolder(View itemView) {
            super(itemView);
            text_mensagem_diario = itemView.findViewById(R.id.text_mensagem_diario);
            image_view_diario = itemView.findViewById(R.id.image_view_diario);
        }
    }
}
