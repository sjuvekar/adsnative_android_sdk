package com.adsnative.android.sdk.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.adsnative.android.sdk.story.SponsoredStory;
import com.adsnative.android.sdk.story.SponsoredStoryData;

import java.util.WeakHashMap;

public class SponsoredStoryController {

    private final Context context;
    private final Handler impressionHandler;
//    private final PositionController positionController;
    private final SponsoredStoryClickListener sponsoredStoryClickListener;
    private final WeakHashMap<View, SponsoredStory> sponsoredStoryWeakHashMap;
//    private final SponsoredStoryClickListener sponsoredStoryClickListener;
    private final String adUnitId;



    public SponsoredStoryController(Context context, /*PositionController positionController,*/ String adUnitId){
        this.context = context;
        this.impressionHandler = new Handler();
//        this.positionController = positionController;
        this.adUnitId = adUnitId;
        this.sponsoredStoryClickListener = new SponsoredStoryClickListener();
        sponsoredStoryWeakHashMap = new WeakHashMap<View, SponsoredStory>();
    }

    void clearPendingImpressions(){
        this.impressionHandler.removeMessages(0);
    }

    public View placeSponsoredStory(SponsoredStory sponsoredStory, View convertView, ViewGroup parent){

        View view = convertView;
        if (view == null){
            view = getStoryView(sponsoredStory.getSponsoredStoryData());
        }

        SponsoredStory newData = sponsoredStory;
        SponsoredStory oldData = (SponsoredStory) this.sponsoredStoryWeakHashMap.get(view);
        if (oldData != newData) {
            sponsoredStoryWeakHashMap.put(view, newData);
//            startImpressionTimer()?
        }


        view.setOnClickListener(this.sponsoredStoryClickListener);
    return view;
    }

    private View getStoryView(SponsoredStoryData sponsoredStoryData){
        float density = context.getResources().getDisplayMetrics().density;
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));

        ImageView imageView = new ImageView(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int margin = (int) density * 10;
        lp.setMargins(margin, margin, margin, margin);
        imageView.setLayoutParams(lp);

        imageView.setImageBitmap(sponsoredStoryData.getThumbnailBitmap());
        relativeLayout.addView(imageView);
        return relativeLayout;
    }

    public void clearAd(View view){
        if (view == null)
            return;

        SponsoredStory sponsoredStory =  (SponsoredStory) this.sponsoredStoryWeakHashMap.get(view);
        if (sponsoredStory != null){
            this.sponsoredStoryWeakHashMap.remove(view);
        }

        view.setOnClickListener(null);
        view.setOnLongClickListener(null);
    }

    private final class SponsoredStoryClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.d("TESTEST", "STORYCLICKED!!!!!!!!");
        }
    }
}
