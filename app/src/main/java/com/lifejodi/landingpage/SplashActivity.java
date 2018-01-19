package com.lifejodi.landingpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lifejodi.home.activity.HomeActivity;
import com.lifejodi.login.activity.LoginActivity;
import com.lifejodi.R;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;

/**
 * Created by Ajay on 11-11-2017.
 */

public class SplashActivity extends AppCompatActivity {

    SharedPreference sharedPreference = SharedPreference.getSharedInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreference.initialize(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPreference.getSharedPrefData(Constants.LOGINSTATUS).equals("1")) {
                   Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                   startActivity(intent);
                   finish();
                }else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }
        }, 5000);

    }
}
