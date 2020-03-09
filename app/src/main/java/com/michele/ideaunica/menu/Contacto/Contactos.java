package com.michele.ideaunica.menu.Contacto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.michele.ideaunica.menu.Entrevista.AdaptadorEntrevista;
import com.michele.ideaunica.menu.Entrevista.EntrevistaClass;
import com.michele.ideaunica.menu.Entrevista.Entrevistas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Contactos extends AppCompatActivity {

    private TextView celular;
    private TextView correo;
    private TextView asunto;
    private TextView mensaje;
    private Button enviar;

    private static  String URL="https://ideaunicabolivia.com/apps/correo.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setTitle("Contáctanos");
        Inicializar();
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(getApplicationContext(),celular.getText().toString()+
                        correo.getText().toString()+asunto.getText().toString()+mensaje.getText().toString(),Toast.LENGTH_LONG).show();*/
                GenerarDatos();
            }
        });
    }

    public void GenerarDatos(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("mensaje");
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                Toast.makeText(getApplicationContext(),object.get("nombre").toString(),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "No se puedo enviar el mensaje", Toast.LENGTH_LONG)
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
                params.put("celular",celular.getText().toString());
                params.put("email",correo.getText().toString());
                params.put("asunto",asunto.getText().toString());
                params.put("mesaje",mensaje.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        requestQueue.add(stringRequest);
    }
    private void Inicializar() {
        celular=findViewById(R.id.celular_contacto);
        correo=findViewById(R.id.email_contacto);
        asunto=findViewById(R.id.asunto_contacto);
        mensaje=findViewById(R.id.mesaje_contacto);
        enviar=findViewById(R.id.enviar_contacto);
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
