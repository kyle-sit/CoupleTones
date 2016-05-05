package com.team4.cse110.coupletones;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;


public interface FavoriteLocationsList {

    List<FavoriteLocation> list = new ArrayList<FavoriteLocation>();

    void addLocation(FavoriteLocation favoriteLocation);

    void deleteLocation(FavoriteLocation favoriteLocation);

    void editLocation(FavoriteLocation favoriteLocation);

}