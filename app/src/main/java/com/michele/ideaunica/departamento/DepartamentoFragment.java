package com.michele.ideaunica.departamento;

import android.content.Intent;
import android.os.AsyncTask;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.michele.ideaunica.R;
import com.michele.ideaunica.ui.notas.Nota;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DepartamentoFragment extends Fragment {

    View view;

    //Componentes
    private RecyclerView myrecyclerview;
    private ProgressBar progress;
    private SwipeRefreshLayout swipeRefreshLayout;

    //Complementos
    AdaptadorDepartamento adaptadorDepartamento;
    private ArrayList<DepartamentoClass> listDepartamento = new ArrayList<>();
    private static  String URL = "https://ideaunicabolivia.com/apps/departamento.php";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_departamentos,container,false);
        InicializarComponentes();
        GenerarDatos();

        //Refresh de los departamentos
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

    public static DepartamentoFragment newInstance(){
        DepartamentoFragment fragment = new DepartamentoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void InicializarComponentes() {
        myrecyclerview = view.findViewById(R.id.departamento_recyclerview);
        progress = view.findViewById(R.id.progress_departamento);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout_departamento);
    }

    public void GenerarDatos(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //JSON
                            JSONObject jsonObject = new JSONObject(response);

                            //vaciar el array listDepartamento
                            listDepartamento.clear();

                            //Obtencion de los departamentos
                            JSONArray jsonArray = jsonObject.getJSONArray("departamentos");
                            for (int i = 0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                DepartamentoClass departamento = new DepartamentoClass(object.getInt("id"),
                                        object.getString("nombre").trim(),object.getString("url").trim());
                                listDepartamento.add(departamento);
                            }

                            progress.setVisibility(View.GONE);
                            adaptadorDepartamento = new AdaptadorDepartamento(getContext(),listDepartamento);
                            myrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

                            //Funcion al seleccionar redireccionar a una lista(recyclerview) de todas las categorias del departamento seleccionada
                            adaptadorDepartamento.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(getContext(), Categoria.class);
                                    Bundle parametros = new Bundle();
                                    parametros.putString("departamento",
                                            String.valueOf(listDepartamento.get(myrecyclerview.getChildAdapterPosition(v)).getID()));
                                    parametros.putString("namedepartamento",
                                            String.valueOf(listDepartamento.get(myrecyclerview.getChildAdapterPosition(v)).getNombre()));
                                    i.putExtras(parametros);
                                    startActivity(i);
                                }
                            });

                            myrecyclerview.setAdapter(adaptadorDepartamento);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),
                                    "Error. Por favor intentelo mas tarde, gracias.", Toast.LENGTH_SHORT)
                                    .show();
                            listDepartamento.clear();
                            progress.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),
                                "Error de conexión, por favor verifique el acceso a internet.", Toast.LENGTH_SHORT)
                                .show();
                        listDepartamento.clear();
                        progress.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
