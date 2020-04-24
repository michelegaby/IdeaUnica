package com.michele.ideaunica.cumple;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NuevoEvento extends AppCompatActivity implements  TimePickerDialog.OnTimeSetListener {

    //componentes
    private EditText fecha;
    private EditText titulo;
    private EditText hora;
    private Button guardar;
    private CalendarView calendario;
    private ImageView fondo;
    private ImageView photo;

    //Complementos
    final String MyAlbum = "IdeaUnica";
    Bitmap fotobitmapSrc;
    Bitmap fondobitmapSrc;
    final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private boolean estadofondo = false;
    private boolean estadofoto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_evento);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fecha_left, null);
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorblanco), PorterDuff.Mode.SRC_IN));
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        getSupportActionBar().setTitle("Nuevo Evento");
        inicializarComponentes();

        //Obtener fehca actual
        Date d = new Date();
        CharSequence s = DateFormat.format("d/MM/yyyy ", d.getTime());
        fecha.setText(s);

        //funcionalidad al querer guadar
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titulo.getText().toString().trim().equals("") || fecha.getText().toString().trim().equals("") || hora.getText().toString().trim().equals(""))
                {
                    msn("Complete todos los datos por favor");
                }
                else
                {
                    if (ContextCompat.checkSelfPermission(NuevoEvento.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(NuevoEvento.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                    }else{
                        Guardar(titulo.getText().toString().trim(),
                                fecha.getText().toString().trim(),
                                hora.getText().toString().trim());
                    }
                }
            }
        });

        //Funcionalidad para seleccionar una hora
        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        //funcionalidad para seleccionar la fecha
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String f = dayOfMonth+"/"+(month+1)+"/"+year;
                fecha.setText(f);
            }
        });
    }

    public File getPublicAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (file.mkdirs()) {
        }else{
        }
        return file;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if(requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE){
            return;
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri path = data.getData();
            if(requestCode == 10)
            {
                try {
                    fondobitmapSrc = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);
                    fondo.setImageURI(path);
                    estadofondo = true;
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
                }
            }
            if(requestCode == 11) {
                try {
                    fotobitmapSrc = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);
                    photo.setImageURI(path);
                    estadofoto = true;
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void onClickSeleccionarImagenfondo(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),10);
    }

    public void onClickSeleccionarImagenfoto(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),11);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void msn(String mss){
        Toast.makeText(getApplicationContext(),mss,Toast.LENGTH_SHORT).show();
    }

    private void inicializarComponentes() {
        fecha = findViewById(R.id.fecha_nuevo_evento);
        titulo = findViewById(R.id.titulo_nuevo_evento);
        hora = findViewById(R.id.hora_nuevo_evento);
        guardar = findViewById(R.id.guardar_nuevo_evento);
        calendario = findViewById(R.id.calendario_nuevo_evento);
        fondo = findViewById(R.id.img_fondo_nuevo_evento);
        photo = findViewById(R.id.photo_nuevo_evento);
    }

    private void showTimePickerDialog(){
        TimePickerDialog dialog = new TimePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.HOUR),
                Calendar.getInstance().get(Calendar.MINUTE),
                true);
        dialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String min = "";
        String hor = "";
        if(minute<10){
            min = "0"+minute;
        }
        else
        {
            min = minute+"";
        }
        if(hourOfDay<10){
            hor = "0"+hourOfDay;
        }
        else
        {
            hor = hourOfDay+"";
        }
        String time = hor+":"+min;
        hora.setText(time);
    }

    public String GuardarFondo(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyMMdd-hhmmss-SSS");
        String fileName = "imgFondo" + simpleDateFormat.format(new Date()) + ".jpg";
        File dir = getPublicAlbumStorageDir(MyAlbum);
        File file = new File(dir, fileName);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            fondobitmapSrc.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
            outputStream.flush();
            outputStream.close();
            //Toast.makeText(getApplicationContext(),"File saved: \n" + file.getAbsolutePath(),Toast.LENGTH_LONG).show();
            return file.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "error";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String GuardarFoto(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyMMdd-hhmmss-SSS");
        String fileName = "imgFoto" + simpleDateFormat.format(new Date()) + ".jpg";
        File dir = getPublicAlbumStorageDir(MyAlbum);
        File file = new File(dir, fileName);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            fotobitmapSrc.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
            outputStream.flush();
            outputStream.close();
            //Toast.makeText(getApplicationContext(),"File saved: \n" + file.getAbsolutePath(),Toast.LENGTH_LONG).show();
            return file.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "error";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public void Guardar(final String titulo,final String fecha, final String Hora){
        try {

            String insert = "";
            if(estadofoto == true && estadofondo == true)
            {
                insert = "insert into evento(id,titulo,fecha,hora,urlfoto,urlfondo,estado) values(?,'"+titulo+"','"+fecha+"','"+Hora+"','"+GuardarFoto()+"','"+GuardarFondo()+"','HABILITADO')";
            }else
            {
                if(estadofoto == true){
                    insert = "insert into evento(id,titulo,fecha,hora,urlfoto,estado) values(?,'"+titulo+"','"+fecha+"','"+Hora+"','"+GuardarFoto()+"','HABILITADO')";
                }else{
                    if(estadofondo == true)
                    {
                        insert = "insert into evento(id,titulo,fecha,hora,urlfondo,estado) values(?,'"+titulo+"','"+fecha+"','"+Hora+"','"+GuardarFondo()+"','HABILITADO')";
                    }else
                    {
                        insert = "insert into evento(id,titulo,fecha,hora,estado) values(?,'"+titulo+"','"+fecha+"','"+Hora+"','HABILITADO')";
                    }
                }
            }
            try {

                BDEvento objEvento = new BDEvento(getApplicationContext(),"bdEvento",null,1);
                SQLiteDatabase bd = objEvento.getWritableDatabase();
                if(bd != null){
                    bd.execSQL(insert);
                    msn("Se Guardo Correctamente");
                }
                bd.close();
                onBackPressed();

            }catch (Exception E){
                msn("ERROR");
            }

        }catch (Exception E){
            msn("ERROR");
        }
    }
}
