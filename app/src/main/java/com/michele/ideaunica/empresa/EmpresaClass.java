package com.michele.ideaunica.empresa;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class EmpresaClass {

    private int ID;
    private String Titulo;
    private String Color;
    private String Nivel;
    private String Codigo;
    private String Categoria;
    private String Direccion;
    private String Descripcion;
    private String Numero;
    private String Whatsapp;
    private String Facebook;
    private String NomFacebook;
    private String Instagram;
    private String NomInstagram;
    private String PagewWeb;
    private String email;
    private String Url;
    private String UrlToolBar;

    public EmpresaClass(int ID, String titulo, String color, String nivel, String codigo, String categoria, String direccion, String descripcion, String numero, String whatsapp, String facebook, String nomFacebook, String instagram, String nomInstagram, String pagewWeb, String email, String url, String urlToolBar) {
        this.ID = ID;
        Titulo = titulo;
        Color = color;
        Nivel = nivel;
        Codigo = codigo;
        Categoria = categoria;
        Direccion = direccion;
        Descripcion = descripcion;
        Numero = numero;
        Whatsapp = whatsapp;
        Facebook = facebook;
        NomFacebook = nomFacebook;
        Instagram = instagram;
        NomInstagram = nomInstagram;
        PagewWeb = pagewWeb;
        this.email = email;
        Url = url;
        UrlToolBar = urlToolBar;
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

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getNivel() {
        return Nivel;
    }

    public void setNivel(String nivel) {
        Nivel = nivel;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    public String getWhatsapp() {
        return Whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        Whatsapp = whatsapp;
    }

    public String getFacebook() {
        return Facebook;
    }

    public void setFacebook(String facebook) {
        Facebook = facebook;
    }

    public String getNomFacebook() {
        return NomFacebook;
    }

    public void setNomFacebook(String nomFacebook) {
        NomFacebook = nomFacebook;
    }

    public String getInstagram() {
        return Instagram;
    }

    public void setInstagram(String instagram) {
        Instagram = instagram;
    }

    public String getNomInstagram() {
        return NomInstagram;
    }

    public void setNomInstagram(String nomInstagram) {
        NomInstagram = nomInstagram;
    }

    public String getPagewWeb() {
        return PagewWeb;
    }

    public void setPagewWeb(String pagewWeb) {
        PagewWeb = pagewWeb;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getUrlToolBar() {
        return UrlToolBar;
    }

    public void setUrlToolBar(String urlToolBar) {
        UrlToolBar = urlToolBar;
    }
}