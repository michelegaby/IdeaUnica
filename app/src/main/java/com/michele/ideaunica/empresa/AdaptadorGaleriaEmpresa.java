package com.michele.ideaunica.empresa;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.michele.ideaunica.R;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class AdaptadorGaleriaEmpresa extends RecyclerView.Adapter<AdaptadorGaleriaEmpresa.MyViewHolder>{

    Context nContext;
    ArrayList<GaleriaEmpresaClass> nData;

    public AdaptadorGaleriaEmpresa(Context nContext, ArrayList<GaleriaEmpresaClass> nData) {
        this.nContext = nContext;
        this.nData = nData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(nContext).inflate(R.layout.item_galeria_empresa,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Glide.with(nContext).load("https://ideaunicabolivia.com/"+nData.get(i).getUrl())
                .thumbnail(0.5f)
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(myViewHolder.img);
    }

    @Override
    public int getItemCount() {
        return nData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_photo_galeria_empresa);
        }
    }

    public void setfilter(ArrayList<GaleriaEmpresaClass> listaGaleria){
        nData = new ArrayList<>();
        nData.addAll(listaGaleria);
        notifyDataSetChanged();
    }

    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private AdaptadorGaleriaEmpresa.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final AdaptadorGaleriaEmpresa.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }
}
