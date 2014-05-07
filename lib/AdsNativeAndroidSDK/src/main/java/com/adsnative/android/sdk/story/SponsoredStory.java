package com.adsnative.android.sdk.story;

import android.content.Context;
import android.os.AsyncTask;

import com.adsnative.android.sdk.device.DeviceInfo;
import com.adsnative.android.sdk.device.GetAdvertisingId;
import com.adsnative.android.sdk.request.AdRequest;
import com.adsnative.android.sdk.request.GetSponsoredStoryRequest;
import com.adsnative.android.sdk.request.GetSponsoredStoryResponse;

import org.json.JSONException;


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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return sponsoredStoryData;
            }
            return null;

        }

        @Override
        protected void onPostExecute(SponsoredStoryData sponsoredStoryData) {
            super.onPostExecute(sponsoredStoryData);
            setSponsoredStoryData(sponsoredStoryData);
            onSponsoredStoryListener.onSponsoredStoryData(sponsoredStoryData);
        }
    }
}

