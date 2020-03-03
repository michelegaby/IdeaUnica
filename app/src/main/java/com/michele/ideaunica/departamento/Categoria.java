package com.michele.ideaunica.departamento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.michele.ideaunica.empresa.Empresas;
import com.michele.ideaunica.ui.notas.Nota;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Categoria extends AppCompatActivity {

    //Componentes
    private RecyclerView myrecyclerview;
    private ProgressBar progress;
    private EditText buscar;
    private TextView departamento;
    private SliderLayout sliderLayout;

    //Complementos
    AdaptadorCategoria adaptadorCategoria;
    private ArrayList<CategoriaClass> listCategoria = new ArrayList<>();
    private static  String URL="https://ideaunicabolivia.com/apps/categoria.php";
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setTitle("Categorias");
        InicializarComponentes();
        Bundle parametros = this.getIntent().getExtras();
        departamento.setText(parametros.getString("namedepartamento"));
        GenerarDatos(parametros.getString("departamento"));
        BuscarCategoria();
        myrecyclerview.setHasFixedSize(true);
        sliderLayout=findViewById(R.id.slider_publicidad_categoria);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setScrollTimeInSec(3);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void InicializarComponentes() {
        myrecyclerview=findViewById(R.id.Categoria_recyclerview);
        progress=findViewById(R.id.progress_categoria);
        buscar=findViewById(R.id.buscar_categorias);
        departamento=findViewById(R.id.departamento_categorias);
    }
    private void BuscarCategoria() {
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
                    ArrayList<CategoriaClass> listafiltrada= filter(listCategoria,s.toString());
                    adaptadorCategoria.setfilter(listafiltrada);

                }catch (Exception e)
                {
                    Toast.makeText(Categoria.this,e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private ArrayList<CategoriaClass> filter(ArrayList<CategoriaClass> categorias,String texto){
        ArrayList<CategoriaClass> listFiltada= new ArrayList<>();
        try{
            texto=texto.toLowerCase();

            for(CategoriaClass cat: categorias){
                String titulo = cat.getTitulo().toLowerCase();
                if(titulo.contains(texto)){
                    listFiltada.add(cat);
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return listFiltada;
    }
    public void GenerarDatos(final String dep){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("categoria");
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                CategoriaClass categoriaClass=
                                        new CategoriaClass(
                                                object.getInt("id"),
                                                object.getString("titulo").trim(),
                                                object.getString("cantidad")+" Resultado/s",
                                                object.getString("url"));
                                listCategoria.add(categoriaClass);
                            }
                            adaptadorCategoria= new AdaptadorCategoria(Categoria.this,listCategoria);
                            manager = new GridLayoutManager(Categoria.this,2);
                            myrecyclerview.setLayoutManager(manager);
                            adaptadorCategoria.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(getApplicationContext(), Empresas.class);
                                    Bundle parametros = new Bundle();
                                    parametros.putString("categoria",String.valueOf(listCategoria.get(myrecyclerview.getChildAdapterPosition(v)).getID()));
                                    parametros.putString("titulo",String.valueOf(listCategoria.get(myrecyclerview.getChildAdapterPosition(v)).getTitulo()));
                                    i.putExtras(parametros);
                                    startActivity(i);
                                }
                            });
                            progress.setVisibility(View.GONE);
                            myrecyclerview.setVisibility(View.VISIBLE);
                            myrecyclerview.setAdapter(adaptadorCategoria);

                            JSONArray jsonArray2 = jsonObject.getJSONArray("publicidad");

                            for (int i = 0; i < jsonArray2.length(); i++) {
                                DefaultSliderView sliderView= new DefaultSliderView(getApplicationContext());
                                JSONObject object = jsonArray2.getJSONObject(i);
                                sliderView.setImageUrl("https://sice.com.bo/ideaunica/"+object.getString("url"));
                                sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                                sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                                    @Override
                                    public void onSliderClick(SliderView sliderView) {
                                        Toast.makeText(getApplicationContext(),"ss",Toast.LENGTH_LONG).show();
                                    }
                                });
                                sliderLayout.addSliderView(sliderView);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Categoria.this,
                                    "Error", Toast.LENGTH_LONG)
                                    .show();
                            progress.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Categoria.this,
                                "Error  2"+error.getMessage(), Toast.LENGTH_LONG)
                                .show();
                        progress.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("departamento",dep);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
