package com.adsnative.android.sdk.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adsnative.android.sdk.WebViewActivity;
import com.adsnative.android.sdk.story.SponsoredStory;
import com.adsnative.android.sdk.story.SponsoredStoryData;
import com.adsnative.android.sdk.story.StoryWebViewClient;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class SponsoredStoryController {

    private final Context context;
    private final SponsoredStoryClickListener sponsoredStoryClickListener;
    private final WeakHashMap<View, SponsoredStory> sponsoredStoryWeakHashMap;
    private List<Integer> impressionsList;

    public SponsoredStoryController(Context context) {
        this.context = context;
        this.sponsoredStoryClickListener = new SponsoredStoryClickListener();
        this.sponsoredStoryWeakHashMap = new WeakHashMap(4, 0.75f);
        this.impressionsList = new ArrayList<Integer>();
    }

    public View placeSponsoredStory(SponsoredStory sponsoredStory, View convertView, int position) {

        View view = convertView;
        if (view == null) {
            view = getStoryView(sponsoredStory.getSponsoredStoryData());
        }

        SponsoredStory newData = sponsoredStory;
        SponsoredStory oldData = (SponsoredStory) this.sponsoredStoryWeakHashMap.get(view);
        if (oldData != newData) {
            sponsoredStoryWeakHashMap.put(view, newData);
        }

        view.setOnClickListener(this.sponsoredStoryClickListener);

        if (!impressionsList.contains(position)) {
            if (!newData.getSponsoredStoryData().getTrackingTags().isEmpty())
                ((RelativeLayout) view).addView(getWebView(sponsoredStory.getSponsoredStoryData()));
            impressionsList.add(position);
        }

        return view;
    }

    private View getWebView(SponsoredStoryData sponsoredStoryData) {
        WebView webView = new WebView(context);
        webView.setLayoutParams(new ViewGroup.LayoutParams(1, 1));
        webView.setWebViewClient(new StoryWebViewClient());
        webView.loadUrl(sponsoredStoryData.getTrackingTags());
        return webView;
    }

    private View getStoryView(SponsoredStoryData sponsoredStoryData) {

        float density = context.getResources().getDisplayMetrics().density;

        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int m = (int) density * 15;
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
        linearLayout.setBackgroundColor(Color.parseColor(sponsoredStoryData.getBackgroundColor()));
        relativeLayout.addView(linearLayout);

        return relativeLayout;

    }

    public void clearAds() {
        this.sponsoredStoryWeakHashMap.clear();
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
            SponsoredStory sponsoredStory = sponsoredStoryWeakHashMap.get(v);
            //Open in Browser
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse(url));
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra("crid", sponsoredStory.getSponsoredStoryData().getCreativeId());
            intent.putExtra("sid", sponsoredStory.getSponsoredStoryData().getSessionId());
            intent.putExtra("url", sponsoredStory.getSponsoredStoryData().getUrl());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
