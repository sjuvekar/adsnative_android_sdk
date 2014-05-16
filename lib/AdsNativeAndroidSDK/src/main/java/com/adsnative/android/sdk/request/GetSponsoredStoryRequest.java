package com.adsnative.android.sdk.request;

import com.adsnative.android.sdk.Constants;
import com.adsnative.android.sdk.device.DeviceInfo;
import com.github.kevinsawicki.http.HttpRequest;

public class GetSponsoredStoryRequest {

    private AdRequest adRequest;
    private String uuid;
    private DeviceInfo deviceInfo;

    public GetSponsoredStoryRequest(AdRequest adRequest, String uuid, DeviceInfo deviceInfo) {
        this.adRequest = adRequest;
        this.uuid = uuid;
        this.deviceInfo = deviceInfo;
    }

    private String getParams() {
        String zid = adRequest.getAdUnitID();
        String ua = deviceInfo.getUserAgent();
        String al = deviceInfo.getLocale();
        String tz = deviceInfo.getTimeZone();
        String bd = deviceInfo.getConnectionType();

        String params = "zid=" + zid + "&app=1" + "&ua=" + ua + "&al=" + al + "&tz=" + tz +
                "&uuid=" + uuid + "&bd=" + bd + "&prefetch=1";

        params = params.replaceAll(" ", "%20");

        return params;
    }

    private String getUrl() {
        return "http://" + Constants.URL_HOST + "/" + Constants.VERSION + "/ad.json?" + getParams();
    }

    public String get() {
        try {
            String body = HttpRequest.get(getUrl()).body();
//            Log.d("TESTEST", body);
            return body;
        } catch (HttpRequest.HttpRequestException exception) {
            return null;
        }
    }
}
