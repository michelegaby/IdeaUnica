package com.michele.ideaunica.cursos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.michele.ideaunica.empresa.AdaptadorGaleriaEmpresa;
import com.michele.ideaunica.empresa.AdaptadorUbicacion;
import com.michele.ideaunica.empresa.Empresa;
import com.michele.ideaunica.empresa.GaleriaEmpresaClass;
import com.michele.ideaunica.empresa.SliderShowGaleriaEmpresa;
import com.michele.ideaunica.empresa.UbicacionClass;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Curso extends AppCompatActivity {

    //Componentes
    private TextView titulo;
    private TextView fecha;
    private TextView departamento;
    private TextView contenido;
    private TextView autor;
    private TextView costo;
    private TextView telefono;
    private TextView whatsapp;
    private TextView facebook;
    private TextView instagram;
    private TextView pageweb;
    private TextView email;
    private LinearLayout lnwhatsapp;
    private LinearLayout lnfacebook;
    private LinearLayout lninstagram;
    private LinearLayout lnpageweb;
    private LinearLayout lnemail;
    private LinearLayout contacto;
    private Button titulo_contacto;
    private TextView duracion;
    private LinearLayout lntelefono;
    private RecyclerView rv_galeria;
    private TextView horario;
    private TextView direccion;

    //Complementos
    AdaptadorGaleriaCurso adaptadorGaleriaCurso;
    private ArrayList<GaleriaCursoClass> listGaleria = new ArrayList<>();
    private static String ID;
    private static  String URL = "https://ideaunicabolivia.com/apps/galeriaCurso.php";

    private TextView informacion;
    private TextView contenidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorblanco), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setTitle("");
        InicilizarComponentes();
        final Bundle parametros = this.getIntent().getExtras();

        ID = parametros.getString("ID");

        titulo.setText(parametros.getString("titulo"));
        costo.setText("Bs."+parametros.getString("costo"));

        try {
            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formateador = new SimpleDateFormat("d MMMM, yyyy");
            Date date = parseador.parse(parametros.getString("fecha"));
            fecha.setText(formateador.format(date));

        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

        departamento.setText(parametros.getString("departamento"));
        contenido.setText(parametros.getString("descripcion"));
        autor.setText(parametros.getString("autor"));
        horario.setText(parametros.getString("horario"));
        direccion.setText(parametros.getString("direccion"));
        duracion.setText("x "+parametros.getString("duracion")+"día/s");

        //Contactos
        try {

            //Desplegar contactos
            titulo_contacto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(contacto.getVisibility() == View.GONE){
                        contacto.setVisibility(View.VISIBLE);
                        titulo_contacto.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_up,0);
                    }
                    else{
                        contacto.setVisibility(View.GONE);
                        titulo_contacto.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_down,0);
                    }
                }
            });

            //Habilitar telefono
            if(!parametros.getString("telefono").equals("") && !parametros.getString("telefono").isEmpty()){
                final String tel = "+"+parametros.getString("telefono");
                telefono.setVisibility(View.VISIBLE);
                telefono.setText(tel);
                lntelefono.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel", tel, null));
                        startActivity(intent);
                    }
                });
            }

            //Habilitar whatsapp
            if(!parametros.getString("whatsapp").equals("null")&& !parametros.getString("whatsapp").isEmpty()){
                lnwhatsapp.setVisibility(View.VISIBLE);
                final String what = parametros.getString("whatsapp");
                whatsapp.setText("+"+what);
                lnwhatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + what + "&text=Hola,%20vengo%20como%20referencia%20de%20la%20app%20IdeaUnica%20y%20quisiera%20consultar%20sobre%20el%20curso%20'" + titulo.getText().toString() + "'%20");
                        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                });
            }

            //Habilitar facebook
            if(!parametros.getString("facebook").equals("null")&& !parametros.getString("facebook").isEmpty()){
                lnfacebook.setVisibility(View.VISIBLE);
                final String face = parametros.getString("facebook");
                facebook.setText(parametros.getString("nombrefacebook"));
                lnfacebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(face);
                        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                });
            }

            //Habilitar instagram
            if(!parametros.getString("instagram").equals("null")&& !parametros.getString("instagram").isEmpty()){
                lninstagram.setVisibility(View.VISIBLE);
                final String inst = parametros.getString("instagram");
                instagram.setText(parametros.getString("nombreinstagram"));
                lninstagram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(inst);
                        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                });
            }

            //Habilitar pagina web
            if(!parametros.getString("paginaweb").equals("null")&& !parametros.getString("paginaweb").isEmpty()){
                lnpageweb.setVisibility(View.VISIBLE);
                final String web = parametros.getString("paginaweb");
                pageweb.setText(web);
                lnpageweb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(web);
                        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                });
            }

            //Habilitar correo electronico
            if(!parametros.getString("email").equals("null") && !parametros.getString("email").isEmpty() ){
                lnemail.setVisibility(View.VISIBLE);
                final String em = parametros.getString("email");
                email.setText(em);
                lnemail.setOnClickListener(new View.OnClickListener() {
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

            GenerarGaleria();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error. Por favor intentelo mas tarde, gracias.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void InicilizarComponentes() {
        titulo = findViewById(R.id.titulo_curso);
        fecha = findViewById(R.id.fecha_curso);
        departamento = findViewById(R.id.departamento_curso);
        contenido = findViewById(R.id.contenido_curso);
        autor = findViewById(R.id.autor_curso);
        costo = findViewById(R.id.costo_curso);
        duracion = findViewById(R.id.duracion_curso);
        lntelefono = findViewById(R.id.phone_Curso);
        telefono = findViewById(R.id.tx_phone_curso);
        lnwhatsapp = findViewById(R.id.whatsapp_curso);
        whatsapp = findViewById(R.id.tx_whatsapp_curso);
        lnfacebook = findViewById(R.id.facebook_curso);
        facebook = findViewById(R.id.tx_facebook_curso);
        lninstagram = findViewById(R.id.instagram_curso);
        instagram = findViewById(R.id.tx_instagram_curso);
        lnpageweb = findViewById(R.id.paginaweb_curso);
        pageweb = findViewById(R.id.tx_paginaweb_curso);
        lnemail = findViewById(R.id.email_curso);
        email = findViewById(R.id.tx_email_curso);
        titulo_contacto = findViewById(R.id.titulo_contacto_curso);
        contacto = findViewById(R.id.contacto_curso);
        contenidos = findViewById(R.id.contenidos);
        informacion = findViewById(R.id.informacion);
        rv_galeria = findViewById(R.id.galeria_curso_recyclerview);
        direccion = findViewById(R.id.direccion_curso);
        horario = findViewById(R.id.horario_curso);
    }

    private void GenerarGaleria() {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //JSON
                                JSONObject jsonObject = new JSONObject(response);

                                //Obtencion de datos de galeria
                                JSONArray jsonArray = jsonObject.getJSONArray("galeria-curso");
                                for (int i = 0;i<jsonArray.length();i++)
                                {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    GaleriaCursoClass cursos = new GaleriaCursoClass(object.getInt("id"),"", object.getString("url"));
                                    listGaleria.add(cursos);
                                }

                                //no tiene funcionalidad al seleccionar

                                adaptadorGaleriaCurso = new AdaptadorGaleriaCurso(Curso.this,listGaleria);
                                rv_galeria.setLayoutManager(new LinearLayoutManager(Curso.this));
                                rv_galeria.setVisibility(View.VISIBLE);
                                rv_galeria.setAdapter(adaptadorGaleriaCurso);

                            } catch (JSONException e) {
                                e.printStackTrace();
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

                    //Envias como atributo el id de curso key = "cod"
                    params.put("cod",ID);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
            requestQueue.add(stringRequest);
        }
}
