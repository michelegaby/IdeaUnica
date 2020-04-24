package com.michele.ideaunica.ui.notas;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.michele.ideaunica.R;

import java.util.ArrayList;

public class AdaptadorNota extends RecyclerView.Adapter<AdaptadorNota.MyViewHolder> implements View.OnClickListener {


    Context nContext;
    ArrayList<NotaClass> nData;
    private View.OnClickListener listener;

    public AdaptadorNota(Context nContext, ArrayList<NotaClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }

    @Override
    public AdaptadorNota.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        view = LayoutInflater.from(nContext).inflate(R.layout.item_nota,viewGroup,false);
        AdaptadorNota.MyViewHolder viewHolder = new AdaptadorNota.MyViewHolder(view);

        //Funcionalidad en el padre
        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorNota.MyViewHolder myViewHolder, final int i) {
        myViewHolder.titulo.setText(nData.get(i).getTitulo());
        myViewHolder.fecha.setText(nData.get(i).getFecha() + " - Ultima Actualización");
        myViewHolder.container.setCardBackgroundColor(Color.parseColor("#" + nData.get(i).getColor()));
    }

    @Override
    public int getItemCount() {
        return nData.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView titulo;
        private TextView fecha;
        private CardView container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_titulo_nota);
            fecha = itemView.findViewById(R.id.item_actualizacion_nota);
            container = itemView.findViewById(R.id.item_cardView_nota);
        }
    }

    public void setfilter(ArrayList<NotaClass> listaNota){
        nData = new ArrayList<>();
        nData.addAll(listaNota);
        notifyDataSetChanged();
    }
}
