package com.adsnative.android.sdk.device;

import android.content.Context;
import android.os.AsyncTask;

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
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        }

        return id;
    }
}


