package com.adsnative.android.sdk.sampleapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.adsnative.android.sdk.device.DeviceInfo;
import com.adsnative.android.sdk.sampleapp.adapter.MainAdapter;
import com.adsnative.android.sdk.sampleapp.item.MainListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MyActivity extends ListActivity {

    private static final String DEBUG = "TESTEST";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        DeviceInfo deviceInfo = new DeviceInfo(this);
        Log.d(DEBUG, "OsVersrion: " + deviceInfo.getOsVersion());
        Log.d(DEBUG, "UA: " + deviceInfo.getUserAgent());
        Log.d(DEBUG, "ConnType: " + deviceInfo.getConnectionType());
        Log.d(DEBUG, "DevModel: " + deviceInfo.getDeviceModel());
        Log.d(DEBUG, "Locale: " + Locale.getDefault().toString());
        Log.d(DEBUG, "ODIN1: " + deviceInfo.getODIN1());
        Log.d(DEBUG, "Vendor: " + deviceInfo.getIdentifierForVendor());
        Log.d(DEBUG, "TimeZone: " + TimeZone.getDefault().getID());

        List<MainListItem> listItems = new ArrayList<MainListItem>();
        listItems.add(new MainListItem("News", R.drawable.news));
        listItems.add(new MainListItem("YouTube", R.drawable.youtube));

        MainAdapter listAdapter = new MainAdapter(this, R.layout.main_list_item, listItems);

        setListAdapter(listAdapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position){
            case 0:
                startActivity(new Intent(this, NewsActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, YouTubeActivity.class));
        }
    }
}
