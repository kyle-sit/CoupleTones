package com.team4.cse110.coupletones;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.SettingsApi;
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
    private LocalFavoriteLocationFragment local_favLocFrag;
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
        local_favLocFrag = new LocalFavoriteLocationFragment();

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

        if (SettingsFragment.getLoggedIn())
        {
            setTitle(item.getTitle());

            if (id == R.id.nav_map) {
                fragment = mapFrag;
            } else if (id == R.id.nav_my_favorites) {
                mapFrag.restoreMarkers();
                fragment = local_favLocFrag;
            } else if (id == R.id.nav_partners_visited) {
                fragment = mapFrag;
            } else if (id == R.id.nav_settings) {
                fragment = settingsFragment;
            }
        }
        else
        {
            Toast.makeText(this, "Please login", Toast.LENGTH_LONG).show();
            setTitle("Settings");
        }


        //switch to appropriate fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        
        if (drawer != null)
        {
            drawer.closeDrawer(GravityCompat.START);
        }

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
