package com.example.android.quakereport;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    private static final String LOG_TAG = EarthquakeLoader.class.getName();
    private String mUrl;
    public EarthquakeLoader(@NonNull Context context, Bundle bundle) {
        super(context);
        mUrl = bundle.getString("url");
        Log.v(LOG_TAG,"url received in Constructor:" + mUrl);
    }

    @Nullable
    @Override
    public List<Earthquake> loadInBackground() {
        if (TextUtils.isEmpty(mUrl)) {
            Log.e(LOG_TAG,"Empty URL Passed");
            return null;
        }
        URL url = createUrl(mUrl);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG,"Error Closing the InputStream", e);
        }
        List<Earthquake> earthquakes = extractEarthquakesFromJson(jsonResponse);
        return earthquakes;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            if (urlConnection.getResponseCode()==200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else {
                Log.e(LOG_TAG, "Error Response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a List of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    private List<Earthquake> extractEarthquakesFromJson(String jsonResponse) {

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // build up a list of Earthquake objects with the corresponding data.
            JSONObject earthquakeGeoJsonRootObject = new JSONObject(jsonResponse);

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray featuresArray = earthquakeGeoJsonRootObject.optJSONArray("features");

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < featuresArray.length(); i++){
                JSONObject featurePropertiesObject =
                        featuresArray.getJSONObject(i).getJSONObject("properties");

                long time =
                        java.lang.Long.parseLong(featurePropertiesObject.optString("time"));
                String place = featurePropertiesObject.optString("place");
                String url = featurePropertiesObject.optString("url");
                double mag =
                        Double.parseDouble(
                                featurePropertiesObject.optString("mag").toString());

                earthquakes.add(new Earthquake(
                        mag,
                        place,
                        time,
                        url));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        catch (NumberFormatException e) {
            Log.e(LOG_TAG, "NumberFormatException", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }
}
