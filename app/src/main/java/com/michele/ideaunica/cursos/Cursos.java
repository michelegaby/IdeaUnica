package com.michele.ideaunica.cursos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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

    //Complementos
    AdaptadorCurso adaptadorCurso;
    private ArrayList<CursosClass> listCurso = new ArrayList<>();
    private static  String URL="https://ideaunicabolivia.com/apps/cursos.php";

    private static String cat;
    private static String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        Bundle parametros = this.getIntent().getExtras();
        cat=parametros.getString("titulo");
        ID=parametros.getString("id");
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
        buscar=findViewById(R.id.buscar_cursos);
        progressBar=findViewById(R.id.progress_cursos);
        rv_curso=findViewById(R.id.Cursos_recyclerview);
    }

    private void GenerarDatos() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("cursos");
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                CursosClass cursos= new CursosClass(
                                        object.getString("id"),
                                        object.getString("titulo"),
                                        object.getString("autor"),
                                        object.getString("fecha"),
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
                                        object.getString("paginaweb"),
                                        object.getString("url"));
                                listCurso.add(cursos);
                            }
                            adaptadorCurso= new AdaptadorCurso(Cursos.this,listCurso);
                            rv_curso.setLayoutManager(new LinearLayoutManager(Cursos.this));
                            progressBar.setVisibility(View.GONE);
                            rv_curso.setVisibility(View.VISIBLE);
                            rv_curso.setAdapter(adaptadorCurso);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error", Toast.LENGTH_LONG)
                                    .show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Error:"+error.getMessage(), Toast.LENGTH_LONG)
                                .show();
                        progressBar.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cod",ID);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        requestQueue.add(stringRequest);
    }
    private void Buscar() {
        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                try{
                    ArrayList<CursosClass> listafiltrada= filter(listCurso,s.toString());
                    adaptadorCurso.setfilter(listafiltrada);
                }catch (Exception e)
                {
                    Toast.makeText(Cursos.this,e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private ArrayList<CursosClass> filter(ArrayList<CursosClass> cursosos,String texto){
        ArrayList<CursosClass> listFiltada= new ArrayList<>();
        try{
            texto=texto.toLowerCase();
            for(CursosClass cur: cursosos){
                String titulo = cur.getTitulo().toLowerCase();
                String contenido = cur.getDescripcion().toLowerCase();
                if(titulo.contains(texto)||contenido.contains(texto)){
                    listFiltada.add(cur);
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return listFiltada;
    }
}