package com.michele.ideaunica.ui.gastos;

public class GastosTotalClass {

    private int ID;
    private String idgasto;
    private String Titulo;
    private String Total;
    private String Apagar;
    private String Pago;
    private String Vencido;

    public GastosTotalClass(int ID, String titulo, String total, String apagar, String pago, String vencido) {
        this.ID = ID;
        Titulo = titulo;
        Total = total;
        Apagar = apagar;
        Pago = pago;
        Vencido = vencido;
    }

    public GastosTotalClass(int ID, String idgasto, String titulo, String total, String apagar, String pago, String vencido) {
        this.ID = ID;
        this.idgasto = idgasto;
        Titulo = titulo;
        Total = total;
        Apagar = apagar;
        Pago = pago;
        Vencido = vencido;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getIdgasto() {
        return idgasto;
    }

    public void setIdgasto(String idgasto) {
        this.idgasto = idgasto;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getApagar() {
        return Apagar;
    }

    public void setApagar(String apagar) {
        Apagar = apagar;
    }

    public String getPago() {
        return Pago;
    }

    public void setPago(String pago) {
        Pago = pago;
    }

    public String getVencido() {
        return Vencido;
    }

    public void setVencido(String vencido) {
        Vencido = vencido;
    }
}
