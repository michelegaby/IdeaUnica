package com.michele.ideaunica.ui.tareas;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.michele.ideaunica.R;
import com.michele.ideaunica.cumple.CumpleanyosClass;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Boolean.TRUE;

public class AdaptadorTarea extends RecyclerView.Adapter<AdaptadorTarea.MyViewHolder>{

    Context nContext;
    ArrayList<TareaClass> nData;

    private AdaptadorTarea.OnItemClickListener nListener;

    public interface OnItemClickListener{
        void onItenClick(int position);
    }

    public void setOnItemClickListener(AdaptadorTarea.OnItemClickListener listener){
        nListener=listener;
    }

    public AdaptadorTarea(Context nContext, ArrayList<TareaClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }
    @Override
    public AdaptadorTarea.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        view= LayoutInflater.from(nContext).inflate(R.layout.item_tarea,viewGroup,false);
        AdaptadorTarea.MyViewHolder viewHolder = new AdaptadorTarea.MyViewHolder(view,nListener);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final AdaptadorTarea.MyViewHolder myViewHolder, final int i) {
        myViewHolder.titulo.setText(nData.get(i).getTitulo());
        if(nData.get(i).getEstado()==2)
        {
            myViewHolder.titulo.setChecked(true);
            myViewHolder.titulo.setTextColor(Color.GRAY);
        }else
        {
            myViewHolder.titulo.setChecked(false);
            myViewHolder.titulo.setTextColor(Color.BLACK);
        }
    }
    @Override
    public int getItemCount() {
        return nData.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout linearLayout;
        private CheckBox titulo;
        public MyViewHolder(@NonNull View itemView, final AdaptadorTarea.OnItemClickListener listener) {
            super(itemView);
            titulo=itemView.findViewById(R.id.item_titulo_tarea);
            linearLayout=itemView.findViewById(R.id.item_tareas);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position= getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItenClick(position);
                        }
                    }
                }
            });
        }
    }
    public void setfilter(ArrayList<TareaClass> listaTarea){
        nData = new ArrayList<>();
        nData.addAll(listaTarea);
        notifyDataSetChanged();
    }
    public void UpdateItem(int position){
        TareaClass tareaClass1 = nData.remove(position);
        if(tareaClass1.getEstado()==1)
        {
            TareaClass tareaClass2= new TareaClass(tareaClass1.getId(),tareaClass1.getTitulo(),2);
            nData.add(tareaClass2);

        }else {
            TareaClass tareaClass2= new TareaClass(tareaClass1.getId(),tareaClass1.getTitulo(),1);
            nData.add(0,tareaClass2);
        }
        notifyDataSetChanged();
    }
}
