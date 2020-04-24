package com.michele.ideaunica;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDEvento extends SQLiteOpenHelper
{
    private static final String CREAR_EVENTO = "create table evento (" +
            "id integer primary key autoincrement," +
            "titulo text not null," +
            "fecha text not null," +
            "hora text not null," +
            "urlfoto text," +
            "urlfondo text," +
            "presupuesto text," +
            "estado text not null );";

    private static final String CREAR_INVITADOS = "create table invitados (" +
            "id integer primary key autoincrement," +
            "idevento integer not null," +
            "nombre text not null," +
            "adultos integer not null," +
            "ninos integer not null," +
            "celular text not null," +
            "tipo text not null," +
            "estado text not null,"+
            "foreign key(idevento) references evento(id));";


    private static final String CREAR_NOTAS = "create table notas (" +
            "id integer primary key autoincrement," +
            "idevento integer not null," +
            "fecha text not null," +
            "titulo text not null," +
            "contenido text not null," +
            "color text not null," +
            "foreign key(idevento) references evento(id));";


    private static final String CREAR_GASTO = "create table gastos (" +
            "id integer primary key autoincrement," +
            "idevento integer not null," +
            "titulo text not null," +
            "proveedor text not null," +
            "dinero text not null," +
            "fecha text not null," +
            "tipo text not null," +
            "foreign key(idevento) references evento(id));";

    private static final String CREAR_CUOTAS = "create table cuotas (" +
            "id integer primary key autoincrement," +
            "idgasto integer not null," +
            "numCuota text not null," +
            "fecha text not null," +
            "dinero text not null," +
            "estado text not null," +
            "comentario text ," +
            "foreign key(idgasto) references gastos(id));";

    private static final String CREAR_TAREA = "create table tarea (" +
            "id integer primary key autoincrement," +
            "idevento integer not null," +
            "mes integer not null," +
            "tarea text not null," +
            "estado int default 0,"+
            "foreign key(idevento) references evento(id));";

    public BDEvento(Context context, String name,
                    SQLiteDatabase.CursorFactory factory,
                    int version)
    {
        super(context, name, factory, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(CREAR_EVENTO);
        sqLiteDatabase.execSQL(CREAR_INVITADOS);
        sqLiteDatabase.execSQL(CREAR_NOTAS);
        sqLiteDatabase.execSQL(CREAR_GASTO);
        sqLiteDatabase.execSQL(CREAR_CUOTAS);
        sqLiteDatabase.execSQL(CREAR_TAREA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tarea");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS cuotas");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS gastos");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS notas");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS invitados");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS evento");
        sqLiteDatabase.execSQL(CREAR_EVENTO);
        sqLiteDatabase.execSQL(CREAR_INVITADOS);
        sqLiteDatabase.execSQL(CREAR_NOTAS);
        sqLiteDatabase.execSQL(CREAR_GASTO);
        sqLiteDatabase.execSQL(CREAR_CUOTAS);
    }
}

