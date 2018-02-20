package com.lifejodi.navigation.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.lifejodi.R;
import com.lifejodi.login.data.RegSpinnersData;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.login.manager.RegSpinnersManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;
import com.lifejodi.utils.customfonts.CustomTextBeatles;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2/10/2018.
 */

public class ShowProfileDataActivity extends AppCompatActivity implements VolleyCallbackInterface {

    @BindView(R.id.tools_edit)
    Toolbar toolsEdit;
    @BindView(R.id.image_edit_basicdetails)
    ImageView imageEditBasicdetails;
    @BindView(R.id.text_editprof_name)
    CustomTextBeatles textEditprofName;
    @BindView(R.id.text_editprof_gender)
    CustomTextBeatles textEditprofGender;
    @BindView(R.id.text_editprof_age)
    CustomTextBeatles textEditprofAge;
    @BindView(R.id.text_editprof_height)
    CustomTextBeatles textEditprofHeight;
    @BindView(R.id.text_editprof_marital_status)
    CustomTextBeatles textEditprofMaritalStatus;
    @BindView(R.id.text_editprof_mothertongue)
    CustomTextBeatles textEditprofMothertongue;
    @BindView(R.id.text_editprof_physical_status)
    CustomTextBeatles textEditprofPhysicalStatus;
    @BindView(R.id.text_editprof_profilefor)
    CustomTextBeatles textEditprofProfilefor;
    @BindView(R.id.image_edit_religiousinfo)
    ImageView imageEditReligiousinfo;
    @BindView(R.id.text_editprof_religion)
    CustomTextBeatles textEditprofReligion;
    @BindView(R.id.text_editprof_caste)
    CustomTextBeatles textEditprofCaste;
    @BindView(R.id.text_editprof_dosham)
    CustomTextBeatles textEditprofDosham;
    @BindView(R.id.image_edit_professionalinfo)
    ImageView imageEditProfessionalinfo;
    @BindView(R.id.text_editprof_education)
    CustomTextBeatles textEditprofEducation;
    @BindView(R.id.text_editprof_occupation)
    CustomTextBeatles textEditprofOccupation;
    @BindView(R.id.text_editprof_employedin)
    CustomTextBeatles textEditprofEmployedin;
    @BindView(R.id.text_editprof_income)
    CustomTextBeatles textEditprofIncome;
    @BindView(R.id.text_editprof_currency)
    CustomTextBeatles textEditprofCurrency;
    @BindView(R.id.image_edit_familydetails)
    ImageView imageEditFamilydetails;
    @BindView(R.id.text_editprof_family_values)
    CustomTextBeatles textEditprofFamilyValues;
    @BindView(R.id.text_editprof_family_type)
    CustomTextBeatles textEditprofFamilyType;
    @BindView(R.id.text_editprof_family_status)
    CustomTextBeatles textEditprofFamilyStatus;
    @BindView(R.id.text_editprof_country)
    CustomTextBeatles textEditprofCountry;

    SharedPreference sharedPreference = SharedPreference.getSharedInstance();
    JSONObject userDataObject = new JSONObject();

    Dialog dialogEditProfile;
    EditText editName,editAnnualIncome;
    RadioGroup familtyType;
    TextView dialogTitle;
    Button dialogSave;
    Spinner spinnerHeight,spinnerMaritalStatus,spinnerMotherTongue,spinnerPhysicalStatus,spinnerCreatedBy,
            spinnerReligion,spinnerCaste,spinnerDosham,spinnerEducation,spinnerOccupation,spinnerEmployedIn,
    spinnerCurrency,spinnerFamilyValues,spinnerFamilyStatus,spinnerCountry;
    View layoutBasicDetails,layoutFamilyDetails,layoutProfessionalDetails,layoutReligiousDetails;

    RegSpinnersManager regSpinnersManager = RegSpinnersManager.getInstance();
    RegSpinnersData regSpinnersData = RegSpinnersData.getInstance();

    String deviceId,userId,profileFor,name,gender,religion,motherTongue,countryCode,phoneNumber,email,
    maritalStatus,dosham,caste,height,physicalStatus,education,educationId,currency,currencyId,annualIncome,familyStatus,
    familyStatusId,familyType,familyTypeId,familyValues,familyValuesId,description,lat,lng,sublocality,locality,
    administrativeArea,country,pincode,address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile_data);
        ButterKnife.bind(this);
        initialization();
    }

    public void initialization()
    {

        setSupportActionBar(toolsEdit);
        toolsEdit.setNavigationIcon(R.drawable.ic_back);
        toolsEdit.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle("Edit profile");
        toolsEdit.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sharedPreference.initialize(this);
        String userData = sharedPreference.getSharedPrefData(Constants.USERDATA);
        try {
            userDataObject = new JSONObject(userData);
            setProfileData(userDataObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        dialogEditProfile = new Dialog(this);
        dialogEditProfile.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEditProfile.setContentView(R.layout.dialog_edit_profile);

        editName = (EditText)dialogEditProfile.findViewById(R.id.edittext_edit_name);
        editAnnualIncome = (EditText)dialogEditProfile.findViewById(R.id.edittext_edit_annualincome);

        familtyType = (RadioGroup)dialogEditProfile.findViewById(R.id.radiogroup_edit_family_type);

        spinnerHeight = (Spinner)dialogEditProfile.findViewById(R.id.spinner_edit_height);
        spinnerMaritalStatus = (Spinner)dialogEditProfile.findViewById(R.id.spinner_edit_marital_status);
        spinnerMotherTongue = (Spinner)dialogEditProfile.findViewById(R.id.spinner_edit_mother_tongue);
        spinnerCreatedBy = (Spinner)dialogEditProfile.findViewById(R.id.spinner_edit_created_by);
        spinnerPhysicalStatus = (Spinner)dialogEditProfile.findViewById(R.id.spinner_edit_physical_status);
        spinnerReligion = (Spinner)dialogEditProfile.findViewById(R.id.spinner_edit_religion);
        spinnerCaste = (Spinner)dialogEditProfile.findViewById(R.id.spinner_edit_caste);
        spinnerDosham = (Spinner)dialogEditProfile.findViewById(R.id.spinner_edit_dosham);
        spinnerEducation = (Spinner)dialogEditProfile.findViewById(R.id.spinner_education);
        spinnerOccupation = (Spinner)dialogEditProfile.findViewById(R.id.spinner_edit_occupation);
        spinnerEmployedIn = (Spinner)dialogEditProfile.findViewById(R.id.spinner_edit_employedin);
        spinnerCurrency = (Spinner)dialogEditProfile.findViewById(R.id.spinner_edit_currency);
        spinnerFamilyValues = (Spinner)dialogEditProfile.findViewById(R.id.spinner_edit_family_values);
        spinnerFamilyStatus = (Spinner)dialogEditProfile.findViewById(R.id.spinner_edit_family_status);
        spinnerCountry = (Spinner)dialogEditProfile.findViewById(R.id.spinner_edit_country);

        layoutBasicDetails = (View)dialogEditProfile.findViewById(R.id.layout_basic_details);
        layoutProfessionalDetails = (View)dialogEditProfile.findViewById(R.id.layout_professional_details);
        layoutFamilyDetails = (View)dialogEditProfile.findViewById(R.id.layout_family_details);
        layoutReligiousDetails = (View)dialogEditProfile.findViewById(R.id.layout_religious_details);

        dialogTitle = (TextView)dialogEditProfile.findViewById(R.id.text_edit_title);
        dialogSave = (Button)dialogEditProfile.findViewById(R.id.button_edit_save);

        dialogSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEditProfile.dismiss();
            }
        });


        regSpinnersManager.initialize(this,this);
        String androidDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.e("DEVICEID",androidDeviceId);
        if(androidDeviceId!=null || androidDeviceId!="")
        {
            regSpinnersManager.getAllSpinnersData(regSpinnersManager.getAllSpinnersDataInputs(androidDeviceId));
        }else {
            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission") String deviceId = telephonyManager.getDeviceId();
            regSpinnersManager.getAllSpinnersData(regSpinnersManager.getAllSpinnersDataInputs(deviceId));
        }
    }

    @OnClick({R.id.image_edit_basicdetails, R.id.image_edit_religiousinfo, R.id.image_edit_professionalinfo, R.id.image_edit_familydetails})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_edit_basicdetails:

                layoutBasicDetails.setVisibility(View.VISIBLE);
                layoutReligiousDetails.setVisibility(View.GONE);
                layoutFamilyDetails.setVisibility(View.GONE);
                layoutProfessionalDetails.setVisibility(View.GONE);
                dialogTitle.setText("Basic Details");

                dialogEditProfile.show();

                break;
            case R.id.image_edit_religiousinfo:

                layoutBasicDetails.setVisibility(View.GONE);
                layoutReligiousDetails.setVisibility(View.VISIBLE);
                layoutFamilyDetails.setVisibility(View.GONE);
                layoutProfessionalDetails.setVisibility(View.GONE);
                dialogTitle.setText("Religious Details");

                dialogEditProfile.show();

                break;
            case R.id.image_edit_professionalinfo:

                layoutBasicDetails.setVisibility(View.GONE);
                layoutReligiousDetails.setVisibility(View.GONE);
                layoutFamilyDetails.setVisibility(View.GONE);
                layoutProfessionalDetails.setVisibility(View.VISIBLE);
                dialogTitle.setText("Professional Details");

                dialogEditProfile.show();

                break;
            case R.id.image_edit_familydetails:

                layoutBasicDetails.setVisibility(View.GONE);
                layoutReligiousDetails.setVisibility(View.GONE);
                layoutFamilyDetails.setVisibility(View.VISIBLE);
                layoutProfessionalDetails.setVisibility(View.GONE);
                dialogTitle.setText("Family Details");

                dialogEditProfile.show();

                break;
        }
    }

    public void setProfileData(JSONObject jsonObject)
    {
        try {

            textEditprofName.setText(jsonObject.getString(UserRegData.NAME));
            textEditprofGender.setText(jsonObject.getString(UserRegData.GENDER));
            textEditprofHeight.setText(jsonObject.getString(UserRegData.HEIGHT));
            textEditprofMaritalStatus.setText(jsonObject.getString(UserRegData.MARITALSTATUSNAME));
            textEditprofMothertongue.setText(jsonObject.getString(UserRegData.MOTHERTONGUENAME));
            textEditprofPhysicalStatus.setText(jsonObject.getString(UserRegData.PHYSICALSTATUSNAME));
            textEditprofProfilefor.setText(jsonObject.getString(UserRegData.PROFILEFORNAME));

            textEditprofReligion.setText(jsonObject.getString(UserRegData.RELIGIONNAME));
            textEditprofCaste.setText(jsonObject.getString(UserRegData.CASTENAME));
            textEditprofDosham.setText(jsonObject.getString(UserRegData.DOSHAM));

            textEditprofEducation.setText(jsonObject.getString(UserRegData.EDUCATIONNAME));
            textEditprofOccupation.setText(jsonObject.getString(UserRegData.OCCUPATIONNAME));
            textEditprofEmployedin.setText(jsonObject.getString(UserRegData.EMPLOYEDINNAME));
            textEditprofIncome.setText(jsonObject.getString(UserRegData.ANNUALINCOME));
            textEditprofCurrency.setText(jsonObject.getString(UserRegData.CURRENCYNAME));

            textEditprofFamilyValues.setText(jsonObject.getString(UserRegData.FAMILYVALUESNAME));
            textEditprofFamilyStatus.setText(jsonObject.getString(UserRegData.FAMILYSTATUSNAME));
            textEditprofFamilyType.setText(jsonObject.getString(UserRegData.FAMILYTYPENAME));
            textEditprofCountry.setText(jsonObject.getString(UserRegData.COUNTRY));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void successCallBack(String msg, String tag) {
        if(tag.equals(Constants.TAG_GET_MASTERS))
        {
            setEditProfileData();
        }
    }
    @Override
    public void errorCallBack(String msg, String tag) {

    }

    private void setEditProfileData() {




    }
}
