package com.lifejodi.login.manager;

import android.content.Context;

import com.android.volley.Request;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;
import com.lifejodi.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 12/19/2017.
 */

public class UserRegManager implements VolleyResponse {
    private VolleyRequest mVolleyRequest;
    private VolleyCallbackInterface mVolleyCallbackInterface;

    private static UserRegManager userRegManager = new UserRegManager();

    public static UserRegManager getInstance()
    {
        return userRegManager;
    }
    public UserRegManager()
    {
        mVolleyRequest = new VolleyRequest(this);
    }
    public void initialize(VolleyCallbackInterface VolleyCallbackInterface, Context mContext) {
        this.mVolleyCallbackInterface = VolleyCallbackInterface;
        mVolleyRequest.setContext(mContext);
    }

    public void registerUser(JSONObject dataObject)
    {
      //  mVolleyRequest.volleyJsonRequest(Request.Method.POST,Constants.URL_REGISTER_USER,Constants.TAG_REGISTER_USER,dataObject);
    }
    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {

    }

    @Override
    public void getResponse(String strResponse, String tag) {
       /*if(tag.equalsIgnoreCase(Constants.TAG_REGISTER_USER))
       {
           parseRegisterUserResponse(strResponse,tag);
       }*/
    }

    @Override
    public void error(String msg, String tag) {
        mVolleyCallbackInterface.errorCallBack(msg,tag);
    }

    public void parseRegisterUserResponse(String strResponse, String tag)
    {
        String status;
        try {
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.has(UserRegData.KEY_STATUSMESSAGE))
            {
                status = Constants.getStringValueOfJsonObject(jsonObject,UserRegData.KEY_STATUSMESSAGE,UserRegData.KEY_STATUSMESSAGE);
                UserRegData.getInstance().setRegStatus(status);
                mVolleyCallbackInterface.successCallBack("success",tag);
            }
        }catch (Exception e) {
            mVolleyCallbackInterface.errorCallBack("error",tag);

        }
    }
}
