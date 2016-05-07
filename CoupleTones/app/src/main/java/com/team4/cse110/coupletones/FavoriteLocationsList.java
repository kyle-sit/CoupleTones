package com.team4.cse110.coupletones;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.google.android.gms.location.Geofence;

import java.util.ArrayList;
import java.util.List;


public interface FavoriteLocationsList {

    List<FavoriteLocation> favLoclist = new ArrayList<FavoriteLocation>();
    List<Geofence> geofenceList = new ArrayList<Geofence>();

    void addLocation(FavoriteLocation favoriteLocation);

    void deleteLocation(FavoriteLocation favoriteLocation);

    void editLocation(FavoriteLocation favoriteLocation, String newName);

    void addGeoFence(Geofence geofence);

}