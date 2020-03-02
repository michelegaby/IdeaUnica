package com.michele.ideaunica.ui.evento;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.michele.ideaunica.BDEvento;
import com.michele.ideaunica.R;
import com.michele.ideaunica.cumple.NuevoEvento;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditarEventoFragment extends Fragment{

    View view;
    private static int ID;
    //componentes
    private EditText fecha;
    private EditText titulo;
    private EditText hora;
    private Button guardar;
    private CalendarView calendario;
    private ImageView fondo;
    private ImageView photo;
    final String MyAlbum = "IdeaUnica";
    Bitmap fotobitmapSrc;
    Bitmap fondobitmapSrc;
    final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    private boolean estadofondo=false;
    private boolean estadofoto=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_editar_evento, container, false);
        Bundle parametros = getActivity().getIntent().getExtras();
        ID = parametros.getInt("ID", 0);
        InicializarComponentes();
        InicializarDatos();

        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime();
            }
        });
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String f = dayOfMonth+"/"+(month+1)+"/"+year;
                fecha.setText(f);
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/");
                startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),11);
            }
        });
        fondo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/");
                startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),10);
            }
        });


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titulo.getText().toString().trim().equals("") || fecha.getText().toString().trim().equals("") || hora.getText().toString().trim().equals(""))
                {
                    msn("Complete todos los datos por favor");
                }
                else
                {
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                getActivity(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            Toast.makeText(getContext(),
                                    "shouldShowRequestPermissionRationale",
                                    Toast.LENGTH_LONG).show();
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                        } else {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    }else{
                        Guardar(titulo.getText().toString().trim(),
                                fecha.getText().toString().trim(),
                                hora.getText().toString().trim());
                    }
                }
            }
        });

        return view;
    }
    public void msn(String mss){
        Toast.makeText(getContext(),mss,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(resultCode==getActivity().RESULT_OK){
                Uri path = data.getData();
                if(requestCode==10)
                {
                    try {
                        fondobitmapSrc = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                        fondo.setImageURI(path);
                        estadofondo=true;
                    } catch (IOException e) {
                        Toast.makeText(getContext(),"error",Toast.LENGTH_LONG).show();
                    }
                }
                if(requestCode==11) {
                    try {
                        fotobitmapSrc = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                        photo.setImageURI(path);
                        estadofoto=true;
                    } catch (IOException e) {
                        Toast.makeText(getContext(),"error",Toast.LENGTH_LONG).show();
                    }
                }
            }
        }catch (Exception e){
            msn("No selecciono ninguna imagen");
        }
    }

    private void InicializarComponentes() {
        fondo=view.findViewById(R.id.img_fondo_editar_evento);
        titulo=view.findViewById(R.id.titulo_editar_evento);
        fecha=view.findViewById(R.id.fecha_editar_evento);
        hora=view.findViewById(R.id.hora_editar_evento);
        calendario=view.findViewById(R.id.calendario_editar_evento);
        photo=view.findViewById(R.id.photo_editar_evento);
        guardar=view.findViewById(R.id.guardar_editar_evento);
    }


    private void showTime() {

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog dialog =
                new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String min="";
                        String hor="";
                        if(minute<10){
                            min="0"+minute;
                        }
                        else
                        {
                            min=minute+"";
                        }
                        if(hourOfDay<10){
                            hor="0"+hourOfDay;
                        }
                        else
                        {
                            hor=hourOfDay+"";
                        }
                        String time = hor+":"+min;
                        hora.setText(time);
                    }
                }, mHour, mMinute, true);
        dialog.show();
    }

    private void InicializarDatos() {
        try {
            BDEvento obj = new BDEvento(getContext(), "bdEvento", null, 1);
            SQLiteDatabase bd = obj.getReadableDatabase();
            if (bd != null) {
                Cursor objCursor = bd.rawQuery("Select * from evento where id=" + ID, null);
                while (objCursor.moveToNext()) {
                    titulo.setText(objCursor.getString(1));
                    fecha.setText(objCursor.getString(2));
                    hora.setText(objCursor.getString(3));
                    try {
                        if(objCursor.getString(4)!= null && !objCursor.getString(4).isEmpty())
                        {
                            File imgFile = new  File(objCursor.getString(4));
                            Uri imageUri = Uri.fromFile(imgFile);
                            Glide.with(getActivity())
                                    .load(imageUri)
                                    .placeholder(R.drawable.fondorosa)
                                    .error(R.drawable.fondorosa)
                                    .into(photo);
                        }
                        if(objCursor.getString(5)!= null && !objCursor.getString(5).isEmpty())
                        {
                            File imgFile = new  File(objCursor.getString(5));
                            Uri imageUri = Uri.fromFile(imgFile);
                            Glide.with(getActivity())
                                    .load(imageUri)
                                    .placeholder(R.drawable.fondorosa)
                                    .error(R.drawable.fondorosa)
                                    .into(fondo);
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "ERROR " + e.getStackTrace(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            bd.close();
        } catch (Exception E) {
            Toast.makeText(getContext(), "Error Nose porque", Toast.LENGTH_SHORT).show();
        }
    }

    public File getPublicAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (file.mkdirs()) {
            Toast.makeText(getContext(),
                    file.getAbsolutePath() + " creado",
                    Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getContext(),
                    "Directory no creado", Toast.LENGTH_LONG).show();
        }
        return file;
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

            Toast.makeText(getContext(),"File saved: \n" + file.getAbsolutePath(),Toast.LENGTH_LONG).show();

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

            Toast.makeText(getContext(),"File saved: \n" + file.getAbsolutePath(),Toast.LENGTH_LONG).show();

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
            String update="";
            if(estadofoto==true && estadofondo==true)
            {
                update="update evento set titulo='"+titulo+"',fecha='"+fecha+"', hora='"+Hora+"', urlfoto='"+GuardarFoto()+"', urlfondo='"+GuardarFondo()+"' where id="+ID;
            }else
            {
                if(estadofoto==true){

                    update="update evento set titulo='"+titulo+"',fecha='"+fecha+"', hora='"+Hora+"', urlfoto='"+GuardarFoto()+"' where id="+ID;

                }else{
                    if(estadofondo==true)
                    {
                        update="update evento set titulo='"+titulo+"',fecha='"+fecha+"', hora='"+Hora+"', urlfondo='"+GuardarFondo()+"' where id="+ID;
                    }else
                    {
                        update="update evento set titulo='"+titulo+"',fecha='"+fecha+"', hora='"+Hora+"' where id="+ID;
                    }
                }
            }

            try {
                BDEvento objEvento= new BDEvento(getContext(),"bdEvento",null,1);
                SQLiteDatabase bd = objEvento.getWritableDatabase();
                if(bd!=null){
                    bd.execSQL(update);
                    msn("Se Guardo Correctamente");
                }
                bd.close();
                getActivity().onBackPressed();

            }catch (Exception E){
                msn("ERROR");
            }
        }catch (Exception E){
            msn("ERROR");
        }
    }
}


