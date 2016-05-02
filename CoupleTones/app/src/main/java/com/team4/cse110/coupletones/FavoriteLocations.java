package com.team4.cse110.coupletones;

import android.app.AlertDialog;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;
import java.util.HashSet;
import android.widget.EditText;


/**
 * Created by niralpathak on 5/1/16.
 */
public class FavoriteLocations
{
    public HashSet<FavoriteLocation> favLocationsSet;

    public FavoriteLocations()
    {
        favLocationsSet = new HashSet<FavoriteLocation>();
    }

    public void addLocation(FavoriteLocation favoriteLocation) {
        favLocationsSet.add(favoriteLocation);
    }

    public void deleteLocation(FavoriteLocation favoriteLocation)
    {
        favLocationsSet.remove(favoriteLocation);
    }

    public void editLocation(FavoriteLocation favoriteLocation)
    {
        AlertDialog.Builder buildDialog = new AlertDialog.Builder();
        buildDialog.setTitle("Name your Favorite Location");
        final EditText userInput = new EditText();
    }
}
