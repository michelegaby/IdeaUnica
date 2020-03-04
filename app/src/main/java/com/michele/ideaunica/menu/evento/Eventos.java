package com.michele.ideaunica.menu.evento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.michele.ideaunica.R;

import java.util.ArrayList;

public class Eventos extends AppCompatActivity {

    private RecyclerView recyclerView;
    //Complementos
    AdaptadorEvento adaptadorEvento;
    private ArrayList<EventoClass> listEvento = new ArrayList<>();
    private static  String URL="https://sice.com.bo/ideaunica/apps/departamento.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_eventos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setTitle("Eventos");

        InicializarComponenetes();
        GenerarDatos();
    }

    private void InicializarComponenetes() {
        recyclerView=findViewById(R.id.Evento_recyclerview);
    }

    private void GenerarDatos() {
        listEvento.add(new EventoClass(1,"https://sice.com.bo/ideaunica/img/empresa/bahiaesmeralda.png","Feria de dia de las madre","2019-05-20","2019-05-29","SSao Paublo Expo. Estudiante no se que mas poner no se que mas ponder 2.0"));
        listEvento.add(new EventoClass(1,"https://sice.com.bo/ideaunica/img/empresa/mamalena.jpeg","Feria de expocruz","2019-05-01","2019-05-29","SaoPaubo Paublo Expo. Estudiante no se que mas poner no se que mas ponder 2.0"));
        listEvento.add(new EventoClass(1,"https://sice.com.bo/ideaunica/img/empresa/toyota.jpg","Feria de expocruz","2019-04-02","2019-05-22","SaoP Paublo Expo. Estudiante no se que mas poner no se que mas ponder 2.0"));
        listEvento.add(new EventoClass(1,"https://sice.com.bo/ideaunica/img/empresa/bahiaesmeralda.png","Feria de dia de las madre","2019-05-05","2019-05-25","Sao ASPaublo Expo. Estudiante no se que mas poner no se que mas ponder 2.0"));
        listEvento.add(new EventoClass(1,"https://sice.com.bo/ideaunica/img/empresa/mamalena.jpeg","Feria de dia de las madre","2019-05-20","2019-05-21","Sao dsPaublo Expo. Estudiante no se que mas poner no se que mas ponder 2.0"));
        listEvento.add(new EventoClass(1,"https://sice.com.bo/ideaunica/img/empresa/saunapiscinaamazonas.png","Feria de expocruz","2019-02-20","2019-05-22","Saofdd Paublo Expo. Estudiante no se que mas poner no se que mas ponder 2.0"));
        listEvento.add(new EventoClass(1,"https://sice.com.bo/ideaunica/img/empresa/mamalena.jpeg","Feria de expocruz","2019-05-01","2019-05-29","SaoPaubo Paublo Expo. Estudiante no se que mas poner no se que mas ponder 2.0"));
        listEvento.add(new EventoClass(1,"https://sice.com.bo/ideaunica/img/empresa/toyota.jpg","Feria de expocruz","2019-04-02","2019-05-22","SaoP Paublo Expo. Estudiante no se que mas poner no se que mas ponder 2.0"));
        listEvento.add(new EventoClass(1,"https://sice.com.bo/ideaunica/img/empresa/bahiaesmeralda.png","Feria de dia de las madre","2019-05-05","2019-05-25","Sao ASPaublo Expo. Estudiante no se que mas poner no se que mas ponder 2.0"));
        listEvento.add(new EventoClass(1,"https://sice.com.bo/ideaunica/img/empresa/mamalena.jpeg","Feria de dia de las madre","2019-05-20","2019-05-21","Sao dsPaublo Expo. Estudiante no se que mas poner no se que mas ponder 2.0"));
        listEvento.add(new EventoClass(1,"https://sice.com.bo/ideaunica/img/empresa/saunapiscinaamazonas.png","Feria de expocruz","2019-02-20","2019-05-22","Saofdd Paublo Expo. Estudiante no se que mas poner no se que mas ponder 2.0"));
        adaptadorEvento= new AdaptadorEvento(getApplicationContext(),listEvento);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adaptadorEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Evento.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adaptadorEvento);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
