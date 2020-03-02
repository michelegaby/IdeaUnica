package com.michele.ideaunica.ui.invitados;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

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

import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.R;

public class NuevoInvitado extends AppCompatActivity {

    //Componentes
    private TextView nombre;
    private TextView adulto;
    private TextView ninyo;
    private TextView celular;
    private Button guardar;
    private Spinner tipo;
    private CheckBox confirmar;

    private static int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_invitado);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setTitle("Nuevo Invitado");

        inicializarComponentes();
        Bundle parametros = this.getIntent().getExtras();
        ID=parametros.getInt("ID",0);
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
                                "CONFIRMADO");
                    }else {
                        Guardar(nombre.getText().toString().trim(),
                                adulto.getText().toString().trim(),
                                ninyo.getText().toString().trim(),
                                celular.getText().toString().trim(),
                                tipo.getSelectedItem().toString(),
                                "NOCONFIRMADO");
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
        adulto=findViewById(R.id.adulto_nuevo_invitado);
        ninyo=findViewById(R.id.ninyo_nuevo_invitado);
        celular=findViewById(R.id.celular_nuevo_invitado);
        guardar=findViewById(R.id.guardar_nuevo_invitado);
        tipo=findViewById(R.id.tipo_nuevo_invitado);
        confirmar=findViewById(R.id.confirmar_nuevo_invitado);
    }
    public void Guardar(final String nom,final String adulto, final String ninyo,final String celular, final String tipo,final String estadp){
        try {
            BDEvento objEvento= new BDEvento(getApplicationContext(),"bdEvento",null,1);
            SQLiteDatabase bd = objEvento.getWritableDatabase();
            if(bd!=null){
                bd.execSQL("insert into invitados values(?,'"+ID+"','"+nom+"',"+adulto+","+ninyo+",'"+celular+"','"+tipo+"','"+estadp+"')");
                msn("Se Guardo Correctamente");
                onBackPressed();
                Borrar();
            }
            bd.close();

        }catch (Exception E){
            msn("ERROR");
        }
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

