package com.lifejodi.login.manager;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.lifejodi.login.data.RegSpinnersData;
import com.lifejodi.login.data.UploadProfilePicData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Administrator on 2/23/2018.
 */

public class UploadProfilePicManager implements VolleyResponse{

    private VolleyRequest mVolleyRequest;
    private VolleyCallbackInterface mVolleyCallbackInterface;

    private static UploadProfilePicManager uploadProfilePicManager = new UploadProfilePicManager();

    public static UploadProfilePicManager getInstance()
    {
        return uploadProfilePicManager;
    }

    public UploadProfilePicManager()
    {
        mVolleyRequest = new VolleyRequest(this);
    }

    SharedPref sharedPref = SharedPref.getSharedInstance();
    public void initialize(VolleyCallbackInterface VolleyCallbackInterface, Context mContext) {
        this.mVolleyCallbackInterface = VolleyCallbackInterface;
        mVolleyRequest.setContext(mContext);
        sharedPref.initialize(mContext);
    }

    //UPLOAD PROFILE PIC
    public JSONObject getUploadProfPicParams(String albumId,String image)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(RegSpinnersData.API,"uploadPhoto");
            jsonObject.put(RegSpinnersData.VERSION,"1.0");
            jsonObject.put("device",sharedPref.getSharedPrefData(Constants.DEVICE_ID));

            JSONObject dataObject = new JSONObject();
            dataObject.put(UploadProfilePicData.USERID,sharedPref.getSharedPrefData(Constants.UID));
            dataObject.put(UploadProfilePicData.ALBUMID,albumId);
            dataObject.put(UploadProfilePicData.IMAGE,image);

            jsonObject.put(RegSpinnersData.DATA,dataObject);
            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getuploadRegisterPicInput(String image)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(RegSpinnersData.API,"upload_profile_pic");
            jsonObject.put(RegSpinnersData.VERSION,"1.0");
            jsonObject.put("device",sharedPref.getSharedPrefData(Constants.DEVICE_ID));

            JSONObject dataObject = new JSONObject();
            dataObject.put(UploadProfilePicData.IMAGE,image);

            jsonObject.put(RegSpinnersData.DATA,dataObject);

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void uploadProfilePic(JSONObject jsonObject)
    {
       mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.URL_UPLOAD_PROF_PIC,Constants.TAG_UPLOAD_PROFILE_PIC,jsonObject);
    }

    public void uploadRegisterPic(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.URL_UPLOAD_PROF_PIC,Constants.TAG_UPLOAD_REGISTER_PIC,jsonObject);
    }

    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {

    }

    @Override
    public void getResponse(String strResponse, String tag) {
        switch (tag)
        {
            case Constants.TAG_UPLOAD_PROFILE_PIC:
                parseUploadProfPicResponse(strResponse,tag);
                break;
            case Constants.TAG_UPLOAD_REGISTER_PIC:
                parseUploadRegisterPicResponse(strResponse,tag);
                break;
        }
    }

    private void parseUploadRegisterPicResponse(String strResponse, String tag) {

        String status = "",message = "";
        HashMap<String,String> dataMap = new HashMap<>();
        try
        {
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.has(UploadProfilePicData.STATUS))
            {
                status = Constants.getStringValueOfJsonObject(jsonObject,UploadProfilePicData.STATUS,UploadProfilePicData.STATUS);
            }

            if(jsonObject.has(UploadProfilePicData.MESSAGE))
            {
                message = Constants.getStringValueOfJsonObject(jsonObject,UploadProfilePicData.MESSAGE,UploadProfilePicData.MESSAGE);
            }

            if(jsonObject.has(UploadProfilePicData.DATA))
            {
                JSONObject dataObj = jsonObject.getJSONObject(UploadProfilePicData.DATA);
                String image = Constants.getStringValueOfJsonObject(dataObj,UploadProfilePicData.FILE_NAME,UploadProfilePicData.FILE_NAME);
                UploadProfilePicData.getInstance().setRegisterPicImage(image);
            }
            mVolleyCallbackInterface.successCallBack("success",tag);
        }catch (Exception e)
        {
            Log.e("UPLOADPROFPICERROR",e.getLocalizedMessage());
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }

    }

    @Override
    public void error(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_UPLOAD_PROFILE_PIC:
                mVolleyCallbackInterface.errorCallBack(msg,tag);
                break;
        }
    }


    public void parseUploadProfPicResponse(String strResponse, String tag)
    {
        String status = "",message = "";
        HashMap<String,String> dataMap = new HashMap<>();
        try
        {
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.has(UploadProfilePicData.STATUS))
            {
                status = Constants.getStringValueOfJsonObject(jsonObject,UploadProfilePicData.STATUS,UploadProfilePicData.STATUS);
            }
            if(jsonObject.has(UploadProfilePicData.MESSAGE))
            {
                message = Constants.getStringValueOfJsonObject(jsonObject,UploadProfilePicData.MESSAGE,UploadProfilePicData.MESSAGE);
            }
            if(jsonObject.has(UploadProfilePicData.DATA))
            {
                JSONObject dataObj = jsonObject.getJSONObject(UploadProfilePicData.DATA);
                sharedPref.putSharedPrefData(Constants.PROFILEPICPATH,Constants.getStringValueOfJsonObject(dataObj,UploadProfilePicData.IMAGEPATH,UploadProfilePicData.IMAGEPATH));
                dataMap.put(UploadProfilePicData.IMAGEPATH,Constants.getStringValueOfJsonObject(dataObj,UploadProfilePicData.IMAGEPATH,UploadProfilePicData.IMAGEPATH));
                dataMap.put(UploadProfilePicData.USERPHOTOSID,Constants.getStringValueOfJsonObject(dataObj,UploadProfilePicData.USERPHOTOSID,UploadProfilePicData.USERPHOTOSID));

            }
            UploadProfilePicData.getInstance().setUploadPicStatus(status);
            UploadProfilePicData.getInstance().setUploadPicMessage(message);
            UploadProfilePicData.getInstance().setUploadPicResultMap(dataMap);

            mVolleyCallbackInterface.successCallBack("success",tag);
        }catch (Exception e)
        {
            Log.e("UPLOADPROFPICERROR",e.getLocalizedMessage());
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }
}
