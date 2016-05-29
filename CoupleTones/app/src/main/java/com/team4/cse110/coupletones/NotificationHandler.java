package com.team4.cse110.coupletones;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.ArrayList;


/**
 * Created by niralpathak on 5/7/16.
 */

/*
 * This class deals with sending notifications depending on our Geofence Trigger
 */
public class NotificationHandler extends Service implements
        FavoriteLocationsList,
        LocationListener
{
    //initialize our trigger
    private GeofenceTrigger geofenceTrigger;
    private LocationManager locationManager;
    private String locationProvider;
    private Context context;

    public NotificationHandler(Context context)
    {
        geofenceTrigger = new GeofenceTrigger();

        this.context = context;

        locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
        locationProvider = LocationManager.GPS_PROVIDER;

        //check for permissions before getting user's location
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this.context, "Fix Permissions for Location", Toast.LENGTH_LONG).show();
            return;
        }

        locationManager.requestLocationUpdates(locationProvider, 0, 0, this);

        Firebase.setAndroidContext(context);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }


    //when the user's location changes, check if a geofence is triggered and send notification if so
    @Override
    public void onLocationChanged(Location location)
    {
        if (geofenceTrigger.isArrivedTriggered(location))
        {
            arrival_sendNotification(geofenceTrigger.getArrivedLocations());
        }
        if (geofenceTrigger.isDepartedTriggered(location))
        {
            departed_sendNotification(geofenceTrigger.getDepartedLocations());
        }
    }

    protected void arrival_sendNotification(ArrayList<String> locationIds)
    {
        if (locationIds.isEmpty())
        {
            return;
        }

        String message = "Arrived at the following location(s): ";
        for (String id: locationIds)
        {
            message += id + ",";
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }

    protected void departed_sendNotification(ArrayList<String> locationIds)
    {
        if (locationIds.isEmpty())
        {
            return;
        }

        String message = "Departed the following location(s): ";
        for (String id: locationIds)
        {
            message += id + ",";
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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
