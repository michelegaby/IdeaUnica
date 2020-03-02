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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.R;

import java.util.Date;

public class EditarNota extends AppCompatActivity {

    private EditText titulo;
    private EditText contenido;
    private LinearLayout encabezado;


    private static int ID;
    private static String colores;
    private static String titulos;
    private static Spanned contenidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_nota);
        getSupportActionBar().setElevation(0);
        InicializarComponentes();
        Bundle parametros = this.getIntent().getExtras();
        ID=parametros.getInt("id",0);
        titulos=parametros.getString("titulo","Error");
        titulo.setText(titulos);
        contenidos= Html.fromHtml(parametros.getString("contenido","Error"));
        contenido.setText(contenidos);
        colores=parametros.getString("color");
        encabezado.setBackgroundColor(Color.parseColor("#"+colores));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#"+colores)));
        contenido.requestFocus();
    }

    private void InicializarComponentes() {
        titulo=findViewById(R.id.titulo_editar_nota);
        encabezado=findViewById(R.id.encabezado_editar_nota);
        contenido=findViewById(R.id.contenido_editar_nota);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_editar_nota,menu);
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
        update(titulo.getText().toString().trim(),Html.toHtml(contenido.getText()));
        contenido.setText(contenidos);
        titulo.setText(titulos);
        onBackPressed();
    }

    private void update(String titl, String conte) {
        try {
            BDEvento obj= new BDEvento(getApplicationContext(),"bdEvento",null,1);
            SQLiteDatabase bd= obj.getReadableDatabase();
            if(bd!=null){
                Date d = new Date();
                CharSequence s = DateFormat.format("MMMM d, yyyy ", d.getTime());
                bd.execSQL("update notas set titulo='"+titl+"', contenido='"+conte+"', color='"+colores+"', fecha='"+s.toString()+"' where id="+ID);
            }
            bd.close();

        }
        catch (Exception E){
            Toast.makeText(getApplicationContext(),E.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
    }

    public void ColorAzul(){
        encabezado.setBackgroundColor(Color.parseColor("#4C79FB"));
        colores="4C79FB";
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4C79FB")));

    }
    public void ColorVerde(){
        encabezado.setBackgroundColor(Color.parseColor("#48CC64"));
        colores="48CC64";
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#48CC64")));
    }

    public void ColorAmarillo(){
        encabezado.setBackgroundColor(Color.parseColor("#FDBB00"));
        colores="FDBB00";
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FDBB00")));
    }

    public void ColorLila(){
        encabezado.setBackgroundColor(Color.parseColor("#9F50E3"));
        colores="9F50E3";
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9F50E3")));
    }
    public void ColorCeleste(){
        encabezado.setBackgroundColor(Color.parseColor("#03A9F4"));
        colores="03A9F4";
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03A9F4")));
    }

    @Override
    public void onBackPressed() {
        if(!titulo.getText().toString().equals(titulos) || !contenido.getText().toString().equals(contenidos.toString())){
            AlertDialog.Builder Advertencia= new AlertDialog.Builder(this);
            Advertencia.setTitle("Advertencia");
            Advertencia.setMessage("Desea salir. Las modificaciones realizadas se perderan");
            Advertencia.setCancelable(false);
            Advertencia.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    contenido.setText(contenidos);
                    titulo.setText(titulos);
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

