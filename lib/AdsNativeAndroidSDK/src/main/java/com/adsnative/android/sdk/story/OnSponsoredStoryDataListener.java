package com.adsnative.android.sdk.story;

public interface OnSponsoredStoryDataListener {

    /**
     * Should be triggered when sponsored story data is completely fetched and correctly parsed
     *
     * @param sponsoredStoryData
     */
    public void onSponsoredStoryData(SponsoredStoryData sponsoredStoryData);

}
