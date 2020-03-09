package com.michele.ideaunica.menu.Entrevista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.michele.ideaunica.menu.Revista.AdaptadorGaleriaRevista;
import com.michele.ideaunica.menu.Revista.DetalleRevista;
import com.michele.ideaunica.menu.Revista.GaleriaRevistaClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VerDetalleEntrevista extends AppCompatActivity {

    private RecyclerView rv_galeria;
    private TextView titulo;
    private TextView fecha;
    private TextView descripcion_inicio;
    private TextView descripcion_final;
    private TextView autor;
    private CardView facebook;
    private CardView whatsapp;
    private CardView instagram;
    private CardView email;
    private ImageView img;

    //Galeria
    AdaptadorGaleriaEntrevista adaptadorGaleriaEntrevista;
    private ArrayList<GaleriaEntrevistaClass> listGaleria = new ArrayList<>();
    private int ID;
    private static  String URL="https://ideaunicabolivia.com/apps/verDetalleEntrevista.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_detalle_entrevista);
        try {
            Toolbar toolbar = findViewById(R.id.toolbarEntrevista);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
            drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorblanco), PorterDuff.Mode.SRC_IN));
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setTitle("");
            inicializarComponente();
            Bundle parametros = this.getIntent().getExtras();
            ID=parametros.getInt("id");
            titulo.setText(parametros.getString("nombre"));
            Glide.with(getApplicationContext())
                    .load("https://ideaunicabolivia.com/"+parametros.getString("img"))
                    .placeholder(R.drawable.cargando)
                    .error(R.drawable.fondorosa)
                    .into(img);
            GenerarDatos();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void GenerarDatos(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("galeria");
                            for (int i=0;i<jsonArray.length();i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                listGaleria.add(new GaleriaEntrevistaClass(object.getInt("id"), "A",
                                        object.getString("url")));
                            }
                            adaptadorGaleriaEntrevista= new AdaptadorGaleriaEntrevista(VerDetalleEntrevista.this,listGaleria);
                            rv_galeria.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            rv_galeria.setAdapter(adaptadorGaleriaEntrevista);
                            JSONArray jsonArray2 = jsonObject.getJSONArray("detalle");
                            JSONObject object = jsonArray2.getJSONObject(0);
                            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat formateador = new SimpleDateFormat("MMM d, yyyy");
                            try {
                                Date date = parseador.parse(object.getString("fecha"));
                                fecha.setText(formateador.format(date));
                            } catch (ParseException e) {
                                e.printStackTrace();
                                fecha.setText(object.getString("fecha"));
                            }
                            descripcion_inicio.setText(object.getString("descripcion_inicio"));
                            descripcion_final.setText(object.getString("descripcion_final"));
                            autor.setText(object.getString("autor"));

                            if(!object.getString("whatsapp").equals("null")&& !object.getString("whatsapp").isEmpty()){
                                whatsapp.setVisibility(View.VISIBLE);
                                final String what=object.getString("whatsapp");
                                whatsapp.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone="+what);
                                        Intent intent =new Intent(Intent.ACTION_VIEW,uri);
                                        startActivity(intent);
                                    }
                                });
                            }
                            if(!object.getString("facebook").equals("null")&& !object.getString("facebook").isEmpty()){
                                facebook.setVisibility(View.VISIBLE);
                                final String face=object.getString("facebook");
                                facebook.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Uri uri = Uri.parse(face);
                                        Intent intent =new Intent(Intent.ACTION_VIEW,uri);
                                        startActivity(intent);
                                    }
                                });
                            }
                            if(!object.getString("instagram").equals("null")&& !object.getString("instagram").isEmpty()){
                                instagram.setVisibility(View.VISIBLE);
                                final String inst=object.getString("instagram");
                                instagram.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Uri uri=Uri.parse(inst);
                                        Intent intent =new Intent(Intent.ACTION_VIEW,uri);
                                        startActivity(intent);
                                    }
                                });
                            }
                            if(!object.getString("email").equals("null") && !object.getString("email").isEmpty() ){
                                email.setVisibility(View.VISIBLE);
                                final String em=object.getString("email");
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
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "No existe fotos", Toast.LENGTH_LONG)
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
    private void inicializarComponente() {
        rv_galeria=findViewById(R.id.galeria_Entrevista_recyclerview);
        titulo=findViewById(R.id.titulo_VerDetalleEntrevista);
        fecha=findViewById(R.id.fecha_VerDetalleEntrevista);
        descripcion_inicio=findViewById(R.id.descripcionArriba_VerdetalleEntrevista);
        autor=findViewById(R.id.autor_VerdetalleEntrevista);
        descripcion_final=findViewById(R.id.descripcionAbajo_VerdetalleEntrevista);
        facebook=findViewById(R.id.facebook_entrevista);
        whatsapp=findViewById(R.id.whatsapp_entrevista);
        instagram=findViewById(R.id.instagram_entrevista);
        email=findViewById(R.id.email_entrevista);
        img=findViewById(R.id.imgToolBar_Entrevista);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
