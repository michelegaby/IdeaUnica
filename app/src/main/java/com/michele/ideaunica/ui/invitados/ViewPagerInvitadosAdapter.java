package com.michele.ideaunica.ui.invitados;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerInvitadosAdapter extends FragmentPagerAdapter {

    private final List<Fragment> lstFragment=new ArrayList<>();
    private final List<String> listTitles= new ArrayList<>();

    public  ViewPagerInvitadosAdapter(FragmentManager fm){
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return lstFragment.get(position);
    }

    @Override
    public int getCount() {
        return listTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitles.get(position);
    }

    public void AddFragment(Fragment fragment,String title){
        lstFragment.add(fragment);
        listTitles.add(title);
    }
}
