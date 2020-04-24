package com.michele.ideaunica.menu.evento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Evento extends AppCompatActivity {

    //Componentes
    private ImageView imgbanner;
    private TextView titulo;
    private TextView fecha;
    private TextView descripcion_arriba;
    private RecyclerView recyclerView;
    private TextView descripcion_abajo;
    private CardView facebook;
    private CardView whatsapp;
    private CardView instragram;
    private CardView email;

    //Complementos
    private String ID;
    private static  String URL = "https://ideaunicabolivia.com/apps/detalleEvento.php";
    AdaptadorGaleriaEvento adaptadorGaleriaEvento;
    private ArrayList<GaleriaEventoClass> listGaleria = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);
        try {
                Toolbar toolbar = findViewById(R.id.toolbarEvento);
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
                drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorblanco), PorterDuff.Mode.SRC_IN));
                getSupportActionBar().setHomeAsUpIndicator(drawable);
                getSupportActionBar().setTitle("");
                inicializarComponente();
                Bundle parametros = this.getIntent().getExtras();
                ID = parametros.getString("ID");
                titulo.setText(parametros.getString("titulo"));
                descripcion_arriba.setText(parametros.getString("descripcion"));

            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formateador = new SimpleDateFormat("MMM d, yyyy");
            try {
                Date date = parseador.parse(parametros.getString("fecha_inicio"));
                fecha.setText(formateador.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
                fecha.setText(parametros.getString("fecha_inicio"));
            }
            try {
                Date date = parseador.parse(parametros.getString("fecha_final"));
                fecha.setText(fecha.getText()+"-"+formateador.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            GenerarDatos();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void inicializarComponente() {
        imgbanner = findViewById(R.id.imgToolBar_Evento);
        titulo = findViewById(R.id.titulo_evento);
        fecha = findViewById(R.id.fecha_evento);
        descripcion_arriba = findViewById(R.id.arriba_detalle_evento);
        recyclerView = findViewById(R.id.galeria_evento_recyclerview);
        descripcion_abajo = findViewById(R.id.abajo_detalle_evento);
        facebook = findViewById(R.id.facebook_evento);
        whatsapp = findViewById(R.id.whatsapp_evento);
        instragram = findViewById(R.id.instagram_evento);
        email = findViewById(R.id.email_evento);
    }

    public void GenerarDatos(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //JSON
                            JSONObject jsonObject = new JSONObject(response);

                            //Obtencion de datos eventos
                            JSONArray jsonArray = jsonObject.getJSONArray("evento");
                            for (int i = 0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                Glide.with(getApplicationContext())
                                        .load("https://ideaunicabolivia.com/"+object.getString("url"))
                                        .placeholder(R.drawable.fondorosa)
                                        .error(R.drawable.cargando)
                                        .into(imgbanner);
                                descripcion_abajo.setText(Html.fromHtml(object.getString("descripcion")));
                                if(!object.getString("whatsapp").equals("null")&& !object.getString("whatsapp").isEmpty()){
                                    whatsapp.setVisibility(View.VISIBLE);
                                    final String what = object.getString("whatsapp");
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
                                if(!object.getString("facebook").equals("null")&& !object.getString("facebook").isEmpty()){
                                    facebook.setVisibility(View.VISIBLE);
                                    final String face = object.getString("facebook");
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
                                if(!object.getString("instagram").equals("null")&& !object.getString("instagram").isEmpty()){
                                    instragram.setVisibility(View.VISIBLE);
                                    final String inst = object.getString("instagram");
                                    instragram.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Uri uri = Uri.parse(inst);
                                            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                                            startActivity(intent);
                                        }
                                    });
                                }

                                //email
                                if(!object.getString("email").equals("null") && !object.getString("email").isEmpty() ){
                                    email.setVisibility(View.VISIBLE);
                                    final String em = object.getString("email");
                                    email.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent emailSelectorIntent = new Intent(Intent.ACTION_SENDTO);
                                            emailSelectorIntent.setData(Uri.parse("mailto:"));
                                            final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{em});
                                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "IDEA UNICA");
                                            emailIntent.putExtra(Intent.EXTRA_TEXT, "Buenas le hablo como referencia de la app idea unica para consultar");
                                            emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                            emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                                            emailIntent.setSelector( emailSelectorIntent );
                                            if( emailIntent.resolveActivity(getPackageManager()) != null )
                                                startActivity(emailIntent);
                                        }
                                    });
                                }
                            }

                            //Obtencion de la galeria
                            JSONArray jsonArray1 = jsonObject.getJSONArray("galeria");
                            for (int i = 0;i<jsonArray1.length();i++) {
                                JSONObject object = jsonArray1.getJSONObject(i);
                                listGaleria.add(new GaleriaEventoClass(object.getInt("id"),
                                        "",object.getString("url")));
                            }

                            adaptadorGaleriaEvento = new AdaptadorGaleriaEvento(getApplicationContext(),listGaleria);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setAdapter(adaptadorGaleriaEvento);

                            } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error. Por favor intentelo mas tarde, gracias.", Toast.LENGTH_SHORT)
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

                //Enviamos el atributo del id key = "id"
                params.put("cod",ID);
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
