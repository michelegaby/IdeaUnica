package com.michele.ideaunica.ui.invitados;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.michele.ideaunica.ui.notas.NuevaNota;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class NuevoInvitado extends AppCompatActivity {

    SessionCumple sessionCumple;

    //Componentes
    private TextView nombre;
    private TextView adulto;
    private TextView ninyo;
    private TextView celular;
    private Button guardar;
    private Spinner tipo;
    private CheckBox confirmar;

    android.app.AlertDialog mDialog;

    private static  String URL = "https://www.ideaunicabolivia.com/apps/fiesta/NuevoInvitado.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_invitado);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorblanco), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setTitle("Nuevo Invitado");

        inicializarComponentes();

        sessionCumple = new SessionCumple(getApplicationContext());

        mDialog = new SpotsDialog.Builder().setContext(NuevoInvitado.this).setMessage("Espera un momento por favor").build();


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombre.getText().toString().trim().equals("") ||
                        celular.getText().toString().trim().equals(""))
                {
                    msn("Complete todos los campos por favor");
                }else
                {
                    if(adulto.getText().toString().trim().equals(""))
                    {
                        adulto.setText("0");
                    }

                    if(ninyo.getText().toString().trim().equals(""))
                    {
                        ninyo.setText("0");
                    }

                    if(confirmar.isChecked()){
                        Guardar(nombre.getText().toString().trim(),
                                adulto.getText().toString().trim(),
                                ninyo.getText().toString().trim(),
                                celular.getText().toString().trim(),
                                tipo.getSelectedItem().toString(),
                                "1");
                    }else {
                        Guardar(nombre.getText().toString().trim(),
                                adulto.getText().toString().trim(),
                                ninyo.getText().toString().trim(),
                                celular.getText().toString().trim(),
                                tipo.getSelectedItem().toString(),
                                "0");
                    }
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void inicializarComponentes() {
        nombre = findViewById(R.id.nombre_nuevo_invitado);
        adulto = findViewById(R.id.adulto_nuevo_invitado);
        ninyo = findViewById(R.id.ninyo_nuevo_invitado);
        celular = findViewById(R.id.celular_nuevo_invitado);
        guardar = findViewById(R.id.guardar_nuevo_invitado);
        tipo = findViewById(R.id.tipo_nuevo_invitado);
        confirmar = findViewById(R.id.confirmar_nuevo_invitado);
    }

    public void Guardar(final String nom,final String adulto, final String ninyo,final String celular, final String tipo,final String estadp){

        mDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            Boolean isStatus = jsonObject.getBoolean("estado");
                            if(!isStatus){

                                AlertDialog.Builder Advertencia = new AlertDialog.Builder(NuevoInvitado.this);

                                Advertencia.setTitle("Error");
                                Advertencia.setMessage("No se guardo, lo sentimos mucho");
                                Advertencia.setCancelable(false);

                                Advertencia.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                Advertencia.show();
                            }else{

                                String idnew = jsonObject.getString("id");

                                ArrayList<InvitadosClass> listInvitadosList = new ArrayList<>();

                                listInvitadosList = sessionCumple.readInvited();

                                listInvitadosList.add(new InvitadosClass(Integer.valueOf(idnew),sessionCumple.getId(),nom,Integer.valueOf(adulto),Integer.valueOf(ninyo),celular,tipo,estadp));

                                sessionCumple.createSessionInvited(listInvitadosList);

                                AlertDialog.Builder Advertencia = new AlertDialog.Builder(NuevoInvitado.this);

                                Advertencia.setTitle("Guardado");
                                Advertencia.setMessage("Se guardo correctamente.");
                                Advertencia.setCancelable(false);

                                Advertencia.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        onBackPressed();
                                    }
                                });

                                Advertencia.show();
                            }
                            mDialog.dismiss();

                        } catch (Exception e) {
                            mDialog.dismiss();

                            AlertDialog.Builder Advertencia = new AlertDialog.Builder(NuevoInvitado.this);

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

                        AlertDialog.Builder Advertencia = new AlertDialog.Builder(NuevoInvitado.this);

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
                params.put("idevento",sessionCumple.getId());
                params.put("nombre",String.valueOf(nom));
                params.put("adulto",String.valueOf(adulto));
                params.put("nino",String.valueOf(ninyo));
                params.put("celular",String.valueOf(celular));
                params.put("tipo",String.valueOf(tipo));
                params.put("estado",String.valueOf(estadp));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(NuevoInvitado.this);
        requestQueue.add(stringRequest);
    }
    public void msn(String mss){
        Toast.makeText(getApplicationContext(),mss,Toast.LENGTH_SHORT).show();
    }
    public void Borrar(){
        nombre.setText("");
        adulto.setText("");
        ninyo.setText("");
        celular.setText("");
    }
}

