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
 * Created by omar_ on 21/04/2017.
 */
public class ActuAdapter extends ArrayAdapter<Actu> {

public ActuAdapter(Context context, List<Actu> elementLists) {
        super(context, 0, elementLists);
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.actu_element,parent, false);
        }

        ElementViewHolder viewHolder = (ElementViewHolder) convertView.getTag();
        if(viewHolder == null){
        viewHolder = new ElementViewHolder();
        viewHolder.title = (TextView) convertView.findViewById(R.id.titre);

        convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Actu elementList = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.title.setText(elementList.getTitle());
        viewHolder.title.setTextColor(Color.BLACK);



        return convertView;
        }

private class ElementViewHolder{
    public TextView title;


}

}