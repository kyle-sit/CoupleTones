package com.team4.cse110.coupletones.Tests;

import com.team4.cse110.coupletones.PartnerFragment;

import junit.framework.TestCase;

/**
 * Created by jialiangzhou on 5/8/16.
 */
public class PartnerTests extends TestCase {
    PartnerFragment partner = new PartnerFragment();

    /*test should fail cause constructor should work*/
    public void testConstructor(){
        assertNotNull(partner);
    }

    public void test_UserInfoFragment(){
        try
        {
            partner.newInstance("par1","par2");
            assertTrue(true);
        }
        catch(RuntimeException exp){
            fail();
        }
    }

    public void StaticTest(){
        try{
            PartnerFragment.newInstance("par1","par2");
            fail();
        }
        catch(RuntimeException exp){
            assertTrue(true);
        }
    }
}
