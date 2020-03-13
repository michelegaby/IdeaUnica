package com.michele.ideaunica.ui.calculadora;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.R;
import com.michele.ideaunica.ui.notas.NuevaNota;

public class CalculadoraFragment extends Fragment{

    View view;
    //Componentes

    //Adulto
    private ImageButton masAdulto;
    private TextView Adulto;
    private ImageButton menosAdulto;

    //Nino
    private ImageButton masNino;
    private TextView Nino;
    private ImageButton menosNino;

    //Pastel
    private ImageButton masPastel;
    private TextView Pastel;
    private ImageButton menosPastel;

    //Dulce
    private ImageButton masDulce;
    private TextView Dulce;
    private ImageButton menosDulce;

    //Salado
    private ImageButton masSalado;
    private TextView Salado;
    private ImageButton menosSalado;

    //Sand
    private ImageButton masSand;
    private TextView Sand;
    private ImageButton menosSand;

    //Gaseosa
    private ImageButton masGas;
    private TextView Gas;
    private ImageButton menosGas;

    //Jugo
    private ImageButton masJugo;
    private TextView Jugo;
    private ImageButton menosJugo;

    //Agua
    private ImageButton masAgua;
    private TextView Agua;
    private ImageButton menosAgua;


    //AlcoholM
    /*
    private ImageButton masAlcoholM;
    private TextView AlcoholM;
    private ImageButton menosAcoholM;*/

    //Platos
    private ImageButton masPlatos;
    private TextView Platos;
    private ImageButton menosPlatos;

    //Vasos
    private ImageButton masVasos;
    private TextView Vasos;
    private ImageButton menosVasos;

    //Cuchara
    private ImageButton masCuchara;
    private TextView Cuchara;
    private ImageButton menosCuchara;

    //Tenedor
    private ImageButton masTenedor;
    private TextView Tenedor;
    private ImageButton menosTenedor;

    //Servilleta
    private ImageButton masServilleta;
    private TextView Servilleta;
    private ImageButton menosServilleta;

    //Copa
    private ImageButton masCopa;
    private TextView Copa;
    private ImageButton menosCopa;

    private Button Calcular;

    private static int ID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_calculadora,container,false);
        Bundle parametros = getActivity().getIntent().getExtras();
        ID=parametros.getInt("ID",0);
        InicializarComponentes();
        GenerarInicializacion();
        ButtonMenosMas();

        Calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Guardar();
            }
        });
        return view;
    }

    public void Guardar(){

        String SMS="";
        int persona=Integer.valueOf(Adulto.getText().toString())+Integer.valueOf(Nino.getText().toString());
        int personaadulto=Integer.valueOf(Adulto.getText().toString());
        int personanino=Integer.valueOf(Nino.getText().toString());

        SMS = "Cantidad total:<br/> <br/> ";

        SMS = SMS +String.valueOf((personaadulto*Double.valueOf(Pastel.getText().toString()))+(personanino*(Double.valueOf(Pastel.getText().toString())/2)))+" gr. de Pastel, <br/> ";
        SMS = SMS +String.valueOf((personaadulto*Double.valueOf(Dulce.getText().toString()))+(personanino*(Double.valueOf(Dulce.getText().toString())/2)))+" cantidad de Aperitivos dulces, <br/> ";
        SMS = SMS +String.valueOf((personaadulto*Double.valueOf(Salado.getText().toString()))+(personanino*(Double.valueOf(Salado.getText().toString())/2)))+" cantidad de Aperitivos salados, <br/> ";
        SMS = SMS +String.valueOf((personaadulto*Double.valueOf(Sand.getText().toString()))+(personanino*(Double.valueOf(Sand.getText().toString()))))+" cantindad de hamburguesa, <br/> ";
        SMS = SMS +String.valueOf((personaadulto*Double.valueOf(Gas.getText().toString()))+(personanino*(Double.valueOf(Gas.getText().toString())/2)))+" ml. de Gaseosa <br/> ";
        SMS = SMS +String.valueOf((personaadulto*Double.valueOf(Jugo.getText().toString()))+(personanino*(Double.valueOf(Jugo.getText().toString())/2)))+" ml. de jugo, <br/> ";
        SMS = SMS +String.valueOf((personaadulto*Double.valueOf(Agua.getText().toString()))+(personanino*(Double.valueOf(Agua.getText().toString())/2)))+" ml. de agua, <br/> <br/> ";



        SMS = SMS+ "<br/> Utensilios: <br/> <br/>";
        SMS = SMS +String.valueOf(persona*Integer.valueOf(Platos.getText().toString()))+" unidad/es de platos, <br/> ";
        SMS = SMS +String.valueOf(persona*Integer.valueOf(Vasos.getText().toString()))+" unidad/es de  vasos, <br/> ";
        SMS = SMS +String.valueOf(persona*Integer.valueOf(Tenedor.getText().toString()))+" unidad/es de  tenedores, <br/> ";
        SMS = SMS +String.valueOf(persona*Integer.valueOf(Servilleta.getText().toString()))+" cantidad de servilletas, <br/> ";
        SMS = SMS +String.valueOf(persona*Integer.valueOf(Copa.getText().toString()))+" cantidad de copas <br/> ";

        Intent intent=new Intent(getContext(), NuevaNota.class);
        Bundle parametros = new Bundle();
        parametros.putInt("ID",ID);
        parametros.putString("titulo","Aperitivos para "+personaadulto+" adulto(s) y "+ personanino+" niño(s)");
        parametros.putString("contenido",SMS);
        intent.putExtras(parametros);
        startActivity(intent);
    }

    private void ButtonMenosMas() {
        masAdulto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.valueOf(Adulto.getText().toString());
                Adulto.setText(String.valueOf(a+1));
            }
        });
        menosAdulto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Adulto.getText().equals("0")){
                    int a=Integer.valueOf(Adulto.getText().toString());
                    Adulto.setText(String.valueOf(a-1));
                }
            }
        });

        masNino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.valueOf(Nino.getText().toString());
                Nino.setText(String.valueOf(a+1));
            }
        });
        menosNino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Nino.getText().equals("0")){
                    int a=Integer.valueOf(Nino.getText().toString());
                    Nino.setText(String.valueOf(a-1));
                }
            }
        });


        masPastel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.valueOf(Pastel.getText().toString());
                Pastel.setText(String.valueOf(a+1));
            }
        });
        menosPastel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Pastel.getText().equals("0")){
                    int a=Integer.valueOf(Pastel.getText().toString());
                    Pastel.setText(String.valueOf(a-1));
                }
            }
        });


        masDulce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.valueOf(Dulce.getText().toString());
                Dulce.setText(String.valueOf(a+1));
            }
        });
        menosDulce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Dulce.getText().equals("0")){
                    int a=Integer.valueOf(Dulce.getText().toString());
                    Dulce.setText(String.valueOf(a-1));
                }
            }
        });


        masSalado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.valueOf(Salado.getText().toString());
                Salado.setText(String.valueOf(a+1));
            }
        });
        menosSalado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Salado.getText().equals("0")){
                    int a=Integer.valueOf(Salado.getText().toString());
                    Salado.setText(String.valueOf(a-1));
                }
            }
        });


        masSand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.valueOf(Sand.getText().toString());
                Sand.setText(String.valueOf(a+1));
            }
        });
        menosSand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Sand.getText().equals("0")){
                    int a=Integer.valueOf(Sand.getText().toString());
                    Sand.setText(String.valueOf(a-1));
                }
            }
        });


        masGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.valueOf(Gas.getText().toString());
                Gas.setText(String.valueOf(a+1));
            }
        });
        menosGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Gas.getText().equals("0")){
                    int a=Integer.valueOf(Gas.getText().toString());
                    Gas.setText(String.valueOf(a-1));
                }
            }
        });


        masJugo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.valueOf(Jugo.getText().toString());
                Jugo.setText(String.valueOf(a+1));
            }
        });
        menosJugo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Jugo.getText().equals("0")){
                    int a=Integer.valueOf(Jugo.getText().toString());
                    Jugo.setText(String.valueOf(a-1));
                }
            }
        });

        masAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.valueOf(Agua.getText().toString());
                Agua.setText(String.valueOf(a+1));
            }
        });
        menosAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Agua.getText().equals("0")){
                    int a=Integer.valueOf(Agua.getText().toString());
                    Agua.setText(String.valueOf(a-1));
                }
            }
        });




        //Utenciolios
        masPlatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.valueOf(Platos.getText().toString());
                Platos.setText(String.valueOf(a+1));
            }
        });
        menosPlatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Platos.getText().equals("0")){
                    int a=Integer.valueOf(Platos.getText().toString());
                    Platos.setText(String.valueOf(a-1));
                }
            }
        });

        masVasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.valueOf(Vasos.getText().toString());
                Vasos.setText(String.valueOf(a+1));
            }
        });
        menosVasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Vasos.getText().equals("0")){
                    int a=Integer.valueOf(Vasos.getText().toString());
                    Vasos.setText(String.valueOf(a-1));
                }
            }
        });

        masCuchara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.valueOf(Cuchara.getText().toString());
                Cuchara.setText(String.valueOf(a+1));
            }
        });
        menosCuchara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Cuchara.getText().equals("0")){
                    int a=Integer.valueOf(Cuchara.getText().toString());
                    Cuchara.setText(String.valueOf(a-1));
                }
            }
        });

        masTenedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.valueOf(Tenedor.getText().toString());
                Tenedor.setText(String.valueOf(a+1));
            }
        });
        menosTenedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Tenedor.getText().equals("0")){
                    int a=Integer.valueOf(Tenedor.getText().toString());
                    Tenedor.setText(String.valueOf(a-1));
                }
            }
        });

        masServilleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.valueOf(Servilleta.getText().toString());
                Servilleta.setText(String.valueOf(a+1));
            }
        });
        menosServilleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Servilleta.getText().equals("0")){
                    int a=Integer.valueOf(Servilleta.getText().toString());
                    Servilleta.setText(String.valueOf(a-1));
                }
            }
        });

        masCopa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.valueOf(Copa.getText().toString());
                Copa.setText(String.valueOf(a+1));
            }
        });
        menosCopa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Copa.getText().equals("0")){
                    int a=Integer.valueOf(Copa.getText().toString());
                    Copa.setText(String.valueOf(a-1));
                }
            }
        });

    }

    private void GenerarInicializacion() {
        try {
            BDEvento obj = new BDEvento(getContext(), "bdEvento", null, 1);
            SQLiteDatabase bd = obj.getReadableDatabase();
            if (bd != null) {
                int adul=0;
                int nin=0;
                Cursor objCursor = bd.rawQuery("Select * from invitados where idevento=" + ID+" and estado='CONFIRMADO'", null);

                while (objCursor.moveToNext()) {
                    adul= adul+objCursor.getInt(3)+1;
                    nin=nin+objCursor.getInt(4);
                }
                if(adul!=0||nin!=0){
                    Adulto.setText(String.valueOf(adul));
                    Nino.setText(String.valueOf(nin));
                }

            }
            bd.close();

        } catch (Exception E) {
            Toast.makeText(getContext(), "Error Nose porque", Toast.LENGTH_SHORT).show();
        }
    }

    private void InicializarComponentes() {

        masAdulto=view.findViewById(R.id.add_adulto);
        menosAdulto=view.findViewById(R.id.quitar_adulto);
        Adulto=view.findViewById(R.id.total_adulto);


        masNino=view.findViewById(R.id.add_ninyo);
        menosNino=view.findViewById(R.id.quitar_ninyo);
        Nino=view.findViewById(R.id.total_ninyo);


        masPastel=view.findViewById(R.id.pastel_add_adulto);
        menosPastel=view.findViewById(R.id.pastel_quitar_adulto);
        Pastel=view.findViewById(R.id.pastel_adulto);


        masDulce=view.findViewById(R.id.dulces_add_adulto);
        menosDulce=view.findViewById(R.id.dulces_quitar_adulto);
        Dulce=view.findViewById(R.id.dulces_adulto);

        masSalado=view.findViewById(R.id.salado_add_adulto);
        menosSalado=view.findViewById(R.id.salado_quitar_adulto);
        Salado=view.findViewById(R.id.salado_adulto);

        masSand=view.findViewById(R.id.sandwiches_add_adulto);
        menosSand=view.findViewById(R.id.sandwiches_quitar_adulto);
        Sand=view.findViewById(R.id.sandwiches_adulto);

        masGas=view.findViewById(R.id.gaseosa_add_adulto);
        menosGas=view.findViewById(R.id.gaseosa_quitar_adulto);
        Gas=view.findViewById(R.id.gaseosa_adulto);

        masJugo=view.findViewById(R.id.jugo_add_adulto);
        menosJugo=view.findViewById(R.id.jugo_quitar_adulto);
        Jugo=view.findViewById(R.id.jugo_adulto);

        masAgua=view.findViewById(R.id.agua_add_adulto);
        menosAgua=view.findViewById(R.id.agua_quitar_adulto);
        Agua=view.findViewById(R.id.agua_adulto);


        //utencilios

        masPlatos=view.findViewById(R.id.platos_add_persona);
        menosPlatos=view.findViewById(R.id.platos_quitar_persona);
        Platos=view.findViewById(R.id.platos_persona);

        masVasos=view.findViewById(R.id.vasos_add_persona);
        menosVasos=view.findViewById(R.id.vasos_quitar_persona);
        Vasos=view.findViewById(R.id.vasos_persona);

        masTenedor=view.findViewById(R.id.tenedores_add_persona);
        menosTenedor=view.findViewById(R.id.tenedores_quitar_persona);
        Tenedor=view.findViewById(R.id.tenedores_persona);


        masCuchara=view.findViewById(R.id.cucharas_add_persona);
        menosCuchara=view.findViewById(R.id.cucharas_quitar_persona);
        Cuchara=view.findViewById(R.id.cucharas_persona);

        masServilleta=view.findViewById(R.id.servilletas_add_persona);
        menosServilleta=view.findViewById(R.id.servilletas_quitar_persona);
        Servilleta=view.findViewById(R.id.servilletas_persona);

        masCopa=view.findViewById(R.id.copa_add_persona);
        menosCopa=view.findViewById(R.id.copa_quitar_persona);
        Copa=view.findViewById(R.id.copa_persona);

        Calcular=view.findViewById(R.id.calcular_comida);
    }

}
