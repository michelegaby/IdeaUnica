package com.michele.ideaunica.cursos;

public class GaleriaCursoClass {
    private int id;
    private String titulo;
    private String url;

    public GaleriaCursoClass(int id, String titulo, String url) {
        this.id = id;
        this.titulo = titulo;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
