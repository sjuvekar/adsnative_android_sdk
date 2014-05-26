package com.adsnative.android.sdk.request;

import android.util.Log;

import com.adsnative.android.sdk.Constants;
import com.adsnative.android.sdk.story.SponsoredStoryData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles and parse response from get operation requested on API
 */
public class GetSponsoredStoryResponse extends AdResponse {

    private int count;
    private SponsoredStoryData storyData;

    /**
     * Constructor
     *
     * @param rawData - body of the response
     * @throws JSONException
     */
    public GetSponsoredStoryResponse(String rawData) throws JSONException {
        super(new JSONObject(rawData));
    }

    /**
     * Parses JSON data from response
     *
     * @return {@link com.adsnative.android.sdk.story.SponsoredStoryData} object with parsed data from response,
     * if something went wrong with parsing returns {@code null}
     */
    public SponsoredStoryData parseJson() {
        if (status.equals("OK")) {
            try {
                this.count = data.getInt("count");

                storyData = new SponsoredStoryData();

                JSONObject ad = data.getJSONObject("ad");

                if (ad != null) {
                    storyData.setTrackingTags(checkHttp(extractUrlFromTrackingTags(ad.getString("trackingTags"))));
                    storyData.setBackgroundColor(ad.getString("backgroundColor"));
                    storyData.setUrl(checkHttp(ad.getString("url")));
                    storyData.setTitle(ad.getString("title"));
                    storyData.setSummary(ad.getString("summary"));
                    storyData.setThumbnailUrl(checkHttp(ad.getString("imageSrc")));
                    storyData.setEmbedUrl(checkHttp(ad.getString("embedUrl")));
                    storyData.setType(ad.getString("type"));
                    storyData.setPromotedBy(ad.getString("promotedBy"));
                    storyData.setPromotedByTag(ad.getString("promotedByTag"));
                    storyData.setPromotedByUrl(checkHttp(ad.getString("promotedByUrl")));
                }

                storyData.setCampaignId(data.getString("cid"));
                storyData.setCreativeId(data.getString("crid"));
                storyData.setSessionId(data.getString("sid"));
                storyData.setZoneId(data.getString("zid"));

                return storyData;
            } catch (JSONException e) {
                Log.e(Constants.ERROR_TAG, e.getMessage());
                return null;
            }
        }
        return null;
    }

    private String extractUrlFromTrackingTags(String trackingTags) {
        int length = trackingTags.length();
        return trackingTags.substring(10, length - 29);
    }

    private String checkHttp(String value) {
        if (value.startsWith("http:"))
            return value;
        else
            return "http:" + value;
    }
}