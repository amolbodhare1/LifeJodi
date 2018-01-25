package com.lifejodi.login.activity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.facebook.FacebookSdk;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.lifejodi.R;
import com.lifejodi.login.adapter.CustomViewPagerAdapter;
import com.lifejodi.login.fragments.RegScreen1Fragment;
import com.lifejodi.login.fragments.RegScreen2Fragment;
import com.lifejodi.login.fragments.RegScreen3Fragment;
import com.lifejodi.login.fragments.RegScreen4Fragment;
import com.lifejodi.login.interfaces.SetRegistrationFragment;
import com.lifejodi.login.viewpagers.CustomViewPager;
import com.lifejodi.utils.AppController;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ajay on 11-11-2017.
 */

public class RegisterActivity extends AppCompatActivity implements SetRegistrationFragment, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.view_pager)
    CustomViewPager viewPager;
    CustomViewPagerAdapter customViewPagerAdapter;

    AppController appController;
    GoogleApiClient googleApiClient;
    SharedPreference sharedPreference;

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
        sharedPreference = SharedPreference.getSharedInstance();
        sharedPreference.initialize(this);
        appController = AppController.getInstance();
        appController.initialize(this);
        if(appController.hasPermissions(Constants.PERMISSIONS))
        {
            appController.requestPermission(Constants.PERMISSIONS);
        }
        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();
        customViewPagerAdapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(customViewPagerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void setRegFragment(int pos) {
        viewPager.setCurrentItem(pos);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        @SuppressLint("MissingPermission")
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        double latitude = lastLocation.getLatitude();
        double longitude = lastLocation.getLongitude();
        sharedPreference.putSharedPrefData(Constants.LATITUDE, String.valueOf(latitude));
        sharedPreference.putSharedPrefData(Constants.LONGITUDE, String.valueOf(longitude));


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
