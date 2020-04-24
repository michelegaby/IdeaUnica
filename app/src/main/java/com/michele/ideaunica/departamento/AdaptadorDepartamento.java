package com.michele.ideaunica.departamento;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.michele.ideaunica.R;

import java.util.ArrayList;

public class AdaptadorDepartamento extends RecyclerView.Adapter<AdaptadorDepartamento.MyViewHolder> implements View.OnClickListener {

    Context nContext;
    ArrayList<DepartamentoClass> nData;
    private View.OnClickListener listener;

    public AdaptadorDepartamento(Context nContext, ArrayList<DepartamentoClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(nContext).inflate(R.layout.item_departamento,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        //setAnimation(myViewHolder.itemView, i);
        myViewHolder.nombre.setText(nData.get(i).getNombre());
        Glide.with(nContext)
                .load("https://ideaunicabolivia.com/"+nData.get(i).getUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        myViewHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        myViewHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
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

        private TextView nombre;
        private ImageView img;
        private ProgressBar progressBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.item_dep_nombre);
            img = itemView.findViewById(R.id.item_dep_img);
            progressBar = itemView.findViewById(R.id.progress_departamento);
        }
    }
    public void setfilter(ArrayList<DepartamentoClass> listaEntrada){
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



