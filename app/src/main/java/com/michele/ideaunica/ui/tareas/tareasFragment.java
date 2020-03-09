package com.michele.ideaunica.ui.tareas;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.R;
import com.michele.ideaunica.ui.notas.AdaptadorNota;
import com.michele.ideaunica.ui.notas.NotaClass;

import java.util.ArrayList;
import java.util.Date;

public class tareasFragment extends Fragment {

    View view;
    private RecyclerView rv_uno;
    private LinearLayout ln_uno;
    private Button btn_uno;

    private RecyclerView rv_dos;
    private LinearLayout ln_dos;
    private Button btn_dos;
    
    private RecyclerView rv_tres;
    private LinearLayout ln_tres;
    private Button btn_tres;

    private RecyclerView rv_cuatro;
    private LinearLayout ln_cuatro;
    private Button btn_cuatro;
    
    private RecyclerView rv_cinco;
    private LinearLayout ln_cinco;
    private Button btn_cinco;

    private RecyclerView rv_seis;
    private LinearLayout ln_seis;
    private Button btn_seis;


    //Complementos
    AdaptadorTarea adaptadorTarea1;
    private ArrayList<TareaClass> listTareas1 = new ArrayList<>();
    AdaptadorTarea adaptadorTarea2;
    private ArrayList<TareaClass> listTareas2 = new ArrayList<>();
    AdaptadorTarea adaptadorTarea3;
    private ArrayList<TareaClass> listTareas3 = new ArrayList<>();
    AdaptadorTarea adaptadorTarea4;
    private ArrayList<TareaClass> listTareas4 = new ArrayList<>();
    AdaptadorTarea adaptadorTarea5;
    private ArrayList<TareaClass> listTareas5 = new ArrayList<>();
    AdaptadorTarea adaptadorTarea6;
    private ArrayList<TareaClass> listTareas6 = new ArrayList<>();

    private static int ID;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_tareas,container,false);
        InicializarComponentes();
        Bundle parametros = getActivity().getIntent().getExtras();
        ID=parametros.getInt("ID",0);
        Onclick();
        GenerarDatosPrimero();
        GenerarDatosSegundo();
        GenerarDatosTercero();
        GenerarDatosCuarto();
        GenerarDatosCinto();
        GenerarDatosSeis();
        return view;
    }
    private void InicializarComponentes() {
        rv_uno=view.findViewById(R.id.rv_seis_meses_tarea);
        ln_uno=view.findViewById(R.id.ln_seis_meses_tarea);
        btn_uno=view.findViewById(R.id.btn_seis_meses_tarea);

        rv_dos=view.findViewById(R.id.rv_cinco_meses_tarea);
        ln_dos=view.findViewById(R.id.ln_cinco_meses_tarea);
        btn_dos=view.findViewById(R.id.btn_cinco_meses_tarea);

        rv_tres=view.findViewById(R.id.rv_un_mes_tarea);
        ln_tres=view.findViewById(R.id.ln_un_mes_tarea);
        btn_tres=view.findViewById(R.id.btn_un_mes_tarea);

        rv_cuatro=view.findViewById(R.id.rv_una_semana_tarea);
        ln_cuatro=view.findViewById(R.id.ln_una_semana_tarea);
        btn_cuatro=view.findViewById(R.id.btn_una_semana_tarea);

        rv_cinco=view.findViewById(R.id.rv_dos_dias_tarea);
        ln_cinco=view.findViewById(R.id.ln_dos_dias_tarea);
        btn_cinco=view.findViewById(R.id.btn_dos_dias_tarea);

        rv_seis=view.findViewById(R.id.rv_dia_del_evento_tarea);
        ln_seis=view.findViewById(R.id.ln_dia_del_evento_tarea);
        btn_seis=view.findViewById(R.id.btn_dia_del_evento_tarea);
    }

    /*UNO*/
    private void GenerarDatosPrimero() {
        try {
            BDEvento obj= new BDEvento(getContext(),"bdEvento",null,1);
            SQLiteDatabase bd= obj.getReadableDatabase();
            if(bd!=null){
                Cursor objCursor = bd.rawQuery("Select * from tarea where idevento=" + ID+" and mes=1 order by estado asc", null);

                while (objCursor.moveToNext()){
                    listTareas1.add(new TareaClass(objCursor.getInt(0),objCursor.getString(3),objCursor.getInt(4)));
                }
                if(listTareas1.size()==0)
                {
                    Llenar1();
                }
                adaptadorTarea1 = new AdaptadorTarea(getContext(), listTareas1);
                rv_uno.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_uno.setAdapter(adaptadorTarea1);
                adaptadorTarea1.setOnItemClickListener(new AdaptadorTarea.OnItemClickListener() {
                    @Override
                    public void onItenClick(int position) {
                        UpdateTarea1(position);
                    }
                });
            }
            bd.close();

        }
        catch (Exception E){
            Toast.makeText(getContext(),E.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void Llenar1(){

        try {
            BDEvento objEvento= new BDEvento(getContext(),"bdEvento",null,1);
            SQLiteDatabase bd = objEvento.getWritableDatabase();
            if(bd!=null){
                Resources res = getResources();
                String[] messeis = res.getStringArray(R.array.tarea_seis_meses);
                for (String t: messeis) {
                    bd.execSQL("insert into tarea values(?,"+ID+",1,'"+t+"',1)");
                }
            }
            bd.close();
            GenerarDatosPrimero();
        }catch (Exception E){
            Toast.makeText(getContext(),"ERROR",Toast.LENGTH_LONG).show();
        }
    }

    /*DOS*/
    private void GenerarDatosSegundo() {
        try {
            BDEvento obj= new BDEvento(getContext(),"bdEvento",null,1);
            SQLiteDatabase bd= obj.getReadableDatabase();
            if(bd!=null){
                Cursor objCursor = bd.rawQuery("Select * from tarea where idevento=" + ID+" and mes=2 order by estado asc", null);

                while (objCursor.moveToNext()){
                    listTareas2.add(new TareaClass(objCursor.getInt(0),objCursor.getString(3),objCursor.getInt(4)));
                }
                if(listTareas2.size()==0)
                {
                    Llenar2();
                }

                adaptadorTarea2=new AdaptadorTarea(getContext(),listTareas2);
                rv_dos.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_dos.setAdapter(adaptadorTarea2);
                adaptadorTarea2.setOnItemClickListener(new AdaptadorTarea.OnItemClickListener() {
                    @Override
                    public void onItenClick(int position) {
                        UpdateTarea2(position);
                    }
                });
                rv_dos.setVisibility(View.VISIBLE);
            }
            bd.close();

        }
        catch (Exception E){
            Toast.makeText(getContext(),E.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void Llenar2(){

        try {
            BDEvento objEvento= new BDEvento(getContext(),"bdEvento",null,1);
            SQLiteDatabase bd = objEvento.getWritableDatabase();
            if(bd!=null){
                Resources res = getResources();
                String[] mescinco = res.getStringArray(R.array.tarea_cinco_meses);
                for (String t: mescinco) {
                    bd.execSQL("insert into tarea values(?,"+ID+",2,'"+t+"',1)");
                }
            }
            bd.close();
            GenerarDatosSegundo();
        }catch (Exception E){
            Toast.makeText(getContext(),"ERROR",Toast.LENGTH_LONG).show();
        }
    }

    /*TRES*/
    private void GenerarDatosTercero() {
        try {
            BDEvento obj= new BDEvento(getContext(),"bdEvento",null,1);
            SQLiteDatabase bd= obj.getReadableDatabase();
            if(bd!=null){
                Cursor objCursor = bd.rawQuery("Select * from tarea where idevento=" + ID+" and mes=3 order by estado asc", null);

                while (objCursor.moveToNext()){
                    listTareas3.add(new TareaClass(objCursor.getInt(0),objCursor.getString(3),objCursor.getInt(4)));
                }
                if(listTareas3.size()==0)
                {
                    Llenar3();
                }

                adaptadorTarea3=new AdaptadorTarea(getContext(),listTareas3);
                rv_tres.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_tres.setAdapter(adaptadorTarea3);
                adaptadorTarea3.setOnItemClickListener(new AdaptadorTarea.OnItemClickListener() {
                    @Override
                    public void onItenClick(int position) {
                        UpdateTarea3(position);
                    }
                });
                rv_tres.setVisibility(View.VISIBLE);
            }
            bd.close();

        }
        catch (Exception E){
            Toast.makeText(getContext(),E.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void Llenar3(){

        try {
            BDEvento objEvento= new BDEvento(getContext(),"bdEvento",null,1);
            SQLiteDatabase bd = objEvento.getWritableDatabase();
            if(bd!=null){
                Resources res = getResources();
                String[] diadelevento = res.getStringArray(R.array.tarea_un_mes);
                for (String t: diadelevento) {
                    bd.execSQL("insert into tarea values(?,"+ID+",3,'"+t+"',1)");
                }
            }
            bd.close();
            GenerarDatosTercero();
        }catch (Exception E){
            Toast.makeText(getContext(),"ERROR",Toast.LENGTH_LONG).show();
        }
    }

    /*CUATRO*/
    private void GenerarDatosCuarto() {
        try {
            BDEvento obj= new BDEvento(getContext(),"bdEvento",null,1);
            SQLiteDatabase bd= obj.getReadableDatabase();
            if(bd!=null){
                Cursor objCursor = bd.rawQuery("Select * from tarea where idevento=" + ID+" and mes=4 order by estado asc", null);

                while (objCursor.moveToNext()){
                    listTareas4.add(new TareaClass(objCursor.getInt(0),objCursor.getString(3),objCursor.getInt(4)));
                }
                if(listTareas4.size()==0)
                {
                    Llenar4();
                }

                adaptadorTarea4=new AdaptadorTarea(getContext(),listTareas4);
                rv_cuatro.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_cuatro.setAdapter(adaptadorTarea4);
                adaptadorTarea4.setOnItemClickListener(new AdaptadorTarea.OnItemClickListener() {
                    @Override
                    public void onItenClick(int position) {
                        UpdateTarea4(position);
                    }
                });
                rv_cuatro.setVisibility(View.VISIBLE);
            }
            bd.close();

        }
        catch (Exception E){
            Toast.makeText(getContext(),E.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void Llenar4(){

        try {
            BDEvento objEvento= new BDEvento(getContext(),"bdEvento",null,1);
            SQLiteDatabase bd = objEvento.getWritableDatabase();
            if(bd!=null){
                Resources res = getResources();
                String[] unmes = res.getStringArray(R.array.tarea_una_semana);
                for (String t: unmes) {
                    bd.execSQL("insert into tarea values(?,"+ID+",4,'"+t+"',1)");
                }
            }
            bd.close();
            GenerarDatosCuarto();
        }catch (Exception E){
            Toast.makeText(getContext(),"ERROR",Toast.LENGTH_LONG).show();
        }
    }

    /*CINCO*/
    private void GenerarDatosCinto() {
        try {
            BDEvento obj= new BDEvento(getContext(),"bdEvento",null,1);
            SQLiteDatabase bd= obj.getReadableDatabase();
            if(bd!=null){
                Cursor objCursor = bd.rawQuery("Select * from tarea where idevento=" + ID+" and mes=5 order by estado asc", null);

                while (objCursor.moveToNext()){
                    listTareas5.add(new TareaClass(objCursor.getInt(0),objCursor.getString(3),objCursor.getInt(4)));
                }
                if(listTareas5.size()==0)
                {
                    Llenar5();
                }

                adaptadorTarea5=new AdaptadorTarea(getContext(),listTareas5);
                rv_cinco.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_cinco.setAdapter(adaptadorTarea5);
                adaptadorTarea5.setOnItemClickListener(new AdaptadorTarea.OnItemClickListener() {
                    @Override
                    public void onItenClick(int position) {
                        UpdateTarea5(position);
                    }
                });
                rv_cinco.setVisibility(View.VISIBLE);
            }
            bd.close();

        }
        catch (Exception E){
            Toast.makeText(getContext(),E.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void Llenar5(){

        try {
            BDEvento objEvento= new BDEvento(getContext(),"bdEvento",null,1);
            SQLiteDatabase bd = objEvento.getWritableDatabase();
            if(bd!=null){
                Resources res = getResources();
                String[] unasemana = res.getStringArray(R.array.tarea_dos_dias);
                for (String t: unasemana) {
                    bd.execSQL("insert into tarea values(?,"+ID+",5,'"+t+"',1)");
                }
            }
            bd.close();
            GenerarDatosCinto();
        }catch (Exception E){
            Toast.makeText(getContext(),"ERROR",Toast.LENGTH_LONG).show();
        }
    }

    /*SEIS*/
    private void GenerarDatosSeis() {
        try {
            BDEvento obj= new BDEvento(getContext(),"bdEvento",null,1);
            SQLiteDatabase bd= obj.getReadableDatabase();
            if(bd!=null){
                Cursor objCursor = bd.rawQuery("Select * from tarea where idevento=" + ID+" and mes=6 order by estado asc", null);

                while (objCursor.moveToNext()){
                    listTareas6.add(new TareaClass(objCursor.getInt(0),objCursor.getString(3),objCursor.getInt(4)));
                }
                if(listTareas6.size()==0)
                {
                    Llenar6();
                }

                adaptadorTarea6=new AdaptadorTarea(getContext(),listTareas6);
                rv_seis.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_seis.setAdapter(adaptadorTarea6);
                adaptadorTarea6.setOnItemClickListener(new AdaptadorTarea.OnItemClickListener() {
                    @Override
                    public void onItenClick(int position) {
                        UpdateTarea6(position);
                    }
                });
                rv_seis.setVisibility(View.VISIBLE);
            }
            bd.close();

        }
        catch (Exception E){
            Toast.makeText(getContext(),E.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void Llenar6(){

        try {
            BDEvento objEvento= new BDEvento(getContext(),"bdEvento",null,1);
            SQLiteDatabase bd = objEvento.getWritableDatabase();
            if(bd!=null){
                Resources res = getResources();
                String[] unasemana = res.getStringArray(R.array.tarea_dia);
                for (String t: unasemana) {
                    bd.execSQL("insert into tarea values(?,"+ID+",6,'"+t+"',1)");
                }
            }
            bd.close();
            GenerarDatosSeis();
        }catch (Exception E){
            Toast.makeText(getContext(),"ERROR",Toast.LENGTH_LONG).show();
        }
    }


    private void Onclick() {
        btn_uno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ln_uno.getVisibility()==View.GONE){
                    ln_uno.setVisibility(View.VISIBLE);
                    btn_uno.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_up,0);

                }
                else{
                    ln_uno.setVisibility(View.GONE);
                    btn_uno.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_down,0);
                }
            }
        });

        btn_dos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ln_dos.getVisibility()==View.GONE){
                    ln_dos.setVisibility(View.VISIBLE);
                    btn_dos.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_up,0);

                }
                else{
                    ln_dos.setVisibility(View.GONE);
                    btn_dos.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_down,0);
                }
            }
        });

        btn_tres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ln_tres.getVisibility()==View.GONE){
                    ln_tres.setVisibility(View.VISIBLE);
                    btn_tres.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_up,0);

                }
                else{
                    ln_tres.setVisibility(View.GONE);
                    btn_tres.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_down,0);
                }
            }
        });

        btn_cuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ln_cuatro.getVisibility()==View.GONE){
                    ln_cuatro.setVisibility(View.VISIBLE);
                    btn_cuatro.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_up,0);

                }
                else{
                    ln_cuatro.setVisibility(View.GONE);
                    btn_cuatro.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_down,0);
                }
            }
        });

        btn_cinco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ln_cinco.getVisibility()==View.GONE){
                    ln_cinco.setVisibility(View.VISIBLE);
                    btn_cinco.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_up,0);

                }
                else{
                    ln_cinco.setVisibility(View.GONE);
                    btn_cinco.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_down,0);
                }
            }
        });

        btn_seis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ln_seis.getVisibility()==View.GONE){
                    ln_seis.setVisibility(View.VISIBLE);
                    btn_seis.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_up,0);

                }
                else{
                    ln_seis.setVisibility(View.GONE);
                    btn_seis.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_down,0);
                }
            }
        });
    }

    private void UpdateTarea1(final int posicion) {

        try {
            if(listTareas1.get(posicion).getEstado()==1){
                adaptadorTarea1.UpdateItem(posicion);
            }else {
                AlertDialog.Builder Advertencia= new AlertDialog.Builder(getContext());
                Advertencia.setTitle("Tarea");
                Advertencia.setMessage("Esta seguro que desea indicar que la tarea no fue realizada?");
                Advertencia.setCancelable(false);
                Advertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adaptadorTarea1.UpdateItem(posicion);
                    }
                });
                Advertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                Advertencia.show();
            }

        } catch (Exception E) {
            Toast.makeText(getContext(), "Error, Intentelo mas tarde por favor", Toast.LENGTH_SHORT).show();
        }
    }
    private void UpdateTarea2(final int posicion) {

        try {
            if(listTareas2.get(posicion).getEstado()==1){

                adaptadorTarea2.UpdateItem(posicion);
            }else {
                AlertDialog.Builder Advertencia= new AlertDialog.Builder(getContext());
                Advertencia.setTitle("Tarea");
                Advertencia.setMessage("Esta seguro que desea indicar que la tarea no fue realizada?");
                Advertencia.setCancelable(false);
                Advertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adaptadorTarea2.UpdateItem(posicion);
                    }
                });
                Advertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                Advertencia.show();
            }

        } catch (Exception E) {
            Toast.makeText(getContext(), "Error, Intentelo mas tarde por favor", Toast.LENGTH_SHORT).show();
        }
    }
    private void UpdateTarea3(final int posicion) {

        try {
            if(listTareas3.get(posicion).getEstado()==1){

                adaptadorTarea3.UpdateItem(posicion);
            }else {
                AlertDialog.Builder Advertencia= new AlertDialog.Builder(getContext());
                Advertencia.setTitle("Tarea");
                Advertencia.setMessage("Esta seguro que desea indicar que la tarea no fue realizada?");
                Advertencia.setCancelable(false);
                Advertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adaptadorTarea3.UpdateItem(posicion);
                    }
                });
                Advertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                Advertencia.show();
            }

        } catch (Exception E) {
            Toast.makeText(getContext(), "Error, Intentelo mas tarde por favor", Toast.LENGTH_SHORT).show();
        }
    }
    private void UpdateTarea4(final int posicion) {

        try {
            if(listTareas4.get(posicion).getEstado()==1){

                adaptadorTarea4.UpdateItem(posicion);
            }else {
                AlertDialog.Builder Advertencia= new AlertDialog.Builder(getContext());
                Advertencia.setTitle("Tarea");
                Advertencia.setMessage("Esta seguro que desea indicar que la tarea no fue realizada?");
                Advertencia.setCancelable(false);
                Advertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adaptadorTarea4.UpdateItem(posicion);
                    }
                });
                Advertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                Advertencia.show();
            }

        } catch (Exception E) {
            Toast.makeText(getContext(), "Error, Intentelo mas tarde por favor", Toast.LENGTH_SHORT).show();
        }
    }
    private void UpdateTarea5(final int posicion) {

        try {
            if(listTareas5.get(posicion).getEstado()==1){

                adaptadorTarea5.UpdateItem(posicion);
            }else {
                AlertDialog.Builder Advertencia= new AlertDialog.Builder(getContext());
                Advertencia.setTitle("Tarea");
                Advertencia.setMessage("Esta seguro que desea indicar que la tarea no fue realizada?");
                Advertencia.setCancelable(false);
                Advertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adaptadorTarea5.UpdateItem(posicion);
                    }
                });
                Advertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                Advertencia.show();
            }

        } catch (Exception E) {
            Toast.makeText(getContext(), "Error, Intentelo mas tarde por favor", Toast.LENGTH_SHORT).show();
        }
    }
    private void UpdateTarea6(final int posicion) {

        try {
            if(listTareas6.get(posicion).getEstado()==1){

                adaptadorTarea6.UpdateItem(posicion);
            }else {
                AlertDialog.Builder Advertencia= new AlertDialog.Builder(getContext());
                Advertencia.setTitle("Tarea");
                Advertencia.setMessage("Esta seguro que desea indicar que la tarea no fue realizada?");
                Advertencia.setCancelable(false);
                Advertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adaptadorTarea6.UpdateItem(posicion);
                    }
                });
                Advertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                Advertencia.show();
            }

        } catch (Exception E) {
            Toast.makeText(getContext(), "Error, Intentelo mas tarde por favor", Toast.LENGTH_SHORT).show();
        }
    }
}
