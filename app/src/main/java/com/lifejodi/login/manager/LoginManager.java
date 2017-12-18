package com.lifejodi.login.manager;

import android.content.Context;

import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;
import com.lifejodi.utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ajay on 04-12-2017.
 */

public class LoginManager implements VolleyResponse {

    private String TAG = LoginManager.class.getSimpleName();
    private VolleyRequest mVolleyRequest;
    private VolleyCallbackInterface mVolleyCallbackInterface;

    private static LoginManager ourInstance = new LoginManager();
    public static LoginManager getInstance(){return ourInstance;};

    SharedPreference sharedPreference = SharedPreference.getSharedInstance();

    private LoginManager(){
        mVolleyRequest = new VolleyRequest(this);
    }
    public void initialize(VolleyCallbackInterface VolleyCallbackInterface, Context mContext) {
        this.mVolleyCallbackInterface = VolleyCallbackInterface;
        mVolleyRequest.setContext(mContext);

        sharedPreference.initialize(mContext);

    }


    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {

    }

    @Override
    public void getResponse(String strResponse, String tag) {

        switch (tag){

        }

    }

    @Override
    public void error(String msg, String tag) {

    }


}
