package com.michele.ideaunica.menu.Revista;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.michele.ideaunica.R;
import com.michele.ideaunica.departamento.CategoriaClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdaptadorRevista extends RecyclerView.Adapter<AdaptadorRevista.MyViewHolder>{

    Context nContext;
    ArrayList<RevistaClass> nData;

    public AdaptadorRevista(Context nContext, ArrayList<RevistaClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }
    @Override
    public AdaptadorRevista.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        view = LayoutInflater.from(nContext).inflate(R.layout.item_revista,viewGroup,false);
        AdaptadorRevista.MyViewHolder viewHolder = new AdaptadorRevista.MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        myViewHolder.titulo.setText(nData.get(i).getTitulo());
        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formateador = new SimpleDateFormat("MMMM d, yyyy");
        try {
            Date date = parseador.parse(nData.get(i).getFecha());
            myViewHolder.fecha.setText(formateador.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            myViewHolder.fecha.setText(nData.get(i).getFecha());
        }
        myViewHolder.descripcion.setText(nData.get(i).getDescripcion());

        Glide.with(nContext).load("https://ideaunicabolivia.com/"+nData.get(i).getUrl())
                .placeholder(R.drawable.fondorosa)
                .error(R.drawable.cargando)
                .into(myViewHolder.img);

        myViewHolder.leer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nContext,DetalleRevista.class);
                Bundle parametros = new Bundle();
                parametros.putInt("id",nData.get(i).getID());
                parametros.putString("titulo",nData.get(i).getTitulo());
                parametros.putString("fecha",myViewHolder.fecha.getText().toString());
                parametros.putString("autor",nData.get(i).getAutor());
                parametros.putString("descripcion",nData.get(i).getDescripcion());
                parametros.putString("descripcion_final",nData.get(i).getDescripcion_final());
                parametros.putString("facebook",nData.get(i).getFacebook());
                parametros.putString("whatsapp",nData.get(i).getWhatsapp());
                parametros.putString("instagram",nData.get(i).getInstagram());
                parametros.putString("email",nData.get(i).getEmail());
                parametros.putString("url",nData.get(i).getUrl());
                intent.putExtras(parametros);
                nContext.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return nData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView titulo;
        private TextView fecha;
        private ImageView img;
        private TextView descripcion;
        private Button leer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_titulo_revista);
            fecha = itemView.findViewById(R.id.item_fecha_revista);
            img = itemView.findViewById(R.id.item_img_revista);
            descripcion = itemView.findViewById(R.id.item_detalle_revista);
            leer = itemView.findViewById(R.id.item_post_leer_mas_revista);
        }
    }

    public void setfilter(ArrayList<RevistaClass> lista){
        nData = new ArrayList<>();
        nData.addAll(lista);
        notifyDataSetChanged();
    }
}