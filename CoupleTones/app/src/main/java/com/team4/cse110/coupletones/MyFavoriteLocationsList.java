package com.team4.cse110.coupletones;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;

import java.util.HashSet;

public class MyFavoriteLocationsList extends AppCompatActivity {

    public HashSet<FavoriteLocation> favLocationsSet;

    public MyFavoriteLocationsList()
    {
        favLocationsSet = new HashSet<FavoriteLocation>();
    }

    public void addLocation(FavoriteLocation favoriteLocation) {
        favLocationsSet.add(favoriteLocation);
    }

    public void deleteLocation(FavoriteLocation favoriteLocation)
    {
        favLocationsSet.remove(favoriteLocation);
    }

    public void editLocation(FavoriteLocation favoriteLocation)
    {
        AlertDialog.Builder buildDialog = new AlertDialog.Builder();
        buildDialog.setTitle("Name your Favorite Location");
        final EditText userInput = new EditText();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite_locations_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
