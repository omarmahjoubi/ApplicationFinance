package com.exemple.application.parsing;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.exemple.application.parsing.guielemement.ElementAdapter;
import com.exemple.application.parsing.guielemement.ElementList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

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






            ListView hausses = (ListView) findViewById(R.id.hausses);


            List<ElementList> elements = new ArrayList<>();
            for (int i=0;i<valeurs.length;i++) {
                elements.add(new ElementList(valeurs[i],variations[i])) ;
            }

            ElementAdapter adapter = new ElementAdapter(HaussesActivity.this, elements);
            hausses.setAdapter(adapter);

        /*    TextView txt = (TextView) findViewById(R.id.html);
            txt.setText(result); */

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hausses, menu);
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
            case R.id.baisses:
                intent = new Intent(this, BaissesActivity.class);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }



}



