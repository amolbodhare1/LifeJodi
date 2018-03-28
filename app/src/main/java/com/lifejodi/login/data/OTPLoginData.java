package com.lifejodi.login.data;

import java.util.HashMap;

/**
 * Created by Administrator on 1/25/2018.
 */

public class OTPLoginData {

    private static OTPLoginData otpLoginData = new OTPLoginData();

    public static OTPLoginData getInstance()
    {
        return otpLoginData;
    }

    public static String API = "api";
    public static String DEVICE = "device";
    public static String VERSION = "version";
    public static String DATA = "data";
    public static String MOBILENUM = "mobile_no";

    public static String STATUS = "status";
    public static String MESSAGE = "msg";

    //LOGIN WITH OTP
    public static String HEIGHT = "height";
    public static String AGE = "age";
    public static String LAT = "lat";
    public static String LNG = "lng";
    public static String SUBLOCALITY = "sublocality";
    public static String LOCALITY = "locality";
    public static String ADMINISTRATIVEAREA = "administrative_area_level_1";
    public static String COUNTRY = "country";
    public static String PINCODE = "pincode";
    public static String FORMATTEDADDRESS = "formatted_address";
    public static String USERID = "user_id";
    public static String ID = "id";
    public static String PROFILEID = "profile_id";
    public static String PROFILEFOR = "profile_for";
    public static String FULLNAME = "full_name";
    public static String GENDER = "gender";
    public static String DOB = "dob";
    public static String RELIGION = "religion";
    public static String MOTHERTOUNGUE = "mother_tongue";
    public static String COUNTRYCODE = "country_code";
    public static String PHONENUMBER = "ph_number";
    public static String EMAIL = "email";
    public static String PASSWORD = "password";
    public static String MARITALSTATUS = "marital_status";
    public static String CASTE = "caste";
    public static String DOSHAM = "dosham";
    public static String MARRYOTHERCASTE = "marry_other_caste";
    public static String PHYSICALSTATUS = "physical_status";
    public static String ANNUALINCOME = "annual_income";
    public static String FAMILYSTATUS = "family_status";
    public static String FAMILYTYPE = "family_type";
    public static String CURRENCYID = "currency_id";
    public static String FAMILYVALUES = "family_values";
    public static String COMMENT = "comment";
    public static String EDUCATIONLEVEL = "educationlevel";
    public static String OCCUPATION = "occupation";
    public static String WORKINGAS = "workingas";
    public static String LASTLOGIN = "last_login";
    public static String ALTPHONENUMNER = "alt_ph_number";
    public static String CHILDREN = "children";
    public static String MARRYOTHERCASTENAME = "marry_other_caste_name";
    public static String PROFILEPIC = "profile_pic";
    public static String OTP = "otp";

    //GET OTP
    String optStatus = "";
    public String getOptStatus() {
        return optStatus;
    }

    public void setOptStatus(String optStatus) {
        this.optStatus = optStatus;
    }

    String otpMessage = "";
    public String getOtpMessage() {
        return otpMessage;
    }

    public void setOtpMessage(String otpMessage) {
        this.otpMessage = otpMessage;
    }

    //LOGIN WITH OTP

    HashMap<String,String> otpLoginInfoMap = new HashMap<>();
    public HashMap<String, String> getOtpLoginInfoMap() {
        return otpLoginInfoMap;
    }

    public void setOtpLoginInfoMap(HashMap<String, String> otpLoginInfoMap) {
        this.otpLoginInfoMap = otpLoginInfoMap;
    }


    String otpLoginMessage = "";
    public String getOtpLoginMessage() {
        return otpLoginMessage;
    }

    public void setOtpLoginMessage(String otpLoginMessage) {
        this.otpLoginMessage = otpLoginMessage;
    }

}
