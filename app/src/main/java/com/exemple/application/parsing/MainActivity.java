package com.exemple.application.parsing;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {

    private URLReader reader ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        new URLReader().execute("http://www.bvmt.com.tn/");


    }

    public void displayPalmares(View view) {
        Intent intent = new Intent(this,HaussesActivity.class) ;
        startActivity(intent);

    }

    private class URLReader extends AsyncTask<String,Integer,String> {
        @Override
        protected String doInBackground(String... params) {
            Document doc = null ;
            try {
                doc = Jsoup.connect(params[0]).get() ;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            String title = "" ;
            if (doc != null) {
                Elements links = doc.getElementsByTag("a") ;
                for (Element link : links) {
                    String href = " href : " + link.attr("href") +";" ;
                    String linkText = " link : " +link.text()  ;
                    title = title + href + linkText + "\n" ;
                }
            }
            return title ;






        }

        @Override
        protected void onPostExecute(String result) {


        /*    TextView txt = (TextView) findViewById(R.id.html);
            txt.setText(result); */

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
