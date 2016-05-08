package com.team4.cse110.coupletones.Tests;

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

    /*
    if this passes, then default constructor works
    */
    public void test_Null()
    {
        assertNotNull(favoriteLocation);
    }

    /*
    testing the getPosition method we defined
     */
    public void test_Position()
    {
        MarkerOptions marker = new MarkerOptions().position(new LatLng(0,0)).title("N/A").snippet("N/A");
        assertEquals(marker.getPosition(),favoriteLocation.getMarkerOptions().getPosition());
    }

    /*
    testing the getSnippet and getMarkerOptions Methds
     */
    public void test_Snippet(){
        String s = favoriteLocation.getMarkerOptions().getSnippet();
        assertEquals("N/A",s);
    }

    /*
    testing the title setting and getting for our favorite location title
     */
    public void test_Title(){
        assertEquals("N/A",favoriteLocation.getTitle());
    }

    /*
    testing null marker passing through
    */
    public void test_SetTitle()
    {
        favoriteLocation.editName("test");
        System.err.println("title = "+favoriteLocation.getTitle());
        assertEquals("test",favoriteLocation.getTitle());
    }

}