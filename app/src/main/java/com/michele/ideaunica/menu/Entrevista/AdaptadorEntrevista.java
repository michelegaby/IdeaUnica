package com.michele.ideaunica.menu.Entrevista;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.michele.ideaunica.R;
import com.michele.ideaunica.departamento.CategoriaClass;

import java.util.ArrayList;

public class AdaptadorEntrevista extends RecyclerView.Adapter<AdaptadorEntrevista.MyViewHolder>{

    Context nContext;
    ArrayList<EntrevistaClass> nData;

    public AdaptadorEntrevista(Context nContext, ArrayList<EntrevistaClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }

    @Override
    public AdaptadorEntrevista.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        view = LayoutInflater.from(nContext).inflate(R.layout.item_entrevista,viewGroup,false);
        AdaptadorEntrevista.MyViewHolder viewHolder = new AdaptadorEntrevista.MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder,final int i) {
        myViewHolder.titulo.setText(nData.get(i).getTitulo());
        Glide.with(nContext)
                .load("https://ideaunicabolivia.com/" + nData.get(i).getUrl())
                .placeholder(R.drawable.cargando)
                .error(R.drawable.fondorosa)
                .into(myViewHolder.img);

        //Funcionlidad de click a entrevista
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nContext,DetalleEntrevista.class);
                Bundle parametros = new Bundle();
                parametros.putInt("id",nData.get(i).getID());
                intent.putExtras(parametros);
                nContext.startActivity(intent);
            }
        });
        myViewHolder.lugar.setText(nData.get(i).getLugar());
    }

    @Override
    public int getItemCount() {
        return nData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView titulo;
        private TextView lugar;
        private ImageView img;
        private CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_titulo_entrevista);
            lugar = itemView.findViewById(R.id.item_lugar_entrevista);
            img = itemView.findViewById(R.id.item_photo_entrevista);
            cardView = itemView.findViewById(R.id.item_cardview_entrevista);
        }
    }

    public void setfilter(ArrayList<EntrevistaClass> listaCategoria){
        nData = new ArrayList<>();
        nData.addAll(listaCategoria);
        notifyDataSetChanged();
    }
}