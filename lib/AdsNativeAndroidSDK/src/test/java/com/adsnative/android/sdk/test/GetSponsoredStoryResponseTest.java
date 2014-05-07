package com.adsnative.android.sdk.test;


import android.content.Context;
import android.util.Log;

import com.adsnative.android.sdk.request.AdRequest;
import com.adsnative.android.sdk.request.GetSponsoredStoryRequest;
import com.adsnative.android.sdk.request.GetSponsoredStoryResponse;

import junit.framework.Assert;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class GetSponsoredStoryResponseTest {

    private GetSponsoredStoryResponse getSponsoredStoryResponse;
    private String json = "{" +
            "    \"ad\": {" +
            "        \"backgroundColor\": \"#fefff2\"," +
            "        \"brandImageUrl\": \"http://dev-www.adsnative.com/media/brand_images/1/82cc86ad-9070-4b90-9006-1fbca6697694.jpg\"," +
            "        \"embedUrl\": \"http://api.adsnative.com/v1/creative.html?crid=xyz&sid=sid123\"," +
            "        \"imageSrc\": \"http://files-www2.adsnative.com/media/1/xyz.jpg\"," +
            "        \"promotedBy\": \"Tesla\"," +
            "        \"promotedByTag\": \"Promoted by\"," +
            "        \"promotedByUrl\": \"http://api.adsnative.com/v1/ad.click?u=http%3A%2F%2Fxyz.com%2F&sid=sid123\"," +
            "        \"summary\": \"Tesla teased electric motorheads earlier this week by announcing an event that would show off its curious battery swapping system...\"," +
            "        \"target\": \"_parent\"," +
            "        \"title\": \"Tesla Shows Off A 90-Second Battery Swap System, Wants It At Supercharging Stations By Year's End\"," +
            "        \"trackingTags\": \"\"," +
            "        \"type\": \"story\"," +
            "        \"url\": \"http://api.adsnative.com/v1/ad.click?u=http%3A%2F%2Fxyz.com%2F&sid=sid123\"" +
            "    }," +
            "    \"cid\": \"cid123\"," +
            "    \"count\": 1," +
            "    \"crid\": \"crid123\"," +
            "    \"sid\": \"sid123\"," +
            "    \"status\": \"OK\"," +
            "    \"uuid\": \"uuid123\"," +
            "    \"zid\": \"ping\"" +
            "}";

    @Before
    public void setup() {
        try {
            getSponsoredStoryResponse = new GetSponsoredStoryResponse(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testParseJson(){
            Assert.assertNotNull(getSponsoredStoryResponse.parseJson());
    }
}
