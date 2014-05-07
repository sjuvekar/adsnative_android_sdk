package com.adsnative.android.sdk.device;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.webkit.WebView;

import com.adsnative.android.sdk.util.StringUtil;

import java.util.Locale;
import java.util.TimeZone;

public class DeviceInfo {

    private String osVersion;
    private String userAgent;
    private String deviceModel;
    private String locale;
    private String timeZone;
    private String identifierForVendor;
    private String connectionType;
    private String ODIN1;

    public DeviceInfo(Context context) {
        this.osVersion = "Android " + Build.VERSION.RELEASE;
        this.userAgent = new WebView(context).getSettings().getUserAgentString();

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            this.deviceModel = new StringUtil().capitalize(model);
        } else {
            this.deviceModel = new StringUtil().capitalize(manufacturer) + " " + model;
        }

        this.locale = Locale.getDefault().toString();
        this.timeZone = TimeZone.getDefault().getDisplayName();
        this.identifierForVendor = context.getApplicationContext().getPackageName();

        Connectivity connectivity = new Connectivity();
        if (!connectivity.isConnected(context))
            this.connectionType = "None";

        if (connectivity.isConnectedMobile(context))
            this.connectionType = "WWAN";

        if (connectivity.isConnectedWifi(context))
            this.connectionType = "Wifi";

        this.ODIN1 = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public String getLocale() {
        return locale;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getIdentifierForVendor() {
        return identifierForVendor;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public String getODIN1() {
        return ODIN1;
    }
}
