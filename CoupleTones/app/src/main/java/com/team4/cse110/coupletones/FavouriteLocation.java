package com.team4.cse110.coupletones;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by jialiangzhou on 4/30/16.
 */
public class FavouriteLocation {

    private Marker marker;
    private String description;


    public FavouriteLocation(){
        description = "";
    }

    public FavouriteLocation(Marker marker){
        this.marker = marker;
    }

    public void editName(String name){
        marker.setTitle(name);
    }

    public void setDescription(String description){
        this.description = description;
    }


    public String getDescription(){
        return description;

    }

    public String getFavouriteName(){
        return marker.getTitle();
    }

    public LatLng getFavouritePosition(){
        return marker.getPosition();
    }

    public void print(){
        System.out.println(marker.getPosition() + " " + marker.getTitle());
    }


}