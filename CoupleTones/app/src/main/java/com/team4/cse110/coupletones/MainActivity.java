package com.team4.cse110.coupletones;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Scanner;

/*
 * This is our MainActivity class and app that is launched at the start of execution.
 * It sets up our navigation bar and UI for other fragments
 */
public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        SettingsFragment.OnFragmentInteractionListener,
        FavoriteLocationsList
{

    protected static final String TAG = "MainActivity";
    private GoogleApiClient client;
    private GMapFragment mapFrag;
    private SettingsFragment settingsFragment;
    private NotificationHandler notificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) //Called by default when you open the app
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize all the necessary fragments
        mapFrag = new GMapFragment();
        settingsFragment = new SettingsFragment();
        notificationHandler = new NotificationHandler(getApplicationContext());

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, settingsFragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        setTitle("CoupleTones");
        if (drawer != null)
        {
            drawer.addDrawerListener(toggle);
        }

        toggle.syncState();

        loadFromFile();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null)
        {
            navigationView.setNavigationItemSelectedListener(this);
        }

        buildClient();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null)
        {
            if (drawer.isDrawerOpen(GravityCompat.START))
            {
                drawer.closeDrawer(GravityCompat.START);
            }
            else
            {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //this method deals with user's click on an item in the navigation bar
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        Fragment fragment = settingsFragment;

        int id = item.getItemId();

        if (id == R.id.nav_map)
        {
            fragment = mapFrag;
        }
        else if (id == R.id.nav_my_favorites)
        {
            fragment = new FavoriteLocationFragment();
        }
        else if (id == R.id.nav_partners_visited)
        {
            fragment = mapFrag;
        }
        else if (id == R.id.nav_settings)
        {
            fragment = settingsFragment;
        }


        //switch to appropriate fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        
        if (drawer != null)
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        setTitle(item.getTitle());
        return true;

    }

    private void buildClient()
    {
        client = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();
    }
    @Override
    public void onStart()   //Called by default at the beginning and collects client for locations services
    {
        super.onStart();
        client.connect();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        client.disconnect();
    }

    @Override
    protected void onPause() {
        saveToFile();
        super.onPause();
    }

    //Save locations title, latitudes, longitudes and dates.
    public void saveToFile()
    {
        String titles = "";
        String latitudes = "";
        String longitudes = "";
        String dates = "";

        //creating specialized strings using tabs as separators
        for( FavoriteLocation favloc : local_favLocList)
        {
            titles += "\t" + favloc.getTitle();
            latitudes += "\t" + favloc.getPosition().latitude;
            longitudes += "\t" + favloc.getPosition().longitude;
            dates += "\t" + favloc.getSnippet();
        }

        SharedPreferences sharedPreferences = getSharedPreferences( "locations_info", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("location_titles", titles);
        editor.putString("location_latitudes", latitudes);
        editor.putString("location_longitudes", longitudes);
        editor.putString("location_dates", dates);
        editor.apply();
    }

    //Load locations we saved
    public void loadFromFile()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("locations_info", 0);
        String titles = sharedPreferences.getString("location_titles", "");
        String latitudes = sharedPreferences.getString("location_latitudes", "");
        String longitudes = sharedPreferences.getString("location_longitudes", "");
        String dates = sharedPreferences.getString("location_dates", "");

        // due to our specialized saveToFile, we implement our own delimiter for loading
        if (titles != "")
        {
            Scanner scanner1 = new Scanner(titles).useDelimiter("\\s*\t\\s*");
            Scanner scanner2 = new Scanner(latitudes).useDelimiter("\\s*\t\\s*");
            Scanner scanner3 = new Scanner(longitudes).useDelimiter("\\s*\t\\s*");
            Scanner scanner4 = new Scanner(dates).useDelimiter("\\s*\t\\s*");

            String temp = null;
            MarkerOptions markerOpt = new MarkerOptions();
            LatLng latlng = null;
            FavoriteLocation locationSaved = null;

            while (scanner1.hasNext())
            {
                latlng = new LatLng(scanner2.nextFloat(), scanner3.nextFloat());
                markerOpt.position(latlng);

                markerOpt.title(scanner1.next());
                locationSaved = new FavoriteLocation(markerOpt);
                locationSaved.setDescription(scanner4.next());

                local_favLocList.add(locationSaved);
            }

        }
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

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
