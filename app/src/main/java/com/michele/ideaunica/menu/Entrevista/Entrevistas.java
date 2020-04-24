package com.michele.ideaunica.menu.Entrevista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
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
import com.michele.ideaunica.menu.evento.AdaptadorEvento;
import com.michele.ideaunica.menu.evento.Evento;
import com.michele.ideaunica.menu.evento.EventoClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Entrevistas extends AppCompatActivity {

    //Componentes
    private TextView titulo;
    private RecyclerView myrecyclerview;
    private ProgressBar progress;

    //Complementos
    AdaptadorEntrevista adaptadorEntrevista;
    private ArrayList<EntrevistaClass> listEntrevisto = new ArrayList<>();
    private static  String URL = "https://ideaunicabolivia.com/apps/entrevista.php";
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
        titulo.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/toledo-serial-bold.ttf"));
        GenerarDatos();
    }

    public void GenerarDatos(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //JSON
                            JSONObject jsonObject = new JSONObject(response);

                            //Obtencion de las entrevistas
                            JSONArray jsonArray = jsonObject.getJSONArray("entrevista");
                            for (int i = 0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                listEntrevisto.add(new EntrevistaClass(
                                        object.getInt("id"),
                                        object.getString("titulo"),
                                        object.getString("lugar"),
                                        object.getString("url")));

                            }

                            //Funcionalidad en adapter

                            adaptadorEntrevista = new AdaptadorEntrevista(Entrevistas.this,listEntrevisto);
                            manager = new GridLayoutManager(Entrevistas.this,2);
                            myrecyclerview.setLayoutManager(manager);
                            myrecyclerview.setVisibility(View.VISIBLE);
                            myrecyclerview.setAdapter(adaptadorEntrevista);
                            progress.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error. Por favor intentelo masy tarde, gracias.", Toast.LENGTH_LONG)
                                    .show();
                            progress.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Error de conexion, por favor verifique el acceso a internet.", Toast.LENGTH_LONG)
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

    private void InicializarComponenetes() {
        myrecyclerview = findViewById(R.id.Entrevista_recyclerview);
        titulo = findViewById(R.id.titulo_entrevistas);
        progress = findViewById(R.id.progress_entrevistas);
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
