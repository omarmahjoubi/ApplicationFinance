package com.exemple.application.parsing.guielemement;

import android.content.Context;
import android.graphics.Color;
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
public class ElementBaissesAdapter extends ArrayAdapter<ElementList> {

    public ElementBaissesAdapter(Context context, List<ElementList> elementLists) {
        super(context, 0, elementLists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_element,parent, false);
        }

        ElementViewHolder viewHolder = (ElementViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ElementViewHolder();
            viewHolder.valeur = (TextView) convertView.findViewById(R.id.valeur);
            viewHolder.variation = (TextView) convertView.findViewById(R.id.variation);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        ElementList elementList = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.valeur.setText(elementList.getValeur());
        viewHolder.variation.setText(elementList.getVariation());
        viewHolder.variation.setTextColor(Color.rgb(255, 0, 0));


        return convertView;
    }

    private class ElementViewHolder{
        public TextView valeur;
        public TextView variation;

    }

}

