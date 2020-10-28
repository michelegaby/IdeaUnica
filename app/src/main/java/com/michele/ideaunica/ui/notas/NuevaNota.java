package com.michele.ideaunica.ui.notas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.R;
import com.michele.ideaunica.departamento.AdaptadorCategoria;
import com.michele.ideaunica.departamento.Categoria;
import com.michele.ideaunica.departamento.CategoriaClass;
import com.michele.ideaunica.empresa.Empresas;
import com.michele.ideaunica.sharedPreferences.SessionCumple;
import com.michele.ideaunica.sharedPreferences.SessionManager;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class NuevaNota extends AppCompatActivity {

    SessionCumple sessionCumple;
    SessionManager sessionManager;

    //Componentes
    private EditText titulo;
    private EditText contenido;
    private LinearLayout encabezado;

    //Complemento
    private static String colores;

    private static  String URL = "https://www.ideaunicabolivia.com/apps/fiesta/Nuevanota.php";

    android.app.AlertDialog mDialog;
    private ArrayList<NotaClass> listNotas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_nota);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4C79FB")));
        colores = "4C79FB";

        InicializarComponentes();

        sessionCumple = new SessionCumple(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());

        try {

            Bundle parametros = this.getIntent().getExtras();
            titulo.setText(parametros.getString("titulo",""));
            contenido.setText(parametros.getString("contenido",""));

        }catch (Exception e){
            e.printStackTrace();
        }

        mDialog = new SpotsDialog.Builder().setContext(NuevaNota.this).setMessage("Espera un momento por favor").build();

        contenido.requestFocus();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nueva_nota,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.mn_guardar_nueva_nota:Save();break;
            case R.id.mn_amarillo_nueva_nota:Color("FDBB00");break;
            case R.id.mn_azul_nueva_nota:Color("4C79FB");break;
            case R.id.mn_celeste_nueva_nota:Color("03A9F4");break;
            case R.id.mn_lila_nueva_nota:Color("9F50E3");break;
            case R.id.mn_verde_nueva_nota:Color("48CC64");break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void InicializarComponentes() {
        titulo = findViewById(R.id.titulo_nueva_nota);
        encabezado = findViewById(R.id.encabezado_nueva_nota);
        contenido = findViewById(R.id.contenido_nueva_nota);
    }

    public void Color(final String clr){
        encabezado.setBackgroundColor(Color.parseColor("#" + clr));
        colores = clr;
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#" + clr)));
    }

    @Override
    public void onBackPressed() {

        if(!titulo.getText().toString().equals("") || !contenido.getText().toString().equals(""))
        {
            AlertDialog.Builder Advertencia = new AlertDialog.Builder(this);

            Advertencia.setTitle("Advertencia");
            Advertencia.setMessage("Desea salir. Las modificaciones realizadas se perderan.");
            Advertencia.setCancelable(false);

            Advertencia.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    contenido.setText("");
                    titulo.setText("");
                    onBackPressed();
                }
            });

            Advertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            Advertencia.show();

        }else
        {
            super.onBackPressed();
        }

    }

    public void Save(){

        Date myDate = new Date();

        String fe = new SimpleDateFormat("yyyy-MM-dd").format(myDate);

        String ide = sessionCumple.getId().toString();
        String tit = titulo.getText().toString();
        String cont = contenido.getText().toString();

        GuardarNewNote(ide,tit,fe,cont,colores);

    }


    public void GuardarNewNote(final String xidevento,final String xtitulo,final String xfecha,final String xcontenido,final String xcolor){

        mDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            final JSONObject jsonObject = new JSONObject(response);
                            Boolean isStatus = jsonObject.getBoolean("estado");

                            if(isStatus){

                                AlertDialog.Builder Advertencia = new AlertDialog.Builder(NuevaNota.this);

                                Advertencia.setTitle("Guardado");
                                Advertencia.setMessage("Se pudo guardar correctamente.");
                                Advertencia.setCancelable(false);

                                Advertencia.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        try {

                                            AddDataNota(jsonObject.getString("id"),xtitulo,xfecha,xcolor,xcontenido);
                                        }catch (Exception e) {
                                            Log.e("ERROR", "NO DEVULVE ID");
                                        }
                                    }
                                });

                                Advertencia.show();

                            }else {

                                AlertDialog.Builder Advertencia = new AlertDialog.Builder(NuevaNota.this);

                                Advertencia.setTitle("Error");
                                Advertencia.setMessage("No se guardo, lo sentimos mucho");
                                Advertencia.setCancelable(false);

                                Advertencia.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                Advertencia.show();
                            }
                            mDialog.dismiss();

                        } catch (Exception e) {
                            mDialog.dismiss();

                            AlertDialog.Builder Advertencia = new AlertDialog.Builder(NuevaNota.this);

                            Advertencia.setTitle("Error");
                            Advertencia.setMessage("Por favor intentelo mas tarde, gracias.");
                            Advertencia.setCancelable(false);

                            Advertencia.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            Advertencia.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mDialog.dismiss();

                        AlertDialog.Builder Advertencia = new AlertDialog.Builder(NuevaNota.this);

                        Advertencia.setTitle("Error");
                        Advertencia.setMessage("Error de conexión, por favor verifique el acceso a internet.");
                        Advertencia.setCancelable(false);

                        Advertencia.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        Advertencia.show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idevento",xidevento);
                params.put("titulo",xtitulo);
                params.put("fecha",xfecha);
                params.put("contenido",xcontenido);
                params.put("color",xcolor);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void AddDataNota(String xid,String xtitulo, String xfecha, String xcolor,String xcontenido){

        if(sessionCumple.isNotes()){

            listNotas.clear();
            listNotas = sessionCumple.readNotes();

            listNotas.add(new NotaClass(Integer.valueOf(xid),xtitulo,xfecha,xcolor,xcontenido));

            sessionCumple.createSessionNotes(listNotas);

        }

        contenido.setText("");
        titulo.setText("");

        onBackPressed();
    }
}
