package com.exemple.application.parsing.guielemement;

/**
 * Created by omar_ on 19/04/2017.
 */
public class CoursData {
    private String code ;
    private String unite ;
    private String achat ;
    private String vente ;

    public CoursData(String code, String unite, String achat, String vente) {
        this.code = code;
        this.unite = unite;
        this.achat = achat;
        this.vente = vente;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getAchat() {
        return achat;
    }

    public void setAchat(String achat) {
        this.achat = achat;
    }

    public String getVente() {
        return vente;
    }

    public void setVente(String vente) {
        this.vente = vente;
    }



}
