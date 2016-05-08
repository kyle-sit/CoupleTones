package com.team4.cse110.coupletones;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/*
 * This class is the fragment that shows our Google Map. It deals with everything related to user
 * input on a map.
 */
public class GMapFragment extends Fragment implements OnMapReadyCallback, FavoriteLocationsList
{

    private static GoogleMap mMap;
    private Context context;
    private float zoomLevel = 15.0f;

    /*
     * citation for aid: http://developer.android.com/training/maps/index.html
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        context = inflater.getContext();
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    /*
     * citation for aid: http://developer.android.com/training/maps/index.html
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = new SupportMapFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.mapFragmentContainer, mapFragment, "mapFragment");
            ft.commit();
            fm.executePendingTransactions();
        }
        mapFragment.getMapAsync(this);
    }

    /*
     * this method defines the user's position on the map and listens for a map click in which case,
     * we should create a marker
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        //this method restores all the markers in our favoriteLocationList from our interface
        restoreMarkers();
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if (mMap == null)
        {
            return;
        }
        //checking permissions for getting location
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission. ACCESS_COARSE_LOCATION) != PackageManager. PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return ;
        }
        else if (mMap != null )
        {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }

        // moves the camera for the map to the user's current location
        LocationListener locationListener = new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location) {
                //called when a new location is found by the network location provider
                LatLng tempLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tempLocation, zoomLevel));
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

        };

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.GPS_PROVIDER;
        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);

        // listens for a map click, in which case create a marker
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(final LatLng latLng)
            {
                createMarker(latLng);
            }
        });

    }

    // this method asks the user for a name to assign to the marker for a favorite location
    private void createMarker(final LatLng latLng)
    {
        //prompt user for name
        AlertDialog.Builder buildDialog = new AlertDialog.Builder(context);
        buildDialog.setTitle("Adding a Favorite Location...");
        buildDialog.setMessage("Name your location\n");

        final EditText userInput = new EditText(context);
        userInput.setHint("name");

        userInput.setInputType(InputType.TYPE_CLASS_TEXT);
        buildDialog.setView(userInput);

        //submitting the name
        buildDialog.setPositiveButton("Apply", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String markerTitle = userInput.getText().toString();
                if (markerTitle.isEmpty())
                {
                    return;
                }

                //no duplicate names allowed
                for (FavoriteLocation favoriteLocation: favLocList)
                {
                    if (favoriteLocation.getTitle().equals(markerTitle))
                    {
                        Toast.makeText(getContext(), "Unsuccessful - '"+markerTitle+"' already exists.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                //create the marker and add it to the map
                Marker tempMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(markerTitle));
                addLocation(new FavoriteLocation(tempMarker));

            }
        });

        buildDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        buildDialog.show();

    }

    private void restoreMarkers()
    {
        // add all the markers to the map
        if (mMap != null)
        {
            for (FavoriteLocation favLoc : favLocList) {
                mMap.addMarker(favLoc.getMarkerOptions());
            }
        }
    }

    //adding location to our universal favorite locations list
    @Override
    public void addLocation(FavoriteLocation favoriteLocation)
    {
        favLocList.add(0,favoriteLocation);

        Toast.makeText(
                getContext(),
                "Successfully added '"+favoriteLocation.getTitle()+"'",
                Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void deleteLocation(FavoriteLocation favoriteLocation)
    {
        // empty body
    }

    @Override
    public void editLocation(FavoriteLocation favoriteLocation, String newName)
    {
        // empty body
    }


}
