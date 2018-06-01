package com.lifejodi.home.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JANHAVI on 2/8/2018.
 */

public class ShortlistData {

    private static ShortlistData shortlistedData = new ShortlistData();

    public static ShortlistData getInstance()
    {
        return shortlistedData;
    }

    public static String API = "api";
    public static String DEVICE = "device";
    public static String VERSION = "version";
    public static String DATA = "data";
    public static String PROFILEID = "profile_id";
    public static String USERID = "user_id";

    public static String STATUS = "status";
    public static String MESSAGE = "msg";
    public static String MODE = "mode";

    public static String ID = "id";
    public static String FULLNAME = "full_name";
    public static String GENDER = "gender";
    public static String DOB = "dob";
    public static String MOTHERTOUNGUEID = "mother_tongue_id";
    public static String RELIGIONID = "religion_id";
    public static String AGE = "age";
    public static String MOTHERTOUNGUENAME = "mothertongue_name";
    public static String RELIGION = "religion";
    public static String PHOTONAME = "photo_name";
    public static String PROFILEPIC = "profile_pic";

    String shortlistingStatus = "";
    public String getShortlistingStatus() {
        return shortlistingStatus;
    }

    public void setShortlistingStatus(String shortlistingStatus) {
        this.shortlistingStatus = shortlistingStatus;
    }

    String shortlistingMessage = "";
    public String getShortlistingMessage() {
        return shortlistingMessage;
    }

    public void setShortlistingMessage(String shortlistingMessage) {
        this.shortlistingMessage = shortlistingMessage;
    }


    ArrayList<HashMap<String,String>> shortListedUsersList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getShortListedUsersList() {
        return shortListedUsersList;
    }

    public void setShortListedUsersList(ArrayList<HashMap<String, String>> shortListedUsersList) {
        this.shortListedUsersList = shortListedUsersList;
    }

    String shortListedUsersStatus = "";
    public String getShortListedUsersStatus() {
        return shortListedUsersStatus;
    }

    public void setShortListedUsersStatus(String shortListedUsersStatus) {
        this.shortListedUsersStatus = shortListedUsersStatus;
    }

    String shortListedUsersMessage = "";
    public String getShortListedUsersMessage() {
        return shortListedUsersMessage;
    }

    public void setShortListedUsersMessage(String shortListedUsersMessage) {
        this.shortListedUsersMessage = shortListedUsersMessage;
    }



}
