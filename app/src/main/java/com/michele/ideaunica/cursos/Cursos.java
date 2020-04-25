package com.michele.ideaunica.cursos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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
import com.bumptech.glide.Glide;
import com.michele.ideaunica.MainActivity;
import com.michele.ideaunica.R;
import com.michele.ideaunica.cumple.AdaptadorCumpleanyos;
import com.michele.ideaunica.departamento.Categoria;
import com.michele.ideaunica.departamento.CategoriaClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cursos extends AppCompatActivity {

    //Componenes
    private EditText buscar;
    private ProgressBar progressBar;
    private RecyclerView rv_curso;
    private ImageView publicidad_img;

    //Complementos
    AdaptadorCurso adaptadorCurso;
    private ArrayList<CursosClass> listCurso = new ArrayList<>();
    private static  String URL = "https://ideaunicabolivia.com/apps/cursos.php";

    private static String cat;
    private static String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorblanco), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        Bundle parametros = this.getIntent().getExtras();

        cat = parametros.getString("titulo");

        ID = parametros.getString("id");

        getSupportActionBar().setTitle("Cursos de "+parametros.getString("titulo"));
        InicializarComponentes();
        GenerarDatos();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void InicializarComponentes() {
        buscar = findViewById(R.id.buscar_cursos);
        progressBar = findViewById(R.id.progress_cursos);
        rv_curso = findViewById(R.id.Cursos_recyclerview);
        publicidad_img = findViewById(R.id.img_publicidad_cursos);

    }

    private void GenerarDatos() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //JSON
                            JSONObject jsonObject = new JSONObject(response);

                            //Obtencion de la cursos de la categoria de curso seleccionada
                            JSONArray jsonArray = jsonObject.getJSONArray("cursos");
                            for (int i = 0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                CursosClass cursos = new CursosClass(
                                        object.getString("id"),
                                        object.getString("titulo"),
                                        object.getString("autor"),
                                        object.getString("fecha"),
                                        object.getString("horario"),
                                        object.getString("direccion"),
                                        object.getString("cantidad"),
                                        object.getString("costo"),
                                        object.getString("contenido"),
                                        "Tipo",
                                        object.getString("departamento"),
                                        object.getString("descripcion"),
                                        object.getString("numero"),
                                        object.getString("whatsapp"),
                                        object.getString("facebook"),
                                        object.getString("nombrefacebook"),
                                        object.getString("instagram"),
                                        object.getString("nombreinstagram"),
                                        object.getString("paginaweb"),
                                        object.getString("email"),
                                        object.getString("url"));
                                listCurso.add(cursos);
                            }

                            //La funcion de seleccionar se encuentra en el adapter

                            adaptadorCurso = new AdaptadorCurso(Cursos.this,listCurso);
                            rv_curso.setLayoutManager(new LinearLayoutManager(Cursos.this));
                            progressBar.setVisibility(View.GONE);
                            rv_curso.setVisibility(View.VISIBLE);
                            rv_curso.setAdapter(adaptadorCurso);

                            JSONArray jsonArray2 = jsonObject.getJSONArray("publicidad");
                            final JSONObject objectPublicidad = jsonArray2.getJSONObject(0);

                            if(!objectPublicidad.getString("photo").equals("null") && !objectPublicidad.getString("photo").isEmpty() ){
                                Glide.with(getApplicationContext()).load("https://ideaunicabolivia.com/"+objectPublicidad.getString("photo"))
                                        .placeholder(R.drawable.fondorosa)
                                        .error(R.drawable.cargando)
                                        .into(publicidad_img);
                                if(!objectPublicidad.getString("url").equals("null") && !objectPublicidad.getString("url").isEmpty() ) {
                                    publicidad_img.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            final String web;
                                            try {

                                                Uri uri = Uri.parse(objectPublicidad.getString("url"));
                                                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                                                startActivity(intent);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Toast.makeText(getApplicationContext(), "No disponible.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else {
                                    publicidad_img.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(getApplicationContext(), "No disponible.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error. Por favor intentelo mas tarde, gracias.", Toast.LENGTH_SHORT)
                                    .show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Error de conexión, por favor verifique el acceso a internet.", Toast.LENGTH_SHORT)
                                .show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                //Envias como atributo el cod de la categoria de curso que seleccinaste key = "cod"
                params.put("cod",ID);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        requestQueue.add(stringRequest);
    }
}