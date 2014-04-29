package com.adsnative.android.sdk.test;

import android.app.Activity;

import com.adsnative.android.sdk.MainActivity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    @Before
    public void setup() {
        //do whatever is necessary before every test
    }

    @Test
    public void testActivityFound() {
        Activity activity = Robolectric.buildActivity(MainActivity.class).create().get();

        Assert.assertNotNull(activity);
    }
}
