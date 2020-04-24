package com.michele.ideaunica.cumple;

public class CumpleanyosClass {

    private int id;
    private String titulo;
    private String fecha;
    private String hora;
    private String urlfoto;
    private String urlfondo;

    public CumpleanyosClass(int id, String titulo, String fecha, String hora, String urlfoto, String urlfondo) {
        this.id = id;
        this.titulo = titulo;
        this.fecha = fecha;
        this.hora = hora;
        this.urlfoto = urlfoto;
        this.urlfondo = urlfondo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getUrlfondo() {
        return urlfondo;
    }

    public void setUrlfondo(String urlfondo) {
        this.urlfondo = urlfondo;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }
}

