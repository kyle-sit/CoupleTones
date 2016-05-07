package com.team4.cse110.coupletones;

import com.google.android.gms.common.ConnectionResult;

/**
 * Created by niralpathak on 5/7/16.
 */
public interface GeofencingRegisterCallbacks
{
    public void onApiClientConnected();
    public void onApiClientSuspended();
    public void onApiClientConnectionFailed(ConnectionResult connectionResult);
    public void onGeofencesRegisteredSuccessful();
}
