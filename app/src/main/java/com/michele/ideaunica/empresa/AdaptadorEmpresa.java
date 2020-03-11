package com.michele.ideaunica.empresa;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.michele.ideaunica.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorEmpresa extends RecyclerView.Adapter<AdaptadorEmpresa.MyViewHolder>{

    Context nContext;
    ArrayList<EmpresaClass> nData;

    public AdaptadorEmpresa(Context nContext, ArrayList<EmpresaClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }
    @Override
    public AdaptadorEmpresa.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(nContext).inflate(R.layout.item_empresa,viewGroup,false);
        AdaptadorEmpresa.MyViewHolder viewHolder = new AdaptadorEmpresa.MyViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final AdaptadorEmpresa.MyViewHolder myViewHolder, final int i) {
        myViewHolder.titulo.setText(nData.get(i).getTitulo());
        myViewHolder.direccion.setText(nData.get(i).getDireccion());
        myViewHolder.categoria.setText(nData.get(i).getCategoria());
        Glide.with(nContext).load("https://ideaunicabolivia.com/"+nData.get(i).getUrl())
                .placeholder(R.drawable.fondorosa)
                .error(R.drawable.fondorosa)
                .into(myViewHolder.circleImageView);
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nContext, Empresa.class);
                Bundle parametros = new Bundle();
                parametros.putInt("id",nData.get(i).getID());
                parametros.putString("titulo",nData.get(i).getTitulo());
                parametros.putString("color",nData.get(i).getColor());
                parametros.putString("codigo",nData.get(i).getCodigo());
                parametros.putString("categoria",nData.get(i).getCategoria());
                parametros.putString("descripcion",nData.get(i).getDescripcion());
                parametros.putString("numero",nData.get(i).getNumero());
                parametros.putString("whatsapp",nData.get(i).getWhatsapp());
                parametros.putString("facebook",nData.get(i).getFacebook());
                parametros.putString("nombrefacebook",nData.get(i).getNomFacebook());
                parametros.putString("instagram",nData.get(i).getInstagram());
                parametros.putString("nombreinstagram",nData.get(i).getNomInstagram());
                parametros.putString("paginaweb",nData.get(i).getPagewWeb());
                parametros.putString("email",nData.get(i).getEmail());
                parametros.putString("url",nData.get(i).getUrl());
                parametros.putString("urlToolBar",nData.get(i).getUrlToolBar());
                intent.putExtras(parametros);
                Pair[] pairs = new Pair[2];
                pairs[0]= new Pair<View,String>(myViewHolder.circleImageView,"imgTransition");
                pairs[1]= new Pair<View,String>(myViewHolder.titulo,"letraTransition");
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

        private TextView titulo;
        private TextView direccion;
        private TextView categoria;
        private CircleImageView circleImageView;
        private CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo=itemView.findViewById(R.id.item_emp_titulo);
            direccion=itemView.findViewById(R.id.item_emp_direccion);
            categoria=itemView.findViewById(R.id.item_emp_cat);
            circleImageView=itemView.findViewById(R.id.item_emp_cv_logo);
            cardView=itemView.findViewById(R.id.item_empresa_item);
        }
    }
    public void setfilter(ArrayList<EmpresaClass> listaEmpresa){
        nData = new ArrayList<>();
        nData.addAll(listaEmpresa);
        notifyDataSetChanged();
    }
}
