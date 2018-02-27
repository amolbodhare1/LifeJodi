package com.lifejodi.event.managers;

import android.content.Context;

import com.android.volley.Request;
import com.lifejodi.event.data.EventRegistrationData;
import com.lifejodi.event.data.EventsData;
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
 * Created by Administrator on 2/27/2018.
 */

public class EventsManager implements VolleyResponse {

    private VolleyRequest mVolleyRequest;
    private VolleyCallbackInterface mVolleyCallbackInterface;

    private static EventsManager eventsManager = new EventsManager();

    public static EventsManager getInstance()
    {
        return eventsManager;
    }

    public EventsManager()
    {
        mVolleyRequest = new VolleyRequest(this);
    }

    public void initialize(VolleyCallbackInterface VolleyCallbackInterface, Context mContext) {
        this.mVolleyCallbackInterface = VolleyCallbackInterface;
        mVolleyRequest.setContext(mContext);

    }

    //GET EVENTS LIST
    public JSONObject getEventsListParams(String devId)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(EventRegistrationData.API,"eventList");
            jsonObject.put(EventRegistrationData.DEVICE,devId);
            jsonObject.put(EventRegistrationData.VERSION,"1.0");

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void getEventsList(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.URL_GET_EVENTS_LIST,Constants.TAG_GET_EVENTS_LIST,jsonObject);
    }
    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {

    }

    @Override
    public void getResponse(String strResponse, String tag) {

        switch (tag)
        {
            case Constants.TAG_GET_EVENTS_LIST:
                parseEventsListResponse(strResponse,tag);
                break;
        }
    }

    @Override
    public void error(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_GET_EVENTS_LIST:
                mVolleyCallbackInterface.errorCallBack(msg,tag);
                break;
        }
    }

    public void parseEventsListResponse(String strResponse, String tag)
    {
        ArrayList<HashMap<String,String>> dataList = new ArrayList<>();

        try
        {
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.has(EventsData.DATA))
            {
                JSONArray jsonArray = jsonObject.getJSONArray(EventsData.DATA);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject dataObject = jsonArray.getJSONObject(i);
                    HashMap<String,String> dataMap = new HashMap<>();

                    dataMap.put(EventsData.ID,Constants.getStringValueOfJsonObject(dataObject,EventsData.ID,EventsData.ID));
                    dataMap.put(EventsData.EVENTNAME,Constants.getStringValueOfJsonObject(dataObject,EventsData.EVENTNAME,EventsData.EVENTNAME));
                    dataMap.put(EventsData.EVENTDATE,Constants.getStringValueOfJsonObject(dataObject,EventsData.EVENTDATE,EventsData.EVENTDATE));
                    dataMap.put(EventsData.EVENTINFORMATION,Constants.getStringValueOfJsonObject(dataObject,EventsData.EVENTINFORMATION,EventsData.EVENTINFORMATION));
                    dataMap.put(EventsData.ADDRESS,Constants.getStringValueOfJsonObject(dataObject,EventsData.ADDRESS,EventsData.ADDRESS));
                    dataMap.put(EventsData.ADDBY,Constants.getStringValueOfJsonObject(dataObject,EventsData.ADDBY,EventsData.ADDBY));
                    dataMap.put(EventsData.ADDDATE,Constants.getStringValueOfJsonObject(dataObject,EventsData.ADDDATE,EventsData.ADDDATE));
                    dataMap.put(EventsData.DELETE,Constants.getStringValueOfJsonObject(dataObject,EventsData.DELETE,EventsData.DELETE));
                    dataMap.put(EventsData.ACTIVE,Constants.getStringValueOfJsonObject(dataObject,EventsData.ACTIVE,EventsData.ACTIVE));

                    dataList.add(dataMap);

                }

                EventsData.getInstance().setEventsList(dataList);
                mVolleyCallbackInterface.successCallBack("success",tag);
            }
        }catch (Exception e)
        {
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }
}
