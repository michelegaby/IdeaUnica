package com.michele.ideaunica.ui.gastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.DhcpInfo;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MostrarGasto extends AppCompatActivity {

    //Componentes
    private TextView titulo;
    private TextView proveedor;
    private TextView dinero;
    private TextView formadepago;
    private TextView cuota;
    private TextView fecha;
    private TextView comentario;
    private CheckBox confirmar;

    //Complementos
    public static String ID;
    public static String id;
    public static String check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_gasto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setTitle("Ver");
        inicializaComponentes();

        Bundle parametros = this.getIntent().getExtras();

        ID = parametros.getString("ID");
        id = parametros.getString("id");

        check = "FALSE";

        InicializarDato();

        confirmar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(check.equals("TRUE"))
                {
                    if(!isChecked){
                        update("Sin Pagar");
                    }
                }else
                {
                    if(isChecked){
                        update("Pagado");
                    }
                }
            }
        });
    }

    public void InicializarDato(){
            try {

                BDEvento obj = new BDEvento(getApplicationContext(),"bdEvento",null,1);
                SQLiteDatabase bd = obj.getReadableDatabase();
                if(bd != null){
                    Cursor objCursor = bd.rawQuery("Select * from gastos where id = " + ID, null);
                    while (objCursor.moveToNext()) {
                        titulo.setText(objCursor.getString(2));
                        proveedor.setText(objCursor.getString(3));
                        formadepago.setText(objCursor.getString(6));

                        BDEvento obj2 = new BDEvento(getApplicationContext(),"bdEvento",null,1);
                        SQLiteDatabase bd2 = obj2.getReadableDatabase();
                        if(bd2 != null){
                            Cursor objCursor2 = bd.rawQuery("Select * from cuotas where idgasto = "+ ID, null);
                            int c = objCursor2.getCount();
                            while (objCursor2.moveToNext()){
                                if(objCursor2.getString(0).equals(id))
                                {

                                    dinero.setText(objCursor2.getString(4));
                                    cuota.setText(objCursor2.getString(1)+"/"+c);
                                    fecha.setText(objCursor2.getString(3));
                                    comentario.setText(objCursor2.getString(6));
                                    if(objCursor2.getString(5).equals("Pagado"))
                                    {
                                        confirmar.setChecked(true);
                                        check = "TRUE";
                                    }
                                    break;
                                }
                            }
                        }
                        bd2.close();
                    }
                }
                bd.close();
            }
            catch (Exception E){
                Toast.makeText(getApplicationContext(),E.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }

    }

    private void inicializaComponentes() {
        titulo = findViewById(R.id.titulo_mostrar_gasto);
        proveedor = findViewById(R.id.proveedor_mostrar_gasto);
        dinero = findViewById(R.id.dinero_mostrar_gasto);
        formadepago = findViewById(R.id.formadepago_mostrar_gasto);
        cuota = findViewById(R.id.cuotas_mostrar_gasto);
        fecha = findViewById(R.id.fecha_mostrar_gasto);
        comentario = findViewById(R.id.comentario_mostrar_gasto);
        confirmar = findViewById(R.id.confirmar_mostrar_invitado);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mn_eliminar_gasto:Eliminar();break;
            case R.id.mn_editar_gasto:Editar();break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Editar(){
        Intent i = new Intent(getApplicationContext(), EditarGasto.class);
        Bundle parametros = new Bundle();
        parametros.putString("ID", ID);
        parametros.putString("id", id);
        parametros.putString("titulo", titulo.getText().toString());
        parametros.putString("proveedor",proveedor.getText().toString());
        parametros.putString("fecha",fecha.getText().toString());
        parametros.putString("dinero",dinero.getText().toString());
        parametros.putString("cuota",cuota.getText().toString());
        parametros.putString("tipo",formadepago.getText().toString());
        parametros.putString("comentario",comentario.getText().toString());
        if(confirmar.isChecked())
            parametros.putString("ACCION","TRUE");
        else
            parametros.putString("ACCION","FALSE");
        i.putExtras(parametros);
        startActivity(i);

    }

    public void Eliminar(){
        AlertDialog.Builder Advertencia = new AlertDialog.Builder(this);
        Advertencia.setTitle("Eliminar");
        Advertencia.setMessage("Esta seguro de eliminar esta Gasto?");
        Advertencia.setCancelable(false);
        Advertencia.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EliminarGasto();
            }
        });
        Advertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        Advertencia.show();
    }

    public void EliminarGasto(){
        if(cuota.getText().toString().equals("1/1")){
            EliminarUnicoGasto();
        }
        else {
            AlertDialog.Builder Advertencia = new AlertDialog.Builder(this);
            Advertencia.setTitle("Eliminar");
            Advertencia.setMessage("Este gasto tiene más de una cuota. ¿Desea eliminar todas las cuotas?");
            Advertencia.setCancelable(false);
            Advertencia.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "Se cancelo acción", Toast.LENGTH_SHORT).show();
                }
            });
            Advertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EliminarBDGasto();
                }
            });
            Advertencia.setNegativeButton("No, Solo esta", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EliminarUnicoGasto();
                }
            });
            Advertencia.show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void EliminarUnicoGasto(){
        try {
            BDEvento objEvento = new BDEvento(getApplicationContext(),"bdEvento",null,1);
            SQLiteDatabase bd = objEvento.getWritableDatabase();
            if(bd != null){
                bd.execSQL("delete from cuotas where id = "+id);
                Verificar();
                onBackPressed();
            }
            bd.close();
        }catch (Exception E){
            Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();
        }
    }

    public void EliminarBDGasto(){
        try {
            BDEvento objEvento = new BDEvento(getApplicationContext(),"bdEvento",null,1);
            SQLiteDatabase bd = objEvento.getWritableDatabase();
            if(bd != null){
                bd.execSQL("delete from cuotas where idgasto = " + ID);
                Verificar();
                onBackPressed();
            }
            bd.close();
        }catch (Exception E){
            Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();
        }
    }

    private void update(String titl) {
        try {
            BDEvento obj = new BDEvento(getApplicationContext(),"bdEvento",null,1);
            SQLiteDatabase bd = obj.getReadableDatabase();
            if(bd != null){
                bd.execSQL("update cuotas set estado = '" + titl + "' where id = " + id);
            }
            bd.close();
            onBackPressed();

        }
        catch (Exception E){
            Toast.makeText(getApplicationContext(),E.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gasto,menu);
        return true;
    }

    public void Verificar(){
        try {
            BDEvento obj = new BDEvento(getApplicationContext(),"bdEvento",null,1);
            SQLiteDatabase bd = obj.getReadableDatabase();
            if(bd != null){
                Cursor objCursor = bd.rawQuery("Select * from cuotas where idgasto=" + ID, null);
                if(objCursor.getCount() <= 0){
                    try {
                        BDEvento objEvento2 = new BDEvento(getApplicationContext(),"bdEvento",null,1);
                        SQLiteDatabase bd2 = objEvento2.getWritableDatabase();
                        if(bd2 != null){
                            bd2.execSQL("delete from gastos where id = " + ID);
                        }
                        bd2.close();
                    }catch (Exception E){
                        Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();
                    }
                }
            }
            bd.close();

        }
        catch (Exception E){
            Toast.makeText(getApplicationContext(),E.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        InicializarDato();
        super.onStart();
    }
}
