package com.lifejodi.search.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.search.data.SearchData;
import com.lifejodi.search.manager.SearchManager;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by parikshit on 28/2/18.
 */

public class SearchResultActivity extends AppCompatActivity implements VolleyCallbackInterface{

    @BindView(R.id.toolbar_search_result)
    Toolbar toolbarSearchResult;
    @BindView(R.id.recycler_search_result)
    RecyclerView recyclerSearchResult;

    SearchManager searchManager = SearchManager.getInstance();
    SharedPreference sharedPreference = SharedPreference.getSharedInstance();
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

        sharedPreference.initialize(this);
        searchManager.initialize(SearchResultActivity.this,this);

        String userData = sharedPreference.getSharedPrefData(Constants.USERDATA);
        String userId = Constants.getValue(userData, SearchData.USERID);
        String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        Log.e("DEVICEID", deviceId);

        if (deviceId != null || deviceId != "") {
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            deviceId = telephonyManager.getDeviceId();
        }

        if(getIntent().hasExtra("profile_id")){

            String profileId = getIntent().getExtras().getString("profile_id");
            searchManager.getSearchById(searchManager.getSearchByIdInput(deviceId,userId,profileId));

        }else {

            try {
                JSONObject jsonObject = new JSONObject(getIntent().getExtras().getString("custom_search"));
                searchManager.getCustomSearch(searchManager.getCustomSearchInput(deviceId,jsonObject));
            }catch (Exception e){

            }

        }


    }

    @Override
    public void successCallBack(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_SEARCH_CUSTOM:
                break;
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_SEARCH_CUSTOM:
                Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
