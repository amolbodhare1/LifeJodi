package com.lifejodi.event.managers;

import android.content.Context;

import com.android.volley.Request;
import com.lifejodi.event.data.EventRegistrationData;
import com.lifejodi.home.data.HomeFragmentsData;
import com.lifejodi.login.data.LoginData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;
import com.lifejodi.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2/26/2018.
 */

public class EventRegManager implements VolleyResponse {

    private VolleyRequest mVolleyRequest;
    private VolleyCallbackInterface mVolleyCallbackInterface;

    private static EventRegManager eventRegManager = new EventRegManager();

    public static EventRegManager getInstance()
    {
        return eventRegManager;
    }


    public EventRegManager()
    {
        mVolleyRequest = new VolleyRequest(this);
    }
    public void initialize(VolleyCallbackInterface VolleyCallbackInterface, Context mContext) {
        this.mVolleyCallbackInterface = VolleyCallbackInterface;
        mVolleyRequest.setContext(mContext);

    }

    //REGISTRATION FOR EVENTS
    public JSONObject getRegEventParams(String devId,String userId,String eventId,String fname,String lname,String mob)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(EventRegistrationData.API,"eventRegistration");
            jsonObject.put(EventRegistrationData.DEVICE,devId);
            jsonObject.put(EventRegistrationData.VERSION,"1.0");

            JSONObject dataObject = new JSONObject();
            dataObject.put(EventRegistrationData.USERID,userId);
            dataObject.put(EventRegistrationData.EVENTID,eventId);
            dataObject.put(EventRegistrationData.FIRSTNAME,fname);
            dataObject.put(EventRegistrationData.LASTNAME,lname);
            dataObject.put(EventRegistrationData.MOBILENUM,mob);

            jsonObject.put(EventRegistrationData.DATA,dataObject);
            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void regUserForEvent(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.URL_REGISTER_EVENT,Constants.TAG_REGISTER_EVENT,jsonObject);
    }

    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {

    }

    @Override
    public void getResponse(String strResponse, String tag) {
        switch (tag)
        {
            case Constants.TAG_REGISTER_EVENT:
                parseRegisterEventResponse(strResponse,tag);
                break;
        }
    }

    @Override
    public void error(String msg, String tag) {
        mVolleyCallbackInterface.errorCallBack("error",tag);
    }

    public void parseRegisterEventResponse(String strResponse, String tag)
    {
        String status = "",message="";
        try
        {
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.has(EventRegistrationData.STATUS))
            {
                status = Constants.getStringValueOfJsonObject(jsonObject,EventRegistrationData.STATUS,EventRegistrationData.STATUS);
            }
            if(jsonObject.has(EventRegistrationData.MESSAGE))
            {
                message = Constants.getStringValueOfJsonObject(jsonObject,EventRegistrationData.MESSAGE,EventRegistrationData.MESSAGE);
            }

            EventRegistrationData.getInstance().setEventRegStatus(status);
            EventRegistrationData.getInstance().setEventRegMessage(message);

            mVolleyCallbackInterface.successCallBack("success",tag);
        }catch (Exception e)
        {
            mVolleyCallbackInterface.errorCallBack("error",tag);
        }
    }
}
