package com.lifejodi.login.data;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 12/18/2017.
 */

public class RegSpinnersData {

    private static RegSpinnersData registerData = new RegSpinnersData();

    public static RegSpinnersData getInstance()
    {
        return registerData;
    }

    public static String KEY_ISACTIVE = "IsActive";
    public static String KEY_CREATEDON = "CreatedOn";
    public static String KEY_CREATEDBY = "CreatedBy";

    //GET RELIGIONS
    public static String KEY_RELIGIONID = "ReligionID";
    public static String KEY_RELIGIONNAME = "Religion1";

    ArrayList<HashMap<String,String>> religionsList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getReligionsList() {
        return religionsList;
    }

    public void setReligionsList(ArrayList<HashMap<String, String>> religionsList) {
        this.religionsList = religionsList;
    }
    //GET CATSTS
    public static String KEY_CASTID = "CastID";
    public static String KEY_CASTNAME = "Cast";
    ArrayList<HashMap<String,String>> castsList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getCastsList() {
        return castsList;
    }

    public void setCastsList(ArrayList<HashMap<String, String>> castsList) {
        this.castsList = castsList;
    }

    //GET
    public static String KEY_MARITALSTATUSID = "MarritialStatusID";
    public static String KEY_MARITALSTATUSNAME = "MarritalStatus";

    ArrayList<HashMap<String,String>> maritalStatusList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getMaritalStatusList() {
        return maritalStatusList;
    }

    public void setMaritalStatusList(ArrayList<HashMap<String, String>> maritalStatusList) {
        this.maritalStatusList = maritalStatusList;
    }


}
