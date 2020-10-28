package com.michele.ideaunica.ui.invitados;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.R;
import com.michele.ideaunica.sharedPreferences.SessionCumple;
import com.michele.ideaunica.ui.notas.NotaClass;
import com.michele.ideaunica.ui.notas.NuevaNota;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class InvitadosConfirmadosFragment  extends Fragment {

    View view;
    SessionCumple sessionCumple;

    //Componentes
    private FloatingActionButton fab;
    private RecyclerView myrecyclerview;
    private ProgressBar progress;
    private SwipeRefreshLayout swipeRefreshLayout;

    //Complementos
    AdaptadorInvitadoConfirmado adaptadorInvitado;
    private ArrayList<InvitadosClass> listInvitados = new ArrayList<>();

    android.app.AlertDialog mDialog;

    private static  String URL = "https://www.ideaunicabolivia.com/apps/fiesta/UpdateEstadoInvitado.php";

    public InvitadosConfirmadosFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_invitados_confirmados,container,false);

        InicializarComponentes();

        sessionCumple = new SessionCumple(getContext());
        mDialog = new SpotsDialog.Builder().setContext(getContext()).setMessage("Espera un momento por favor").build();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NuevoInvitado.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void InicializarComponentes() {
        fab = view.findViewById(R.id.fab_fragment_confirmado);
        myrecyclerview = view.findViewById(R.id.Invitado_confirmar_recyclerview);
        progress = view.findViewById(R.id.progress_invitados_confirmar);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout_invitados);
    }

    private void GenerarDatos() {
        if(sessionCumple.isInvited()){
            listInvitados.clear();

            ArrayList<InvitadosClass> listInvitadosList = new ArrayList<>();

            listInvitadosList = sessionCumple.readInvited();

            for (InvitadosClass item: listInvitadosList) {
                if (item.getEstado().equals("1")) {
                    listInvitados.add(item);
                }
            }

            progress.setVisibility(View.GONE);
            adaptadorInvitado = new AdaptadorInvitadoConfirmado(getContext(), listInvitados);
            myrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            myrecyclerview.setAdapter(adaptadorInvitado);
            adaptadorInvitado.setOnItemClickListener(new AdaptadorInvitadoConfirmado.OnItemClickListener() {
                @Override
                public void onModificarClick(int position) {
                    Intent intent = new Intent(getContext(), EditarInvitado.class);
                    Bundle parametros = new Bundle();
                    parametros.putInt("ID",listInvitados.get(position).getID());
                    parametros.putString("nom",listInvitados.get(position).getNombre());
                    parametros.putString("adulto",String.valueOf(listInvitados.get(position).getAdultos()));
                    parametros.putString("nin",String.valueOf(listInvitados.get(position).getNinyos()));
                    parametros.putString("cel",listInvitados.get(position).getCelular());
                    parametros.putString("tipo",listInvitados.get(position).getTipo());
                    parametros.putString("confirmar",listInvitados.get(position).getEstado());
                    intent.putExtras(parametros);
                    startActivity(intent);
                }

                @Override
                public void onDesConfirmarClick(final int position) {
                    AlertDialog.Builder Advertencia = new AlertDialog.Builder(getContext());
                    Advertencia.setTitle("Desconfirmar");
                    Advertencia.setMessage("Esta seguro que desea deconfirmar?");
                    Advertencia.setCancelable(false);
                    Advertencia.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //DesConfirmarInvitado(position);
                            GuardarStatus(listInvitados.get(position).getID(),"0");
                        }
                    });
                    Advertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    Advertencia.show();
                }
            });
        }
    }

    public void GuardarStatus(final int idinvitado,final String estado){

        mDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            final JSONObject jsonObject = new JSONObject(response);
                            Boolean isStatus = jsonObject.getBoolean("estado");

                            Log.e("ERROR","ESTADO: " + isStatus);
                            if(!isStatus) {

                                AlertDialog.Builder Advertencia = new AlertDialog.Builder(getContext());

                                Advertencia.setTitle("Error");
                                Advertencia.setMessage("No se guardo, lo sentimos mucho");
                                Advertencia.setCancelable(false);

                                Advertencia.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                Advertencia.show();
                            }else {

                                ArrayList<InvitadosClass> listInvitadosList = new ArrayList<>();

                                listInvitadosList = sessionCumple.readInvited();

                                for(InvitadosClass item: listInvitadosList){
                                    if(item.getID() == idinvitado){

                                        item.setEstado("0");
                                        break;
                                    }
                                }
                                sessionCumple.createSessionInvited(listInvitadosList);
                                GenerarDatos();

                            }

                            mDialog.dismiss();

                        } catch (Exception e) {
                            mDialog.dismiss();

                            AlertDialog.Builder Advertencia = new AlertDialog.Builder(getContext());

                            Advertencia.setTitle("Error");
                            Advertencia.setMessage("Por favor intentelo mas tarde, gracias.");
                            Advertencia.setCancelable(false);

                            Advertencia.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            Advertencia.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mDialog.dismiss();

                        AlertDialog.Builder Advertencia = new AlertDialog.Builder(getContext());

                        Advertencia.setTitle("Error");
                        Advertencia.setMessage("Error de conexión, por favor verifique el acceso a internet.");
                        Advertencia.setCancelable(false);

                        Advertencia.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        Advertencia.show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",String.valueOf(idinvitado));
                params.put("estado",String.valueOf(estado));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onStart() {
        GenerarDatos();
        super.onStart();
    }
}
