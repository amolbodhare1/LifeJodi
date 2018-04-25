package com.lifejodi.radarsearch.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.radarsearch.data.RadarSearchData;
import com.lifejodi.radarsearch.managers.RadarSearchManager;
import com.lifejodi.search.data.SearchData;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.smasini.radar.RadarPoint;
import it.smasini.radar.RadarView;


/**
 * Created by Administrator on 3/31/2018.
 */

public class RadarSearchActivity extends AppCompatActivity implements VolleyCallbackInterface {


    @BindView(R.id.toolbar_radarsearch)
    Toolbar toolbarRadarsearch;

    RadarSearchData radarSearchData = RadarSearchData.getInstance();
    RadarSearchManager radarSearchManager;

    SharedPreference sharedPreference;
    @BindView(R.id.radar_view)
    RadarView radarView;

    String latitude="",longitude="";
    ArrayList<RadarPoint> points = new ArrayList<RadarPoint>();
    ArrayList<HashMap<String,String>> dataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_search);
        ButterKnife.bind(this);
        initialization();
    }

    public void initialization() {
        toolbarRadarsearch.setTitle("Radar Search");
        toolbarRadarsearch.setTitleTextColor(Color.WHITE);
        toolbarRadarsearch.setNavigationIcon(R.drawable.ic_back);
        toolbarRadarsearch.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        sharedPreference = SharedPreference.getSharedInstance();
        sharedPreference.initialize(this);
        String userData = sharedPreference.getSharedPrefData(Constants.USERDATA);
        String userId = Constants.getValue(userData, SearchData.USERID);
        String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        String latitude = Constants.getValue(userData, UserRegData.LATITUDE);
        String longitude = Constants.getValue(userData, UserRegData.LONGITUDE);

        radarSearchManager = RadarSearchManager.getInstance();
        radarSearchManager.initialize(this, this);
        radarSearchManager.getRadarSearchList(radarSearchManager.getRadarSearchInputs(deviceId, userId, latitude, longitude, "10"));

        startAll();
    }


    @Override
    public void successCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_RADAR_SEARCH:

                radarView.setReferencePoint(new RadarPoint("center", Float.parseFloat(latitude),Float.parseFloat(longitude)));
                dataList = radarSearchData.getRadarSearchList();
                for(int i=0;i<dataList.size();i++)
                {
                    HashMap<String,String> dataMap = dataList.get(i);
                    RadarPoint r1 = new RadarPoint("identifier1", Float.parseFloat(dataMap.get(RadarSearchData.LAT)),Float.parseFloat(dataMap.get(RadarSearchData.LNG)), dataMap.get(RadarSearchData.PROFILEPIC));
                    points.add(r1);
                }
                radarView.setPoints(points);
                break;
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_RADAR_SEARCH:
                Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }



    private void startAll(){
        radarView.resetPoints();
        radarView.startAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String userData = sharedPreference.getSharedPrefData(Constants.USERDATA);
                String userId = Constants.getValue(userData, SearchData.USERID);
                String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                latitude = Constants.getValue(userData, UserRegData.LATITUDE);
                longitude = Constants.getValue(userData, UserRegData.LONGITUDE);

                radarView.setMaxDistance(5000);
                radarSearchManager = RadarSearchManager.getInstance();
                radarSearchManager.initialize(RadarSearchActivity.this, RadarSearchActivity.this);
                radarSearchManager.getRadarSearchList(radarSearchManager.getRadarSearchInputs(deviceId, userId, latitude, longitude, "10"));

            }
        }, 10000);
    }
}
