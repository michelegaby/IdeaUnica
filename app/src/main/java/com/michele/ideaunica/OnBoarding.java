package com.michele.ideaunica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.michele.ideaunica.Boarding.AdapterBoarding;
import com.michele.ideaunica.Boarding.BoardingClass;

import java.util.ArrayList;
import java.util.List;

public class OnBoarding extends AppCompatActivity {

    private ViewPager mSliderViewPaper;
    private LinearLayout mDotsLayout;
    private Button mNextBtn;
    private Button mBackBtn;
    private TextView[] mDots;
    private int mCurrentPage;
    private List<BoardingClass> mList;
    private AdapterBoarding sliderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        mSliderViewPaper= findViewById(R.id.sliderViewPagerBoarding);
        mDotsLayout=findViewById(R.id.dotsLayout);
        mNextBtn=findViewById(R.id.nextBtn);
        mBackBtn=findViewById(R.id.prevBtn);
        mList = new ArrayList<>();
        mList.add(new BoardingClass("5 Opciones","Dispone de 5 opciones, la de inicio donde eligira el el departamento y categoria para ver los diferentes lugares donde podra disponer que desea.",R.drawable.pentanyas));
        mList.add(new BoardingClass("Inicio","Donde podras encontrar diferentes tipo de empresas, con dependencia de los departamentos",R.drawable.home));
        mList.add(new BoardingClass("Cursos","Cursos que se va presentar",R.drawable.img2));
        mList.add(new BoardingClass("Easy Payment","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit",R.drawable.pentanyas));
        mList.add(new BoardingClass("Easy Payment","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit",R.drawable.img2));

        //sliderAdapter=new SliderAdapter(this);
        sliderAdapter=new AdapterBoarding(this,mList);
        mSliderViewPaper.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        mSliderViewPaper.addOnPageChangeListener(viewLitener);
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mNextBtn.getText().equals("Finish"))
                {
                    Intent mainActivity = new Intent(getApplicationContext(),IdeaUnica.class);
                    startActivity(mainActivity);
                    savePrefsData();
                    finish();
                }else
                {
                    mSliderViewPaper.setCurrentItem(mCurrentPage+1);
                }
            }
        });
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSliderViewPaper.setCurrentItem(mCurrentPage-1);
            }
        });
        if (restorePrefData()) {
            Intent mainActivity = new Intent(getApplicationContext(),IdeaUnica.class );
            startActivity(mainActivity);
            finish();
        }
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();
    }

    public void addDotsIndicator(int position)
    {
        mDots = new TextView[mList.size()];
        mDotsLayout.removeAllViews();
        for (int i=0;i<mDots.length;i++){
            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorAccent));
            mDotsLayout.addView(mDots[i]);
        }
        if(mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    ViewPager.OnPageChangeListener viewLitener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurrentPage=position;
            if(position==0){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);
                mNextBtn.setText("NEXT");
                mBackBtn.setText("");
            }else if(position==mDots.length -1){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);
                mNextBtn.setText("Finish");
                mBackBtn.setText("Back");
            }else{
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);
                mNextBtn.setText("Next");
                mBackBtn.setText("Back");
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}

