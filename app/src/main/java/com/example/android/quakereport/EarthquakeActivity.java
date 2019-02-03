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

//        // Dateformat for date input
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // Create a fake list of earthquake locations.
        String earthquakeGeoJson=
        "{"+
			"\"features\" :["+
                "{"+
                    "\"properties\":{"+
                        "\"mag\":\"1.29\","+
                        "\"place\":\"10km SSW of Idyllwild, CA\","+
                        "\"time\":\"1388620296020\""+
                    "}"+
                "},"+
                "{"+
                    "\"properties\":{"+
                        "\"mag\":\"1.29\","+
                        "\"place\":\"10km SSW of Idyllwild, CA\","+
                        "\"time\":\"1388620296020\""+
                    "}"+
                "},"+
                "{"+
                    "\"properties\":{"+
                        "\"mag\":\"1.29\","+
                        "\"place\":\"10km SSW of Idyllwild, CA\","+
                        "\"time\":\"1388620296020\""+
                    "}"+
                "}"+
			"]"+
        "}";

        try {
            JSONObject earthquakeGeoJsonRootObject = new JSONObject(earthquakeGeoJson);

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray featuresArray = earthquakeGeoJsonRootObject.optJSONArray("features");

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < featuresArray.length(); i++){
                JSONObject featurePropertiesObject =
                        featuresArray.getJSONObject(i).getJSONObject("properties");

                long time = java.lang.Long.parseLong(featurePropertiesObject.optString("time"));
                String place = featurePropertiesObject.optString("place");
                double mag = Double.parseDouble(featurePropertiesObject.optString("mag").toString());

                earthquakes.add(new Earthquake(
                        mag,
                        place,
                        time));
            }
        }
        catch (JSONException e ) {
            Log.e(LOG_TAG, "JSONException", e);
        }
        catch (NumberFormatException e) {
            Log.e(LOG_TAG, "NumberFormatException", e);
        }



//        try {
//            earthquakes.add(new Earthquake(
//                    7.2,
//                    "San Francisco",
//                    (long) dateFormat.parse("2016/02/02").getTime()/1000));
//            earthquakes.add(new Earthquake(
//                    6.1,
//                    "London",
//                    (long) dateFormat.parse("2015/07/20").getTime()/1000));
//            earthquakes.add(new Earthquake(
//                    3.9,
//                    "Tokyo",
//                    (long) dateFormat.parse("2014/11/10").getTime()/1000));
//            earthquakes.add(new Earthquake(
//                    5.4,
//                    "Mexico City",
//                    (long) dateFormat.parse("2014/05/03").getTime()/1000));
//            earthquakes.add(new Earthquake(
//                    2.8,
//                    "Moscow",
//                    (long) dateFormat.parse("2013/01/31").getTime()/1000));
//            earthquakes.add(new Earthquake(
//                    4.9,
//                    "Rio de Janeiro",
//                    (long) dateFormat.parse("2012/08/19").getTime()/1000));
//            earthquakes.add(new Earthquake(
//                    1.6,
//                    "Paris",
//                    (long) dateFormat.parse("2011/10/30").getTime()/1000));
//        }
//        catch (ParseException e) {
//            Log.e(LOG_TAG,"Date Parse Error", e);
//        }

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
