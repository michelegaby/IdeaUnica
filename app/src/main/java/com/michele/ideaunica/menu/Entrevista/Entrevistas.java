package com.michele.ideaunica.menu.Entrevista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.michele.ideaunica.R;

import java.util.ArrayList;

public class Entrevistas extends AppCompatActivity {

    //Componentes
    private TextView titulo;
    private RecyclerView myrecyclerview;

    //Complementos
    AdaptadorEntrevista adaptadorEntrevista;
    private ArrayList<EntrevistaClass> listEntrevisto = new ArrayList<>();
    private static  String URL="https://sice.com.bo/ideaunica/apps/categoria.php";
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrevistas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setTitle("Entrevistas");
        InicializarComponenetes();
        GenerarDatos();
        titulo.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/toledo-serial-bold.ttf"));
    }

    private void GenerarDatos() {
        listEntrevisto.add(new EntrevistaClass(
                1,
                "In the Bog: Iris Low",
                "https://www.okchicas.com/wp-content/uploads/2015/08/artista-combina-rostros-de-famosos-19.jpg"));
        listEntrevisto.add(new EntrevistaClass(1, "Roni Vieira","https://scontent.fsrz1-2.fna.fbcdn.net/v/t1.0-9/14702430_993853764075450_7981139326619015024_n.png?_nc_cat=105&_nc_sid=a61e81&_nc_ohc=MbgxELUXp1YAX-4T-6z&_nc_ht=scontent.fsrz1-2.fna&oh=1c65856c569981f570fe60f0880e69c9&oe=5EFB10F0"));
        listEntrevisto.add(new EntrevistaClass(1, "Roni Vieira","https://scontent.fsrz1-2.fna.fbcdn.net/v/t31.0-8/13002564_773869786081734_340677104602916805_o.jpg?_nc_cat=107&_nc_sid=8024bb&_nc_ohc=oLJTQI8nIrQAX8Edl2o&_nc_ht=scontent.fsrz1-2.fna&oh=a9eae1ada5d8292ef0a22ede44296782&oe=5EF59677"));
        listEntrevisto.add(new EntrevistaClass(1, "aaaaa","https://akns-images.eonline.com/eol_images/Entire_Site/20181027/rs_600x600-181127075643-600.adam-levine-cover.112718.jpg?fit=around|700:700&crop=700:700;center,top&output-quality=90"));
        listEntrevisto.add(new EntrevistaClass(1, "aaaaa","https://es.web.img3.acsta.net/pictures/19/11/12/22/54/0812791.jpg"));
        listEntrevisto.add(new EntrevistaClass(1, "aaaaa","https://media.metrolatam.com/2018/03/16/kendalljenner1-01c4b2ea59e527c586b8cd367d8fde74-900x600.jpg"));

        listEntrevisto.add(new EntrevistaClass(1,"In the Bog: Iris Low","https://www.okchicas.com/wp-content/uploads/2015/08/artista-combina-rostros-de-famosos-19.jpg"));
        listEntrevisto.add(new EntrevistaClass(1, "Dolore eu fugiot","https://www.starzip.de/wp-content/uploads/2019/05/Kylie-Jenner-verlosung-17.800-Euro-696x510.jpg"));
        listEntrevisto.add(new EntrevistaClass(1, "aaaaa","https://smoda.elpais.com/wp-content/uploads/images/201421/ryan_gosling_5019.jpg"));

        listEntrevisto.add(new EntrevistaClass(1, "aaaaa","https://akns-images.eonline.com/eol_images/Entire_Site/20181027/rs_600x600-181127075643-600.adam-levine-cover.112718.jpg?fit=around|700:700&crop=700:700;center,top&output-quality=90"));
        listEntrevisto.add(new EntrevistaClass(1, "aaaaa","https://es.web.img3.acsta.net/pictures/19/11/12/22/54/0812791.jpg"));
        listEntrevisto.add(new EntrevistaClass(1, "aaaaa","https://media.metrolatam.com/2018/03/16/kendalljenner1-01c4b2ea59e527c586b8cd367d8fde74-900x600.jpg"));

        adaptadorEntrevista= new AdaptadorEntrevista(Entrevistas.this,listEntrevisto);
        manager = new GridLayoutManager(Entrevistas.this,2);
        myrecyclerview.setLayoutManager(manager);
        myrecyclerview.setVisibility(View.VISIBLE);
        myrecyclerview.setAdapter(adaptadorEntrevista);

    }

    private void InicializarComponenetes() {
        myrecyclerview=findViewById(R.id.Entrevista_recyclerview);
        titulo=findViewById(R.id.titulo_entrevistas);
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
