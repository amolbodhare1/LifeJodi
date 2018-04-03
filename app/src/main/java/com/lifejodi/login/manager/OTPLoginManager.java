package com.lifejodi.login.manager;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.lifejodi.login.data.LoginData;
import com.lifejodi.login.data.OTPLoginData;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Administrator on 1/25/2018.
 */

public class OTPLoginManager implements VolleyResponse {
    private VolleyRequest mVolleyRequest;
    private VolleyCallbackInterface mVolleyCallbackInterface;

    SharedPreference sharedPreference = SharedPreference.getSharedInstance();
    UserRegData userRegData = UserRegData.getInstance();

    private static OTPLoginManager otpLoginManager = new OTPLoginManager();

    public static OTPLoginManager getInstance()
    {
        return otpLoginManager;
    }

    public OTPLoginManager()
    {
        mVolleyRequest = new VolleyRequest(this);
    }
    public void initialize(VolleyCallbackInterface VolleyCallbackInterface, Context mContext) {
        this.mVolleyCallbackInterface = VolleyCallbackInterface;
        mVolleyRequest.setContext(mContext);
    }

    //GET OTP
    public JSONObject getOtpInputParams(String mobileNum,String devId)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(OTPLoginData.API,"sendOtp");
            jsonObject.put(OTPLoginData.DEVICE,devId);
            jsonObject.put(OTPLoginData.VERSION,"1.0");

            JSONObject dataObject = new JSONObject();
            dataObject.put(OTPLoginData.MOBILENUM,mobileNum);

            jsonObject.put(OTPLoginData.DATA,dataObject);
            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void getOTP(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST,Constants.URL_OTP,Constants.TAG_OTP,jsonObject);
    }

    //LOGIN WITH OTP
    public JSONObject getOtpLoginInputParams(String otp,String devId)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(OTPLoginData.API,"loginWithOtp");
            jsonObject.put(OTPLoginData.DEVICE,devId);
            jsonObject.put(OTPLoginData.VERSION,"1.0");

            JSONObject dataObject = new JSONObject();
            dataObject.put(OTPLoginData.OTP,otp);

            jsonObject.put(OTPLoginData.DATA,dataObject);
            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void loginWithOTP(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST,Constants.URL_LOGIN_WITH_OTP,Constants.TAG_LOGIN_WITH_OTP,jsonObject);
    }
    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {

    }

    @Override
    public void getResponse(String strResponse, String tag) {
        switch (tag)
        {
            case Constants.TAG_OTP:
                parsegetOTPResponse(strResponse,tag);
                break;
            case Constants.TAG_LOGIN_WITH_OTP:
                parseLoginWithOtpResponse(strResponse,tag);
                break;
        }
    }

    @Override
    public void error(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_OTP:
                mVolleyCallbackInterface.errorCallBack(msg,tag);
                break;
            case Constants.TAG_LOGIN_WITH_OTP:
                mVolleyCallbackInterface.errorCallBack(msg,tag);
                break;
        }
    }

    public void parsegetOTPResponse(String strResponse, String tag)
    {
        String status = "",message="";
        try
        {
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.has(OTPLoginData.STATUS))
            {
                status = Constants.getStringValueOfJsonObject(jsonObject,OTPLoginData.STATUS,OTPLoginData.STATUS);
            }
            if(jsonObject.has(OTPLoginData.MESSAGE))
            {
                message = Constants.getStringValueOfJsonObject(jsonObject,OTPLoginData.MESSAGE,OTPLoginData.MESSAGE);
            }
            if(jsonObject.has(OTPLoginData.DATA))
            {

            }

            OTPLoginData.getInstance().setOptStatus(status);
            OTPLoginData.getInstance().setOtpMessage(message);
            mVolleyCallbackInterface.successCallBack("success",tag);
        }catch (Exception e)
        {
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }


    public void parseLoginWithOtpResponse(String strResponse, String tag)
    {
        HashMap<String,String> dataMap =new HashMap<>();
        String message = "",status="";
        try
        {
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.has(OTPLoginData.MESSAGE))
            {
                message =Constants.getStringValueOfJsonObject(jsonObject,OTPLoginData.MESSAGE,OTPLoginData.MESSAGE);
            }
            if(jsonObject.has(OTPLoginData.STATUS))
            {
                status =Constants.getStringValueOfJsonObject(jsonObject,OTPLoginData.STATUS,OTPLoginData.STATUS);
            }
            if(jsonObject.has(OTPLoginData.DATA))
            {
                JSONObject dataObject = jsonObject.getJSONObject(OTPLoginData.DATA);

                dataMap.put(OTPLoginData.HEIGHT,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.HEIGHT,OTPLoginData.HEIGHT));
                dataMap.put(OTPLoginData.AGE,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.AGE,OTPLoginData.AGE));
                dataMap.put(OTPLoginData.LAT,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.LAT,OTPLoginData.LAT));
                dataMap.put(OTPLoginData.LNG,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.LNG,OTPLoginData.LNG));
                dataMap.put(OTPLoginData.SUBLOCALITY,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.SUBLOCALITY,OTPLoginData.SUBLOCALITY));
                dataMap.put(OTPLoginData.LOCALITY,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.LOCALITY,OTPLoginData.LOCALITY));
                dataMap.put(OTPLoginData.ADMINISTRATIVEAREA,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.ADMINISTRATIVEAREA,OTPLoginData.ADMINISTRATIVEAREA));
                dataMap.put(OTPLoginData.COUNTRY,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.COUNTRY,OTPLoginData.COUNTRY));
                dataMap.put(OTPLoginData.PINCODE,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.PINCODE,OTPLoginData.PINCODE));
                dataMap.put(OTPLoginData.FORMATTEDADDRESS,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.FORMATTEDADDRESS,OTPLoginData.FORMATTEDADDRESS));
                dataMap.put(OTPLoginData.USERID,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.USERID,OTPLoginData.USERID));
                dataMap.put(OTPLoginData.ID,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.ID,OTPLoginData.ID));
                dataMap.put(OTPLoginData.PROFILEID,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.PROFILEID,OTPLoginData.PROFILEID));
                dataMap.put(OTPLoginData.PROFILEFOR,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.PROFILEFOR,OTPLoginData.PROFILEFOR));
                dataMap.put(OTPLoginData.FULLNAME,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.FULLNAME,OTPLoginData.FULLNAME));
                dataMap.put(OTPLoginData.GENDER,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.GENDER,OTPLoginData.GENDER));
                dataMap.put(OTPLoginData.DOB,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.DOB,OTPLoginData.DOB));
                dataMap.put(OTPLoginData.RELIGION,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.RELIGION,OTPLoginData.RELIGION));
                dataMap.put(OTPLoginData.MOTHERTOUNGUE,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.MOTHERTOUNGUE,OTPLoginData.MOTHERTOUNGUE));
                dataMap.put(OTPLoginData.COUNTRYCODE,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.COUNTRYCODE,OTPLoginData.COUNTRYCODE));

                dataMap.put(OTPLoginData.PHONENUMBER,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.PHONENUMBER,OTPLoginData.PHONENUMBER));
                dataMap.put(OTPLoginData.EMAIL,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.EMAIL,OTPLoginData.EMAIL));
                dataMap.put(OTPLoginData.PASSWORD,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.PASSWORD,OTPLoginData.PASSWORD));
                dataMap.put(OTPLoginData.MARITALSTATUS,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.MARITALSTATUS,OTPLoginData.MARITALSTATUS));
                dataMap.put(OTPLoginData.CASTE,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.CASTE,OTPLoginData.CASTE));
                dataMap.put(OTPLoginData.DOSHAM,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.DOSHAM,OTPLoginData.DOSHAM));
                dataMap.put(OTPLoginData.MARRYOTHERCASTE,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.MARRYOTHERCASTE,OTPLoginData.MARRYOTHERCASTE));
                dataMap.put(OTPLoginData.PHYSICALSTATUS,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.PHYSICALSTATUS,OTPLoginData.PHYSICALSTATUS));
                dataMap.put(OTPLoginData.ANNUALINCOME,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.ANNUALINCOME,OTPLoginData.ANNUALINCOME));
                dataMap.put(OTPLoginData.FAMILYSTATUS,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.FAMILYSTATUS,OTPLoginData.FAMILYSTATUS));
                dataMap.put(OTPLoginData.FAMILYTYPE,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.FAMILYTYPE,OTPLoginData.FAMILYTYPE));
                dataMap.put(OTPLoginData.CURRENCYID,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.CURRENCYID,OTPLoginData.CURRENCYID));
                dataMap.put(OTPLoginData.FAMILYVALUES,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.FAMILYVALUES,OTPLoginData.FAMILYVALUES));
                dataMap.put(OTPLoginData.COMMENT,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.COMMENT,OTPLoginData.COMMENT));
                dataMap.put(OTPLoginData.EDUCATIONLEVEL,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.EDUCATIONLEVEL,OTPLoginData.EDUCATIONLEVEL));
                dataMap.put(OTPLoginData.OCCUPATION,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.OCCUPATION,OTPLoginData.OCCUPATION));
                dataMap.put(OTPLoginData.WORKINGAS,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.WORKINGAS,OTPLoginData.WORKINGAS));
                dataMap.put(OTPLoginData.LASTLOGIN,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.LASTLOGIN,OTPLoginData.LASTLOGIN));
                dataMap.put(OTPLoginData.ALTPHONENUMNER,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.ALTPHONENUMNER,OTPLoginData.ALTPHONENUMNER));
                dataMap.put(OTPLoginData.CHILDREN,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.CHILDREN,OTPLoginData.CHILDREN));

                dataMap.put(OTPLoginData.MARRYOTHERCASTENAME,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.MARRYOTHERCASTENAME,OTPLoginData.MARRYOTHERCASTENAME));
                dataMap.put(OTPLoginData.PROFILEPIC,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.PROFILEPIC,OTPLoginData.PROFILEPIC));



                userRegData.regDataObject = new JSONObject();

                userRegData.regDataObject.put(UserRegData.USERID,Constants.getStringValueOfJsonObject(dataObject,UserRegData.USERID,UserRegData.USERID));
                userRegData.regDataObject.put(UserRegData.EMAIL,Constants.getStringValueOfJsonObject(dataObject,UserRegData.EMAIL,UserRegData.EMAIL));
                userRegData.regDataObject.put(UserRegData.PHONENUMBER,Constants.getStringValueOfJsonObject(dataObject,UserRegData.PHONENUMBER,UserRegData.PHONENUMBER));
                userRegData.regDataObject.put(UserRegData.PROFILEFOR,Constants.getStringValueOfJsonObject(dataObject,UserRegData.PROFILEFOR,UserRegData.PROFILEFOR));
                userRegData.regDataObject.put(UserRegData.GENDER,Constants.getStringValueOfJsonObject(dataObject,UserRegData.GENDER,UserRegData.GENDER));
                userRegData.regDataObject.put(UserRegData.DOB,Constants.getStringValueOfJsonObject(dataObject,UserRegData.DOB,UserRegData.DOB));
                userRegData.regDataObject.put(UserRegData.PROFILEID,Constants.getStringValueOfJsonObject(dataObject,UserRegData.PROFILEID,UserRegData.PROFILEID));
                userRegData.regDataObject.put(UserRegData.FULLNAME,Constants.getStringValueOfJsonObject(dataObject,UserRegData.FULLNAME,UserRegData.FULLNAME));

                userRegData.regDataObject.put(userRegData.HEIGHT, Constants.getStringValueOfJsonObject(dataObject,UserRegData.HEIGHT,UserRegData.HEIGHT));
                userRegData.regDataObject.put(userRegData.PHYSICALSTATUS, Constants.getStringValueOfJsonObject(dataObject,UserRegData.PHYSICALSTATUS,UserRegData.PHYSICALSTATUS));
                userRegData.regDataObject.put(userRegData.MARITALSTATUS, Constants.getStringValueOfJsonObject(dataObject,UserRegData.MARITALSTATUS,UserRegData.MARITALSTATUS));
                userRegData.regDataObject.put(userRegData.MOTHERTONGUE, Constants.getStringValueOfJsonObject(dataObject,UserRegData.MOTHERTONGUE,UserRegData.MOTHERTONGUE));
                userRegData.regDataObject.put(userRegData.CURRENCY, Constants.getStringValueOfJsonObject(dataObject,UserRegData.CURRENCY,UserRegData.CURRENCY));
                userRegData.regDataObject.put(userRegData.ANNUALINCOME, Constants.getStringValueOfJsonObject(dataObject,UserRegData.ANNUALINCOME,UserRegData.ANNUALINCOME));
                userRegData.regDataObject.put(userRegData.FAMILYSTATUS, Constants.getStringValueOfJsonObject(dataObject,UserRegData.FAMILYSTATUS,UserRegData.FAMILYSTATUS));
                userRegData.regDataObject.put(userRegData.FAMILYTYPE, Constants.getStringValueOfJsonObject(dataObject,UserRegData.FAMILYTYPE,UserRegData.FAMILYTYPE));
                userRegData.regDataObject.put(userRegData.FAMILYVALUES, Constants.getStringValueOfJsonObject(dataObject,UserRegData.FAMILYVALUES,UserRegData.FAMILYVALUES));
                userRegData.regDataObject.put(userRegData.ABOUT_COMMENT, Constants.getStringValueOfJsonObject(dataObject,UserRegData.ABOUT,UserRegData.ABOUT));

                userRegData.regDataObject.put(userRegData.RELIGION, Constants.getStringValueOfJsonObject(dataObject,UserRegData.RELIGION,UserRegData.RELIGION));
                userRegData.regDataObject.put(userRegData.CASTE, Constants.getStringValueOfJsonObject(dataObject,UserRegData.CASTE,UserRegData.CASTE));
                userRegData.regDataObject.put(userRegData.DOSHAM, Constants.getStringValueOfJsonObject(dataObject,UserRegData.DOSHAM,UserRegData.DOSHAM));
                userRegData.regDataObject.put(userRegData.MARRYOTHERCASTE, Constants.getStringValueOfJsonObject(dataObject,UserRegData.MARRYOTHERCASTE,UserRegData.MARRYOTHERCASTE));

                userRegData.regDataObject.put(userRegData.EDUCATIONNAME, Constants.getStringValueOfJsonObject(dataObject,UserRegData.EDUCATIONNAME,UserRegData.EDUCATIONNAME));
                userRegData.regDataObject.put(userRegData.OCCUPATIONNAME, Constants.getStringValueOfJsonObject(dataObject,UserRegData.OCCUPATIONNAME,UserRegData.OCCUPATIONNAME));
                userRegData.regDataObject.put(userRegData.EMPLOYEDINNAME, Constants.getStringValueOfJsonObject(dataObject,UserRegData.EMPLOYEDINNAME,UserRegData.EMPLOYEDINNAME));

                userRegData.regDataObject.put(userRegData.LATITUDE, Constants.getStringValueOfJsonObject(dataObject,UserRegData.LATITUDE,UserRegData.LATITUDE));
                userRegData.regDataObject.put(userRegData.LONGITUDE,Constants.getStringValueOfJsonObject(dataObject,UserRegData.LONGITUDE,UserRegData.LONGITUDE));
                userRegData.regDataObject.put(userRegData.LOCALITY, Constants.getStringValueOfJsonObject(dataObject,UserRegData.LOCALITY,UserRegData.LOCALITY));
                userRegData.regDataObject.put(userRegData.SUBLOCALITY, Constants.getStringValueOfJsonObject(dataObject,UserRegData.SUBLOCALITY,UserRegData.SUBLOCALITY));
                userRegData.regDataObject.put(userRegData.ADMINISTRATIVEAREA, Constants.getStringValueOfJsonObject(dataObject,UserRegData.ADMINISTRATIVEAREA,UserRegData.ADMINISTRATIVEAREA));
                userRegData.regDataObject.put(userRegData.PINCODE, Constants.getStringValueOfJsonObject(dataObject,UserRegData.PINCODE,UserRegData.PINCODE));
                userRegData.regDataObject.put(userRegData.COUNTRY, Constants.getStringValueOfJsonObject(dataObject,UserRegData.COUNTRY,UserRegData.COUNTRY));
                userRegData.regDataObject.put(userRegData.COUNTRYCODE, Constants.getStringValueOfJsonObject(dataObject,UserRegData.COUNTRYCODE,UserRegData.COUNTRYCODE));
                userRegData.regDataObject.put(userRegData.FORMATTEDADDRESS, Constants.getStringValueOfJsonObject(dataObject,UserRegData.FORMATTEDADDRESS,UserRegData.FORMATTEDADDRESS));
                userRegData.regDataObject.put(userRegData.PROFILEPIC, Constants.getStringValueOfJsonObject(dataObject,UserRegData.PROFILEPIC,UserRegData.PROFILEPIC));



                sharedPreference.putSharedPrefData(Constants.LOGINNAME,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.FULLNAME,OTPLoginData.FULLNAME));
                sharedPreference.putSharedPrefData(Constants.LOGINEMAIL,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.EMAIL,OTPLoginData.EMAIL));
                sharedPreference.putSharedPrefData(Constants.PROFILEID,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.PROFILEID,OTPLoginData.PROFILEID));
                sharedPreference.putSharedPrefData(Constants.UID,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.USERID,OTPLoginData.USERID));
                sharedPreference.putSharedPrefData(Constants.PROFILEPICPATH,Constants.getStringValueOfJsonObject(dataObject,OTPLoginData.PROFILEPIC,OTPLoginData.PROFILEPIC));
                sharedPreference.putSharedPrefData(Constants.USERDATA,userRegData.regDataObject.toString());



            }

            OTPLoginData.getInstance().setOtpLoginInfoMap(dataMap);
            OTPLoginData.getInstance().setOtpLoginMessage(message);
            OTPLoginData.getInstance().setOtpLoginSuccess(status);
            mVolleyCallbackInterface.successCallBack("success",tag);
        }catch (Exception e)
        {
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }

}
