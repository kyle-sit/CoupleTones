package com.team4.cse110.coupletones.Tests;

import android.location.Location;

import com.team4.cse110.coupletones.GeofenceTrigger;

import junit.framework.TestCase;

/**
 * Created by jialiangzhou on 5/8/16.
 */
public class GeofenceTests extends TestCase
{
    Location location = new Location("test");

    /*success because the size should be 0 */
    public void testTrig(){
        GeofenceTrigger geo = new GeofenceTrigger();
        assertEquals(0,geo.getTriggered(location).size());

    }

    /*Failed cause it should return false*/
    public void testIsTrig(){
        GeofenceTrigger geo = new GeofenceTrigger();
        assertFalse(geo.isTriggered(location));
    }


}