package com.michele.ideaunica.ui.tareas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.R;

import java.util.ArrayList;

public class AdaptadorTarea extends RecyclerView.Adapter<AdaptadorTarea.MyViewHolder>{

    Context nContext;
    ArrayList<TareaClass> nData;

    private AdaptadorTarea.OnItemClickListener nListener;

    public interface OnItemClickListener{
        void onItenClick(int position);
    }

    public void setOnItemClickListener(AdaptadorTarea.OnItemClickListener listener){
        nListener = listener;
    }

    public AdaptadorTarea(Context nContext, ArrayList<TareaClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }

    @Override
    public AdaptadorTarea.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        view = LayoutInflater.from(nContext).inflate(R.layout.item_tarea,viewGroup,false);
        AdaptadorTarea.MyViewHolder viewHolder = new AdaptadorTarea.MyViewHolder(view,nListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorTarea.MyViewHolder myViewHolder, final int i) {
        myViewHolder.titulo.setText(nData.get(i).getTitulo());
        if(nData.get(i).getEstado() == 1)
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
            titulo = itemView.findViewById(R.id.item_titulo_tarea);
            linearLayout = itemView.findViewById(R.id.item_tareas);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
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
        if(tareaClass1.getEstado() == 0)
        {
            TareaClass tareaClass2 = new TareaClass(tareaClass1.getId(),tareaClass1.getTitulo(),1);
            nData.add(tareaClass2);
            //CambiarEstado(tareaClass1.getId(),2);

        }else {
            TareaClass tareaClass2 = new TareaClass(tareaClass1.getId(),tareaClass1.getTitulo(),0);
            nData.add(0,tareaClass2);
            //CambiarEstado(tareaClass1.getId(),1);
        }
        notifyDataSetChanged();
    }

    private boolean CambiarEstado(int idd,int nuevoestado){
        try {
            BDEvento obj = new BDEvento(nContext,"bdEvento",null,1);
            SQLiteDatabase bd = obj.getReadableDatabase();
            if(bd != null){
                bd.execSQL("update tarea set estado = " + nuevoestado + " where id = " + idd);
                return true;
            }
            bd.close();
            return false;
        }
        catch (Exception E){
            return false;
        }
    }
}
