package com.example.nnazimudeen.outreach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Item> {
    private final Context context;
    private final ArrayList<Item> itemsArrayList;

    public ListAdapter(Context context, ArrayList<Item> itemsArrayList)
    {

        super(context, R.layout.item_list, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.item_list, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.ActivityName);
        //TextView valueView = (TextView) rowView.findViewById(R.id.ActivityStatus);
        // 4. Set the text for textView
        //labelView.setCompoundDrawables(itemsArrayList.get(position).getImage() ,null, null, null);
        labelView.setCompoundDrawablesRelativeWithIntrinsicBounds(itemsArrayList.get(position).getImage() ,null, null, null);
        labelView.setPadding(20,20,20,20);
        labelView.setText("  " +itemsArrayList.get(position).getName());
       // valueView.setText(itemsArrayList.get(position).getStatus());

        // 5. return rowView
        return rowView;
    }
}