package com.michele.ideaunica.cursos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.michele.ideaunica.R;

import java.util.ArrayList;

public class AdaptadorGaleriaCurso extends RecyclerView.Adapter<AdaptadorGaleriaCurso.MyViewHolder>{

    Context nContext;
    ArrayList<GaleriaCursoClass> nData;

    public AdaptadorGaleriaCurso(Context nContext, ArrayList<GaleriaCursoClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }

    @Override
    public AdaptadorGaleriaCurso.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(nContext).inflate(R.layout.item_galeria_curso,viewGroup,false);
        AdaptadorGaleriaCurso.MyViewHolder viewHolder = new AdaptadorGaleriaCurso.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorGaleriaCurso.MyViewHolder myViewHolder, int i) {
        Glide.with(nContext).load("https://ideaunicabolivia.com/"+nData.get(i).getUrl())
                .placeholder(R.drawable.fondorosa)
                .error(R.drawable.cargando)
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
            img = itemView.findViewById(R.id.item_photo_galeria_curso);
        }
    }

    public void setfilter(ArrayList<GaleriaCursoClass> lista){
        nData = new ArrayList<>();
        nData.addAll(lista);
        notifyDataSetChanged();
    }
}