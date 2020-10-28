package com.michele.ideaunica.ui.notas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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
import com.michele.ideaunica.sharedPreferences.SessionCumple;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class EditarNota extends AppCompatActivity {

    SessionCumple sessionCumple;
    //Componentes
    private EditText titulo;
    private EditText contenido;
    private LinearLayout encabezado;

    //Complemento
    private static int ID;
    private static String colores;

    private static String intColor;
    private static String intTitulo;
    private static String intContenido;

    private static  String URL = "https://www.ideaunicabolivia.com/apps/fiesta/UpdateNota.php";

    android.app.AlertDialog mDialog;
    private ArrayList<NotaClass> listNotas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editar_nota);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setElevation(0);

        InicializarComponentes();

        sessionCumple = new SessionCumple(getApplicationContext());

        Bundle parametros = this.getIntent().getExtras();

        ID = parametros.getInt("id",0);

        mDialog = new SpotsDialog.Builder().setContext(EditarNota.this).setMessage("Espera un momento por favor").build();

        intColor = parametros.getString("color");
        intTitulo = parametros.getString("titulo","Error");
        intContenido = parametros.getString("contenido","Error");

        titulo.setText(intTitulo);
        contenido.setText(intContenido);
        colores = intColor;

        encabezado.setBackgroundColor(Color.parseColor("#" + colores));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#" + colores)));
        contenido.requestFocus();
    }

    private void InicializarComponentes() {
        titulo = findViewById(R.id.titulo_editar_nota);
        encabezado = findViewById(R.id.encabezado_editar_nota);
        contenido = findViewById(R.id.contenido_editar_nota);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar_nota,menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.mn_guardar_editar_nota:Guardar();break;
            case R.id.mn_amarillo_editar_nota:ColorAmarillo();break;
            case R.id.mn_azul_editar_nota:ColorAzul();break;
            case R.id.mn_celeste_editar_nota:ColorCeleste();break;
            case R.id.mn_lila_editar_nota:ColorLila();break;
            case R.id.mn_verde_editar_nota:ColorVerde();break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    public  void Guardar(){

        if(!titulo.getText().toString().equals(intTitulo) || !contenido.getText().toString().equals(intContenido) || !colores.equals(intColor)){

            Date myDate = new Date();

            String fe = new SimpleDateFormat("yyyy-MM-dd").format(myDate);

            update(titulo.getText().toString().trim(),fe,contenido.getText().toString().trim(),colores);

        }else {

            onBackPressed();
        }
    }

    public void update(final String xtitulo,final String xfecha,final String xcontenido,final String xcolor){

        mDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            final JSONObject jsonObject = new JSONObject(response);
                            Boolean isStatus = jsonObject.getBoolean("estado");

                            if(isStatus){

                                AlertDialog.Builder Advertencia = new AlertDialog.Builder(EditarNota.this);

                                Advertencia.setTitle("Guardado");
                                Advertencia.setMessage("Se pudo guardar correctamente.");
                                Advertencia.setCancelable(false);

                                Advertencia.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        try {

                                            UpdateDataNota(xtitulo,xfecha,xcolor,xcontenido);


                                        }catch (Exception e) {
                                            Log.e("ERROR", "NO DEVULVE ID");
                                        }
                                    }
                                });

                                Advertencia.show();

                            }else {

                                AlertDialog.Builder Advertencia = new AlertDialog.Builder(EditarNota.this);

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

                            AlertDialog.Builder Advertencia = new AlertDialog.Builder(EditarNota.this);

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

                        AlertDialog.Builder Advertencia = new AlertDialog.Builder(EditarNota.this);

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
                params.put("id",String.valueOf(ID));
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


    public void ColorAzul(){
        encabezado.setBackgroundColor(Color.parseColor("#4C79FB"));
        colores = "4C79FB";
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4C79FB")));

    }
    public void ColorVerde(){
        encabezado.setBackgroundColor(Color.parseColor("#48CC64"));
        colores = "48CC64";
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#48CC64")));
    }

    public void ColorAmarillo(){
        encabezado.setBackgroundColor(Color.parseColor("#FDBB00"));
        colores = "FDBB00";
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FDBB00")));
    }

    public void ColorLila(){
        encabezado.setBackgroundColor(Color.parseColor("#9F50E3"));
        colores = "9F50E3";
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9F50E3")));
    }
    public void ColorCeleste(){
        encabezado.setBackgroundColor(Color.parseColor("#03A9F4"));
        colores = "03A9F4";
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03A9F4")));
    }

    @Override
    public void onBackPressed() {
        if(!titulo.getText().toString().equals(intTitulo) || !contenido.getText().toString().equals(intContenido) || !colores.equals(intColor)){
            AlertDialog.Builder Advertencia = new AlertDialog.Builder(this);
            Advertencia.setTitle("Advertencia");
            Advertencia.setMessage("Desea salir. Las modificaciones realizadas se perderan");
            Advertencia.setCancelable(false);
            Advertencia.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    contenido.setText(intContenido);
                    titulo.setText(intTitulo);
                    colores = intColor;
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


    public void UpdateDataNota(String xtitulo, String xfecha, String xcolor,String xcontenido){

        if(sessionCumple.isNotes()){

            listNotas.clear();
            listNotas = sessionCumple.readNotes();

            for (NotaClass item: listNotas){
                if(item.getId() == ID){

                    item.setTitulo(xtitulo);
                    item.setContenido(xcontenido);
                    item.setColor(xcolor);
                    item.setFecha(xfecha);

                    break;
                }
            }

            sessionCumple.createSessionNotes(listNotas);



        }

        intContenido = xcontenido;
        intTitulo = xtitulo;
        intColor = xcolor;

        onBackPressed();
    }
}

