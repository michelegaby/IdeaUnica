package com.michele.ideaunica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    //Componentes
    private TextView nombre;
    private TextView from;
    private ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        inicializarComponentes();
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.animsplashcreen);
        logo.startAnimation(animation);
        nombre.startAnimation(animation);
        from.startAnimation(animation);
        final Intent intent = new Intent(this,IdeaUnica.class);
        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(800);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }

    private void inicializarComponentes() {
        nombre=findViewById(R.id.nombre_splash);
        from=findViewById(R.id.from_splash);
        logo=findViewById(R.id.logo_splash);
    }
}
