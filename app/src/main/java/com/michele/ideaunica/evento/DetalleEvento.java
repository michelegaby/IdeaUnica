package com.michele.ideaunica.evento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.ByteArrayLoader;
import com.michele.ideaunica.R;
import com.michele.ideaunica.blog.MySpannable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetalleEvento extends AppCompatActivity {

    private ImageView img;
    private TextView titulo;
    private TextView fecha;
    private TextView descripcion;
    private RecyclerView rv_galeria;
    private boolean isOpen = false ;
    private ConstraintSet layout1,layout2;
    private ConstraintLayout constraintLayout ;
    private ImageView imageViewPhoto;

    AdaptadorGaleriaEvento adaptadorGaleriaEvento;
    private ArrayList<GaleriaEventoClass> listGaleria = new ArrayList<>();
    private static  String URL="https://sice.com.bo/ideaunica/apps/evento_galeria.php";
    private RecyclerView.LayoutManager manager;
    private static String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_evento);

        Window w = getWindow();
        w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorblanco), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setTitle("");

        InicializarComponentes();
        Bundle parametros = this.getIntent().getExtras();
        Glide.with(getApplicationContext()).load(parametros.getString("url"))
                .placeholder(R.drawable.fondorosa)
                .error(R.drawable.fondorosa)
                .into(img);
        titulo.setText(parametros.getString("titulo"));
        fecha.setText(parametros.getString("fecha"));
        ID=parametros.getString("ID");

        layout1 = new ConstraintSet();
        layout2 = new ConstraintSet();
        layout2.clone(this,R.layout.duplex_detalle_evento);
        layout1.clone(constraintLayout);
        descripcion.setText(parametros.getString("descripcion"));
        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOpen) {
                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout2.applyTo(constraintLayout);
                    rv_galeria.setVisibility(View.VISIBLE);
                    descripcion.setMaxLines(Integer.MAX_VALUE);
                    titulo.setTextColor(Color.BLACK);
                    fecha.setTextColor(Color.BLACK);
                    descripcion.setTextColor(Color.BLACK);
                    isOpen = !isOpen ;
                }
                else {
                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout1.applyTo(constraintLayout);
                    rv_galeria.setVisibility(View.GONE);
                    descripcion.setMaxLines(5);
                    titulo.setTextColor(Color.WHITE);
                    fecha.setTextColor(Color.WHITE);
                    descripcion.setTextColor(Color.WHITE);
                    isOpen = !isOpen ;
                }
            }
        });
        GenerarDatos();

        titulo.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/toledo-serial-bold.ttf"));
    }


    private void GenerarDatos() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("galeria");

                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                listGaleria.add(new GaleriaEventoClass(
                                        object.getInt("id"),
                                        object.getString("descripcion"),
                                        object.getString("url")));
                            }


                            adaptadorGaleriaEvento = new AdaptadorGaleriaEvento(getApplicationContext(), listGaleria);
                            manager = new GridLayoutManager(getApplicationContext(),2);

                            rv_galeria.setLayoutManager(manager);
                            rv_galeria.setAdapter(adaptadorGaleriaEvento);
                            rv_galeria.addOnItemTouchListener(new AdaptadorGaleriaEvento.RecyclerTouchListener(getApplicationContext(), rv_galeria, new AdaptadorGaleriaEvento.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("images", listGaleria);
                                    bundle.putInt("position", position);

                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    SliderShowGaleriaEvento newFragment = SliderShowGaleriaEvento.newInstance();
                                    newFragment.setArguments(bundle);
                                    newFragment.show(ft, "slideshow");
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Error  2"+error.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cod",ID);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void InicializarComponentes() {
        img=findViewById(R.id.img_fondo_detalle_venta);
        titulo=findViewById(R.id.titulo_detalle_venta);
        fecha=findViewById(R.id.fecha_detalle_venta);
        descripcion=findViewById(R.id.txt_detalle_venta);
        imageViewPhoto = findViewById(R.id.abajo_detalle_evento);
        constraintLayout = findViewById(R.id.activity_detalle_evento);
        rv_galeria=findViewById(R.id.galeria_evento_recyclerview);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
