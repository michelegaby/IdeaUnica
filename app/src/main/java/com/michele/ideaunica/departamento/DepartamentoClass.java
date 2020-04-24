package com.michele.ideaunica.departamento;

public class DepartamentoClass {

    private int ID;
    private String Nombre;
    private String Url;

    public DepartamentoClass(int ID, String nombre, String url) {
        this.ID = ID;
        Nombre = nombre;
        Url = url;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}

