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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.exemple.application.parsing.guielemement.Actu;
import com.exemple.application.parsing.guielemement.ActuAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private URLReader reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        new URLReader().execute("http://www.ilboursa.com/");


    }








        private class URLReader extends AsyncTask<String, Integer, String> {

        private String urlImage;
        private String variation = null;
        private String cours = null;
        private Bitmap bmpGraphe = null;
        private Bitmap bmpFlag = null;
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
                    Elements variation = doc.select("div + span");
                    for (Element el : variation) {
                        this.variation = el.text();
                    }
                    Elements data = doc.select(".f14");
                    for (Element el : data) {
                        System.out.println(el.text()) ;
                        if ((!el.text().contains("%")) && (el.text().length()<15)) {
                            this.cours = el.text();
                        }

                    }

                    Element img = doc.getElementById("ctl00_BodyABC_chartTN");
                    urlImage = "http://www.ilboursa.com/" + img.attr("src");
                    try {
                        URL url = new URL(this.urlImage);
                        bmpGraphe = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        URL url1 = new URL("http://www.ilboursa.com/i/tn.png");
                        bmpFlag = BitmapFactory.decodeStream(url1.openConnection().getInputStream());


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Elements infos = doc.select(".f14");
                    for (Element el : infos) {
                        if (el.text().length() >= 15) {
                            Actu actu = new Actu(el.text(), "http://www.ilboursa.com/" + el.attr("href"));
                            this.actus.add(actu);
                        }

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

            if (isNetworkAvailable()) {
                TextView tunindex = (TextView) findViewById(R.id.tunindex);
                tunindex.setText("TUNINDEX");
                TextView titleActu = (TextView) findViewById(R.id.titre_actu);
                titleActu.setText("Actualités");
                titleActu.setTextColor(Color.BLUE);
            }


            ImageView graphe = (ImageView) findViewById(R.id.graphe);
            //graphe.setImageBitmap(bmp);
            if (bmpGraphe != null) {
                graphe.setImageBitmap(Bitmap.createScaledBitmap(bmpGraphe, 400, 250, false));
            }
            ImageView flag = (ImageView) findViewById(R.id.tn_flag);
            //graphe.setImageBitmap(bmp);
            if (bmpFlag != null) {
                flag.setImageBitmap(bmpFlag);
            }
            TextView variation = (TextView) findViewById(R.id.variation);
            variation.setText(this.variation);

            if (this.variation != null) {
                if (this.variation.contains("-")) {
                    variation.setTextColor(Color.rgb(255, 0, 0));
                } else {
                    variation.setTextColor(Color.rgb(0, 153, 51));
                }
            }


            TextView cours = (TextView) findViewById(R.id.cours);
            cours.setText(this.cours);


            ListView actu = (ListView) findViewById(R.id.actu);
            ActuAdapter adapter = new ActuAdapter(MainActivity.this, this.actus);
            actu.setAdapter(adapter);

            actu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
