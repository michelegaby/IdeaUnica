package com.michele.ideaunica.departamento;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.michele.ideaunica.R;
import java.util.ArrayList;

public class AdaptadorCategoria extends RecyclerView.Adapter<AdaptadorCategoria.MyViewHolder> implements View.OnClickListener {

    Context nContext;
    ArrayList<CategoriaClass> nData;
    private View.OnClickListener listener;

    public AdaptadorCategoria(Context nContext, ArrayList<CategoriaClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }

    @Override
    public AdaptadorCategoria.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(nContext).inflate(R.layout.item_categoria,viewGroup,false);
        AdaptadorCategoria.MyViewHolder viewHolder = new AdaptadorCategoria.MyViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.titulo.setText(nData.get(i).getTitulo());
        myViewHolder.detalle.setText(nData.get(i).getCantidad());
        Glide.with(nContext)
                .load("https://ideaunicabolivia.com/"+nData.get(i).getUrl())
                .placeholder(R.drawable.cargando)
                .error(R.drawable.fondorosa)
                .into(myViewHolder.img);
    }

    @Override
    public int getItemCount() {
        return nData.size();
    }

    //Indica que la funcionalidad de seleccion esta en el padre
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
        private TextView detalle;
        private ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_cat_titulo);
            detalle = itemView.findViewById(R.id.item_cat_cant);
            img = itemView.findViewById(R.id.item_cat_img);
        }
    }

    public void setfilter(ArrayList<CategoriaClass> listaCategoria){
        nData = new ArrayList<>();
        nData.addAll(listaCategoria);
        notifyDataSetChanged();
    }
}