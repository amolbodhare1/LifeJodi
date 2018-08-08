package com.lifejodi.navigation.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.home.adapters.MatchesRecyclerAdapter;
import com.lifejodi.home.data.HomeFragmentsData;
import com.lifejodi.home.managers.HomeFragmentsManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPref;
import com.lifejodi.utils.customfonts.CustomTextBeatles;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2/10/2018.
 */

public class DailyRecommActivity extends AppCompatActivity implements VolleyCallbackInterface {

    @BindView(R.id.tools_daily_recomm)
    Toolbar toolsDailyRecomm;
    @BindView(R.id.recycler_daily_recomm)
    RecyclerView recyclerDailyRecomm;
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;

    HomeFragmentsData homeFragmentsData = HomeFragmentsData.getInstance();
    HomeFragmentsManager homeFragmentsManager;

    String profId;
    SharedPref sharedPreference = SharedPref.getSharedInstance();
    MatchesRecyclerAdapter matchesRecyclerAdapter;
    GridLayoutManager gridLayoutManager;
    @BindView(R.id.text_no_recommendations)
    CustomTextBeatles textNoRecommendations;
    @BindView(R.id.layout_dailyrecomm_data)
    LinearLayout layoutDailyrecommData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_recommendations);
        ButterKnife.bind(this);
        initialization();
    }

    public void initialization() {
        setSupportActionBar(toolsDailyRecomm);
        toolsDailyRecomm.setNavigationIcon(R.drawable.ic_back);
        toolsDailyRecomm.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle("Daily Recommendations");
        toolsDailyRecomm.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerDailyRecomm.setLayoutManager(gridLayoutManager);
        sharedPreference.initialize(this);
        homeFragmentsManager = HomeFragmentsManager.getInstance();
        homeFragmentsManager.initialize(this, this);
        profId = sharedPreference.getSharedPrefData(Constants.PROFILEID);
        String androidDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        progressLayout.setVisibility(View.VISIBLE);
        homeFragmentsManager.getDailyRecommendations(homeFragmentsManager.getDailyRecommParams(androidDeviceId, profId));
    }

    @Override
    public void successCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_DAILY_RECOMMENDATIONS:
                progressLayout.setVisibility(View.GONE);
                String status = homeFragmentsData.getDailyRecommStatus();
                String message = homeFragmentsData.getDailyRecommMessage();
                if (status.equals("1")) {
                    ArrayList<HashMap<String, String>> dataList = homeFragmentsData.getDailyRecommList();
                    if (dataList.size() > 0) {
                        textNoRecommendations.setVisibility(View.GONE);
                        layoutDailyrecommData.setVisibility(View.VISIBLE);
                        matchesRecyclerAdapter = new MatchesRecyclerAdapter(DailyRecommActivity.this, dataList);
                        recyclerDailyRecomm.setAdapter(matchesRecyclerAdapter);
                    } else {
                        textNoRecommendations.setVisibility(View.VISIBLE);
                        layoutDailyrecommData.setVisibility(View.GONE);
                    }
                } else if (status.equals("0")) {
                    textNoRecommendations.setText(message);
                    textNoRecommendations.setVisibility(View.VISIBLE);
                    layoutDailyrecommData.setVisibility(View.GONE);
                }

                break;
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_DAILY_RECOMMENDATIONS:
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
