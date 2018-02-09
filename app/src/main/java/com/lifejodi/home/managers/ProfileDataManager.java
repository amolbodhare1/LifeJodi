package com.lifejodi.home.managers;

import android.content.Context;

import com.android.volley.Request;
import com.lifejodi.home.data.ProfilesData;
import com.lifejodi.home.data.ShortlistData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by JANHAVI on 2/9/2018.
 */

public class ProfileDataManager implements VolleyResponse {

    private VolleyRequest mVolleyRequest;
    private VolleyCallbackInterface mVolleyCallbackInterface;
    SharedPreference sharedPreference = SharedPreference.getSharedInstance();

    private static ProfileDataManager profileDataManager = new ProfileDataManager();

    public static ProfileDataManager getInstance()
    {
        return profileDataManager;
    }

    public ProfileDataManager()
    {
        mVolleyRequest = new VolleyRequest(this);
    }

    public void initialize(VolleyCallbackInterface VolleyCallbackInterface, Context mContext) {
        this.mVolleyCallbackInterface = VolleyCallbackInterface;
        mVolleyRequest.setContext(mContext);
        sharedPreference.initialize(mContext);

    }
    //GET SHORTLISTED PROFILE DETAILS
    public JSONObject getShortListedDetailsParams(String devId,String profId)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ShortlistData.API,"profileShortDetails");
            jsonObject.put(ShortlistData.DEVICE,devId);
            jsonObject.put(ShortlistData.VERSION,"1.0");

            JSONObject dataObject = new JSONObject();
            dataObject.put(ProfilesData.PROFILEID,profId);

            jsonObject.put(ProfilesData.DATA,dataObject);

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void getShortListedProfDetails(JSONObject jsonObject)
    {
       mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.URL_SHORTLISTED_USERSDETAILS,Constants.TAG_SHORTLISTED_USERSDETAILS,jsonObject);
    }
    //GET  PROFILE DETAILS

    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {

    }

    @Override
    public void getResponse(String strResponse, String tag) {

        switch (tag)
        {
            case Constants.TAG_SHORTLISTED_USERSDETAILS:
                parseShortlistedDetails(strResponse,tag);
                break;
        }
    }

    @Override
    public void error(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_SHORTLISTED_USERSDETAILS:
                mVolleyCallbackInterface.errorCallBack(msg,tag);
                break;
        }
    }

    //PARSE SHORTLISTED DETAILS RESPONSE
    public void parseShortlistedDetails(String strResponse, String tag)
    {
        String status;
        HashMap<String,String> dataMap = new HashMap<>();
        try
        {
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.has(ProfilesData.STATUS))
            {
                status = Constants.getStringValueOfJsonObject(jsonObject,ProfilesData.STATUS,ProfilesData.STATUS);
                ProfilesData.getInstance().setShortListedDetailsStatus(status);
            }
            if(jsonObject.has(ProfilesData.DATA))
            {
                JSONObject dataObject = jsonObject.getJSONObject(ProfilesData.DATA);
                if(dataObject.has(ProfilesData.USERDATA))
                {
                    JSONObject userObject = dataObject.getJSONObject(ProfilesData.USERDATA);

                    dataMap.put(ProfilesData.ID,Constants.getStringValueOfJsonObject(userObject,ProfilesData.ID,ProfilesData.ID));
                    dataMap.put(ProfilesData.PROFILEID,Constants.getStringValueOfJsonObject(userObject,ProfilesData.PROFILEID,ProfilesData.PROFILEID));
                    dataMap.put(ProfilesData.FULLNAME,Constants.getStringValueOfJsonObject(userObject,ProfilesData.FULLNAME,ProfilesData.FULLNAME));
                    dataMap.put(ProfilesData.GENDER,Constants.getStringValueOfJsonObject(userObject,ProfilesData.GENDER,ProfilesData.GENDER));
                    dataMap.put(ProfilesData.DOB,Constants.getStringValueOfJsonObject(userObject,ProfilesData.DOB,ProfilesData.DOB));
                    dataMap.put(ProfilesData.MOTHERTOUNGUEID,Constants.getStringValueOfJsonObject(userObject,ProfilesData.MOTHERTOUNGUEID,ProfilesData.MOTHERTOUNGUEID));
                    dataMap.put(ProfilesData.RELIGIONID,Constants.getStringValueOfJsonObject(userObject,ProfilesData.RELIGIONID,ProfilesData.RELIGIONID));
                    dataMap.put(ProfilesData.AGE,Constants.getStringValueOfJsonObject(userObject,ProfilesData.AGE,ProfilesData.AGE));
                    dataMap.put(ProfilesData.MOTHERTONGUENAME,Constants.getStringValueOfJsonObject(userObject,ProfilesData.MOTHERTONGUENAME,ProfilesData.MOTHERTONGUENAME));
                    dataMap.put(ProfilesData.RELIGION,Constants.getStringValueOfJsonObject(userObject,ProfilesData.RELIGION,ProfilesData.RELIGION));
                    dataMap.put(ProfilesData.PHOTONAME,Constants.getStringValueOfJsonObject(userObject,ProfilesData.PHOTONAME,ProfilesData.PHOTONAME));
                    dataMap.put(ProfilesData.PROFILEPIC,Constants.getStringValueOfJsonObject(userObject,ProfilesData.PROFILEPIC,ProfilesData.PROFILEPIC));

                    ProfilesData.getInstance().setShortListedDetailsMap(dataMap);
                }
            }
            mVolleyCallbackInterface.successCallBack("success",tag);
        }catch (Exception e)
        {
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }


    //PARSE PROFILEDETAILS RESPONSE
}
