package com.adsnative.android.sdk.device;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.webkit.WebView;

import com.adsnative.android.sdk.util.StringUtil;

import java.util.Locale;
import java.util.TimeZone;

public class DeviceInfo {

    private Context context;

    public DeviceInfo(Context context) {
        this.context = context;
    }

    public String getOsVersion() {
        return "Android " + Build.VERSION.RELEASE;
    }

    public String getUserAgent() {
        return new WebView(context).getSettings().getUserAgentString();
    }

    public String getDeviceModel() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return new StringUtil().capitalize(model);
        } else {
            return new StringUtil().capitalize(manufacturer) + " " + model;
        }
    }

    public String getLocale() {
        return Locale.getDefault().getDisplayLanguage();
    }

    public String getTimeZone() {
        return TimeZone.getDefault().getDisplayName();
    }

    public String getIdentifierForVendor() {
        return context.getApplicationContext().getPackageName();
    }

    public String getConnectionType() {
        Connectivity connectivity = new Connectivity();
        if (!connectivity.isConnected(context))
            return "None";

        if (connectivity.isConnectedMobile(context))
            return "WWAN";

        if (connectivity.isConnectedWifi(context))
            return "Wifi";

        return null;
    }

    public String getODIN1() {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

}
