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

    public static String API = "api";
    public static String VERSION = "version";
    public static String DEVICEID = "device_id";
    public static String DATA = "data";


    public static final String RESPONSE = "response";
    public static final String PROFILEFOR = "profile_for";
    public static final String MARITALSTATUS = "marital_status";
    public static final String PHYSICALSTATUS = "physical_status";
    public static final String FAMILYSTATUS = "family_status";
    public static final String FAMILYTYPE = "family_type";
    public static final String FAMILYVALUES = "family_values";
    public static final String RELIGION = "religion";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String ACTIVE = "active";
    public static final String MOTHERTOUNGUE = "mothertongue";
    public static final String CASTE = "caste";
    public static final String RELIGIONID = "religion_id";
    public static final String COUNTRY = "country";
    public static final String COUNTRYCODE = "country_code";
    public static final String HEIGHT = "height";
    public static final String EDUCATION = "education";
    public static final String OCCUPATION = "occupation";
    public static final String CURRENCY = "currency";
    public static final String VALUE = "val";




    //GET PROFILE FOR
    ArrayList<HashMap<String,String>> profileForList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getProfileForList() {
        return profileForList;
    }

    public void setProfileForList(ArrayList<HashMap<String, String>> profileForList) {

        this.profileForList = profileForList;
    }


    //GET MARITAL STATUS LIST
    ArrayList<HashMap<String,String>> maritalStatusList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getMaritalStatusList() {
        return maritalStatusList;
    }

    public void setMaritalStatusList(ArrayList<HashMap<String, String>> maritalStatusList) {
        this.maritalStatusList = maritalStatusList;
    }

    //GET PHYSICAL STATUS
    ArrayList<HashMap<String,String>> physicalStatusList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getPhysicalStatusList() {
        return physicalStatusList;
    }

    public void setPhysicalStatusList(ArrayList<HashMap<String, String>> physicalStatusList) {
        this.physicalStatusList = physicalStatusList;
    }


    //GET RELIGIONS
    ArrayList<HashMap<String,String>> religionsList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getReligionsList() {
        return religionsList;
    }

    public void setReligionsList(ArrayList<HashMap<String, String>> religionsList) {
        this.religionsList = religionsList;
    }

    //GET CATSTS
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
    ArrayList<String> heightList = new ArrayList<>();
    public ArrayList<String> getHeightList() {
        return heightList;
    }

    public void setHeightList(ArrayList<String> heightList) {
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

    //GET COUNTRIES
    ArrayList<HashMap<String,String>> countriesList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getCountriesList() {
        return countriesList;
    }

    public void setCountriesList(ArrayList<HashMap<String, String>> countriesList) {
        this.countriesList = countriesList;
    }

    //GET FAMILY STATUS
    ArrayList<HashMap<String,String>> familyStatus = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getFamilyStatus() {
        return familyStatus;
    }

    public void setFamilyStatus(ArrayList<HashMap<String, String>> familyStatus) {
        this.familyStatus = familyStatus;
    }

    //GET FAMILY TYPE
    ArrayList<HashMap<String,String>> familyType = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getFamilyType() {
        return familyType;
    }

    public void setFamilyType(ArrayList<HashMap<String, String>> familyType) {
        this.familyType = familyType;
    }


    //GET FAMILY VALUES
    ArrayList<HashMap<String,String>> familyValues = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getFamilyValues() {
        return familyValues;
    }

    public void setFamilyValues(ArrayList<HashMap<String, String>> familyValues) {
        this.familyValues = familyValues;
    }



    //GET DOSHAM
    ArrayList<HashMap<String,String>> doshamList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getDoshamList() {
        return doshamList;
    }

    public void setDoshamList(ArrayList<HashMap<String, String>> doshamList) {
        this.doshamList = doshamList;
    }



}
