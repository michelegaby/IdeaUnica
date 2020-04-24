package com.michele.ideaunica.ui.gastos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michele.ideaunica.R;

import java.util.ArrayList;

public class AdaptadorGastos extends RecyclerView.Adapter<AdaptadorGastos.MyViewHolder> implements View.OnClickListener{

    Context nContext;
    ArrayList<GastosClass> nData;
    private View.OnClickListener listener;

    public AdaptadorGastos(Context nContext, ArrayList<GastosClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }

    @Override
    public AdaptadorGastos.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        view = LayoutInflater.from(nContext).inflate(R.layout.item_gastos,viewGroup,false);
        AdaptadorGastos.MyViewHolder viewHolder = new AdaptadorGastos.MyViewHolder(view);

        //Funcionalidad en el padre
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorGastos.MyViewHolder myViewHolder, final int i) {
        myViewHolder.titulo.setText(nData.get(i).getTitulo());
        myViewHolder.fecha.setText(nData.get(i).getFecha());
        myViewHolder.tipo.setText(nData.get(i).getTipo());
        myViewHolder.dinero.setText(nData.get(i).getDinero());
        myViewHolder.cuotas.setText(nData.get(i).getCuotas());
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
        private TextView tipo;
        private TextView dinero;
        private TextView cuotas;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_titulo_gastos);
            fecha = itemView.findViewById(R.id.item_fecha_gastos);
            tipo = itemView.findViewById(R.id.item_tipo_gastos);
            dinero = itemView.findViewById(R.id.item_pago_gastos);
            cuotas = itemView.findViewById(R.id.item_cuota_gastos);
        }
    }

    public void setfilter(ArrayList<GastosClass> listaGastos){
        nData = new ArrayList<>();
        nData.addAll(listaGastos);
        notifyDataSetChanged();
    }
}
