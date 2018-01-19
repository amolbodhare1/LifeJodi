package com.lifejodi.login.data;

import org.json.JSONObject;

/**
 * Created by Administrator on 12/19/2017.
 */

public class UserRegData {

    private static UserRegData userRegData = new UserRegData();

    public static UserRegData getInstance()
    {
        return userRegData;
    }

    public JSONObject regDataObject = new JSONObject();
    //REGISTER USER
    public static String KEY_USERNAME = "UserName";
    public static String KEY_EMAIL = "Email";
    public static String KEY_FIRSTNAME = "FirstName";
    public static String KEY_LASTNAME = "LastName";
    public static String KEY_MIDDLENAME = "MiddleName";
    public static String KEY_GENDER = "Gender";
    public static String KEY_DOB = "DOB";
    public static String KEY_MARITALSTATUS = "MarritalStatusIDFK";
    public static String KEY_HEIGHT = "Height";
    public static String KEY_WEIGHT = "Weight";
    public static String KEY_CONTACTNUMBER = "ContactNumber";
    public static String KEY_RELIGION = "ReligionIDFK";
    public static String KEY_CAST = "CastIDFK";
    public static String KEY_PROFILEFOR = "ProfileForTypeIDFK";

    public static String KEY_STATUSMESSAGE = "StatusMessage";
    public static String KEY_STATUSCODE = "StatusCode";

    String regStatus;
    public String getRegStatus() {
        return regStatus;
    }

    public void setRegStatus(String regStatus) {
        this.regStatus = regStatus;
    }



}
