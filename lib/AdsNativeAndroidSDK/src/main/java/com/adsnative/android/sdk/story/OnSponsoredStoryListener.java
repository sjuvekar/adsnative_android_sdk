package com.adsnative.android.sdk.story;

/**
 * Listens if sponsored story if completely fetched from server
 */
public interface OnSponsoredStoryListener {
    /**
     * Should be triggered when sponsored story data is completely fetched and correctly parsed
     *
     * @param sponsoredStoryData
     */
    public void onSponsoredStoryData(SponsoredStoryData sponsoredStoryData);
}
