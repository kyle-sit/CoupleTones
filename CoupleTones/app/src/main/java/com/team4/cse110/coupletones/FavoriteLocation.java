package com.team4.cse110.coupletones;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.Date;

/**
 * Created by jialiangzhou on 4/30/16.
 */
public class FavoriteLocation
{
    private final float GEOFENCE_RADIUS_IN_METERS = 160.934f; // this is 1/10 mile
    private Marker marker;
    private String snippet;
    private String name;
    private LatLng location;
    private Date dateCreated;


    public FavoriteLocation(Marker marker){
        this.name = marker.getTitle();
        this.marker = marker;
        this.location = marker.getPosition();
        dateCreated = new Date();
        setDescription("created " + dateCreated.toString());
        Geofence.Builder fence = new Geofence.Builder()
                .setRequestId(location.toString())
                .setCircularRegion(location.latitude, location.longitude, GEOFENCE_RADIUS_IN_METERS)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER);
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