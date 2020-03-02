package com.michele.ideaunica.ui.gastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NuevoGasto extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    //Componentes
    private TextView titulo;
    private TextView proveedor;
    private TextView dinero;
    private Spinner tipo;
    private TextView cuota;
    private TextView fecha;
    private TextView comentario;
    private LinearLayout linearLayout;

    public static int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_gasto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        InicializarComponentes();
        Bundle parametros = this.getIntent().getExtras();
        ID=parametros.getInt("ID",0);
        tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1)
                    linearLayout.setVisibility(View.VISIBLE);
                else
                    linearLayout.setVisibility(View.GONE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void InicializarComponentes() {
        titulo=findViewById(R.id.titulo_nuevo_gasto);
        proveedor=findViewById(R.id.proveedor_nuevo_gasto);
        dinero=findViewById(R.id.dinero_nuevo_gasto);
        tipo=findViewById(R.id.tipo_nuevo_gasto);
        cuota=findViewById(R.id.cuotas_nuevo_gasto);
        fecha=findViewById(R.id.fecha_nuevo_gasto);
        comentario=findViewById(R.id.comentario_nuevo_gasto);
        linearLayout=findViewById(R.id.tipo_linear_nuevo_gasto);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nuevo_gasto,menu);
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.mn_guardar_nuevo_gasto)
        {
            if(linearLayout.getVisibility()==View.VISIBLE)
            {
                if(!titulo.getText().toString().trim().equals("")
                && !proveedor.getText().toString().trim().equals("")
                && !dinero.getText().toString().trim().equals("")
                && !cuota.getText().toString().trim().equals("")
                && !fecha.getText().toString().trim().equals(""))
                {
                    GuardarCuota(titulo.getText().toString(),proveedor.getText().toString(),dinero.getText().toString(),cuota.getText().toString(),fecha.getText().toString(),comentario.getText().toString());
                }
                else
                    Toast.makeText(getApplicationContext(),"Por favor complete todos los campos.",Toast.LENGTH_LONG).show();

            }
            else
            {
                if(!titulo.getText().toString().trim().equals("")
                        && !proveedor.getText().toString().trim().equals("")
                        && !dinero.getText().toString().trim().equals(""))
                {
                    GuardarAlContado(titulo.getText().toString(),proveedor.getText().toString(),dinero.getText().toString(),comentario.getText().toString());
                }
                else
                    Toast.makeText(getApplicationContext(),"Por favor complete todos los campos.",Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void GuardarAlContado(final String titulo,final String proveedor,
                             final String dinero,final String comentario){
        try {
            BDEvento objEvento= new BDEvento(getApplicationContext(),"bdEvento",null,1);
            SQLiteDatabase bd = objEvento.getWritableDatabase();
            if(bd!=null){

                Date d = new Date();
                CharSequence s = DateFormat.format("d/MM/yyyy", d.getTime());
                int result=-1;

                ContentValues nuevo= new ContentValues();
                nuevo.put("idevento",ID);
                nuevo.put("titulo",titulo);
                nuevo.put("proveedor",proveedor);
                nuevo.put("dinero",dinero);
                nuevo.put("fecha",s.toString());
                nuevo.put("tipo","Al Contado");

                result= (int) bd.insert("gastos",null, nuevo);
                CrearCuotas(result,dinero,"1",s.toString(),comentario,"Pagado");
                msn("Se Guardo Correctamente");
                onBackPressed();
            }
            bd.close();

        }catch (Exception E){
            msn(E.getMessage());
        }
    }

    public void GuardarCuota(final String titulo,final String proveedor,
                                 final String dinero,final String cuota, final String fecha,final String comentario){
        try {
            BDEvento objEvento= new BDEvento(getApplicationContext(),"bdEvento",null,1);
            SQLiteDatabase bd = objEvento.getWritableDatabase();
            if(bd!=null){
                int result=-1;

                ContentValues nuevo= new ContentValues();
                nuevo.put("idevento",ID);
                nuevo.put("titulo",titulo);
                nuevo.put("proveedor",proveedor);
                nuevo.put("dinero",dinero);
                nuevo.put("fecha",fecha);
                nuevo.put("tipo","En Cuotas");

                result= (int) bd.insert("gastos",null, nuevo);
                CrearCuotas(result,dinero,cuota,fecha,comentario,"Sin Pagar");

            }
            bd.close();


        }catch (Exception E){
            msn("ERROR");
        }
    }

    private void CrearCuotas(final int result,final String dinero,final String cuota,final String fecha,final String comentario,final String estado) {
        try {
            double d=Double.valueOf(dinero)/Double.valueOf(cuota);

            for (int i= 1;i<=Integer.valueOf(cuota);i++)
            {
                BDEvento objEvento= new BDEvento(getApplicationContext(),"bdEvento",null,1);
                SQLiteDatabase bd = objEvento.getWritableDatabase();
                if(bd!=null){
                    int resulta=-1;
                    String dt = fecha;
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar c = Calendar.getInstance();
                        c.setTime(sdf.parse(dt));
                        c.add(Calendar.MONTH, i-1);
                        dt = sdf.format(c.getTime());

                    } catch (Exception e) {
                    }
                    ContentValues nuevo= new ContentValues();
                    nuevo.put("idgasto",result);
                    nuevo.put("numCuota",i);
                    nuevo.put("fecha",fecha);
                    nuevo.put("dinero",d);
                    nuevo.put("fecha",dt);
                    nuevo.put("estado",estado);//Pagado
                    nuevo.put("comentario",comentario);//Pagado
                    resulta= (int) bd.insert("cuotas",null, nuevo);
                }
                bd.close();
            }
            msn("Se Guardo Correctamente");
            onBackPressed();
        }catch (Exception E){
            msn(E.getMessage());
        }

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        fecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog= new DatePickerDialog(
                this,this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    public void msn(String mss){
        Toast.makeText(getApplicationContext(),mss,Toast.LENGTH_SHORT).show();
    }

}
