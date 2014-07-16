package com.adsnative.android.sdk.story;

public interface OnSponsoredStoryDataListener {

    /**
     * Should be triggered when sponsored story data is completely fetched and correctly parsed
     *
     * @param sponsoredStoryData
     */
    public void onSponsoredStoryData(SponsoredStoryData sponsoredStoryData);

    /**
     * Should be triggered when response failed and failure message is added to the {@link com.adsnative.android.sdk.story.SponsoredStoryController}
     *
     * @param failureMessage
     */
    public void onFailure(FailureMessage failureMessage);

}
