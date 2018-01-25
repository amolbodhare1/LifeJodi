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

    //GET MOTHERTONGUE
    ArrayList<HashMap<String,String>> motherTongueList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getMotherTongueList() {
        return motherTongueList;
    }

    public void setMotherTongueList(ArrayList<HashMap<String, String>> motherTongueList) {
        this.motherTongueList = motherTongueList;
    }

    //GET HEIGHT
    ArrayList<HashMap<String,String>> heightList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getHeightList() {
        return heightList;
    }

    public void setHeightList(ArrayList<HashMap<String, String>> heightList) {
        this.heightList = heightList;
    }



    //GET EDUCATION
    ArrayList<HashMap<String,String>> educationList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getEducationList() {
        return educationList;
    }

    public void setEducationList(ArrayList<HashMap<String, String>> educationList) {
        this.educationList = educationList;
    }

    //GET OCCUPATION
    ArrayList<HashMap<String,String>> occupationList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getOccupationList() {
        return occupationList;
    }

    public void setOccupationList(ArrayList<HashMap<String, String>> occupationList) {
        this.occupationList = occupationList;
    }


    //GET CURRENCY
    ArrayList<HashMap<String,String>> currencyList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(ArrayList<HashMap<String, String>> currencyList) {
        this.currencyList = currencyList;
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
