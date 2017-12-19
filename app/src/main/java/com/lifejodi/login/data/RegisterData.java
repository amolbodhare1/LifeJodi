package com.lifejodi.login.data;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 12/18/2017.
 */

public class RegisterData {

    private static RegisterData registerData = new RegisterData();

    public static RegisterData getInstance()
    {
        return registerData;
    }

    public static String KEY_ISACTIVE = "IsActive";
    public static String KEY_CREATEDON = "CreatedOn";
    public static String KEY_CREATEDBY = "CreatedBy";

    //GET RELIGIONS
    public static String KEY_RELIGIONID = "ReligionID";
    public static String KEY_RELIGIONNAME = "Religion1";

    public JSONObject regDataObject = new JSONObject();

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





}
