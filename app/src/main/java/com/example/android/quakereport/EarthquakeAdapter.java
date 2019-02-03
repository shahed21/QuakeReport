package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class EarthquakeAdapter extends ArrayAdapter {
    public static final String LOG_TAG = EarthquakeAdapter.class.getName();
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
            listItemView = LayoutInflater.from(
                    getContext()).inflate(
                            R.layout.earthquake_list_item,
                            parent,
                            false);
        }
        // Get the {@link Earthquake} at this position in the list
        final Earthquake currentEarthquake = (Earthquake) getItem(position);

        TextView magTextView = (TextView) listItemView.findViewById(R.id.mag_text_view);
        TextView nearStringTextView = (TextView) listItemView.findViewById(R.id.near_string_text_view);
        TextView placeTextView = (TextView) listItemView.findViewById(R.id.place_text_view);
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time_text_view);

        LinearLayout earthquakeContainerLayout = (LinearLayout) listItemView.findViewById(R.id.earthquake_container_layout);
        earthquakeContainerLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                browserIntent(currentEarthquake.getUrl());
            }
        });


        magTextView.setText(formatMagnitude(currentEarthquake.getMag()));
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMag());

        Log.v(LOG_TAG, "magColorResId :" + magnitudeColor);

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);
        nearStringTextView.setText(currentEarthquake.getNearString(getContext()));
        placeTextView.setText(currentEarthquake.getLandmark());
        dateTextView.setText(
                currentEarthquake.getFormattedDate(
                        new SimpleDateFormat("MMM DD, yyyy")));
        timeTextView.setText(
                currentEarthquake.getFormattedDate(
                        new SimpleDateFormat("h:mm a")));

        return listItemView;
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double mag) {
        switch ((int)mag) {
            case 1:
                return ContextCompat.getColor(getContext(), R.color.magnitude1);
            case 2:
                return ContextCompat.getColor(getContext(), R.color.magnitude2);
            case 3:
                return ContextCompat.getColor(getContext(), R.color.magnitude3);
            case 4:
                return ContextCompat.getColor(getContext(), R.color.magnitude4);
            case 5:
                return ContextCompat.getColor(getContext(), R.color.magnitude5);
            case 6:
                return ContextCompat.getColor(getContext(), R.color.magnitude6);
            case 7:
                return ContextCompat.getColor(getContext(), R.color.magnitude7);
            case 8:
                return ContextCompat.getColor(getContext(), R.color.magnitude8);
            case 9:
                return ContextCompat.getColor(getContext(), R.color.magnitude9);
            default:
                return ContextCompat.getColor(getContext(), R.color.magnitude10plus);
        }
    }

    private void browserIntent(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        if (i!=null) {
            i.setData(Uri.parse(url));
            this.getContext().startActivity(i);
        }
    }
}