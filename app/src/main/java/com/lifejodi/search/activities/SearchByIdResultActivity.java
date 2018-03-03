package com.lifejodi.search.activities;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.home.data.ShortlistData;
import com.lifejodi.home.managers.ShortListManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.search.data.SearchData;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;
import com.lifejodi.utils.customfonts.CustomTextBeatles;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 3/1/2018.
 */

public class SearchByIdResultActivity extends AppCompatActivity implements VolleyCallbackInterface {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image_searchid_result_profile)
    ImageView imageSearchidResultProfile;
    @BindView(R.id.text_searchid_result_name)
    CustomTextBeatles textSearchidResultName;
    @BindView(R.id.layout_searchid_result_shortlist)
    LinearLayout layoutSearchidResultShortlist;
    @BindView(R.id.layout_searchid_result_call)
    LinearLayout layoutSearchidResultCall;
    @BindView(R.id.layout_searchid_result_chat)
    LinearLayout layoutSearchidResultChat;
    @BindView(R.id.text_searchid_result_dob)
    CustomTextBeatles textSearchidResultDob;
    @BindView(R.id.text_searchid_result_age)
    CustomTextBeatles textSearchidResultAge;
    @BindView(R.id.text_searchid_result_height)
    CustomTextBeatles textSearchidResultHeight;
    @BindView(R.id.text_searchid_result_gender)
    CustomTextBeatles textSearchidResultGender;
    @BindView(R.id.text_searchid_result_religion)
    CustomTextBeatles textSearchidResultReligion;
    @BindView(R.id.text_searchid_result_mothertongue)
    CustomTextBeatles textSearchidResultMothertongue;
    @BindView(R.id.text_searchid_result_phnumber)
    CustomTextBeatles textSearchidResultPhnumber;
    @BindView(R.id.text_searchid_result_email)
    CustomTextBeatles textSearchidResultEmail;
    @BindView(R.id.text_searchid_result_maritalstatus)
    CustomTextBeatles textSearchidResultMaritalstatus;
    @BindView(R.id.text_searchid_result_caste)
    CustomTextBeatles textSearchidResultCaste;
    @BindView(R.id.text_searchid_result_dosham)
    CustomTextBeatles textSearchidResultDosham;
    @BindView(R.id.text_searchid_result_physicalstatus)
    CustomTextBeatles textSearchidResultPhysicalstatus;
    @BindView(R.id.text_searchid_result_marryothercaste)
    CustomTextBeatles textSearchidResultMarryothercaste;

    @BindView(R.id.text_searchid_result_education)
    CustomTextBeatles textSearchidResultEducation;
    @BindView(R.id.text_searchid_result_occupation)
    CustomTextBeatles textSearchidResultOccupation;
    @BindView(R.id.text_searchid_result_workingas)
    CustomTextBeatles textSearchidResultWorkingas;
    @BindView(R.id.text_searchid_result_income)
    CustomTextBeatles textSearchidResultIncome;
    @BindView(R.id.text_searchid_result_familytype)
    CustomTextBeatles textSearchidResultFamilytype;
    @BindView(R.id.text_searchid_result_familyvalues)
    CustomTextBeatles textSearchidResultFamilyvalues;
    @BindView(R.id.text_searchid_result_familystatus)
    CustomTextBeatles textSearchidResultFamilystatus;
    @BindView(R.id.text_searchid_result_addr)
    CustomTextBeatles textSearchidResultAddr;
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;

    SearchData searchData = SearchData.getInstance();
    ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
    String profId = "", userId = "";
    SharedPreference sharedPreference;
    ShortListManager shortListManager;
    ShortlistData shortlistData = ShortlistData.getInstance();
    @BindView(R.id.layout_searchbyid_results)
    LinearLayout layoutSearchbyidResults;
    @BindView(R.id.text_no_searchbyid_results)
    CustomTextBeatles textNoSearchbyidResults;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_id_result);
        ButterKnife.bind(this);
        initialization();
    }

    public void initialization() {
        sharedPreference = SharedPreference.getSharedInstance();
        sharedPreference.initialize(this);

        dataList = searchData.getSearchByIdsList();
        if(dataList.size()>0)
        {
            HashMap<String, String> dataMap = dataList.get(0);

            String imageUrl = dataMap.get(SearchData.PROFILEPIC);
            imageUrl = imageUrl.replace("https", "http");
            Picasso.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.image_event1)
                    .into(imageSearchidResultProfile);

            textSearchidResultName.setText(dataMap.get(searchData.FULLNAME));
            textSearchidResultDob.setText(dataMap.get(searchData.DOB));
            textSearchidResultAge.setText(dataMap.get(searchData.AGE));
            textSearchidResultHeight.setText(dataMap.get(searchData.HEIGHT));
            textSearchidResultGender.setText(dataMap.get(searchData.GENDER));
            textSearchidResultReligion.setText(dataMap.get(searchData.RELIGION));
            textSearchidResultMothertongue.setText(dataMap.get(searchData.MOTHERTONGUE));
            textSearchidResultPhnumber.setText(dataMap.get(searchData.PHNUMBER));
            textSearchidResultEmail.setText(dataMap.get(searchData.EMAIL));
            textSearchidResultMaritalstatus.setText(dataMap.get(searchData.MARITALSTATUS));
            textSearchidResultCaste.setText(dataMap.get(searchData.CASTE));
            textSearchidResultDosham.setText(dataMap.get(searchData.DOSHAM));
            textSearchidResultMarryothercaste.setText(dataMap.get(searchData.MARRYOTHERCAST));
            textSearchidResultPhysicalstatus.setText(dataMap.get(searchData.PHYSICALSTATUS));

            textSearchidResultEducation.setText(dataMap.get(searchData.EDUCATIONLEVEL));
            textSearchidResultOccupation.setText(dataMap.get(searchData.OCCUPATIONNAME));
            textSearchidResultWorkingas.setText(dataMap.get(searchData.WORKINGAS));
            textSearchidResultIncome.setText(dataMap.get(searchData.ANNUALINCOME));

            textSearchidResultFamilystatus.setText(dataMap.get(searchData.FAMILYSTATUS));
            textSearchidResultFamilytype.setText(dataMap.get(searchData.FAMILYTYPE));
            textSearchidResultFamilyvalues.setText(dataMap.get(searchData.FAMILYVALUES));
            textSearchidResultAddr.setText(dataMap.get(searchData.FORMATTEDADRRESS));

            textNoSearchbyidResults.setVisibility(View.GONE);
            layoutSearchbyidResults.setVisibility(View.VISIBLE);
        }else {
            textNoSearchbyidResults.setVisibility(View.VISIBLE);
            layoutSearchbyidResults.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.layout_searchid_result_shortlist, R.id.layout_searchid_result_call, R.id.layout_searchid_result_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_searchid_result_shortlist:
                dataList = searchData.getSearchByIdsList();
                HashMap<String, String> dataMap = dataList.get(0);
                profId = dataMap.get(searchData.ID);
                userId = sharedPreference.getSharedPrefData(Constants.UID);
                String androidDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                shortListManager = ShortListManager.getInstance();
                shortListManager.initialize(this, SearchByIdResultActivity.this);
                shortListManager.shortListUser(shortListManager.getShortlistUserParams(androidDeviceId, profId, userId));
                break;
            case R.id.layout_searchid_result_call:
                break;
            case R.id.layout_searchid_result_chat:
                break;
        }
    }

    @Override
    public void successCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_SHORTLIST_USER:
                progressLayout.setVisibility(View.GONE);
                String message = shortlistData.getShortlistingMessage();
                Toast.makeText(SearchByIdResultActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_SHORTLIST_USER:
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
