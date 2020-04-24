package com.michele.ideaunica.ui.invitados;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.michele.ideaunica.R;

import java.util.ArrayList;

public class AdaptadorInvitadoConfirmado extends RecyclerView.Adapter<AdaptadorInvitadoConfirmado.MyViewHolder>{

    Context nContext;
    ArrayList<InvitadosClass> nData;

    private OnItemClickListener nListener;

    public interface OnItemClickListener{
        void onModificarClick(int position);
        void onDesConfirmarClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        nListener = listener;
    }


    public AdaptadorInvitadoConfirmado(Context nContext, ArrayList<InvitadosClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }
    @Override
    public AdaptadorInvitadoConfirmado.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        view = LayoutInflater.from(nContext).inflate(R.layout.item_invitado_confirmado,viewGroup,false);
        AdaptadorInvitadoConfirmado.MyViewHolder viewHolder = new AdaptadorInvitadoConfirmado.MyViewHolder(view,nListener);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final AdaptadorInvitadoConfirmado.MyViewHolder myViewHolder, final int i) {
        myViewHolder.nombre.setText(nData.get(i).getNombre());
        myViewHolder.adulto.setText(nData.get(i).getAdultos() + " personas");
        myViewHolder.ninyo.setText(nData.get(i).getNinyos() + " personas");
        myViewHolder.celular.setText(String.valueOf(nData.get(i).getCelular()));
        myViewHolder.tipo.setText(nData.get(i).getTipo());
        myViewHolder.mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myViewHolder.expandableView.getVisibility() == View.GONE){
                    myViewHolder.expandableView.setVisibility(View.VISIBLE);
                    myViewHolder.mostrar.setBackgroundResource(R.drawable.keyboard_arrow_up);
                }
                else{
                    myViewHolder.expandableView.setVisibility(View.GONE);
                    myViewHolder.mostrar.setBackgroundResource(R.drawable.keyboard_arrow_down);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return nData.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{


        private TextView nombre;
        private TextView adulto;
        private TextView ninyo;
        private TextView celular;
        private TextView tipo;

        private LinearLayout expandableView;
        private Button modificar;
        private Button desconfirmar;
        private Button mostrar;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            nombre = itemView.findViewById(R.id.item_nombre_invitado_confirmado);
            tipo = itemView.findViewById(R.id.item_tipo_invitado_confirmado);
            adulto = itemView.findViewById(R.id.item_adulto_invitado_confirmado);
            ninyo = itemView.findViewById(R.id.item_ninyo_invitado_confirmado);
            expandableView = itemView.findViewById(R.id.item_detalle_invitado_confirmado);
            celular = itemView.findViewById(R.id.item_celular_invitado_confirmado);
            modificar = itemView.findViewById(R.id.item_modificar_invitado_confirmado);
            desconfirmar = itemView.findViewById(R.id.item_desconfirmar_invitado_confirmado);
            mostrar = itemView.findViewById(R.id.item_mostrar_invitado_confirmado);

            //Funcionalidad de modificar en el padre
            modificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onModificarClick(position);
                        }
                    }
                }
            });

            //Funcionalidad de desconfirmar en el padre
            desconfirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDesConfirmarClick(position);
                        }
                    }
                }
            });
        }
    }

    public void setfilter(ArrayList<InvitadosClass> listaInvitado){
        nData = new ArrayList<>();
        nData.addAll(listaInvitado);
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        nData.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(InvitadosClass invitado,int position){
        nData.add(position, invitado);
        notifyItemInserted(position);
    }
}
