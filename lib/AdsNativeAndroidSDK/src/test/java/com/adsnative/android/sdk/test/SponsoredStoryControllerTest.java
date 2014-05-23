package com.adsnative.android.sdk.test;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.adsnative.android.sdk.request.AdRequest;
import com.adsnative.android.sdk.story.SponsoredStory;
import com.adsnative.android.sdk.story.SponsoredStoryController;

import junit.framework.Assert;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;
import java.net.URL;

@RunWith(RobolectricTestRunner.class)
public class SponsoredStoryControllerTest {

    private SponsoredStoryController sponsoredStoryController;
    private SponsoredStory sponsoredStory;

    @Before
    public void setup() throws JSONException, IOException {
        Context context = Robolectric.getShadowApplication().getApplicationContext();
        sponsoredStoryController = new SponsoredStoryController(context);
        sponsoredStory = new SponsoredStory(new AdRequest("test"), context);
        sponsoredStory.getSponsoredStoryData().setBackgroundColor("#fefff2");
        sponsoredStory.getSponsoredStoryData().setEmbedUrl("http://api.adsnative.com/v1/creative.html?crid=N0UTP1W1&sid=m5rl8o4hrngiqq1jcecry9m5o5eyw771");
        sponsoredStory.getSponsoredStoryData().setThumbnailUrl("http://static.adsnative.com/media/693/4626e650-043c-4ccf-aa9c-2b983934f910.jpg");
        sponsoredStory.getSponsoredStoryData().setPromotedBy("BuzzFeed");
        sponsoredStory.getSponsoredStoryData().setPromotedByTag("Promoted by");
        sponsoredStory.getSponsoredStoryData().setPromotedByUrl("http://api.adsnative.com/v1/ad.click?u=http%3A%2F%2Fbuzzfeed.com%2F&sid=m5rl8o4hrngiqq1jcecry9m5o5eyw771");
        sponsoredStory.getSponsoredStoryData().setSummary("Tesla teased electric motorheads earlier this week by announcing an event that would show off its curious battery swapping system...");
        sponsoredStory.getSponsoredStoryData().setTitle("Skateboarders take over a Chicago office space - Red Bull DEMO");
        sponsoredStory.getSponsoredStoryData().setTrackingTags("http://api.adsnative.com/v1/log/impression.gif?crid=N0UTP1W1&sid=m5rl8o4hrngiqq1jcecry9m5o5eyw771");
        sponsoredStory.getSponsoredStoryData().setType("story");
        sponsoredStory.getSponsoredStoryData().setUrl("http://api.adsnative.com/v1/ad.click?u=http%3A%2F%2Fwww.buzzfeed.com%2Femilyhennen%2Fjimmy-kimmel-asks-moms-to-admit-one-shocking-thing-their-kid&sid=m5rl8o4hrngiqq1jcecry9m5o5eyw771");
        sponsoredStory.getSponsoredStoryData().setCampaignId("SG1TBILZ");
        sponsoredStory.getSponsoredStoryData().setCreativeId("N0UTP1W1");
        sponsoredStory.getSponsoredStoryData().setSessionId("m5rl8o4hrngiqq1jcecry9m5o5eyw771");
        sponsoredStory.getSponsoredStoryData().setThumbnailBitmap(BitmapFactory.decodeStream(new URL(sponsoredStory.getSponsoredStoryData().getThumbnailUrl()).openConnection().getInputStream()));
    }

    @Test
    public void testGetSponsoredStoryView(){
        Assert.assertNotNull(sponsoredStoryController.getSponsoredStoryView(sponsoredStory, 10));
    }
}
