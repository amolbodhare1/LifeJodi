package com.lifejodi.login.manager;

import android.content.Context;

import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 1/25/2018.
 */

public class OTPLoginManager implements VolleyResponse {
    private VolleyRequest mVolleyRequest;
    private VolleyCallbackInterface mVolleyCallbackInterface;

    private static OTPLoginManager otpLoginManager = new OTPLoginManager();

    public static OTPLoginManager getInstance()
    {
        return otpLoginManager;
    }

    public OTPLoginManager()
    {
        mVolleyRequest = new VolleyRequest(this);
    }
    public void initialize(VolleyCallbackInterface VolleyCallbackInterface, Context mContext) {
        this.mVolleyCallbackInterface = VolleyCallbackInterface;
        mVolleyRequest.setContext(mContext);
    }
    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {

    }

    @Override
    public void getResponse(String strResponse, String tag) {

    }

    @Override
    public void error(String msg, String tag) {

    }
}
