package com.lifejodi.login.manager;

import android.content.Context;

import com.android.volley.Request;
import com.lifejodi.login.data.LoginData;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Ajay on 04-12-2017.
 */

public class LoginManager implements VolleyResponse {

    private VolleyRequest mVolleyRequest;
    private VolleyCallbackInterface mVolleyCallbackInterface;

    private static LoginManager ourInstance = new LoginManager();
    public static LoginManager getInstance(){return ourInstance;};

    SharedPreference sharedPreference = SharedPreference.getSharedInstance();
    UserRegData userRegData = UserRegData.getInstance();

    private LoginManager(){
        mVolleyRequest = new VolleyRequest(this);
    }
    public void initialize(VolleyCallbackInterface VolleyCallbackInterface, Context mContext) {
        this.mVolleyCallbackInterface = VolleyCallbackInterface;
        mVolleyRequest.setContext(mContext);
        sharedPreference.initialize(mContext);

    }

    public JSONObject getLoginInputs(String email,String password)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(LoginData.EMAIL,email);
            jsonObject.put(LoginData.PASSWORD,password);

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getLoginDataObject(String deviceId,JSONObject object)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(LoginData.API,"login");
            jsonObject.put(LoginData.DEVICE,deviceId);
            jsonObject.put(LoginData.VERSION,"1.0");
            jsonObject.put(LoginData.DATA,object);
            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public void loginUser(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.URL_LOGIN,Constants.TAG_LOGIN,jsonObject);
    }


    //FORGOT PASSWORD
    public  JSONObject getForgotPassParams(String deviceId,String email)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(LoginData.API,"forgot");
            jsonObject.put(LoginData.DEVICE,deviceId);
            jsonObject.put(LoginData.VERSION,"1.0");

            JSONObject dataObj = new JSONObject();
            dataObj.put(LoginData.EMAIL,email);
            jsonObject.put(LoginData.DATA,dataObj);
            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void getForgotPassword(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST,Constants.URL_FORGOTPASSWORD,Constants.TAG_FORGOTPASSWORD,jsonObject);
    }

    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {

    }

    @Override
    public void getResponse(String strResponse, String tag) {

        switch (tag){
            case Constants.TAG_LOGIN:
                parseLoginResponse(strResponse,tag);
                break;
            case Constants.TAG_FORGOTPASSWORD:
                parseForgotpasswordResponse(strResponse,tag);
                break;
        }

    }

    @Override
    public void error(String msg, String tag) {
        mVolleyCallbackInterface.errorCallBack(msg,tag);
    }

    public void parseLoginResponse(String strResponse, String tag)
    {
        String status = "",message = "";
        HashMap<String,String> dataMap = new HashMap<>();
        try{
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.has(LoginData.MESSAGE))
            {
                message = Constants.getStringValueOfJsonObject(jsonObject,LoginData.MESSAGE,LoginData.MESSAGE);
                LoginData.getInstance().setLoginmessage(message);
            }
            if(jsonObject.has(LoginData.STATUS))
            {
                status = Constants.getStringValueOfJsonObject(jsonObject,LoginData.STATUS,LoginData.STATUS);
                LoginData.getInstance().setLoginStatus(status);
            }
            if(jsonObject.has(LoginData.DATA))
            {
                JSONArray jsonArray = jsonObject.getJSONArray(LoginData.DATA);
              /*  for(int i=0;i<jsonArray.length();i++)
                {*/

                    JSONObject dataObject = jsonArray.getJSONObject(0);
                    dataMap.put(LoginData.ID,Constants.getStringValueOfJsonObject(dataObject,LoginData.ID,LoginData.ID));
                    dataMap.put(LoginData.EMAIL,Constants.getStringValueOfJsonObject(dataObject,LoginData.EMAIL,LoginData.EMAIL));
                    dataMap.put(LoginData.CONTACTNAME,Constants.getStringValueOfJsonObject(dataObject,LoginData.CONTACTNAME,LoginData.CONTACTNAME));
                    dataMap.put(LoginData.PROFILEFOR,Constants.getStringValueOfJsonObject(dataObject,LoginData.PROFILEFOR,LoginData.PROFILEFOR));
                    dataMap.put(LoginData.GENDER,Constants.getStringValueOfJsonObject(dataObject,LoginData.GENDER,LoginData.GENDER));
                    dataMap.put(LoginData.DOB,Constants.getStringValueOfJsonObject(dataObject,LoginData.DOB,LoginData.DOB));
                    dataMap.put(LoginData.BIRTHTIME,Constants.getStringValueOfJsonObject(dataObject,LoginData.BIRTHTIME,LoginData.BIRTHTIME));
                    dataMap.put(LoginData.BIRTHPLACE,Constants.getStringValueOfJsonObject(dataObject,LoginData.BIRTHPLACE,LoginData.BIRTHPLACE));
                    dataMap.put(LoginData.PROFILEID,Constants.getStringValueOfJsonObject(dataObject,LoginData.PROFILEID,LoginData.PROFILEID));
                    dataMap.put(LoginData.FULLNAME,Constants.getStringValueOfJsonObject(dataObject,LoginData.FULLNAME,LoginData.FULLNAME));

                    sharedPreference.putSharedPrefData(Constants.LOGINNAME,Constants.getStringValueOfJsonObject(dataObject,LoginData.FULLNAME,LoginData.FULLNAME));
                    sharedPreference.putSharedPrefData(Constants.LOGINEMAIL,Constants.getStringValueOfJsonObject(dataObject,LoginData.EMAIL,LoginData.EMAIL));
                    sharedPreference.putSharedPrefData(Constants.PROFILEID,Constants.getStringValueOfJsonObject(dataObject,LoginData.PROFILEID,LoginData.PROFILEID));
                    sharedPreference.putSharedPrefData(Constants.UID,Constants.getStringValueOfJsonObject(dataObject,LoginData.USERID,LoginData.USERID));

                    userRegData.regDataObject = new JSONObject();


                    userRegData.regDataObject.put(UserRegData.USERID,Constants.getStringValueOfJsonObject(dataObject,UserRegData.USERID,UserRegData.USERID));
                    userRegData.regDataObject.put(UserRegData.EMAIL,Constants.getStringValueOfJsonObject(dataObject,UserRegData.EMAIL,UserRegData.EMAIL));
                    userRegData.regDataObject.put(UserRegData.PROFILEFOR,Constants.getStringValueOfJsonObject(dataObject,UserRegData.PROFILEFOR,UserRegData.PROFILEFOR));
                    userRegData.regDataObject.put(UserRegData.GENDER,Constants.getStringValueOfJsonObject(dataObject,UserRegData.GENDER,UserRegData.GENDER));
                    userRegData.regDataObject.put(UserRegData.DOB,Constants.getStringValueOfJsonObject(dataObject,UserRegData.DOB,UserRegData.DOB));
                    userRegData.regDataObject.put(UserRegData.PROFILEID,Constants.getStringValueOfJsonObject(dataObject,UserRegData.PROFILEID,UserRegData.PROFILEID));
                    userRegData.regDataObject.put(UserRegData.FULLNAME,Constants.getStringValueOfJsonObject(dataObject,UserRegData.FULLNAME,UserRegData.FULLNAME));

                    userRegData.regDataObject.put(userRegData.HEIGHT, Constants.getStringValueOfJsonObject(dataObject,UserRegData.HEIGHT,UserRegData.HEIGHT));
                    userRegData.regDataObject.put(userRegData.PHYSICALSTATUS, Constants.getStringValueOfJsonObject(dataObject,UserRegData.PHYSICALSTATUS,UserRegData.PHYSICALSTATUS));
                    userRegData.regDataObject.put(userRegData.EDUCATION, Constants.getStringValueOfJsonObject(dataObject,UserRegData.EDUCATION,UserRegData.EDUCATION));
                    userRegData.regDataObject.put(userRegData.OCCUPATION, Constants.getStringValueOfJsonObject(dataObject,UserRegData.OCCUPATION,UserRegData.OCCUPATION));
                    userRegData.regDataObject.put(userRegData.EMPLOYEDIN, Constants.getStringValueOfJsonObject(dataObject,UserRegData.EMPLOYEDIN,UserRegData.EMPLOYEDIN));
                    userRegData.regDataObject.put(userRegData.CURRENCY, Constants.getStringValueOfJsonObject(dataObject,UserRegData.CURRENCY,UserRegData.CURRENCY));
                    userRegData.regDataObject.put(userRegData.ANNUALINCOME, Constants.getStringValueOfJsonObject(dataObject,UserRegData.ANNUALINCOME,UserRegData.ANNUALINCOME));
                    userRegData.regDataObject.put(userRegData.FAMILYSTATUS, Constants.getStringValueOfJsonObject(dataObject,UserRegData.FAMILYSTATUS,UserRegData.FAMILYSTATUS));
                    userRegData.regDataObject.put(userRegData.FAMILYTYPE, Constants.getStringValueOfJsonObject(dataObject,UserRegData.FAMILYTYPE,UserRegData.FAMILYTYPE));
                    userRegData.regDataObject.put(userRegData.FAMILYVALUES, Constants.getStringValueOfJsonObject(dataObject,UserRegData.FAMILYVALUES,UserRegData.FAMILYVALUES));
                    userRegData.regDataObject.put(userRegData.ABOUT, Constants.getStringValueOfJsonObject(dataObject,UserRegData.ABOUT,UserRegData.ABOUT));

                    userRegData.regDataObject.put(userRegData.PHYSICALSTATUSNAME, Constants.getStringValueOfJsonObject(dataObject,UserRegData.PHYSICALSTATUSNAME,UserRegData.PHYSICALSTATUSNAME));
                    userRegData.regDataObject.put(userRegData.EDUCATIONNAME, Constants.getStringValueOfJsonObject(dataObject,UserRegData.EDUCATIONNAME,UserRegData.EDUCATIONNAME));
                    userRegData.regDataObject.put(userRegData.OCCUPATIONNAME, Constants.getStringValueOfJsonObject(dataObject,UserRegData.OCCUPATIONNAME,UserRegData.OCCUPATIONNAME));
                    userRegData.regDataObject.put(userRegData.EMPLOYEDINNAME, Constants.getStringValueOfJsonObject(dataObject,UserRegData.EMPLOYEDINNAME,UserRegData.EMPLOYEDINNAME));
                    userRegData.regDataObject.put(userRegData.CURRENCYNAME, Constants.getStringValueOfJsonObject(dataObject,UserRegData.CURRENCYNAME,UserRegData.CURRENCYNAME));
                    userRegData.regDataObject.put(userRegData.FAMILYSTATUSNAME, Constants.getStringValueOfJsonObject(dataObject,UserRegData.FAMILYSTATUSNAME,UserRegData.FAMILYSTATUSNAME));
                    userRegData.regDataObject.put(userRegData.FAMILYVALUESNAME, Constants.getStringValueOfJsonObject(dataObject,UserRegData.FAMILYVALUESNAME,UserRegData.FAMILYVALUESNAME));
                    userRegData.regDataObject.put(userRegData.FAMILYTYPENAME, Constants.getStringValueOfJsonObject(dataObject,UserRegData.FAMILYTYPENAME,UserRegData.FAMILYTYPENAME));

                    userRegData.regDataObject.put(userRegData.LATITUDE, Constants.getStringValueOfJsonObject(dataObject,UserRegData.LATITUDE,UserRegData.LATITUDE));
                    userRegData.regDataObject.put(userRegData.LONGITUDE,Constants.getStringValueOfJsonObject(dataObject,UserRegData.LONGITUDE,UserRegData.LONGITUDE));
                    userRegData.regDataObject.put(userRegData.LOCALITY, Constants.getStringValueOfJsonObject(dataObject,UserRegData.LOCALITY,UserRegData.LOCALITY));
                    userRegData.regDataObject.put(userRegData.SUBLOCALITY, Constants.getStringValueOfJsonObject(dataObject,UserRegData.SUBLOCALITY,UserRegData.SUBLOCALITY));
                    userRegData.regDataObject.put(userRegData.ADMINISTRATIVEAREA, Constants.getStringValueOfJsonObject(dataObject,UserRegData.ADMINISTRATIVEAREA,UserRegData.ADMINISTRATIVEAREA));
                    userRegData.regDataObject.put(userRegData.PINCODE, Constants.getStringValueOfJsonObject(dataObject,UserRegData.PINCODE,UserRegData.PINCODE));
                    userRegData.regDataObject.put(userRegData.FORMATTEDADDRESS, Constants.getStringValueOfJsonObject(dataObject,UserRegData.FORMATTEDADDRESS,UserRegData.FORMATTEDADDRESS));

                    sharedPreference.putSharedPrefData(Constants.USERDATA,userRegData.regDataObject.toString());



                    /*
                }
*/

                LoginData.getInstance().setLoginInfoMap(dataMap);
            }

            mVolleyCallbackInterface.successCallBack("success",tag);
        }catch (Exception e)
        {
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }

    public void parseForgotpasswordResponse(String strResponse, String tag)
    {
        String status = "",message = "";
        try
        {
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.has(LoginData.MESSAGE))
            {
                message = Constants.getStringValueOfJsonObject(jsonObject,LoginData.MESSAGE,LoginData.MESSAGE);
                LoginData.getInstance().setForgotPassMessage(message);
            }
            if(jsonObject.has(LoginData.STATUS))
            {
                status = Constants.getStringValueOfJsonObject(jsonObject,LoginData.STATUS,LoginData.STATUS);
                LoginData.getInstance().setForgotPassStatus(status);
            }
            mVolleyCallbackInterface.successCallBack("success",tag);
        }catch (Exception e)
        {
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }
}
