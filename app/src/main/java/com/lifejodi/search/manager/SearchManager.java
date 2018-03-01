package com.lifejodi.search.manager;

import android.content.Context;

import com.android.volley.Request;
import com.lifejodi.login.data.LoginData;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;
import com.lifejodi.search.data.SearchData;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by parikshit on 28/2/18.
 */

public class SearchManager implements VolleyResponse {

    private VolleyRequest mVolleyRequest;
    private VolleyCallbackInterface mVolleyCallbackInterface;

    private static SearchManager ourInstance = new SearchManager();
    public static SearchManager getInstance(){return ourInstance;};

    SharedPreference sharedPreference = SharedPreference.getSharedInstance();
    UserRegData userRegData = UserRegData.getInstance();

    private SearchManager(){
        mVolleyRequest = new VolleyRequest(this);
    }
    public void initialize(VolleyCallbackInterface VolleyCallbackInterface, Context mContext) {
        this.mVolleyCallbackInterface = VolleyCallbackInterface;
        mVolleyRequest.setContext(mContext);
        sharedPreference.initialize(mContext);

    }

    public JSONObject getSearchByIdInput(String deviceId,String userId,String profileId){

        JSONObject jsonObject = new JSONObject();

        try{
            jsonObject.put(LoginData.API,"search");
            jsonObject.put(LoginData.DEVICE,deviceId);
            jsonObject.put(LoginData.VERSION,"1.0");

            JSONObject dataObj = new JSONObject();
            dataObj.put(LoginData.USERID,userId);
            dataObj.put("search_by_id","1");
            dataObj.put("profile_id",profileId);
            jsonObject.put(LoginData.DATA,dataObj);
        }catch (Exception e){

        }

        return jsonObject;
    }

    public JSONObject getCustomSearchInput(String deviceId,JSONObject data){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put(LoginData.API,"search");
            jsonObject.put(LoginData.DEVICE,deviceId);
            jsonObject.put(LoginData.VERSION,"1.0");

            jsonObject.put(LoginData.DATA,data);
        }catch (Exception e){

        }

        return jsonObject;
    }

    public void getCustomSearch(JSONObject jsonObject){

        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.URL_UPDATE_PROFILE,Constants.TAG_SEARCH_CUSTOM,jsonObject);

    }

    public void getSearchById(JSONObject jsonObject){

        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.URL_UPDATE_PROFILE,Constants.TAG_SEARCH_BY_ID,jsonObject);

    }

    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {

    }

    @Override
    public void getResponse(String strResponse, String tag) {

        switch (tag)
        {
            case Constants.TAG_SEARCH_BY_ID:
                parseSearchByIdResponse(strResponse,tag);
                break;
            case Constants.TAG_SEARCH_CUSTOM:
                parseCustomSearchResponse(strResponse,tag);
                break;
        }

    }

    @Override
    public void error(String msg, String tag) {
        mVolleyCallbackInterface.errorCallBack(msg,tag);
    }

    public void parseSearchByIdResponse(String strResponse, String tag)
    {
        String status="",message="";
        ArrayList<HashMap<String,String>> dataList = new ArrayList<>();
        try
        {
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.has(SearchData.STATUS))
            {
                status = Constants.getStringValueOfJsonObject(jsonObject,SearchData.STATUS,SearchData.STATUS);
            }
            if(jsonObject.has(SearchData.MESSAGE))
            {
                message = Constants.getStringValueOfJsonObject(jsonObject,SearchData.MESSAGE,SearchData.MESSAGE);
            }
            if(jsonObject.has(SearchData.DATA))
            {
                JSONArray jsonArray = jsonObject.getJSONArray(SearchData.DATA);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject dataObject = jsonArray.getJSONObject(i);
                    HashMap<String,String> dataMap = new HashMap<>();

                    dataMap.put(SearchData.HEIGHT,Constants.getStringValueOfJsonObject(dataObject,SearchData.HEIGHT,SearchData.HEIGHT));
                    dataMap.put(SearchData.AGE,Constants.getStringValueOfJsonObject(dataObject,SearchData.AGE,SearchData.AGE));
                    dataMap.put(SearchData.LAT,Constants.getStringValueOfJsonObject(dataObject,SearchData.LAT,SearchData.LAT));
                    dataMap.put(SearchData.LNG,Constants.getStringValueOfJsonObject(dataObject,SearchData.LNG,SearchData.LNG));
                    dataMap.put(SearchData.SUBLOCALITY,Constants.getStringValueOfJsonObject(dataObject,SearchData.SUBLOCALITY,SearchData.SUBLOCALITY));
                    dataMap.put(SearchData.LOCALITY,Constants.getStringValueOfJsonObject(dataObject,SearchData.LOCALITY,SearchData.LOCALITY));
                    dataMap.put(SearchData.ADMINISTRATIVEAREA,Constants.getStringValueOfJsonObject(dataObject,SearchData.ADMINISTRATIVEAREA,SearchData.ADMINISTRATIVEAREA));
                    dataMap.put(SearchData.COUNTRY,Constants.getStringValueOfJsonObject(dataObject,SearchData.COUNTRY,SearchData.COUNTRY));
                    dataMap.put(SearchData.PINCODE,Constants.getStringValueOfJsonObject(dataObject,SearchData.PINCODE,SearchData.PINCODE));
                    dataMap.put(SearchData.FORMATTEDADRRESS,Constants.getStringValueOfJsonObject(dataObject,SearchData.FORMATTEDADRRESS,SearchData.FORMATTEDADRRESS));
                    dataMap.put(SearchData.USERID,Constants.getStringValueOfJsonObject(dataObject,SearchData.USERID,SearchData.USERID));
                    dataMap.put(SearchData.ID,Constants.getStringValueOfJsonObject(dataObject,SearchData.ID,SearchData.ID));
                    dataMap.put(SearchData.PROFILEID,Constants.getStringValueOfJsonObject(dataObject,SearchData.PROFILEID,SearchData.PROFILEID));
                    dataMap.put(SearchData.PROFILEFOR,Constants.getStringValueOfJsonObject(dataObject,SearchData.PROFILEFOR,SearchData.PROFILEFOR));

                    dataMap.put(SearchData.FULLNAME,Constants.getStringValueOfJsonObject(dataObject,SearchData.FULLNAME,SearchData.FULLNAME));
                    dataMap.put(SearchData.GENDER,Constants.getStringValueOfJsonObject(dataObject,SearchData.GENDER,SearchData.GENDER));
                    dataMap.put(SearchData.DOB,Constants.getStringValueOfJsonObject(dataObject,SearchData.DOB,SearchData.DOB));
                    dataMap.put(SearchData.RELIGION,Constants.getStringValueOfJsonObject(dataObject,SearchData.RELIGION,SearchData.RELIGION));
                    dataMap.put(SearchData.MOTHERTONGUE,Constants.getStringValueOfJsonObject(dataObject,SearchData.MOTHERTONGUE,SearchData.MOTHERTONGUE));
                    dataMap.put(SearchData.COUNTRYCODE,Constants.getStringValueOfJsonObject(dataObject,SearchData.COUNTRYCODE,SearchData.COUNTRYCODE));
                    dataMap.put(SearchData.PHNUMBER,Constants.getStringValueOfJsonObject(dataObject,SearchData.PHNUMBER,SearchData.PHNUMBER));
                    dataMap.put(SearchData.EMAIL,Constants.getStringValueOfJsonObject(dataObject,SearchData.EMAIL,SearchData.EMAIL));
                    dataMap.put(SearchData.PASSWORD,Constants.getStringValueOfJsonObject(dataObject,SearchData.PASSWORD,SearchData.PASSWORD));
                    dataMap.put(SearchData.MARITALSTATUS,Constants.getStringValueOfJsonObject(dataObject,SearchData.MARITALSTATUS,SearchData.MARITALSTATUS));

                    dataMap.put(SearchData.CASTE,Constants.getStringValueOfJsonObject(dataObject,SearchData.CASTE,SearchData.CASTE));
                    dataMap.put(SearchData.DOSHAM,Constants.getStringValueOfJsonObject(dataObject,SearchData.DOSHAM,SearchData.DOSHAM));
                    dataMap.put(SearchData.MARRYOTHERCAST,Constants.getStringValueOfJsonObject(dataObject,SearchData.MARRYOTHERCAST,SearchData.MARRYOTHERCAST));
                    dataMap.put(SearchData.PHYSICALSTATUS,Constants.getStringValueOfJsonObject(dataObject,SearchData.PHYSICALSTATUS,SearchData.PHYSICALSTATUS));
                    dataMap.put(SearchData.ANNUALINCOME,Constants.getStringValueOfJsonObject(dataObject,SearchData.ANNUALINCOME,SearchData.ANNUALINCOME));
                    dataMap.put(SearchData.FAMILYSTATUS,Constants.getStringValueOfJsonObject(dataObject,SearchData.FAMILYSTATUS,SearchData.FAMILYSTATUS));
                    dataMap.put(SearchData.FAMILYTYPE,Constants.getStringValueOfJsonObject(dataObject,SearchData.FAMILYTYPE,SearchData.FAMILYTYPE));
                    dataMap.put(SearchData.CURRENCYID,Constants.getStringValueOfJsonObject(dataObject,SearchData.CURRENCYID,SearchData.CURRENCYID));
                    dataMap.put(SearchData.FAMILYVALUES,Constants.getStringValueOfJsonObject(dataObject,SearchData.FAMILYVALUES,SearchData.FAMILYVALUES));
                    dataMap.put(SearchData.COMMENT,Constants.getStringValueOfJsonObject(dataObject,SearchData.COMMENT,SearchData.COMMENT));
                    dataMap.put(SearchData.EDUCATIONLEVEL,Constants.getStringValueOfJsonObject(dataObject,SearchData.EDUCATIONLEVEL,SearchData.EDUCATIONLEVEL));

                    dataMap.put(SearchData.OCCUPATION,Constants.getStringValueOfJsonObject(dataObject,SearchData.OCCUPATION,SearchData.OCCUPATION));
                    dataMap.put(SearchData.WORKINGAS,Constants.getStringValueOfJsonObject(dataObject,SearchData.WORKINGAS,SearchData.WORKINGAS));
                    dataMap.put(SearchData.PROFILEPIC,Constants.getStringValueOfJsonObject(dataObject,SearchData.PROFILEPIC,SearchData.PROFILEPIC));

                    dataList.add(dataMap);
                }

                SearchData.getInstance().setSearchByIdsList(dataList);
                SearchData.getInstance().setSearchByIdStatus(status);
                SearchData.getInstance().setSearchByIdMessage(message);
                mVolleyCallbackInterface.successCallBack("success",tag);
            }
        }catch (Exception e)
        {
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }


    public void parseCustomSearchResponse(String strResponse, String tag)
    {
        String status="",message="";
        ArrayList<HashMap<String,String>> dataList = new ArrayList<>();

        try
        {
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.has(SearchData.STATUS))
            {
                status = Constants.getStringValueOfJsonObject(jsonObject,SearchData.STATUS,SearchData.STATUS);
            }
            if(jsonObject.has(SearchData.MESSAGE))
            {
                message = Constants.getStringValueOfJsonObject(jsonObject,SearchData.MESSAGE,SearchData.MESSAGE);
            }
            if(jsonObject.has(SearchData.DATA))
            {
               JSONArray jsonArray = jsonObject.getJSONArray(SearchData.DATA);
               for(int i=0;i<jsonArray.length();i++)
               {
                   JSONObject dataObject = jsonArray.getJSONObject(i);
                   HashMap<String,String> dataMap = new HashMap<>();

                   dataMap.put(SearchData.ID,Constants.getStringValueOfJsonObject(dataObject,SearchData.ID,SearchData.ID));
                   dataMap.put(SearchData.PROFILEID,Constants.getStringValueOfJsonObject(dataObject,SearchData.ID,SearchData.ID));
                   dataMap.put(SearchData.FULLNAME,Constants.getStringValueOfJsonObject(dataObject,SearchData.ID,SearchData.ID));
                   dataMap.put(SearchData.GENDER,Constants.getStringValueOfJsonObject(dataObject,SearchData.ID,SearchData.ID));
                   dataMap.put(SearchData.DOB,Constants.getStringValueOfJsonObject(dataObject,SearchData.ID,SearchData.ID));
                   dataMap.put(SearchData.MOTHERTONGUE,Constants.getStringValueOfJsonObject(dataObject,SearchData.ID,SearchData.ID));
                   dataMap.put(SearchData.RELIGION,Constants.getStringValueOfJsonObject(dataObject,SearchData.ID,SearchData.ID));
                   dataMap.put(SearchData.AGE,Constants.getStringValueOfJsonObject(dataObject,SearchData.ID,SearchData.ID));
                   dataMap.put(SearchData.PROFILEPIC,Constants.getStringValueOfJsonObject(dataObject,SearchData.ID,SearchData.ID));

                   dataList.add(dataMap);
               }

               SearchData.getInstance().setCustomSearchStatus(status);
               SearchData.getInstance().setCustomSearchMessage(message);
               SearchData.getInstance().setCustomSearchList(dataList);
               mVolleyCallbackInterface.successCallBack("success",tag);
            }
        }catch (Exception e)
        {
                mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }
}
