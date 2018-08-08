package com.lifejodi.navigation.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.inscripts.interfaces.Callbacks;
import com.lifejodi.R;
import com.lifejodi.navigation.adapter.PackagesAdapter;
import com.lifejodi.navigation.data.PackageData;
import com.lifejodi.navigation.manager.PackageManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPref;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cometchat.inscripts.com.cometchatcore.coresdk.CometChat;

public class PackagesActivity extends AppCompatActivity implements VolleyCallbackInterface, Callbacks {

    @BindView(R.id.toolbar_packages)
    Toolbar toolbarPackages;
    @BindView(R.id.recycler_packages)
    RecyclerView recyclerPackages;

    SharedPref sharedPreference = SharedPref.getSharedInstance();

    String deviceId,userId;
    PackageManager packageManager = PackageManager.getInstance();
    PackageData packageData = PackageData.getInstance();

    CometChat cometChat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);
        ButterKnife.bind(this);
        packageManager.initialize(this,this);
        sharedPreference.initialize(this);

        initialize();

    }

    private void initialize() {

        toolbarPackages.setTitle("Packages");
        toolbarPackages.setTitleTextColor(getResources().getColor(R.color.white));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerPackages.setLayoutManager(layoutManager);

        deviceId = sharedPreference.getSharedPrefData(Constants.DEVICE_ID);
        userId = sharedPreference.getSharedPrefData(Constants.USERID);

        getLifeJodiPackages();



    }

    private void getLifeJodiPackages() {

        packageManager.getAllPackages(packageManager.getAllPackagesInput(deviceId));

    }

    @Override
    public void successCallBack(String msg, String tag) {


        switch (tag){
            case Constants.TAG_ALL_PACKAGES:

                setRecylerView();

                break;
            case Constants.TAG_MY_PACKAGE:

                break;
        }

    }

    private void setRecylerView() {

    recyclerPackages.setAdapter(new PackagesAdapter(this,packageData.getAllPackagesList()));

    }

    @Override
    public void errorCallBack(String msg, String tag) {



    }

    @Override
    public void successCallback(JSONObject jsonObject) {

    }

    @Override
    public void failCallback(JSONObject jsonObject) {

    }
}
