package com.michele.ideaunica.empresa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michele.ideaunica.R;

import java.util.ArrayList;

public class AdaptadorUbicacion extends RecyclerView.Adapter<AdaptadorUbicacion.MyViewHolder> implements View.OnClickListener {

    Context nContext;
    ArrayList<UbicacionClass> nData;
    private View.OnClickListener listener;

    public AdaptadorUbicacion(Context nContext, ArrayList<UbicacionClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }
    @Override
    public AdaptadorUbicacion.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        view= LayoutInflater.from(nContext).inflate(R.layout.item_direccion,viewGroup,false);
        AdaptadorUbicacion.MyViewHolder viewHolder = new AdaptadorUbicacion.MyViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull AdaptadorUbicacion.MyViewHolder myViewHolder, int i) {
        myViewHolder.titulo.setText(nData.get(i).getSucursal());
        myViewHolder.detalle.setText(nData.get(i).getDireccion());

    }
    @Override
    public int getItemCount() {
        return nData.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener =listener;
    }
    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView titulo;
        private TextView detalle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo=itemView.findViewById(R.id.item_dir_titulo);
            detalle=itemView.findViewById(R.id.item_dir_detalle);
        }
    }

    public void setfilter(ArrayList<UbicacionClass> listUbicacion){
        nData = new ArrayList<>();
        nData.addAll(listUbicacion);
        notifyDataSetChanged();

    }
}
