package com.example.yap_news;

public class Noticias {
    private String nombre;
    private String autor;
    private String fecha;
    private String hora;
    private String newsUrl;

    public Noticias() {}

    public Noticias(String nombre, String autor, String fecha, String hora, String newsUrl) {
        this.nombre = nombre;
        this.autor = autor;
        this.fecha = fecha;
        this.hora = hora;
        this.newsUrl = newsUrl;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getHora(){
        return hora;
    }

    public void setHora(String hora){
        this.hora = hora;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}
