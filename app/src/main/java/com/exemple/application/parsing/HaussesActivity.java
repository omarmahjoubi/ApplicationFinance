package com.exemple.application.parsing;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class HaussesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palmares);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        new URLReader().execute("http://www.ilboursa.com/marches/palmares.aspx");
    }

    public void displayBaisses(View view) {
        Intent intent = new Intent(this, BaissesActivity.class);
        startActivity(intent);
    }

    private class URLReader extends AsyncTask<String, Integer, String> {

        private String[] valeurs = {"", "", "", "", ""};


        private String[] variations = {"", "", "", "", ""};

        public String[] getValeurs() {
            return valeurs;
        }

        public String[] getVariations() {
            return variations;
        }

        @Override
        protected String doInBackground(String... params) {
            Document doc = null;
            try {
                doc = Jsoup.connect(params[0]).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String title = "";
            if (doc != null) {
                Elements valeurs = doc.select("td a");
                ArrayList<Element> valeurElements = new ArrayList<>();
                for (Element valeur : valeurs) {
                    valeurElements.add(valeur);
                }
                for (int i = 0; i < 5; i++) {
                    if (i < valeurElements.size()) {
                        this.valeurs[i] = valeurElements.get(i).text();
                    }
                }

                Elements variations = doc.select(".quote_up2");
                ArrayList<Element> variationElements = new ArrayList<>();
                for (Element variation : variations) {
                    variationElements.add(variation);
                }
                int j = 0;
                int k = 0;
                while (j < 5) {
                    if (k < variationElements.size()) {
                        this.variations[j] = variationElements.get(k).text();
                    }
                        j++;
                        k += 2;
                    
                }
            }
            return title;

        }

        @Override
        protected void onPostExecute(String result) {

            String[] valeurs = this.getValeurs();
            String[] variations = this.getVariations();

            TextView tv11 = (TextView) findViewById(R.id.valeur1);
            tv11.setText(valeurs[0]);

            TextView tv12 = (TextView) findViewById(R.id.variation1);
            tv12.setText(variations[0]);
            tv12.setTextColor(Color.rgb(0, 153, 51));

            TextView tv21 = (TextView) findViewById(R.id.valeur2);
            tv21.setText(valeurs[1]);

            TextView tv22 = (TextView) findViewById(R.id.variation2);
            tv22.setText(variations[1]);
            tv22.setTextColor(Color.rgb(0, 153, 51));

            TextView tv31 = (TextView) findViewById(R.id.valeur3);
            tv31.setText(valeurs[2]);

            TextView tv32 = (TextView) findViewById(R.id.variation3);
            tv32.setText(variations[2]);
            tv32.setTextColor(Color.rgb(0, 153, 51));

            TextView tv41 = (TextView) findViewById(R.id.valeur4);
            tv41.setText(valeurs[3]);

            TextView tv42 = (TextView) findViewById(R.id.variation4);
            tv42.setText(variations[3]);
            tv42.setTextColor(Color.rgb(0, 153, 51));

            TextView tv51 = (TextView) findViewById(R.id.valeur5);
            tv51.setText(valeurs[4]);

            TextView tv52 = (TextView) findViewById(R.id.variation5);
            tv52.setText(variations[4]);
            tv52.setTextColor(Color.rgb(0, 153, 51));

        /*    TextView txt = (TextView) findViewById(R.id.html);
            txt.setText(result); */

        }

    }


}



