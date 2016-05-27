package com.team4.cse110.coupletones;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.google.android.gms.location.Geofence;

import java.util.ArrayList;
import java.util.List;

/*
 * This is the interface that ties our entire project together; we have a list of our favoriteLocations
 * that we can edit. This class is basically a mediator between all other classes
 */
public interface FavoriteLocationsList {

    List<FavoriteLocation> local_favLocList = new ArrayList<FavoriteLocation>();
    List<FavoriteLocation> partner_favLocList = new ArrayList<FavoriteLocation>();


    void addLocation(FavoriteLocation favoriteLocation);

    void deleteLocation(FavoriteLocation favoriteLocation);

    void editLocation(FavoriteLocation favoriteLocation, String newName);

}