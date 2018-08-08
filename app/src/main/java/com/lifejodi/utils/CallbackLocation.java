package com.lifejodi.utils;

import android.location.Location;

/**
 * Created by Ajay on 18-03-2017.
 */

public interface CallbackLocation {

    void onLocationFound(Location mLastLocation);
    void onLocationLost(String s);
}
