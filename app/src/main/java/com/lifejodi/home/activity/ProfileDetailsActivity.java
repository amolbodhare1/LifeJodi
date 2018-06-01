package com.lifejodi.home.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.home.data.ProfilesData;
import com.lifejodi.home.data.ShortlistData;
import com.lifejodi.home.managers.ProfileDataManager;
import com.lifejodi.home.managers.ShortListManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;
import com.lifejodi.utils.customfonts.CustomTextBeatles;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JANHAVI on 2/6/2018.
 */

public class ProfileDetailsActivity extends AppCompatActivity implements VolleyCallbackInterface {

   /* @BindView(R.id.toolbar)
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
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;*/

    ProfileDataManager profileDataManager;
    ProfilesData profilesData = ProfilesData.getInstance();

    ShortlistData shortlistData = ShortlistData.getInstance();
    ShortListManager shortListManager;

    String profId = "";
    SharedPreference sharedPreference = SharedPreference.getSharedInstance();
    @BindView(R.id.image_profiledetails)
    ImageView imageProfiledetails;
    @BindView(R.id.toolbar_profiledetails)
    Toolbar toolbarProfiledetails;
    @BindView(R.id.collapsing_toolbar_profiledetails)
    CollapsingToolbarLayout collapsingToolbarProfiledetails;
    @BindView(R.id.appbar_profiledetails)
    AppBarLayout appbarProfiledetails;
    @BindView(R.id.layout_profdetails_shortlist)
    LinearLayout layoutProfdetailsShortlist;
    @BindView(R.id.layout_profdetails_call)
    LinearLayout layoutProfdetailsCall;
    @BindView(R.id.layout_profdetails_chat)
    LinearLayout layoutProfdetailsChat;
    @BindView(R.id.text_profiledetails_dob)
    CustomTextBeatles textProfiledetailsDob;
    @BindView(R.id.text_profiledetails_age)
    CustomTextBeatles textProfiledetailsAge;
    @BindView(R.id.text_profiledetails_height)
    CustomTextBeatles textProfiledetailsHeight;
    @BindView(R.id.text_profiledetails_gender)
    CustomTextBeatles textProfiledetailsGender;
    @BindView(R.id.text_profiledetails_religion)
    CustomTextBeatles textProfiledetailsReligion;
    @BindView(R.id.text_profiledetails_mothertongue)
    CustomTextBeatles textProfiledetailsMothertongue;
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.text_chat_now_details)
    CustomTextBeatles textChatNowDetails;


    HashMap<String, String> profileMap = new HashMap<>();
    /*  @BindView(R.id.text_profiledetails_phnumber)
      CustomTextBeatles textProfiledetailsPhnumber;
      @BindView(R.id.text_profiledetails_email)
      CustomTextBeatles textProfiledetailsEmail;
      @BindView(R.id.text_profiledetails_maritalstatus)
      CustomTextBeatles textProfiledetailsMaritalstatus;
      @BindView(R.id.text_profiledetails_caste)
      CustomTextBeatles textProfiledetailsCaste;
      @BindView(R.id.text_profiledetails_dosham)
      CustomTextBeatles textProfiledetailsDosham;
      @BindView(R.id.text_profiledetails_physicalstatus)
      CustomTextBeatles textProfiledetailsPhysicalstatus;
      @BindView(R.id.text_profiledetails_marryothercaste)
      CustomTextBeatles textProfiledetailsMarryothercaste;
      @BindView(R.id.text_profiledetails_education)
      CustomTextBeatles textProfiledetailsEducation;
      @BindView(R.id.text_profiledetails_occupation)
      CustomTextBeatles textProfiledetailsOccupation;
      @BindView(R.id.text_profiledetails_workingas)
      CustomTextBeatles textProfiledetailsWorkingas;
      @BindView(R.id.text_profiledetails_income)
      CustomTextBeatles textProfiledetailsIncome;
      @BindView(R.id.text_profiledetails_familytype)
      CustomTextBeatles textProfiledetailsFamilytype;
      @BindView(R.id.text_profiledetails_familyvalues)
      CustomTextBeatles textProfiledetailsFamilyvalues;
      @BindView(R.id.text_profiledetails_familystatus)
      CustomTextBeatles textProfiledetailsFamilystatus;
      @BindView(R.id.text_profiledetails_addr)
      CustomTextBeatles textProfiledetailsAddr;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
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
        profileDataManager.getShortListedProfDetails(profileDataManager.getShortListedDetailsParams(androidDeviceId, profId));

    }

    @OnClick({R.id.layout_profdetails_shortlist, R.id.layout_profdetails_call, R.id.layout_profdetails_chat,R.id.text_chat_now_details})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_profdetails_shortlist:
                shortListManager = ShortListManager.getInstance();
                shortListManager.initialize(this, ProfileDetailsActivity.this);
                String androidDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                String userId = sharedPreference.getSharedPrefData(Constants.UID);
            //    HashMap<String, String> profileMap = profilesData.getShortListedDetailsMap();
                String profId = profileMap.get(ProfilesData.ID);
                progressLayout.setVisibility(View.VISIBLE);
                shortListManager.shortListUser(shortListManager.getShortlistUserParams(androidDeviceId, profId, userId));
                break;
            case R.id.layout_profdetails_call:
                break;
            case R.id.layout_profdetails_chat:
                break;
            case R.id.text_chat_now_details:

                String text = textChatNowDetails.getText().toString();
                if(text.equals(getResources().getString(R.string.chat_now))){

                }else if(text.equals(getResources().getString(R.string.send_request))){
                    sendRequest();
                }else if(text.equals(getResources().getString(R.string.accept_request))){
                    acceptRequest();
                }

                break;
        }
    }

    private void acceptRequest() {

        AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
        aBuilder.setMessage("Do you want to accept request ?");
        aBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                profileDataManager.acceptRequest(profileDataManager.getAccpetRequestInput(profileMap.get(ProfilesData.ID)));
            }
        });
        aBuilder.setNegativeButton("Reject", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                profileDataManager.rejectRequest(profileDataManager.getRejectRequestInput(profileMap.get(ProfilesData.ID)));
            }
        });
        aBuilder.show();

    }

    private void sendRequest() {

        AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
        aBuilder.setMessage("Do you want to send request ?");
        aBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                profileDataManager.sendRequest(profileDataManager.getSendRequestInput(profileMap.get(ProfilesData.ID)));
            }
        });
        aBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        aBuilder.show();

    }

    @Override
    public void successCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_SHORTLISTED_USERSDETAILS:
                progressLayout.setVisibility(View.GONE);
                profileMap = profilesData.getShortListedDetailsMap();
                setProfileInfo(profileMap);
                break;
            case Constants.TAG_SHORTLIST_USER:
                progressLayout.setVisibility(View.GONE);
                String message = shortlistData.getShortlistingMessage();
                Toast.makeText(ProfileDetailsActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                break;
            case Constants.TAG_SEND_REQUEST:
                Toast.makeText(ProfileDetailsActivity.this, msg, Toast.LENGTH_SHORT).show();
                textChatNowDetails.setText(getResources().getString(R.string.request_pending));
                break;
            case Constants.TAG_ACCEPT_REQUEST:
                Toast.makeText(ProfileDetailsActivity.this, msg, Toast.LENGTH_SHORT).show();
                textChatNowDetails.setText(getResources().getString(R.string.chat_now));
                break;
            case Constants.TAG_REJECT_REQUEST:
                Toast.makeText(ProfileDetailsActivity.this, msg, Toast.LENGTH_SHORT).show();
                textChatNowDetails.setText(getResources().getString(R.string.chat_now));
                layoutProfdetailsChat.setVisibility(View.GONE);
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

    public void setProfileInfo(HashMap<String, String> dataMap) {
        // text.setText(dataMap.get(ProfilesData.FULLNAME));

        collapsingToolbarProfiledetails.setTitle(dataMap.get(ProfilesData.FULLNAME));

        collapsingToolbarProfiledetails.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbarProfiledetails.setExpandedTitleColor(getResources().getColor(R.color.colorPrimary));
        // toolbarEventDetails.setNavigationIcon(R.drawable.ic_back);
        collapsingToolbarProfiledetails.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarProfiledetails.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        textProfiledetailsDob.setText(dataMap.get(ProfilesData.DOB));
        textProfiledetailsMothertongue.setText(dataMap.get(ProfilesData.MOTHERTONGUENAME));
        textProfiledetailsReligion.setText(dataMap.get(ProfilesData.RELIGION));
        textProfiledetailsAge.setText(dataMap.get(ProfilesData.AGE));
        textProfiledetailsGender.setText(dataMap.get(ProfilesData.GENDER));

        int defaultImage = 0;
        String imageUrl = dataMap.get(ProfilesData.PROFILEPIC);
        imageUrl = imageUrl.replace("https", "http");
        if (dataMap.get(ProfilesData.GENDER).equalsIgnoreCase("Male")) {
            defaultImage = R.drawable.image_default_man;
        } else if (dataMap.get(ProfilesData.GENDER).equalsIgnoreCase("Female")) {
            defaultImage = R.drawable.image_default_women;
        }
        try {
            //  Glide.with(ProfileDetailsActivity.this).load(imageUrl).placeholder(defaultImage).into(imageProfDetailsPic);
            Picasso.with(ProfileDetailsActivity.this).load(imageUrl).placeholder(defaultImage).into(imageProfiledetails);
        } catch (Exception e) {
            Log.e("IMAGELOAD", e.getLocalizedMessage());
        }


        if(dataMap.get(ProfilesData.STATUS).equals("status")){
            textChatNowDetails.setText(getResources().getString(R.string.send_request));
        }else if(dataMap.get(ProfilesData.STATUS).equals("0")){
            if(dataMap.get(ProfilesData.MODE).equals("1")){
                textChatNowDetails.setText(getResources().getString(R.string.accept_request));
            }else {
                textChatNowDetails.setText(getResources().getString(R.string.request_pending));
            }
        }else if(dataMap.get(ProfilesData.STATUS).equals("1")){
            textChatNowDetails.setText(getResources().getString(R.string.chat_now));
        }else if(dataMap.get(ProfilesData.STATUS).equals("2")){
            layoutProfdetailsChat.setVisibility(View.GONE);
        }

    }
}
