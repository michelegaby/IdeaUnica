package com.michele.ideaunica.ui.gastos;

public class CuotaClass {

    private int ID;
    private int idgato;
    private int idevento;
    private String numCuota;
    private String Fecha;
    private String Dinero;
    private String Estado;

    public CuotaClass(int ID, int idgato, int idevento, String numCuota, String fecha, String dinero, String estado) {
        this.ID = ID;
        this.idgato = idgato;
        this.idevento = idevento;
        this.numCuota = numCuota;
        Fecha = fecha;
        Dinero = dinero;
        Estado = estado;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIdgato() {
        return idgato;
    }

    public void setIdgato(int idgato) {
        this.idgato = idgato;
    }

    public int getIdevento() {
        return idevento;
    }

    public void setIdevento(int idevento) {
        this.idevento = idevento;
    }

    public String getNumCuota() {
        return numCuota;
    }

    public void setNumCuota(String numCuota) {
        this.numCuota = numCuota;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getDinero() {
        return Dinero;
    }

    public void setDinero(String dinero) {
        Dinero = dinero;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }
}
