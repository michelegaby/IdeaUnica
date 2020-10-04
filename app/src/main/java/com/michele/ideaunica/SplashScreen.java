package com.michele.ideaunica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.michele.ideaunica.sharedPreferences.SessionManager;

public class SplashScreen extends AppCompatActivity {

    private TextView nombre;
    private TextView from;
    private ImageView logo;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        inicializarComponentes();

        sessionManager = new SessionManager(this);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animsplashcreen);
        logo.startAnimation(animation);
        nombre.startAnimation(animation);
        from.startAnimation(animation);

        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(800);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    if(sessionManager.isLogin()){
                        final Intent intent = new Intent(getApplicationContext(),IdeaUnica.class);
                        startActivity(intent);
                        finish();

                    }else{
                        final Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        };

        timer.start();
    }

    private void inicializarComponentes() {
        nombre = findViewById(R.id.nombre_splash);
        from = findViewById(R.id.from_splash);
        logo = findViewById(R.id.logo_splash);
    }
}
