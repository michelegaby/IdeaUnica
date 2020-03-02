package com.michele.ideaunica.ui.tareas;

public class TareaClass {
    private int id;
    private String titulo;
    private int estado;

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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public TareaClass(int id, String titulo, int estado) {
        this.id = id;
        this.titulo = titulo;
        this.estado = estado;
    }
}
