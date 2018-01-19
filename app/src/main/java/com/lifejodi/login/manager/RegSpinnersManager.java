package com.lifejodi.login.manager;

import android.content.Context;

import com.android.volley.Request;
import com.lifejodi.login.data.RegSpinnersData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;
import com.lifejodi.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 12/18/2017.
 */

public class RegSpinnersManager implements VolleyResponse {
    private String TAG = LoginManager.class.getSimpleName();
    private VolleyRequest mVolleyRequest;
    private VolleyCallbackInterface mVolleyCallbackInterface;

    RegSpinnersData registerData = RegSpinnersData.getInstance();

    private static RegSpinnersManager registrationManager = new RegSpinnersManager();

    public static RegSpinnersManager getInstance()
    {
        return registrationManager;
    }

    public RegSpinnersManager()
    {
        mVolleyRequest = new VolleyRequest(this);
    }
    public void initialize(VolleyCallbackInterface VolleyCallbackInterface, Context mContext) {
        this.mVolleyCallbackInterface = VolleyCallbackInterface;
        mVolleyRequest.setContext(mContext);
    }

    //GET RELIGIONS
    public void getReligions()
    {
        mVolleyRequest.volleyStringRequest(Request.Method.GET, Constants.URL_GET_RELIGION,Constants.TAG_GET_RELIGION,null);
    }

    //GET CATSTS
    public void getCasts()
    {
        mVolleyRequest.volleyStringRequest(Request.Method.GET, Constants.URL_GET_CAST,Constants.TAG_GET_CAST,null);
    }

    //GET MARITAL STATUS
    public void getMaritalStatus()
    {
        mVolleyRequest.volleyStringRequest(Request.Method.GET, Constants.URL_GET_MARITALSTATUS,Constants.TAG_GET_MARITALSTATUS,null);
    }
    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {

    }

    @Override
    public void getResponse(String strResponse, String tag) {
        if(tag.equalsIgnoreCase(Constants.TAG_GET_RELIGION)) {
            parseReligionResponse(strResponse,tag);
        }else if(tag.equalsIgnoreCase(Constants.TAG_GET_CAST)) {
            parseCastResponse(strResponse,tag);
        }else if(tag.equalsIgnoreCase(Constants.TAG_GET_MARITALSTATUS)) {
            parseMaritalStatusResponse(strResponse,tag);
        }
    }

    @Override
    public void error(String msg, String tag) {
        mVolleyCallbackInterface.errorCallBack(msg,tag);
    }

    public void parseReligionResponse(String strResponse, String tag)
    {
        ArrayList<HashMap<String,String>> dataList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(strResponse);
            for(int i=0;i<jsonArray.length();i++)
            {
                HashMap<String,String> dataMap = new HashMap<>();
                JSONObject dataObject = jsonArray.getJSONObject(i);
                dataMap.put(RegSpinnersData.KEY_RELIGIONID,Constants.getStringValueOfJsonObject(dataObject, RegSpinnersData.KEY_RELIGIONID, RegSpinnersData.KEY_RELIGIONID));
                dataMap.put(RegSpinnersData.KEY_RELIGIONNAME,Constants.getStringValueOfJsonObject(dataObject, RegSpinnersData.KEY_RELIGIONNAME, RegSpinnersData.KEY_RELIGIONNAME));
                dataMap.put(RegSpinnersData.KEY_ISACTIVE,Constants.getStringValueOfJsonObject(dataObject, RegSpinnersData.KEY_ISACTIVE, RegSpinnersData.KEY_ISACTIVE));
                dataMap.put(RegSpinnersData.KEY_CREATEDON,Constants.getStringValueOfJsonObject(dataObject, RegSpinnersData.KEY_CREATEDON, RegSpinnersData.KEY_CREATEDON));
                dataMap.put(RegSpinnersData.KEY_CREATEDBY,Constants.getStringValueOfJsonObject(dataObject, RegSpinnersData.KEY_CREATEDBY, RegSpinnersData.KEY_CREATEDBY));
                dataList.add(dataMap);

            }

            registerData.setReligionsList(dataList);
            mVolleyCallbackInterface.successCallBack("success",tag);
        }catch (Exception e){
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }

    public void parseCastResponse(String strResponse, String tag)
    {
        ArrayList<HashMap<String,String>> dataList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(strResponse);
            for(int i=0;i<jsonArray.length();i++)
            {
                HashMap<String,String> dataMap = new HashMap<>();
                JSONObject dataObject = jsonArray.getJSONObject(i);
                dataMap.put(RegSpinnersData.KEY_CASTID,Constants.getStringValueOfJsonObject(dataObject, RegSpinnersData.KEY_CASTID, RegSpinnersData.KEY_CASTID));
                dataMap.put(RegSpinnersData.KEY_CASTNAME,Constants.getStringValueOfJsonObject(dataObject, RegSpinnersData.KEY_CASTNAME, RegSpinnersData.KEY_CASTNAME));
                dataMap.put(RegSpinnersData.KEY_ISACTIVE,Constants.getStringValueOfJsonObject(dataObject, RegSpinnersData.KEY_ISACTIVE, RegSpinnersData.KEY_ISACTIVE));
                dataMap.put(RegSpinnersData.KEY_CREATEDON,Constants.getStringValueOfJsonObject(dataObject, RegSpinnersData.KEY_CREATEDON, RegSpinnersData.KEY_CREATEDON));
                dataMap.put(RegSpinnersData.KEY_CREATEDBY,Constants.getStringValueOfJsonObject(dataObject, RegSpinnersData.KEY_CREATEDBY, RegSpinnersData.KEY_CREATEDBY));
                dataList.add(dataMap);

            }

            registerData.setCastsList(dataList);
            mVolleyCallbackInterface.successCallBack("success",tag);
        }catch (Exception e){
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }

    public void parseMaritalStatusResponse(String strResponse, String tag)
    {
        ArrayList<HashMap<String,String>> dataList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(strResponse);
            for(int i=0;i<jsonArray.length();i++)
            {
                HashMap<String,String> dataMap = new HashMap<>();
                JSONObject dataObject = jsonArray.getJSONObject(i);
                dataMap.put(RegSpinnersData.KEY_MARITALSTATUSID,Constants.getStringValueOfJsonObject(dataObject, RegSpinnersData.KEY_MARITALSTATUSID, RegSpinnersData.KEY_MARITALSTATUSID));
                dataMap.put(RegSpinnersData.KEY_MARITALSTATUSNAME,Constants.getStringValueOfJsonObject(dataObject, RegSpinnersData.KEY_MARITALSTATUSNAME, RegSpinnersData.KEY_MARITALSTATUSNAME));
                dataMap.put(RegSpinnersData.KEY_ISACTIVE,Constants.getStringValueOfJsonObject(dataObject, RegSpinnersData.KEY_ISACTIVE, RegSpinnersData.KEY_ISACTIVE));
                dataMap.put(RegSpinnersData.KEY_CREATEDON,Constants.getStringValueOfJsonObject(dataObject, RegSpinnersData.KEY_CREATEDON, RegSpinnersData.KEY_CREATEDON));
                dataMap.put(RegSpinnersData.KEY_CREATEDBY,Constants.getStringValueOfJsonObject(dataObject, RegSpinnersData.KEY_CREATEDBY, RegSpinnersData.KEY_CREATEDBY));
                dataList.add(dataMap);

            }

            registerData.setMaritalStatusList(dataList);
            mVolleyCallbackInterface.successCallBack("success",tag);
        }catch (Exception e){
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }
}
