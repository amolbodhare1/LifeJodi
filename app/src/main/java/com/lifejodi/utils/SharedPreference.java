package com.lifejodi.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ajay on 15-03-2017.
 */

public class SharedPreference {

    SharedPreferences sd;
    SharedPreferences.Editor ed;
    Context mContext;

    private static SharedPreference sharedInstance = new SharedPreference();
    public static SharedPreference getSharedInstance(){return sharedInstance;};



    public void initialize(Context context)
    {
        mContext = context;
        sd = mContext.getSharedPreferences("CRICKETDATA", Context.MODE_PRIVATE);
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
}
