package com.adsnative.android.sdk.device;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.adsnative.android.sdk.Constants;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.io.IOException;

/**
 * Class fetches Advertising Id of the user.
 * It extends AsyncTask because Advertising Id can't be fetched on UI thread and
 * have to be run in the background.
 */

public class GetAdvertisingId extends AsyncTask<Void, Void, String> {

    private Context context;

    /**
     * Constructor of GetAdvertisingID class
     *
     * @param context
     */
    public GetAdvertisingId(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS) {
            try {
                return AdvertisingIdClient.getAdvertisingIdInfo(context).getId();
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
        } else {
            String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            if (deviceId != null) {
                return deviceId;
            } else {
                return android.os.Build.SERIAL;
            }
        }
    }
}


