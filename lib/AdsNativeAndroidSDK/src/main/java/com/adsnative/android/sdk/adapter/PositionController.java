package com.adsnative.android.sdk.adapter;

import com.adsnative.android.sdk.story.SponsoredStory;

import java.util.Arrays;

public class PositionController {

    private SponsoredStory[] sponsoredStories;
    private int[] sponsoredStoriesPositions;
    private int[] originalPositions;

    public PositionController() {
        this.sponsoredStories = new SponsoredStory[0];
        this.sponsoredStoriesPositions = new int[0];
        this.originalPositions = new int[0];
}

    public int getAdjustedCount(int originalCount) {
        if (originalCount == 0) {
            return 0;
        }
        return getAdjustedPosition(originalCount - 1) + 1;
    }

    public int getAdjustedPosition(int originalPosition) {
        int index = Arrays.binarySearch(this.originalPositions, originalPosition);

        if (index < 0) {
            return originalPosition + (~index);
        }
        while ((index < this.originalPositions.length) && (this.originalPositions[index] == originalPosition)) {
            index++;
        }
        return originalPosition + index;

    }

    public int getOriginalCount(int adjustedCount) {
        if (adjustedCount == 0) {
            return 0;
        }

        return getOriginalPosition(adjustedCount - 1) + 1;
    }

    public int getOriginalPosition(int position) {
        int index = Arrays.binarySearch(this.sponsoredStoriesPositions, position);
//        Log.d("TESTEST", "sponsoredStoiresPositions index: " + index);
        if (index < 0) {
            return position - (~index);
        }

        return -1;
    }

    public boolean isAd(int position) {
        int index = Arrays.binarySearch(this.sponsoredStoriesPositions, position);
        return index >= 0;
    }

    public SponsoredStory getSponsoredStory(int position) {
        int index = Arrays.binarySearch(this.sponsoredStoriesPositions, position);
        if (index >= 0) {
            return this.sponsoredStories[index];
        }
        return null;
    }

    void clearSponsoredStories() {
        this.sponsoredStories = new SponsoredStory[0];
        this.sponsoredStoriesPositions = new int[0];
        this.originalPositions = new int[0];
    }

    public SponsoredStory[] getSponsoredStories() {
        return this.sponsoredStories;
    }

    public int[] getAdPositions() {
        return this.sponsoredStoriesPositions;
    }

    public int[] getOriginalPositions() {
        return this.originalPositions;
    }

    void insertSponsoredStories(SponsoredStory[] newSponsoredStories, int[] newPositions) {
//        Preconditions.checkState(newSponsoredStories.length == newPositions.length);

        if (newSponsoredStories.length != newPositions.length){
            return;
        }

        if (newSponsoredStories.length == 0) {
            return;
        }

        this.sponsoredStoriesPositions = new int[newPositions.length];
        this.sponsoredStories = new SponsoredStory[newSponsoredStories.length];

        for (int i = 0; i < newPositions.length; i++) {
            this.sponsoredStoriesPositions[i] = newPositions[i];
            this.sponsoredStories[i] = newSponsoredStories[i];
        }

        this.originalPositions = buildOriginalPositions(this.sponsoredStoriesPositions);
    }

    private int[] buildOriginalPositions(int[] adPositions) {
        int[] result = new int[adPositions.length];
        for (int i = 0; i < adPositions.length; i++) {
            adPositions[i] -= i;
        }
        return result;
    }

    public void addItem(int position) {
        int index = Arrays.binarySearch(this.sponsoredStoriesPositions, position);
        int start = index < 0 ? ~index : index;
        for (int i = start; i < this.sponsoredStoriesPositions.length; i++) {
            this.sponsoredStoriesPositions[i] += 1;
            this.originalPositions[i] -= 1;
        }
    }

    private void incrementArray(int[] target, int startIndex, int amount) {
        for (int i = startIndex; i < target.length; i++)
            target[i] += amount;
    }

    public void removeItem(int position) {
        int index = Arrays.binarySearch(this.sponsoredStoriesPositions, position);
        int start = index < 0 ? ~index : index;
        incrementArray(this.sponsoredStoriesPositions, start, -1);
        if (index >= 0) {
            this.sponsoredStoriesPositions = removeArrayItem(this.sponsoredStoriesPositions, index);
            this.sponsoredStories = removeArrayItem(this.sponsoredStories, index);
        }
        this.originalPositions = buildOriginalPositions(this.sponsoredStoriesPositions);
    }

    private SponsoredStory[] removeArrayItem(SponsoredStory[] target, int index) {
        SponsoredStory[] newArray = new SponsoredStory[target.length - 1];
        System.arraycopy(target, index + 1, newArray, index, target.length - index - 1);
        return newArray;
    }

    private int[] removeArrayItem(int[] target, int index) {
        int[] newArray = new int[target.length - 1];
        System.arraycopy(target, index + 1, newArray, index, target.length - index - 1);
        return newArray;
    }
}
