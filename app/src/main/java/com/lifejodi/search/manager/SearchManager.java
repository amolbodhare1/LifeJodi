package com.lifejodi.search.manager;

import android.content.Context;

import com.android.volley.Request;
import com.lifejodi.login.data.LoginData;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

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

    }

    @Override
    public void error(String msg, String tag) {

    }
}
