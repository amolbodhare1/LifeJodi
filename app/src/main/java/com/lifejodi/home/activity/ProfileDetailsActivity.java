package com.lifejodi.home.activity;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lifejodi.R;
import com.lifejodi.home.data.HomeFragmentsData;
import com.lifejodi.home.data.ProfilesData;
import com.lifejodi.home.data.ShortlistData;
import com.lifejodi.home.managers.ProfileDataManager;
import com.lifejodi.home.managers.ShortListManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;
import com.lifejodi.utils.customfonts.CustomButtonBeatles;
import com.lifejodi.utils.customfonts.CustomTextBeatles;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JANHAVI on 2/6/2018.
 */

public class ProfileDetailsActivity extends AppCompatActivity implements VolleyCallbackInterface {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image_prof_details_pic)
    ImageView imageProfDetailsPic;
    @BindView(R.id.text_prof_details_name)
    CustomTextBeatles textProfDetailsName;
    @BindView(R.id.layout_profdetails_shortlist)
    LinearLayout layoutProfdetailsShortlist;
    @BindView(R.id.layout_profdetails_call)
    LinearLayout layoutProfdetailsCall;
    @BindView(R.id.layout_profdetails_chat)
    LinearLayout layoutProfdetailsChat;
    @BindView(R.id.text_prof_details_dob)
    CustomTextBeatles textProfDetailsDob;
    @BindView(R.id.text_prof_details_religion)
    CustomTextBeatles textProfDetailsReligion;
    @BindView(R.id.text_prof_details_mothertongue)
    CustomTextBeatles textProfDetailsMothertongue;
    @BindView(R.id.text_prof_details_age)
    CustomTextBeatles textProfDetailsAge;
    @BindView(R.id.text_prof_details_gender)
    CustomTextBeatles textProfDetailsGender;
    @BindView(R.id.button_profdetails_yes)
    CustomButtonBeatles buttonProfdetailsYes;
    @BindView(R.id.button_profdetails_skip)
    CustomButtonBeatles buttonProfdetailsSkip;
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;

    ProfileDataManager profileDataManager;
    ProfilesData profilesData = ProfilesData.getInstance();

    ShortlistData shortlistData = ShortlistData.getInstance();
    ShortListManager shortListManager;

    String profId = "";
    SharedPreference sharedPreference = SharedPreference.getSharedInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        initialization();
    }

    public void initialization() {

        if (getIntent().hasExtra(Constants.USERID)) {
            profId = getIntent().getExtras().getString(Constants.USERID);
        }
        sharedPreference.initialize(this);
        profileDataManager = ProfileDataManager.getInstance();
        profileDataManager.initialize(this, ProfileDetailsActivity.this);
        progressLayout.setVisibility(View.VISIBLE);
        String androidDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        profileDataManager.getShortListedProfDetails(profileDataManager.getShortListedDetailsParams(androidDeviceId,profId));
    }

    @OnClick({R.id.layout_profdetails_shortlist, R.id.layout_profdetails_call, R.id.layout_profdetails_chat, R.id.button_profdetails_yes, R.id.button_profdetails_skip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_profdetails_shortlist:
                shortListManager = ShortListManager.getInstance();
                shortListManager.initialize(this,ProfileDetailsActivity.this);
                String androidDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                String  profId = sharedPreference.getSharedPrefData(Constants.PROFILEID);
                HashMap<String,String> profileMap = profilesData.getShortListedDetailsMap();
                String userId = profileMap.get(ProfilesData.ID);
                progressLayout.setVisibility(View.VISIBLE);
                shortListManager.shortListUser(shortListManager.getShortlistUserParams(androidDeviceId,profId,userId));
                break;
            case R.id.layout_profdetails_call:
                break;
            case R.id.layout_profdetails_chat:
                break;
            case R.id.button_profdetails_yes:
                break;
            case R.id.button_profdetails_skip:
                break;
        }
    }

    @Override
    public void successCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_SHORTLISTED_USERSDETAILS:
                progressLayout.setVisibility(View.GONE);
                HashMap<String,String> profileMap = profilesData.getShortListedDetailsMap();
                setProfileInfo(profileMap);
                break;
            case Constants.TAG_SHORTLIST_USER:
                progressLayout.setVisibility(View.GONE);
                String message = shortlistData.getShortlistingMessage();
                Toast.makeText(ProfileDetailsActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_SHORTLISTED_USERSDETAILS:
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
                break;
            case Constants.TAG_SHORTLIST_USER:
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void setProfileInfo(HashMap<String,String> dataMap)
    {
        textProfDetailsName.setText(dataMap.get(ProfilesData.FULLNAME));
        textProfDetailsDob.setText(dataMap.get(ProfilesData.DOB));
        textProfDetailsMothertongue.setText(dataMap.get(ProfilesData.MOTHERTONGUENAME));
        textProfDetailsReligion.setText(dataMap.get(ProfilesData.RELIGION));
        textProfDetailsAge.setText(dataMap.get(ProfilesData.AGE));
        textProfDetailsGender.setText(dataMap.get(ProfilesData.GENDER));

        int defaultImage = 0;
        String imageUrl = dataMap.get(ProfilesData.PROFILEPIC);
        imageUrl = imageUrl.replace("https","http");
        if(dataMap.get(ProfilesData.GENDER).equalsIgnoreCase("Male"))
        {
            defaultImage = R.drawable.picture;
        }else if(dataMap.get(ProfilesData.GENDER).equalsIgnoreCase("Female"))
        {
            defaultImage = R.drawable.images;
        }
        try {
            Glide.with(ProfileDetailsActivity.this).load(imageUrl).placeholder(defaultImage).into(imageProfDetailsPic);
        }catch (Exception e)
        {
            Log.e("IMAGELOAD",e.getLocalizedMessage());
        }
    }
}
