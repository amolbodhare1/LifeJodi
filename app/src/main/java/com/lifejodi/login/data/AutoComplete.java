package com.lifejodi.login.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by parikshit on 16/3/17.
 */

public class AutoComplete {

    private static AutoComplete ourInstance = new AutoComplete();
    public static AutoComplete getInstance()
    {
        return  ourInstance;
    }
    public static ArrayList<HashMap<String,String>> placesList = new ArrayList<>();

    public static String KEY_PLACE_ADDRESS = "description";
    public static String KEY_PLACE_ID = "place_id";

    public static ArrayList<HashMap<String, String>> getPlacesList() {
        return placesList;
    }

    public static void setPlacesList(ArrayList<HashMap<String, String>> placesList) {
        AutoComplete.placesList = placesList;
    }

}
