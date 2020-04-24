package com.michele.ideaunica.cursos;

public class CategoriaCursosClass {

    private String id;
    private String titulo;
    private String contenido;
    private String url;

    public CategoriaCursosClass(String id, String titulo, String contenido, String url) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

