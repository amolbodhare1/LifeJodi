package com.lifejodi.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ajay on 15-03-2017.
 */

public class SharedPref {

    SharedPreferences sd;
    SharedPreferences.Editor ed;
    Context mContext;

    private static SharedPref sharedInstance = new SharedPref();
    public static SharedPref getSharedInstance(){return sharedInstance;};



    public void initialize(Context context)
    {
        mContext = context;
        sd = mContext.getSharedPreferences(Constants.DATABASE_NAME, Context.MODE_PRIVATE);
        ed = sd.edit();
    }

    public void putSharedPrefData(String key, String value)
    {
        ed.putString(key,value);
        ed.commit();
    }

    public String getSharedPrefData(String key)
    {
        String sharedData = sd.getString(key,"");
        return sharedData;
    }

    public void putBoolean(String key, boolean value)
    {
        ed.putBoolean(key,value);
        ed.commit();
    }

    public boolean getBoolean(String key){

        return sd.getBoolean(key,false);
    };



}
