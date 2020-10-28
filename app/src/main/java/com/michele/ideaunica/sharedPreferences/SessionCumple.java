package com.michele.ideaunica.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.michele.ideaunica.ui.gastos.CuotaClass;
import com.michele.ideaunica.ui.gastos.GastosClass;
import com.michele.ideaunica.ui.invitados.InvitadosClass;
import com.michele.ideaunica.ui.notas.NotaClass;
import com.michele.ideaunica.ui.tareas.TareaClass;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SessionCumple {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "FIESTA";
    private static final String ISFIESTA = "IS_FIESTA";
    private static final String ID = "ID";
    private static final String TITULO = "TITULO";
    private static final String FECHA = "FECHA";
    private static final String HORA = "HORA";
    private static final String URLPERFIL = "URLPERFIL";
    private static final String URLFONDO = "URLFONDO";
    private static final String PRESUPUESTO = "PRESUPUESTO";


    private static final String ISDATOS = "IS_DATOS";

    public static final String INVITED = "INVITED";
    public static final String ISINVITED = "IS_INVITED";

    public static final String NOTES = "NOTES";
    public static final String ISNOTES = "IS_NOTES";

    public static final String TAREA = "TAREA";
    public static final String ISTAREA = "IS_TAREA";

    public static final String GASTO = "GASTO";
    public static final String ISGASTO = "IS_GASTO";

    public static final String CUOTAS = "CUOTAS";
    public static final String ISCUOTAS = "IS_CUOTAS";

    public SessionCumple(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String id, String titulo,String fecha,String hora,String urlperfil,String urlfondo, String presupuesto){
        editor.putBoolean(ISFIESTA,true);
        editor.putString(ID,id);
        editor.putString(TITULO,titulo);
        editor.putString(FECHA,fecha);
        editor.putString(HORA,hora);
        editor.putString(URLPERFIL,urlperfil);
        editor.putString(URLFONDO,urlfondo);
        editor.putString(PRESUPUESTO,presupuesto);
        editor.apply();
    }

    public void createData(boolean estado) {
        editor.putBoolean(ISDATOS,estado);
        editor.apply();
    }

    public boolean isData(){
        return sharedPreferences.getBoolean(ISDATOS,false);
    }

    //Invited
    public void createSessionInvited(List<InvitadosClass> listInvited){

        Gson gson = new Gson();
        String jsonString = gson.toJson(listInvited);
        editor.putString(INVITED, jsonString);
        editor.putBoolean(ISINVITED,true);
        editor.apply();
    }

    public ArrayList<InvitadosClass> readInvited(){

        String jsonString = sharedPreferences.getString(INVITED,"");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<InvitadosClass>>(){}.getType();
        ArrayList<InvitadosClass> list = gson.fromJson(jsonString, type);

        return list;
    }

    //Note
    public void createSessionNotes(List<NotaClass> listnote){

        Gson gson = new Gson();
        String jsonString = gson.toJson(listnote);
        editor.putString(NOTES, jsonString);
        editor.putBoolean(ISNOTES,true);
        editor.apply();
    }

    public ArrayList<NotaClass> readNotes(){

        String jsonString = sharedPreferences.getString(NOTES,"");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<NotaClass>>(){}.getType();
        ArrayList<NotaClass> list = gson.fromJson(jsonString, type);

        return list;
    }

    //TAREA
    public void createSessionTarea(List<TareaClass> listtarea){

        Gson gson = new Gson();
        String jsonString = gson.toJson(listtarea);
        editor.putString(TAREA, jsonString);
        editor.putBoolean(ISTAREA,true);
        editor.apply();
    }

    public ArrayList<TareaClass> readTarea(){

        String jsonString = sharedPreferences.getString(TAREA,"");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<TareaClass>>(){}.getType();
        ArrayList<TareaClass> list = gson.fromJson(jsonString, type);

        return list;
    }

    //GASTO
    public void createSessionGasto(List<GastosClass> listGasto){

        Gson gson = new Gson();
        String jsonString = gson.toJson(listGasto);
        editor.putString(GASTO, jsonString);
        editor.putBoolean(ISGASTO,true);
        editor.apply();
    }

    public ArrayList<GastosClass> readGasto(){

        String jsonString = sharedPreferences.getString(GASTO,"");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GastosClass>>(){}.getType();
        ArrayList<GastosClass> list = gson.fromJson(jsonString, type);

        return list;
    }

    //CUOTAS
    public void createSessionCuota(List<CuotaClass> listCuota){

        Gson gson = new Gson();
        String jsonString = gson.toJson(listCuota);
        editor.putString(CUOTAS, jsonString);
        editor.putBoolean(ISCUOTAS,true);
        editor.apply();
    }

    public ArrayList<CuotaClass> readCuota(){

        String jsonString = sharedPreferences.getString(CUOTAS,"");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<CuotaClass>>(){}.getType();
        ArrayList<CuotaClass> list = gson.fromJson(jsonString, type);

        return list;
    }

    public boolean isFiesta(){
        return sharedPreferences.getBoolean(ISFIESTA,false);
    }

    public boolean isInvited(){
        return sharedPreferences.getBoolean(ISINVITED,false);
    }

    public boolean isNotes(){
        return sharedPreferences.getBoolean(ISNOTES,false);
    }

    public boolean isTarea(){
        return sharedPreferences.getBoolean(ISTAREA,false);
    }

    public boolean isGasto(){
        return sharedPreferences.getBoolean(ISGASTO,false);
    }

    public boolean isCuota(){
        return sharedPreferences.getBoolean(ISCUOTAS,false);
    }

    public String getId(){
        return sharedPreferences.getString(ID,null);
    }

    public String getTitulo(){
        return sharedPreferences.getString(TITULO,null);
    }

    public String getFecha(){
        return sharedPreferences.getString(FECHA,null);
    }

    public String getHora(){
        return sharedPreferences.getString(HORA,null);
    }

    public String getUrlperfil(){
        return sharedPreferences.getString(URLPERFIL,null);
    }

    public String getUrlfondo(){
        return sharedPreferences.getString(URLFONDO,null);
    }

    public String getPresupuesto(){
        return sharedPreferences.getString(URLFONDO,null);
    }

    public void shopClean(){
        editor.clear();
        editor.commit();
    }
}
