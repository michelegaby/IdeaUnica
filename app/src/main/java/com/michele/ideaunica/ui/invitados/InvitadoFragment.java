package com.michele.ideaunica.ui.invitados;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.michele.ideaunica.R;

public class InvitadoFragment extends Fragment {

    View view;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    public static int ID;
    public static InvitadoFragment newInstance() {
        return new InvitadoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.invitado_fragment, container, false);
        inicializarComponentes();
        Bundle parametros = getActivity().getIntent().getExtras();
        ID=parametros.getInt("ID",0);
        return view;
    }

    private void inicializarComponentes() {
        viewPager=view.findViewById(R.id.viewpaper_invitados);
        tabLayout=view.findViewById(R.id.tabs_invitados);
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
        ViewPagerInvitadosAdapter adapter= new ViewPagerInvitadosAdapter(getChildFragmentManager());
        Bundle m= new Bundle();
        m.putInt("ID",ID);

        InvitadosaConfirmarFragment resumenFragment= new InvitadosaConfirmarFragment();
        resumenFragment.setArguments(m);
        adapter.AddFragment(resumenFragment,"A Confirmar");

        InvitadosConfirmadosFragment aPagarFragment= new InvitadosConfirmadosFragment();
        aPagarFragment.setArguments(m);
        adapter.AddFragment(aPagarFragment,"Confirmados");

        viewPager.setAdapter(adapter);
    }

}
