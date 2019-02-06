package com.example.android.quakereport;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {
    private static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final int OPERATION_SEARCH_LOADER = 22;

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson"+
                    "&starttime=2019-01-01&endtime=2019-02-01&minmagnitude=6";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        Log.v(LOG_TAG,"Setting up Bundle with url: " + USGS_REQUEST_URL);
        Bundle bundle = new Bundle();
        bundle.putString("url", USGS_REQUEST_URL);
        Log.v(LOG_TAG,"Calling initLoader()");
        getSupportLoaderManager().initLoader(OPERATION_SEARCH_LOADER,bundle,this);
    }

    private void updateUi(List<Earthquake> earthquakes) {
        EarthquakeAdapter earthquakeAdapter = new EarthquakeAdapter(
                EarthquakeActivity.this,
                earthquakes);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        TextView emptyView = findViewById(R.id.empty);
        emptyView.setText(R.string.empty_string);
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(earthquakeAdapter);
        earthquakeListView.setEmptyView(emptyView);
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int i, @Nullable Bundle bundle) {
        Log.v(LOG_TAG, "onCreateLoader()");
        return new EarthquakeLoader(EarthquakeActivity.this, bundle);
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object o) {
        Log.v(LOG_TAG, "onLoadFinished()");
        List<Earthquake> earthquakes = (ArrayList<Earthquake>) o;
        updateUi(earthquakes);
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        Log.v(LOG_TAG, "onLoaderReset()");
    }
}
