package com.team4.cse110.coupletones;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

/**
 * Created by niralpathak on 5/7/16.
 */
public abstract class GeofenceTransitionIntentService extends IntentService {

    public GeofenceTransitionIntentService()
    {
        super("GeofenceTransitionIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        GeofencingEvent event = GeofencingEvent.fromIntent(intent);

        if(event != null)
        {

            if(event.hasError())
            {
                onError(event.getErrorCode());
            }
            else
            {
                int transition = event.getGeofenceTransition();

                if(transition == Geofence.GEOFENCE_TRANSITION_ENTER )
                {
                    String[] geofenceIds = new String[event.getTriggeringGeofences().size()];

                    for (int index = 0; index < event.getTriggeringGeofences().size(); index++)
                    {
                        geofenceIds[index] = event.getTriggeringGeofences().get(index).getRequestId();
                    }

                    onEnteredGeofences(geofenceIds);
                }
            }

        }
    }

    protected abstract void onEnteredGeofences(String[] geofenceIds);

    protected abstract void onError(int errorCode);
}
