package com.michele.ideaunica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.michele.ideaunica.blog.BlogFragment;
import com.michele.ideaunica.cumple.CumpleFragment;
import com.michele.ideaunica.cursos.CursosFragment;
import com.michele.ideaunica.departamento.DepartamentoFragment;
import com.michele.ideaunica.menu.MenuFragment;

public class IdeaUnica extends AppCompatActivity {

    private int STOREGE_PERMISSION_CODE = 1;

    final Fragment fragment1 = new DepartamentoFragment();
    final Fragment fragment2 = new CursosFragment();
    final Fragment fragment3 = new BlogFragment();
    final Fragment fragment4 = new CumpleFragment();
    final Fragment fragment5 = new MenuFragment();

    final FragmentManager fm = getSupportFragmentManager();

    Fragment active = fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_unica);
        getSupportActionBar().setTitle("Departamento");

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        fm.beginTransaction().add(R.id.fragment_container, fragment5, "5").hide(fragment5).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.fragment_container,fragment1, "1").commit();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            requestStoregePermission();
        }
    }

    private void requestStoregePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this)
                    .setTitle("Perminisos denegados")
                    .setMessage("Se necesitan los permisos para habilitar la sección de evento.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(IdeaUnica.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},STOREGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},STOREGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==STOREGE_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED &&
                    grantResults[1]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permisos Aceptados",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"Permisos Denegados",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                    getSupportActionBar().setTitle("Departamentos");
                    return true;

                case R.id.nav_cursos:
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                    getSupportActionBar().setTitle("Cursos");
                    return true;

                case R.id.nav_blog:
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    active = fragment3;
                    getSupportActionBar().setTitle("Blog Idea Unica");
                    return true;

                case R.id.nav_cumple:
                    fm.beginTransaction().hide(active).show(fragment4).commit();
                    active = fragment4;
                    getSupportActionBar().setTitle("Mi fiesta");
                    return true;

                case R.id.nav_evento:
                    fm.beginTransaction().hide(active).show(fragment5).commit();
                    active = fragment5;
                    getSupportActionBar().setTitle("Menú");
                    return true;
            }
            return false;
        }
    };
}
