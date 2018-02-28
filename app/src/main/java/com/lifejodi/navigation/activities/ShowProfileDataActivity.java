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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.home.managers.ProfileDataManager;
import com.lifejodi.login.adapter.CustomSpinnerAdapter;
import com.lifejodi.login.adapter.SpinnerAdapter;
import com.lifejodi.login.data.RegSpinnersData;
import com.lifejodi.login.data.RegSpinnersStaticData;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.login.manager.RegSpinnersManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;
import com.lifejodi.utils.customfonts.CustomTextBeatles;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
    @BindView(R.id.button_update_profile)
    Button buttonUpdateProfile;
    @BindView(R.id.layout_preogress)
    RelativeLayout layoutPreogress;

    SharedPreference sharedPreference = SharedPreference.getSharedInstance();
    JSONObject userDataObject = new JSONObject();

    Dialog dialogEditProfile;
    EditText editName, editAnnualIncome;
    RadioGroup radiofamiltyType;
    RadioButton radioButtonNuclear, radioButtonJoint;
    TextView dialogTitle;
    Button dialogSave;
    Spinner spinnerHeight, spinnerMaritalStatus, spinnerMotherTongue, spinnerPhysicalStatus, spinnerCreatedBy,
            spinnerReligion, spinnerCaste, spinnerDosham, spinnerEducation, spinnerOccupation, spinnerEmployedIn,
            spinnerCurrency, spinnerFamilyValues, spinnerFamilyStatus, spinnerCountry;
    View layoutBasicDetails, layoutFamilyDetails, layoutProfessionalDetails, layoutReligiousDetails;

    RegSpinnersManager regSpinnersManager = RegSpinnersManager.getInstance();
    RegSpinnersData regSpinnersData = RegSpinnersData.getInstance();
    ProfileDataManager profileDataManager = ProfileDataManager.getInstance();

    String deviceId, userId, profileFor, profileForId, name, gender, religion, religionId, motherTongue, motherTongueId, countryCode, phoneNumber, email,
            maritalStatus, maritalStatusId, dosham, caste, casteId, height, physicalStatus, physicalStatusId, education, educationId, occupation, occupationId,
            employedIn, employedInId, currency, currencyId, annualIncome, familyStatus,
            familyStatusId, familyType, familyTypeId, familyValues, familyValuesId, description, lat, lng, sublocality, locality,
            administrativeArea, country, pincode, address, dateOfBirth, maryOtherCaste, maryOtherCasteId, profileId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile_data);
        ButterKnife.bind(this);
        initialization();
    }

    @SuppressLint("MissingPermission")
    public void initialization() {

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
            setData(userDataObject);
            setProfileData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialogEditProfile = new Dialog(this);
        dialogEditProfile.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEditProfile.setContentView(R.layout.dialog_edit_profile);

        editName = (EditText) dialogEditProfile.findViewById(R.id.edittext_edit_name);
        editAnnualIncome = (EditText) dialogEditProfile.findViewById(R.id.edittext_edit_annualincome);

        radiofamiltyType = (RadioGroup) dialogEditProfile.findViewById(R.id.radiogroup_edit_family_type);

        spinnerHeight = (Spinner) dialogEditProfile.findViewById(R.id.spinner_edit_height);
        spinnerMaritalStatus = (Spinner) dialogEditProfile.findViewById(R.id.spinner_edit_marital_status);
        spinnerMotherTongue = (Spinner) dialogEditProfile.findViewById(R.id.spinner_edit_mother_tongue);
        spinnerCreatedBy = (Spinner) dialogEditProfile.findViewById(R.id.spinner_edit_created_by);
        spinnerPhysicalStatus = (Spinner) dialogEditProfile.findViewById(R.id.spinner_edit_physical_status);
        spinnerReligion = (Spinner) dialogEditProfile.findViewById(R.id.spinner_edit_religion);
        spinnerCaste = (Spinner) dialogEditProfile.findViewById(R.id.spinner_edit_caste);
        spinnerDosham = (Spinner) dialogEditProfile.findViewById(R.id.spinner_edit_dosham);
        spinnerEducation = (Spinner) dialogEditProfile.findViewById(R.id.spinner_edit_education);
        spinnerOccupation = (Spinner) dialogEditProfile.findViewById(R.id.spinner_edit_occupation);
        spinnerEmployedIn = (Spinner) dialogEditProfile.findViewById(R.id.spinner_edit_employedin);
        spinnerCurrency = (Spinner) dialogEditProfile.findViewById(R.id.spinner_edit_currency);
        spinnerFamilyValues = (Spinner) dialogEditProfile.findViewById(R.id.spinner_edit_family_values);
        spinnerFamilyStatus = (Spinner) dialogEditProfile.findViewById(R.id.spinner_edit_family_status);
        spinnerCountry = (Spinner) dialogEditProfile.findViewById(R.id.spinner_edit_country);

        radioButtonNuclear = (RadioButton) dialogEditProfile.findViewById(R.id.radiobutton_type_nuclear);
        radioButtonJoint = (RadioButton) dialogEditProfile.findViewById(R.id.radiobutton_type_joint);

        layoutBasicDetails = (View) dialogEditProfile.findViewById(R.id.layout_basic_details);
        layoutProfessionalDetails = (View) dialogEditProfile.findViewById(R.id.layout_professional_details);
        layoutFamilyDetails = (View) dialogEditProfile.findViewById(R.id.layout_family_details);
        layoutReligiousDetails = (View) dialogEditProfile.findViewById(R.id.layout_religious_details);

        dialogTitle = (TextView) dialogEditProfile.findViewById(R.id.text_edit_title);
        dialogSave = (Button) dialogEditProfile.findViewById(R.id.button_edit_save);

        regSpinnersManager.initialize(this, this);
        profileDataManager.initialize(this, this);
        deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.e("DEVICEID", deviceId);
        if (deviceId != null || deviceId != "") {
            regSpinnersManager.getAllSpinnersData(regSpinnersManager.getAllSpinnersDataInputs(deviceId));
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            deviceId = telephonyManager.getDeviceId();
            regSpinnersManager.getAllSpinnersData(regSpinnersManager.getAllSpinnersDataInputs(deviceId));
        }

        dialogSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEditProfile.dismiss();

                if (dialogTitle.getText().toString().equals("Basic Details")) {
                    name = editName.getText().toString();
                    height = regSpinnersData.getHeightList().get(spinnerHeight.getSelectedItemPosition());
                    maritalStatus = regSpinnersData.getMaritalStatusList().get(spinnerMaritalStatus.getSelectedItemPosition()).get(regSpinnersData.VALUE);
                    maritalStatusId = regSpinnersData.getMaritalStatusList().get(spinnerMaritalStatus.getSelectedItemPosition()).get(regSpinnersData.ID);
                    motherTongue = regSpinnersData.getMotherTongueList().get(spinnerMotherTongue.getSelectedItemPosition()).get(regSpinnersData.NAME);
                    motherTongueId = regSpinnersData.getMotherTongueList().get(spinnerMotherTongue.getSelectedItemPosition()).get(regSpinnersData.ID);
                    physicalStatus = regSpinnersData.getPhysicalStatusList().get(spinnerPhysicalStatus.getSelectedItemPosition()).get(regSpinnersData.VALUE);
                    physicalStatusId = regSpinnersData.getPhysicalStatusList().get(spinnerPhysicalStatus.getSelectedItemPosition()).get(regSpinnersData.ID);
                    profileFor = regSpinnersData.getProfileForList().get(spinnerCreatedBy.getSelectedItemPosition()).get(regSpinnersData.NAME);
                    profileForId = regSpinnersData.getProfileForList().get(spinnerCreatedBy.getSelectedItemPosition()).get(regSpinnersData.ID);
                } else if (dialogTitle.getText().toString().equals("Religious Details")) {
                    religion = regSpinnersData.getReligionsList().get(spinnerReligion.getSelectedItemPosition()).get(regSpinnersData.NAME);
                    religionId = regSpinnersData.getReligionsList().get(spinnerReligion.getSelectedItemPosition()).get(regSpinnersData.ID);
                    caste = regSpinnersData.getCastsList().get(spinnerCaste.getSelectedItemPosition()).get(regSpinnersData.NAME);
                    casteId = regSpinnersData.getCastsList().get(spinnerCaste.getSelectedItemPosition()).get(regSpinnersData.ID);
                    List<String> doshamList = Constants.getArraylistFromArray(RegSpinnersStaticData.doshamArray);
                    dosham = doshamList.get(spinnerDosham.getSelectedItemPosition());
                } else if (dialogTitle.getText().toString().equals("Professional Details")) {
                    education = regSpinnersData.getEducationList().get(spinnerEducation.getSelectedItemPosition()).get(regSpinnersData.NAME);
                    educationId = regSpinnersData.getEducationList().get(spinnerEducation.getSelectedItemPosition()).get(regSpinnersData.ID);
                    occupation = regSpinnersData.getOccupationList().get(spinnerOccupation.getSelectedItemPosition()).get(regSpinnersData.NAME);
                    occupationId = regSpinnersData.getOccupationList().get(spinnerOccupation.getSelectedItemPosition()).get(regSpinnersData.ID);
                    employedIn = regSpinnersData.getEmployedInList().get(spinnerEmployedIn.getSelectedItemPosition()).get(regSpinnersData.NAME);
                    employedInId = regSpinnersData.getEmployedInList().get(spinnerEmployedIn.getSelectedItemPosition()).get(regSpinnersData.ID);
                    annualIncome = editAnnualIncome.getText().toString();
                    currency = regSpinnersData.getCurrencyList().get(spinnerCurrency.getSelectedItemPosition()).get(regSpinnersData.VALUE);
                    currencyId = regSpinnersData.getCurrencyList().get(spinnerCurrency.getSelectedItemPosition()).get(regSpinnersData.ID);
                } else if (dialogTitle.getText().toString().equals("Family Details")) {
                    familyValues = regSpinnersData.getFamilyValues().get(spinnerFamilyValues.getSelectedItemPosition()).get(regSpinnersData.VALUE);
                    familyValuesId = regSpinnersData.getFamilyValues().get(spinnerFamilyValues.getSelectedItemPosition()).get(regSpinnersData.ID);
                    familyStatus = regSpinnersData.getFamilyStatus().get(spinnerFamilyStatus.getSelectedItemPosition()).get(regSpinnersData.VALUE);
                    familyStatusId = regSpinnersData.getFamilyStatus().get(spinnerFamilyStatus.getSelectedItemPosition()).get(regSpinnersData.ID);
                    if (radioButtonJoint.isChecked()) {
                        familyType = radioButtonJoint.getText().toString();
                        familyTypeId = regSpinnersData.getFamilyType().get(0).get(regSpinnersData.ID);
                    } else if (radioButtonNuclear.isChecked()) {
                        familyType = radioButtonNuclear.getText().toString();
                        familyTypeId = regSpinnersData.getFamilyType().get(1).get(regSpinnersData.ID);
                    }
                    country = regSpinnersData.getCountriesList().get(spinnerCountry.getSelectedItemPosition()).get(regSpinnersData.NAME);
                }
                setProfileData();
            }
        });


    }

    @OnClick({R.id.image_edit_basicdetails, R.id.image_edit_religiousinfo, R.id.image_edit_professionalinfo, R.id.image_edit_familydetails, R.id.button_update_profile})
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

            case R.id.button_update_profile:

                layoutPreogress.setVisibility(View.VISIBLE);
                updateProfile();

                break;
        }
    }

    public void setData(JSONObject jsonObject) {

        try {
            dateOfBirth = jsonObject.getString(UserRegData.DOB);
            userId = jsonObject.getString(UserRegData.USERID);
            profileId = jsonObject.getString(UserRegData.PROFILEID);
            phoneNumber = jsonObject.getString(UserRegData.PHONENUMBER);
            maryOtherCaste = jsonObject.getString(UserRegData.MARRYOTHERCASTE);
            email = jsonObject.getString(UserRegData.EMAIL);
            name = jsonObject.getString(UserRegData.FULLNAME);
            gender = jsonObject.getString(UserRegData.GENDER);
            height = jsonObject.getString(UserRegData.HEIGHT);
            maritalStatus = jsonObject.getString(UserRegData.MARITALSTATUS);
            motherTongue = jsonObject.getString(UserRegData.MOTHERTONGUE);
            physicalStatus = jsonObject.getString(UserRegData.PHYSICALSTATUS);
            profileFor = jsonObject.getString(UserRegData.PROFILEFOR);
            religion = jsonObject.getString(UserRegData.RELIGION);
            caste = jsonObject.getString(UserRegData.CASTE);
            dosham = jsonObject.getString(UserRegData.DOSHAM);
            education = jsonObject.getString(UserRegData.EDUCATIONNAME);
            occupation = jsonObject.getString(UserRegData.OCCUPATIONNAME);
            employedIn = jsonObject.getString(UserRegData.EMPLOYEDINNAME);
            annualIncome = jsonObject.getString(UserRegData.ANNUALINCOME);
            currency = jsonObject.getString(UserRegData.CURRENCY);
            familyValues = jsonObject.getString(UserRegData.FAMILYVALUES);
            familyStatus = jsonObject.getString(UserRegData.FAMILYSTATUS);
            familyType = jsonObject.getString(UserRegData.FAMILYTYPE);
            country = jsonObject.getString(UserRegData.COUNTRY);
            lat = jsonObject.getString(UserRegData.LATITUDE);
            lng = jsonObject.getString(UserRegData.LONGITUDE);
            locality = jsonObject.getString(UserRegData.LOCALITY);
            sublocality = jsonObject.getString(UserRegData.SUBLOCALITY);
            administrativeArea = jsonObject.getString(UserRegData.ADMINISTRATIVEAREA);
            address = jsonObject.getString(UserRegData.FORMATTEDADDRESS);
            countryCode = jsonObject.getString(UserRegData.COUNTRYCODE);
            pincode = jsonObject.getString(UserRegData.PINCODE);
            description = jsonObject.getString(UserRegData.ABOUT_COMMENT);
        } catch (Exception e) {

        }


    }

    public void setProfileData() {

        textEditprofName.setText(name);
        textEditprofGender.setText(gender);
        textEditprofHeight.setText(height);
        textEditprofMaritalStatus.setText(maritalStatus);
        textEditprofMothertongue.setText(motherTongue);
        textEditprofPhysicalStatus.setText(physicalStatus);
        textEditprofProfilefor.setText(profileFor);

        textEditprofReligion.setText(religion);
        textEditprofCaste.setText(caste);
        textEditprofDosham.setText(dosham);

        textEditprofEducation.setText(education);
        textEditprofOccupation.setText(occupation);
        textEditprofEmployedin.setText(employedIn);
        textEditprofIncome.setText(annualIncome);
        textEditprofCurrency.setText(currency);

        textEditprofFamilyValues.setText(familyValues);
        textEditprofFamilyStatus.setText(familyStatus);
        textEditprofFamilyType.setText(familyType);
        textEditprofCountry.setText(country);

    }

    @Override
    public void successCallBack(String msg, String tag) {
        if (tag.equals(Constants.TAG_GET_MASTERS)) {
            setEditProfileData();
        } else if (tag.equals(Constants.TAG_UPDATE_PROFILE)) {
            layoutPreogress.setVisibility(View.GONE);
            Toast.makeText(this, "Profile update successfully !!", Toast.LENGTH_SHORT).show();
            upDateObject();
            finish();
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        layoutPreogress.setVisibility(View.GONE);
    }

    private void setEditProfileData() {

        spinnerHeight.setAdapter(new SpinnerAdapter(this, regSpinnersData.getHeightList()));
        spinnerCountry.setAdapter(new CustomSpinnerAdapter(this, regSpinnersData.getCountriesList(), regSpinnersData.COUNTRY));
        spinnerFamilyStatus.setAdapter(new CustomSpinnerAdapter(this, regSpinnersData.getFamilyStatus(), regSpinnersData.FAMILYSTATUS));
        spinnerCurrency.setAdapter(new CustomSpinnerAdapter(this, regSpinnersData.getCurrencyList(), regSpinnersData.CURRENCY));
        spinnerFamilyValues.setAdapter(new CustomSpinnerAdapter(this, regSpinnersData.getFamilyValues(), regSpinnersData.FAMILYVALUES));
        spinnerEmployedIn.setAdapter(new CustomSpinnerAdapter(this, regSpinnersData.getEmployedInList(), regSpinnersData.EMPLOYEDIN));
        spinnerOccupation.setAdapter(new CustomSpinnerAdapter(this, regSpinnersData.getOccupationList(), regSpinnersData.OCCUPATION));
        spinnerEducation.setAdapter(new CustomSpinnerAdapter(this, regSpinnersData.getEducationList(), regSpinnersData.EDUCATION));

        final List<String> doshamList = Constants.getArraylistFromArray(RegSpinnersStaticData.doshamArray);
        spinnerDosham.setAdapter(new SpinnerAdapter(this, doshamList));

        spinnerCaste.setAdapter(new CustomSpinnerAdapter(this, regSpinnersData.getCastsList(), regSpinnersData.CASTE));
        spinnerReligion.setAdapter(new CustomSpinnerAdapter(this, regSpinnersData.getReligionsList(), regSpinnersData.RELIGION));
        spinnerPhysicalStatus.setAdapter(new CustomSpinnerAdapter(this, regSpinnersData.getPhysicalStatusList(), regSpinnersData.PHYSICALSTATUS));
        spinnerCreatedBy.setAdapter(new CustomSpinnerAdapter(this, regSpinnersData.getProfileForList(), regSpinnersData.PROFILEFOR));
        spinnerMotherTongue.setAdapter(new CustomSpinnerAdapter(this, regSpinnersData.getMotherTongueList(), regSpinnersData.MOTHERTOUNGUE));
        spinnerMaritalStatus.setAdapter(new CustomSpinnerAdapter(this, regSpinnersData.getMaritalStatusList(), regSpinnersData.MARITALSTATUS));

        editName.setText(name);
        editAnnualIncome.setText(annualIncome);

        int index = 0;
        index = regSpinnersData.getHeightList().contains(height) ? regSpinnersData.getHeightList().indexOf(height) : 0;
        spinnerHeight.setSelection(index);
        index = doshamList.contains(dosham) ? doshamList.indexOf(dosham) : 0;
        spinnerDosham.setSelection(index);
        index = Constants.getIndexFromHashMap(regSpinnersData.NAME, regSpinnersData.getCountriesList(), country);
        spinnerCountry.setSelection(index);
        index = Constants.getIndexFromHashMap(regSpinnersData.VALUE, regSpinnersData.getMaritalStatusList(), maritalStatus);
        spinnerMaritalStatus.setSelection(index);
        maritalStatusId = regSpinnersData.getMaritalStatusList().get(spinnerMaritalStatus.getSelectedItemPosition()).get(regSpinnersData.ID);

        index = Constants.getIndexFromHashMap(regSpinnersData.VALUE, regSpinnersData.getCurrencyList(), currency);
        spinnerCurrency.setSelection(index);
        currencyId = regSpinnersData.getCurrencyList().get(spinnerCurrency.getSelectedItemPosition()).get(regSpinnersData.ID);

        index = Constants.getIndexFromHashMap(regSpinnersData.VALUE, regSpinnersData.getFamilyValues(), familyValues);
        spinnerFamilyValues.setSelection(index);
        familyValuesId = regSpinnersData.getFamilyValues().get(spinnerFamilyValues.getSelectedItemPosition()).get(regSpinnersData.ID);

        index = Constants.getIndexFromHashMap(regSpinnersData.NAME, regSpinnersData.getEmployedInList(), employedIn);
        spinnerEmployedIn.setSelection(index);
        employedInId = regSpinnersData.getEmployedInList().get(spinnerEmployedIn.getSelectedItemPosition()).get(regSpinnersData.ID);

        index = Constants.getIndexFromHashMap(regSpinnersData.NAME, regSpinnersData.getOccupationList(), occupation);
        spinnerOccupation.setSelection(index);
        occupationId = regSpinnersData.getOccupationList().get(spinnerOccupation.getSelectedItemPosition()).get(regSpinnersData.ID);

        index = Constants.getIndexFromHashMap(regSpinnersData.NAME, regSpinnersData.getEducationList(), education);
        spinnerEducation.setSelection(index);
        educationId = regSpinnersData.getEducationList().get(spinnerEducation.getSelectedItemPosition()).get(regSpinnersData.ID);

        index = Constants.getIndexFromHashMap(regSpinnersData.NAME, regSpinnersData.getCastsList(), caste);
        spinnerCaste.setSelection(index);
        casteId = regSpinnersData.getCastsList().get(spinnerCaste.getSelectedItemPosition()).get(regSpinnersData.ID);

        index = Constants.getIndexFromHashMap(regSpinnersData.NAME, regSpinnersData.getReligionsList(), religion);
        spinnerReligion.setSelection(index);
        religionId = regSpinnersData.getReligionsList().get(spinnerReligion.getSelectedItemPosition()).get(regSpinnersData.ID);

        index = Constants.getIndexFromHashMap(regSpinnersData.VALUE, regSpinnersData.getPhysicalStatusList(), physicalStatus);
        spinnerPhysicalStatus.setSelection(index);
        physicalStatusId = regSpinnersData.getPhysicalStatusList().get(spinnerPhysicalStatus.getSelectedItemPosition()).get(regSpinnersData.ID);

        index = Constants.getIndexFromHashMap(regSpinnersData.NAME, regSpinnersData.getProfileForList(), profileFor);
        spinnerCreatedBy.setSelection(index);
        profileForId = regSpinnersData.getProfileForList().get(spinnerCreatedBy.getSelectedItemPosition()).get(regSpinnersData.ID);

        index = Constants.getIndexFromHashMap(regSpinnersData.NAME, regSpinnersData.getMotherTongueList(), motherTongue);
        spinnerMotherTongue.setSelection(index);
        motherTongueId = regSpinnersData.getMotherTongueList().get(spinnerMotherTongue.getSelectedItemPosition()).get(regSpinnersData.ID);

        index = Constants.getIndexFromHashMap(regSpinnersData.VALUE, regSpinnersData.getFamilyStatus(), familyStatus);
        spinnerFamilyStatus.setSelection(index);
        familyStatusId = regSpinnersData.getFamilyStatus().get(spinnerFamilyStatus.getSelectedItemPosition()).get(regSpinnersData.ID);

        if (familyType.equals("Joint")) {
            radioButtonJoint.setChecked(true);
            familyTypeId = regSpinnersData.getFamilyType().get(0).get(regSpinnersData.ID);
        } else if (familyType.equals("Nuclear")) {
            radioButtonNuclear.setChecked(true);
            familyTypeId = regSpinnersData.getFamilyType().get(1).get(regSpinnersData.ID);
        }

    }

    public void updateProfile() {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(UserRegData.USERID, userId);
            jsonObject.put(UserRegData.PROFILEFOR, profileForId);
            jsonObject.put(UserRegData.NAME, name);
            jsonObject.put(UserRegData.GENDER, gender);
            jsonObject.put(UserRegData.DOB, dateOfBirth);
            jsonObject.put(UserRegData.RELIGION, religionId);
            jsonObject.put(UserRegData.MOTHERTONGUE, motherTongueId);
            jsonObject.put(UserRegData.COUNTRYCODE, countryCode);
            jsonObject.put(UserRegData.PHONENUMBER, phoneNumber);
            jsonObject.put(UserRegData.EMAIL, email);
            jsonObject.put(UserRegData.MARITALSTATUS, maritalStatusId);
            jsonObject.put(UserRegData.CASTE, casteId);
            jsonObject.put(UserRegData.DOSHAM, dosham);
            //    jsonObject.put(UserRegData.MARRYOTHERCASTE,maritalStatusId);
            jsonObject.put(UserRegData.HEIGHT, height);
            jsonObject.put(UserRegData.PHYSICALSTATUS, physicalStatus);
            jsonObject.put(UserRegData.EDUCATION, educationId);
            jsonObject.put(UserRegData.OCCUPATION, occupationId);
            jsonObject.put(UserRegData.EMPLOYEDIN, employedInId);
            jsonObject.put(UserRegData.CURRENCY, currencyId);
            jsonObject.put(UserRegData.ANNUALINCOME, annualIncome);
            jsonObject.put(UserRegData.FAMILYSTATUS, familyStatusId);
            jsonObject.put(UserRegData.FAMILYTYPE, familyTypeId);
            jsonObject.put(UserRegData.FAMILYVALUES, familyValuesId);
            jsonObject.put(UserRegData.ABOUT, description);
            jsonObject.put(UserRegData.LATITUDE, lat);
            jsonObject.put(UserRegData.LONGITUDE, lng);
            jsonObject.put(UserRegData.SUBLOCALITY, sublocality);
            jsonObject.put(UserRegData.LOCALITY, locality);
            jsonObject.put(UserRegData.ADMINISTRATIVEAREA, administrativeArea);
            jsonObject.put(UserRegData.COUNTRY, country);
            jsonObject.put(UserRegData.FORMATTEDADDRESS, address);
            jsonObject.put(UserRegData.PINCODE, pincode);

            profileDataManager.getUpdateProfile(profileDataManager.getUpdateProfileParams(deviceId, jsonObject));

        } catch (Exception e) {

        }
    }

    public void upDateObject() {

        try {

            UserRegData userRegData = UserRegData.getInstance();
            userRegData.regDataObject = new JSONObject();

            userRegData.regDataObject.put(UserRegData.USERID, userId);
            userRegData.regDataObject.put(UserRegData.EMAIL, email);
            userRegData.regDataObject.put(UserRegData.PHONENUMBER, phoneNumber);
            userRegData.regDataObject.put(UserRegData.PROFILEFOR, profileFor);
            userRegData.regDataObject.put(UserRegData.GENDER, gender);
            userRegData.regDataObject.put(UserRegData.DOB, dateOfBirth);
            userRegData.regDataObject.put(UserRegData.PROFILEID, profileId);
            userRegData.regDataObject.put(UserRegData.FULLNAME, name);

            userRegData.regDataObject.put(userRegData.HEIGHT, height);
            userRegData.regDataObject.put(userRegData.PHYSICALSTATUS, physicalStatus);
            userRegData.regDataObject.put(userRegData.MARITALSTATUS, maritalStatus);
            userRegData.regDataObject.put(userRegData.MOTHERTONGUE, motherTongue);
            userRegData.regDataObject.put(userRegData.CURRENCY, currency);
            userRegData.regDataObject.put(userRegData.ANNUALINCOME, annualIncome);
            userRegData.regDataObject.put(userRegData.FAMILYSTATUS, familyStatus);
            userRegData.regDataObject.put(userRegData.FAMILYTYPE, familyType);
            userRegData.regDataObject.put(userRegData.FAMILYVALUES, familyValues);
            userRegData.regDataObject.put(userRegData.ABOUT_COMMENT, description);

            userRegData.regDataObject.put(userRegData.RELIGION, religion);
            userRegData.regDataObject.put(userRegData.CASTE, caste);
            userRegData.regDataObject.put(userRegData.DOSHAM, dosham);
            userRegData.regDataObject.put(userRegData.MARRYOTHERCASTE, maryOtherCaste);

            userRegData.regDataObject.put(userRegData.EDUCATIONNAME, education);
            userRegData.regDataObject.put(userRegData.OCCUPATIONNAME, occupation);
            userRegData.regDataObject.put(userRegData.EMPLOYEDINNAME, employedIn);

            userRegData.regDataObject.put(userRegData.LATITUDE, lat);
            userRegData.regDataObject.put(userRegData.LONGITUDE, lng);
            userRegData.regDataObject.put(userRegData.LOCALITY, locality);
            userRegData.regDataObject.put(userRegData.SUBLOCALITY, sublocality);
            userRegData.regDataObject.put(userRegData.ADMINISTRATIVEAREA, administrativeArea);
            userRegData.regDataObject.put(userRegData.PINCODE, pincode);
            userRegData.regDataObject.put(userRegData.COUNTRY, country);
            userRegData.regDataObject.put(userRegData.COUNTRYCODE, countryCode);
            userRegData.regDataObject.put(userRegData.FORMATTEDADDRESS, address);

            sharedPreference.putSharedPrefData(Constants.USERDATA, userRegData.regDataObject.toString());

        } catch (Exception e) {

        }

    }
}
