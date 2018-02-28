package com.lifejodi.search.data;

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
    public static String MOTHERTONGUE = "mother_tongue";
    public static String MARITALSTATUS = "marital_status";
    public static String CASTE = "caste";
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
    public static String MAX_HEIGHT = "min_height";
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

}
