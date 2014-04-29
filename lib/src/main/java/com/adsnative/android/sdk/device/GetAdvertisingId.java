package com.adsnative.android.sdk.device;

import android.content.Context;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;

import roboguice.util.SafeAsyncTask;

public class GetAdvertisingId extends SafeAsyncTask<String> {

    private Context context;

    public GetAdvertisingId(Context context) {
        this.context = context;
    }

    @Override
    public String call() throws Exception {
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


