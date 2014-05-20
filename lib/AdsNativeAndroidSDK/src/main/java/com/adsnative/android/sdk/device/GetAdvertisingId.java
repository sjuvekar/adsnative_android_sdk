package com.adsnative.android.sdk.device;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.adsnative.android.sdk.Constants;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;

public class GetAdvertisingId extends AsyncTask<Void, Void, String> {

    private Context context;

    public GetAdvertisingId(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        String id = null;
        try {
            id = AdvertisingIdClient.getAdvertisingIdInfo(context).getId();
        } catch (IOException e) {
            Log.e(Constants.ERROR_TAG, e.getMessage());
            return null;
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e(Constants.ERROR_TAG, e.getMessage());
            return null;
        } catch (GooglePlayServicesRepairableException e) {
            Log.e(Constants.ERROR_TAG, e.getMessage());
            return null;
        }

        return id;
    }
}


