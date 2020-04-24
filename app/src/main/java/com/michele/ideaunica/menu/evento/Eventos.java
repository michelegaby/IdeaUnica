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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.michele.ideaunica.R;
import com.michele.ideaunica.departamento.AdaptadorDepartamento;
import com.michele.ideaunica.departamento.Categoria;
import com.michele.ideaunica.departamento.DepartamentoClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Eventos extends AppCompatActivity {

    //Componentes
    private RecyclerView recyclerView;
    private ProgressBar progress;
    private ImageView publicidad_img;

    //Complementos
    AdaptadorEvento adaptadorEvento;
    private ArrayList<EventoClass> listEvento = new ArrayList<>();
    private static  String URL = "https://ideaunicabolivia.com/apps/evento.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_eventos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorblanco), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setTitle("Eventos");

        InicializarComponenetes();
        GenerarDatos();
    }

    private void InicializarComponenetes() {
        recyclerView = findViewById(R.id.Evento_recyclerview);
        progress = findViewById(R.id.progress_eventos);
        publicidad_img = findViewById(R.id.img_publicidad_eventos);
    }

    public void GenerarDatos(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //JSON
                            JSONObject jsonObject = new JSONObject(response);

                            //Obtencion de datos de eventos
                            JSONArray jsonArray = jsonObject.getJSONArray("evento");
                            for (int i = 0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                EventoClass departamento = new EventoClass(
                                        object.getInt("id"),
                                        object.getString("url").trim(),
                                        object.getString("titulo").trim(),
                                        object.getString("fecha_inicio"),
                                        object.getString("fecha_final"),
                                        object.getString("descripcion").trim());
                                listEvento.add(departamento);
                            }

                            adaptadorEvento = new AdaptadorEvento(getApplicationContext(),listEvento);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                            //Funcionalidad al seleccionar un evento
                            adaptadorEvento.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(getApplicationContext(),Evento.class);
                                    Bundle parametros = new Bundle();
                                    parametros.putString("ID",
                                            String.valueOf(listEvento.get(recyclerView.getChildAdapterPosition(v)).getID()));
                                    parametros.putString("titulo",
                                            String.valueOf(listEvento.get(recyclerView.getChildAdapterPosition(v)).getTitulo()));
                                    parametros.putString("fecha_inicio",
                                            String.valueOf(listEvento.get(recyclerView.getChildAdapterPosition(v)).getFecha()));
                                    parametros.putString("fecha_final",
                                            String.valueOf(listEvento.get(recyclerView.getChildAdapterPosition(v)).getFecha_final()));
                                    parametros.putString("descripcion",
                                            String.valueOf(listEvento.get(recyclerView.getChildAdapterPosition(v)).getDescripcion()));
                                    i.putExtras(parametros);
                                    startActivity(i);
                                }
                            });

                            progress.setVisibility(View.GONE);
                            recyclerView.setAdapter(adaptadorEvento);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error. Por favor intentelo mas tarde, gracias.", Toast.LENGTH_SHORT)
                                    .show();
                            progress.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Error de conexión, por favor verifique el acceso a internet.", Toast.LENGTH_SHORT)
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
