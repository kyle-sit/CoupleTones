package com.team4.cse110.coupletones;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.Date;


public class FavoriteLocation
{

    private Marker marker;
    private String snippet;
    private String title;
    private LatLng location;
    private Date dateCreated;

    public FavoriteLocation()
    {
        title = "N/A";
        location = new LatLng(0,0);
        snippet = "N/A";
    }

    public FavoriteLocation(Marker marker){
        this.title = marker.getTitle();
        this.marker = marker;
        this.location = marker.getPosition();
        dateCreated = new Date();
        this.snippet = "created " + dateCreated.toString();
        setDescription(snippet);
    }

    public FavoriteLocation(MarkerOptions markerOptions)
    {
        this.title = markerOptions.getTitle();
        this.marker = null;
        this.location = markerOptions.getPosition();
        this.snippet = markerOptions.getSnippet();
    }

    public void editName(String name)
    {
        if (marker != null) {
            marker.setTitle(name);
        }
        this.title = name;
    }

    public void setDescription(String description)
    {
        this.snippet = description;
        if (marker != null) {
            marker.setSnippet(description);
        }
    }

    public MarkerOptions getMarkerOptions()
    {
        return new MarkerOptions().position(location).title(title).snippet(snippet);
    }

    @Override
    public String toString()
    {
        return title+"\n\t\t\t"+snippet;
    }

    public String getTitle()
    {
        return title;
    }

    public Marker getMarker() {return marker;}

    public String getSnippet() {return snippet;}

    public LatLng getPosition()
    {
        return location;
    }

}