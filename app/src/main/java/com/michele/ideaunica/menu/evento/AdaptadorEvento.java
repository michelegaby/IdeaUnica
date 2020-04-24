package com.michele.ideaunica.menu.evento;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.michele.ideaunica.R;
import com.michele.ideaunica.departamento.DepartamentoClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdaptadorEvento extends RecyclerView.Adapter<AdaptadorEvento.MyViewHolder> implements View.OnClickListener {

    Context nContext;
    ArrayList<EventoClass> nData;
    private View.OnClickListener listener;

    public AdaptadorEvento(Context nContext, ArrayList<EventoClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        view = LayoutInflater.from(nContext).inflate(R.layout.item_evento,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        //Funcionalidad se encuentra en el padre
        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        myViewHolder.titulo.setText(nData.get(i).getTitulo());
        SimpleDateFormat dia = new SimpleDateFormat("dd");
        SimpleDateFormat mes = new SimpleDateFormat("MMM");
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
        }
        try {
            Date date = parseador.parse(nData.get(i).getFecha());
            myViewHolder.dia.setText(dia.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            myViewHolder.dia.setText("00");
        }

        try {
            Date date = parseador.parse(nData.get(i).getFecha());
            myViewHolder.mes.setText(mes.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            myViewHolder.dia.setText("En.");
        }

        myViewHolder.detalle.setText(nData.get(i).getDescripcion());
        Glide.with(nContext)
                .load(nData.get(i).getUrl())
                .placeholder(R.drawable.fondorosa)
                .error(R.drawable.cargando)
                .into(myViewHolder.img);
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
        private TextView dia;
        private TextView mes;
        private TextView detalle;
        private ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_titulo_evento);
            fecha = itemView.findViewById(R.id.item_fecha_evento);
            dia = itemView.findViewById(R.id.item_dia_evento);
            mes = itemView.findViewById(R.id.item_mes_evento);
            detalle = itemView.findViewById(R.id.item_detalle_evento);
            img = itemView.findViewById(R.id.item_img_evento);
        }
    }

    public void setfilter(ArrayList<EventoClass> listaEntrada){
        nData = new ArrayList<>();
        nData.addAll(listaEntrada);
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



