package com.adsnative.android.sdk.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.adsnative.android.sdk.request.AdRequest;
import com.adsnative.android.sdk.story.OnSponsoredStoryListener;
import com.adsnative.android.sdk.story.SponsoredStory;
import com.adsnative.android.sdk.story.SponsoredStoryData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdsNativeListAdapter<T extends ListAdapter> extends BaseAdapter {

    private final Context context;
    private T originalAdapter;
    private List<Integer> sponsoredStoriesPositions;
    private List<SponsoredStory> sponsoredStories;
    private String adUnitId;
    private PositionController positionController;
    private SponsoredStoryController sponsoredStoryController;

    public AdsNativeListAdapter(Context context, T originalAdapter, int[] sponsoredStoriesPositions, String adUnitId) {
        this.context = context;
        this.originalAdapter = originalAdapter;
        this.sponsoredStoriesPositions = new ArrayList<Integer>();
        for (Integer i : sponsoredStoriesPositions) {
            this.sponsoredStoriesPositions.add(i);
        }
        Collections.sort(this.sponsoredStoriesPositions);
        this.sponsoredStories = new ArrayList<SponsoredStory>();
        this.positionController = new PositionController(this.originalAdapter.getCount());
        this.sponsoredStoryController = new SponsoredStoryController(context);
        this.adUnitId = adUnitId;

        originalAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                AdsNativeListAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void onInvalidated() {
                AdsNativeListAdapter.this.notifyDataSetInvalidated();
            }
        });
    }

    private void internalNotifyDataSetChanged() {
        this.positionController.updateLists();
        super.notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        this.positionController.updateOriginalSize(this.originalAdapter.getCount());
        internalNotifyDataSetChanged();
    }

    public void clearAds() {
        this.sponsoredStories.clear();
        this.sponsoredStoryController.clearAds();
        this.positionController.clearSponsoredStories();
        internalNotifyDataSetChanged();
    }

    public void loadSponsoredStories() {
        this.sponsoredStories.clear();
        if (sponsoredStoriesPositions.size() > 0) {
            for (int i = 0; i < sponsoredStoriesPositions.size(); i++) {
                final int j = i;
                final SponsoredStory sponsoredStory = new SponsoredStory(new AdRequest(adUnitId), context);
                sponsoredStory.loadRequest();
                sponsoredStory.setOnSponsoredStoryListener(new OnSponsoredStoryListener() {
                    @Override
                    public void onSponsoredStoryData(SponsoredStoryData sponsoredStoryData) {
                        addSponsoredStory(sponsoredStory, j);
                    }
                });
            }
        }
    }

    private void addSponsoredStory(SponsoredStory sponsoredStory, int i) {
        this.sponsoredStories.add(sponsoredStory);
        if (i == sponsoredStoriesPositions.size() - 1) {
            this.positionController.insertSponsoredStories(this.sponsoredStories, this.sponsoredStoriesPositions);
            this.internalNotifyDataSetChanged();
        }
    }

    private void addAsyncSponsoredStory(SponsoredStory sponsoredStory, int position) {
        this.sponsoredStories.add(sponsoredStory);
        this.positionController.insertSponsoredStory(sponsoredStory, position);
        this.internalNotifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.positionController.getAdjustedCount();
    }

    public int getViewTypeCount() {
        return this.originalAdapter.getViewTypeCount() + 1;
    }

    public int getItemViewType(int position) {
        return this.positionController.isAd(position) ? 0 : this.originalAdapter.getItemViewType(this.positionController.getOriginalPosition(position)) + 1;
    }

    @Override
    public Object getItem(int position) {
        SponsoredStory sponsoredStory = this.positionController.getSponsoredStory(position);
        return sponsoredStory != null ? sponsoredStory : this.originalAdapter.getItem(this.positionController.getOriginalPosition(position));
    }

    @Override
    public long getItemId(int position) {
        if (this.positionController.isAd(position))
            return -1;
        else
            return this.positionController.getOriginalPosition(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SponsoredStory sponsoredStory = this.positionController.getSponsoredStory(position);
        if (sponsoredStory == null) {
            int originalPosition = this.positionController.getOriginalPosition(position);
            return this.originalAdapter.getView(originalPosition, convertView, parent);
        }
        return this.sponsoredStoryController.placeSponsoredStory(this.positionController.getSponsoredStory(position), convertView, position);
    }
}


