package com.michele.ideaunica.ui.notas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.R;
import com.michele.ideaunica.sharedPreferences.SessionCumple;
import com.michele.ideaunica.ui.tareas.TareaClass;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class Nota extends AppCompatActivity {

    SessionCumple sessionCumple;

    //componentes
    private TextView titulo;
    private TextView fecha;
    private TextView contenido;
    private LinearLayout color;
    private FloatingActionButton fab;

    //Complementos
    private static int id;
    private static String colores;
    private ArrayList<NotaClass> listNotas = new ArrayList<>();

    private static  String URL = "https://www.ideaunicabolivia.com/apps/fiesta/EliminarNota.php";

    android.app.AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setElevation(0);

        inializarComponentes();

        sessionCumple = new SessionCumple(getApplicationContext());

        mDialog = new SpotsDialog.Builder().setContext(Nota.this).setMessage("Espera un momento por favor").build();

        Bundle parametros = this.getIntent().getExtras();

        id = parametros.getInt("id");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),EditarNota.class);
                Bundle parametros  = new Bundle();
                parametros.putInt("id",id);
                parametros.putString("titulo",titulo.getText().toString());
                parametros.putString("color",colores);
                parametros.putString("contenido",contenido.getText().toString());
                intent.putExtras(parametros);
                startActivity(intent);
            }
        });

    }

    private void GetNotas(){

        if(sessionCumple.isNotes()){

            listNotas.clear();
            listNotas = sessionCumple.readNotes();

            for (NotaClass item: listNotas){
                if(item.getId() == id){

                    titulo.setText(item.getTitulo());

                    SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formateador = new SimpleDateFormat("MMMM d, yyyy");

                    try {
                        Date date = parseador.parse(item.getFecha());
                        fecha.setText(formateador.format(date) + " - Ultima Actualización");
                    } catch (ParseException e) {
                        fecha.setText(item.getFecha() + " - Ultima Actualización");
                    }

                    contenido.setText(item.getContenido());
                    colores = item.getColor();
                    color.setBackgroundColor(Color.parseColor("#" + colores));
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#" + colores)));

                    break;
                }
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_eliminar_nota,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mn_borrar_eliminar_nota:Eliminar();break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Eliminar() {
        AlertDialog.Builder Advertencia = new AlertDialog.Builder(this);
        Advertencia.setTitle("Eliminar");
        Advertencia.setMessage("Esta seguro de eliminar esta nota?");
        Advertencia.setCancelable(false);
        Advertencia.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EliminarNota();
            }
        });
        Advertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        Advertencia.show();
    }

    public void EliminarNota(){

        mDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            Boolean isStatus = jsonObject.getBoolean("estado");

                            if(isStatus){

                                AlertDialog.Builder Advertencia = new AlertDialog.Builder(Nota.this);

                                Advertencia.setTitle("Guardado");
                                Advertencia.setMessage("Se pudo guardar correctamente.");
                                Advertencia.setCancelable(false);

                                Advertencia.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        EliminarDataNota();

                                    }
                                });

                                Advertencia.show();

                            }else {

                                AlertDialog.Builder Advertencia = new AlertDialog.Builder(Nota.this);

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

                            AlertDialog.Builder Advertencia = new AlertDialog.Builder(Nota.this);

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

                        AlertDialog.Builder Advertencia = new AlertDialog.Builder(Nota.this);

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
                params.put("id",String.valueOf(id));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void EliminarDataNota(){

        if(sessionCumple.isNotes()){

            listNotas.clear();
            listNotas = sessionCumple.readNotes();

            ArrayList<NotaClass> listNotasNew = new ArrayList<>();

            for (NotaClass item: listNotas){

                if(item.getId() != id){
                    listNotasNew.add(item);
                }

            }

            sessionCumple.createSessionNotes(listNotasNew);

        }

        onBackPressed();
    }

    private void inializarComponentes() {
        titulo = findViewById(R.id.titulo_nota);
        fecha = findViewById(R.id.fecha_nota);
        contenido = findViewById(R.id.contenido_nota);
        color = findViewById(R.id.encabezado_nota);
        fab = findViewById(R.id.fab_nota_editar);
    }

    @Override
    protected void onStart() {
        GetNotas();
        super.onStart();
    }

}
