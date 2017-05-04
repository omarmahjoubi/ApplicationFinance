package com.exemple.application.parsing.guielemement;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.exemple.application.parsing.R;

import java.util.List;

/**
 * Created by omar_ on 19/04/2017.
 */
public class ElementBaissesAdapter extends ArrayAdapter<ElementBaissesList> {

    public ElementBaissesAdapter(Context context, List<ElementBaissesList> elementBaissesLists) {
        super(context, 0, elementBaissesLists);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.baisse_element,parent, false);
        }

        ElementViewHolder viewHolder = (ElementViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ElementViewHolder();
            viewHolder.valeur = (TextView) convertView.findViewById(R.id.valeur);
            viewHolder.variation = (TextView) convertView.findViewById(R.id.variation);
            viewHolder.cours = (TextView) convertView.findViewById(R.id.cours);
            viewHolder.variation_label = (TextView) convertView.findViewById(R.id.label_variation);
            viewHolder.cours_label = (TextView) convertView.findViewById(R.id.label_cours);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        ElementBaissesList elementBaissesList = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.valeur.setText(elementBaissesList.getValeur());
        viewHolder.variation.setText(elementBaissesList.getVariation());
        viewHolder.variation.setTextColor(Color.rgb(255, 0, 0));
        viewHolder.cours.setText(elementBaissesList.getCours());

        if (isNetworkAvailable()) {
            viewHolder.cours_label.setText("dernier cours :");
            viewHolder.variation_label.setText("variation :");
        }


        return convertView;
    }

    private class ElementViewHolder{
        public TextView valeur;
        public TextView variation;
        public TextView cours;
        public TextView variation_label;
        public TextView cours_label;
    }

}

