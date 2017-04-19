package com.exemple.application.parsing.devises;

import java.util.HashMap;

/**
 * Created by omar_ on 19/04/2017.
 */
public class Converter {

    private HashMap<String, String> devises;

    public Converter(HashMap<String, String> devises) {
        this.devises = devises;
    }

    public String convert(String from, String to, String value) {

        double montantConverti;

        try {


            double montant = Double.parseDouble(value);

            if ((from.equals(to))) {
                return value;
            } else if (from.equals("Dinar Tunisien")) {
                if (this.devises.containsKey(to)) {
                    Double cours = Double.parseDouble(this.devises.get(to));
                    montantConverti = montant / cours;
                    return Double.toString(montantConverti);
                }

            } else if (to.equals("Dinar Tunisien")) {
                if (this.devises.containsKey(from)) {
                    Double cours = Double.parseDouble(this.devises.get(from));
                    montantConverti = montant * cours;
                    return Double.toString(montantConverti);
                }
            } else if ((this.devises.containsKey(to)) && (this.devises.containsKey(from))) {
                Double cours1 = Double.parseDouble(this.devises.get(from));
                double montantInter = montant * cours1;
                Double cours2 = Double.parseDouble(this.devises.get(to));
                montantConverti = montantInter / cours2;
                return Double.toString(montantConverti);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";

    }
}

