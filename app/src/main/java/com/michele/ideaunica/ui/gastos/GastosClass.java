package com.michele.ideaunica.ui.gastos;

public class GastosClass {

    private int ID;
    private int id;
    private String Titulo;
    private String Proveedor;
    private String Dinero;
    private String Tipo;
    private String Fecha;
    private String Cuotas;
    private String Comentario;

    public GastosClass(int ID, String titulo, String proveedor, String dinero, String tipo, String fecha, String cuotas, String comentario) {
        this.ID = ID;
        Titulo = titulo;
        Proveedor = proveedor;
        Dinero = dinero;
        Tipo = tipo;
        Fecha = fecha;
        Cuotas = cuotas;
        Comentario = comentario;
    }

    public GastosClass(int ID, int id, String titulo, String proveedor, String dinero, String tipo, String fecha, String cuotas, String comentario) {
        this.ID = ID;
        this.id = id;
        Titulo = titulo;
        Proveedor = proveedor;
        Dinero = dinero;
        Tipo = tipo;
        Fecha = fecha;
        Cuotas = cuotas;
        Comentario = comentario;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getProveedor() {
        return Proveedor;
    }

    public void setProveedor(String proveedor) {
        Proveedor = proveedor;
    }

    public String getDinero() {
        return Dinero;
    }

    public void setDinero(String dinero) {
        Dinero = dinero;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getCuotas() {
        return Cuotas;
    }

    public void setCuotas(String cuotas) {
        Cuotas = cuotas;
    }

    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String comentario) {
        Comentario = comentario;
    }
}
