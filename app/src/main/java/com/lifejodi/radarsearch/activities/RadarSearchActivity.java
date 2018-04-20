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
    @BindView(R.id.radar_view)
    RadarView radarView;
    private ArrayList<RadarPoint> points = new ArrayList<RadarPoint>();
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_radar_search);
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


    }


    @Override
    public void successCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_RADAR_SEARCH:
                startAll();
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

    private void startAll() {
        points = new ArrayList<RadarPoint>();
        radarView.resetPoints();
        radarView.startAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RadarPoint r1 = new RadarPoint("identifier1", 44.139175f, 12.247117f, "http://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg");
                RadarPoint r2 = new RadarPoint("identifier2", 44.138205f, 12.248533f, "http://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg");
                RadarPoint r3 = new RadarPoint("identifier3", 44.137265f, 12.250056f, "http://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg");
                RadarPoint r4 = new RadarPoint("identifier4", 44.134374f, 12.251215f, "http://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg");
                RadarPoint r5 = new RadarPoint("identifier5", 44.132491f, 12.248833f, "http://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg");
                RadarPoint r6 = new RadarPoint("identifier6", 44.130676f, 12.248908f, "http://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg");
                RadarPoint r7 = new RadarPoint("identifier7", 44.128889f, 12.248286f, "http://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg");
                RadarPoint r8 = new RadarPoint("identifier8", 44.124769f, 12.242053f, "http://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg");
                RadarPoint r9 = new RadarPoint("identifier9", 44.118592f, 12.242053f, "http://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg");
                RadarPoint r10 = new RadarPoint("identifier10", 44.116289f, 12.240840f, "http://x1.xingassets.com/assets/frontend_minified/img/users/nobody_m.original.jpg");

                points.add(r1);
                points.add(r2);
                points.add(r3);
                points.add(r4);
                points.add(r5);
                points.add(r6);
                points.add(r7);
                points.add(r8);
                points.add(r9);
                points.add(r10);

                radarView.setPoints(points);

            }
        }, 10000);
    }
}
