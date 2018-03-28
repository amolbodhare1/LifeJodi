package com.lifejodi.landingpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.lifejodi.R;
import com.lifejodi.home.activity.HomeActivity;
import com.lifejodi.login.activity.LoginActivity;
import com.lifejodi.login.data.RegSpinnersData;
import com.lifejodi.login.manager.RegSpinnersManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ajay on 11-11-2017.
 */

public class SplashActivity extends AppCompatActivity implements VolleyCallbackInterface {

    SharedPreference sharedPreference = SharedPreference.getSharedInstance();
    RegSpinnersManager regSpinnersManager = RegSpinnersManager.getInstance();
    RegSpinnersData regSpinnersData = RegSpinnersData.getInstance();
    @BindView(R.id.image_splash)
    ImageView imageSplash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        sharedPreference.initialize(this);

        imageSplash.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this,R.anim.translate));
        if (sharedPreference.getSharedPrefData(Constants.REGSTATUS).equals("1")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (sharedPreference.getSharedPrefData(Constants.LOGINSTATUS).equals("1")) {
                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                }
            }, 4000);

        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initialization();
                }
            }, 1000);
        }



    }

    public void initialization() {
        regSpinnersManager.initialize(this, this);
        String androidDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.e("DEVICEID", androidDeviceId);
        if (androidDeviceId != null || androidDeviceId != "") {
            regSpinnersManager.getAllSpinnersData(regSpinnersManager.getAllSpinnersDataInputs(androidDeviceId));
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            String deviceId = telephonyManager.getDeviceId();
            regSpinnersManager.getAllSpinnersData(regSpinnersManager.getAllSpinnersDataInputs(deviceId));
        }
    }

    @Override
    public void successCallBack(String msg, String tag) {
        if (tag.equals(Constants.TAG_GET_MASTERS)) {
            if (sharedPreference.getSharedPrefData(Constants.LOGINSTATUS).equals("1")) {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {

    }
}
