package com.adsnative.android.sdk.test;

import com.adsnative.android.sdk.request.LogImpressionRequest;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class LogImpressionRequestTest {
    private LogImpressionRequest logImpressionRequest;

    @Before
    public void setup(){
        String crid = "CAEY18VQ";
        String sid = "dubexbfqin1365vthwxtwq72ouruz5f4";
        logImpressionRequest = new LogImpressionRequest(crid, sid);
    }

    @Test
    public void testLogImpression(){
        Assert.assertEquals(200, logImpressionRequest.logImpression());
    }
}
