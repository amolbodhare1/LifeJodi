package com.lifejodi.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.facebook.FacebookSdk;
import com.lifejodi.R;
import com.lifejodi.login.adapter.CustomViewPagerAdapter;
import com.lifejodi.login.interfaces.SetRegistrationFragment;
import com.lifejodi.login.viewpagers.CustomViewPager;
import com.lifejodi.utils.AppController;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ajay on 11-11-2017.
 */

public class RegisterActivity extends AppCompatActivity implements SetRegistrationFragment {

    @BindView(R.id.view_pager)
    CustomViewPager viewPager;
    CustomViewPagerAdapter customViewPagerAdapter;

    AppController appController;

    SharedPref sharedPreference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        initialization();


    }

    public void initialization()
    {
        sharedPreference = SharedPref.getSharedInstance();
        sharedPreference.initialize(this);
        appController = AppController.getInstance();
        appController.initialize(this);
        if(appController.hasPermissions(Constants.PERMISSIONS))
        {
            appController.requestPermission(Constants.PERMISSIONS);
        }

        customViewPagerAdapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(customViewPagerAdapter);
    }

    @Override
    public void setRegFragment(int pos) {
        viewPager.setCurrentItem(pos);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            finish();
        }
        return false;
    }
}
