package com.exemple.application.parsing.guielemement;

/**
 * Created by omar_ on 21/04/2017.
 */
public class Actu {
    private String title ;
    private String url ;

    public Actu(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
