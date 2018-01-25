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

public class ForgotPasswordManager implements VolleyResponse {
    private VolleyRequest mVolleyRequest;
    private VolleyCallbackInterface mVolleyCallbackInterface;

    private static ForgotPasswordManager forgotPasswordManager = new ForgotPasswordManager();

    public static ForgotPasswordManager getInstance()
    {
        return forgotPasswordManager;
    }

    public ForgotPasswordManager()
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
