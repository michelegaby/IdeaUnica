package com.michele.ideaunica.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String ISLOGIN = "IS_LOGIN";
    private static final String ID = "ID";
    private static final String PROVIDER = "PROVIDER";
    private static final String IDPROVIDER = "IDPROVIDER";
    private static final String NOMBRE = "NOMBRE";
    private static final String EMAIL = "EMAIL";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String id, String provider,String idprovider,String nombre,String email){
        editor.putBoolean(ISLOGIN,true);
        editor.putString(ID,id);
        editor.putString(PROVIDER,provider);
        editor.putString(IDPROVIDER,idprovider);
        editor.putString(NOMBRE,nombre);
        editor.putString(EMAIL,email);
        editor.apply();
    }

    public boolean isLogin(){
        return sharedPreferences.getBoolean(ISLOGIN,false);
    }

    public String getId(){
        return sharedPreferences.getString(ID,null);
    }

    public String getProvider(){
        return sharedPreferences.getString(PROVIDER,null);
    }

    public String getIdprovider(){
        return sharedPreferences.getString(IDPROVIDER,null);
    }

    public String getNombre(){
        return sharedPreferences.getString(NOMBRE,null);
    }

    public String getEmail(){
        return sharedPreferences.getString(EMAIL,null);
    }

    public void shopClean(){
        editor.clear();
        editor.commit();
    }
}