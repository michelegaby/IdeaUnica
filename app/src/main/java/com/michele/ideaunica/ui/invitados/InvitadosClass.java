package com.michele.ideaunica.ui.invitados;

public class InvitadosClass {

    private int ID;
    private String idevento;
    private String nombre;
    private int adultos;
    private int ninyos;
    private String celular;
    private String tipo;
    private String estado;

    public InvitadosClass(int ID, String nombre, int adultos, int ninyos, String celular, String tipo) {
        this.ID = ID;
        this.nombre = nombre;
        this.adultos = adultos;
        this.ninyos = ninyos;
        this.celular = celular;
        this.tipo = tipo;
    }

    public InvitadosClass(int ID, String idevento, String nombre, int adultos, int ninyos, String celular, String tipo, String estado) {
        this.ID = ID;
        this.idevento = idevento;
        this.nombre = nombre;
        this.adultos = adultos;
        this.ninyos = ninyos;
        this.celular = celular;
        this.tipo = tipo;
        this.estado = estado;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getIdevento() {
        return idevento;
    }

    public void setIdevento(String idevento) {
        this.idevento = idevento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getnombre() {
        return nombre;
    }

    public void setnombre(String nombre) {
        this.nombre = nombre;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
