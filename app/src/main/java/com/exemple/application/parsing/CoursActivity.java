package com.exemple.application.parsing;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.exemple.application.parsing.guielemement.CoursAdapter;
import com.exemple.application.parsing.guielemement.CoursData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

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
                    System.out.println("devise ====> "+data) ;
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
                            if ((list[0].equals("Ryal"))&&(list[1].equals("qatari"))) {
                                this.getCours().add(new CoursData("Ryal qatari",list[list.length-3],list[list.length-2],list[list.length-1])) ;
                            }
                            if ((list[0].equals("Ryal"))&&(list[1].equals("saoudien"))) {
                                this.getCours().add(new CoursData("Ryal Saoudien",list[list.length-3],list[list.length-2],list[list.length-1])) ;
                            }
                            if (list[0].equals("Franc")) {
                                this.getCours().add(new CoursData("Franc suisse",list[list.length-3],list[list.length-2],list[list.length-1])) ;
                            }
                            if (list[0].equals("Dollar") && (list[1].equals("canadien"))) {
                                this.getCours().add(new CoursData("Dollar canada",list[list.length-3],list[list.length-2],list[list.length-1])) ;
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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cours, menu);
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
            case R.id.display_palmares:
                intent = new Intent(this, HaussesActivity.class);
                startActivity(intent);
                return true;
            case R.id.display_convertisseur:
                intent = new Intent(this, DeviseActivity.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


}

