package com.adsnative.android.sdk.story;

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
import com.adsnative.android.sdk.request.AdRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Controller and renderer of the SponsoredStories attached to ListView
 */
public class SponsoredStoryController {

    private final Context context;
    private final SponsoredStoryClickListener sponsoredStoryClickListener;
    private final WeakHashMap<View, SponsoredStory> sponsoredStoryWeakHashMap;
    private List<String> impressionsList;
    private List<SponsoredStory> sponsoredStories;
    private List<Integer> cachedSponsoredStoriesPositionsList;
    private OnSponsoredStoryListener onSponsoredStoryListener;

    /**
     * Constructor
     *
     * @param context
     */
    public SponsoredStoryController(Context context) {
        this(context, null);
    }

    /**
     * Constructor with cached sponsored stories positions list from listview
     *
     * @param context
     * @param cachedList
     */
    public SponsoredStoryController(Context context, List<Integer> cachedList) {
        this.context = context;
        this.sponsoredStoryClickListener = new SponsoredStoryClickListener();
        this.sponsoredStoryWeakHashMap = new WeakHashMap(4, 0.75f);
        this.impressionsList = new ArrayList<String>();
        this.sponsoredStories = new ArrayList<SponsoredStory>();
        this.cachedSponsoredStoriesPositionsList = cachedList;
    }

    /**
     * Fetches {@link com.adsnative.android.sdk.story.SponsoredStory} without any keywords
     *
     * @param adUnitId AdsNative user ID
     * @return
     */
    public SponsoredStory fetchSponsoredStory(String adUnitId) {
        return this.fetchSponsoredStory(adUnitId, null);
    }

    /**
     * Fetches {@link com.adsnative.android.sdk.story.SponsoredStory} with specified keywords
     *
     * @param adUnitId          AdsNative user ID
     * @param adRequestKeywords list of requested keywords
     * @return
     */
    public SponsoredStory fetchSponsoredStory(String adUnitId, List<String> adRequestKeywords) {
        final SponsoredStory sponsoredStory = new SponsoredStory(new AdRequest(adUnitId, adRequestKeywords), context);
        sponsoredStory.loadRequest();
        sponsoredStory.setOnSponsoredStoryDataListener(new OnSponsoredStoryDataListener() {
            @Override
            public void onSponsoredStoryData(SponsoredStoryData sponsoredStoryData) {
                addSponsoredStory(sponsoredStory);
            }
        });
        return sponsoredStory;
    }

    /**
     * Adds specified SponsoredStory to the property list of SponsoredStories
     *
     * @param sponsoredStory
     */
    private void addSponsoredStory(SponsoredStory sponsoredStory) {
        this.sponsoredStories.add(sponsoredStory);
        onSponsoredStoryListener.onSponsoredStory(sponsoredStory);
    }

    /**
     * Sets OnSponsoredStoryListener to know when the story is completely added to the {@link com.adsnative.android.sdk.story.SponsoredStoryController}
     *
     * @param onSponsoredStoryListener
     */
    public void setOnSponsoredStoryListener(OnSponsoredStoryListener onSponsoredStoryListener) {
        this.onSponsoredStoryListener = onSponsoredStoryListener;
    }

    /**
     * * Check {@link com.adsnative.android.sdk.story.SponsoredStoryController}.getSponsoredStoryView(SponsoredStory sponsoredStory, View convertView, int sponsoredStoryId)
     *
     * @param sponsoredStory
     * @return
     */
    public View getSponsoredStoryView(SponsoredStory sponsoredStory) {
        return this.getSponsoredStoryView(sponsoredStory, null);
    }

    /**
     * Check {@link com.adsnative.android.sdk.story.SponsoredStoryController}.getSponsoredStoryView(SponsoredStory sponsoredStory, View convertView, ViewGroup parent, int sponsoredStoryId)
     *
     * @param sponsoredStory
     * @param convertView    if is {@code null} proper layout will be rendered for View
     * @return fully functional SponsoredStory View
     */
    public View getSponsoredStoryView(SponsoredStory sponsoredStory, View convertView) {
        return this.getSponsoredStoryView(sponsoredStory, convertView, null);
    }

    /**
     * Uses getStoryView(SponsoredStoryData) to render proper layout of SponsoredStory.
     * Maps SponsoredStories to its proper Views. Sets SponsoredStory click listener
     * and log impression by displaying 1x1 drop pixel.
     *
     * @param sponsoredStory
     * @param convertView    if {@code null} default layout will be rendered for View
     * @param parent         if {@code null} generated view is not going to be attached to any parent, if convertView is already attached to any parent it's going to be removed and attached to the specified one
     * @return
     */
    public View getSponsoredStoryView(SponsoredStory sponsoredStory, View convertView, ViewGroup parent) {
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

        String sid = sponsoredStory.getSponsoredStoryData().getSessionId();
        if (!impressionsList.contains(sid)) {
            if (!newData.getSponsoredStoryData().getTrackingTags().isEmpty())
                ((RelativeLayout) view).addView(getImpressionPixel(sponsoredStory.getSponsoredStoryData()));
            impressionsList.add(sid);
        }

        if (parent != null) {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            parent.addView(view);
        }

        return view;
    }

    /**
     * Provides 1x1 drop pixel WebView
     *
     * @param sponsoredStoryData
     * @return created 1x1 size WebView
     */
    private View getImpressionPixel(SponsoredStoryData sponsoredStoryData) {
        WebView webView = new WebView(context);
        webView.setLayoutParams(new ViewGroup.LayoutParams(1, 1));
        webView.setWebViewClient(new StoryWebViewClient(context));
        webView.loadUrl(sponsoredStoryData.getTrackingTags());
        return webView;
    }

    /**
     * Renders proper View for specified SponsoredStoryData
     *
     * @param sponsoredStoryData
     * @return rendered View
     */
    private View getStoryView(SponsoredStoryData sponsoredStoryData) {

        float density = context.getResources().getDisplayMetrics().density;

        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
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
//        linearLayout.setBackgroundColor(Color.parseColor("#00a0c1"));
        linearLayout.setBackgroundColor(Color.parseColor(sponsoredStoryData.getBackgroundColor()));
        relativeLayout.addView(linearLayout);

        return relativeLayout;
    }

    /**
     * Clears all SponsoredStory mappings
     */
    public void clearSponsoredStories() {
        this.sponsoredStories.clear();
        this.sponsoredStoryWeakHashMap.clear();
        this.impressionsList.clear();
    }

    /**
     * Click listener for SponsoredStory Views
     */
    private final class SponsoredStoryClickListener implements View.OnClickListener {

        /**
         * Open proper WebView and starts {@link com.adsnative.android.sdk.WebViewActivity}
         * for SponsoredStory attached to  after the click action was performed
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            SponsoredStory sponsoredStory = sponsoredStoryWeakHashMap.get(v);
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra("crid", sponsoredStory.getSponsoredStoryData().getCreativeId());
            intent.putExtra("sid", sponsoredStory.getSponsoredStoryData().getSessionId());
            intent.putExtra("url", sponsoredStory.getSponsoredStoryData().getUrl());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
