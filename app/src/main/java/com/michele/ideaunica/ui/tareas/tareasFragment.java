package com.michele.ideaunica.ui.tareas;

import android.content.DialogInterface;
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

    private RecyclerView rv_nueve;
    private LinearLayout ln_nueve;
    private Button btn_nueve;

    private RecyclerView rv_diez;
    private LinearLayout ln_diez;
    private Button btn_diez;


    //Complementos
    AdaptadorTarea adaptadorTarea1;
    private ArrayList<TareaClass> listTareas1 = new ArrayList<>();
    AdaptadorTarea adaptadorTarea2;
    private ArrayList<TareaClass> listTareas2 = new ArrayList<>();
    AdaptadorTarea adaptadorTarea3;
    private ArrayList<TareaClass> listTareas3 = new ArrayList<>();
    AdaptadorTarea adaptadorTarea4;
    private ArrayList<TareaClass> listTareas4 = new ArrayList<>();

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

        return view;
    }

    /*UNO*/
    private void GenerarDatosPrimero() {
        try {
            BDEvento obj= new BDEvento(getContext(),"bdEvento",null,1);
            SQLiteDatabase bd= obj.getReadableDatabase();
            if(bd!=null){
                Cursor objCursor = bd.rawQuery("Select * from tarea where idevento=" + ID+" and mes=1", null);

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
                Cursor objCursor = bd.rawQuery("Select * from tarea where idevento=" + ID+" and mes=2", null);

                while (objCursor.moveToNext()){
                    listTareas2.add(new TareaClass(objCursor.getInt(0),objCursor.getString(3),objCursor.getInt(4)));
                }
                if(listTareas2.size()==0)
                {
                    Llenar2();
                }

                adaptadorTarea2=new AdaptadorTarea(getContext(),listTareas2);
                rv_nueve.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_nueve.setAdapter(adaptadorTarea2);
                adaptadorTarea2.setOnItemClickListener(new AdaptadorTarea.OnItemClickListener() {
                    @Override
                    public void onItenClick(int position) {
                        UpdateTarea2(position);
                    }
                });
                rv_nueve.setVisibility(View.VISIBLE);
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
                Cursor objCursor = bd.rawQuery("Select * from tarea where idevento=" + ID+" and mes=3", null);

                while (objCursor.moveToNext()){
                    listTareas3.add(new TareaClass(objCursor.getInt(0),objCursor.getString(3),objCursor.getInt(4)));
                }
                if(listTareas3.size()==0)
                {
                    Llenar3();
                }

                adaptadorTarea3=new AdaptadorTarea(getContext(),listTareas3);
                rv_diez.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_diez.setAdapter(adaptadorTarea3);
                adaptadorTarea3.setOnItemClickListener(new AdaptadorTarea.OnItemClickListener() {
                    @Override
                    public void onItenClick(int position) {
                        UpdateTarea3(position);
                    }
                });
                rv_diez.setVisibility(View.VISIBLE);
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
                String[] diadelevento = res.getStringArray(R.array.tarea_dia);
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





    private void Llenar() {
        Resources res = getResources();
        /*String[] messeis = res.getStringArray(R.array.tarea_seis_meses);
        for (String t: messeis) {
            listTareas1.add(new TareaClass(1,t,1));
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
        rv_uno.setVisibility(View.VISIBLE);

        String[] mescinco = res.getStringArray(R.array.tarea_cinco_meses);
        for (String t: mescinco) {
            listTareas2.add(new TareaClass(1,t,1));
        }
        adaptadorTarea2=new AdaptadorTarea(getContext(),listTareas2);
        rv_nueve.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_nueve.setAdapter(adaptadorTarea2);
        adaptadorTarea2.setOnItemClickListener(new AdaptadorTarea.OnItemClickListener() {
            @Override
            public void onItenClick(int position) {
                UpdateTarea2(position);
            }
        });
        rv_nueve.setVisibility(View.VISIBLE);

        String[] diadelevento = res.getStringArray(R.array.tarea_dia);
        for (String t: diadelevento) {
            listTareas3.add(new TareaClass(1,t,1));
        }
        adaptadorTarea3=new AdaptadorTarea(getContext(),listTareas3);
        rv_diez.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_diez.setAdapter(adaptadorTarea3);
        adaptadorTarea3.setOnItemClickListener(new AdaptadorTarea.OnItemClickListener() {
            @Override
            public void onItenClick(int position) {
                UpdateTarea3(position);
            }
        });
        rv_diez.setVisibility(View.VISIBLE);*/
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

        btn_nueve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ln_nueve.getVisibility()==View.GONE){
                    ln_nueve.setVisibility(View.VISIBLE);
                    btn_nueve.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_up,0);

                }
                else{
                    ln_nueve.setVisibility(View.GONE);
                    btn_nueve.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_down,0);
                }
            }
        });

        btn_diez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ln_diez.getVisibility()==View.GONE){
                    ln_diez.setVisibility(View.VISIBLE);
                    btn_diez.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_up,0);

                }
                else{
                    ln_diez.setVisibility(View.GONE);
                    btn_diez.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.keyboard_arrow_down,0);
                }
            }
        });
    }

    private void InicializarComponentes() {
        rv_uno=view.findViewById(R.id.rv_seis_meses_tarea);
        ln_uno=view.findViewById(R.id.ln_seis_meses_tarea);
        btn_uno=view.findViewById(R.id.btn_seis_meses_tarea);

        rv_nueve=view.findViewById(R.id.rv_un_dia_tarea);
        ln_nueve=view.findViewById(R.id.ln_un_dia_tarea);
        btn_nueve=view.findViewById(R.id.btn_un_dia_tarea);

        rv_diez=view.findViewById(R.id.rv_dia_del_evento_tarea);
        ln_diez=view.findViewById(R.id.ln_dia_del_evento_tarea);
        btn_diez=view.findViewById(R.id.btn_dia_del_evento_tarea);
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

}
