package com.example.nnazimudeen.outreach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nnazimudeen on 4/29/15.
 */
public class ScoreAdaptor extends ArrayAdapter<Score> {
    private final Context context;
    private final ArrayList<Score> itemsArrayList;

    public ScoreAdaptor(Context context, ArrayList<Score> itemsArrayList) {

        super(context, R.layout.score_list, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.score_list, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.UserName);
        TextView valueView = (TextView) rowView.findViewById(R.id.Score);
//        // 4. Set the text for textView
//        //labelView.setCompoundDrawables(itemsArrayList.get(position).getImage() ,null, null, null);
//        labelView.setCompoundDrawablesRelativeWithIntrinsicBounds(itemsArrayList.get(position).getImage() ,null, null, null);
//        labelView.setPadding(20,20,20,20);
        labelView.setText("  " + itemsArrayList.get(position).getName());
        valueView.setText(String.valueOf(itemsArrayList.get(position).getScore()));

        // 5. return rowView
        return rowView;
    }
}