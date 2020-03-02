package com.michele.ideaunica.empresa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Empresas extends AppCompatActivity {


    //Componentes
    private RecyclerView myrecyclerview;
    private ProgressBar progress;
    private EditText buscar;
    //Complementos
    AdaptadorEmpresa adaptadorEmpresa;
    private ArrayList<EmpresaClass> listEmpresa = new ArrayList<>();
    private static  String URL="https://sice.com.bo/ideaunica/apps/empresas.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresas);
        InicializarComponentes();
        Bundle parametros = this.getIntent().getExtras();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setTitle(parametros.getString("titulo"));
        GenerarDatos(parametros.getString("categoria"));
        BuscarEmpresas();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void InicializarComponentes() {
        myrecyclerview=findViewById(R.id.Empresas_recyclerview);
        progress=findViewById(R.id.progress_empresas);
        buscar=findViewById(R.id.buscar_empresas);
    }
    private void BuscarEmpresas() {
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
                    ArrayList<EmpresaClass> listafiltrada= filter(listEmpresa,s.toString());
                    adaptadorEmpresa.setfilter(listafiltrada);

                }catch (Exception e)
                {
                    Toast.makeText(Empresas.this,e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private ArrayList<EmpresaClass> filter(ArrayList<EmpresaClass> empresass,String texto){
        ArrayList<EmpresaClass> listFiltada= new ArrayList<>();
        try{
            texto=texto.toLowerCase();

            for(EmpresaClass emp: empresass){
                String titulo = emp.getTitulo().toLowerCase();
                if(titulo.contains(texto)){
                    listFiltada.add(emp);
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return listFiltada;
    }
    public void GenerarDatos(final String cate){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("empresas");
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                EmpresaClass empresaClass =
                                        new EmpresaClass(
                                                object.getInt("id"),
                                                object.getString("titulo").trim(),
                                                object.getString("color").trim(),
                                                object.getString("nivel").trim(),
                                                object.getString("codigo").trim(),
                                                object.getString("categoria").trim(),
                                                object.getString("direccion").trim(),
                                                object.getString("descripcion").trim(),
                                                object.getString("numero").trim(),
                                                object.getString("whatsapp").trim(),
                                                object.getString("facebook").trim(),
                                                object.getString("nombrefacebook").trim(),
                                                object.getString("instagram").trim(),
                                                object.getString("nombreinstagram").trim(),
                                                object.getString("paginaweb").trim(),
                                                object.getString("email").trim(),
                                                object.getString("url"),
                                                object.getString("urlToolbar"));

                                listEmpresa.add(empresaClass);
                            }

                            adaptadorEmpresa= new AdaptadorEmpresa(Empresas.this,listEmpresa);
                            myrecyclerview.setLayoutManager(new LinearLayoutManager(Empresas.this));
                            progress.setVisibility(View.GONE);
                            myrecyclerview.setVisibility(View.VISIBLE);
                            myrecyclerview.setAdapter(adaptadorEmpresa);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Empresas.this,
                                    "Error", Toast.LENGTH_LONG)
                                    .show();
                            progress.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Empresas.this,
                                "Error  2"+error.getMessage(), Toast.LENGTH_LONG)
                                .show();
                        progress.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cat",cate);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

