package com.michele.ideaunica.ui.invitados;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.R;
import com.michele.ideaunica.ui.notas.EditarNota;
import com.michele.ideaunica.ui.notas.NuevaNota;

import java.util.ArrayList;

public class InvitadosaConfirmarFragment extends Fragment {

    View view;

    //Componentes
    private FloatingActionButton fab;
    private RecyclerView myrecyclerview;
    private ProgressBar progress;
    private SwipeRefreshLayout swipeRefreshLayout;
    //Complementos
    AdaptadorInvitadoAConfirmar adaptadorInvitado;
    private ArrayList<InvitadosClass> listInvitados = new ArrayList<>();
    private static int ID;

    public InvitadosaConfirmarFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_invitados_a_confirmar,container,false);
        InicializarComponentes();

        Bundle parametros = getActivity().getIntent().getExtras();
        ID=parametros.getInt("ID",0);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NuevoInvitado.class);
                Bundle parmetros= new Bundle();
                parmetros.putInt("ID",ID);
                intent.putExtras(parmetros);
                startActivity(intent);
            }
        });

        GenerarDatos();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new UnaTarea().execute();

            }
        });
        return view;
    }


    private class UnaTarea extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                listInvitados.clear();
                GenerarDatos();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    private void InicializarComponentes() {
        fab = view.findViewById(R.id.fab);
        myrecyclerview=view.findViewById(R.id.Invitado_a_confirmar_recyclerview);
        progress=view.findViewById(R.id.progress_invitados_a_confirmar);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout_invitadosA);
    }

    private void GenerarDatos() {
        try {
            BDEvento obj = new BDEvento(getContext(), "bdEvento", null, 1);
            SQLiteDatabase bd = obj.getReadableDatabase();
            if (bd != null) {
                Cursor objCursor = bd.rawQuery("Select * from invitados where idevento=" + ID+" and estado='NOCONFIRMADO'", null);
                listInvitados.clear();

                while (objCursor.moveToNext()) {
                    listInvitados.add(new InvitadosClass(objCursor.getInt(0), objCursor.getString(2), objCursor.getInt(3), objCursor.getInt(4), objCursor.getString(5), objCursor.getString(6)));
                }
                progress.setVisibility(View.GONE);
                adaptadorInvitado = new AdaptadorInvitadoAConfirmar(getContext(), listInvitados);
                myrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                myrecyclerview.setAdapter(adaptadorInvitado);
                adaptadorInvitado.setOnItemClickListener(new AdaptadorInvitadoAConfirmar.OnItemClickListener(){
                    @Override
                    public void onElimanarClick(final int position) {

                        AlertDialog.Builder Advertencia= new AlertDialog.Builder(getContext());
                        Advertencia.setTitle("Eliminar");
                        Advertencia.setMessage("Esta seguro de eliminar a este Invitado?");
                        Advertencia.setCancelable(false);
                        Advertencia.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EliminarInvitado(position);
                            }
                        });
                        Advertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        Advertencia.show();
                    }

                    @Override
                    public void onModificarClick(int position) {
                        Intent intent=new Intent(getContext(), EditarInvitado.class);
                        Bundle parametros = new Bundle();
                        parametros.putInt("ID",listInvitados.get(position).getID());
                        parametros.putString("nom",listInvitados.get(position).getNombre());
                        parametros.putString("adulto",String.valueOf(listInvitados.get(position).getAdultos()));
                        parametros.putString("nin",String.valueOf(listInvitados.get(position).getNinyos()));
                        parametros.putString("cel",listInvitados.get(position).getCelular());
                        parametros.putString("tipo",listInvitados.get(position).getTipo());
                        parametros.putString("confirmar","ACONFIRMAR");
                        intent.putExtras(parametros);
                        startActivity(intent);

                    }

                    @Override
                    public void onConfirmarClick(int position) {
                        ConfirmarInvitado(position);
                    }
                });
            }
            bd.close();

        } catch (Exception E) {
            Toast.makeText(getContext(), "Error Nose porque", Toast.LENGTH_SHORT).show();
        }

    }

    private void ConfirmarInvitado(int posicion){
        try {
            BDEvento obj = new BDEvento(getContext(), "bdEvento", null, 1);
            SQLiteDatabase bd = obj.getReadableDatabase();
            if (bd != null) {

                bd.execSQL("update invitados set estado='CONFIRMADO' where id="+listInvitados.get(posicion).getID());
                adaptadorInvitado.removeItem(posicion);
            }
            bd.close();

        } catch (Exception E) {
            Toast.makeText(getContext(), "Error Nose", Toast.LENGTH_SHORT).show();
        }
    }
    private void EliminarInvitado(int posicion) {

        try {
            BDEvento obj = new BDEvento(getContext(), "bdEvento", null, 1);
            SQLiteDatabase bd = obj.getReadableDatabase();
            if (bd != null) {

                bd.execSQL("delete from invitados where id="+listInvitados.get(posicion).getID());
            }
            bd.close();
            adaptadorInvitado.removeItem(posicion);

        } catch (Exception E) {
            Toast.makeText(getContext(), "Error Nose", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        GenerarDatos();
        super.onStart();
    }
}

