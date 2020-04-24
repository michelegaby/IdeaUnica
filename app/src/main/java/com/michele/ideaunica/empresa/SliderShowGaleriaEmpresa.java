package com.michele.ideaunica.empresa;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.michele.ideaunica.R;

import java.util.ArrayList;

public class SliderShowGaleriaEmpresa extends DialogFragment {

    private String TAG = SliderShowGaleriaEmpresa.class.getSimpleName();
    private ArrayList<GaleriaEmpresaClass> galeriaEmpresaClasses;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView lblCount, lblTitule;
    private int selectedPosition = 0;

    public static SliderShowGaleriaEmpresa newInstance(){
        SliderShowGaleriaEmpresa f = new SliderShowGaleriaEmpresa();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_galeria,container,false);
        viewPager = v.findViewById(R.id.fragment_viewpaper);
        lblCount = v.findViewById(R.id.lbl_count);
        lblTitule = v.findViewById(R.id.titulo_viewpaper);
        galeriaEmpresaClasses = (ArrayList<GaleriaEmpresaClass>) getArguments().getSerializable("images");
        selectedPosition = getArguments().getInt("position");
        Log.e(TAG, "position: " + selectedPosition);
        Log.e(TAG, "images size: " + galeriaEmpresaClasses.size());
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        setCurrentItem(selectedPosition);
        return v;
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    //	page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    private void displayMetaInfo(int position) {
        lblCount.setText((position + 1) + " of " + galeriaEmpresaClasses.size());
        GaleriaEmpresaClass image = galeriaEmpresaClasses.get(position);
        lblTitule.setText(image.getTitulo());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        public MyViewPagerAdapter(){
        }
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.image_fullscreen, container, false);
            ImageView imageViewPreview = view.findViewById(R.id.item_image_fullscreen);
            GaleriaEmpresaClass image = galeriaEmpresaClasses.get(position);
            Glide.with(getActivity()).load("https://ideaunicabolivia.com/"+image.getUrl())
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewPreview);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return galeriaEmpresaClasses.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}