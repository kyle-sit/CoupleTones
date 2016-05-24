package com.team4.cse110.coupletones;

import android.location.Location;

import java.util.ArrayList;

/**
 * Created by niralpathak on 5/7/16.
 */


/*
 * This is the Geofencing Trigger class which determines based on the current location whether the
 * user is near any favorite locations
 *
 */
public class GeofenceTrigger implements FavoriteLocationsList
{

    // this method returns a boolean value indicating whether the user's position has triggered

    public boolean isTriggered(Location location)
    {

        return true;
    }


    public ArrayList<String> getArrived(Location currentLocation)
    {
        ArrayList<String> triggeredLocations = new ArrayList<String>();

        for (FavoriteLocation favoriteLocation: local_favLocList)
        {
            //creating a new location based on the user's location
            Location tempLocation = new Location("dummyProvider");

            tempLocation.setLatitude(favoriteLocation.getPosition().latitude);
            tempLocation.setLongitude(favoriteLocation.getPosition().longitude);

            //distanceTo returns meters
            if (tempLocation.distanceTo(currentLocation) <= Constants.GEOFENCE_RADIUS_IN_METERS)
            {
                if (!favoriteLocation.hasArrived())
                {
                    triggeredLocations.add(favoriteLocation.getTitle());
                    favoriteLocation.updateArrived();
                }
            }
        }

        //return the arraylist of fences triggered
        return triggeredLocations;
    }

    public ArrayList<String> getDeparted(Location currentLocation)
    {
        ArrayList<String> triggeredLocations = new ArrayList<String>();

        for (FavoriteLocation favoriteLocation: local_favLocList)
        {
            //creating a new location based on the user's location
            Location tempLocation = new Location("dummyProvider");

            tempLocation.setLatitude(favoriteLocation.getPosition().latitude);
            tempLocation.setLongitude(favoriteLocation.getPosition().longitude);

            //distanceTo returns meters
            if (favoriteLocation.hasArrived())
            {
                if (tempLocation.distanceTo(currentLocation) > Constants.GEOFENCE_RADIUS_IN_METERS)
                {
                    triggeredLocations.add(favoriteLocation.getTitle());
                    favoriteLocation.updateArrived();
                }
            }
        }

        //return the arraylist of fences triggered
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
