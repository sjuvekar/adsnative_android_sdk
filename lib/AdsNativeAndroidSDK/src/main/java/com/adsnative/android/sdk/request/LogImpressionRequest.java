package com.adsnative.android.sdk.request;

import com.adsnative.android.sdk.Constants;
import com.github.kevinsawicki.http.HttpRequest;

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

    public int postLoggedImpression() {
        try {
            return HttpRequest.get(getUrl()).code();
        } catch (HttpRequest.HttpRequestException exception) {

        }
        return 0;
    }
}
