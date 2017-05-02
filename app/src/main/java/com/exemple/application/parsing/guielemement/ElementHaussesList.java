package com.exemple.application.parsing.guielemement;

/**
 * Created by omar_ on 18/04/2017.
 */
public class ElementHaussesList {
    private String valeur ;
    private String variation ;
    private String cours ;
    private String volume ;


    public ElementHaussesList(String variation, String valeur, String cours, String volume) {
        this.variation = variation;
        this.valeur = valeur;
        this.cours = cours;
        this.volume = volume;
    }

    public void setCours(String cours) {
        this.cours = cours;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getCours() {
        return cours;
    }

    public String getVolume() {
        return volume;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }


}
