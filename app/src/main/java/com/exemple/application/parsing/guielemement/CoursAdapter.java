package com.exemple.application.parsing.guielemement;

import android.content.Context;
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
public class CoursAdapter extends ArrayAdapter<CoursData> {

    public CoursAdapter(Context context, List<CoursData> elementLists) {
        super(context, 0, elementLists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cours_element, parent, false);
        }

        ElementViewHolder viewHolder = (ElementViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ElementViewHolder();
            viewHolder.code = (TextView) convertView.findViewById(R.id.code1);
            viewHolder.unite = (TextView) convertView.findViewById(R.id.unite1);
            viewHolder.achat = (TextView) convertView.findViewById(R.id.achat1);
            viewHolder.vente = (TextView) convertView.findViewById(R.id.vente1);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        CoursData elementList = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.code.setText(elementList.getCode());
        viewHolder.unite.setText(elementList.getUnite());
        viewHolder.achat.setText(elementList.getAchat());
        viewHolder.vente.setText(elementList.getVente());



        return convertView;
    }

    private class ElementViewHolder {
        public TextView code;
        public TextView unite;
        public TextView achat;
        public TextView vente;

    }

}
