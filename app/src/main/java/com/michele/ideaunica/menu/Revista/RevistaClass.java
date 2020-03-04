package com.michele.ideaunica.menu.Revista;

public class RevistaClass {
    private int ID;
    private String titulo;
    private String fecha;
    private String Url;
    private String descripcion;
    private String autor;
    private String descripcion_final;
    private String facebook;
    private String whatsapp;
    private String instagram;
    private String email;


    public RevistaClass(int ID, String titulo, String fecha, String url, String descripcion) {
        this.ID = ID;
        this.titulo = titulo;
        this.fecha = fecha;
        Url = url;
        this.descripcion = descripcion;
    }

    public RevistaClass(int ID, String titulo, String fecha, String url, String descripcion, String autor) {
        this.ID = ID;
        this.titulo = titulo;
        this.fecha = fecha;
        Url = url;
        this.descripcion = descripcion;
        this.autor = autor;
    }

    public RevistaClass(int ID, String titulo, String fecha, String url, String descripcion, String autor, String descripcion_final, String facebook, String whatsapp, String instagram, String email) {
        this.ID = ID;
        this.titulo = titulo;
        this.fecha = fecha;
        Url = url;
        this.descripcion = descripcion;
        this.autor = autor;
        this.descripcion_final = descripcion_final;
        this.facebook = facebook;
        this.whatsapp = whatsapp;
        this.instagram = instagram;
        this.email = email;
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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescripcion_final() {
        return descripcion_final;
    }

    public void setDescripcion_final(String descripcion_final) {
        this.descripcion_final = descripcion_final;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
