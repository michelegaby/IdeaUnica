package com.michele.ideaunica.menu.Revista;

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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.michele.ideaunica.R;
import com.michele.ideaunica.menu.evento.AdaptadorEvento;
import com.michele.ideaunica.menu.evento.Evento;
import com.michele.ideaunica.menu.evento.EventoClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Revistas extends AppCompatActivity {

    //Componentes
    private RecyclerView myrecyclerview;
    private ProgressBar progress;
    //Complementos
    AdaptadorRevista adaptadorRevista;
    private ArrayList<RevistaClass> listRevista = new ArrayList<>();
    private static  String URL="https://ideaunicabolivia.com/apps/revista.php";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revistas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setTitle("Revista");
        InicializarComponentes();
        GenerarDatos1();
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

    private void GenerarDatos() {
        listRevista.add(new RevistaClass(0,"Titulo 1","2020-02-02",
                "https://s3-us-west-2.amazonaws.com/denomades/blog/wp-content/uploads/2017/08/03121455/orurorororo.jpg","El Carnaval de Oruro es un evento folclórico y cultural, así como la máxima representación de los carnavales en Bolivia, “Obra Maestra del Patrimonio Oral e Intangible de la Humanidad” (Unesco). Una de las principales contribuciones en materia de composición es la del Dr. Ricardo Yugar Flores Ph.D./Post-Doctor cuyo tema Diablada hay solo una y es de Oruro fue interpretada por 5000 músicos en Oruro y es difundida por el Grupo Hermanos Yugar en el mundo a través de sus conciertos internacionales. A lo largo del carnaval participan más de 48 conjuntos folklóricos que son distribuidos en 18 especialidades de danzas que reúnen de distintas partes de Bolivia y que realizan su peregrinación hacia el Santuario del Socavón cada sábado de carnaval en la tradicional “Entrada”. Esta celebración, por la gran popularidad que alcanzó en los últimos años y debido a su gran manifestación cultural y atracción turística, pasó a volverse uno de los carnavales más importantes conjuntamente con el de Río de Janeiro, Brasil y otros carnavales en el mundo. Alrededor de 400 mil personas visitan anualmente el carnaval, generando un movimiento económico de al menos 125 millones de bolivianos.1"));
        listRevista.add(new RevistaClass(0,"Titulo 3","2020-02-02",
                "https://media-cdn.tripadvisor.com/media/photo-s/0e/d4/24/81/club-oasis-piscina-atemperada.jpg","La palabra piscina viene del latín piscis \"pez\" y originalmente se utilizaba para designar pozos para peces de agua dulce o salada. También se utilizó para designar los depósitos de agua conectados a los acueductos. Los primeros cristianos utilizaron la palabra piscina para designar la pila bautismal. Efectivamente, antes de la invención de las depuradoras, en las albercas, de baño o decorativas, al aire libre, se utilizaban peces para la limpieza del agua, puesto que se comían las larvas de insectos, y de ahí viene el nombre."));
        listRevista.add(new RevistaClass(0,"Titulo 2","2020-02-02","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTRD9V07aFSu4-FLkrlW3sX9WKclrdhjaXbP9xkzr0XJ5dZ6Wjb","Las cataratas del Niágara (Niagara Falls en inglés, Chutes du Niagara en francés) son un grupo de cascadas situadas en el río Niágara, en la zona noreste de América del Norte, en Canadá. Situadas a unos 236 metros sobre el nivel del mar, su caída es de aproximadamente 64 metros."));
        progress.setVisibility(View.GONE);

        adaptadorRevista= new AdaptadorRevista(this,listRevista);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myrecyclerview.setAdapter(adaptadorRevista);
    }


    public void GenerarDatos1(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("evento");
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);

                                listRevista.add(new RevistaClass(
                                        object.getInt("id"),
                                        object.getString("titulo"),
                                        object.getString("fecha"),
                                        object.getString("url"),
                                        object.getString("descripcion_inicio"),
                                        object.getString("autor"),
                                        object.getString("descripcion_final"),
                                        object.getString("facebook"),
                                        object.getString("whatsapp"),
                                        object.getString("instagram"),
                                        object.getString("email")));

                            }
                            progress.setVisibility(View.GONE);
                            adaptadorRevista= new AdaptadorRevista(Revistas.this,listRevista);
                            myrecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            myrecyclerview.setAdapter(adaptadorRevista);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "No existe ningun evento", Toast.LENGTH_LONG)
                                    .show();
                            progress.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Error de conexion"+error.getMessage(), Toast.LENGTH_LONG)
                                .show();
                        progress.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void InicializarComponentes() {
        myrecyclerview=findViewById(R.id.Revista_recyclerview);
        progress=findViewById(R.id.progress_revista);
    }


}
