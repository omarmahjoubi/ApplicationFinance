package com.exemple.application.parsing;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BaissesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baisses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new URLReader().execute("http://www.ilboursa.com/");
    }


    public void displayHausses(View view) {
        Intent intent = new Intent(this,HaussesActivity.class);
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
            String s = "";
            if (doc != null) {
                Elements valeurs = doc.select("td a");
                for (Element valeur : valeurs) {
                    String nameValeur = valeur.text();
                    System.out.println("valeur : " + nameValeur);
                }
                Elements variations = doc.select(".quote_down")  ;
                for (Element variation : variations) {
                    String valeurVariation = variation.text() ;
                    System.out.println("variation : " + valeurVariation) ;
                }


            }

            return s;
        }

        @Override
        protected void onPostExecute(String result) {


        /*    TextView txt = (TextView) findViewById(R.id.html);
            txt.setText(result); */

        }
    }
}
