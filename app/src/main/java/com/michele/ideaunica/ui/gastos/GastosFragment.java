package com.michele.ideaunica.ui.gastos;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.michele.ideaunica.MainActivity;
import com.michele.ideaunica.R;

public class GastosFragment extends Fragment {

    
    View view;
    public static int ID;

    //Componentes
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gastos, container, false);
        inicializarComponentes();
        Bundle parametros = getActivity().getIntent().getExtras();
        ID = parametros.getInt("ID",0);
        return view;
    }

    private void inicializarComponentes() {
        viewPager = view.findViewById(R.id.viewpaper_gatos);
        tabLayout = view.findViewById(R.id.tabs_gastos);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        ViewPagerGastosAdapter adapter = new ViewPagerGastosAdapter(getChildFragmentManager());
        Bundle m = new Bundle();
        m.putInt("ID",ID);

        ResumenFragment resumenFragment = new ResumenFragment();
        resumenFragment.setArguments(m);
        adapter.AddFragment(resumenFragment,"Resumen");

        APagarFragment aPagarFragment = new APagarFragment();
        aPagarFragment.setArguments(m);
        adapter.AddFragment(aPagarFragment,"A Pagar");

        PagoFragment pagoFragment = new PagoFragment();
        pagoFragment.setArguments(m);
        adapter.AddFragment(pagoFragment,"Pago");

        VencidoFragment vencidoFragment = new VencidoFragment();
        vencidoFragment.setArguments(m);
        adapter.AddFragment(vencidoFragment,"Vencido");

        TodoFragment todoFragment = new TodoFragment();
        todoFragment.setArguments(m);
        adapter.AddFragment(todoFragment,"Todo");

        viewPager.setAdapter(adapter);
    }
}
