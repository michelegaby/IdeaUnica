package com.michele.ideaunica.menu.evento;

public class EventoClass {
    private int ID;
    private String Url;
    private String titulo;
    private String fecha;
    private String fecha_final;
    private String descripcion;

    public EventoClass(int ID, String url, String titulo, String fecha, String fecha_final, String descripcion) {
        this.ID = ID;
        Url = url;
        this.titulo = titulo;
        this.fecha = fecha;
        this.fecha_final = fecha_final;
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

    public String getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }
}

