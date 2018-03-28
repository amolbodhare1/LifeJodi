package com.lifejodi.search.data;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by parikshit on 27/2/18.
 */

public class SearchData {

    private static SearchData searchData = new SearchData();

    public static SearchData getInstance()
    {
        return searchData;
    }

    public final static String TAG_MARITAL_STATUS = "maritalstatus";
    public final static String TAG_RELIGION = "religion";
    public final static String TAG_MOTHER_TONGUE = "mother_tongue";
    public final static String TAG_CASTE = "caste";
    public final static String TAG_DOSHAM = "dosham";
    public final static String TAG_EDUCATION = "education";
    public final static String TAG_OCCUPATION = "occupation";
    public final static String TAG_COUNTRY = "country";
    public final static String TAG_STATE = "state";
    public final static String TAG_CITY = "city";

    public static ArrayList<HashMap<String,String>> selectedList = new ArrayList<>();

    public static String RELIGION = "religion";
    public static String RELIGIONID = "religion_id";
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
    public static String SEARCH_NOW = "search_now";
    public static String MIN_AGE = "min_age";
    public static String MAX_AGE = "max_age";
    public static String MIN_HEIGHT = "min_height";
    public static String MAX_HEIGHT = "max_height";
    public static String MIN_INCOME = "min_annual_income";
    public static String MAX_INCOME = "max_annual_income";
    public static String CITY = "city";
    public static String STATE = "state";
    public static String SHOW_PROFILE_WITH_PHOTO = "show_profile_with_photo";
    public static String SHOW_PROFILE_WITH_HOROSCOPE = "show_profile_with_horoscope";
    public static String SHOW_PROFILE_WITH_PREMIUM = "show_profile_with_premium";
    public static String DONT_SHOW_CONTACTED = "dont_show_profile_contacted";
    public static String DONT_SHOW_SHORTLISTED = "dont_show_profile_shortlisted";
    public static String DONT_SHOW_IGNORED = "dont_show_profile_ignored";
    public static String DONT_SHOW_VISITED = "dont_show_profile_visited";

    public static String AGE = "age";
    public static String LAT = "lat";
    public static String LNG = "lng";
    public static String SUBLOCALITY = "sublocality";
    public static String LOCALITY = "locality";
    public static String ADMINISTRATIVEAREA = "administrative_area_level_1";
    public static String PINCODE = "pincode";
    public static String FORMATTEDADRRESS = "formatted_address";
    public static String ID = "id";
    public static String PROFILEID = "profile_id";
    public static String PROFILEFOR = "profile_for";
    public static String FULLNAME = "full_name";
    public static String GENDER = "gender";
    public static String DOB = "dob";
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
    public static String PROFILEPIC = "profile_pic";


    public static String GETSAVEDSEARCHES = "get_saved_searches";
    public static String SAVESEARCH = "save_search";
    public static String SEARCHNAME = "search_name";
    public static String ADDDATE = "add_date";
    public static String SAVEDSEARCHOBJECT = "saved_search_object";


    public static String STATUS = "status";
    public static String MESSAGE = "msg";
    public static String DATA = "data";

    //SEARCH BY ID
    String searchByIdStatus = "";
    public String getSearchByIdStatus() {
        return searchByIdStatus;
    }

    public void setSearchByIdStatus(String searchByIdStatus) {
        this.searchByIdStatus = searchByIdStatus;
    }

    String searchByIdMessage = "";
    public String getSearchByIdMessage() {
        return searchByIdMessage;
    }

    public void setSearchByIdMessage(String searchByIdMessage) {
        this.searchByIdMessage = searchByIdMessage;
    }


    ArrayList<HashMap<String,String>> searchByIdsList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getSearchByIdsList() {
        return searchByIdsList;
    }

    public void setSearchByIdsList(ArrayList<HashMap<String, String>> searchByIdsList) {
        this.searchByIdsList = searchByIdsList;
    }


    //CUSTOM SEARCH
    String customSearchStatus="";
    public String getCustomSearchStatus() {
        return customSearchStatus;
    }

    public void setCustomSearchStatus(String customSearchStatus) {
        this.customSearchStatus = customSearchStatus;
    }


    String customSearchMessage="";
    public String getCustomSearchMessage() {
        return customSearchMessage;
    }

    public void setCustomSearchMessage(String customSearchMessage) {
        this.customSearchMessage = customSearchMessage;
    }


    ArrayList<HashMap<String,String>> customSearchList = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getCustomSearchList() {
        return customSearchList;
    }

    public void setCustomSearchList(ArrayList<HashMap<String, String>> customSearchList) {
        this.customSearchList = customSearchList;
    }



    //SAVE SEARCH
    String saveSearchStatus="";

    public String getSaveSearchStatus() {
        return saveSearchStatus;
    }

    public void setSaveSearchStatus(String saveSearchStatus) {
        this.saveSearchStatus = saveSearchStatus;
    }

    String saveSearchMessage="";
    public String getSaveSearchMessage() {
        return saveSearchMessage;
    }

    public void setSaveSearchMessage(String saveSearchMessage) {
        this.saveSearchMessage = saveSearchMessage;
    }


    //GET SAVED SEARCH
    String getSavedSearchStatus = "";

    public String getGetSavedSearchStatus() {
        return getSavedSearchStatus;
    }

    public void setGetSavedSearchStatus(String getSavedSearchStatus) {
        this.getSavedSearchStatus = getSavedSearchStatus;
    }



    String getSavedSearchMessage="";
    public String getGetSavedSearchMessage() {
        return getSavedSearchMessage;
    }

    public void setGetSavedSearchMessage(String getSavedSearchMessage) {
        this.getSavedSearchMessage = getSavedSearchMessage;
    }

    ArrayList<HashMap<String,Object>> savedSearchesList = new ArrayList<>();

    public ArrayList<HashMap<String,Object>> getSavedSearchesList() {
        return savedSearchesList;
    }

    public void setSavedSearchesList(ArrayList<HashMap<String,Object>> savedSearchesList) {
        this.savedSearchesList = savedSearchesList;
    }


}
