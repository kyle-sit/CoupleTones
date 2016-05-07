package com.team4.cse110.coupletones;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;

/**
 * Created by niralpathak on 5/7/16.
 */
public class GeofencingRegister implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        FavoriteLocationsList
{
    private Context mContext;
    private GoogleApiClient client;
    private List<Geofence> geofencesToAdd;
    private PendingIntent mGeofencePendingIntent;

    private GeofencingRegisterCallbacks mCallback;

    public final String TAG = this.getClass().getName();

    public GeofencingRegister(Context context) {
        mContext = context;

        client = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public void setGeofencingCallback(GeofencingRegisterCallbacks callback) {
        mCallback = callback;
    }


    public void registerGeofences() {
        geofencesToAdd = geofenceList;

        client.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mCallback != null) {
            mCallback.onApiClientConnected();
        }

        mGeofencePendingIntent = createRequestPendingIntent();

        GeofencingRequest geofencingRequest = new GeofencingRequest.Builder().addGeofences(geofencesToAdd).build();

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Log.e(TAG, "Permission denied");

            return;
        }
        PendingResult<Status> result = LocationServices.GeofencingApi.addGeofences(client, geofencingRequest, mGeofencePendingIntent);
        result.setResultCallback(new ResultCallback<Status>()
        {
            @Override
            public void onResult(Status status)
            {
                if (status.isSuccess())
                {
                    // Successfully registered
                    if(mCallback != null)
                    {
                        mCallback.onGeofencesRegisteredSuccessful();
                    }
                }
                else if (status.hasResolution())
                {
                    // Google provides a way to fix the issue
                    /*
                    status.startResolutionForResult(
                            mContext,     // your current activity used to receive the result
                            RESULT_CODE); // the result code you'll look for in your
                    // onActivityResult method to retry registering
                    */
                }
                else
                {
                    // No recovery. Weep softly or inform the user.
                    Log.e(TAG, "Registering failed: " + status.getStatusMessage());
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i)
    {
        if(mCallback != null)
        {
            mCallback.onApiClientSuspended();
        }

        Log.e(TAG, "onConnectionSuspended: " + i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        if(mCallback != null)
        {
            mCallback.onApiClientConnectionFailed(connectionResult);
        }

        Log.e(TAG, "onConnectionFailed: " + connectionResult.getErrorCode());
    }

    /**
     * Returns the current PendingIntent to the caller.
     *
     * @return The PendingIntent used to create the current set of geofences
     */
    public PendingIntent getRequestPendingIntent()
    {
        return createRequestPendingIntent();
    }

    /**
     * Get a PendingIntent to send with the request to add Geofences. Location
     * Services issues the Intent inside this PendingIntent whenever a geofence
     * transition occurs for the current list of geofences.
     *
     * @return A PendingIntent for the IntentService that handles geofence
     * transitions.
     */
    private PendingIntent createRequestPendingIntent()
    {
        if (mGeofencePendingIntent != null)
        {
            return mGeofencePendingIntent;
        }
        else
        {
            Intent intent = new Intent(mContext, GeofencingReceiver.class);
            return PendingIntent.getService(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
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

    @Override
    public void addGeoFence(Geofence geofence) {

    }
}
