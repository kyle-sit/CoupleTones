package com.team4.cse110.coupletones;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.Date;

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

    /* default constructor */
    public FavoriteLocation()
    {
        title = "N/A";
        location = new LatLng(0,0);
        snippet = "N/A";
        arrived = false;
    }

    /* use member variables from the arg marker to construct a FavoriteLocation */
    public FavoriteLocation(Marker marker){
        this.title = marker.getTitle();
        this.marker = marker;
        this.location = marker.getPosition();
        dateCreated = new Date();
        this.snippet = "created " + dateCreated.toString();
        setDescription(snippet);
        arrived = false;
    }

    /* changes the name of the Favorite Location */
    public FavoriteLocation(MarkerOptions markerOptions)
    {
        this.title = markerOptions.getTitle();
        this.marker = null;
        this.location = markerOptions.getPosition();
        this.snippet = markerOptions.getSnippet();
        this.arrived = false;
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

    /* returns marker options in order to recreate Marker */
    public MarkerOptions getMarkerOptions()
    {
        return new MarkerOptions().position(location).title(title).snippet(snippet);
    }

    /* override toString() method in order to print out FavoriteLocations in list format */
    @Override
    public String toString()
    {
        return title+"\n\t\t\t"+snippet;
    }

    public String getTitle()
    {
        return title;
    }

    public String getSnippet()
    {
        return snippet;
    }

    public LatLng getPosition()
    {
        return location;
    }

    public void updateArrived()
    {
        arrived = !arrived;
    }

    public boolean hasArrived()
    {
        return arrived;
    }


}