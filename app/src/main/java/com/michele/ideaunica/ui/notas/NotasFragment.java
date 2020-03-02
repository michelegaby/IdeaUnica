package com.michele.ideaunica.ui.notas;

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
import com.michele.ideaunica.R;

import java.util.ArrayList;

public class NotasFragment extends Fragment {

    View view;
    //componentes
    private RecyclerView rv_notas;
    private ProgressBar progressBar;
    private FloatingActionButton fab;

    //Complementos
    AdaptadorNota adaptadorNota;
    private ArrayList<NotaClass> listNotas = new ArrayList<>();

    private static int ID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_notas,container,false);
        InicializarComponentes();
        Bundle parametros = getActivity().getIntent().getExtras();
        ID=parametros.getInt("ID",0);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),NuevaNota.class);
                Bundle parm= new Bundle();
                parm.putInt("ID",ID);
                intent.putExtras(parm);
                startActivity(intent);
            }
        });

        GenerarDatos();
        return view;
    }
    private void InicializarComponentes() {
        rv_notas=view.findViewById(R.id.notas_fragment_recyclerview);
        progressBar=view.findViewById(R.id.progress_fragment_notas);
        fab=view.findViewById(R.id.fab_fragment_notas);
    }
    private void GenerarDatos() {

        try {
            BDEvento obj= new BDEvento(getContext(),"bdEvento",null,1);
            SQLiteDatabase bd= obj.getReadableDatabase();
            if(bd!=null){
                Cursor objCursor = bd.rawQuery("Select * from notas where idevento=" + ID, null);
                listNotas.clear();

                while (objCursor.moveToNext()){
                    listNotas.add(new NotaClass(objCursor.getInt(0),objCursor.getString(3),objCursor.getString(2),objCursor.getString(5),objCursor.getString(4)));
                }

                progressBar.setVisibility(View.GONE);
                adaptadorNota = new AdaptadorNota(getContext(), listNotas);
                adaptadorNota.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getContext(),Nota.class);
                        Bundle parametros = new Bundle();
                        parametros.putInt("ID",ID);
                        parametros.putInt("id",listNotas.get(rv_notas.getChildAdapterPosition(v)).getId());
                        intent.putExtras(parametros);
                        startActivity(intent);
                    }
                });
                rv_notas.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_notas.setAdapter(adaptadorNota);
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
