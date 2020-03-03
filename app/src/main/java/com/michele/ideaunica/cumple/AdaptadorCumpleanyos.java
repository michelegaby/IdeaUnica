package com.michele.ideaunica.cumple;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.michele.ideaunica.R;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorCumpleanyos extends RecyclerView.Adapter<AdaptadorCumpleanyos.MyViewHolder>{

    Context nContext;
    ArrayList<CumpleanyosClass> nData;

    private AdaptadorCumpleanyos.OnItemClickListener nListener;

    public interface OnItemClickListener{
        void onItenClick(int position);
    }

    public void setOnItemClickListener(AdaptadorCumpleanyos.OnItemClickListener listener){
        nListener=listener;
    }


    public AdaptadorCumpleanyos(Context nContext, ArrayList<CumpleanyosClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }
    @Override
    public AdaptadorCumpleanyos.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        view= LayoutInflater.from(nContext).inflate(R.layout.item_cumpleanyos,viewGroup,false);
        AdaptadorCumpleanyos.MyViewHolder viewHolder = new AdaptadorCumpleanyos.MyViewHolder(view,nListener);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final AdaptadorCumpleanyos.MyViewHolder myViewHolder, final int i) {
        myViewHolder.titulo.setText(nData.get(i).getTitulo());
        myViewHolder.fecha.setText("Fecha: " +nData.get(i).getFecha());
        myViewHolder.hora.setText("Hora: " +nData.get(i).getHora());
        try
        {
            if(nData.get(i).getUrlfoto()!= null && !nData.get(i).getUrlfoto().isEmpty())
            {
                File imgFile = new  File(nData.get(i).getUrlfoto());
                Uri imageUri = Uri.fromFile(imgFile);
                Glide.with(nContext)
                        .load(imageUri)
                        .placeholder(R.drawable.cargando)
                        .error(R.drawable.fondorosa)
                        .into(myViewHolder.img);
            }
        }catch (Exception e){
            Toast.makeText(nContext,nData.get(i).getUrlfoto()+" MSN "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public int getItemCount() {
        return nData.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView titulo;
        private TextView fecha;
        private TextView hora;
        private CircleImageView img;
        private LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView, final AdaptadorCumpleanyos.OnItemClickListener listener) {
            super(itemView);
            titulo=itemView.findViewById(R.id.item_titulo_cumpleanyos);
            fecha=itemView.findViewById(R.id.item_fecha_cumpleanyos);
            hora=itemView.findViewById(R.id.item_hora_cumpleanyos);
            img=itemView.findViewById(R.id.item_img_cumpleanyos);
            linearLayout=itemView.findViewById(R.id.item_cumpleanos);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position= getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItenClick(position);
                        }
                    }
                }
            });
        }
    }
    public void setfilter(ArrayList<CumpleanyosClass> listaCumpleanyos){
        nData = new ArrayList<>();
        nData.addAll(listaCumpleanyos);
        notifyDataSetChanged();
    }
}
