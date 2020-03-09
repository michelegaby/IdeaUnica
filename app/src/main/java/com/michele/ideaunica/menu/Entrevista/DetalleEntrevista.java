package com.michele.ideaunica.menu.Entrevista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.michele.ideaunica.R;
import com.michele.ideaunica.menu.Revista.DetalleRevista;
import com.michele.ideaunica.menu.evento.AdaptadorGaleriaEvento;
import com.michele.ideaunica.menu.evento.GaleriaEventoClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetalleEntrevista extends AppCompatActivity {

    private Button ver;
    private TextView nombre;
    private TextView biografia;
    private ImageView img;

    private static int ID;
    private static String urlimg;
    private static  String URL="https://ideaunicabolivia.com/apps/biografia.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_entrevista);
        inicializarComponentes();
        Bundle parametros = this.getIntent().getExtras();
        ID=parametros.getInt("id");

        GenerarDatos();
        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), VerDetalleEntrevista.class);
                Bundle parametros = new Bundle();
                parametros.putInt("id",ID);
                parametros.putString("nombre",nombre.getText().toString());
                parametros.putString("img",urlimg);
                intent.putExtras(parametros);
                startActivity(intent);
            }
        });
    }

    private void inicializarComponentes(){
        ver=findViewById(R.id.abajo_detalle_evento);
        nombre=findViewById(R.id.nombre_detalleEntrevista);
        biografia=findViewById(R.id.biografia_detalleEntrevista);
        img=findViewById(R.id.img_fondo_detalleentrevista);
    }


    public void GenerarDatos(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("biografia");

                            JSONObject object = jsonArray.getJSONObject(0);
                            Glide.with(getApplicationContext())
                                    .load("https://ideaunicabolivia.com/"+object.getString("url"))
                                    .placeholder(R.drawable.cargando)
                                    .error(R.drawable.fondorosa)
                                    .into(img);
                            urlimg=object.getString("url");
                            nombre.setText(object.getString("nombre"));
                            biografia.setText(object.getString("biografia"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "No existe Biografia", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Error de conexion"+error.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",String.valueOf(ID));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
