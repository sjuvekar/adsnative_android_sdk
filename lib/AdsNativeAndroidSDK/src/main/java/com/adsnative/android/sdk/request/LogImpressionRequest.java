package com.adsnative.android.sdk.request;

import android.util.Log;

import com.adsnative.android.sdk.Constants;
import com.github.kevinsawicki.http.HttpRequest;

/*
I don't know if this class is necessary while we're using 1x1 drop pixel to log impressions...
by Mieszko
 */

public class LogImpressionRequest {
    protected String creativeId;
    protected String sessionId;

    public LogImpressionRequest(String creativeId, String sessionId) {
        this.creativeId = creativeId;
        this.sessionId = sessionId;
    }

    protected String getParams() {
        return "crid=" + this.creativeId + "&sid=" + this.sessionId;
    }

    private String getUrl() {
        return "http://" + Constants.URL_HOST + "/" + Constants.VERSION + "/log/impression?" + getParams();
    }

    public int logImpression() {
        try {
            return HttpRequest.get(getUrl()).code();
        } catch (HttpRequest.HttpRequestException exception) {
            Log.e(Constants.ERROR_TAG, exception.getMessage());
        }
        return 0;
    }
}
