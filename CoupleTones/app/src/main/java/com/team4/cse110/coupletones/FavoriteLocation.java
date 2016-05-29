package com.team4.cse110.coupletones;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 *
 * This is the FavoriteLocation class. This defines a name, a snippet, and LatLng position
 * and a Date of when the object was created.
 */
public class FavoriteLocation
{
    private Marker marker;
    private String snippet;
    private String title;
    private LatLng location;
    private Date dateCreated;
    private boolean arrived;
    private double latitude;
    private double longitude;
    private MarkerOptions markerOptions;

    /* default constructor */
    public FavoriteLocation()
    {
        title = "N/A";
        snippet = "N/A";
        arrived = false;

        latitude = 0;
        longitude = 0;
        location = new LatLng(latitude, longitude);
        setMarkerOptions();
    }

    /* use member variables from the arg marker to construct a FavoriteLocation */
    public FavoriteLocation(Marker marker){
        this.title = marker.getTitle();
        this.marker = marker;
        this.location = marker.getPosition();
        this.dateCreated = new Date();
        this.snippet = "created " + dateCreated.toString();
        setDescription(snippet);
        arrived = false;

        this.latitude = location.latitude;
        this.longitude = location.longitude;
        setMarkerOptions();

    }

    /* changes the name of the Favorite Location */
    public FavoriteLocation(MarkerOptions markerOptions)
    {
        this.title = markerOptions.getTitle();
        this.marker = null;
        this.location = markerOptions.getPosition();
        this.snippet = markerOptions.getSnippet();
        this.arrived = false;

        this.latitude = location.latitude;
        this.longitude = location.longitude;
        setMarkerOptions();

    }

    public FavoriteLocation(double latitude, double longitude, String snippet, String title)
    {
        this.title = title;
        this.snippet = snippet;
        this.latitude = latitude;
        this.longitude = longitude;

        this.marker = null;
        this.arrived = false;
        this.dateCreated = null;

        this.location = new LatLng(latitude, longitude);
        setMarkerOptions();
    }

    public void editName(String name)
    {
        if (marker != null) {
            marker.setTitle(name);
        }
        this.title = name;
    }

    /* changes the description */
    public void setDescription(String description)
    {
        this.snippet = description;
        if (marker != null) {
            marker.setSnippet(description);
        }
    }

    @JsonIgnore
    public void setMarkerOptions()
    {
        markerOptions = new MarkerOptions().position(location).title(title).snippet(snippet);
    }


    /* returns marker options in order to recreate Marker */
    @JsonIgnore
    public MarkerOptions getMarkerOptions()
    {
        setMarkerOptions();
        return markerOptions;
    }

    /* override toString() method in order to print out FavoriteLocations in list format */
    @Override
    public String toString()
    {

        return title + "\n\t\t\t" + snippet;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    //keep for JSON creation
    public String getSnippet()
    {
        return snippet;
    }

    //keep for JSON creation
    public void setSnippet(String snippet)
    {
        this.snippet = snippet;
    }

    //keep for JSON creation
    public double getLatitude()
    {
        return latitude;
    }

    //keep for JSON creation
    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    //keep for JSON creation
    public double getLongitude()
    {
        return longitude;
    }

    //keep for JSON creation
    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public void updateArrived() {
        arrived = !arrived;
    }

    public boolean hasArrived() {
        return arrived;
    }


}