package com.lifejodi.radarsearch.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 4/3/2018.
 */

public class RadarSearchData {

    private static RadarSearchData radarSearchData = new RadarSearchData();

    public static RadarSearchData getInstance()
    {
        return radarSearchData;
    }

    public static String API = "api";
    public static String DEVICE = "device";
    public static String VERSION = "version";
    public static String DATA = "data";
    public static String USERID = "user_id";
    public static String LAT = "lat";
    public static String LNG = "lng";
    public static String RADIUS = "radius";

    public static String RESPONSE = "api";
    public static String STATUS = "status";
    public static String MESSAGE = "msg";

    public static String ID = "id";
    public static String PROFILEID = "profile_id";
    public static String FULLNAME = "full_name";
    public static String GENDER = "gender";
    public static String DOB = "dob";
    public static String MOTHERTONGUEID = "mother_tongue_id";
    public static String RELIGIONID = "religion_id";
    public static String AGE = "age";
    public static String MOTHERTONGUENAME = "mothertongue_name";
    public static String RELIGION = "religion";
    public static String PHOTONAME = "photo_name";
    public static String DISTANCE = "distance";
    public static String PROFILEPIC = "profile_pic";

    String radarSearchStatus="";
    public String getRadarSearchStatus() {
        return radarSearchStatus;
    }

    public void setRadarSearchStatus(String radarSearchStatus) {
        this.radarSearchStatus = radarSearchStatus;
    }


    ArrayList<HashMap<String,String>> radarSearchList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getRadarSearchList() {
        return radarSearchList;
    }

    public void setRadarSearchList(ArrayList<HashMap<String, String>> radarSearchList) {
        this.radarSearchList = radarSearchList;
    }


}
