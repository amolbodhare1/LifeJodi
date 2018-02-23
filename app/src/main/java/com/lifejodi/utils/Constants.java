package com.lifejodi.utils;

import android.Manifest;
import android.util.Base64;

import com.android.volley.AuthFailureError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ajay on 04-12-2017.
 */

public class Constants {

    public static String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE};

    //sharedPref KEYS
    public static String LATITUDE="latitude";
    public static String LONGITUDE="longitude";
    public static String LOCALOTY="locality";
    public static String SUBLOCALITY="sub_locality";
    public static String ADMINISTRATIVEADDR="admin_addr";
    public static String COUNTRY="country";
    public static String PINCODE="pincode";
    public static String REGSTATUS="registration_status";
    public static String FORMATTEDADDR="formatted_addr";
    public static String LOCATION_DATA_EXTRA = "location_data";
    public static String RECEIVER = "receiver";
    public static String RESULT_DATA_KEY = "address_result";
    public static int FAILURE_RESULT = 1;
    public static int  SUCCESS_RESULT =0;
    public static String  USERID ="userid";
    public static String  USERDATA ="user_data";


    public static String PROFILEFOR="profile_for";
    public static String SAVEDEMAIL="saved_email";
    public static String SAVEDPASSWORD="saved_password";
    public static String SAVEDMOBILE="saved_mobile";
    public static String LOGINSTATUS="login_status";
    public static String FACEBOOKSIGNUP="fb_signup";
    public static String FBNAME="fb_name";
    public static String FBEMAIL="fb_email";
    public static String FBGENDER="fb_gender";
    public static String USERNAME="username";

    public static String LOGINNAME="login_name";
    public static String LOGINEMAIL="login_email";
    public static String PROFILEID="profile_id";
    public static String UID="uid";

    //GET MASTERS DATA
    public static String URL_GET_MASTERSDATA = "http://www.digiinterface.com/demos/lifejodi/web_services/Lifejodiapi";
    public static final String TAG_GET_MASTERS = "get_masters";

    //REGISTER USER
    public static String URL_REGISTER_USER = "http://www.digiinterface.com/demos/lifejodi/web_services/Lifejodiapi";
    public static final String TAG_REGISTER_USER = "register_user";

    //LOGIN
    public static String URL_LOGIN = "http://www.digiinterface.com/demos/lifejodi/web_services/Lifejodiapi";
    public static final String TAG_LOGIN= "login";

    //FORGOT PASSWORD
    public static String URL_FORGOTPASSWORD = "http://www.digiinterface.com/demos/lifejodi/web_services/Lifejodiapi";
    public static final String TAG_FORGOTPASSWORD= "forgot_password";

    //HOME MATCHES
    public static String URL_HOME_MATCHES = "http://www.digiinterface.com/demos/lifejodi/web_services/Lifejodiapi";
    public static final String TAG_HOME_MATCHES= "home_matches";

    //HOME NEW MATCHES
    public static String URL_HOME_NEWMATCHES = "http://www.digiinterface.com/demos/lifejodi/web_services/Lifejodiapi";
    public static final String TAG_HOME_NEWMATCHES= "home_new_matches";

    //SHORTLISTUSER
    public static String URL_SHORTLIST_USER = "http://www.digiinterface.com/demos/lifejodi/web_services/Lifejodiapi";
    public static final String TAG_SHORTLIST_USER= "shortlist_user";

    //SHORTLISTUSERs LIST
    public static String URL_SHORTLISTED_USERSLIST = "http://www.digiinterface.com/demos/lifejodi/web_services/Lifejodiapi";
    public static final String TAG_SHORTLISTED_USERSLIST= "shortlistes_users_list";


    //SHORTLISTUSERs LIST
    public static String URL_SHORTLISTED_USERSDETAILS = "http://www.digiinterface.com/demos/lifejodi/web_services/Lifejodiapi";
    public static final String TAG_SHORTLISTED_USERSDETAILS= "shortlisted_details";

    //DAILY RECOMMENDATIONS
    public static String URL_DAILY_RECOMMENDATIONS = "http://www.digiinterface.com/demos/lifejodi/web_services/Lifejodiapi";
    public static final String TAG_DAILY_RECOMMENDATIONS= "daily_recommendations";

    //UPLOAD PROFILE PIC
    public static String URL_UPLOAD_PROF_PIC = "http://www.digiinterface.com/demos/lifejodi/web_services/Lifejodiapi";
    public static final String TAG_UPLOAD_PROFILE_PIC= "upload_prof_pic";

    public static Map<String, String> getHeader() throws AuthFailureError {
        Map<String,String> headers = new HashMap<>();
        String credentials = "admin:1234";
        String auth = "Basic "
                + Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
        headers.put("Authorization", auth);
        headers.put("x-api-key", "123456");
        return headers;
    }
    public static JSONArray getArrayValueFromJsonObject(JSONObject jsonObject, String key) {
        try {
            if (jsonObject.has(key)) {
                if ("".equals(jsonObject.getString(key))) {
                    return null;
                }else if ("".equals(jsonObject.getJSONArray(key))) {
                    return null;
                } else if (null == jsonObject.getJSONArray(key)) {
                    return null;
                } else {
                    return jsonObject.getJSONArray(key);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static String getStringValueOfJsonObject(JSONObject jsonObject, String key, String defaultValue) {
        try {
            if (jsonObject.has(key)) {
                if ("null".equals(jsonObject.getString(key))) {
                    return defaultValue;
                } else if (null == jsonObject.getString(key)) {
                    return defaultValue;
                } else {
                    return jsonObject.getString(key);
                }
            } else {
                return defaultValue;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }


    public static List<String> getArraylistFromArray(String [] arr)
    {
        List<String> dataList = new ArrayList<>();
        dataList = Arrays.asList(arr);
        return dataList;
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }



}
