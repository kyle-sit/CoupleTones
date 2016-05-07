package com.team4.cse110.coupletones;

import android.util.Log;

/**
 * Created by niralpathak on 5/7/16.
 */
public class GeofencingReceiver extends GeofenceTransitionIntentService
{
    @Override
    protected void onEnteredGeofences(String[] strings)
    {
        Log.d(GeofencingReceiver.class.getName(), "onEnter");

        //do something!

        System.out.println("In onEnter for GeofencingReceiver-----------------*********************");

    }

    @Override
    protected void onError(int i)
    {
        Log.e(GeofencingReceiver.class.getName(), "Error: " + i);
    }

}