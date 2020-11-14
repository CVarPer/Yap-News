package com.example.yap_news;

import android.graphics.Bitmap;

public class publicacionComunidad {

    private String id;
    private String titulo;
    private String contenido;
    private String usuario;
    private Bitmap img;
    private String imgId;

    public String getImgId(){
        return this.imgId;
    }

    public String getId(){
        return this.id;
    }

    public String getTitulo(){
        return this.titulo;
    }

    public String getContenido(){
        return this.contenido;
    }

    public String getUsuario(){
        return this.usuario;
    }

    public Bitmap getImg(){
        return this.img;
    }

    public void setImgId(String imgId){
        this.imgId = imgId;
    }

    public  void setId(String id){
        this.id = id;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public void setContenido(String contenido){
        this.contenido = contenido;
    }

    public void setUsuario(String usuario){
        this.usuario = usuario;
    }

    public void setImg(Bitmap img){
        this.img = img;
    }

}
