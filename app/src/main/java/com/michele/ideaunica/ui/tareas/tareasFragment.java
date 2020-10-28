package com.michele.ideaunica.ui.tareas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.michele.ideaunica.R;
import com.michele.ideaunica.sharedPreferences.SessionCumple;
import com.michele.ideaunica.ui.notas.NuevaNota;
import com.michele.ideaunica.ui.tareas.ui.HeaderTaskClass;
import com.michele.ideaunica.ui.tareas.ui.TimeTaskAdapter;
import com.michele.ideaunica.ui.tareas.ui.TimeTaskClass;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class tareasFragment extends Fragment {

    View view;

    SessionCumple sessionCumple;

    private ArrayList<TareaClass> listTarea = new ArrayList<>();

    //Componentes
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

    android.app.AlertDialog mDialog;

    private static  String URL = "https://www.ideaunicabolivia.com/apps/fiesta/UpdateTarea.php";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tareas,container,false);

        InicializarComponentes();

        sessionCumple = new SessionCumple(getContext());

        mDialog = new SpotsDialog.Builder().setContext(getContext()).setMessage("Espera un momento por favor").build();

        initAdapter();

        GetData();

        Onclick();

        return view;
    }

    private void InicializarComponentes() {

        rv_uno = view.findViewById(R.id.rv_seis_meses_tarea);
        ln_uno = view.findViewById(R.id.ln_seis_meses_tarea);
        btn_uno = view.findViewById(R.id.btn_seis_meses_tarea);

        rv_dos = view.findViewById(R.id.rv_cinco_meses_tarea);
        ln_dos = view.findViewById(R.id.ln_cinco_meses_tarea);
        btn_dos = view.findViewById(R.id.btn_cinco_meses_tarea);

        rv_tres = view.findViewById(R.id.rv_un_mes_tarea);
        ln_tres = view.findViewById(R.id.ln_un_mes_tarea);
        btn_tres = view.findViewById(R.id.btn_un_mes_tarea);

        rv_cuatro = view.findViewById(R.id.rv_una_semana_tarea);
        ln_cuatro = view.findViewById(R.id.ln_una_semana_tarea);
        btn_cuatro = view.findViewById(R.id.btn_una_semana_tarea);

        rv_cinco = view.findViewById(R.id.rv_dos_dias_tarea);
        ln_cinco = view.findViewById(R.id.ln_dos_dias_tarea);
        btn_cinco = view.findViewById(R.id.btn_dos_dias_tarea);

        rv_seis = view.findViewById(R.id.rv_dia_del_evento_tarea);
        ln_seis = view.findViewById(R.id.ln_dia_del_evento_tarea);
        btn_seis = view.findViewById(R.id.btn_dia_del_evento_tarea);
    }

    public void initAdapter(){

        adaptadorTarea1 = new AdaptadorTarea(getContext(), listTareas1);
        rv_uno.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_uno.setAdapter(adaptadorTarea1);
        adaptadorTarea1.setOnItemClickListener(new AdaptadorTarea.OnItemClickListener() {
            @Override
            public void onItenClick(int position) {
                UpdateTarea1(position);
            }
        });

        adaptadorTarea2 = new AdaptadorTarea(getContext(),listTareas2);
        rv_dos.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_dos.setAdapter(adaptadorTarea2);
        adaptadorTarea2.setOnItemClickListener(new AdaptadorTarea.OnItemClickListener() {
            @Override
            public void onItenClick(int position) {
                UpdateTarea2(position);
            }
        });
        rv_dos.setVisibility(View.VISIBLE);

        adaptadorTarea3 = new AdaptadorTarea(getContext(),listTareas3);
        rv_tres.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_tres.setAdapter(adaptadorTarea3);
        adaptadorTarea3.setOnItemClickListener(new AdaptadorTarea.OnItemClickListener() {
            @Override
            public void onItenClick(int position) {
                UpdateTarea3(position);
            }
        });
        rv_tres.setVisibility(View.VISIBLE);

        adaptadorTarea4 = new AdaptadorTarea(getContext(),listTareas4);
        rv_cuatro.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_cuatro.setAdapter(adaptadorTarea4);
        adaptadorTarea4.setOnItemClickListener(new AdaptadorTarea.OnItemClickListener() {
            @Override
            public void onItenClick(int position) {
                UpdateTarea4(position);
            }
        });
        rv_cuatro.setVisibility(View.VISIBLE);

        adaptadorTarea5 = new AdaptadorTarea(getContext(),listTareas5);
        rv_cinco.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_cinco.setAdapter(adaptadorTarea5);
        adaptadorTarea5.setOnItemClickListener(new AdaptadorTarea.OnItemClickListener() {
            @Override
            public void onItenClick(int position) {
                UpdateTarea5(position);
            }
        });
        rv_cinco.setVisibility(View.VISIBLE);

        adaptadorTarea6 = new AdaptadorTarea(getContext(),listTareas6);
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

    public void GetData(){

        if(sessionCumple.isTarea()){

            listTareas1.clear();
            listTareas2.clear();
            listTareas3.clear();
            listTareas4.clear();
            listTareas5.clear();
            listTareas6.clear();

            listTarea = sessionCumple.readTarea();

            for (TareaClass item: listTarea){

                switch (item.getPosicion()){
                    case "1": listTareas1.add(item);break;
                    case "2": listTareas2.add(item);break;
                    case "3": listTareas3.add(item);break;
                    case "4": listTareas4.add(item);break;
                    case "5": listTareas5.add(item);break;
                    case "6": listTareas6.add(item);break;
                    default:break;
                }

            }

            adaptadorTarea1.notifyDataSetChanged();
            adaptadorTarea2.notifyDataSetChanged();
            adaptadorTarea3.notifyDataSetChanged();
            adaptadorTarea4.notifyDataSetChanged();
            adaptadorTarea5.notifyDataSetChanged();
            adaptadorTarea6.notifyDataSetChanged();
        }
    }

    private void Onclick() {
        btn_uno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ln_uno.getVisibility() == View.GONE){
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
                if(ln_dos.getVisibility() == View.GONE){
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
                if(ln_tres.getVisibility() == View.GONE){
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
                if(ln_cuatro.getVisibility() == View.GONE){
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
                if(ln_cinco.getVisibility() == View.GONE){
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
                if(ln_seis.getVisibility() == View.GONE){
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
            if(listTareas1.get(posicion).getEstado() == 0){

                AlertDialog.Builder Advertencia = new AlertDialog.Builder(getContext());
                Advertencia.setTitle("Tarea");
                Advertencia.setMessage("Esta seguro que desea indicar que la tarea fue realizada?");
                Advertencia.setCancelable(false);
                Advertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        GuardarStatus(listTareas1.get(posicion).getId(),1,1, posicion);

                    }
                });
                Advertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                Advertencia.show();

            }else {
                AlertDialog.Builder Advertencia = new AlertDialog.Builder(getContext());
                Advertencia.setTitle("Tarea");
                Advertencia.setMessage("Esta seguro que desea indicar que la tarea no fue realizada?");
                Advertencia.setCancelable(false);
                Advertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        GuardarStatus(listTareas1.get(posicion).getId(),0,1, posicion);
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
            if(listTareas2.get(posicion).getEstado() == 0){

                AlertDialog.Builder Advertencia = new AlertDialog.Builder(getContext());
                Advertencia.setTitle("Tarea");
                Advertencia.setMessage("Esta seguro que desea indicar que la tarea fue realizada?");
                Advertencia.setCancelable(false);
                Advertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        GuardarStatus(listTareas2.get(posicion).getId(),1,2, posicion);
                    }
                });
                Advertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                Advertencia.show();
            }else {
                AlertDialog.Builder Advertencia = new AlertDialog.Builder(getContext());
                Advertencia.setTitle("Tarea");
                Advertencia.setMessage("Esta seguro que desea indicar que la tarea no fue realizada?");
                Advertencia.setCancelable(false);
                Advertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GuardarStatus(listTareas2.get(posicion).getId(),0,2, posicion);
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
            if(listTareas3.get(posicion).getEstado() == 0){

                AlertDialog.Builder Advertencia = new AlertDialog.Builder(getContext());
                Advertencia.setTitle("Tarea");
                Advertencia.setMessage("Esta seguro que desea indicar que la tarea fue realizada?");
                Advertencia.setCancelable(false);
                Advertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        GuardarStatus(listTareas3.get(posicion).getId(),1,3, posicion);
                    }
                });
                Advertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                Advertencia.show();

            }else {
                AlertDialog.Builder Advertencia = new AlertDialog.Builder(getContext());
                Advertencia.setTitle("Tarea");
                Advertencia.setMessage("Esta seguro que desea indicar que la tarea no fue realizada?");
                Advertencia.setCancelable(false);
                Advertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        GuardarStatus(listTareas3.get(posicion).getId(),0,3, posicion);
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
            if(listTareas4.get(posicion).getEstado() == 0){

                AlertDialog.Builder Advertencia = new AlertDialog.Builder(getContext());
                Advertencia.setTitle("Tarea");
                Advertencia.setMessage("Esta seguro que desea indicar que la tarea fue realizada?");
                Advertencia.setCancelable(false);
                Advertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        GuardarStatus(listTareas4.get(posicion).getId(),1,4, posicion);
                    }
                });
                Advertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                Advertencia.show();

            }else {
                AlertDialog.Builder Advertencia = new AlertDialog.Builder(getContext());
                Advertencia.setTitle("Tarea");
                Advertencia.setMessage("Esta seguro que desea indicar que la tarea no fue realizada?");
                Advertencia.setCancelable(false);
                Advertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        GuardarStatus(listTareas4.get(posicion).getId(),0,4, posicion);
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
            if(listTareas5.get(posicion).getEstado() == 0){

                AlertDialog.Builder Advertencia = new AlertDialog.Builder(getContext());
                Advertencia.setTitle("Tarea");
                Advertencia.setMessage("Esta seguro que desea indicar que la tarea fue realizada?");
                Advertencia.setCancelable(false);
                Advertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        GuardarStatus(listTareas5.get(posicion).getId(),1,5, posicion);
                    }
                });
                Advertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                Advertencia.show();

            }else {
                AlertDialog.Builder Advertencia = new AlertDialog.Builder(getContext());
                Advertencia.setTitle("Tarea");
                Advertencia.setMessage("Esta seguro que desea indicar que la tarea no fue realizada?");
                Advertencia.setCancelable(false);
                Advertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        GuardarStatus(listTareas5.get(posicion).getId(),0,5, posicion);
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
            if(listTareas6.get(posicion).getEstado() == 0){

                AlertDialog.Builder Advertencia = new AlertDialog.Builder(getContext());
                Advertencia.setTitle("Tarea");
                Advertencia.setMessage("Esta seguro que desea indicar que la tarea fue realizada?");
                Advertencia.setCancelable(false);
                Advertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        GuardarStatus(listTareas6.get(posicion).getId(),1,6, posicion);
                    }
                });
                Advertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                Advertencia.show();

            }else {
                AlertDialog.Builder Advertencia = new AlertDialog.Builder(getContext());
                Advertencia.setTitle("Tarea");
                Advertencia.setMessage("Esta seguro que desea indicar que la tarea no fue realizada?");
                Advertencia.setCancelable(false);
                Advertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        GuardarStatus(listTareas6.get(posicion).getId(),0,6, posicion);
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

    public void GetDataNew(int post){

        if(sessionCumple.isTarea()) {

            listTarea = sessionCumple.readTarea();

            try {

                int posiscion = 0;
                for (TareaClass item : listTarea) {
                    if(item.getId() == post){
                        Log.e("ERROR","SE ENCONTRO Y ELIMINO: " + item.getTitulo());
                        break;
                    }

                    posiscion = posiscion + 1;
                }

                TareaClass s = listTarea.get(posiscion);
                listTarea.remove(posiscion);

                int pp = 0;

                if(s.getEstado() == 1){

                    switch (s.getPosicion()){
                        case "1": pp = 0; break;
                        case "2": pp = listTareas1.size();break;
                        case "3": pp = listTareas1.size() + listTareas2.size();break;
                        case "4": pp = listTareas1.size() + listTareas2.size() + listTareas3.size();break;
                        case "5": pp = listTareas1.size() + listTareas2.size() + listTareas3.size() + listTareas4.size();break;
                        case "6": pp = listTareas1.size() + listTareas2.size() + listTareas3.size() + listTareas4.size() + listTareas5.size();break;
                        default:break;
                    }
                    s.setEstado(0);

                }else{

                    switch (s.getPosicion()){
                        case "1": pp = listTareas1.size() -1; break;
                        case "2": pp = listTareas1.size() + listTareas2.size() -1; break;
                        case "3": pp = listTareas1.size() + listTareas2.size() + listTareas3.size() -1; break;
                        case "4": pp = listTareas1.size() + listTareas2.size() + listTareas3.size() + listTareas4.size() -1; break;
                        case "5": pp = listTareas1.size() + listTareas2.size() + listTareas3.size() + listTareas4.size() + listTareas5.size() -1; break;
                        case "6": pp = listTareas1.size() + listTareas2.size() + listTareas3.size() + listTareas4.size() + listTareas5.size() + listTareas6.size() -1; break;
                        default:break;
                    }

                    s.setEstado(1);
                }

                Log.e("ERROR","POSUCUION: " + pp);
                listTarea.add(pp,s);

                sessionCumple.createSessionTarea(listTarea);

            }catch (Exception e){

                Log.e("ERROR","ERROR: " + e.getMessage());
            }
        }
    }

    public void GuardarStatus(final int xid,final int xstatus,final int recycler,final int posicion){

        mDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            final JSONObject jsonObject = new JSONObject(response);
                            Boolean isStatus = jsonObject.getBoolean("estado");

                            Log.e("ERROR","Status: " + isStatus + "POSICO: " + posicion);
                            if(!isStatus){

                                AlertDialog.Builder Advertencia = new AlertDialog.Builder(getContext());

                                Advertencia.setTitle("Error");
                                Advertencia.setMessage("No se guardo, lo sentimos mucho");
                                Advertencia.setCancelable(false);

                                Advertencia.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                Advertencia.show();
                            }else{

                                switch (recycler){
                                    case 1:
                                        GetDataNew(listTareas1.get(posicion).getId());
                                        adaptadorTarea1.UpdateItem(posicion);break;
                                    case 2:
                                        GetDataNew(listTareas2.get(posicion).getId());
                                        adaptadorTarea2.UpdateItem(posicion);break;
                                    case 3:
                                        GetDataNew(listTareas3.get(posicion).getId());
                                        adaptadorTarea3.UpdateItem(posicion);break;
                                    case 4:
                                        GetDataNew(listTareas4.get(posicion).getId());
                                        adaptadorTarea4.UpdateItem(posicion);break;
                                    case 5:
                                        GetDataNew(listTareas5.get(posicion).getId());
                                        adaptadorTarea5.UpdateItem(posicion);break;
                                    case 6:
                                        GetDataNew(listTareas6.get(posicion).getId());
                                        adaptadorTarea6.UpdateItem(posicion);break;
                                    default:break;
                                }

                            }

                            mDialog.dismiss();

                        } catch (Exception e) {
                            mDialog.dismiss();

                            AlertDialog.Builder Advertencia = new AlertDialog.Builder(getContext());

                            Advertencia.setTitle("Error");
                            Advertencia.setMessage("Por favor intentelo mas tarde, gracias.");
                            Advertencia.setCancelable(false);

                            Advertencia.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            Advertencia.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mDialog.dismiss();

                        AlertDialog.Builder Advertencia = new AlertDialog.Builder(getContext());

                        Advertencia.setTitle("Error");
                        Advertencia.setMessage("Error de conexión, por favor verifique el acceso a internet.");
                        Advertencia.setCancelable(false);

                        Advertencia.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        Advertencia.show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",String.valueOf(xid));
                params.put("new",String.valueOf(xstatus));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
