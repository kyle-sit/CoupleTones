package com.team4.cse110.coupletones;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 5/4/16.
 */
public class FavoriteLocationsList {

    private List<FavoriteLocation> list;

    public FavoriteLocationsList() {

        list = new ArrayList<FavoriteLocation>();
    }

    public void addLocation(FavoriteLocation favoriteLocation) {
        list.add(0, favoriteLocation);
    }

    private void deleteLocation(FavoriteLocation favoriteLocation)
    {
        list.remove(favoriteLocation);
    }

    private void editLocation(FavoriteLocation favoriteLocation)
    {
        //AlertDialog.Builder buildDialog = new AlertDialog.Builder(this);
        //buildDialog.setTitle("Rename your Favorite Location");
        //final EditText userInput = new EditText(this);
    }

    public FavoriteLocation[] createArray()
    {
        return list.toArray(new FavoriteLocation[list.size()]);
    }

}