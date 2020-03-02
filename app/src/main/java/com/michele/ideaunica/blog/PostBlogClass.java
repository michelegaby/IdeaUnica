package com.michele.ideaunica.blog;

public class PostBlogClass {

    private String id;
    private String time;
    private String empresa;
    private String contenido;
    private String urlphoto;
    private String urlempresa;
    private String url;

    public PostBlogClass(String id, String time, String empresa, String contenido, String urlphoto, String urlempresa, String url) {
        this.id = id;
        this.time = time;
        this.empresa = empresa;
        this.contenido = contenido;
        this.urlphoto = urlphoto;
        this.urlempresa = urlempresa;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getUrlphoto() {
        return urlphoto;
    }

    public void setUrlphoto(String urlphoto) {
        this.urlphoto = urlphoto;
    }

    public String getUrlempresa() {
        return urlempresa;
    }

    public void setUrlempresa(String urlempresa) {
        this.urlempresa = urlempresa;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
