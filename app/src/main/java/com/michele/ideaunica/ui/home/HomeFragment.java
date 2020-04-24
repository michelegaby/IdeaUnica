package com.michele.ideaunica.ui.home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

import com.bumptech.glide.Glide;
import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {

    View view;
    private static int ID;

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
    private String EVENT_DATE_TIME = "01/02/2020 00:00:00";
    private String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private Handler handler = new Handler();
    private Runnable runnable;
    private TextView msn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        Bundle parametros = getActivity().getIntent().getExtras();
        ID = parametros.getInt("ID", 0);
        InicializarComponentes();
        InicializarDatos();
        GenerarInicializacionInvitados();
        GenerarTarea();
        return view;
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

    private void GenerarInicializacionInvitados() {
        try {

            BDEvento obj = new BDEvento(getContext(), "bdEvento", null, 1);
            SQLiteDatabase bd = obj.getReadableDatabase();
            if (bd != null) {
                int si = 0;
                int no = 0;
                Cursor objCursor = bd.rawQuery("Select * from invitados where idevento = " + ID, null);
                while (objCursor.moveToNext()) {
                    if(objCursor.getString(7).equals("CONFIRMADO"))
                    {
                        si = si + objCursor.getInt(3) + 1 + objCursor.getInt(4);
                    }else {
                        no = no + objCursor.getInt(3) + 1 + objCursor.getInt(4);
                    }
                }
                sinconfirmar.setText(String.valueOf(no));
                confirmar.setText(String.valueOf(si));
            }
            bd.close();
        } catch (Exception E) {
            Toast.makeText(getContext(), "Error:" + E.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void GenerarTarea() {
        try {
            BDEvento obj = new BDEvento(getContext(), "bdEvento", null, 1);
            SQLiteDatabase bd = obj.getReadableDatabase();
            if (bd != null) {
                Cursor objCursor = bd.rawQuery("Select * from tarea where estado = 2 and idevento = " + ID, null);
                tareas_hechas.setText(objCursor.getCount() + "");
            }
            bd.close();
        } catch (Exception E) {
            Toast.makeText(getContext(), "Error:" + E.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void InicializarDatos() {
        try {
            BDEvento obj = new BDEvento(getContext(), "bdEvento", null, 1);
            SQLiteDatabase bd = obj.getReadableDatabase();
            if (bd != null) {
                Cursor objCursor = bd.rawQuery("Select * from evento where id = " + ID, null);
                while (objCursor.moveToNext()) {
                    if(objCursor.getString(7).equals("HABILITADO"))
                    {
                        titulo.setText(objCursor.getString(1));

                        EVENT_DATE_TIME = objCursor.getString(2) + " " + objCursor.getString(3) + ":00";
                        try {
                            SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
                            SimpleDateFormat formateador = new SimpleDateFormat("d MMMM, yyyy");

                            Date date = parseador.parse(objCursor.getString(2));
                            fecha.setText(formateador.format(date));
                            if(objCursor.getString(4)!= null && !objCursor.getString(4).isEmpty())
                            {
                                File imgFile = new  File(objCursor.getString(4));
                                Uri imageUri = Uri.fromFile(imgFile);
                                Glide.with(getActivity())
                                        .load(imageUri)
                                        .placeholder(R.drawable.cargando)
                                        .error(R.drawable.fondorosa)
                                        .into(img_perfil);
                            }
                            if(objCursor.getString(5)!= null && !objCursor.getString(5).isEmpty())
                            {
                                File imgFile = new  File(objCursor.getString(5));
                                Uri imageUri = Uri.fromFile(imgFile);
                                Glide.with(getActivity())
                                        .load(imageUri)
                                        .placeholder(R.drawable.fondorosa)
                                        .error(R.drawable.fondorosa)
                                        .into(img_banner);
                            }
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "ERROR " + e.getStackTrace(), Toast.LENGTH_LONG).show();
                        }
                        countDownStart();
                    }
                }
            }
            bd.close();
        } catch (Exception E) {
            Toast.makeText(getContext(), E.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
