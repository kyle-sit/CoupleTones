package com.team4.cse110.coupletones;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.Date;

/**
 * Created by jialiangzhou on 4/30/16.
 */
public class FavoriteLocation
{
    private Marker marker;
    private String snippet;
    private String name;
    private Date dateCreated;

    public FavoriteLocation(Marker marker){
        this.name = marker.getTitle();
        this.marker = marker;
        dateCreated = new Date();
        setDescription("created " + dateCreated.toString());
    }

    public void editName(String name){
        marker.setTitle(name);
        this.name = name;
    }

    private void setDescription(String description) {
        this.snippet = description;
        marker.setSnippet(description);
    }

    public LatLng getLatLng()
    {
        return marker.getPosition();
    }

}