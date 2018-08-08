package com.lifejodi.home.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JANHAVI on 2/8/2018.
 */

public class HomeFragmentsData {

    private static HomeFragmentsData homeFragmentsData  = new HomeFragmentsData();

    public static HomeFragmentsData getInstance()
    {
        return homeFragmentsData;
    }


    public static String API = "api";
    public static String DEVICE = "device";
    public static String VERSION = "version";
    public static String DATA = "data";
    public static String PROFILEID = "profile_id";


    public static String MODE = "mode";
    public static String STATUS = "status";
    public static String MESSAGE = "msg";
    public static String ID = "id";
    public static String FULLNAME = "full_name";
    public static String GENDER = "gender";
    public static String DOB = "dob";
    public static String MOTHERTONGUEID = "mother_tongue_id";
    public static String RELIGIONID = "religion_id";
    public static String AGE = "age";
    public static String MOTHERTONGUENAME = "mothertongue_name";
    public static String RELIGION = "religion";
    public static String PHOTONAME = "photo_name";
    public static String PROFILEPIC = "profile_pic";
    public static String FEATURED_PROFILE = "featured_profile";

    public static String USERID = "user_id";

    ArrayList<HashMap<String,String>> matchesList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getMatchesList() {
        return matchesList;
    }

    public void setMatchesList(ArrayList<HashMap<String, String>> matchesList) {
        this.matchesList = matchesList;
    }

    String matchesStatus = "";
    public String getMatchesStatus() {
        return matchesStatus;
    }

    public void setMatchesStatus(String matchesStatus) {
        this.matchesStatus = matchesStatus;
    }

    String matchesMessage = "";
    public String getMatchesMessage() {
        return matchesMessage;
    }

    public void setMatchesMessage(String matchesMessage) {
        this.matchesMessage = matchesMessage;
    }


    //NEW MATCHES
    ArrayList<HashMap<String,String>> newMatchesList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getNewMatchesList() {
        return newMatchesList;
    }

    public void setNewMatchesList(ArrayList<HashMap<String, String>> newMatchesList) {
        this.newMatchesList = newMatchesList;
    }

    String newMatchesStatus = "";
    public String getNewMatchesStatus() {
        return newMatchesStatus;
    }

    public void setNewMatchesStatus(String newMatchesStatus) {
        this.newMatchesStatus = newMatchesStatus;
    }


    String newMatchesMessage = "";
    public String getNewMatchesMessage() {
        return newMatchesMessage;
    }

    public void setNewMatchesMessage(String newMatchesMessage) {
        this.newMatchesMessage = newMatchesMessage;
    }

    //DAILY RECOMMENDATIONS
    String dailyRecommStatus = "";
    public String getDailyRecommStatus() {
        return dailyRecommStatus;
    }

    public void setDailyRecommStatus(String dailyRecommStatus) {
        this.dailyRecommStatus = dailyRecommStatus;
    }

    String dailyRecommMessage = "";
    public String getDailyRecommMessage() {
        return dailyRecommMessage;
    }

    public void setDailyRecommMessage(String dailyRecommMessage) {
        this.dailyRecommMessage = dailyRecommMessage;
    }


    ArrayList<HashMap<String,String>> dailyRecommList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getDailyRecommList() {
        return dailyRecommList;
    }

    public void setDailyRecommList(ArrayList<HashMap<String, String>> dailyRecommList) {
        this.dailyRecommList = dailyRecommList;
    }

}
