package com.michele.ideaunica.ui.invitados;

public class InvitadosClass {

    private int ID;
    private String Nombre;
    private int adultos;
    private int ninyos;
    private String celular;
    private String tipo;

    public InvitadosClass(int ID, String nombre, int adultos, int ninyos, String celular, String tipo) {
        this.ID = ID;
        Nombre = nombre;
        this.adultos = adultos;
        this.ninyos = ninyos;
        this.celular = celular;
        this.tipo = tipo;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getAdultos() {
        return adultos;
    }

    public void setAdultos(int adultos) {
        this.adultos = adultos;
    }

    public int getNinyos() {
        return ninyos;
    }

    public void setNinyos(int ninyos) {
        this.ninyos = ninyos;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
