package com.michele.ideaunica.menu.Entrevista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.michele.ideaunica.R;
import com.michele.ideaunica.menu.Revista.DetalleRevista;

public class DetalleEntrevista extends AppCompatActivity {

    private Button ver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_entrevista);
        ver=findViewById(R.id.abajo_detalle_evento);
        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), VerDetalleEntrevista.class);
                startActivity(intent);
            }
        });
    }
}
