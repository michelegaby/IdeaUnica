package com.michele.ideaunica.ui.gastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.R;

import java.util.Calendar;

public class EditarGasto extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    //Componentes
    private EditText titulo;
    private EditText proveedor;
    private EditText dinero;
    private TextView formadepago;
    private TextView cuota;
    private EditText fecha;
    private EditText comentario;
    private CheckBox confirmar;

    //Complementos
    public static String ID;
    public static String id;
    public static String check;
    public static String tit;
    public static String pro;
    public static String di;
    public static String form;
    public static String cuo;
    public static String fec;
    public static String com;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_gasto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorblanco), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setTitle("Editar Gasto");
        inicializaComponentes();
        Bundle parametros = this.getIntent().getExtras();

        ID = parametros.getString("ID");
        id = parametros.getString("id");

        titulo.setText(parametros.getString("titulo"));
        tit = parametros.getString("titulo");

        proveedor.setText(parametros.getString("proveedor"));
        pro = parametros.getString("proveedor");

        dinero.setText(parametros.getString("dinero"));
        di = parametros.getString("dinero");

        formadepago.setText(parametros.getString("tipo"));
        form = parametros.getString("tipo");

        cuota.setText(parametros.getString("cuota"));
        cuo = parametros.getString("cuota");

        fecha.setText(parametros.getString("fecha"));
        fec = parametros.getString("fecha");

        comentario.setText(parametros.getString("comentario"));
        com = parametros.getString("comentario");

        check = parametros.getString("ACCION");
        if(parametros.getString("ACCION").equals("TRUE"))
        {
            confirmar.setChecked(true);
        }

        //Funcionalidad de click fecha
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void inicializaComponentes() {
        titulo = findViewById(R.id.titulo_editar_gasto);
        proveedor = findViewById(R.id.proveedor_editar_gasto);
        dinero = findViewById(R.id.dinero_editar_gasto);
        formadepago = findViewById(R.id.formadepago_editar_gasto);
        cuota = findViewById(R.id.cuotas_editar_gasto);
        fecha = findViewById(R.id.fecha_editar_gasto);
        comentario = findViewById(R.id.comentario_editar_gasto);
        confirmar = findViewById(R.id.confirmar_editar_gasto);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_guardar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mn_guardar:Guardar();break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Guardar(){

        if(!titulo.getText().toString().equals(tit) ||
                !proveedor.getText().toString().equals(pro)){
            GuardarInicio(titulo.getText().toString(),proveedor.getText().toString());
            tit = titulo.getText().toString();
            pro = proveedor.getText().toString();
        }
        if(!dinero.getText().toString().equals(di) ||
                !fecha.getText().toString().equals(fec) ||
                !comentario.getText().toString().equals(com)){
            GuardarFinal(dinero.getText().toString(),fecha.getText().toString(),comentario.getText().toString());
            di = dinero.getText().toString();
            fec = fecha.getText().toString();
            com = comentario.getText().toString();
        }

        if(!confirmar.isChecked()){
            update("Sin Pagar");
        }else
        {
            update("Pagado");
        }
        onBackPressed();
    }

    private void GuardarFinal(String d, String f, String c) {
        try {
            BDEvento obj = new BDEvento(getApplicationContext(),"bdEvento",null,1);
            SQLiteDatabase bd = obj.getReadableDatabase();
            if(bd != null){
                    bd.execSQL("update cuotas set fecha = '"+f+"', dinero = '"+d+"', comentario = '"+c+"'  where id = "+id);
            }
            bd.close();

        }
        catch (Exception E){
            Toast.makeText(getApplicationContext(),E.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void update(String est) {
        try {
            BDEvento obj = new BDEvento(getApplicationContext(),"bdEvento",null,1);
            SQLiteDatabase bd = obj.getReadableDatabase();
            if(bd != null){
                bd.execSQL("update cuotas set estado = '"+est+"' where id = "+id);
            }
            bd.close();

        }
        catch (Exception E){
            Toast.makeText(getApplicationContext(),E.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void GuardarInicio(String t, String p) {
        try {
            BDEvento obj = new BDEvento(getApplicationContext(),"bdEvento",null,1);
            SQLiteDatabase bd = obj.getReadableDatabase();
            if(bd != null){
                bd.execSQL("update gastos set titulo = '"+t+"', proveedor = '"+p+"' where id = "+ID);
            }
            bd.close();
        }
        catch (Exception E){
            Toast.makeText(getApplicationContext(),E.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if(!titulo.getText().toString().equals(tit) ||
                !proveedor.getText().toString().equals(pro) ||
                !dinero.getText().toString().equals(di) ||
                !fecha.getText().toString().equals(fec) ||
                !comentario.getText().toString().equals(com))
        {
            AlertDialog.Builder Advertencia = new AlertDialog.Builder(this);
            Advertencia.setTitle("Salir");
            Advertencia.setMessage("Esta seguro de que desee salir sin guardar las modificaciones");
            Advertencia.setCancelable(false);
            Advertencia.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditarGasto.super.onBackPressed();
                }
            });
            Advertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            Advertencia.show();
        }
        else {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        fecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}
