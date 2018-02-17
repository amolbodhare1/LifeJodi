package com.lifejodi.navigation.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.lifejodi.R;
import com.lifejodi.login.data.UserRegData;
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

public class ShowProfileDataActivity extends AppCompatActivity {

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

    }

    @OnClick({R.id.image_edit_basicdetails, R.id.image_edit_religiousinfo, R.id.image_edit_professionalinfo, R.id.image_edit_familydetails})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_edit_basicdetails:
                break;
            case R.id.image_edit_religiousinfo:
                break;
            case R.id.image_edit_professionalinfo:
                break;
            case R.id.image_edit_familydetails:
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
}
