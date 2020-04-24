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

public class EditarInvitado extends AppCompatActivity {

    //Componentes
    private TextView nombre;
    private TextView adulto;
    private TextView ninyo;
    private TextView celular;
    private Button guardar;
    private Spinner tipo;
    private CheckBox confirmar;

    //Complemento
    private static int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_invitado);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setTitle("Editar Invitado");
        inicializarComponentes();
        Bundle parametros = this.getIntent().getExtras();

        ID = parametros.getInt("ID",0);

        nombre.setText(parametros.getString("nom","a"));
        adulto.setText(parametros.getString("adulto","a"));
        ninyo.setText(parametros.getString("nin","a"));
        celular.setText(parametros.getString("cel","a"));

        String c = parametros.getString("confirmar","ACONFIRMAR");
        if(c.equals("CONFIRMADO")){
            confirmar.setChecked(true);
        }

        //Funcionalidad para guardar modificacion
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombre.getText().toString().trim().equals("") ||
                        adulto.getText().toString().trim().equals("") ||
                        ninyo.getText().toString().trim().equals("") ||
                        celular.getText().toString().trim().equals(""))
                {
                    msn("Complete todos los campos por favor");
                }else
                {
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
        try {
            BDEvento objEvento = new BDEvento(getApplicationContext(),"bdEvento",null,1);
            SQLiteDatabase bd = objEvento.getWritableDatabase();
            if(bd != null){
                bd.execSQL("update invitados set nombre = '" + nom + "', adultos = " + adulto + ", ninos = " + ninyo + ", celular = '" + celular + "', tipo = '" + tipo + "',estado = '" + estado + "' where id='" + ID + "'");
                msn("Se guardo correctamente");
                onBackPressed();
            }
            bd.close();

        }catch (Exception E){
            msn("ERROR");
        }
    }

    public void msn(String mss){
        Toast.makeText(getApplicationContext(),mss,Toast.LENGTH_SHORT).show();
    }
}
