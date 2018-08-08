package com.lifejodi.home.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JANHAVI on 2/9/2018.
 */

public class ProfilesData {

    private static ProfilesData profilesData = new ProfilesData();

    public static ProfilesData getInstance()
    {
        return profilesData;
    }

    public static String API = "api";
    public static String DEVICE = "device";
    public static String VERSION = "version";


    public static String DATA = "data";

    public static String STATUS = "status";
    public static String MODE = "mode";
    public static String MESSAGE = "msg";
    public static String USERDATA = "user_data";
    public static String ID = "id";
    public static String PROFILEID = "profile_id";
    public static String FULLNAME = "full_name";
    public static String GENDER = "gender";
    public static String DOB = "dob";
    public static String MOTHERTOUNGUEID = "mother_tongue_id";
    public static String RELIGIONID = "religion_id";
    public static String AGE = "age";
    public static String MOTHERTONGUENAME = "mothertongue_name";
    public static String RELIGION = "religion";
    public static String PHOTONAME = "photo_name";
    public static String PROFILEPIC = "profile_pic";
    public static String LAT = "lat";
    public static String LNG = "lng";
    public static String SUBLOCALITY = "sublocality";
    public static String LOCALITY = "locality";
    public static String ADMINISTRATIVEAREA = "administrative_area_level_1";
    public static String PINCODE = "pincode";
    public static String FORMATTEDADRRESS = "formatted_address";
    public static String PROFILEFOR = "profile_for";
    public static String COUNTRYCODE = "country_code";
    public static String PHNUMBER = "ph_number";
    public static String EMAIL = "email";
    public static String PASSWORD = "password";
    public static String MARRYOTHERCAST = "marry_other_caste";
    public static String CURRENCYID = "currency_id";
    public static String COMMENT = "comment";
    public static String EDUCATIONLEVEL = "educationlevel";
    public static String OCCUPATIONNAME = "occupation";
    public static String WORKINGAS = "workingas";
    public static String MOTHERTONGUE = "mother_tongue";
    public static String MOTHERTONGUEID = "mother_tongue_id";
    public static String MARITALSTATUS = "marital_status";
    public static String CASTE = "caste";
    public static String CASTEID = "caste_id";
    public static String DOSHAM = "dosham";
    public static String HEIGHT = "height";
    public static String PHYSICALSTATUS = "physical_status";
    public static String EDUCATION = "edulevel_id";
    public static String OCCUPATION = "occupation_id";
    public static String EMPLOYEDIN = "employed_in";
    public static String ANNUALINCOME = "annual_income";
    public static String FAMILYSTATUS = "family_status";
    public static String FAMILYTYPE = "family_type";
    public static String FAMILYVALUES = "family_values";
    public static String ABOUT = "about";
    public static String COUNTRY = "country";
    public static String USERID = "user_id";
    public static String SHORTLISTED_STATUS = "shortlist_status";





    public String ShortListedDetailsStatus="";
    public String getShortListedDetailsStatus() {
        return ShortListedDetailsStatus;
    }

    public void setShortListedDetailsStatus(String shortListedDetailsStatus) {
        ShortListedDetailsStatus = shortListedDetailsStatus;
    }

    public ArrayList<HashMap<String,String>> pendingList = new ArrayList<>();
    public ArrayList<HashMap<String,String>> acceptedList = new ArrayList<>();
    public ArrayList<HashMap<String,String>> rejectedList = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getPendingList() {
        return pendingList;
    }

    public void setPendingList(ArrayList<HashMap<String, String>> pendingList) {
        this.pendingList = pendingList;
    }

    public ArrayList<HashMap<String, String>> getAcceptedList() {
        return acceptedList;
    }

    public void setAcceptedList(ArrayList<HashMap<String, String>> acceptedList) {
        this.acceptedList = acceptedList;
    }

    public ArrayList<HashMap<String, String>> getRejectedList() {
        return rejectedList;
    }

    public void setRejectedList(ArrayList<HashMap<String, String>> rejectedList) {
        this.rejectedList = rejectedList;
    }

    public HashMap<String,String> shortListedDetailsMap = new HashMap<>();
    public HashMap<String, String> getShortListedDetailsMap() {
        return shortListedDetailsMap;
    }

    public void setShortListedDetailsMap(HashMap<String, String> shortListedDetailsMap) {
        this.shortListedDetailsMap = shortListedDetailsMap;
    }


}
