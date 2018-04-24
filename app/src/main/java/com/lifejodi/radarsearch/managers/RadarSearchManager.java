package com.lifejodi.radarsearch.managers;

import android.content.Context;

import com.android.volley.Request;
import com.lifejodi.login.data.RegSpinnersData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;
import com.lifejodi.radarsearch.data.RadarSearchData;
import com.lifejodi.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 4/3/2018.
 */

public class RadarSearchManager implements VolleyResponse {

    private VolleyRequest mVolleyRequest;
    private VolleyCallbackInterface mVolleyCallbackInterface;
    private static RadarSearchManager radarSearchManager = new RadarSearchManager();
    public static RadarSearchManager getInstance()
    {
        return radarSearchManager;
    }

    public RadarSearchManager()
    {
        mVolleyRequest = new VolleyRequest(this);
    }
    public void initialize(VolleyCallbackInterface VolleyCallbackInterface, Context mContext) {
        this.mVolleyCallbackInterface = VolleyCallbackInterface;
        mVolleyRequest.setContext(mContext);
    }


    //GET RADAR SEARCH LIST
    public JSONObject getRadarSearchInputs(String deviceId,String userId,String lat,String lng,String radius)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(RadarSearchData.API,"latLngSearch");
            jsonObject.put(RadarSearchData.VERSION,"1.0");
            jsonObject.put(RadarSearchData.DEVICE,deviceId);

            JSONObject dataObject = new JSONObject();
            dataObject.put(RadarSearchData.USERID,userId);
            dataObject.put(RadarSearchData.LAT,lat);
            dataObject.put(RadarSearchData.LNG,lng);
            dataObject.put(RadarSearchData.RADIUS,radius);

            jsonObject.put(RegSpinnersData.DATA,dataObject);

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void getRadarSearchList(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST,Constants.URL_RADAR_SEARCH,Constants.TAG_RADAR_SEARCH,jsonObject);
    }
    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {

    }

    @Override
    public void getResponse(String strResponse, String tag) {
        switch (tag)
        {
            case Constants.TAG_RADAR_SEARCH:
                parseRadarSearchListResponse(strResponse,tag);
                break;
        }
    }

    @Override
    public void error(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_RADAR_SEARCH:
                mVolleyCallbackInterface.errorCallBack(msg,tag);
                break;
        }
    }

    public void parseRadarSearchListResponse(String strResponse, String tag)
    {
        String status="";
        ArrayList<HashMap<String,String>> dataList = new ArrayList<>();
        try
        {
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.has(RadarSearchData.STATUS))
            {
                status = Constants.getStringValueOfJsonObject(jsonObject,RadarSearchData.STATUS,RadarSearchData.STATUS);
            }
            if(jsonObject.has(RadarSearchData.DATA))
            {
                JSONArray dataArray = jsonObject.getJSONArray(RadarSearchData.DATA);
                for(int i=0;i<dataArray.length();i++)
                {
                    JSONObject dataObject = dataArray.getJSONObject(i);
                    HashMap<String,String> dataMap = new HashMap<>();

                    dataMap.put(RadarSearchData.ID,Constants.getStringValueOfJsonObject(dataObject,RadarSearchData.ID,RadarSearchData.ID));
                    dataMap.put(RadarSearchData.PROFILEID,Constants.getStringValueOfJsonObject(dataObject,RadarSearchData.PROFILEID,RadarSearchData.PROFILEID));
                    dataMap.put(RadarSearchData.FULLNAME,Constants.getStringValueOfJsonObject(dataObject,RadarSearchData.FULLNAME,RadarSearchData.FULLNAME));
                    dataMap.put(RadarSearchData.GENDER,Constants.getStringValueOfJsonObject(dataObject,RadarSearchData.GENDER,RadarSearchData.GENDER));
                    dataMap.put(RadarSearchData.DOB,Constants.getStringValueOfJsonObject(dataObject,RadarSearchData.DOB,RadarSearchData.DOB));
                    dataMap.put(RadarSearchData.MOTHERTONGUEID,Constants.getStringValueOfJsonObject(dataObject,RadarSearchData.MOTHERTONGUEID,RadarSearchData.MOTHERTONGUEID));
                    dataMap.put(RadarSearchData.RELIGIONID,Constants.getStringValueOfJsonObject(dataObject,RadarSearchData.RELIGIONID,RadarSearchData.RELIGIONID));
                    dataMap.put(RadarSearchData.AGE,Constants.getStringValueOfJsonObject(dataObject,RadarSearchData.AGE,RadarSearchData.AGE));
                    dataMap.put(RadarSearchData.MOTHERTONGUENAME,Constants.getStringValueOfJsonObject(dataObject,RadarSearchData.MOTHERTONGUENAME,RadarSearchData.MOTHERTONGUENAME));
                    dataMap.put(RadarSearchData.RELIGION,Constants.getStringValueOfJsonObject(dataObject,RadarSearchData.RELIGION,RadarSearchData.RELIGION));
                    dataMap.put(RadarSearchData.PHOTONAME,Constants.getStringValueOfJsonObject(dataObject,RadarSearchData.PHOTONAME,RadarSearchData.PHOTONAME));
                    dataMap.put(RadarSearchData.DISTANCE,Constants.getStringValueOfJsonObject(dataObject,RadarSearchData.DISTANCE,RadarSearchData.DISTANCE));
                    dataMap.put(RadarSearchData.PROFILEPIC,Constants.getStringValueOfJsonObject(dataObject,RadarSearchData.PROFILEPIC,RadarSearchData.PROFILEPIC));
                    dataMap.put(RadarSearchData.DISTANCE,Constants.getStringValueOfJsonObject(dataObject,RadarSearchData.DISTANCE,RadarSearchData.DISTANCE));
                    dataMap.put(RadarSearchData.PROFILEPIC,Constants.getStringValueOfJsonObject(dataObject,RadarSearchData.PROFILEPIC,RadarSearchData.PROFILEPIC));
                    dataMap.put(RadarSearchData.LAT,Constants.getStringValueOfJsonObject(dataObject,RadarSearchData.LAT,RadarSearchData.LAT));
                    dataMap.put(RadarSearchData.LNG,Constants.getStringValueOfJsonObject(dataObject,RadarSearchData.LNG,RadarSearchData.LNG));


                    dataList.add(dataMap);

                }

            }

            RadarSearchData.getInstance().setRadarSearchList(dataList);
            RadarSearchData.getInstance().setRadarSearchStatus(status);
            mVolleyCallbackInterface.successCallBack("success",tag);
        }catch (Exception e)
        {
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }
}
