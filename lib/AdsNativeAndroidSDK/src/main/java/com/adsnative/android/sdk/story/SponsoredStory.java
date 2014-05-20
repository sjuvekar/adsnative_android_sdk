package com.adsnative.android.sdk.story;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.adsnative.android.sdk.Constants;
import com.adsnative.android.sdk.device.DeviceInfo;
import com.adsnative.android.sdk.device.GetAdvertisingId;
import com.adsnative.android.sdk.request.AdRequest;
import com.adsnative.android.sdk.request.GetSponsoredStoryRequest;
import com.adsnative.android.sdk.request.GetSponsoredStoryResponse;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class SponsoredStory {

    private String uuid;
    private SponsoredStoryData sponsoredStoryData;
    private AdRequest adRequest;
    private Context context;
    private DeviceInfo deviceInfo;
    private OnSponsoredStoryListener onSponsoredStoryListener;

    public SponsoredStory(AdRequest adRequest, Context context) {
        this.adRequest = adRequest;
        this.context = context;
        this.deviceInfo = new DeviceInfo(context);
    }

    public void loadRequest() {
        new GetAdvertisingId(context) {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    setUuid(s);
                    new GetSponsoredStoryTask(adRequest, deviceInfo).execute(s);
                } else {
                    setUuid("");
                }
            }
        }.execute();
    }

    private void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private void setSponsoredStoryData(SponsoredStoryData sponsoredStoryData) {
        this.sponsoredStoryData = sponsoredStoryData;
    }

    public SponsoredStoryData getSponsoredStoryData() {
        return sponsoredStoryData;
    }

    public void setOnSponsoredStoryListener(OnSponsoredStoryListener onSponsoredStoryListener) {
        this.onSponsoredStoryListener = onSponsoredStoryListener;
    }

    private class GetSponsoredStoryTask extends AsyncTask<String, Void, SponsoredStoryData> {

        private AdRequest adRequest;
        private DeviceInfo deviceInfo;

        public GetSponsoredStoryTask(AdRequest adRequest, DeviceInfo deviceInfo) {
            this.adRequest = adRequest;
            this.deviceInfo = deviceInfo;
        }

        @Override
        protected SponsoredStoryData doInBackground(String... params) {
            GetSponsoredStoryRequest getSponsoredStoryRequest =
                    new GetSponsoredStoryRequest(adRequest, uuid, deviceInfo);
            String json = getSponsoredStoryRequest.get();
            if (json != null) {
                SponsoredStoryData sponsoredStoryData = null;
                try {
                    sponsoredStoryData = new GetSponsoredStoryResponse(json).parseJson();
                    String url;
                    if (sponsoredStoryData.getThumbnailUrl().startsWith("http:"))
                        url = sponsoredStoryData.getThumbnailUrl();
                    else
                        url = "http:" + sponsoredStoryData.getThumbnailUrl();
                    sponsoredStoryData.setThumbnailBitmap(BitmapFactory.decodeStream(new URL(url).openConnection().getInputStream()));
                } catch (JSONException e) {
                    Log.e(Constants.ERROR_TAG, e.getMessage());
                    return null;
                } catch (MalformedURLException e) {
                    Log.e(Constants.ERROR_TAG, e.getMessage());
                    return null;
                } catch (IOException e) {
                    Log.e(Constants.ERROR_TAG, e.getMessage());
                    return null;
                }
                return sponsoredStoryData;
            }
            return null;

        }

        @Override
        protected void onPostExecute(SponsoredStoryData sponsoredStoryData) {
            super.onPostExecute(sponsoredStoryData);
            if (sponsoredStoryData != null) {
                setSponsoredStoryData(sponsoredStoryData);
                onSponsoredStoryListener.onSponsoredStoryData(sponsoredStoryData);
            }
        }
    }
}

