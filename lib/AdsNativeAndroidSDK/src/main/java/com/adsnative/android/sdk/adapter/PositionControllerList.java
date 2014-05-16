package com.adsnative.android.sdk.adapter;

import com.adsnative.android.sdk.story.SponsoredStory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PositionControllerList {
    private List<SponsoredStory> sponsoredStoriesList;
    private List<Integer> sponsoredStoriesPositionsList;
    private List<Integer> sponsoredStoriesPositionsListAdjusted;
    private HashMap<Integer, Integer> originalPositionsList;
    private int originalSize;
    private int mergedListSize;

    public PositionControllerList(int originalSize) {
        this.originalSize = originalSize;
        this.mergedListSize = originalSize;
        this.sponsoredStoriesList = new ArrayList<SponsoredStory>();
        this.sponsoredStoriesPositionsList = new ArrayList<Integer>();
        this.sponsoredStoriesPositionsListAdjusted = new ArrayList<Integer>();
        this.originalPositionsList = new HashMap<Integer, Integer>();
        for (int i = 0; i < this.originalSize; i++) {
            this.originalPositionsList.put(i, i);
        }
    }

    public void setOriginalSize(int originalSize) {
        this.originalSize = originalSize;
    }

    public void updateOriginalSize(int newSize) {
        setOriginalSize(newSize);
        createDefaultOriginals();
        adjustSponsoredStoriesPositionsList();
    }

    public int getAdjustedCount() {
        return this.mergedListSize;
    }

    public int getOriginalPosition(int position) {
        return this.originalPositionsList.get(position);
    }

    public boolean isAd(int position) {
        return sponsoredStoriesPositionsListAdjusted.contains(position);
    }

    public SponsoredStory getSponsoredStory(int position) {
        if (sponsoredStoriesPositionsListAdjusted.contains(position)) {
            int index = 0;
            for (Integer i : sponsoredStoriesPositionsListAdjusted) {
                if (i == position)
                    return sponsoredStoriesList.get(index);
                index++;
            }
        }
        return null;
    }

    public void clearSponsoredStories() {
        sponsoredStoriesList.clear();
        sponsoredStoriesPositionsList.clear();
        sponsoredStoriesPositionsListAdjusted.clear();
        createDefaultOriginals();
        mergedListSize = originalSize;
    }

    private void createDefaultOriginals() {
        this.originalPositionsList.clear();
        for (int i = 0; i < this.originalSize; i++) {
            this.originalPositionsList.put(i, i);
        }
    }

    public void insertSponsoredStories(List<SponsoredStory> newSponsoredStories, List<Integer> newPositions) {

        if (newSponsoredStories.size() != newPositions.size())
            return;

        clearSponsoredStories();
        sponsoredStoriesList = newSponsoredStories;
        sponsoredStoriesPositionsList = newPositions;
        adjustSponsoredStoriesPositionsList();
    }

    public void insertSponsoredStory(SponsoredStory sponsoredStory, int position){
        sponsoredStoriesList.add(sponsoredStory);
        sponsoredStoriesPositionsList.add(position);
        adjustSponsoredStoriesPositionsList(position);
    }

    private void adjustSponsoredStoriesPositionsList(int position){
        mergedListSize++;
        if (position > mergedListSize - 1)
            position = mergedListSize - 1;
        sponsoredStoriesPositionsListAdjusted.add(position);
    }

    private void adjustSponsoredStoriesPositionsList() {
        this.sponsoredStoriesPositionsListAdjusted.clear();
        mergedListSize = originalSize;
        for (Integer i : sponsoredStoriesPositionsList) {
            mergedListSize++;
            if (i > mergedListSize - 1)
                i = mergedListSize - 1;
            sponsoredStoriesPositionsListAdjusted.add(i);
        }
    }

    public void updateLists() {
        HashMap<Integer, Integer> tmpHashMap = new HashMap<Integer, Integer>();
        int range = originalSize;
        for (Integer i : sponsoredStoriesPositionsListAdjusted) {

            for (Integer j = i; j < range; j++) {
                tmpHashMap.put(j, originalPositionsList.get(j));
            }

            for (Integer j = i; j < range; j++) {
                originalPositionsList.put(j + 1, tmpHashMap.get(j));
            }

            originalPositionsList.remove(i);
            range++;
        }
    }
}