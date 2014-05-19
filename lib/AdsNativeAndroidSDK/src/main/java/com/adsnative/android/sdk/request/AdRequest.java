package com.adsnative.android.sdk.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdRequest {

    private static final String SDK_VERSION = "1.0";
    private String adUnitID;
    private List<String> keywords;
    private Map<String, String> parameters;

    public AdRequest(String adUnitID) {
        this.adUnitID = adUnitID;
        keywords = new ArrayList<String>();
        parameters = new HashMap<String, String>();
    }

    public static String getSdkVersion() {
        return SDK_VERSION;
    }

    public String getAdUnitID() {
        return adUnitID;
    }

    public boolean putKeyword(String keyword) {
        return keywords.add(keyword);
    }

    public boolean removeKeyword(String keyword) {
        return keywords.remove(keyword);
    }

    public String putParameter(String key, String value) {
        return parameters.put(key, value);
    }

    public String removeParameter(String key) {
        return parameters.remove(key);
    }
}
