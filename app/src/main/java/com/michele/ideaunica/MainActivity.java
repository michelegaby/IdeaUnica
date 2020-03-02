package com.michele.ideaunica;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    public static int ID;
    public TextView navuser;
    public ImageView navphoto;

    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle parametros = this.getIntent().getExtras();
        ID=parametros.getInt("ID",0);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView= navigationView.getHeaderView(0);
        navuser = headerView.findViewById(R.id.usernameevento);
        navphoto= headerView.findViewById(R.id.userphotoevento);
        navuser.setText(parametros.getString("titulo","NO"));
        appBarLayout=findViewById(R.id.appbarEvento);


        if(parametros.getString("urlfoto","")
                != null && !parametros.getString("urlfoto","").isEmpty())
        {
            File imgFile = new  File(parametros.getString("urlfoto"));
            Uri imageUri = Uri.fromFile(imgFile);
            Glide.with(this)
                    .load(imageUri)
                    .placeholder(R.drawable.fondorosa)
                    .error(R.drawable.fondorosa)
                    .into(navphoto);
        }

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_gastos,R.id.nav_tareas,R.id.nav_invitados_a_confirmar,R.id.nav_invitados_confirmados,R.id.nav_notas,R.id.nav_calculadora, R.id.nav_editar)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId()==R.id.nav_gastos)
                {
                    appBarLayout.setTargetElevation(0);
                }else
                {
                    appBarLayout.setElevation(10);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_exits)
        {
            super.onBackPressed();
            super.onBackPressed();
        }

        if(item.getItemId()==R.id.action_eliminar_evento)
        {
            AlertDialog.Builder Advertencia= new AlertDialog.Builder(this);
            Advertencia.setTitle("Eliminar");
            Advertencia.setMessage("Esta seguro de eliminar esta evento?");
            Advertencia.setCancelable(false);
            Advertencia.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EliminarEvento();
                    MainActivity.super.onBackPressed();
                    MainActivity.super.onBackPressed();
                }
            });
            Advertencia.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            Advertencia.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void EliminarEvento() {
        try {
            BDEvento objEvento= new BDEvento(getApplicationContext(),"bdEvento",null,1);
            SQLiteDatabase bd = objEvento.getWritableDatabase();
            if(bd!=null){
                bd.execSQL("update evento set estado='DESHABILITADO' where id="+ID);
            }
            bd.close();
        }catch (Exception E){
            Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
