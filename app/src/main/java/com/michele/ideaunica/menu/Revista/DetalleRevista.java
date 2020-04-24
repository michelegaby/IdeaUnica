package com.michele.ideaunica.menu.Revista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import com.michele.ideaunica.menu.evento.AdaptadorGaleriaEvento;
import com.michele.ideaunica.menu.evento.GaleriaEventoClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetalleRevista extends AppCompatActivity {

    //Componentes
    private RecyclerView rv_galeria;
    private TextView titulo;
    private TextView fecha;
    private TextView autor;
    private TextView descripcion_arriba;
    private TextView descripcion_abajo;
    private ImageView banner;
    private CardView facebook;
    private CardView whatsapp;
    private CardView instragram;
    private CardView email;

    //Complementos
    private  static int ID;
    AdaptadorGaleriaRevista adaptadorGaleriaRevista;
    private ArrayList<GaleriaRevistaClass> listGaleria = new ArrayList<>();
    private static  String URL = "https://ideaunicabolivia.com/apps/galeriaRevista.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_revista);
        try {
            Toolbar toolbar = findViewById(R.id.toolbarRevista);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
            drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorblanco), PorterDuff.Mode.SRC_IN));
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setTitle("");
            inicializarComponente();
            Bundle parametros = this.getIntent().getExtras();

            ID = parametros.getInt("id");

            titulo.setText(parametros.getString("titulo"));
            fecha.setText(parametros.getString("fecha"));
            descripcion_arriba.setText(parametros.getString("descripcion"));
            autor.setText(parametros.getString("autor"));
            descripcion_abajo.setText(parametros.getString("descripcion_final"));

            Glide.with(getApplicationContext())
                    .load("https://ideaunicabolivia.com/"+parametros.getString("url"))
                    .placeholder(R.drawable.fondorosa)
                    .error(R.drawable.cargando)
                    .into(banner);

            //whatsapp
            if(!parametros.getString("whatsapp").equals("null")&& !parametros.getString("whatsapp").isEmpty()){
                whatsapp.setVisibility(View.VISIBLE);
                final String what = parametros.getString("whatsapp");
                whatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone="+what);
                        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                });
            }

            //facebook
            if(!parametros.getString("facebook").equals("null")&& !parametros.getString("facebook").isEmpty()){
                facebook.setVisibility(View.VISIBLE);
                final String face = parametros.getString("facebook");
                facebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(face);
                        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                });
            }

            //instagram
            if(!parametros.getString("instagram").equals("null")&& !parametros.getString("instagram").isEmpty()){
                instragram.setVisibility(View.VISIBLE);
                final String inst = parametros.getString("instagram");
                instragram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(inst);
                        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                });
            }

            //Email
            if(!parametros.getString("email").equals("null") && !parametros.getString("email").isEmpty() ){
                email.setVisibility(View.VISIBLE);
                final String em = parametros.getString("email");
                email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent emailSelectorIntent = new Intent(Intent.ACTION_SENDTO);
                        emailSelectorIntent.setData(Uri.parse("mailto:"));
                        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{em});
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "IDEA UNICA");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Buenas le hablo como referencia de la app para consultar");
                        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        emailIntent.setSelector( emailSelectorIntent );
                        if( emailIntent.resolveActivity(getPackageManager()) != null )
                            startActivity(emailIntent);
                    }
                });
            }

            GenerarDatos();

        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void inicializarComponente() {
        rv_galeria = findViewById(R.id.galeria_revista_recyclerview);
        titulo = findViewById(R.id.titulo_detallerevista);
        fecha = findViewById(R.id.fecha_detallerevista);
        autor = findViewById(R.id.autor_detallerevista);
        descripcion_arriba = findViewById(R.id.detalle_arriba_detallerevista);
        descripcion_abajo = findViewById(R.id.detalle_abajo_detallerevista);
        facebook = findViewById(R.id.facebook_revista);
        whatsapp = findViewById(R.id.whatsapp_revista);
        instragram = findViewById(R.id.instagram_revista);
        email = findViewById(R.id.email_revista);
        banner = findViewById(R.id.imgToolBar_revista);
    }

    public void GenerarDatos(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //JSON
                            JSONObject jsonObject = new JSONObject(response);

                            //Obtencion de las imagenes
                            JSONArray jsonArray = jsonObject.getJSONArray("galeria-revista");
                            for (int i = 0;i<jsonArray.length();i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                listGaleria.add(new GaleriaRevistaClass(object.getInt("id"), "A",
                                        object.getString("url")));
                            }

                            adaptadorGaleriaRevista = new AdaptadorGaleriaRevista(DetalleRevista.this,listGaleria);
                            rv_galeria.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            rv_galeria.setAdapter(adaptadorGaleriaRevista);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error. Por favor intentelo mass tarde, gracias.", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Error de conexión, por favor verifique el acceso a internet.", Toast.LENGTH_SHORT)
                                .show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                //Enviar atributo codigo de la revista key = "cod"
                params.put("cod",String.valueOf(ID));
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
