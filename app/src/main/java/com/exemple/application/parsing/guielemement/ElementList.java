package com.exemple.application.parsing.guielemement;

/**
 * Created by omar_ on 18/04/2017.
 */
public class ElementList {
    private String valeur ;
    private String variation ;


    public ElementList(String valeur, String variation) {
        this.valeur = valeur;

        this.variation = variation;
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
