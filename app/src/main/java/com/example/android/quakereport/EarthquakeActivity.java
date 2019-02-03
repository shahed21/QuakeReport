package com.example.android.quakereport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import java.lang.Long;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.*;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {
    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();

        EarthquakeAdapter earthquakeAdapter = new EarthquakeAdapter(
                EarthquakeActivity.this,
                earthquakes);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(earthquakeAdapter);
    }
}
