package com.adsnative.android.sdk.request;

import com.adsnative.android.sdk.Constants;
import com.github.kevinsawicki.http.HttpRequest;

public class LogTimeRequest extends LogImpressionRequest {
    private long loggedTime;

    public LogTimeRequest(long loggedTime, String creativeId, String sessionId) {
        super(creativeId, sessionId);
        this.loggedTime = loggedTime;
    }

    private String getTime() {
        return "&time_spent=" + loggedTime;
    }

    private String getUrl() {
        return "http://" + Constants.URL_HOST + "/" + Constants.VERSION + "/log/time?" + getParams() + getTime();
    }

    public int postLoggedTime() {
        try {
            return HttpRequest.get(getUrl()).code();
        } catch (HttpRequest.HttpRequestException exception) {

        }
        return 0;
    }
}
