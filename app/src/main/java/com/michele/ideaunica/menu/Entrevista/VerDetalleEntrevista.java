package com.michele.ideaunica.menu.Entrevista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.michele.ideaunica.R;

import java.util.ArrayList;

public class VerDetalleEntrevista extends AppCompatActivity {

    private RecyclerView rv_galeria;

    //Galeria
    AdaptadorGaleriaEntrevista adaptadorGaleriaEntrevista;
    private ArrayList<GaleriaEntrevistaClass> listGaleria = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_detalle_entrevista);
        try {
            Toolbar toolbar = findViewById(R.id.toolbarEntrevista);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
            drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorblanco), PorterDuff.Mode.SRC_IN));
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setTitle("");
            inicializarComponente();
            GenerarDatos();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void GenerarDatos() {
        listGaleria.add(new GaleriaEntrevistaClass(1, "A", "https://instagram.fsrz1-2.fna.fbcdn.net/v/t51.2885-15/sh0.08/e35/p640x640/84334237_1141950142810111_2477597141694371976_n.jpg?_nc_ht=instagram.fsrz1-2.fna.fbcdn.net&_nc_cat=107&_nc_ohc=aSti_n6LMw8AX-hz6N-&oh=71bc8bebaf215fac498fd09cf5c55d86&oe=5E89FAE5"));
        listGaleria.add(new GaleriaEntrevistaClass(1, "A", "https://instagram.fsrz1-1.fna.fbcdn.net/v/t51.2885-15/sh0.08/e35/s640x640/82632320_168875744401288_5025732237549078941_n.jpg?_nc_ht=instagram.fsrz1-1.fna.fbcdn.net&_nc_cat=104&_nc_ohc=hFgv4emN36sAX-EXLez&oh=de779c16c23c9968827a9d4b8db895ad&oe=5E8AB3BC"));
        adaptadorGaleriaEntrevista= new AdaptadorGaleriaEntrevista(this,listGaleria);
        rv_galeria.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_galeria.setAdapter(adaptadorGaleriaEntrevista);
    }

    private void inicializarComponente() {
        rv_galeria=findViewById(R.id.galeria_Entrevista_recyclerview);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
