package com.michele.ideaunica.Boarding;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.michele.ideaunica.R;

import java.util.List;

public class AdapterBoarding extends PagerAdapter {

    Context context;
    List<BoardingClass> mListSlider;

    public AdapterBoarding(Context context,List<BoardingClass> mListSlider) {
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
        View view=inflater.inflate(R.layout.boarding_layout,null);

        ImageView slideImageView=view.findViewById(R.id.boarding_image);
        TextView slideHeading=view.findViewById(R.id.boarding_heading);
        TextView slideDescription=view.findViewById(R.id.boarding_desc);
        slideHeading.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/toledo-serial-bold.ttf"));
        slideHeading.setText(mListSlider.get(position).getHeading());
        slideDescription.setText(mListSlider.get(position).getDesc());
        slideImageView.setImageResource(mListSlider.get(position).getImages());

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
