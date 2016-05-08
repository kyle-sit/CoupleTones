package com.team4.cse110.coupletones;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.team4.cse110.coupletones.FavoriteLocation;

import junit.framework.TestCase;

/**
 * Created by jialiangzhou on 5/7/16.
 */


public class FavoriteLocationTests extends TestCase
{
    FavoriteLocation favoriteLocation = new FavoriteLocation();

    /*if this passes, then default constructor works*/
    public void testNull(){
        assertNotNull(favoriteLocation);
    }

    //faild because of memory address
    public void testMarkerOption(){
        MarkerOptions marker = new MarkerOptions().position(new LatLng(0,0)).title("").snippet("");
        assertEquals(marker,favoriteLocation.getMarkerOptions());
    }

    public void testPosition(){
        MarkerOptions marker = new MarkerOptions().position(new LatLng(0,0)).title("N/A").snippet("N/A");
        assertEquals(marker.getPosition(),favoriteLocation.getMarkerOptions().getPosition());
    }

    public void testSnippet(){
        String s = favoriteLocation.getMarkerOptions().getSnippet();
        assertEquals("N/A",s);
    }
    public void testTitle(){
        assertEquals("N/A",favoriteLocation.getTitle());
    }

    /*this faild because marker.settitle was called first,
      but we didnt initiallize because we dont initiallize
      marker in drfault constructor
    */
    public void testSetTitle(){
        favoriteLocation.editName("test");
        assertEquals("test",favoriteLocation.getTitle());
    }

}