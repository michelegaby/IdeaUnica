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
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorblanco), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setTitle("Revista");
        InicializarComponentes();
        GenerarDatos();
    }

    private void InicializarComponentes() {
        myrecyclerview=findViewById(R.id.Revista_recyclerview);
        progress=findViewById(R.id.progress_revista);
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

    public void GenerarDatos(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //JSON
                            JSONObject jsonObject = new JSONObject(response);

                            //Obtener datos del evento
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
}
