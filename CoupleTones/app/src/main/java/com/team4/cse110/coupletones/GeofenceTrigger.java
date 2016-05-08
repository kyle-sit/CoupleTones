package com.team4.cse110.coupletones;

import android.location.Location;

import java.util.ArrayList;

/**
 * Created by niralpathak on 5/7/16.
 */
public class GeofenceTrigger implements FavoriteLocationsList
{
    public boolean isTriggered(Location location)
    {
        if (getTriggered(location).isEmpty())
        {
            return false;
        }
        return true;
    }

    public ArrayList<String> getTriggered(Location location)
    {
        ArrayList<String> triggeredLocations = new ArrayList<String>();

        for (FavoriteLocation favoriteLocation: favLocList)
        {
            Location tempLocation = new Location("dummyProvider");
            tempLocation.setLatitude(favoriteLocation.getPosition().latitude);
            tempLocation.setLongitude(favoriteLocation.getPosition().longitude);

            //distanceTo returns meters
            if (tempLocation.distanceTo(location) >= Constants.GEOFENCE_RADIUS_IN_METERS)
            {
                triggeredLocations.add(favoriteLocation.getTitle());
            }
        }

        return triggeredLocations;
    }

    @Override
    public void addLocation(FavoriteLocation favoriteLocation) {

    }

    @Override
    public void deleteLocation(FavoriteLocation favoriteLocation) {

    }

    @Override
    public void editLocation(FavoriteLocation favoriteLocation, String newName) {

    }
}
