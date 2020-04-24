package com.michele.ideaunica.ui.gastos;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.R;
import com.michele.ideaunica.empresa.AdaptadorGaleriaEmpresa;
import com.michele.ideaunica.empresa.Empresa;
import com.michele.ideaunica.empresa.GaleriaEmpresaClass;
import com.michele.ideaunica.ui.notas.AdaptadorNota;
import com.michele.ideaunica.ui.notas.Nota;
import com.michele.ideaunica.ui.notas.NotaClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TodoFragment extends Fragment {

    View view;
    public static int ID;

    //Componentes
    private RecyclerView recyclerView;

    //GastosTotal
    AdaptadorGastosTotal adaptadorGastosTotal;
    private ArrayList<GastosTotalClass> listGastos = new ArrayList<>();

    public TodoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_todo, container, false);
        inicializarComponentes();
        Bundle parametros = getArguments();
        ID = parametros.getInt("ID", 0);
        GenerarDatos();
        return view;
    }

    private void inicializarComponentes() {
        recyclerView = view.findViewById(R.id.Todo_recyclerview);
    }

    private void iniciarDatos() {
        listGastos.clear();
        GastosTotalClass gastosTotalClass =
                new GastosTotalClass(
                        1,
                        "Titulo 1",
                        "500",
                        "100",
                        "400",
                        "0");

        listGastos.add(gastosTotalClass);

        adaptadorGastosTotal = new AdaptadorGastosTotal(getContext(), listGastos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptadorGastosTotal);
    }

    private void GenerarDatos() {
        try {
            BDEvento obj = new BDEvento(getContext(), "bdEvento", null, 1);
            SQLiteDatabase bd = obj.getReadableDatabase();
            if (bd != null) {
                Cursor objCursor = bd.rawQuery("Select * from gastos where idevento = " + ID, null);
                listGastos.clear();
                while (objCursor.moveToNext()) {
                    try {
                        BDEvento obj2 = new BDEvento(getContext(), "bdEvento", null, 1);
                        SQLiteDatabase bd2 = obj2.getReadableDatabase();
                        if (bd2 != null) {
                            Cursor objCursor2 = bd.rawQuery("Select * from cuotas where idgasto = " +
                                    objCursor.getInt(0), null);


                            double apagar = 0;
                            double pago = 0;
                            double vencido = 0;


                            Date d = new Date();
                            CharSequence s = DateFormat.format("dd/MM/yyyy", d.getTime());
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date date1 = sdf.parse(s.toString());

                            while (objCursor2.moveToNext()) {
                                if (objCursor2.getString(5).equals("Sin Pagar")) {
                                    Date date2 = sdf.parse(objCursor2.getString(3));
                                    if (date1.before(date2) || date1.equals(date2)) {
                                        apagar = apagar + Double.valueOf(objCursor2.getString(4));
                                    } else {
                                        vencido = vencido + Double.valueOf(objCursor2.getString(4));
                                    }
                                } else {
                                    pago = pago + Double.valueOf(objCursor2.getString(4));
                                }
                            }

                            listGastos.add(
                                    new GastosTotalClass(
                                            objCursor.getInt(0),
                                            objCursor.getString(2),
                                            String.valueOf(pago + apagar + vencido),
                                            String.valueOf(apagar),
                                            String.valueOf(pago),
                                            String.valueOf(vencido))
                            );
                        }
                        bd2.close();

                    } catch (Exception E) {
                        Toast.makeText(getContext(), E.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                    adaptadorGastosTotal = new AdaptadorGastosTotal(getContext(), listGastos);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adaptadorGastosTotal);
                }
            }
        } catch (Exception e) {
            Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        GenerarDatos();
        super.onStart();
    }
}
