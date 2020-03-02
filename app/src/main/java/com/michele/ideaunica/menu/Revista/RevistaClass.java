package com.michele.ideaunica.menu.Revista;

public class RevistaClass {
    private int ID;
    private String titulo;
    private String fecha;
    private String Url;
    private String descripcion;

    public RevistaClass(int ID, String titulo, String fecha, String url, String descripcion) {
        this.ID = ID;
        this.titulo = titulo;
        this.fecha = fecha;
        Url = url;
        this.descripcion = descripcion;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
