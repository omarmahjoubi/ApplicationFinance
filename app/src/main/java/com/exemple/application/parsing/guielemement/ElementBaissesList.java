package com.exemple.application.parsing.guielemement;

/**
 * Created by omar_ on 30/04/2017.
 */
public class ElementBaissesList {

    private String valeur ;
    private String variation ;
    private String cours ;



    public ElementBaissesList(String variation, String valeur, String cours) {
        this.variation = variation;
        this.valeur = valeur;
        this.cours = cours;

    }

    public void setCours(String cours) {
        this.cours = cours;
    }



    public String getCours() {
        return cours;
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
