package com.michele.ideaunica.cursos;

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
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.michele.ideaunica.ui.notas.NuevaNota;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CursosFragment extends Fragment {

    View view;
    //Componentes
    private RecyclerView rv_cursos;
    private ProgressBar progress;
    private SwipeRefreshLayout swipeRefreshLayout;
    //Complementos
    AdaptadorCategoriaCurso adaptadorCategoriaCurso;
    private ArrayList<CategoriaCursosClass> listCategoriaCurso = new ArrayList<>();
    private static  String URL="https://ideaunicabolivia.com/apps/categoria_cursos.php";
    private RecyclerView.LayoutManager manager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_cursos,container,false);
        InicializarComponentes();
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
                listCategoriaCurso.clear();
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

    public static CursosFragment newInstance(){
        CursosFragment fragment=new CursosFragment();
        Bundle args=new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void InicializarComponentes() {
        rv_cursos=view.findViewById(R.id.Categoria_Curso_recyclerview);
        progress=view.findViewById(R.id.progress_Categoria_Curso);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout_cursos);
    }
    public void GenerarDatos(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("categoria-cursos");
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                CategoriaCursosClass cursos=
                                        new CategoriaCursosClass(
                                                object.getString("id"),
                                                object.getString("titulo"),
                                                object.getString("descripcion"),
                                                object.getString("url"));
                                listCategoriaCurso.add(cursos);
                            }
                            adaptadorCategoriaCurso= new
                                    AdaptadorCategoriaCurso(
                                    getContext(),listCategoriaCurso);
                            manager = new GridLayoutManager(getContext(),2);
                            rv_cursos.setLayoutManager(manager);
                            progress.setVisibility(View.GONE);
                            rv_cursos.setVisibility(View.VISIBLE);
                            adaptadorCategoriaCurso.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(getContext(), Cursos.class);
                                    Bundle parametros = new Bundle();
                                    parametros.putString("id",
                                            String.valueOf(listCategoriaCurso.get(rv_cursos.getChildAdapterPosition(v)).getId()));
                                    parametros.putString("titulo",
                                            String.valueOf(listCategoriaCurso.get(rv_cursos.getChildAdapterPosition(v)).getTitulo()));
                                    i.putExtras(parametros);
                                    startActivity(i);
                                }
                            });
                            rv_cursos.setAdapter(adaptadorCategoriaCurso);
                            progress.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),
                                    "Error 1", Toast.LENGTH_LONG)
                                    .show();
                            progress.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),
                                "Error  2"+error.getMessage(), Toast.LENGTH_LONG)
                                .show();
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
