package com.michele.ideaunica.empresa.Publicidad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.michele.ideaunica.R;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class AdapterPublicidad extends PagerAdapter {

    Context context;
    List<PublicidadClass> mListSlider;

    public AdapterPublicidad(Context context,List<PublicidadClass> mListSlider) {
        this.context = context;
        this.mListSlider=mListSlider;
    }
    @Override
    public int getCount() {
        return mListSlider.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.publicidad_layout,null);
        ImageView slideImageView=view.findViewById(R.id.publicidad_image);
        Glide.with(context).load("https://ideaunicabolivia.com/"+mListSlider.get(position).getImages())
                .thumbnail(0.5f)
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(slideImageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
