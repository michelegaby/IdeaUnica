package com.michele.ideaunica.departamento;

public class CategoriaClass {

    private int ID;
    private String Titulo;
    private String Cantidad;
    private String Url;

    public CategoriaClass(int ID, String titulo, String cantidad, String url) {
        this.ID = ID;
        Titulo = titulo;
        Cantidad = cantidad;
        Url = url;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
