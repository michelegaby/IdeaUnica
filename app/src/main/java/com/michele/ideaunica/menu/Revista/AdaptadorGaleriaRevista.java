package com.michele.ideaunica.menu.Revista;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdaptadorGaleriaRevista extends RecyclerView.Adapter<AdaptadorGaleriaRevista.MyViewHolder>{

    Context nContext;
    ArrayList<GaleriaRevistaClass> nData;

    public AdaptadorGaleriaRevista(Context nContext, ArrayList<GaleriaRevistaClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }
    @Override
    public AdaptadorGaleriaRevista.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        view= LayoutInflater.from(nContext).inflate(R.layout.item_galeria_revista,viewGroup,false);
        AdaptadorGaleriaRevista.MyViewHolder viewHolder = new AdaptadorGaleriaRevista.MyViewHolder(view);

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
            img=itemView.findViewById(R.id.item_photo_galeria_revista);
        }
    }
    public void setfilter(ArrayList<GaleriaRevistaClass> lista){
        nData = new ArrayList<>();
        nData.addAll(lista);
        notifyDataSetChanged();
    }
}