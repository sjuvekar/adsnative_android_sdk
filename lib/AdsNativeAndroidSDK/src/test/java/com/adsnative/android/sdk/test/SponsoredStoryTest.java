package com.adsnative.android.sdk.test;

import android.content.Context;

import com.adsnative.android.sdk.request.AdRequest;
import com.adsnative.android.sdk.story.SponsoredStory;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class SponsoredStoryTest {

    private SponsoredStory sponsoredStory;
    private AdRequest adRequest;
    private Context context;

    @Before
    public void setup() {
        adRequest = new AdRequest("2-cIvYDwuRBFYSgRR3Xfvt9fsC_bnsXIb1YRn47w");
        context = Robolectric.getShadowApplication().getApplicationContext();
        sponsoredStory = new SponsoredStory(adRequest, context);
//        sponsoredStory.loadRequest();
    }
    @Test
    public void test(){
        Assert.assertEquals(true, true);
    }
}

