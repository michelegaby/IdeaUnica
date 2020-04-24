package com.michele.ideaunica.empresa;

public class UbicacionClass {

    private int ID;
    private String Sucursal;
    private String Direccion;
    private String Url;

    public UbicacionClass(int ID, String sucursal, String direccion, String url) {
        this.ID = ID;
        Sucursal = sucursal;
        Direccion = direccion;
        Url = url;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSucursal() {
        return Sucursal;
    }

    public void setSucursal(String sucursal) {
        Sucursal = sucursal;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
