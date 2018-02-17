package com.lifejodi.login.data;

import java.util.HashMap;

/**
 * Created by Ajay on 04-12-2017.
 */

public class LoginData {

    private static LoginData loginData = new LoginData();
    public static LoginData getInstance(){return loginData;};

    public static String API = "api";
    public static String DEVICE = "device";
    public static String VERSION = "version";
    public static String DATA = "data";
    public static String EMAIL = "email";
    public static String PASSWORD = "password";

    public static String STATUS = "status";
    public static String MESSAGE = "msg";

    public static String ID = "id";
    public static String CONTACTNAME = "contact_name";
    public static String PROFILEFOR  = "profile_for";
    public static String GENDER = "gender";
    public static String DOB = "dob";
    public static String BIRTHTIME = "birth_time";
    public static String BIRTHPLACE = "birth_place";
    public static String PROFILEID = "profile_id";
    public static String FULLNAME = "full_name";


    String loginmessage="";
    public String getLoginmessage() {
        return loginmessage;
    }

    public void setLoginmessage(String loginmessage) {
        this.loginmessage = loginmessage;
    }

    String loginStatus="";
    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }


    HashMap<String,String> loginInfoMap = new HashMap<>();
    public HashMap<String, String> getLoginInfoMap() {
        return loginInfoMap;
    }

    public void setLoginInfoMap(HashMap<String, String> loginInfoMap) {
        this.loginInfoMap = loginInfoMap;
    }

    //FORGOT PASSWORD

    String forgotPassStatus="";
    public String getForgotPassStatus() {
        return forgotPassStatus;
    }

    public void setForgotPassStatus(String forgotPassStatus) {
        this.forgotPassStatus = forgotPassStatus;
    }

    String forgotPassMessage = "";
    public String getForgotPassMessage() {
        return forgotPassMessage;
    }

    public void setForgotPassMessage(String forgotPassMessage) {
        this.forgotPassMessage = forgotPassMessage;
    }

}
