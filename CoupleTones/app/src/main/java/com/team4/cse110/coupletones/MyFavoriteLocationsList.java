package com.team4.cse110.coupletones;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MyFavoriteLocationsList extends AppCompatActivity {

    public List<FavoriteLocation> list;

    public MyFavoriteLocationsList() {

        list = new ArrayList<FavoriteLocation>();
    }

    public void addLocation(FavoriteLocation favoriteLocation) {
        list.add(0, favoriteLocation);
    }

    public void deleteLocation(FavoriteLocation favoriteLocation)
    {
        list.remove(favoriteLocation);
    }

    public void editLocation(FavoriteLocation favoriteLocation)
    {
        AlertDialog.Builder buildDialog = new AlertDialog.Builder(this);
        buildDialog.setTitle("Name your Favorite Location");
        final EditText userInput = new EditText(this);
    }

    public FavoriteLocation[] createArray()
    {
        return list.toArray(new FavoriteLocation[list.size()]);
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
