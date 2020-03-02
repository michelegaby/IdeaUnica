package com.michele.ideaunica.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.michele.ideaunica.R;
import com.michele.ideaunica.menu.Contacto.Contactos;
import com.michele.ideaunica.menu.Entrevista.Entrevistas;
import com.michele.ideaunica.menu.Revista.Revistas;
import com.michele.ideaunica.menu.evento.Eventos;

public class MenuFragment extends Fragment{

    View view;

    private CardView evento;
    private CardView revista;
    private CardView entrevista;
    private CardView contactanos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_menu,container,false);
        InicialiazarComponentes();
        CLICK();
        return view;
    }

    private void CLICK() {
        evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Eventos.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        revista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Revistas.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        entrevista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Entrevistas.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        contactanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Contactos.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }

    private void InicialiazarComponentes() {
        evento=view.findViewById(R.id.evento_menu);
        revista=view.findViewById(R.id.revista_menu);
        entrevista=view.findViewById(R.id.entrevista_menu);
        contactanos=view.findViewById(R.id.contactanos_menu);
    }
}
