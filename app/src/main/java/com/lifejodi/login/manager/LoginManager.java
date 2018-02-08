package com.lifejodi.login.manager;

import android.content.Context;

import com.android.volley.Request;
import com.facebook.login.Login;
import com.lifejodi.login.data.LoginData;
import com.lifejodi.login.data.RegSpinnersData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Ajay on 04-12-2017.
 */

public class LoginManager implements VolleyResponse {

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

    public JSONObject getLoginInputs(String email,String password)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(LoginData.EMAIL,email);
            jsonObject.put(LoginData.PASSWORD,password);

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getLoginDataObject(String deviceId,JSONObject object)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(LoginData.API,"login");
            jsonObject.put(LoginData.DEVICE,deviceId);
            jsonObject.put(LoginData.VERSION,"1.0");
            jsonObject.put(LoginData.DATA,object);
            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public void loginUser(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.URL_LOGIN,Constants.TAG_LOGIN,jsonObject);
    }

    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {

    }

    @Override
    public void getResponse(String strResponse, String tag) {

        switch (tag){
            case Constants.TAG_LOGIN:
                parseLoginResponse(strResponse,tag);
                break;
        }

    }

    @Override
    public void error(String msg, String tag) {
        mVolleyCallbackInterface.errorCallBack(msg,tag);
    }

    public void parseLoginResponse(String strResponse, String tag)
    {
        String status = "";
        HashMap<String,String> dataMap = new HashMap<>();
        try{
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.has(LoginData.MESSAGE))
            {
                status = Constants.getStringValueOfJsonObject(jsonObject,LoginData.MESSAGE,LoginData.MESSAGE);
            }
            if(jsonObject.has(LoginData.DATA))
            {
                JSONArray jsonArray = jsonObject.getJSONArray(LoginData.DATA);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject dataObject = jsonArray.getJSONObject(i);
                    dataMap.put(LoginData.ID,Constants.getStringValueOfJsonObject(dataObject,LoginData.ID,LoginData.ID));
                    dataMap.put(LoginData.EMAIL,Constants.getStringValueOfJsonObject(dataObject,LoginData.EMAIL,LoginData.EMAIL));
                    dataMap.put(LoginData.CONTACTNAME,Constants.getStringValueOfJsonObject(dataObject,LoginData.CONTACTNAME,LoginData.CONTACTNAME));
                    dataMap.put(LoginData.PROFILEFOR,Constants.getStringValueOfJsonObject(dataObject,LoginData.PROFILEFOR,LoginData.PROFILEFOR));
                    dataMap.put(LoginData.GENDER,Constants.getStringValueOfJsonObject(dataObject,LoginData.GENDER,LoginData.GENDER));
                    dataMap.put(LoginData.DOB,Constants.getStringValueOfJsonObject(dataObject,LoginData.DOB,LoginData.DOB));
                    dataMap.put(LoginData.BIRTHTIME,Constants.getStringValueOfJsonObject(dataObject,LoginData.BIRTHTIME,LoginData.BIRTHTIME));
                    dataMap.put(LoginData.BIRTHPLACE,Constants.getStringValueOfJsonObject(dataObject,LoginData.BIRTHPLACE,LoginData.BIRTHPLACE));
                    dataMap.put(LoginData.PROFILEID,Constants.getStringValueOfJsonObject(dataObject,LoginData.PROFILEID,LoginData.PROFILEID));
                    dataMap.put(LoginData.FULLNAME,Constants.getStringValueOfJsonObject(dataObject,LoginData.FULLNAME,LoginData.FULLNAME));

                    sharedPreference.putSharedPrefData(Constants.LOGINNAME,Constants.getStringValueOfJsonObject(dataObject,LoginData.FULLNAME,LoginData.FULLNAME));
                    sharedPreference.putSharedPrefData(Constants.LOGINEMAIL,Constants.getStringValueOfJsonObject(dataObject,LoginData.EMAIL,LoginData.EMAIL));
                }

                LoginData.getInstance().setLoginStatus(status);
                LoginData.getInstance().setLoginInfoMap(dataMap);
            }
            mVolleyCallbackInterface.successCallBack("success",tag);
        }catch (Exception e)
        {
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }

}
