package com.michele.ideaunica.menu.Entrevista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.michele.ideaunica.R;
import com.michele.ideaunica.menu.Revista.GaleriaRevistaClass;

import java.util.ArrayList;

public class AdaptadorGaleriaEntrevista extends RecyclerView.Adapter<AdaptadorGaleriaEntrevista.MyViewHolder>{

    Context nContext;
    ArrayList<GaleriaEntrevistaClass> nData;

    public AdaptadorGaleriaEntrevista(Context nContext, ArrayList<GaleriaEntrevistaClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }
    @Override
    public AdaptadorGaleriaEntrevista.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        view= LayoutInflater.from(nContext).inflate(R.layout.item_galeria_entrevista,viewGroup,false);
        AdaptadorGaleriaEntrevista.MyViewHolder viewHolder = new AdaptadorGaleriaEntrevista.MyViewHolder(view);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Glide.with(nContext).load("https://ideaunicabolivia.com/"+nData.get(i).getUrl())
                .placeholder(R.drawable.cargando)
                .error(R.drawable.fondorosa)
                .into(myViewHolder.img);


    }
    @Override
    public int getItemCount() {
        return nData.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.item_photo_galeria_entrevista);
        }
    }
    public void setfilter(ArrayList<GaleriaEntrevistaClass> lista){
        nData = new ArrayList<>();
        nData.addAll(lista);
        notifyDataSetChanged();
    }
}