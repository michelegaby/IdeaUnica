package com.michele.ideaunica.ui.tareas;

public class TareaClass {

    private int id;
    private String idevento;
    private String titulo;
    private int estado;

    public TareaClass(int id, String titulo, int estado) {
        this.id = id;
        this.titulo = titulo;
        this.estado = estado;
    }

    public TareaClass(int id, String idevento, String titulo, int estado) {
        this.id = id;
        this.idevento = idevento;
        this.titulo = titulo;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdevento() {
        return idevento;
    }

    public void setIdevento(String idevento) {
        this.idevento = idevento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
