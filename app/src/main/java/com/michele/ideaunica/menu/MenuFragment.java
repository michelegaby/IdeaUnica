package com.michele.ideaunica.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.michele.ideaunica.R;
import com.michele.ideaunica.menu.Contacto.Contactos;
import com.michele.ideaunica.menu.Entrevista.Entrevistas;
import com.michele.ideaunica.menu.Revista.Revistas;
import com.michele.ideaunica.menu.evento.Eventos;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MenuFragment extends Fragment{

    View view;

    //Componentes
    private CardView evento;
    private CardView revista;
    private CardView entrevista;
    private CardView contactanos;
    private SliderLayout sliderLayout;

    //Complemento
    private static  String URL = "https://ideaunicabolivia.com/apps/publicidad.php";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu,container,false);
        InicialiazarComponentes();
        CLICK();
        GenerarPublicidad();
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setScrollTimeInSec(3);
        return view;
    }

    private void CLICK() {
        evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Eventos.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        revista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Revistas.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        entrevista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Entrevistas.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        contactanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Contactos.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }

    private void InicialiazarComponentes() {
        evento = view.findViewById(R.id.evento_menu);
        revista = view.findViewById(R.id.revista_menu);
        entrevista = view.findViewById(R.id.entrevista_menu);
        contactanos = view.findViewById(R.id.contactanos_menu);
        sliderLayout = view.findViewById(R.id.slider_publicidad_menu);
    }

    public void GenerarPublicidad(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray2 = jsonObject.getJSONArray("publicidad");
                            for (int i = 0; i < jsonArray2.length(); i++) {
                                DefaultSliderView sliderView = new DefaultSliderView(getContext());
                                JSONObject object = jsonArray2.getJSONObject(i);
                                sliderView.setImageUrl("https://sice.com.bo/ideaunica/"+object.getString("url"));
                                sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                                sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                                    @Override
                                    public void onSliderClick(SliderView sliderView) {
                                        Toast.makeText(getContext(),"ss",Toast.LENGTH_LONG).show();
                                    }
                                });
                                sliderLayout.addSliderView(sliderView);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),
                                    "Error", Toast.LENGTH_LONG)
                                    .show();
                            sliderLayout.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),
                                "Error de conexión"+error.getMessage(), Toast.LENGTH_LONG)
                                .show();
                        sliderLayout.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tipo","2");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
