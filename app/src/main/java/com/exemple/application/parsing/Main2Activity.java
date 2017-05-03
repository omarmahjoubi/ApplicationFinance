package com.exemple.application.parsing;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.exemple.application.parsing.R;
import com.exemple.application.parsing.guielemement.Actu;
import com.exemple.application.parsing.guielemement.ActuAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new URLReader().execute("http://www.ilboursa.com/marches/cotation.aspx?s=PX1");
    }



        private class URLReader extends AsyncTask<String, Integer, String> {

            private String urlImage;
            private String variation = "";
            private String cours = "";
            private String volumeTitre = "" ;
            private String volume = "" ;
            private String ouverture = "" ;
            private String plusBas = "" ;
            private String plusHaut = "" ;
            private String cloture = "" ;
            private String volatilite = "" ;
            private String capitalEchange = "" ;
            private String valorisation = "" ;
            private ArrayList<Actu> actus = new ArrayList<>();


            @Override
            protected String doInBackground(String... params) {
                String title = "";
                if (isNetworkAvailable()) {
                    Document doc = null;
                    try {
                        doc = Jsoup.connect(params[0]).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (doc != null) {

                        Elements donneesResume = doc.select(".alri");
                        ArrayList<String> donneesResumeList = new ArrayList<>() ;
                        for(Element e : donneesResume) {
                            System.out.println("donneesReusme ==> " +e.text()) ;
                            donneesResumeList.add(e.text()) ;
                        }
                        if (donneesResumeList.size() >= 9) {
                            volumeTitre=donneesResumeList.get(0) ;
                            volume=donneesResumeList.get(1) ;
                            ouverture=donneesResumeList.get(2) ;
                            plusHaut=donneesResumeList.get(3) ;
                            plusBas=donneesResumeList.get(4) ;
                            cloture=donneesResumeList.get(5) ;
                            volatilite=donneesResumeList.get(6) ;
                            capitalEchange=donneesResumeList.get(7) ;
                            valorisation=donneesResumeList.get(8) ;
                        }


                        Elements coursVariation = doc.select(".cot1");
                        for(Element e : coursVariation) {
                            String coursVariationText = e.text() ;
                            String[] list = coursVariationText.split(" ") ;
                            if (list.length>= 2) {
                                cours=list[0] ;
                                variation=list[1] ;
                            }
                        }

                        System.out.println("this ===> " + this) ;

                        Elements Actus = doc.select(".lks") ;
                        for (Element e : Actus) {
                            Actu actu = new Actu(e.text(),"http://www.ilboursa.com/marches/"+e.attr("href")) ;
                            this.actus.add(actu) ;
                        }

                    }
                }
                return title;


            }

            private boolean isNetworkAvailable() {
                ConnectivityManager connectivityManager
                        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }

            @Override
            protected void onPostExecute(String result) {
                TextView cours = (TextView) findViewById(R.id.cours) ;
                cours.setText(this.cours);

                TextView variation = (TextView) findViewById(R.id.variation) ;
                ImageView variationImage = (ImageView) findViewById(R.id.variation_image) ;
                variation.setText(this.variation);
                if (this.variation.contains("+")) {
                    variation.setTextColor(Color.rgb(0,153,51));
                    variationImage.setBackgroundResource(R.drawable.up);
                } else if (this.variation.contains("-")) {
                    variation.setTextColor(Color.rgb(255, 0, 0));
                    variationImage.setBackgroundResource(R.drawable.down);
                }

                TextView volumeTitre = (TextView) findViewById(R.id.volumeTitre) ;
                volumeTitre.setText(this.volumeTitre);

                TextView volume = (TextView) findViewById(R.id.volume) ;
                volume.setText(this.volume);

                TextView ouverture = (TextView) findViewById(R.id.ouverture) ;
               ouverture.setText(this.ouverture);

                TextView plus_haut = (TextView) findViewById(R.id.plus_haut) ;
                plus_haut.setText(this.plusHaut);

                TextView plus_bas = (TextView) findViewById(R.id.plus_bas) ;
                plus_bas.setText(this.plusBas);

                TextView cloture = (TextView) findViewById(R.id.cloture) ;
                cloture.setText(this.cloture);

                TextView volatilite = (TextView) findViewById(R.id.volatilite) ;
                volatilite.setText(this.volatilite);

                TextView capital_echange = (TextView) findViewById(R.id.capital_echange) ;
                capital_echange.setText(this.capitalEchange);

                TextView valorisation = (TextView) findViewById(R.id.valorisation) ;
                valorisation.setText(this.valorisation);

                ListView actusList = (ListView) findViewById(R.id.actus) ;
                ActuAdapter actuAdapter = new ActuAdapter(Main2Activity.this,this.actus) ;
                actusList.setAdapter(actuAdapter);

                actusList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        Actu actu = (Actu) parent.getAdapter().getItem(position);
                        String url = actu.getUrl();
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                });










            }

            @Override
            public String toString() {
                return "URLReader{" +
                        "urlImage='" + urlImage + '\'' +
                        ", variation='" + variation + '\'' +
                        ", cours='" + cours + '\'' +
                        ", volumeTitre='" + volumeTitre + '\'' +
                        ", volume='" + volume + '\'' +
                        ", ouverture='" + ouverture + '\'' +
                        ", plusBas='" + plusBas + '\'' +
                        ", plusHaut='" + plusHaut + '\'' +
                        ", cloture='" + cloture + '\'' +
                        ", volatilite='" + volatilite + '\'' +
                        ", capitalEchange='" + capitalEchange + '\'' +
                        ", valorisation='" + valorisation + '\'' +
                        ", actus=" + actus +
                        '}';
            }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.display_palmares:
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
