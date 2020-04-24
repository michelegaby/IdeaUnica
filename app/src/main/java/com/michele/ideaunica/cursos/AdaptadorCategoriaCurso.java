package com.michele.ideaunica.cursos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.michele.ideaunica.R;
import java.util.ArrayList;

public class AdaptadorCategoriaCurso  extends RecyclerView.Adapter<AdaptadorCategoriaCurso.MyViewHolder> implements View.OnClickListener {

    private Context nContext;
    private ArrayList<CategoriaCursosClass> nData;
    private View.OnClickListener listener;

    public AdaptadorCategoriaCurso(Context nContext, ArrayList<CategoriaCursosClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }

    @Override
    public AdaptadorCategoriaCurso.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(nContext).inflate(R.layout.item_categoria_curso,viewGroup,false);
        AdaptadorCategoriaCurso.MyViewHolder viewHolder = new AdaptadorCategoriaCurso.MyViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorCategoriaCurso.MyViewHolder myViewHolder, int i) {
        myViewHolder.titulo.setText(nData.get(i).getTitulo());
        myViewHolder.contenido.setText(nData.get(i).getContenido());
        Glide.with(nContext).load("https://ideaunicabolivia.com/"+nData.get(i).getUrl())
                .placeholder(R.drawable.fondorosa)
                .error(R.drawable.cargando)
                .into(myViewHolder.img);
    }

    @Override
    public int getItemCount() {
        return nData.size();
    }

    //Funcionalidad se encuentra en el padre
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
        private TextView contenido;
        private ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_titulo_categoria_curso);
            contenido = itemView.findViewById(R.id.item_tipo_categoria_curso);
            img = itemView.findViewById(R.id.item_img_categoria_curso);
        }
    }

    public void setfilter(ArrayList<CategoriaCursosClass> listaCategoriaCursos){
        nData = new ArrayList<>();
        nData.addAll(listaCategoriaCursos);
        notifyDataSetChanged();
    }
}