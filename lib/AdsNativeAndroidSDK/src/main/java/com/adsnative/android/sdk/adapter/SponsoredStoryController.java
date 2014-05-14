package com.adsnative.android.sdk.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adsnative.android.sdk.WebViewActivity;
import com.adsnative.android.sdk.story.SponsoredStory;
import com.adsnative.android.sdk.story.SponsoredStoryData;

import java.util.WeakHashMap;

public class SponsoredStoryController {

    private final Context context;
    private final Handler impressionHandler;
    private final SponsoredStoryClickListener sponsoredStoryClickListener;
    private final WeakHashMap<View, SponsoredStory> sponsoredStoryWeakHashMap;
    private final String adUnitId;

    public SponsoredStoryController(Context context, /*PositionController positionController,*/ String adUnitId) {
        this.context = context;
        this.impressionHandler = new Handler();
        this.adUnitId = adUnitId;
        this.sponsoredStoryClickListener = new SponsoredStoryClickListener();
        sponsoredStoryWeakHashMap = new WeakHashMap<View, SponsoredStory>();
    }

    void clearPendingImpressions() {
        this.impressionHandler.removeMessages(0);
    }

    public View placeSponsoredStory(SponsoredStory sponsoredStory, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
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

    private View getStoryView(SponsoredStoryData sponsoredStoryData) {
        Log.d("Testest", "GetStoryView");
        float density = context.getResources().getDisplayMetrics().density;

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int m = (int) density * 10;
        imageParams.setMargins(m, m, m, m);
        imageView.setLayoutParams(imageParams);
        imageView.setImageBitmap(sponsoredStoryData.getThumbnailBitmap());
        linearLayout.addView(imageView);

        LinearLayout textLayout = new LinearLayout(context);
        textLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textLayoutParams.weight = 1;
        textLayoutParams.setMargins(m, m, m, m);
        textLayout.setLayoutParams(textLayoutParams);

        TextView title = new TextView(context);
        title.setSingleLine(true);
        title.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        title.setLayoutParams(titleParams);
        title.setText(sponsoredStoryData.getTitle());
        textLayout.addView(title);

        TextView byLine = new TextView(context);
        byLine.setSingleLine(true);
        byLine.setTextColor(Color.LTGRAY);
        LinearLayout.LayoutParams byLineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        byLine.setLayoutParams(byLineParams);
        byLine.setText("by " + sponsoredStoryData.getPromotedBy());
        textLayout.addView(byLine);

        linearLayout.addView(textLayout);
        linearLayout.setBackgroundColor(Color.parseColor("#fcf5da"));
        return linearLayout;

    }

    public void clearAd(View view) {
        if (view == null)
            return;

        SponsoredStory sponsoredStory = (SponsoredStory) this.sponsoredStoryWeakHashMap.get(view);
        if (sponsoredStory != null) {
            this.sponsoredStoryWeakHashMap.remove(view);
        }

        view.setOnClickListener(null);
        view.setOnLongClickListener(null);
    }

    private final class SponsoredStoryClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.d("TESTEST", "STORYCLICKED!!!!!!!!");
            SponsoredStory sponsoredStory = sponsoredStoryWeakHashMap.get(v);
            String url = sponsoredStory.getSponsoredStoryData().getUrl();
            //Open in Browser
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse(url));
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra("url", url);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
