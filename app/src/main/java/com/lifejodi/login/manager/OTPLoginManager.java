package com.lifejodi.login.manager;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.lifejodi.login.data.LoginData;
import com.lifejodi.login.data.OTPLoginData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;
import com.lifejodi.utils.Constants;

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

    //GET OTP
    public JSONObject getOtpInputParams(String mobileNum,String devId)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(OTPLoginData.API,"sendOtp");
            jsonObject.put(OTPLoginData.DEVICE,devId);
            jsonObject.put(OTPLoginData.VERSION,"1.0");

            JSONObject dataObject = new JSONObject();
            dataObject.put(OTPLoginData.MOBILENUM,mobileNum);

            jsonObject.put(OTPLoginData.DATA,dataObject);
            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void getOTP(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST,Constants.URL_OTP,Constants.TAG_OTP,jsonObject);
    }

    //LOGIN WITH OTP
    public JSONObject getOtpLoginInputParams(String otp,String devId)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(OTPLoginData.API,"loginWithOtp");
            jsonObject.put(OTPLoginData.DEVICE,devId);
            jsonObject.put(OTPLoginData.VERSION,"1.0");

            JSONObject dataObject = new JSONObject();
            dataObject.put(OTPLoginData.OTP,otp);

            jsonObject.put(OTPLoginData.DATA,dataObject);
            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void loginWithOTP(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST,Constants.URL_LOGIN_WITH_OTP,Constants.TAG_LOGIN_WITH_OTP,jsonObject);
    }
    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {

    }

    @Override
    public void getResponse(String strResponse, String tag) {
        switch (tag)
        {
            case Constants.TAG_OTP:
                parsegetOTPResponse(strResponse,tag);
                break;
            case Constants.TAG_LOGIN_WITH_OTP:

                break;
        }
    }

    @Override
    public void error(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_OTP:
                mVolleyCallbackInterface.errorCallBack(msg,tag);
                break;
            case Constants.TAG_LOGIN_WITH_OTP:
                mVolleyCallbackInterface.errorCallBack(msg,tag);
                break;
        }
    }

    public void parsegetOTPResponse(String strResponse, String tag)
    {
        String status = "",message="";
        try
        {
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.has(OTPLoginData.STATUS))
            {
                status = Constants.getStringValueOfJsonObject(jsonObject,OTPLoginData.STATUS,OTPLoginData.STATUS);
            }
            if(jsonObject.has(OTPLoginData.MESSAGE))
            {
                message = Constants.getStringValueOfJsonObject(jsonObject,OTPLoginData.MESSAGE,OTPLoginData.MESSAGE);
            }
            if(jsonObject.has(OTPLoginData.DATA))
            {

            }

            OTPLoginData.getInstance().setOptStatus(status);
            OTPLoginData.getInstance().setOtpMessage(message);
            mVolleyCallbackInterface.successCallBack("success",tag);
        }catch (Exception e)
        {
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }



}
