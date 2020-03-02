package com.michele.ideaunica.evento;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.michele.ideaunica.R;
import com.michele.ideaunica.departamento.DepartamentoClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdaptadorEvento extends RecyclerView.Adapter<AdaptadorEvento.MyViewHolder>{

    Context nContext;
    ArrayList<EventoClass> nData;

    public AdaptadorEvento(Context nContext, ArrayList<EventoClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(nContext).inflate(R.layout.item_evento,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        /*myViewHolder.titulo.setTypeface(Typeface.createFromAsset(nContext.getAssets(),"fonts/toledo-serial-bold.ttf"));
        myViewHolder.titulo.setText(nData.get(i).getTitulo());
        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formateador = new SimpleDateFormat("MMM d, yyyy");
        try {
            Date date = parseador.parse(nData.get(i).getFecha());
            myViewHolder.fecha.setText(formateador.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            myViewHolder.fecha.setText(nData.get(i).getFecha());
        }
        try {
            Date date = parseador.parse(nData.get(i).getFecha_final());
            myViewHolder.fecha.setText(myViewHolder.fecha.getText().toString()+"-"+formateador.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        Glide.with(nContext).load(nData.get(i).getUrl())
                .placeholder(R.drawable.cargando)
                .error(R.drawable.fondorosa)
                .into(myViewHolder.img);
        myViewHolder.contenido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nContext, DetalleEvento.class);
                Bundle parmetros= new Bundle();
                parmetros.putString("ID",nData.get(i).getID()+"");
                parmetros.putString("url",nData.get(i).getUrl());
                parmetros.putString("titulo",nData.get(i).getTitulo());
                parmetros.putString("descripcion",nData.get(i).getDescripcion());
                intent.putExtras(parmetros);
                Pair[] pairs = new Pair[3];
                pairs[0]= new Pair<View,String>(myViewHolder.img,"imgTransition3");
                ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation((Activity) nContext,pairs);
                nContext.startActivity(intent,options.toBundle());
            }
        });
    }
    @Override
    public int getItemCount() {
        return nData.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private CardView contenido;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.item_img_evento);
            contenido=itemView.findViewById(R.id.item_evento_card);
        }
    }
    public void setfilter(ArrayList<EventoClass> listaEvento){
        nData = new ArrayList<>();
        nData.addAll(listaEvento);
        notifyDataSetChanged();
    }
    private int lastPosition = -1;
    private void setAnimation(View viewToAnimate, int position) {

        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(nContext, android.R.anim.slide_in_left);
            animation.setDuration(1000);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        } else if ( position < lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(nContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}