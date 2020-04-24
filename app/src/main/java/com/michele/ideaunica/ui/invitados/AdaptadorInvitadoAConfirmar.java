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

public class AdaptadorInvitadoAConfirmar extends RecyclerView.Adapter<AdaptadorInvitadoAConfirmar.MyViewHolder> {

    Context nContext;
    ArrayList<InvitadosClass> nData;

    private OnItemClickListener nListener;

    public interface OnItemClickListener{
        void onElimanarClick(int position);
        void onModificarClick(int position);
        void onConfirmarClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        nListener = listener;
    }

    public AdaptadorInvitadoAConfirmar(Context nContext, ArrayList<InvitadosClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }

    @NonNull
    @Override
    public AdaptadorInvitadoAConfirmar.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        view = LayoutInflater.from(nContext).inflate(R.layout.item_invitado_a_confirmar,viewGroup,false);
        AdaptadorInvitadoAConfirmar.MyViewHolder viewHolder = new AdaptadorInvitadoAConfirmar.MyViewHolder(view,nListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorInvitadoAConfirmar.MyViewHolder myViewHolder, int i) {
        myViewHolder.nombre.setText(nData.get(i).getNombre());
        myViewHolder.adulto.setText(String.valueOf(nData.get(i).getAdultos())+" personas");
        myViewHolder.ninyo.setText(String.valueOf(nData.get(i).getNinyos())+" personas");
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
        private Button eliminar;
        private Button modificar;
        private Button confirmar;
        private Button mostrar;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            nombre = itemView.findViewById(R.id.item_nombre_invitado_a_confirmar);
            tipo = itemView.findViewById(R.id.item_tipo_invitado_a_confirmar);
            adulto = itemView.findViewById(R.id.item_adulto_invitado_a_confirmar);
            ninyo = itemView.findViewById(R.id.item_ninyo_invitado_a_confirmar);
            expandableView = itemView.findViewById(R.id.item_detalle_invitado_a_confirmar);
            celular = itemView.findViewById(R.id.item_celular_invitado_a_confirmar);

            eliminar = itemView.findViewById(R.id.item_eliminar_invitado_a_confirmar);
            modificar = itemView.findViewById(R.id.item_modificar_invitado_a_confirmar);
            confirmar = itemView.findViewById(R.id.item_confirmar_invitado_a_confirmar);

            mostrar = itemView.findViewById(R.id.item_mostrar_invitado_a_confirmar);

            //Funcionalidad al boton eliminar en el padre
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onElimanarClick(position);
                        }
                    }
                }
            });

            //Funcionalidad al boton modificar en el padre
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

            //Funcionalidad al boton confirmar en el padre
            confirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onConfirmarClick(position);
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
