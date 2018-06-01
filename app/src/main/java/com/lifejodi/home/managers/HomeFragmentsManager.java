package com.lifejodi.home.managers;

import android.content.Context;

import com.android.volley.Request;
import com.lifejodi.home.data.HomeFragmentsData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JANHAVI on 2/8/2018.
 */

public class HomeFragmentsManager implements VolleyResponse {

    private VolleyRequest mVolleyRequest;
    private VolleyCallbackInterface mVolleyCallbackInterface;
    SharedPreference sharedPreference = SharedPreference.getSharedInstance();

    private static HomeFragmentsManager homeFragmentsManager = new HomeFragmentsManager();

    public static HomeFragmentsManager getInstance()
    {
        return homeFragmentsManager;
    }

    public HomeFragmentsManager()
    {
        mVolleyRequest = new VolleyRequest(this);
    }

    public void initialize(VolleyCallbackInterface VolleyCallbackInterface, Context mContext) {
        this.mVolleyCallbackInterface = VolleyCallbackInterface;
        mVolleyRequest.setContext(mContext);
        sharedPreference.initialize(mContext);

    }

    //HOME MATCHES
    public JSONObject getMatchesInputParams(String deviceId,String profileId)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(HomeFragmentsData.API,"matchesProfile");
            jsonObject.put(HomeFragmentsData.DEVICE,deviceId);
            jsonObject.put(HomeFragmentsData.VERSION,"1.0");

            JSONObject dataObject = new JSONObject();
            dataObject.put(HomeFragmentsData.USERID,profileId);

            jsonObject.put(HomeFragmentsData.DATA,dataObject);

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void getMatchesList(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.URL_HOME_MATCHES,Constants.TAG_HOME_MATCHES,jsonObject);
    }


    //NEW MATCHES
    public JSONObject getNewMatchesInputParams(String deviceId,String profileId)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(HomeFragmentsData.API,"newMatchesProfile");
            jsonObject.put(HomeFragmentsData.DEVICE,deviceId);
            jsonObject.put(HomeFragmentsData.VERSION,"1.0");

            JSONObject dataObject = new JSONObject();
            dataObject.put(HomeFragmentsData.PROFILEID,profileId);

            jsonObject.put(HomeFragmentsData.DATA,dataObject);

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void getNewMatchesList(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.URL_HOME_NEWMATCHES,Constants.TAG_HOME_NEWMATCHES,jsonObject);
    }

    //DAILY RECOMMENDATIONS
    public JSONObject getDailyRecommParams(String devId,String userId)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(HomeFragmentsData.API,"dailyRecommendations");
            jsonObject.put(HomeFragmentsData.DEVICE,devId);
            jsonObject.put(HomeFragmentsData.VERSION,"1.0");

            JSONObject dataObject = new JSONObject();
            dataObject.put(HomeFragmentsData.USERID,userId);

            jsonObject.put(HomeFragmentsData.DATA,dataObject);

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void getDailyRecommendations(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST,Constants.URL_DAILY_RECOMMENDATIONS,Constants.TAG_DAILY_RECOMMENDATIONS,jsonObject);
    }
    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {

    }

    @Override
    public void getResponse(String strResponse, String tag) {
        switch (tag)
        {
            case Constants.TAG_HOME_MATCHES:
                parseMatchesListResponse(strResponse,tag);
                break;
            case Constants.TAG_HOME_NEWMATCHES:
                parseMatchesListResponse(strResponse,tag);
                break;
            case Constants.TAG_DAILY_RECOMMENDATIONS:
                parseMatchesListResponse(strResponse,tag);
                break;
        }
    }

    @Override
    public void error(String msg, String tag) {
        mVolleyCallbackInterface.errorCallBack(msg,tag);
    }

    public void parseMatchesListResponse(String strResponse, String tag)
    {
        ArrayList<HashMap<String,String>> dataList = new ArrayList<>();
        String status = "";
        try
        {
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.has(HomeFragmentsData.STATUS))
            {
                status = Constants.getStringValueOfJsonObject(jsonObject,HomeFragmentsData.STATUS,HomeFragmentsData.STATUS);
                if(tag.equals(Constants.TAG_HOME_MATCHES))
                {
                    HomeFragmentsData.getInstance().setMatchesStatus(status);
                }else if(tag.equals(Constants.TAG_HOME_NEWMATCHES))
                {
                    HomeFragmentsData.getInstance().setNewMatchesStatus(status);
                }else if(tag.equals(Constants.TAG_DAILY_RECOMMENDATIONS))
                {
                    HomeFragmentsData.getInstance().setDailyRecommStatus(status);
                }

            }
            if(jsonObject.has(HomeFragmentsData.MESSAGE))
            {
                String message = Constants.getStringValueOfJsonObject(jsonObject,HomeFragmentsData.MESSAGE,HomeFragmentsData.MESSAGE);
                if(tag.equals(Constants.TAG_HOME_MATCHES))
                {
                    HomeFragmentsData.getInstance().setMatchesMessage(message);
                }else if(tag.equals(Constants.TAG_HOME_NEWMATCHES))
                {
                    HomeFragmentsData.getInstance().setNewMatchesMessage(message);
                }else if(tag.equals(Constants.TAG_DAILY_RECOMMENDATIONS))
                {
                    HomeFragmentsData.getInstance().setDailyRecommMessage(message);
                }
            }
            if(status.equals("0"))
            {

            }else {
                if (jsonObject.has(HomeFragmentsData.DATA)) {
                    JSONArray jsonArray = jsonObject.getJSONArray(HomeFragmentsData.DATA);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        HashMap<String, String> dataMap = new HashMap<>();
                        JSONObject dataObject = jsonArray.getJSONObject(i);

                        dataMap.put(HomeFragmentsData.ID, Constants.getStringValueOfJsonObject(dataObject, HomeFragmentsData.ID, HomeFragmentsData.ID));
                        dataMap.put(HomeFragmentsData.PROFILEID, Constants.getStringValueOfJsonObject(dataObject, HomeFragmentsData.PROFILEID, HomeFragmentsData.PROFILEID));
                        dataMap.put(HomeFragmentsData.FULLNAME, Constants.getStringValueOfJsonObject(dataObject, HomeFragmentsData.FULLNAME, HomeFragmentsData.FULLNAME));
                        dataMap.put(HomeFragmentsData.GENDER, Constants.getStringValueOfJsonObject(dataObject, HomeFragmentsData.GENDER, HomeFragmentsData.GENDER));
                        dataMap.put(HomeFragmentsData.DOB, Constants.getStringValueOfJsonObject(dataObject, HomeFragmentsData.DOB, HomeFragmentsData.DOB));
                        dataMap.put(HomeFragmentsData.MOTHERTONGUEID, Constants.getStringValueOfJsonObject(dataObject, HomeFragmentsData.MOTHERTONGUEID, HomeFragmentsData.MOTHERTONGUEID));
                        dataMap.put(HomeFragmentsData.RELIGIONID, Constants.getStringValueOfJsonObject(dataObject, HomeFragmentsData.RELIGIONID, HomeFragmentsData.RELIGIONID));
                        dataMap.put(HomeFragmentsData.AGE, Constants.getStringValueOfJsonObject(dataObject, HomeFragmentsData.AGE, HomeFragmentsData.AGE));
                        dataMap.put(HomeFragmentsData.MOTHERTONGUENAME, Constants.getStringValueOfJsonObject(dataObject, HomeFragmentsData.MOTHERTONGUENAME, HomeFragmentsData.MOTHERTONGUENAME));
                        dataMap.put(HomeFragmentsData.RELIGION, Constants.getStringValueOfJsonObject(dataObject, HomeFragmentsData.RELIGION, HomeFragmentsData.RELIGION));
                        dataMap.put(HomeFragmentsData.PHOTONAME, Constants.getStringValueOfJsonObject(dataObject, HomeFragmentsData.PHOTONAME, HomeFragmentsData.PHOTONAME));
                        dataMap.put(HomeFragmentsData.PROFILEPIC, Constants.getStringValueOfJsonObject(dataObject, HomeFragmentsData.PROFILEPIC, HomeFragmentsData.PROFILEPIC));
                        dataMap.put(HomeFragmentsData.PROFILEPIC, Constants.getStringValueOfJsonObject(dataObject, HomeFragmentsData.PROFILEPIC, HomeFragmentsData.PROFILEPIC));
                        dataMap.put(HomeFragmentsData.STATUS, Constants.getStringValueOfJsonObject(dataObject,HomeFragmentsData.STATUS,HomeFragmentsData.STATUS));
                        dataMap.put(HomeFragmentsData.MODE, Constants.getStringValueOfJsonObject(dataObject,HomeFragmentsData.MODE,HomeFragmentsData.MODE));

                        dataList.add(dataMap);

                    }

                    if(tag.equals(Constants.TAG_HOME_MATCHES))
                    {
                        HomeFragmentsData.getInstance().setMatchesList(dataList);
                    }else if(tag.equals(Constants.TAG_HOME_NEWMATCHES))
                    {
                        HomeFragmentsData.getInstance().setNewMatchesList(dataList);
                    }
                    else if(tag.equals(Constants.TAG_DAILY_RECOMMENDATIONS))
                    {
                        HomeFragmentsData.getInstance().setDailyRecommList(dataList);
                    }
                }
            }

            mVolleyCallbackInterface.successCallBack("success",tag);
        }catch (Exception e)
        {
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }
}
