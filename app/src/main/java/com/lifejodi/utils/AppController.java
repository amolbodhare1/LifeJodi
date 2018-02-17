package com.lifejodi.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

/**
 * Created by parikshit on 13/3/17.
 */

public class AppController {

    private long time;
    private int count = 0;
    private Context mContext;
    private static AppController instance = new AppController();
    public static AppController getInstance(){return instance;};

    public void initialize(Context context) {

        this.mContext = context;

    }
    public boolean hasPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && mContext != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(mContext,permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;                }
            }
        }
        return true;
    }

    public void requestPermission(String[] permissions){
        ActivityCompat.requestPermissions((Activity) mContext, permissions, 1);
    }

    public boolean isConnected(){

        ConnectivityManager cm =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    public void doubleTapToExit(Context context){

        this.mContext = context;
        if (count == 1) {
            long diffInMs = System.currentTimeMillis() - time;
            long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);
            if (diffInSec < 15) {
                ((Activity)mContext).finish();
                count = 0;
            } else {
                count = 0;
            }
        }else if (count == 0) {
            time = System.currentTimeMillis();
            Toast.makeText(mContext, "press again to exit", Toast.LENGTH_LONG).show();
            count++;
        }

    }
}
