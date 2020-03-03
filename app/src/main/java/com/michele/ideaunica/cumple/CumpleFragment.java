package com.michele.ideaunica.cumple;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.MainActivity;
import com.michele.ideaunica.R;

import java.util.ArrayList;

public class CumpleFragment extends Fragment {

    View view;

    private RecyclerView myrecyclerview;
    private FloatingActionButton nuevo;
    AdaptadorCumpleanyos adaptadorCumpleanyos;
    private ArrayList<CumpleanyosClass> listCumple = new ArrayList<>();
    private RecyclerView.LayoutManager manager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_cumple,container,false);
        InicializarComponentes();
        GenerarDatos();
        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NuevoEvento.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    public static CumpleFragment newInstance(){
        CumpleFragment fragment=new CumpleFragment();
        Bundle args=new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void InicializarComponentes() {
        myrecyclerview=view.findViewById(R.id.Cumpleanyos_recyclerview);
        nuevo=view.findViewById(R.id.btn_nuevo_evento);
    }

    private void GenerarDatos() {
        try {
            BDEvento obj= new BDEvento(getContext(),"bdEvento",null,1);
            SQLiteDatabase bd= obj.getReadableDatabase();
            if(bd!=null){
                Cursor objCursor=bd.rawQuery("Select * from evento where estado='HABILITADO'",null);
                listCumple.clear();
                while (objCursor.moveToNext()){
                    listCumple.add(new CumpleanyosClass(objCursor.getInt(0),objCursor.getString(1),objCursor.getString(2),objCursor.getString(3),objCursor.getString(4),objCursor.getString(5)));
                }
                adaptadorCumpleanyos= new AdaptadorCumpleanyos(getContext(),listCumple);
                myrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                myrecyclerview.setAdapter(adaptadorCumpleanyos);
                adaptadorCumpleanyos.setOnItemClickListener(new AdaptadorCumpleanyos.OnItemClickListener() {
                    @Override
                    public void onItenClick(int position) {
                        Intent intent= new Intent(getContext(), MainActivity.class);
                        Bundle parmetros= new Bundle();
                        parmetros.putInt("ID",listCumple.get(position).getId());
                        parmetros.putString("titulo",listCumple.get(position).getTitulo());
                        parmetros.putString("fecha",listCumple.get(position).getFecha());
                        parmetros.putString("hora",listCumple.get(position).getHora());
                        parmetros.putString("urlfoto",listCumple.get(position).getUrlfoto());
                        intent.putExtras(parmetros);
                        startActivity(intent);
                    }
                });
            }
            bd.close();
        }
        catch (Exception E){
            Toast.makeText(getContext(),"Error Nose porque",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        GenerarDatos();
        super.onStart();
    }
}
