package com.michele.ideaunica.cursos;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.michele.ideaunica.R;
import com.michele.ideaunica.empresa.Empresa;
import com.michele.ideaunica.empresa.EmpresaClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorCurso extends RecyclerView.Adapter<AdaptadorCurso.MyViewHolder>{

    Context nContext;
    ArrayList<CursosClass> nData;

    public AdaptadorCurso(Context nContext, ArrayList<CursosClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }

    @Override
    public AdaptadorCurso.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(nContext).inflate(R.layout.item_curso,viewGroup,false);
        AdaptadorCurso.MyViewHolder viewHolder = new AdaptadorCurso.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorCurso.MyViewHolder myViewHolder, final int position) {
        myViewHolder.titulo.setText(nData.get(position).getTitulo());

        Glide.with(nContext).load("https://ideaunicabolivia.com/"+nData.get(position).getUrl())
                .placeholder(R.drawable.fondorosa)
                .error(R.drawable.cargando)
                .into(myViewHolder.imagen);

        myViewHolder.precio.setText(nData.get(position).getCosto());
        myViewHolder.contenido.setText(nData.get(position).getContenido());
        myViewHolder.departamento.setText(nData.get(position).getDepartamento());

        try {
            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formateador = new SimpleDateFormat("d MMMM, yyyy");
            Date date = parseador.parse(nData.get(position).getFecha());
            myViewHolder.fecha.setText(formateador.format(date));

        } catch (ParseException e) {
            Toast.makeText(nContext,e.getMessage(),Toast.LENGTH_LONG).show();
        };

        //Funcion al seleccionar direcciona a detalle del curso
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nContext, Curso.class);
                Bundle parmetros = new Bundle();
                parmetros.putString("ID",nData.get(position).getId());
                parmetros.putString("fecha",nData.get(position).getFecha());
                parmetros.putString("horario",nData.get(position).getHorario());
                parmetros.putString("direccion",nData.get(position).getDireccion());
                parmetros.putString("autor",nData.get(position).getAutor());
                parmetros.putString("whatsapp",nData.get(position).getWhatsapp());
                parmetros.putString("facebook",nData.get(position).getFacebook());
                parmetros.putString("nombrefacebook",nData.get(position).getNombrefacebook());
                parmetros.putString("instagram",nData.get(position).getInstagram());
                parmetros.putString("nombreinstagram",nData.get(position).getNombreinstagram());
                parmetros.putString("paginaweb",nData.get(position).getPaginaweb());
                parmetros.putString("email",nData.get(position).getEmail());
                parmetros.putString("departamento",nData.get(position).getDepartamento());
                parmetros.putString("titulo",nData.get(position).getTitulo());
                parmetros.putString("descripcion",nData.get(position).getDescripcion());
                parmetros.putString("telefono",nData.get(position).getTelefono());
                parmetros.putString("categoria","A");
                parmetros.putString("costo",nData.get(position).getCosto());
                parmetros.putString("duracion",nData.get(position).getCantidad());
                parmetros.putString("img","https://sice.com.bo/ideaunica/"+nData.get(position).getUrl());
                intent.putExtras(parmetros);
                nContext.startActivity(intent);
                ((Activity) nContext).overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }
    @Override
    public int getItemCount() {
        return nData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView titulo;
        private ImageView imagen;
        private TextView contenido;
        private TextView departamento;
        private TextView precio;
        private TextView fecha;
        private CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_titulo_curso);
            imagen = itemView.findViewById(R.id.item_img_curso);
            cardView = itemView.findViewById(R.id.item_cardview_curso);
            contenido = itemView.findViewById(R.id.item_contenido_curso);
            departamento = itemView.findViewById(R.id.item_departamento_curso);
            precio = itemView.findViewById(R.id.item_precio_curso);
            fecha = itemView.findViewById(R.id.item_fecha_curso);
        }
    }

    public void setfilter(ArrayList<CursosClass> listaCursos){
        nData = new ArrayList<>();
        nData.addAll(listaCursos);
        notifyDataSetChanged();
    }
}
