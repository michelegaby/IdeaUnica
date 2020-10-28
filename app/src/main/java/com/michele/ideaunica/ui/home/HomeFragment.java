package com.michele.ideaunica.ui.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.MainActivity;
import com.michele.ideaunica.R;
import com.michele.ideaunica.cumple.AdaptadorCumpleanyos;
import com.michele.ideaunica.cumple.CumpleanyosClass;
import com.michele.ideaunica.sharedPreferences.SessionCumple;
import com.michele.ideaunica.ui.gastos.CuotaClass;
import com.michele.ideaunica.ui.gastos.GastosClass;
import com.michele.ideaunica.ui.invitados.InvitadosClass;
import com.michele.ideaunica.ui.notas.NotaClass;
import com.michele.ideaunica.ui.tareas.TareaClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class HomeFragment extends Fragment {

    View view;

    SessionCumple sessionCumple;

    //Componentes
    private TextView tareas;
    private TextView tareas_hechas;
    private TextView dias;
    private TextView hora;
    private TextView min;
    private TextView seg;
    private TextView titulo;
    private TextView fecha;
    private ImageView img_perfil;
    private ImageView img_banner;
    private TextView sinconfirmar;
    private TextView confirmar;
    private String EVENT_DATE_TIME = "2020-01-01 00:00:00";
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private Handler handler = new Handler();
    private Runnable runnable;
    private TextView msn;

    AlertDialog mDialog;

    private static  String URL = "https://www.ideaunicabolivia.com/apps/fiesta/todoSobreEvento.php";


    private ArrayList<InvitadosClass> listInvited = new ArrayList<>();

    private ArrayList<NotaClass> listNote = new ArrayList<>();

    private ArrayList<TareaClass> listTarea = new ArrayList<>();

    private ArrayList<GastosClass> listGasto = new ArrayList<>();

    private ArrayList<CuotaClass> listCuota = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        sessionCumple = new SessionCumple(getContext());

        mDialog = new SpotsDialog.Builder().setContext(getContext()).setMessage("Espera un momento por favor").build();

        InicializarComponentes();

        if(sessionCumple.isData() == false){
            sessionCumple.createData(true);
            Init(sessionCumple.getId());
        }

        return view;
    }

    private void InitData(){

        Log.e("ERROR","DENTRO DE INIT");
        titulo.setText(sessionCumple.getTitulo());

        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formateador = new SimpleDateFormat("MMMM d, yyyy");

        EVENT_DATE_TIME = sessionCumple.getFecha()+" "+ sessionCumple.getHora();

        try {
            Date date = parseador.parse(sessionCumple.getFecha());
            fecha.setText(formateador.format(date));
        } catch (ParseException e) {
            fecha.setText(sessionCumple.getFecha());
        }

        if(!sessionCumple.getUrlperfil().equals("null"))
        {
            Glide.with(getActivity())
                    .load("https://www.ideaunicabolivia.com/apps/fiesta/"+sessionCumple.getUrlperfil())
                    .placeholder(R.drawable.fondorosa)
                    .error(R.drawable.cargando)
                    .into(img_perfil);
        }

        if(!sessionCumple.getUrlfondo().equals("null"))
        {
            Glide.with(getActivity())
                    .load("https://www.ideaunicabolivia.com/apps/fiesta/"+sessionCumple.getUrlfondo())
                    .placeholder(R.drawable.fondorosa)
                    .error(R.drawable.cargando)
                    .into(img_banner);
        }

        countDownStart();

        calculateInvited();
        calculateTask();

    }

    private void countDownStart() {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date event_date = dateFormat.parse(EVENT_DATE_TIME);
                    Date current_date = new Date();
                    if (!current_date.after(event_date)) {
                        long diff = event_date.getTime() - current_date.getTime();
                        long Days = diff / (24 * 60 * 60 * 1000);
                        long Hours = diff / (60 * 60 * 1000) % 24;
                        long Minutes = diff / (60 * 1000) % 60;
                        long Seconds = diff / 1000 % 60;
                        dias.setText(String.format("%02d", Days));
                        hora.setText(String.format("%02d", Hours));
                        min.setText(String.format("%02d", Minutes));
                        seg.setText(String.format("%02d", Seconds));
                    } else {
                        handler.removeCallbacks(runnable);
                        msn.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    private void InicializarComponentes() {

        dias = view.findViewById(R.id.dias_dias_evento);
        hora = view.findViewById(R.id.dias_hora_evento);
        min = view.findViewById(R.id.dias_minutos_evento);
        seg = view.findViewById(R.id.dias_seg_evento);
        titulo = view.findViewById(R.id.titulo_home_evento);
        img_banner = view.findViewById(R.id.img_fondo_evento);
        img_perfil = view.findViewById(R.id.img_perfil_evento);
        fecha = view.findViewById(R.id.fecha_home_evento);
        sinconfirmar = view.findViewById(R.id.invitados_sin_confirmar_evento);
        confirmar = view.findViewById(R.id.invitados_confirmados_evento);
        msn = view.findViewById(R.id.msn_home_evento);
        tareas = view.findViewById(R.id.tareas_total_evento);
        tareas_hechas = view.findViewById(R.id.tareas_hechas_evento);
    }

    public void Init(final String id){
        mDialog.show();

        Log.e("ERROR","DENTRO DE CREATEVIEW");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //JSON
                            JSONObject jsonObject = new JSONObject(response);
                            listTarea.clear();

                            try{
                                JSONArray jsonArrayTarea = jsonObject.getJSONArray("tarea");
                                for (int i = 0;i<jsonArrayTarea.length();i++)
                                {
                                    JSONObject object = jsonArrayTarea.getJSONObject(i);
                                    listTarea.add(new TareaClass(
                                            object.getInt("id"),
                                            object.getString("idevento"),
                                            object.getString("posicion"),
                                            object.getString("mes"),
                                            object.getString("ta"),
                                            object.getInt("estado")));
                                }

                                sessionCumple.createSessionTarea(listTarea);


                            }catch (Exception e){
                                Log.e("ERROR","SIN TAREAS");
                            }


                            listNote.clear();

                            try {
                                JSONArray jsonArraynota = jsonObject.getJSONArray("nota");
                                for (int i = 0; i < jsonArraynota.length(); i++) {
                                    JSONObject object = jsonArraynota.getJSONObject(i);
                                    listNote.add(new NotaClass(
                                            object.getInt("id"),
                                            object.getString("idevento"),
                                            object.getString("titulo"),
                                            object.getString("fecha"),
                                            object.getString("color"),
                                            object.getString("contenido")));
                                }

                                sessionCumple.createSessionNotes(listNote);

                            }catch (Exception e){
                                Log.e("ERROR","SIN NOTAS");
                            }

                            listGasto.clear();

                            try{
                                JSONArray jsonArraygasto = jsonObject.getJSONArray("gasto");
                                for (int i = 0;i<jsonArraygasto.length();i++)
                                {
                                    JSONObject object = jsonArraygasto.getJSONObject(i);
                                    listGasto.add(new GastosClass(
                                            object.getInt("id"),
                                            object.getString("idevento"),
                                            object.getString("titulo"),
                                            object.getString("proveedor"),
                                            object.getString("dinero"),
                                            object.getString("fecha"),
                                            object.getString("tipo")));
                                }

                                sessionCumple.createSessionGasto(listGasto);

                            }catch (Exception e){
                                Log.e("ERROR","SIN GASTOS");
                            }

                            listInvited.clear();

                            try {

                                JSONArray jsonArrayinvited = jsonObject.getJSONArray("invitado");
                                for (int i = 0; i < jsonArrayinvited.length(); i++) {
                                    JSONObject object = jsonArrayinvited.getJSONObject(i);
                                    listInvited.add(new InvitadosClass(
                                            object.getInt("id"),
                                            object.getString("idevento"),
                                            object.getString("nombre"),
                                            object.getInt("adultos"),
                                            object.getInt("ninos"),
                                            object.getString("celular"),
                                            object.getString("tipo"),
                                            object.getString("estado")));
                                }

                                sessionCumple.createSessionInvited(listInvited);

                            }catch (Exception e){
                                Log.e("ERROR","SIN INVITADOS");
                            }
                            listCuota.clear();

                            try {

                                JSONArray jsonArraycuota = jsonObject.getJSONArray("cuota");
                                for (int i = 0; i < jsonArraycuota.length(); i++) {
                                    JSONObject object = jsonArraycuota.getJSONObject(i);
                                    listCuota.add(new CuotaClass(
                                            object.getInt("id"),
                                            object.getInt("idgatos"),
                                            object.getInt("idevento"),
                                            object.getString("numCuota"),
                                            object.getString("fecha"),
                                            object.getString("dinero"),
                                            object.getString("estado")));
                                }

                                sessionCumple.createSessionCuota(listCuota);

                            }catch (Exception e){
                                Log.e("ERROR","SIN CUOTAS");
                            }

                            InitData();

                            mDialog.dismiss();

                        } catch (JSONException e) {
                            mDialog.dismiss();

                            Toast.makeText(getContext(),
                                    "Error. Por favor intentelo mas tarde, gracias.", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mDialog.dismiss();

                        Toast.makeText(getContext(),
                                "Error de conexión, por favor verifique el acceso a internet.", Toast.LENGTH_SHORT)
                                .show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void calculateInvited(){
        if(sessionCumple.isInvited()){

            listInvited = sessionCumple.readInvited();

            int yes = 0;
            int no = 0;

            for (InvitadosClass item: listInvited){
                if(item.getEstado().equals("1")){
                    yes = yes + Integer.valueOf(item.getAdultos()) + Integer.valueOf(item.getNinyos()) + 1;
                }else{
                    no = no + Integer.valueOf(item.getAdultos()) + Integer.valueOf(item.getNinyos()) + 1;
                }
            }
            sinconfirmar.setText(String.valueOf(yes));
            confirmar.setText(String.valueOf(no));

        }else{
            sinconfirmar.setText("0");
            confirmar.setText("0");
        }
    }

    private void calculateTask(){
        if(sessionCumple.isTarea()){
            listTarea = sessionCumple.readTarea();

            tareas.setText(String.valueOf(listTarea.size()));

            int num = 0;

            for (TareaClass item: listTarea){
                if(item.getEstado() == 1)
                    num ++;
            }

            tareas_hechas.setText(String.valueOf(num));

        }else{
            tareas.setText("0");
            tareas_hechas.setText("0");
        }
    }

    @Override
    public void onStart() {
        InitData();
        super.onStart();
    }
}
