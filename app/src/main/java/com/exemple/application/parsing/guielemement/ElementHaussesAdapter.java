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
 * Created by omar_ on 18/04/2017.
 */
public class ElementHaussesAdapter extends ArrayAdapter<ElementHaussesList> {

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public ElementHaussesAdapter(Context context, List<ElementHaussesList> elementHaussesLists) {
        super(context, 0, elementHaussesLists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hausse_element,parent, false);
        }

        ElementViewHolder viewHolder = (ElementViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ElementViewHolder();
            viewHolder.valeur = (TextView) convertView.findViewById(R.id.valeur);
            viewHolder.volume = (TextView) convertView.findViewById(R.id.volume);
            viewHolder.cours = (TextView) convertView.findViewById(R.id.cours);
            viewHolder.variation = (TextView) convertView.findViewById(R.id.variation);


            viewHolder.volume_label = (TextView) convertView.findViewById(R.id.label_volume);
            viewHolder.cours_label = (TextView) convertView.findViewById(R.id.label_cours);
            viewHolder.variation_label = (TextView) convertView.findViewById(R.id.label_variation);


            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        ElementHaussesList elementHaussesList = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.valeur.setText(elementHaussesList.getValeur());
        viewHolder.volume.setText(elementHaussesList.getVolume());
        viewHolder.cours.setText(elementHaussesList.getCours());
        viewHolder.variation.setText(elementHaussesList.getVariation());
        viewHolder.variation.setTextColor(Color.rgb(0,153,51));

        if (isNetworkAvailable()) {
            viewHolder.volume_label.setText("volume :");
            viewHolder.cours_label.setText("cours :");
            viewHolder.variation_label.setText("variation :");
        }


        return convertView;
    }

    private class ElementViewHolder{
        public TextView valeur;
        public TextView variation;
        public TextView cours ;
        public TextView volume ;
        public TextView volume_label ;
        public TextView cours_label ;
        public TextView variation_label ;

    }

}
