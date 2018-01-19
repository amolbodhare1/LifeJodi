package com.lifejodi.utils;

import android.Manifest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ajay on 04-12-2017.
 */

public class Constants {

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

    //GET RELIGION
    public static String URL_GET_RELIGION = "http://lifejodi.coinage.in/api/MasterData/GetReligion?ID=0";
    public static String TAG_GET_RELIGION = "get_religion";

    //GET CATSTS
    public static String URL_GET_CAST = "http://lifejodi.coinage.in/api/MasterData/GetCasts?ID=0";
    public static  String TAG_GET_CAST = "get_casts";

    //GET MARITAL STATUS
    public static String URL_GET_MARITALSTATUS = "http://lifejodi.coinage.in/api/MasterData/GetMarritalStatus?ID=0";
    public static  String TAG_GET_MARITALSTATUS = "get_marital_status";

    //USER REGISTRATION
    public static String URL_REGISTER_USER = "http://lifejodi.coinage.in/api/UserAccount/RegisterWithDetails";
    public static  String TAG_REGISTER_USER = "register_user";


    public static String[] PERMISSIONS = {
                            /*Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN*/};

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
