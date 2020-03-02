package com.michele.ideaunica.empresa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.michele.ideaunica.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Empresa extends AppCompatActivity {

    //Componentes
    private RecyclerView rv_ubicacion;
    private RecyclerView rv_galeria;
    private TextView titulo;
    private TextView categoria;
    private CircleImageView photo;
    private ImageView imgToolBar;
    private TextView descripcion;
    private LinearLayout contacto;

    private LinearLayout phone;
    private LinearLayout whatsApp;
    private LinearLayout facebook;
    private LinearLayout instagram;
    private LinearLayout pageweb;
    private LinearLayout email;

    private TextView tvphone;
    private TextView tvwhatsApp;
    private TextView tvfacebook;
    private TextView tvinstagram;
    private TextView tvpageweb;
    private TextView tvemail;


    private Button titulo_descripcion;
    private Button titulo_contacto;

    private ProgressBar ub;
    private ProgressBar gal;
    //Galeria
    AdaptadorGaleriaEmpresa adaptadorGaleriaEmpresa;
    private ArrayList<GaleriaEmpresaClass> listGaleria = new ArrayList<>();

    //Descripcion
    String des="";

    //Complementos Ubicacion
    AdaptadorUbicacion adaptadorUbicacion;
    private ArrayList<UbicacionClass> listUbicacion = new ArrayList<>();
    private static  String URL="https://sice.com.bo/ideaunica/apps/empresa_descripcion.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);
        try {
            InicializarComponentes();
            Toolbar toolbar = findViewById(R.id.toolbarEmpresa);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
            drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorblanco), PorterDuff.Mode.SRC_IN));
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setTitle("");

            Bundle parametros = this.getIntent().getExtras();
            titulo.setText(parametros.getString("titulo"));
            categoria.setText(parametros.getString("categoria"));
            descripcion.setText(Html.fromHtml(parametros.getString("descripcion")));
            Glide.with(getApplicationContext()).load("https://sice.com.bo/ideaunica/"+parametros.getString("url"))
                    .placeholder(R.drawable.fondorosa)
                    .error(R.drawable.fondorosa)
                    .into(photo);

            Glide.with(getApplicationContext()).load("https://sice.com.bo/ideaunica/"+parametros.getString("urlToolBar"))
                    .placeholder(R.drawable.fondorosa)
                    .error(R.drawable.fondorosa)
                    .into(imgToolBar);

            CollapsingToolbarLayout collapsingToolbarLayout= findViewById(R.id.collappEmpresa);
            collapsingToolbarLayout.setContentScrim(new ColorDrawable(Color.parseColor("#"+parametros.getString("color","ffffff"))));
            titulo_descripcion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(descripcion.getMaxLines()==3){
                        descripcion.setMaxLines(Integer.MAX_VALUE);
                        titulo_descripcion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_up,0);
                    }
                    else{
                        descripcion.setMaxLines(3);
                        titulo_descripcion.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_down,0);
                    }
                }
            });
            titulo_contacto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(contacto.getVisibility()==View.GONE){
                        contacto.setVisibility(View.VISIBLE);
                        titulo_contacto.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_up,0);
                    }
                    else{
                        contacto.setVisibility(View.GONE);
                        titulo_contacto.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_down,0);
                    }
                }
            });
            GenerarDatos(parametros.getString("codigo"),parametros.getString("titulo"));
            if(!parametros.getString("numero").equals("") && !parametros.getString("numero").isEmpty()){
                phone.setVisibility(View.VISIBLE);
                final String tel="+"+parametros.getString("numero");
                tvphone.setText(tel);
                phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent =new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel", tel, null));
                        startActivity(intent);
                    }
                });
            }

            if(!parametros.getString("whatsapp").equals("null")&& !parametros.getString("whatsapp").isEmpty()){
                whatsApp.setVisibility(View.VISIBLE);
                final String what=parametros.getString("whatsapp");
                tvwhatsApp.setText("+"+what);
                whatsApp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone="+what);
                        Intent intent =new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                });
            }
            if(!parametros.getString("facebook").equals("null")&& !parametros.getString("facebook").isEmpty()){
                facebook.setVisibility(View.VISIBLE);
                final String face=parametros.getString("facebook");
                tvfacebook.setText(parametros.getString("nombrefacebook"));
                facebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(face);
                        Intent intent =new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                });

            }
            if(!parametros.getString("instagram").equals("null")&& !parametros.getString("instagram").isEmpty()){
                instagram.setVisibility(View.VISIBLE);
                final String inst=parametros.getString("instagram");
                tvinstagram.setText(parametros.getString("nombreinstagram"));
                instagram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri=Uri.parse(inst);
                        Intent intent =new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                });
            }
            if(!parametros.getString("paginaweb").equals("null")&& !parametros.getString("paginaweb").isEmpty()){
                pageweb.setVisibility(View.VISIBLE);
                final String web=parametros.getString("paginaweb");
                tvpageweb.setText(web);
                pageweb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(web);
                        Intent intent =new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                });
            }
            if(!parametros.getString("email").equals("null") && !parametros.getString("email").isEmpty() ){
                email.setVisibility(View.VISIBLE);
                final String em=parametros.getString("email");
                tvemail.setText(em);
                email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent emailSelectorIntent = new Intent(Intent.ACTION_SENDTO);
                        emailSelectorIntent.setData(Uri.parse("mailto:"));
                        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{em});
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "IDEA UNICA");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "CUERPO");
                        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        emailIntent.setSelector( emailSelectorIntent );
                        if( emailIntent.resolveActivity(getPackageManager()) != null )
                            startActivity(emailIntent);
                    }
                });
            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        onBackPressed();
        return true;
    }

    private void InicializarComponentes() {
        rv_ubicacion=findViewById(R.id.Ubicacion_recyclerview);
        rv_galeria=findViewById(R.id.Galeria_empresa_recyclerview);
        titulo=findViewById(R.id.titulo_empresa);
        categoria=findViewById(R.id.categoria_empresa);
        photo=findViewById(R.id.img_empresa);
        descripcion=findViewById(R.id.descripcion_empresa);
        contacto=findViewById(R.id.contacto_empresa);
        phone=findViewById(R.id.phone_empresa);
        whatsApp=findViewById(R.id.whatsapp_empresa);
        facebook=findViewById(R.id.facebook_empresa);
        instagram=findViewById(R.id.instagram_empresa);
        pageweb=findViewById(R.id.paginaweb_empresa);
        email=findViewById(R.id.email_empresa);
        tvphone=findViewById(R.id.tx_phone_empresa);
        tvwhatsApp=findViewById(R.id.tx_whatsapp_empresa);
        tvfacebook=findViewById(R.id.tx_facebook_empresa);
        tvinstagram=findViewById(R.id.tx_instagram_empresa);
        tvpageweb=findViewById(R.id.tx_paginaweb_empresa);
        tvemail=findViewById(R.id.tx_email_empresa);
        titulo_descripcion=findViewById(R.id.titulo_descripcion_empresa);
        titulo_contacto=findViewById(R.id.titulo_contacto_empresa);
        imgToolBar=findViewById(R.id.imgToolBar_empresa);
        ub=findViewById(R.id.progress_sucursal);
        gal=findViewById(R.id.progress_galeria_empresa);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void GenerarDatos(final String cod,final String tl){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            try {
                                JSONArray jsonArray = jsonObject.getJSONArray("galeria");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    GaleriaEmpresaClass galeriaEmpresaClass =
                                            new GaleriaEmpresaClass(
                                                    object.getInt("id"),
                                                    tl,
                                                    object.getString("url"));

                                    listGaleria.add(galeriaEmpresaClass);
                                }
                                adaptadorGaleriaEmpresa = new AdaptadorGaleriaEmpresa(Empresa.this, listGaleria);
                                rv_galeria.setLayoutManager(new LinearLayoutManager(Empresa.this, LinearLayoutManager.HORIZONTAL, false));
                                rv_galeria.setAdapter(adaptadorGaleriaEmpresa);
                                rv_galeria.addOnItemTouchListener(new AdaptadorGaleriaEmpresa.RecyclerTouchListener(getApplicationContext(), rv_galeria, new AdaptadorGaleriaEmpresa.ClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("images", listGaleria);
                                        bundle.putInt("position", position);

                                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                        SliderShowGaleriaEmpresa newFragment = SliderShowGaleriaEmpresa.newInstance();
                                        newFragment.setArguments(bundle);
                                        newFragment.show(ft, "slideshow");
                                    }

                                    @Override
                                    public void onLongClick(View view, int position) {

                                    }
                                }));
                                gal.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                gal.setVisibility(View.GONE);
                            }

                            try {
                                JSONArray jsonArray2 = jsonObject.getJSONArray("direccion");
                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    JSONObject object = jsonArray2.getJSONObject(i);
                                    UbicacionClass ubicacionClass =
                                            new UbicacionClass(
                                                    object.getInt("id"),
                                                    object.getString("sucursal"),
                                                    object.getString("direccion"), object.getString("url"));

                                    listUbicacion.add(ubicacionClass);
                                }
                                adaptadorUbicacion = new AdaptadorUbicacion(Empresa.this, listUbicacion);
                                rv_ubicacion.setLayoutManager(new LinearLayoutManager(Empresa.this));
                                adaptadorUbicacion.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Uri location=Uri.parse(listUbicacion.get(rv_ubicacion.getChildAdapterPosition(v)).getUrl());
                                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

                                        PackageManager packageManager = getPackageManager();
                                        List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
                                        boolean isIntentSafe = activities.size() > 0;

                                        if (isIntentSafe) {
                                            startActivity(mapIntent);
                                        }
                                    }
                                });
                                rv_ubicacion.setVisibility(View.VISIBLE);
                                rv_ubicacion.setAdapter(adaptadorUbicacion);

                                ub.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                ub.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Empresa.this,
                                    "Error", Toast.LENGTH_LONG)
                                    .show();

                            gal.setVisibility(View.GONE);
                            ub.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Empresa.this,
                                "Error  2"+error.getMessage(), Toast.LENGTH_LONG)
                                .show();
                        gal.setVisibility(View.GONE);
                        ub.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cod",cod);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

