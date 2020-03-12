package com.michele.ideaunica.ui.gastos;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.InputType;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ResumenFragment extends Fragment {

    View view;

    //Componentes
    private TextView presupuesto;
    private TextView APagar;
    private TextView Pago;
    private TextView Vencido;
    private TextView Actual;
    private TextView Final;
    private TextView Gastos;
    private CardView RESUMEN;
    private FloatingActionButton fab;
    public static int ID;

    public ResumenFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_resumen, container, false);
        inicializacionComponentes();
        Bundle parametros = getArguments();
        ID=parametros.getInt("ID",0);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(),NuevoGasto.class);
                Bundle parmetros= new Bundle();
                parmetros.putInt("ID",ID);
                intent.putExtras(parmetros);
                startActivity(intent);
            }
        });

        RESUMEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Presupuesto:");
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                input.setText(presupuesto.getText().toString());
                builder.setView(input);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CambiarProsupuesto(Double.valueOf(input.getText().toString()));
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        GenerarDatos();
        return view;
    }

    private void GenerarDatos() {
        try {

            BDEvento obj= new BDEvento(getContext(),"bdEvento",null,1);
            SQLiteDatabase bd= obj.getReadableDatabase();
            if(bd!=null){
                Cursor objCursor = bd.rawQuery("Select * from evento where id=" +ID, null);
                while (objCursor.moveToNext()) {
                    if(objCursor.getString(6)!= null && !objCursor.getString(6).isEmpty())
                    {
                        presupuesto.setText(objCursor.getString(6));
                    }
                }
                ControlDeGastos();
            }
            bd.close();
        }
        catch (Exception E){
            Toast.makeText(getContext(),E.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
    }

    private void ControlDeGastos(){
        try {
            BDEvento obj= new BDEvento(getContext(),"bdEvento",null,1);
            SQLiteDatabase bd= obj.getReadableDatabase();
            if(bd!=null){
                double apagar=0;
                double pago=0;
                double vencido=0;
                Cursor objCursor = bd.rawQuery("Select * from gastos where idevento=" + ID, null);
                while (objCursor.moveToNext()){
                        try {
                            BDEvento obj2= new BDEvento(getContext(),"bdEvento",null,1);
                            SQLiteDatabase bd2= obj2.getReadableDatabase();
                            if(bd2!=null){
                                Cursor objCursor2 = bd.rawQuery("Select * from cuotas where idgasto=" +
                                        objCursor.getInt(0), null);

                                Date d = new Date();
                                CharSequence s = DateFormat.format("dd/MM/yyyy", d.getTime());
                                SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                                Date date1= sdf.parse(s.toString());

                                while (objCursor2.moveToNext()){
                                    if(objCursor2.getString(5).equals("Sin Pagar")){
                                        Date date2=sdf.parse(objCursor2.getString(3));
                                        if(date1.before(date2) || date1.equals(date2))
                                        {
                                            apagar=apagar+Double.valueOf(objCursor2.getString(4));
                                        }
                                        else {
                                            vencido=vencido+Double.valueOf(objCursor2.getString(4));
                                        }
                                    }else{
                                        pago=pago+Double.valueOf(objCursor2.getString(4));
                                    }
                                }
                            }
                            bd2.close();

                        }
                        catch (Exception E){
                            Toast.makeText(getContext(),E.getMessage().toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                APagar.setText(String.valueOf(apagar));
                Pago.setText(String.valueOf(pago));
                Vencido.setText(String.valueOf(vencido));
                Gastos.setText(String.valueOf(apagar+pago));
                double p=Double.valueOf(presupuesto.getText().toString());
                Actual.setText(String.valueOf(p-pago));
                Final.setText(String.valueOf(p-pago-apagar));
            }
            bd.close();
        }
        catch (Exception E){
            Toast.makeText(getContext(),E.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    private void CambiarProsupuesto(Double money){
        try {
            BDEvento obj= new BDEvento(getContext(),"bdEvento",null,1);
            SQLiteDatabase bd= obj.getReadableDatabase();
            if(bd!=null){
                bd.execSQL("update evento set presupuesto='"+money.toString()+"'  where id="+ID);
                presupuesto.setText(money.toString());
            }
            bd.close();
            ControlDeGastos();
        }
        catch (Exception E){
            Toast.makeText(getContext(),E.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
    }
    private void inicializacionComponentes() {
        fab=view.findViewById(R.id.fab_fragment_gastos);
        presupuesto=view.findViewById(R.id.presupuesto_resumen);
        APagar=view.findViewById(R.id.apagar_resumen);
        Pago=view.findViewById(R.id.pago_resumen);
        Vencido=view.findViewById(R.id.vencido_resumen);
        Actual=view.findViewById(R.id.actual_resumen);
        Final=view.findViewById(R.id.final_resumen);
        Gastos=view.findViewById(R.id.gastos_resumen);
        RESUMEN=view.findViewById(R.id.cardview_resumen);
    }
    @Override
    public void onStart() {
        GenerarDatos();
        super.onStart();
    }
}
