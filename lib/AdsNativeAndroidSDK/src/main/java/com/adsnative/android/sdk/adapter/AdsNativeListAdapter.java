package com.adsnative.android.sdk.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private List<Integer> sponsoredStoriesPositionsLoaded;
    private List<SponsoredStory> sponsoredStories;
    private String adUnitId;
    private PositionControllerList positionControllerList;
    private SponsoredStoryController sponsoredStoryController;
    private AdapterView.OnItemClickListener onItemClickListener;
    private boolean fetching;

    public AdsNativeListAdapter(Context context, T originalAdapter, int[] sponsoredStoriesPositions, String adUnitId) {
        this.context = context;
        this.originalAdapter = originalAdapter;
        this.sponsoredStoriesPositions = new ArrayList<Integer>();
        this.sponsoredStoriesPositionsLoaded = new ArrayList<Integer>();
        for (Integer i : sponsoredStoriesPositions) {
            this.sponsoredStoriesPositions.add(i);
        }
        Collections.sort(this.sponsoredStoriesPositions);
        this.sponsoredStories = new ArrayList<SponsoredStory>();
        this.positionControllerList = new PositionControllerList(this.originalAdapter.getCount());
        this.sponsoredStoryController = new SponsoredStoryController(context, this.adUnitId);
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
        fetching = false;
    }

    private void internalNotifyDataSetChanged() {
        this.positionControllerList.updateLists();
        super.notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        this.positionControllerList.updateOriginalSize(this.originalAdapter.getCount());
        internalNotifyDataSetChanged();
    }

    public void clearAds() {
        this.sponsoredStories.clear();
        this.sponsoredStoryController.clearAds();
        this.positionControllerList.clearSponsoredStories();
        internalNotifyDataSetChanged();
    }

    public void loadSponsoredStories() {
        this.sponsoredStories.clear();
        if (sponsoredStoriesPositions.size() > 0) {
            for (int i = 0; i < sponsoredStoriesPositions.size(); i++) {
                final int position = sponsoredStoriesPositions.get(i);
                final int j = i;
                final SponsoredStory sponsoredStory = new SponsoredStory(new AdRequest(adUnitId), context);
                sponsoredStory.loadRequest();
                sponsoredStory.setOnSponsoredStoryListener(new OnSponsoredStoryListener() {
                    @Override
                    public void onSponsoredStoryData(SponsoredStoryData sponsoredStoryData) {
                        addSponsoredStory(sponsoredStory, position, j);
                    }
                });
            }
        }
    }

    private void addSponsoredStory(SponsoredStory sponsoredStory, int position, int i) {
        this.sponsoredStories.add(sponsoredStory);
        if (i == sponsoredStoriesPositions.size() - 1) {
            this.positionControllerList.insertSponsoredStories(this.sponsoredStories, this.sponsoredStoriesPositions);
            this.internalNotifyDataSetChanged();
        }
    }

    private void addSponsoredStory(SponsoredStory sponsoredStory, int position){
        this.sponsoredStories.add(sponsoredStory);
        this.positionControllerList.insertSponsoredStory(sponsoredStory, position);
        this.internalNotifyDataSetChanged();
        fetching = false;
    }

    @Override
    public int getCount() {
        return this.positionControllerList.getAdjustedCount();
    }

    public int getViewTypeCount() {
        return this.originalAdapter.getViewTypeCount() + 1;
    }

    public int getItemViewType(int position) {
        return this.positionControllerList.isAd(position) ? 0 : this.originalAdapter.getItemViewType(this.positionControllerList.getOriginalPosition(position)) + 1;
    }

    @Override
    public Object getItem(int position) {
        SponsoredStory sponsoredStory = this.positionControllerList.getSponsoredStory(position);
        return sponsoredStory != null ? sponsoredStory : this.originalAdapter.getItem(this.positionControllerList.getOriginalPosition(position));
    }

    @Override
    public long getItemId(int position) {
        if (this.positionControllerList.isAd(position))
            return -1;
        else
            return this.positionControllerList.getOriginalPosition(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if (this.sponsoredStoriesPositions.contains(position) && !fetching){
//            if (!this.sponsoredStoriesPositionsLoaded.contains(position)){
//                fetching = true;
//                Log.d("TESTEST", "FetchingADD " + position);
//                this.sponsoredStoriesPositionsLoaded.add(position);
//                final int adPosition = position;
//                final SponsoredStory sponsoredStory = new SponsoredStory(new AdRequest(adUnitId), context);
//                sponsoredStory.loadRequest();
//                sponsoredStory.setOnSponsoredStoryListener(new OnSponsoredStoryListener() {
//                    @Override
//                    public void onSponsoredStoryData(SponsoredStoryData sponsoredStoryData) {
//                        addSponsoredStory(sponsoredStory, adPosition);
//                    }
//                });
//            }
//        }

        SponsoredStory sponsoredStory = this.positionControllerList.getSponsoredStory(position);
        if (sponsoredStory == null) {
            int originalPosition = this.positionControllerList.getOriginalPosition(position);
            return this.originalAdapter.getView(originalPosition, convertView, parent);
        }

//        if (this.positionControllerList.isAd(position)) {
//            Log.d("TESTEST", "add viewed " + position);
            return this.sponsoredStoryController.placeSponsoredStory(this.positionControllerList.getSponsoredStory(position), convertView, parent, position);
//        } else {
//            int originalPosition = this.positionControllerList.getOriginalPosition(position);
//            return this.originalAdapter.getView(originalPosition, convertView, parent);
//        }
    }
}


