package com.lifejodi.home.managers;

import android.content.Context;

import com.android.volley.Request;
import com.lifejodi.home.data.ProfilesData;
import com.lifejodi.home.data.ShortlistData;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;
import com.lifejodi.search.data.SearchData;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JANHAVI on 2/9/2018.
 */

public class ProfileDataManager implements VolleyResponse {

    private VolleyRequest mVolleyRequest;
    private VolleyCallbackInterface mVolleyCallbackInterface;
    SharedPref sharedPreference = SharedPref.getSharedInstance();

    private static ProfileDataManager profileDataManager = new ProfileDataManager();

    public static ProfileDataManager getInstance()
    {
        return profileDataManager;
    }

    public String deviceId ,userId;

    public ProfileDataManager()
    {
        mVolleyRequest = new VolleyRequest(this);
    }

    ProfilesData profilesData = ProfilesData.getInstance();
    public void initialize(VolleyCallbackInterface VolleyCallbackInterface, Context mContext) {
        this.mVolleyCallbackInterface = VolleyCallbackInterface;
        mVolleyRequest.setContext(mContext);
        sharedPreference.initialize(mContext);
        deviceId = sharedPreference.getSharedPrefData(Constants.DEVICE_ID);
        userId = sharedPreference.getSharedPrefData(Constants.UID);

    }
    //GET SHORTLISTED PROFILE DETAILS

    public JSONObject getUpdateProfileParams(String devId,JSONObject json)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ShortlistData.API,"editProfile");
            jsonObject.put(ShortlistData.DEVICE,deviceId);
            jsonObject.put(ShortlistData.VERSION,"1.0");

            jsonObject.put(ProfilesData.DATA,json);

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getSendRequestInput(String receiverId)
    {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put(ShortlistData.API,"sendRequest");
            jsonObject.put(ShortlistData.DEVICE,deviceId);
            jsonObject.put(ShortlistData.VERSION,"1.0");

            JSONObject data = new JSONObject();
            data.put("user_id_sender",userId);
            data.put("user_id_receiver",receiverId);

            jsonObject.put(ProfilesData.DATA,data);

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getAccpetRequestInput(String receiverId)
    {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put(ShortlistData.API,"acceptRejectRequest");
            jsonObject.put(ShortlistData.DEVICE,deviceId);
            jsonObject.put(ShortlistData.VERSION,"1.0");

            JSONObject data = new JSONObject();
            data.put("sender_id",receiverId);
            data.put("receiver_id",userId);
            data.put("status","1");

            jsonObject.put(ProfilesData.DATA,data);

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getRejectRequestInput(String receiverId)
    {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put(ShortlistData.API,"acceptRejectRequest");
            jsonObject.put(ShortlistData.DEVICE,deviceId);
            jsonObject.put(ShortlistData.VERSION,"1.0");

            JSONObject data = new JSONObject();
            data.put("sender_id",receiverId);
            data.put("receiver_id",userId);
            data.put("status","2");

            jsonObject.put(ProfilesData.DATA,data);

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getRequestStatusInput()
    {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put(ShortlistData.API,"requestStatus");
            jsonObject.put(ShortlistData.DEVICE,deviceId);
            jsonObject.put(ShortlistData.VERSION,"1.0");

            JSONObject data = new JSONObject();
            data.put("user_id",userId);

            jsonObject.put(ProfilesData.DATA,data);

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getShortListedDetailsParams(String devId,String profId)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ShortlistData.API,"profileShortDetails");
            jsonObject.put(ShortlistData.DEVICE,devId);
            jsonObject.put(ShortlistData.VERSION,"1.0");

            JSONObject dataObject = new JSONObject();
            dataObject.put(ProfilesData.PROFILEID,profId);
            String userId = sharedPreference.getSharedPrefData(Constants.UID);
            dataObject.put(UserRegData.USERID,userId);

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

    public void sendRequest(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.BASE_URL,Constants.TAG_SEND_REQUEST,jsonObject);
    }

    public void acceptRequest(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.BASE_URL,Constants.TAG_ACCEPT_REQUEST,jsonObject);
    }

    public void  rejectRequest(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.BASE_URL,Constants.TAG_REJECT_REQUEST,jsonObject);
    }



    public void getRequestStatus(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.BASE_URL,Constants.TAG_REQUEST_STATUS,jsonObject);
    }

    //GET  PROFILE DETAILS


    public void getUpdateProfile(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.URL_UPDATE_PROFILE,Constants.TAG_UPDATE_PROFILE,jsonObject);
    }
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
            case Constants.TAG_UPDATE_PROFILE:
                parseUpdateProfileResponse(strResponse,tag);
                break;
            case Constants.TAG_SEND_REQUEST:
                parseSendRequestResponse(strResponse,tag);
                break;
            case Constants.TAG_ACCEPT_REQUEST:
                parseAcceptRequestResponse(strResponse,tag);
                break;
            case Constants.TAG_REJECT_REQUEST:
                parseRejectRequestResponse(strResponse,tag);
                break;
            case Constants.TAG_REQUEST_STATUS:
                parseRequestStatusResponse(strResponse,tag);
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
            case Constants.TAG_UPDATE_PROFILE:
                mVolleyCallbackInterface.errorCallBack(msg,tag);
                break;
            case Constants.TAG_SEND_REQUEST:
                mVolleyCallbackInterface.errorCallBack(msg,tag);
                break;
            case Constants.TAG_ACCEPT_REQUEST:
                mVolleyCallbackInterface.errorCallBack(msg,tag);
                break;
            case Constants.TAG_REQUEST_STATUS:
                mVolleyCallbackInterface.errorCallBack(msg,tag);
                break;
        }
    }
    private void parseUpdateProfileResponse(String strResponse, String tag) {

        try {
            JSONObject jsonObject = new JSONObject(strResponse);
            if (jsonObject.has(ProfilesData.STATUS)) {
            String status = Constants.getStringValueOfJsonObject(jsonObject, ProfilesData.STATUS, ProfilesData.STATUS);
            if(status.equals("1")){
                mVolleyCallbackInterface.successCallBack("success",tag);
            }else {
                mVolleyCallbackInterface.errorCallBack("Update failed",tag);
            }
            }
        }catch (Exception e){
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
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
                    dataMap.put(ProfilesData.STATUS, Constants.getStringValueOfJsonObject(userObject,ProfilesData.STATUS,ProfilesData.STATUS));
                    dataMap.put(ProfilesData.MODE, Constants.getStringValueOfJsonObject(userObject,ProfilesData.MODE,ProfilesData.MODE));

                    dataMap.put(ProfilesData.HEIGHT,Constants.getStringValueOfJsonObject(userObject,ProfilesData.HEIGHT,ProfilesData.HEIGHT));
                    dataMap.put(ProfilesData.LAT,Constants.getStringValueOfJsonObject(userObject,ProfilesData.LAT,ProfilesData.LAT));
                    dataMap.put(ProfilesData.LNG,Constants.getStringValueOfJsonObject(userObject,ProfilesData.LNG,ProfilesData.LNG));
                    dataMap.put(ProfilesData.SUBLOCALITY,Constants.getStringValueOfJsonObject(userObject,ProfilesData.SUBLOCALITY,ProfilesData.SUBLOCALITY));
                    dataMap.put(ProfilesData.LOCALITY,Constants.getStringValueOfJsonObject(userObject,ProfilesData.LOCALITY,ProfilesData.LOCALITY));
                    dataMap.put(ProfilesData.ADMINISTRATIVEAREA,Constants.getStringValueOfJsonObject(userObject,ProfilesData.ADMINISTRATIVEAREA,ProfilesData.ADMINISTRATIVEAREA));
                    dataMap.put(ProfilesData.COUNTRY,Constants.getStringValueOfJsonObject(userObject,ProfilesData.COUNTRY,ProfilesData.COUNTRY));
                    dataMap.put(ProfilesData.PINCODE,Constants.getStringValueOfJsonObject(userObject,ProfilesData.PINCODE,ProfilesData.PINCODE));
                    dataMap.put(ProfilesData.FORMATTEDADRRESS,Constants.getStringValueOfJsonObject(userObject,ProfilesData.FORMATTEDADRRESS,ProfilesData.FORMATTEDADRRESS));
                    dataMap.put(ProfilesData.USERID,Constants.getStringValueOfJsonObject(userObject,ProfilesData.USERID,ProfilesData.USERID));
                    dataMap.put(ProfilesData.PROFILEFOR,Constants.getStringValueOfJsonObject(userObject,ProfilesData.PROFILEFOR,ProfilesData.PROFILEFOR));

                    dataMap.put(ProfilesData.COUNTRYCODE,Constants.getStringValueOfJsonObject(userObject,ProfilesData.COUNTRYCODE,ProfilesData.COUNTRYCODE));
                    dataMap.put(ProfilesData.PHNUMBER,Constants.getStringValueOfJsonObject(userObject,ProfilesData.PHNUMBER,ProfilesData.PHNUMBER));
                    dataMap.put(ProfilesData.EMAIL,Constants.getStringValueOfJsonObject(userObject,ProfilesData.EMAIL,ProfilesData.EMAIL));
                    dataMap.put(ProfilesData.PASSWORD,Constants.getStringValueOfJsonObject(userObject,ProfilesData.PASSWORD,ProfilesData.PASSWORD));
                    dataMap.put(ProfilesData.MARITALSTATUS,Constants.getStringValueOfJsonObject(userObject,ProfilesData.MARITALSTATUS,ProfilesData.MARITALSTATUS));

                    dataMap.put(ProfilesData.CASTE,Constants.getStringValueOfJsonObject(userObject,ProfilesData.CASTE,ProfilesData.CASTE));
                    dataMap.put(ProfilesData.DOSHAM,Constants.getStringValueOfJsonObject(userObject,ProfilesData.DOSHAM,ProfilesData.DOSHAM));
                    dataMap.put(ProfilesData.MARRYOTHERCAST,Constants.getStringValueOfJsonObject(userObject,ProfilesData.MARRYOTHERCAST,ProfilesData.MARRYOTHERCAST));
                    dataMap.put(ProfilesData.PHYSICALSTATUS,Constants.getStringValueOfJsonObject(userObject,ProfilesData.PHYSICALSTATUS,ProfilesData.PHYSICALSTATUS));
                    dataMap.put(ProfilesData.ANNUALINCOME,Constants.getStringValueOfJsonObject(userObject,ProfilesData.ANNUALINCOME,ProfilesData.ANNUALINCOME));
                    dataMap.put(ProfilesData.FAMILYSTATUS,Constants.getStringValueOfJsonObject(userObject,ProfilesData.FAMILYSTATUS,ProfilesData.FAMILYSTATUS));
                    dataMap.put(ProfilesData.FAMILYTYPE,Constants.getStringValueOfJsonObject(userObject,ProfilesData.FAMILYTYPE,ProfilesData.FAMILYTYPE));
                    dataMap.put(ProfilesData.CURRENCYID,Constants.getStringValueOfJsonObject(userObject,ProfilesData.CURRENCYID,ProfilesData.CURRENCYID));
                    dataMap.put(ProfilesData.FAMILYVALUES,Constants.getStringValueOfJsonObject(userObject,ProfilesData.FAMILYVALUES,ProfilesData.FAMILYVALUES));
                    dataMap.put(ProfilesData.COMMENT,Constants.getStringValueOfJsonObject(userObject,ProfilesData.COMMENT,ProfilesData.COMMENT));
                    dataMap.put(ProfilesData.EDUCATIONLEVEL,Constants.getStringValueOfJsonObject(userObject,ProfilesData.EDUCATIONLEVEL,ProfilesData.EDUCATIONLEVEL));

                    dataMap.put(ProfilesData.OCCUPATION,Constants.getStringValueOfJsonObject(userObject,ProfilesData.OCCUPATION,ProfilesData.OCCUPATION));
                    dataMap.put(ProfilesData.WORKINGAS,Constants.getStringValueOfJsonObject(userObject,ProfilesData.WORKINGAS,ProfilesData.WORKINGAS));
                    dataMap.put(ProfilesData.SHORTLISTED_STATUS,Constants.getStringValueOfJsonObject(userObject,ProfilesData.SHORTLISTED_STATUS,ProfilesData.SHORTLISTED_STATUS));

                    ProfilesData.getInstance().setShortListedDetailsMap(dataMap);
                }
            }
            mVolleyCallbackInterface.successCallBack("success",tag);
        }catch (Exception e)
        {
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }

    private void parseSendRequestResponse(String strResponse, String tag) {

        try {
            JSONObject jsonObject = new JSONObject(strResponse);
            if (jsonObject.has(ShortlistData.STATUS)) {
                String status = Constants.getStringValueOfJsonObject(jsonObject, ProfilesData.STATUS, ProfilesData.STATUS);
                String message = Constants.getStringValueOfJsonObject(jsonObject, ProfilesData.MESSAGE, ProfilesData.MESSAGE);
                if(status.equals("1")){
                    mVolleyCallbackInterface.successCallBack(message,tag);
                }else {
                    mVolleyCallbackInterface.errorCallBack("Something went wrong..",tag);
                }
            }
        }catch (Exception e){
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }

    }

    private void parseAcceptRequestResponse(String strResponse, String tag) {

        try {
            JSONObject jsonObject = new JSONObject(strResponse);
            if (jsonObject.has(ShortlistData.STATUS)) {
                String status = Constants.getStringValueOfJsonObject(jsonObject, ProfilesData.STATUS, ProfilesData.STATUS);
                String message = Constants.getStringValueOfJsonObject(jsonObject, ProfilesData.MESSAGE, ProfilesData.MESSAGE);
                if(status.equals("1")){
                    mVolleyCallbackInterface.successCallBack(message,tag);
                }else {
                    mVolleyCallbackInterface.errorCallBack("Something went wrong..",tag);
                }
            }
        }catch (Exception e){
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }


    }

    private void parseRejectRequestResponse(String strResponse, String tag) {

        try {
            JSONObject jsonObject = new JSONObject(strResponse);
            if (jsonObject.has(ShortlistData.STATUS)) {
                String status = Constants.getStringValueOfJsonObject(jsonObject, ProfilesData.STATUS, ProfilesData.STATUS);
                String message = Constants.getStringValueOfJsonObject(jsonObject, ProfilesData.MESSAGE, ProfilesData.MESSAGE);
                if(status.equals("1")){
                    mVolleyCallbackInterface.successCallBack(message,tag);
                }else {
                    mVolleyCallbackInterface.errorCallBack("Something went wrong..",tag);
                }
            }
        }catch (Exception e){
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }

    }

    private void parseRequestStatusResponse(String strResponse, String tag) {

        String status = null, message;
        ArrayList<HashMap<String, String>> pendingList = new ArrayList<>();
        ArrayList<HashMap<String, String>> acceptedList = new ArrayList<>();
        ArrayList<HashMap<String, String>> rejectedList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(strResponse);
            if (jsonObject.has(ShortlistData.STATUS)) {
                status = Constants.getStringValueOfJsonObject(jsonObject, ShortlistData.STATUS, ShortlistData.STATUS);
            }
            if (jsonObject.has(ShortlistData.MESSAGE)) {
                message = Constants.getStringValueOfJsonObject(jsonObject, ShortlistData.MESSAGE, ShortlistData.MESSAGE);
            }
            if (status.equals("1")) {
                if (jsonObject.has(ShortlistData.DATA)) {
                    JSONArray jsonArray = jsonObject.getJSONArray(ShortlistData.DATA);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject userObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> dataMap = new HashMap<>();

                        dataMap.put(ProfilesData.ID, Constants.getStringValueOfJsonObject(userObject, ProfilesData.ID, ProfilesData.ID));
                        dataMap.put(ProfilesData.PROFILEID, Constants.getStringValueOfJsonObject(userObject, ProfilesData.PROFILEID, ProfilesData.PROFILEID));
                        dataMap.put(ProfilesData.FULLNAME, Constants.getStringValueOfJsonObject(userObject, ProfilesData.FULLNAME, ProfilesData.FULLNAME));
                        dataMap.put(ProfilesData.GENDER, Constants.getStringValueOfJsonObject(userObject, ProfilesData.GENDER, ProfilesData.GENDER));
                        dataMap.put(ProfilesData.DOB, Constants.getStringValueOfJsonObject(userObject, ProfilesData.DOB, ProfilesData.DOB));
                        dataMap.put(ProfilesData.MOTHERTOUNGUEID, Constants.getStringValueOfJsonObject(userObject, ProfilesData.MOTHERTOUNGUEID, ProfilesData.MOTHERTOUNGUEID));
                        dataMap.put(ProfilesData.RELIGIONID, Constants.getStringValueOfJsonObject(userObject, ProfilesData.RELIGIONID, ProfilesData.RELIGIONID));
                        dataMap.put(ProfilesData.AGE, Constants.getStringValueOfJsonObject(userObject, ProfilesData.AGE, ProfilesData.AGE));
                        dataMap.put(ProfilesData.MOTHERTONGUENAME, Constants.getStringValueOfJsonObject(userObject, ProfilesData.MOTHERTONGUENAME, ProfilesData.MOTHERTONGUENAME));
                        dataMap.put(ProfilesData.RELIGION, Constants.getStringValueOfJsonObject(userObject, ProfilesData.RELIGION, ProfilesData.RELIGION));
                        dataMap.put(ProfilesData.PHOTONAME, Constants.getStringValueOfJsonObject(userObject, ProfilesData.PHOTONAME, ProfilesData.PHOTONAME));
                        dataMap.put(ProfilesData.PROFILEPIC, Constants.getStringValueOfJsonObject(userObject, ProfilesData.PROFILEPIC, ProfilesData.PROFILEPIC));
                        dataMap.put(ProfilesData.STATUS, Constants.getStringValueOfJsonObject(userObject, ProfilesData.STATUS, ProfilesData.STATUS));
                        dataMap.put(ProfilesData.MODE, Constants.getStringValueOfJsonObject(userObject, ProfilesData.MODE, ProfilesData.MODE));

                        if (dataMap.get(ProfilesData.STATUS).equals("1")) {
                            acceptedList.add(dataMap);
                        } else if (dataMap.get(ProfilesData.STATUS).equals("2")) {
                            rejectedList.add(dataMap);
                        } else if (dataMap.get(ProfilesData.STATUS).equals("0")) {
                            pendingList.add(dataMap);
                        }

                    }

                    profilesData.setPendingList(pendingList);
                    profilesData.setAcceptedList(acceptedList);
                    profilesData.setRejectedList(rejectedList);
                    mVolleyCallbackInterface.successCallBack("success", tag);
                }
            }
        } catch (Exception e) {
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(), tag);
        }


    }

    //PARSE PROFILEDETAILS RESPONSE
}
