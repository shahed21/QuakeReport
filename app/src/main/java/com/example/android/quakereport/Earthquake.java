package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class holds the information for one instance of an earthquake
 */
public class Earthquake {
    // the magnitude
    private double mMag;

    // the place
    private String mPlace;

    // time
    private long mTime;

    // URL
    private String mUrl;

    // Was there a tsunami warning
    private boolean mTsunami;

    // What type of occurance was it?
    private String mType;

    // Longitude
    private double mLongitude;

    //Latitude
    private double mLatitude;

    //depth
    private double mDepth;

    // magnitude type
    private String mMagType;

    //date
    private Date mDate;

    //Title
    private String mTitle;

    // the following is used to separate place string
    private static final String LOCATION_SEPARATOR = " of ";

    /**
     * Constructor with all the fields
     * @param mag
     * @param place
     * @param time
     * @param url
     */
    public Earthquake(
            @NonNull double mag,
            @NonNull String place,
            @NonNull long time,
            String url
    ) {
        this(
                mag,
                place,
                time,
                url,
                false,
                null,
                0,
                0,
                0,
                null,
                null);
    }

    /**
     * Constructor with all the fields
     * @param mag
     * @param place
     * @param time
     * @param url
     * @param tsunami
     * @param type
     * @param longitude
     * @param latitude
     * @param depth
     * @param magType
     * @param title
     */
    public Earthquake(
            @NonNull double mag,
            @NonNull String place,
            @NonNull long time,
            String url,
            boolean tsunami,
            String type,
            double longitude,
            double latitude,
            double depth,
            String magType,
            String title
    ) {
        mMag=mag;
        mPlace=place;
        mTime=time;
        mUrl = url;
        mTsunami=tsunami;
        mType=type;
        mLongitude = longitude;
        mLatitude = latitude;
        mDepth = depth;
        mMagType = magType;
        mTitle = title;
        mDate = new Date((long)time);
    }

    /**
     * This method sets the title for the Earthquake
     * @param title
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * This method gets the title for the Earthquake
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * This method sets Magnitude for the Earthquake
     * @param mag magnitude
     */
    public void setMag(double mag) {
        mMag = mag;
    }

    /**
     * This method gets Magnitude for the Earthquake
     */
    public double getMag() {
        return mMag;
    }

    /**
     * This method sets place for the Earthquake
     * @param place magnitude
     */
    public void setPlace(String place) {
        mPlace = place;
    }

    /**
     * This method gets place for the Earthquake
     */
    public String getPlace() {
        return mPlace;
    }

    /**
     * This method gets landmark for the Earthquake
     * Location is the major landmark that the earthquake was near
     */
    public String getLandmark() {
        if (mPlace.contains(LOCATION_SEPARATOR)) {
            return mPlace.substring(mPlace.indexOf(LOCATION_SEPARATOR)+4);
        }
        return mPlace;
    }

    /**
     * This method gets the "near string" to the landmark for the Earthquake
     * Location is the major landmark that the earthquake was near
     * Near string is the small text pin pointing the location near the landmark
     */
    public String getNearString(Context context) {
        if (mPlace.contains(LOCATION_SEPARATOR)) {
            return mPlace.substring(0, mPlace.indexOf(LOCATION_SEPARATOR)+3);
        }
        return context.getString(R.string.near_the);
    }

    /**
     * This method sets the time for the Earthquake in UNIX time format
     * @param time UNIX time
     */
    public void setTime(long time) {
        mTime = time;
    }

    /**
     * This method gets the time for the Earthquake in UNIX time
     */
    public long getTime() {
        return mTime;
    }

    /**
     * returns the URL of the event
     * @return URL
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Sets the URL for the event
     * @param url
     */
    public void setUrl(String url) {
        this.mUrl = url;
    }

    /**
     * This method sets tsunami for the Earthquake
     * @param tsunami boolean for Tsunami notification
     */
    public void setTsunami(boolean tsunami) {
        mTsunami = tsunami;
    }

    /**
     * This method gets whether tsunami notification was there for the Earthquake
     */
    public boolean getTsunami() {
        return mTsunami;
    }

    /**
     * This method sets the type for the Earthquake
     * @param type
     */
    public void setType(String type) {
        mType = type;
    }

    /**
     * This method gets the type for the Earthquake
     */
    public String getType() {
        return mType;
    }

    /**
     * This method sets Magnitude Type for the Earthquake
     * @param magType magnitude
     */
    public void setMagType(String magType) {
        mMagType = magType;
    }

    /**
     * This method gets Magnitude Type for the Earthquake
     */
    public String getMagType() {
        return mMagType;
    }

    /**
     * This method sets place Longitude for the Earthquake
     * @param longitude coordinates
     */
    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    /**
     * This method gets place Longitude for the Earthquake
     */
    public double setLongitude() {
        return mLongitude;
    }

    /**
     * This method sets place Latitude for the Earthquake
     * @param latitude coordinates
     */
    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    /**
     * This method gets place Latitude for the Earthquake
     */
    public double getLatitude() {
        return mLatitude;
    }

    /**
     * This method sets Earthquake Depth for the Earthquake
     * @param depth coordinates
     */
    public void setDepth(double depth) {
        mDepth = depth;
    }

    /**
     * This method gets place Depth for the Earthquake
     */
    public double getDepth() {
        return mDepth;
    }

    /**
     * This method returns the date of the Earthquake
     * @return date class Date
     */
    public Date getDate() {
        return mDate;
    }

    public String getFormattedDate(SimpleDateFormat dateFormat) {
        return dateFormat.format(mDate);
    }
}
