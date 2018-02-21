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
    public static String API = "api";
    public static String DEVICEID = "device";
    public static String VERSION = "version";
    public static String DATA = "data";

    public static String STATUS = "status";
    public static String MESSAGE = "msg";

    public static String PROFILEFOR = "profile_for";
    public static String PROFILEFORNAME = "profile_for_name";
    public static String NAME = "name";
    public static String FULLNAME = "fullname";
    public static String GENDER = "gender";
    public static String DOB = "dob";
    public static String PROFILEID = "profile_id";
    public static String RELIGION = "religion";
    public static String RELIGIONNAME = "religion_name";
    public static String MOTHERTONGUE = "mother_tongue";
    public static String MOTHERTONGUENAME = "mother_tongue_name";
    public static String COUNTRYCODE = "country_code";
    public static String PHONENUMBER = "ph_number";
    public static String EMAIL = "email";
    public static String PASSWORD = "password";
    public static String MARITALSTATUS = "marital_status";
    public static String MARITALSTATUSNAME = "marital_status_name";
    public static String CASTE = "caste";
    public static String CASTENAME = "caste_name";
    public static String DOSHAM = "dosham";
    public static String MARRYOTHERCASTE = "marry_other_caste";
    public static String HEIGHT = "height";
    public static String PHYSICALSTATUS = "physical_status";
    public static String PHYSICALSTATUSNAME = "physical_status_name";
    public static String EDUCATION = "edulevel_id";
    public static String EDUCATIONNAME = "edulevel_id_name";
    public static String OCCUPATION = "occupation_id";
    public static String OCCUPATIONNAME = "occupation_id_name";
    public static String EMPLOYEDIN = "employed_in";
    public static String EMPLOYEDINNAME = "employed_in_name";
    public static String CURRENCY = "currency_id";
    public static String CURRENCYNAME = "currency_id_name";
    public static String ANNUALINCOME = "annual_income";
    public static String FAMILYSTATUS = "family_status";
    public static String FAMILYSTATUSNAME = "family_status_name";
    public static String FAMILYTYPE = "family_type";
    public static String FAMILYTYPENAME = "family_type_name";
    public static String FAMILYVALUES = "family_values";
    public static String FAMILYVALUESNAME = "family_values_name";
    public static String ABOUT = "about";
    public static String LATITUDE = "lat";
    public static String LONGITUDE = "lng";
    public static String LOCALITY = "locality";
    public static String SUBLOCALITY = "sublocality";
    public static String ADMINISTRATIVEAREA = "administrative_area_level_1";
    public static String COUNTRY = "country";
    public static String PINCODE = "pincode";
    public static String FORMATTEDADDRESS = "formatted_address";
    public static String USERID = "id";




    String regStatus;
    public String getRegStatus() {
        return regStatus;
    }

    public void setRegStatus(String regStatus) {
        this.regStatus = regStatus;
    }

    //EDIT PROFILE
    String editProfStatus = "";
    public String getEditProfStatus() {
        return editProfStatus;
    }

    public void setEditProfStatus(String editProfStatus) {
        this.editProfStatus = editProfStatus;
    }

    String editProfMessage = "";
    public String getEditProfMessage() {
        return editProfMessage;
    }

    public void setEditProfMessage(String editProfMessage) {
        this.editProfMessage = editProfMessage;
    }




}
