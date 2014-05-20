package com.adsnative.android.sdk.request;

import org.json.JSONException;
import org.json.JSONObject;

public class AdResponse {

    protected String status;
    protected String error;
    protected JSONObject data;

    public AdResponse(JSONObject data) {
        this.data = data;
        try {
            this.status = data.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getStatus(){
        return this.status;
    }
}
