package com.adsnative.android.sdk.adapter;

import android.util.Log;

import com.adsnative.android.sdk.story.SponsoredStory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PositionControllerList {
    private List<SponsoredStory> sponsoredStoriesList;
    private List<Integer> sponsoredStoriesPositionsList;
    private HashMap<Integer, Integer> originalPositionsList;
    private int originalSize;
    private int mergedListSize;

    public PositionControllerList(int originalSize) {
        this.originalSize = originalSize;
        this.mergedListSize = originalSize;
        this.sponsoredStoriesList = new ArrayList<SponsoredStory>();
        this.sponsoredStoriesPositionsList = new ArrayList<Integer>();
        this.originalPositionsList = new HashMap<Integer, Integer>();
        for (int i = 0; i < this.originalSize; i++) {
            this.originalPositionsList.put(i, i);
        }
    }

    public int getAdjustedCount() {
        return this.mergedListSize;
    }

    public int getOriginalPosition(int position) {
        Log.d("TESTEST", "getOriginalPosition: " + position);
        int pos = originalPositionsList.get(position);
        Log.d("TESTEST", "getOriginalPositionNEW: " + pos);
        return pos;
    }

    public boolean isAd(int position) {
        return sponsoredStoriesPositionsList.contains(position);
    }

    public SponsoredStory getSponsoredStory(int position) {
        if (sponsoredStoriesPositionsList.contains(position)) {
            int index = 0;
            for (Integer i : sponsoredStoriesPositionsList) {
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
    }

    public void insertSponsoredStories(List<SponsoredStory> newSponsoredStories, List<Integer> newPositions) {

        if (newSponsoredStories.size() != newPositions.size())
            return;

//        for (Integer i : newPositions) {
//            if (sponsoredStoriesPositionsList.contains(i)) {
//                newSponsoredStories.remove(newPositions.indexOf(i));
//                newPositions.remove(i);
//            }
//        }

        for (SponsoredStory s : newSponsoredStories) {
            sponsoredStoriesList.add(s);
        }

        for (Integer i : newPositions) {
            sponsoredStoriesPositionsList.add(i);
        }


    }

    public void updateLists() {
        HashMap<Integer, Integer> tmpHashMap = new HashMap<Integer, Integer>();
        int range = mergedListSize;
        for (Integer i : sponsoredStoriesPositionsList) {
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