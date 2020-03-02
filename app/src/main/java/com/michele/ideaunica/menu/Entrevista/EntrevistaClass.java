package com.michele.ideaunica.menu.Entrevista;

public class EntrevistaClass {
    private int ID;
    private String Titulo;
    private String Url;

    public EntrevistaClass(int ID, String titulo, String url) {
        this.ID = ID;
        Titulo = titulo;
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
    public String getUrl() {
        return Url;
    }
    public void setUrl(String url) {
        Url = url;
    }
}
