package com.michele.ideaunica.menu.evento;

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

public class AdaptadorGaleriaEvento extends RecyclerView.Adapter<AdaptadorGaleriaEvento.MyViewHolder>{

    Context nContext;
    ArrayList<GaleriaEventoClass> nData;

    public AdaptadorGaleriaEvento(Context nContext, ArrayList<GaleriaEventoClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }
    @Override
    public AdaptadorGaleriaEvento.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        view= LayoutInflater.from(nContext).inflate(R.layout.item_galeria_evento,viewGroup,false);
        AdaptadorGaleriaEvento.MyViewHolder viewHolder = new AdaptadorGaleriaEvento.MyViewHolder(view);

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
            img=itemView.findViewById(R.id.item_galeria_evento);
        }
    }
    public void setfilter(ArrayList<GaleriaEventoClass> lista){
        nData = new ArrayList<>();
        nData.addAll(lista);
        notifyDataSetChanged();
    }
}