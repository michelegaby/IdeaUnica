package com.michele.ideaunica.ui.notas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.R;

import java.util.Date;

public class NuevaNota extends AppCompatActivity {

    //Componentes
    private EditText titulo;
    private EditText contenido;
    private LinearLayout encabezado;

    //Complemento
    private static int ID;
    private static String colores;

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

        try {
            Bundle parametros = this.getIntent().getExtras();
            ID=parametros.getInt("ID",0);
            titulo.setText(parametros.getString("titulo",""));
            contenido.setText(Html.fromHtml(parametros.getString("contenido","")));
        }catch (Exception e){
            e.printStackTrace();
        }

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
            case R.id.mn_guardar_nueva_nota:Guardar();break;
            case R.id.mn_amarillo_nueva_nota:Color("FDBB00");break;
            case R.id.mn_azul_nueva_nota:Color("4C79FB");break;
            case R.id.mn_celeste_nueva_nota:Color("03A9F4");break;
            case R.id.mn_lila_nueva_nota:Color("9F50E3");break;
            case R.id.mn_verde_nueva_nota:Color("48CC64");break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Guardar() {
        try {
            if(!contenido.getText().toString().trim().equals(""))
            {
                BDEvento objEvento = new BDEvento(getApplicationContext(),"bdEvento",null,1);
                SQLiteDatabase bd = objEvento.getWritableDatabase();
                if(bd != null){
                    Date d = new Date();
                    CharSequence s = DateFormat.format("MMMM d, yyyy ", d.getTime());
                    String con = Html.toHtml(contenido.getText());
                    String tl = titulo.getText().toString().trim();
                    if(tl.equals(""))
                    {
                        tl = "Titulo";
                    }
                    bd.execSQL("insert into notas values(?," + ID + ",'" + s.toString() + "','" + tl + "','" + con + "','" + colores + "')");
                    contenido.setText("");
                    titulo.setText("");
                    onBackPressed();
                }
                bd.close();

            }
            else
            {
                Toast.makeText(getApplicationContext(),"No existe contenido para guardar",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception E){
            Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();
        }
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
            Advertencia.setMessage("Desea salir. Las modificaciones realizadas se perderan");
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
}
