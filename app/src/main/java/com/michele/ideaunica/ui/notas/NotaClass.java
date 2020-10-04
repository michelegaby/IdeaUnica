package com.michele.ideaunica.ui.notas;

public class NotaClass {

    private int id;
    private String idevento;
    private String titulo;
    private String fecha;
    private String Color;
    private String contenido;

    public NotaClass(int id, String titulo, String fecha, String color, String contenido) {
        this.id = id;
        this.titulo = titulo;
        this.fecha = fecha;
        Color = color;
        this.contenido = contenido;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(final String fecha) {
        this.fecha = fecha;
    }

    public String getColor() {
        return this.Color;
    }

    public void setColor(final String color) {
        this.Color = color;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
