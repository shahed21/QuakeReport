package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EarthquakeAdapter extends ArrayAdapter {
    /**
     * Constructor for creating this Class Object
     * @param context
     * @param objects
     */
    public EarthquakeAdapter(@NonNull Context context, @NonNull ArrayList objects) {
        super(context, 0, objects);
    }

    /**
     * Overriden Method to inflate the view of Earthquakes
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView==null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }
        // Get the {@link Earthquake} at this position in the list
        Earthquake currentEarthquake = (Earthquake) getItem(position);

        TextView magTextView = (TextView) listItemView.findViewById(R.id.mag_text_view);
        TextView placeTextView = (TextView) listItemView.findViewById(R.id.place_text_view);
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);

        LinearLayout earthquakeContainerLayout = (LinearLayout) listItemView.findViewById(R.id.earthquake_container_layout);

        magTextView.setText(currentEarthquake.getMag()+"");
        placeTextView.setText(currentEarthquake.getPlace());
        dateTextView.setText(
                currentEarthquake.getFormattedDate(
                        new SimpleDateFormat("MMM dd, yyyy")));

        return listItemView;
    }
}