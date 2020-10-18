package com.example.yap_news;

import java.net.URL;

public class modeloNoticias {
    private URL newsUrl;
    public modeloNoticias(){}
    public modeloNoticias(URL newsUrl){
        this.newsUrl = newsUrl;
    }
    public void setNewsUrl(URL newsUrl){
        this.newsUrl = newsUrl;
    }

    public URL getNewsUrl(){
        return newsUrl;
    }
}
