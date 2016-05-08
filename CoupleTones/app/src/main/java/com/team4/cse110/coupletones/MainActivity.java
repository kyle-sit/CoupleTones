package com.team4.cse110.coupletones;

import android.annotation.TargetApi;
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

/*
 * This is our MainActivity class and app that is launched at the start of execution.
 * It sets up our navigation bar and UI for other fragments
 */
public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        PartnerFragment.OnFragmentInteractionListener,
        UserInfoFragment.OnFragmentInteractionListener
{

    protected static final String TAG = "MainActivity";
    private GoogleApiClient client;
    private GMapFragment mapFrag;
    private UserInfoFragment userInfoFrag;
    private PartnerFragment partnerFrag;
    private NotificationHandler notificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize all the necessary fragments
        mapFrag = new GMapFragment();
        userInfoFrag = new UserInfoFragment();
        partnerFrag = new PartnerFragment();
        notificationHandler = new NotificationHandler(getApplicationContext());

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, userInfoFrag).commit();

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
        Fragment fragment = null;
        Class fragmentClass = FavoriteLocationFragment.class;

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
            fragment = userInfoFrag;
        }
        else if (id == R.id.nav_edit_partner)
        {
            fragment = partnerFrag;
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
    public void onStart()
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
}
