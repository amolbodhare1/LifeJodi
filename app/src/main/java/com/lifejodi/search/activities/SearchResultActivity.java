package com.lifejodi.search.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.search.adapter.CustomSearchListAdapter;
import com.lifejodi.search.data.SearchData;
import com.lifejodi.search.manager.SearchManager;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by parikshit on 28/2/18.
 */

public class SearchResultActivity extends AppCompatActivity implements VolleyCallbackInterface {

    @BindView(R.id.toolbar_search_result)
    Toolbar toolbarSearchResult;
    @BindView(R.id.recycler_search_result)
    RecyclerView recyclerSearchResult;
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;


    SearchManager searchManager = SearchManager.getInstance();
    SearchData searchData = SearchData.getInstance();
    SharedPreference sharedPreference = SharedPreference.getSharedInstance();
    CustomSearchListAdapter customSearchListAdapter;
    @BindView(R.id.layout_customsearch_results)
    LinearLayout layoutCustomsearchResults;
    @BindView(R.id.text_no_search_results)
    TextView textNoSearchResults;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search_result);
        ButterKnife.bind(this);

        initialize();
    }

    private void initialize() {

        toolbarSearchResult.setTitle("Search Result");
        toolbarSearchResult.setTitleTextColor(Color.WHITE);
        toolbarSearchResult.setNavigationIcon(R.drawable.ic_back);
        toolbarSearchResult.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerSearchResult.setLayoutManager(linearLayoutManager);

        sharedPreference.initialize(this);
        searchManager.initialize(SearchResultActivity.this, this);

        String userData = sharedPreference.getSharedPrefData(Constants.USERDATA);
        String userId = Constants.getValue(userData, SearchData.USERID);
        String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        Log.e("DEVICEID", deviceId);

        if (deviceId != null || deviceId != "") {
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            deviceId = telephonyManager.getDeviceId();
        }

        if (getIntent().hasExtra("profile_id")) {
            progressLayout.setVisibility(View.VISIBLE);
            String profileId = getIntent().getExtras().getString("profile_id");
            searchManager.getSearchById(searchManager.getSearchByIdInput(deviceId, userId, profileId));

        } else {

            try {
                progressLayout.setVisibility(View.VISIBLE);
                JSONObject jsonObject = new JSONObject(getIntent().getExtras().getString("custom_search"));
                searchManager.getCustomSearch(searchManager.getCustomSearchInput(deviceId, jsonObject));
            } catch (Exception e) {

            }

        }


    }

    @Override
    public void successCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_SEARCH_CUSTOM:
                progressLayout.setVisibility(View.GONE);
                ArrayList<HashMap<String, String>> dataList = searchData.getCustomSearchList();
                if(dataList.size()>0)
                {
                    textNoSearchResults.setVisibility(View.GONE);
                    recyclerSearchResult.setVisibility(View.VISIBLE);
                    customSearchListAdapter = new CustomSearchListAdapter(SearchResultActivity.this, dataList);
                    recyclerSearchResult.setAdapter(customSearchListAdapter);
                }else {
                    textNoSearchResults.setVisibility(View.VISIBLE);
                    recyclerSearchResult.setVisibility(View.GONE);
                }

                break;
            case Constants.TAG_SEARCH_BY_ID:
                progressLayout.setVisibility(View.GONE);
                String status = searchData.getSearchByIdStatus();
                if (status.equals("1")) {
                    Intent intent = new Intent(this, SearchByIdResultActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(this, "" + searchData.getSearchByIdMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_SEARCH_CUSTOM:
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
                break;
            case Constants.TAG_SEARCH_BY_ID:
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
