package com.exemple.application.parsing;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.exemple.application.parsing.guielemement.ElementHaussesAdapter;
import com.exemple.application.parsing.guielemement.ElementHaussesList;

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




    private class URLReader extends AsyncTask<String, Integer, String> {

        private String[] valeurs = {"", "", "", "", ""};


        private String[] variations = {"", "", "", "", ""};

        private String[] cours = {"", "", "", "", ""};

        private String[] volumes = {"", "", "", "", ""};

        public String[] getVolumes() {
            return volumes;
        }

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
                String title = "";
                if (doc != null) {
                    Elements datas = doc.select(".alri") ;
                    ArrayList<String> donnees = new ArrayList<>() ;
                for (Element data : datas) {
                        donnees.add(data.text()) ;
                        }
                    int j = 0 ;
                    for(int i =0 ; i < donnees.size() ; i++) {
                    if ( i < 5) {
                        String[] list = donnees.get(i).split(" ") ;
                        this.variations[j] = list[list.length-2] ;
                        this.volumes[j] = list[list.length-3] ;
                        this.cours[j] = list[list.length-4] ;
                        String valeur = list[0] ;
                        for (int k=1;k<list.length-6;k++) {
                            valeur = valeur + " " + list[k] ;
                        }
                        this.valeurs[j]=valeur ;
                        j++ ;
                    }
                }

            }
            return title;

        }

        @Override
        protected void onPostExecute(String result) {








            ListView hausses = (ListView) findViewById(R.id.hausses);


            List<ElementHaussesList> elements = new ArrayList<>();
            for (int i=0;i<valeurs.length;i++) {
                elements.add(new ElementHaussesList(this.variations[i],this.valeurs[i],this.cours[i],this.volumes[i])) ;
            }

            ElementHaussesAdapter adapter = new ElementHaussesAdapter(HaussesActivity.this, elements);
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
                intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
                return true;
            case R.id.baisses:
                intent = new Intent(this, BaissesActivity.class);
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



