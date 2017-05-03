package com.exemple.application.parsing;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.exemple.application.parsing.guielemement.ElementBaissesAdapter;
import com.exemple.application.parsing.guielemement.ElementBaissesList;
import com.exemple.application.parsing.guielemement.ElementHaussesList;

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


    private class URLReader extends AsyncTask<String, Integer, String> {

        private String[] valeurs = {"", "", ""};


        private String[] variations = {"", "", ""};

        private String[] cours = {"", "", ""};

        public String[] getCours() {
            return cours;
        }

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
               /* Elements valeurs = doc.select("td a");
                for (Element valeur : valeurs) {
                    String nameValeur = valeur.text();
                    System.out.println("valeur : " + nameValeur);
                } */
                Elements baisses = doc.select(".alri");
                int i = 0;
                for (Element baisse : baisses) {
                    String baisseText = baisse.text();

                    String[] list = baisseText.split(" ");
                    if (list.length == 3) {
                        if (list[2].contains("-")) {
                            System.out.println("baisseText ===> " + baisseText);
                            if (i < this.getVariations().length) {
                                valeurs[i] = list[0];
                                cours[i] = list[1] ;
                                variations[i] = list[2] ;
                            }
                            i++;
                        }
                    }
                }


            }

            return s;
        }

        @Override
        protected void onPostExecute(String result) {




            ListView hausses = (ListView) findViewById(R.id.baisses);

            List<ElementBaissesList> elements = new ArrayList<>();
            for (int i = 0; i < valeurs.length; i++) {
                elements.add(new ElementBaissesList( variations[i],valeurs[i],cours[i]));
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
        Intent intent;
        switch (item.getItemId()) {
            case R.id.acceuil:
                intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
                return true;
            case R.id.hausses:
                intent = new Intent(this, HaussesActivity.class);
                startActivity(intent);
                return true;
            case R.id.display_convertisseur:
                intent = new Intent(this, DeviseActivity.class);
                startActivity(intent);
                return true;
            case R.id.display_cours_devise:
                intent = new Intent(this, CoursActivity.class);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


}
