package com.michele.ideaunica.cursos;

public class CursosClass {

    private String id;
    private String titulo;
    private String autor;
    private String fecha;
    private String cantidad;
    private String costo;
    private String contenido;
    private String tipo;
    private String departamento;
    private String descripcion;
    private String telefono;
    private String whatsapp;
    private String facebook;
    private String nombrefacebook;
    private String instagram;
    private String nombreinstagram;
    private String paginaweb;
    private String email;
    private String url;

    public CursosClass(String id, String autor, String fecha, String cantidad, String tipo, String titulo, String departamento, String descripcion, String telefono, String url) {
        this.id = id;
        this.autor = autor;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.titulo = titulo;
        this.departamento = departamento;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.url = url;
    }

    public CursosClass(String id, String titulo, String autor, String fecha, String cantidad, String costo, String contenido, String tipo, String departamento, String descripcion, String telefono, String whatsapp, String facebook, String nombrefacebook, String instagram, String nombreinstagram, String paginaweb, String email, String url) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.costo = costo;
        this.contenido = contenido;
        this.tipo = tipo;
        this.departamento = departamento;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.whatsapp = whatsapp;
        this.facebook = facebook;
        this.nombrefacebook = nombrefacebook;
        this.instagram = instagram;
        this.nombreinstagram = nombreinstagram;
        this.paginaweb = paginaweb;
        this.email = email;
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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getNombrefacebook() {
        return nombrefacebook;
    }

    public void setNombrefacebook(String nombrefacebook) {
        this.nombrefacebook = nombrefacebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getNombreinstagram() {
        return nombreinstagram;
    }

    public void setNombreinstagram(String nombreinstagram) {
        this.nombreinstagram = nombreinstagram;
    }

    public String getPaginaweb() {
        return paginaweb;
    }

    public void setPaginaweb(String paginaweb) {
        this.paginaweb = paginaweb;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

