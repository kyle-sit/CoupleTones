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
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.widget.Toast;

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
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this.context, "Fix Permissions for Location", Toast.LENGTH_LONG).show();
            return;
        }

        locationManager.requestLocationUpdates(locationProvider, 0, 0, this);

    }

    // given ids that trigger a favorite location, send a text message to our partner
    protected void sendNotification(ArrayList<String> locationIds)
    {
        String number = PartnerFragment.getPartner_number();
        String partner_name = PartnerFragment.getPartner_name();
        String local_name = UserInfoFragment.getName();
        String message = "Hello "+partner_name+", "+local_name+" has visited ";

        //concatenating strings when necessary
        if (locationIds.size() == 1)
        {
            message += "the following location: "+locationIds.get(0);
        }
        else
        {
            String multipleIds = "";
            for (String id: locationIds)
            {
                multipleIds += id+"\n";
            }
            message += "the following locations: "+ multipleIds;
        }

        SmsManager manager = SmsManager.getDefault();

        //do not send message if the partner's number isnt set up
        if (number != null && !number.isEmpty()) {
            manager.sendTextMessage(number, null, message, null, null);
        }
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
        /*
        if (geofenceTrigger.isTriggered(location))
        {
            sendNotification(geofenceTrigger.getTriggered(location));
        }
        */


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
