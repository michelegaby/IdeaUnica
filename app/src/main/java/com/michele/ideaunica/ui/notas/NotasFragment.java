package com.michele.ideaunica.ui.notas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
import com.michele.ideaunica.sharedPreferences.SessionCumple;
import com.michele.ideaunica.ui.invitados.InvitadosClass;

import java.util.ArrayList;

public class NotasFragment extends Fragment {

    View view;

    SessionCumple sessionCumple;

    //componentes
    private RecyclerView rv_notas;
    private ProgressBar progressBar;
    private FloatingActionButton fab;

    //Complementos
    AdaptadorNota adaptadorNota;
    private ArrayList<NotaClass> listNotas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notas,container,false);

        InicializarComponentes();

        sessionCumple = new SessionCumple(getContext());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),NuevaNota.class);
                startActivity(intent);
            }
        });

        GetNotas();

        return view;
    }


    private void GetNotas(){

        if(sessionCumple.isNotes()){

            listNotas.clear();

            listNotas = sessionCumple.readNotes();

            progressBar.setVisibility(View.GONE);
            adaptadorNota = new AdaptadorNota(getContext(), listNotas);

            adaptadorNota.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(),Nota.class);
                    Bundle parametros = new Bundle();
                    parametros.putInt("id",listNotas.get(rv_notas.getChildAdapterPosition(v)).getId());
                    intent.putExtras(parametros);
                    startActivity(intent);
                }
            });

            rv_notas.setLayoutManager(new LinearLayoutManager(getContext()));
            rv_notas.setAdapter(adaptadorNota);

        }

    }

    private void InicializarComponentes() {
        rv_notas = view.findViewById(R.id.notas_fragment_recyclerview);
        progressBar = view.findViewById(R.id.progress_fragment_notas);
        fab = view.findViewById(R.id.fab_fragment_notas);
    }

    @Override
    public void onStart() {
        GetNotas();
        super.onStart();
    }
}
