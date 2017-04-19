package com.exemple.application.parsing;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.exemple.application.parsing.guielemement.ElementBaissesAdapter;
import com.exemple.application.parsing.guielemement.ElementList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

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

        private String[] valeurs = {"", "", ""};


        private String[] variations = {"", "", ""};

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
                Elements variations = doc.select(".alri")  ;
                int i = 0 ;
                for (Element variation : variations) {
                    String valeurVariation = variation.text() ;
                    String[] list = valeurVariation.split(" ");
                    if (list.length == 3) {
                        if (list[2].contains("-")) {
                            if (i < this.getVariations().length) {
                                this.getValeurs()[i] = list[0] ;
                                this.getVariations()[i] = list[2] ;
                            }
                            i++ ;
                        }
                    }
                }


            }

            return s;
        }

        @Override
        protected void onPostExecute(String result) {

            String[] valeurs = this.getValeurs();
            String[] variations = this.getVariations();


            ListView hausses = (ListView) findViewById(R.id.baisses);

            List<ElementList> elements = new ArrayList<>();
            for (int i=0;i<valeurs.length;i++) {
                elements.add(new ElementList(valeurs[i],variations[i])) ;
            }

            ElementBaissesAdapter adapter = new ElementBaissesAdapter(BaissesActivity.this, elements);
            hausses.setAdapter(adapter);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_baisses, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent ;
        switch (item.getItemId()){
            case R.id.acceuil:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.hausses:
                intent = new Intent(this,HaussesActivity.class);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


}
