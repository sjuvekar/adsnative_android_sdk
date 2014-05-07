package com.adsnative.android.sdk.test;

import com.adsnative.android.sdk.request.AdRequest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class AdRequestTest {

    private AdRequest adRequest;

    @Before
    public void setup() {
        adRequest = new AdRequest("12345");
    }

    @Test
    public void testPutKeyword() {
        Assert.assertEquals(true, adRequest.putKeyword("test_keyword"));
        Assert.assertEquals(true, adRequest.putKeyword(""));
    }

    @Test
    public void testRemoveKeyword() {
        adRequest.putKeyword("123");
        Assert.assertEquals(false, adRequest.removeKeyword("12"));
        Assert.assertEquals(true, adRequest.removeKeyword("123"));
    }

    @Test
    public void testPutParameter() {
        Assert.assertNull("Adding new key failed", adRequest.putParameter("test_key", "first_test"));
        Assert.assertEquals("first_test", adRequest.putParameter("test_key", "test_value1"));
        Assert.assertNotNull("Putting parameter failed", adRequest.putParameter("test_key", "test_value2"));
    }

    @Test
    public void testRemoveParameter() {
        Assert.assertNull("Adding new key failed", adRequest.putParameter("test_remove", "remove"));
        Assert.assertEquals("remove", adRequest.removeParameter("test_remove"));
        Assert.assertNull("Removing succes - test failed", adRequest.removeParameter("test_remove"));
    }
}
