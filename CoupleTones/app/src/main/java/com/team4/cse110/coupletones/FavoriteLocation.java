package com.team4.cse110.coupletones;

import com.google.android.gms.location.Geofence;
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
    private Geofence.Builder fence;


    public FavoriteLocation(Marker marker){
        this.title = marker.getTitle();
        this.marker = marker;
        this.location = marker.getPosition();
        dateCreated = new Date();
        this.snippet = "created " + dateCreated.toString();
        setDescription(snippet);

        fence = new Geofence.Builder()
                .setRequestId(this.title)
                .setCircularRegion(location.latitude, location.longitude, Constants.GEOFENCE_RADIUS_IN_METERS)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER);
    }

    public void editName(String name){
        marker.setTitle(name);
        this.title = name;
        fence.setRequestId(this.title);
    }

    private void setDescription(String description) {
        this.snippet = description;
        marker.setSnippet(description);
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

}