package com.michele.ideaunica.blog;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
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
import com.michele.ideaunica.departamento.AdaptadorDepartamento;
import com.michele.ideaunica.departamento.Categoria;
import com.michele.ideaunica.departamento.DepartamentoClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlogFragment extends Fragment{

    View view;

    //componentes
    private RecyclerView timelineRv;
    private SwipeRefreshLayout swipeRefreshLayout;
    //Complementos
    private TimelineAdapter adapter;
    private List<TimelineClass> nData;
    private static  String URL="https://sice.com.bo/ideaunica/apps/blog.php";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_blog,container,false);

        iniRV();
        setupAdapter();
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
                nData.clear();
                setupAdapter();
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

    public static BlogFragment newInstance(){
        BlogFragment fragment=new BlogFragment();
        Bundle args=new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void iniRV() {
        timelineRv=view.findViewById(R.id.timelinedosfragment_rv);
        timelineRv.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout_blog);
    }

    private void setupAdapter() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("blog");
                            String fecha="";
                            nData= new ArrayList<>();
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);

                                if(!fecha.equals(object.getString("fecha")))
                                {
                                    fecha=object.getString("fecha");
                                    HeaderBlogClass headerBlogClass = new HeaderBlogClass(object.getString("fecha"));
                                    TimelineClass headerTimelineClass = new TimelineClass(headerBlogClass);
                                    nData.add(headerTimelineClass);
                                }
                                ///aaaaaa
                                PostBlogClass postBlogClass=
                                        new PostBlogClass(
                                                object.getString("id"),
                                                object.getString("hora"),
                                                object.getString("titulo"),
                                                object.getString("contenido"),
                                                object.getString("urlimg"),
                                                object.getString("logo"),
                                                object.getString("url"));
                                final TimelineClass postBlogTimelineClass= new TimelineClass(postBlogClass);

                                nData.add(postBlogTimelineClass);
                            }

                            adapter=new TimelineAdapter(getContext(),nData);
                            timelineRv.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),
                                    "Error 1", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),
                                "Error  2"+error.getMessage(), Toast.LENGTH_LONG)
                                .show();
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
