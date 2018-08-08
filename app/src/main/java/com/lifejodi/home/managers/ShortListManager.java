package com.lifejodi.home.managers;

import android.content.Context;

import com.android.volley.Request;
import com.lifejodi.home.data.HomeFragmentsData;
import com.lifejodi.home.data.ShortlistData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JANHAVI on 2/8/2018.
 */

public class ShortListManager implements VolleyResponse {

    private VolleyRequest mVolleyRequest;
    private VolleyCallbackInterface mVolleyCallbackInterface;
    SharedPref sharedPreference = SharedPref.getSharedInstance();

    private static ShortListManager shortListManager = new ShortListManager();

    public static ShortListManager getInstance()
    {
        return shortListManager;
    }

    public ShortListManager()
    {
        mVolleyRequest = new VolleyRequest(this);
    }
    public void initialize(VolleyCallbackInterface VolleyCallbackInterface, Context mContext) {
        this.mVolleyCallbackInterface = VolleyCallbackInterface;
        mVolleyRequest.setContext(mContext);
        sharedPreference.initialize(mContext);

    }


    //SHORTLIST USER
    public JSONObject getShortlistUserParams(String deviceId,String profId,String userId)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ShortlistData.API,"shortlistUser");
            jsonObject.put(ShortlistData.DEVICE,deviceId);
            jsonObject.put(ShortlistData.VERSION,"1.0");

            JSONObject dataObject = new JSONObject();
            dataObject.put(ShortlistData.PROFILEID,profId);
            dataObject.put(ShortlistData.USERID,userId);

            jsonObject.put(ShortlistData.DATA,dataObject);

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void shortListUser(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.URL_SHORTLIST_USER,Constants.TAG_SHORTLIST_USER,jsonObject);
    }

    //GET SHORTLISTED USERS
    public JSONObject getShortlistedUsersListParams(String deviceId,String profId)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ShortlistData.API,"shortlisted");
            jsonObject.put(ShortlistData.DEVICE,deviceId);
            jsonObject.put(ShortlistData.VERSION,"1.0");

            JSONObject dataObject = new JSONObject();
            dataObject.put(ShortlistData.PROFILEID,profId);

            jsonObject.put(ShortlistData.DATA,dataObject);

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public void getShortListedUsersList(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST,Constants.URL_SHORTLISTED_USERSLIST,Constants.TAG_SHORTLISTED_USERSLIST,jsonObject);
    }
    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {

    }

    @Override
    public void getResponse(String strResponse, String tag) {
        switch (tag)
        {
            case Constants.TAG_SHORTLIST_USER:
                parseShortListUserResponse(strResponse,tag);
                break;
            case Constants.TAG_SHORTLISTED_USERSLIST:
                parseShortListedUsersResponse(strResponse,tag);
                break;
        }
    }

    @Override
    public void error(String msg, String tag) {
        mVolleyCallbackInterface.errorCallBack(msg,tag);
    }

    public void parseShortListUserResponse(String strResponse, String tag)
    {
        try
        {
          JSONObject jsonObject = new JSONObject(strResponse);
          if(jsonObject.has(ShortlistData.STATUS))
          {
              String status = Constants.getStringValueOfJsonObject(jsonObject,ShortlistData.STATUS,ShortlistData.STATUS);
              ShortlistData.getInstance().setShortlistingStatus(status);
          }
          if(jsonObject.has(ShortlistData.MESSAGE))
          {
              String message = Constants.getStringValueOfJsonObject(jsonObject,ShortlistData.MESSAGE,ShortlistData.MESSAGE);
              ShortlistData.getInstance().setShortlistingMessage(message);
          }
          mVolleyCallbackInterface.successCallBack("success",tag);
        }catch (Exception e)
        {
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }


    //SHORTLISTED USERS LIST
    public void parseShortListedUsersResponse(String strResponse, String tag)
    {
        String status = null,message;
        ArrayList<HashMap<String,String>> dataList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.has(ShortlistData.STATUS))
            {
                status = Constants.getStringValueOfJsonObject(jsonObject,ShortlistData.STATUS,ShortlistData.STATUS);
                ShortlistData.getInstance().setShortListedUsersStatus(status);
            }
            if(jsonObject.has(ShortlistData.MESSAGE))
            {
                message = Constants.getStringValueOfJsonObject(jsonObject,ShortlistData.MESSAGE,ShortlistData.MESSAGE);
                ShortlistData.getInstance().setShortListedUsersMessage(message);
            }
            if(status.equals("1"))
            {
                if(jsonObject.has(ShortlistData.DATA))
                {
                    JSONArray jsonArray = jsonObject.getJSONArray(ShortlistData.DATA);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                       JSONObject dataObject = jsonArray.getJSONObject(i);
                       HashMap<String,String> dataMap = new HashMap<>();

                        dataMap.put(ShortlistData.ID,Constants.getStringValueOfJsonObject(dataObject,ShortlistData.ID,ShortlistData.ID));
                        dataMap.put(ShortlistData.PROFILEID,Constants.getStringValueOfJsonObject(dataObject,ShortlistData.PROFILEID,ShortlistData.PROFILEID));
                        dataMap.put(ShortlistData.FULLNAME,Constants.getStringValueOfJsonObject(dataObject,ShortlistData.FULLNAME,ShortlistData.FULLNAME));
                        dataMap.put(ShortlistData.GENDER,Constants.getStringValueOfJsonObject(dataObject,ShortlistData.GENDER,ShortlistData.GENDER));
                        dataMap.put(ShortlistData.DOB,Constants.getStringValueOfJsonObject(dataObject,ShortlistData.DOB,ShortlistData.DOB));
                        dataMap.put(ShortlistData.MOTHERTOUNGUEID,Constants.getStringValueOfJsonObject(dataObject,ShortlistData.MOTHERTOUNGUEID,ShortlistData.MOTHERTOUNGUEID));
                        dataMap.put(ShortlistData.RELIGIONID,Constants.getStringValueOfJsonObject(dataObject,ShortlistData.RELIGIONID,ShortlistData.RELIGIONID));
                        dataMap.put(ShortlistData.AGE,Constants.getStringValueOfJsonObject(dataObject,ShortlistData.AGE,ShortlistData.AGE));
                        dataMap.put(ShortlistData.MOTHERTOUNGUENAME,Constants.getStringValueOfJsonObject(dataObject,ShortlistData.MOTHERTOUNGUENAME,ShortlistData.MOTHERTOUNGUENAME));
                        dataMap.put(ShortlistData.RELIGION,Constants.getStringValueOfJsonObject(dataObject,ShortlistData.RELIGION,ShortlistData.RELIGION));
                        dataMap.put(ShortlistData.PHOTONAME,Constants.getStringValueOfJsonObject(dataObject,ShortlistData.PHOTONAME,ShortlistData.PHOTONAME));
                        dataMap.put(ShortlistData.PROFILEPIC,Constants.getStringValueOfJsonObject(dataObject,ShortlistData.PROFILEPIC,ShortlistData.PROFILEPIC));
                        dataMap.put(ShortlistData.STATUS, Constants.getStringValueOfJsonObject(dataObject,ShortlistData.STATUS,ShortlistData.STATUS));
                        dataMap.put(ShortlistData.MODE, Constants.getStringValueOfJsonObject(dataObject,ShortlistData.MODE,ShortlistData.MODE));
                        dataMap.put(ShortlistData.FEATURED_PROFILE, Constants.getStringValueOfJsonObject(dataObject,ShortlistData.FEATURED_PROFILE,ShortlistData.FEATURED_PROFILE));

                        dataList.add(dataMap);
                    }

                    ShortlistData.getInstance().setShortListedUsersList(dataList);
                }

                mVolleyCallbackInterface.successCallBack("success",tag);
            }
        }catch (Exception e)
        {
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }
}
