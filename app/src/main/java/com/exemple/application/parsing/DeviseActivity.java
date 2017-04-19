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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.exemple.application.parsing.devises.Converter;
import com.exemple.application.parsing.guielemement.ElementAdapter;
import com.exemple.application.parsing.guielemement.ElementList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeviseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String from;
    private String to;
    private URLReader urlreader;

    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.devises, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new Spinner2Listener());

        urlreader = new URLReader();
        urlreader.execute("http://www.bna.tn/site/fr/devise.php?id_article=188");
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        String from = (String) parent.getItemAtPosition(pos);
        this.setFrom(from);

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    private class Spinner2Listener implements AdapterView.OnItemSelectedListener {


        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            String to = (String) parent.getItemAtPosition(pos);
            DeviseActivity.this.setTo(to);

        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }

    public void callToConvert(View view) {
        Converter converter = new Converter(urlreader.getDev());
        EditText et = (EditText) findViewById(R.id.from);
        String montant = et.getText().toString();
        String res = converter.convert(from, to, montant);
        if (res.contains(".")) {
            res = res.substring(0, res.indexOf('.') + 3);
        }
        TextView tv = (TextView) findViewById(R.id.to);
        tv.setText(res);

    }


    private class URLReader extends AsyncTask<String, Integer, String> {

        HashMap<String, String> dev = new HashMap<>();

        public HashMap<String, String> getDev() {
            return dev;
        }

        @Override
        public String toString() {
            return "URLReader{" +
                    "dev=" + dev +
                    '}';
        }

        @Override
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
                                this.getDev().put(list[0], list[list.length - 2]);
                            }

                            if (list[0].equals("Dollar") && (list[1].equals("des"))) {
                                this.getDev().put("Dollar US", list[list.length - 2]);
                            }

                            if (list[0].equals("Livre")) {
                                this.getDev().put("Livre Sterling", list[list.length - 2]);
                            }
                        }
                    }
                }
                Converter converter = new Converter(this.getDev());
                String res = converter.convert("Euro", "Livre Sterling", "5.4");
                System.out.println(res);
            }


            return s;
        }


        @Override
        protected void onPostExecute(String result) {

            Converter converter = new Converter(this.getDev());


            String res = converter.convert("Euro", "Livre Sterling", "5.4");
            System.out.println(res);

        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_devise, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.acceuil:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.display_cours:
                intent = new Intent(this, CoursActivity.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
