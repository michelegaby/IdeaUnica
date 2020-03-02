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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PagoFragment extends Fragment {

    View view;
    //Componentes
    private RecyclerView recyclerView;

    //GastosTotal
    AdaptadorGastos adaptadorGastos;
    private ArrayList<GastosClass> listGastos = new ArrayList<>();

    public static int ID;

    public PagoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_pago, container, false);
        inicializarComponentes();
        Bundle parametros = getArguments();
        ID=parametros.getInt("ID",0);
        GenerarDatos();

        return view;
    }

    private void inicializarComponentes() {
        recyclerView=view.findViewById(R.id.Pago_recyclerview);
    }

    private void GenerarDatos() {
        try {

            BDEvento obj= new BDEvento(getContext(),"bdEvento",null,1);
            SQLiteDatabase bd= obj.getReadableDatabase();
            if(bd!=null){
                Cursor objCursor = bd.rawQuery("Select * from gastos where idevento=" + ID, null);
                listGastos.clear();

                while (objCursor.moveToNext()){
                    try {
                        BDEvento obj2= new BDEvento(getContext(),"bdEvento",null,1);
                        SQLiteDatabase bd2= obj2.getReadableDatabase();
                        if(bd2!=null){
                            Cursor objCursor2 = bd.rawQuery("Select * from cuotas where idgasto=" +
                                    objCursor.getInt(0), null);

                            int c= objCursor2.getCount();
                            while (objCursor2.moveToNext()){
                                if(objCursor2.getString(5).equals("Pagado"))
                                    listGastos.add(
                                            new GastosClass(
                                                    objCursor.getInt(0),
                                                    objCursor2.getInt(0),
                                                    objCursor.getString(2),
                                                    objCursor.getString(3),
                                                    objCursor2.getString(4),
                                                    objCursor.getString(6),
                                                    objCursor2.getString(3),
                                                    objCursor2.getString(2)+"/"+c,
                                                    objCursor2.getString(6)));

                            }
                        }
                        bd2.close();

                    }
                    catch (Exception E){
                        Toast.makeText(getContext(),"a",Toast.LENGTH_SHORT).show();
                    }
                }
                adaptadorGastos = new AdaptadorGastos(getContext(), listGastos);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adaptadorGastos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getContext(), MostrarGasto.class);
                        Bundle parametros = new Bundle();
                        parametros.putString("ID",
                                String.valueOf(listGastos.get(recyclerView.getChildAdapterPosition(v)).getID()));
                        parametros.putString("id",
                                String.valueOf(listGastos.get(recyclerView.getChildAdapterPosition(v)).getId()));
                        parametros.putString("titulo",
                                String.valueOf(listGastos.get(recyclerView.getChildAdapterPosition(v)).getTitulo()));
                        parametros.putString("proveedor",
                                String.valueOf(listGastos.get(recyclerView.getChildAdapterPosition(v)).getProveedor()));
                        parametros.putString("fecha",
                                String.valueOf(listGastos.get(recyclerView.getChildAdapterPosition(v)).getFecha()));
                        parametros.putString("dinero",
                                String.valueOf(listGastos.get(recyclerView.getChildAdapterPosition(v)).getDinero()));
                        parametros.putString("cuota",
                                String.valueOf(listGastos.get(recyclerView.getChildAdapterPosition(v)).getCuotas()));
                        parametros.putString("tipo",
                                String.valueOf(listGastos.get(recyclerView.getChildAdapterPosition(v)).getTipo()));
                        parametros.putString("comentario",
                                String.valueOf(listGastos.get(recyclerView.getChildAdapterPosition(v)).getComentario()));
                        parametros.putString("tipo",
                                String.valueOf(listGastos.get(recyclerView.getChildAdapterPosition(v)).getTipo()));
                        parametros.putString("ACCION", "FALSE");
                        i.putExtras(parametros);
                        startActivity(i);
                    }
                });
                recyclerView.setAdapter(adaptadorGastos);
            }
            bd.close();

        }
        catch (Exception E){
            Toast.makeText(getContext(),E.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onStart() {
        GenerarDatos();
        super.onStart();
    }
}
