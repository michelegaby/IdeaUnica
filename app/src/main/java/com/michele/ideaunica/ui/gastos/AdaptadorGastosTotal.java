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

public class AdaptadorGastosTotal extends RecyclerView.Adapter<AdaptadorGastosTotal.MyViewHolder>{

    Context nContext;
    ArrayList<GastosTotalClass> nData;

    public AdaptadorGastosTotal(Context nContext, ArrayList<GastosTotalClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }
    @Override
    public AdaptadorGastosTotal.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        view= LayoutInflater.from(nContext).inflate(R.layout.item_gastos_total,viewGroup,false);
        AdaptadorGastosTotal.MyViewHolder viewHolder = new AdaptadorGastosTotal.MyViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final AdaptadorGastosTotal.MyViewHolder myViewHolder, final int i) {
        myViewHolder.titulo.setText(nData.get(i).getTitulo());
        myViewHolder.total.setText(nData.get(i).getTotal());
        myViewHolder.apagar.setText(nData.get(i).getApagar());
        myViewHolder.pago.setText(nData.get(i).getPago());
        myViewHolder.vencido.setText(nData.get(i).getVencido());
    }
    @Override
    public int getItemCount() {
        return nData.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView titulo;
        private TextView total;
        private TextView apagar;
        private TextView pago;
        private TextView vencido;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo=itemView.findViewById(R.id.item_titulo_gastos_total);
            total=itemView.findViewById(R.id.item_total_gastos_total);
            apagar=itemView.findViewById(R.id.item_apagar_gastos_total);
            pago=itemView.findViewById(R.id.item_pago_gastos_total);
            vencido=itemView.findViewById(R.id.item_vencido_gastos_total);
        }
    }
    public void setfilter(ArrayList<GastosTotalClass> listaGastos){
        nData = new ArrayList<>();
        nData.addAll(listaGastos);
        notifyDataSetChanged();
    }
}
