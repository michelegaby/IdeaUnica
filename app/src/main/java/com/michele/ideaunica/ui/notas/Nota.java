package com.michele.ideaunica.ui.notas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.R;

public class Nota extends AppCompatActivity {

    //componentes
    private TextView titulo;
    private TextView fecha;
    private TextView contenido;
    private LinearLayout color;
    private FloatingActionButton fab;

    private static int ID;
    private static int id;
    private static String colores;
    private static String contenidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setElevation(0);
        inializarComponentes();
        Bundle parametros = this.getIntent().getExtras();
        ID=parametros.getInt("ID");
        id=parametros.getInt("id");

        Generar();
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


    public void Generar(){
        try {
            BDEvento obj= new BDEvento(getApplicationContext(),"bdEvento",null,1);
            SQLiteDatabase bd= obj.getReadableDatabase();
            if(bd!=null){
                Cursor objCursor = bd.rawQuery("Select * from notas where id=" + id, null);

                while (objCursor.moveToNext()){
                    titulo.setText(objCursor.getString(3));
                    fecha.setText(objCursor.getString(2)+" - Ultima Actualización");
                    contenidos=objCursor.getString(4);
                    contenido.setText(Html.fromHtml(contenidos));
                    colores=objCursor.getString(5);
                    color.setBackgroundColor(Color.parseColor("#"+colores));
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#"+colores)));
                }

            }
            bd.close();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getApplicationContext(),EditarNota.class);
                    Bundle parametros = new Bundle();
                    parametros.putInt("id",id);
                    parametros.putString("titulo",titulo.getText().toString());
                    parametros.putString("color",colores);
                    parametros.putString("contenido",contenidos);
                    intent.putExtras(parametros);
                    startActivity(intent);
                }
            });
        }
        catch (Exception E){
            Toast.makeText(getApplicationContext(),E.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
    }

    private void Eliminar() {
        AlertDialog.Builder Advertencia= new AlertDialog.Builder(this);
        Advertencia.setTitle("Eliminar");
        Advertencia.setMessage("Esta seguro de eliminar esta nota?");
        Advertencia.setCancelable(false);
        Advertencia.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EliminarNota();
                onBackPressed();
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
        try {
            BDEvento objEvento= new BDEvento(getApplicationContext(),"bdEvento",null,1);
            SQLiteDatabase bd = objEvento.getWritableDatabase();
            if(bd!=null){
                bd.execSQL("delete from notas where id="+id);
                onBackPressed();
            }
            bd.close();
        }catch (Exception E){
            Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();
        }
    }

    private void inializarComponentes() {
        titulo=findViewById(R.id.titulo_nota);
        fecha=findViewById(R.id.fecha_nota);
        contenido=findViewById(R.id.contenido_nota);
        color=findViewById(R.id.encabezado_nota);
        fab=findViewById(R.id.fab_nota_editar);
    }

    @Override
    protected void onStart() {
        Generar();
        super.onStart();
    }

}
