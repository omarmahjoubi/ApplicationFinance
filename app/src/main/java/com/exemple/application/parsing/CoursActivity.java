package com.exemple.application.parsing;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.exemple.application.parsing.devises.Converter;
import com.exemple.application.parsing.guielemement.CoursAdapter;
import com.exemple.application.parsing.guielemement.CoursData;
import com.exemple.application.parsing.guielemement.ElementAdapter;
import com.exemple.application.parsing.guielemement.ElementList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CoursActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cours);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new URLReader().execute("http://www.bna.tn/site/fr/devise.php?id_article=188");
    }

    private class URLReader extends AsyncTask<String, Integer, String> {

        private ArrayList<CoursData> cours = new ArrayList<>() ;

        public ArrayList<CoursData> getCours() {
            return cours;
        }

        protected String doInBackground(String... params) {
            String s = "";
            Document doc = null;
            try {
                doc = Jsoup.connect(params[0]).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (doc != null) {
                Elements devises = doc.select("tr");
                for (Element devise : devises) {
                    String data = devise.text();
                    String[] list = data.split(" ");

                    if (list.length >= 5) {
                        if (!(list[list.length - 2].equals("Achat"))) {
                            if (list[0].equals("Euro")) {
                                this.getCours().add(new CoursData("EURO",list[list.length-3],list[list.length-2],list[list.length-1])) ;
                            }

                            if (list[0].equals("Dollar") && (list[1].equals("des"))) {
                                this.getCours().add(new CoursData("Dollar US", list[list.length - 3], list[list.length - 2], list[list.length - 1])) ;
                            }

                            if (list[0].equals("Livre")) {
                                this.getCours().add(new CoursData("Livre Sterling", list[list.length - 3], list[list.length - 2], list[list.length - 1])) ;
                            }
                            if (list[0].equals("Yen")) {
                                this.getCours().add(new CoursData("Yen Japonais",list[list.length-3],list[list.length-2],list[list.length-1])) ;
                            }
                        }
                    }
                }
            }
            return s ;
        }


        protected void onPostExecute(String result) {

            ListView hausses = (ListView) findViewById(R.id.list_cours);




            CoursAdapter adapter = new CoursAdapter(CoursActivity.this, this.getCours());
            hausses.setAdapter(adapter);
        }

    }


}

