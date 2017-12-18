package com.lifejodi.network;

import org.json.JSONException;
import org.json.JSONObject;

public interface VolleyResponse {

    void getResponse(JSONObject jsonObject, String tag) throws JSONException;

    void getResponse(String strResponse, String tag);

    void error(String msg, String tag);
    
}
