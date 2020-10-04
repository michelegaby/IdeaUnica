package com.michele.ideaunica.cumple;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.login.LoginManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.LoginActivity;
import com.michele.ideaunica.MainActivity;
import com.michele.ideaunica.R;
import com.michele.ideaunica.SplashScreen;
import com.michele.ideaunica.departamento.AdaptadorCategoria;
import com.michele.ideaunica.departamento.Categoria;
import com.michele.ideaunica.departamento.CategoriaClass;
import com.michele.ideaunica.empresa.Empresas;
import com.michele.ideaunica.sharedPreferences.SessionManager;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class CumpleFragment extends Fragment {

    View view;

    SessionManager sessionManager;

    private RecyclerView myrecyclerview;
    private CardView logout;
    private FloatingActionButton nuevo;
    private TextView name;
    private TextView email;

    AdaptadorCumpleanyos adaptadorCumpleanyos;
    private ArrayList<CumpleanyosClass> listCumple = new ArrayList<>();
    private RecyclerView.LayoutManager manager;

    private static  String URL = "https://www.ideaunicabolivia.com/apps/fiesta/fiestaEventos.php";

    AlertDialog mDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cumple,container,false);
        InicializarComponentes();

        sessionManager = new SessionManager(getContext());

        mDialog = new SpotsDialog.Builder().setContext(getContext()).setMessage("Espera un momento por favor").build();

        name.setText(sessionManager.getNombre());
        email.setText(sessionManager.getEmail());

        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NuevoEvento.class);
                getActivity().startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();

            }
        });

        Init(sessionManager.getId());

        return view;
    }

    public static CumpleFragment newInstance(){
        CumpleFragment fragment = new CumpleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void InicializarComponentes() {
        myrecyclerview = view.findViewById(R.id.Cumpleanyos_recyclerview);
        nuevo = view.findViewById(R.id.btn_nuevo_evento);
        logout = view.findViewById(R.id.btn_logout);
        name = view.findViewById(R.id.name_log);
        email = view.findViewById(R.id.email_log);
    }


    public void Init(final String id){
        mDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //JSON
                            JSONObject jsonObject = new JSONObject(response);
                            listCumple.clear();

                            JSONArray jsonArray = jsonObject.getJSONArray("fiestaevento");
                            for (int i = 0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                listCumple.add(new CumpleanyosClass(
                                        object.getInt("id"),
                                        object.getString("titulo"),
                                        object.getString("fecha"),
                                        object.getString("hora"),
                                        object.getString("urlfoto"),
                                        object.getString("urlfondo"),
                                        object.getString("presupuesto")));
                            }

                            adaptadorCumpleanyos = new AdaptadorCumpleanyos(getContext(),listCumple);
                            myrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                            myrecyclerview.setAdapter(adaptadorCumpleanyos);

                            adaptadorCumpleanyos.setOnItemClickListener(new AdaptadorCumpleanyos.OnItemClickListener() {
                                @Override
                                public void onItenClick(int position) {
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    Bundle parmetros = new Bundle();
                                    parmetros.putInt("ID",listCumple.get(position).getId());
                                    parmetros.putString("titulo",listCumple.get(position).getTitulo());
                                    parmetros.putString("fecha",listCumple.get(position).getFecha());
                                    parmetros.putString("hora",listCumple.get(position).getHora());
                                    parmetros.putString("urlfoto",listCumple.get(position).getUrlfoto());
                                    intent.putExtras(parmetros);
                                    startActivity(intent);
                                }
                            });
                            mDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),
                                    "Error. Por favor intentelo mas tarde, gracias.", Toast.LENGTH_SHORT)
                                    .show();
                            mDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),
                                "Error de conexión, por favor verifique el acceso a internet.", Toast.LENGTH_SHORT)
                                .show();
                        mDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void LogOut(){

        sessionManager.shopClean();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(getContext(), SplashScreen.class);
        startActivity(intent);
        killActivity();

    }

    private void killActivity() {
        getActivity().finish();
    }
    @Override
    public void onStart() {
        //GenerarDatos();
        Init(sessionManager.getId());
        super.onStart();
    }
}
