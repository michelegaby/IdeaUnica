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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class EditarInvitado extends AppCompatActivity {

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

    private static  String URL = "https://www.ideaunicabolivia.com/apps/fiesta/UpdateInvitado.php";


    //Complemento
    private static int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_invitado);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorblanco), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setTitle("Editar Invitado");
        inicializarComponentes();
        Bundle parametros = this.getIntent().getExtras();

        sessionCumple = new SessionCumple(getApplicationContext());

        mDialog = new SpotsDialog.Builder().setContext(EditarInvitado.this).setMessage("Espera un momento por favor").build();

        ID = parametros.getInt("ID",0);

        nombre.setText(parametros.getString("nom","a"));
        adulto.setText(parametros.getString("adulto","a"));
        ninyo.setText(parametros.getString("nin","a"));
        celular.setText(parametros.getString("cel","a"));

        String c = parametros.getString("confirmar","0");

        if(c.equals("1")){
            confirmar.setChecked(true);
        }

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

    private void inicializarComponentes() {
        nombre = findViewById(R.id.nombre_editar_invitado);
        adulto = findViewById(R.id.adulto_editar_invitado);
        ninyo = findViewById(R.id.ninyo_editar_invitado);
        celular = findViewById(R.id.celular_editar_invitado);
        guardar = findViewById(R.id.guardar_editar_invitado);
        tipo = findViewById(R.id.tipo_editar_invitado);
        confirmar = findViewById(R.id.confirmar_editar_invitado);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void Guardar(final String nom,final String adulto, final String ninyo,final String celular, final String tipo, final String estado){

        mDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            Boolean isStatus = jsonObject.getBoolean("estado");
                            if(!isStatus){

                                AlertDialog.Builder Advertencia = new AlertDialog.Builder(EditarInvitado.this);

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

                                ArrayList<InvitadosClass> listInvitadosList = new ArrayList<>();

                                listInvitadosList = sessionCumple.readInvited();

                                for (InvitadosClass item: listInvitadosList){
                                    if(item.getID() == ID){
                                        item.setNombre(nom);
                                        item.setNinyos(Integer.valueOf(ninyo));
                                        item.setAdultos(Integer.valueOf(adulto));
                                        item.setNinyos(Integer.valueOf(ninyo));
                                        item.setCelular(celular);
                                        item.setTipo(tipo);
                                        item.setEstado(estado);
                                        break;
                                    }
                                }
                                sessionCumple.createSessionInvited(listInvitadosList);

                                AlertDialog.Builder Advertencia = new AlertDialog.Builder(EditarInvitado.this);

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

                            AlertDialog.Builder Advertencia = new AlertDialog.Builder(EditarInvitado.this);

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

                        AlertDialog.Builder Advertencia = new AlertDialog.Builder(EditarInvitado.this);

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
                params.put("nombre",String.valueOf(nom));
                params.put("adultos",String.valueOf(adulto));
                params.put("ninos",String.valueOf(ninyo));
                params.put("celular",String.valueOf(celular));
                params.put("tipo",String.valueOf(tipo));
                params.put("estado",String.valueOf(estado));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(EditarInvitado.this);
        requestQueue.add(stringRequest);
    }

    public void msn(String mss){
        Toast.makeText(getApplicationContext(),mss,Toast.LENGTH_SHORT).show();
    }
}
