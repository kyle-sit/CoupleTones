package com.team4.cse110.coupletones;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.Date;

/*
 *
 * This is the FavoriteLocation class. This defines a name, a snippet, and LatLng position
 * and a Date of when the object was created.
 */
public class FavoriteLocation
{

    private Marker marker;
    private String snippet;
    private String title;
    private LatLng location;
    private Date dateCreated;

    /* default constructor */
    public FavoriteLocation()
    {
        title = "N/A";
        location = new LatLng(0,0);
        snippet = "N/A";
    }

    /* use member variables from the arg marker to construct a FavoriteLocation */
    public FavoriteLocation(Marker marker){
        this.title = marker.getTitle();
        this.marker = marker;
        this.location = marker.getPosition();
        dateCreated = new Date();
        this.snippet = "created " + dateCreated.toString();
        setDescription(snippet);
    }


    /* changes the name of the Favorite Location */
    public void editName(String name)
    {
        if (marker != null) {
            marker.setTitle(name);
        }
        this.title = name;
    }

    /* changes the description */
    private void setDescription(String description)
    {
        this.snippet = description;
        if (marker != null) {
            marker.setSnippet(description);
        }
    }

    public MarkerOptions getMarkerOptions()
    {
        return new MarkerOptions().position(location).title(title).snippet(snippet);
    }

    @Override
    public String toString()
    {
        return title+"\n\t\t\t"+snippet;
    }

    public String getTitle()
    {
        return title;
    }

    public LatLng getPosition()
    {
        return location;
    }

}