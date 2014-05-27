package com.adsnative.android.sdk.story;

public interface OnSponsoredStoryListener {

    /**
     * Should be triggered when sponsored story data is added to the {@link com.adsnative.android.sdk.story.SponsoredStoryController}
     *
     * @param sponsoredStory
     */
    public void onSponsoredStory(SponsoredStory sponsoredStory);
}
