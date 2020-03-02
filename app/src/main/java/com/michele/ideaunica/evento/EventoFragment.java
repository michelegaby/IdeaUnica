package com.michele.ideaunica.evento;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.michele.ideaunica.R;
import com.michele.ideaunica.departamento.Categoria;
import com.michele.ideaunica.empresa.Empresa;
import com.michele.ideaunica.empresa.EmpresaClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EventoFragment extends Fragment {

    View view;
    private RecyclerView rv_evento;
    private LinearLayout hoy;
    private LinearLayout semana;
    private LinearLayout mes;
    private LinearLayout todo;
    private TextView txthoy;
    private TextView txtsemana;
    private TextView txtmes;
    private TextView txttodo;
    private TextView txtTitulo;
    private SwipeRefreshLayout swipeRefreshLayout;

    //Complementos
    AdaptadorEvento adaptadorEvento;
    private ArrayList<EventoClass> listEvento = new ArrayList<>();
    private static  String URL="https://sice.com.bo/ideaunica/apps/evento.php";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_evento,container,false);
        inicializarcomponentes();

        GenerarDatos();
        Busqueda();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new UnaTarea().execute();

            }
        });

        return view;
    }


    private class UnaTarea extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                listEvento.clear();
                GenerarDatos();
                Limpiar();
                txttodo.setTypeface(txttodo.getTypeface(), Typeface.BOLD);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public static EventoFragment newInstance(){
        EventoFragment fragment=new EventoFragment();
        Bundle args=new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    private void Busqueda() {
        hoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Limpiar();
                txthoy.setTypeface(txthoy.getTypeface(), Typeface.BOLD);
                ArrayList<EventoClass> listafiltrada= filter(listEvento,"HOY");
                adaptadorEvento.setfilter(listafiltrada);
            }
        });

        semana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Limpiar();
                txtsemana.setTypeface(txtsemana.getTypeface(), Typeface.BOLD);
                ArrayList<EventoClass> listafiltrada= filter(listEvento,"SEMANA");
                adaptadorEvento.setfilter(listafiltrada);
            }
        });

        mes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Limpiar();
                txtmes.setTypeface(txtmes.getTypeface(), Typeface.BOLD);
                ArrayList<EventoClass> listafiltrada= filter(listEvento,"MES");
                adaptadorEvento.setfilter(listafiltrada);
            }
        });

        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Limpiar();
                txttodo.setTypeface(txttodo.getTypeface(), Typeface.BOLD);
                ArrayList<EventoClass> listafiltrada= filter(listEvento,"TODO");
                adaptadorEvento.setfilter(listafiltrada);
            }
        });
    }
    private ArrayList<EventoClass> filter(ArrayList<EventoClass> categorias,String texto){
        ArrayList<EventoClass> listFiltada= new ArrayList<>();
        try{
            Date d = new Date();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            CharSequence fechaComprar = DateFormat.format("yyyy-MM-dd", d.getTime());
            Date fechaComparar=new SimpleDateFormat("yyyy-MM-dd").parse(fechaComprar.toString());
            switch (texto)
            {
                case "TODO":
                    listFiltada.addAll(categorias);break;
                case "HOY":
                    for(EventoClass cat: categorias){
                        Date fecha1=new SimpleDateFormat("yyyy-MM-dd").parse(cat.getFecha());
                        Date fecha2;
                        if (cat.getFecha_final() == null || cat.getFecha_final().equals("null")) {
                            fecha2 = new SimpleDateFormat("yyyy-MM-dd").parse(cat.getFecha());
                        }else
                        {   fecha2=new SimpleDateFormat("yyyy-MM-dd").parse(cat.getFecha_final());}
                        if(ComprardosfechasDentro(fecha1,fecha2,fechaComparar)){
                            listFiltada.add(cat); }
                    };
                    break;
                case "SEMANA":
                    Calendar c = Calendar.getInstance();
                    c.setTime(fechaComparar);
                    for(EventoClass cat: categorias){
                        Date fecha1=new SimpleDateFormat("yyyy-MM-dd").parse(cat.getFecha());
                        Date fecha2;
                        if (cat.getFecha_final() == null || cat.getFecha_final().equals("null")) {
                            fecha2 = new SimpleDateFormat("yyyy-MM-dd").parse(cat.getFecha());
                        }else
                        {   fecha2=new SimpleDateFormat("yyyy-MM-dd").parse(cat.getFecha_final());}

                        for(int i=0; i<7; ++i)
                        {
                            switch (i){
                                case 0:
                                    c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
                                    Date lunes = c.getTime();
                                    if(ComprardosfechasDentro(fecha1,fecha2,lunes))
                                    {
                                        i=7;
                                        listFiltada.add(cat);
                                    }
                                    ;break;
                                case 1:
                                    c.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
                                    Date martes = c.getTime();
                                    if(ComprardosfechasDentro(fecha1,fecha2,martes))
                                    {
                                        i=7;
                                        listFiltada.add(cat);
                                    }
                                    ;break;
                                case 2:
                                    c.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
                                    Date miercoles = c.getTime();
                                    if(ComprardosfechasDentro(fecha1,fecha2,miercoles))
                                    {
                                        i=7;
                                        listFiltada.add(cat);
                                    }
                                    ;break;
                                case 3:
                                    c.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
                                    Date jueves = c.getTime();
                                    if(ComprardosfechasDentro(fecha1,fecha2,jueves))
                                    {
                                        i=7;
                                        listFiltada.add(cat);
                                    }
                                    ;break;
                                case 4:
                                    c.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
                                    Date viernes = c.getTime();
                                    if(ComprardosfechasDentro(fecha1,fecha2,viernes))
                                    {
                                        i=7;
                                        listFiltada.add(cat);
                                    }
                                    ;break;
                                case 5:
                                    c.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
                                    Date sabado = c.getTime();
                                    if(ComprardosfechasDentro(fecha1,fecha2,sabado))
                                    {
                                        i=7;
                                        listFiltada.add(cat);
                                    }
                                    ;break;
                                case 6:
                                    c.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
                                    c.add(Calendar.DAY_OF_YEAR, 1);
                                    Date domingo = c.getTime();
                                    if(ComprardosfechasDentro(fecha1,fecha2,domingo))
                                    {
                                        i=7;
                                        listFiltada.add(cat);
                                    }
                                    ;break;
                            }
                        };
                    };
                    break;
                case "MES":
                    Calendar c1 = Calendar.getInstance();
                    SimpleDateFormat formatoMES = new SimpleDateFormat("MM/YYYY");
                    for(EventoClass cat: categorias){
                        Date fecha1 = new SimpleDateFormat("yyyy-MM-dd").parse(cat.getFecha());
                        Date fecha2;
                        if (cat.getFecha_final() == null || cat.getFecha_final().equals("null")) {
                            fecha2 = new SimpleDateFormat("yyyy-MM-dd").parse(cat.getFecha());
                        }else
                        {   fecha2 = new SimpleDateFormat("yyyy-MM-dd").parse(cat.getFecha_final());}
                        String fechaInicio=formatoMES.format(fecha1);
                        String fechaFinal=formatoMES.format(fecha2);
                        String MH=formatoMES.format(c1.getTime());
                        if(fechaInicio.equals(MH) || fechaFinal.equals(MH)){
                            listFiltada.add(cat);
                        }
                    }
                    ;break;
            }
        }catch (Exception e)
        {
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return listFiltada;
    }
    public void Limpiar(){
        /*
        txtmes.setTypeface(txtmes.getTypeface(), Typeface.NORMAL);
        txttodo.setTypeface(txttodo.getTypeface(), Typeface.NORMAL);*/
        txthoy.setTypeface(null, Typeface.NORMAL);
        txtsemana.setTypeface(null, Typeface.NORMAL);
        txtmes.setTypeface(null, Typeface.NORMAL);
        txttodo.setTypeface(null, Typeface.NORMAL);
    }
    private void inicializarcomponentes() {
        rv_evento=view.findViewById(R.id.Eventofragment_rv);
        hoy=view.findViewById(R.id.hoy_evento);
        semana=view.findViewById(R.id.semana_evento);
        mes=view.findViewById(R.id.mes_evento);
        todo=view.findViewById(R.id.todo_evento);

        txthoy=view.findViewById(R.id.txt_hoy_evento);
        txtsemana=view.findViewById(R.id.txt_semana_evento);
        txtmes=view.findViewById(R.id.txt_mes_evento);
        txttodo=view.findViewById(R.id.txt_todo_evento);
        txtTitulo=view.findViewById(R.id.titulo_evento_f);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout_evento);

    }
    private void GenerarDatos() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("evento");
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                listEvento.add(new EventoClass(
                                        object.getInt("id"),
                                        object.getString("url"),
                                        object.getString("titulo"),
                                        object.getString("fecha_inicio"),
                                        object.getString("fecha_final"),
                                        object.getString("descripcion")));
                            }
                            adaptadorEvento= new AdaptadorEvento(getContext(),listEvento);
                            rv_evento.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                            rv_evento.setAdapter(adaptadorEvento);
                            rv_evento.setPadding(10,0,100,0);

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(getContext(),
                                    "Error"+ e.getMessage(), Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),
                                "Error  2"+error.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
    private boolean ComprardosfechasDentro(Date min,Date max,Date dia){
        return dia.compareTo(min) >= 0 && dia.compareTo(max) <= 0;
    }
}