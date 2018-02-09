package com.lifejodi.login.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.login.activity.LoginActivity;
import com.lifejodi.login.adapter.CustomSpinnerAdapter;
import com.lifejodi.login.adapter.SpinnerAdapter;
import com.lifejodi.login.data.RegSpinnersData;
import com.lifejodi.login.data.RegSpinnersStaticData;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.login.manager.RegSpinnersManager;
import com.lifejodi.login.manager.UserRegManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;
import com.lifejodi.utils.customfonts.CustomButtonBeatles;
import com.lifejodi.utils.customfonts.CustomEditBeatles;
import com.lifejodi.utils.customfonts.CustomTextBeatles;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ajay on 07-12-2017.
 */

public class RegScreen4Fragment extends Fragment implements AdapterView.OnItemSelectedListener, VolleyCallbackInterface {

    @BindView(R.id.spinner_height)
    Spinner spinnerHeight;
    @BindView(R.id.spinner_physical_status)
    Spinner spinnerPhysicalStatus;
    @BindView(R.id.spinner_education)
    Spinner spinnerEducation;
    @BindView(R.id.spinner_occupation)
    Spinner spinnerOccupation;
    @BindView(R.id.spinner_employed_in)
    Spinner spinnerEmployedIn;
    @BindView(R.id.spinner_currency_code)
    Spinner spinnerCurrencyCode;
    @BindView(R.id.edit_annual_income)
    EditText editAnnualIncome;
    @BindView(R.id.spinner_family_status)
    Spinner spinnerFamilyStatus;
    @BindView(R.id.radiogroup_family_type)
    RadioGroup radiogroupFamilyType;
    @BindView(R.id.spinner_family_values)
    Spinner spinnerFamilyValues;
    @BindView(R.id.edit_about_friend)
    CustomEditBeatles editAboutFriend;
    @BindView(R.id.button_continue)
    CustomButtonBeatles buttonContinue;
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;
    @BindView(R.id.text_about_you)
    CustomTextBeatles textAboutYou;
    Unbinder unbinder;

    ArrayList<HashMap<String,String>> physicalStatusList = new ArrayList<>();
    ArrayList<HashMap<String,String>> educationList = new ArrayList<>();
    ArrayList<HashMap<String,String>> occupationList = new ArrayList<>();
    ArrayList<HashMap<String,String>> currencyList = new ArrayList<>();
    ArrayList<HashMap<String,String>> familyStatusList = new ArrayList<>();
    ArrayList<HashMap<String,String>> familyValuesList = new ArrayList<>();
    ArrayList<String> heightList = new ArrayList<>();
    ArrayList<HashMap<String,String>> employedInList = new ArrayList<>();
    String height = "", physicalStatus = "", education = "", occupation = "", employedIn = "", currency = "", annualIncome = "", familyStatus = "";
    String familyType = "", familyValues = "", aboutFriend = "",profile="",familyTypeId = "";


    RegSpinnersData regSpinnersData = RegSpinnersData.getInstance();
    UserRegManager userRegManager = UserRegManager.getInstance();
    UserRegData userRegData = UserRegData.getInstance();
    SpinnerAdapter spinnerAdapter;
    SharedPreference sharedPreference;
    CustomSpinnerAdapter customSpinnerAdapter;

    View view;
    RadioButton radioButton;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reg_screen4, null);
        unbinder = ButterKnife.bind(this, view);
        initialization();
        return view;
    }

    public void initialization() {
        sharedPreference = SharedPreference.getSharedInstance();
        sharedPreference.initialize(getActivity());
        profile = sharedPreference.getSharedPrefData(Constants.PROFILEFOR);
        if(profile.equalsIgnoreCase("Myself"))
        {
            textAboutYou.setText("About yourself");
            editAboutFriend.setHint("Enter about yourself");
        }else {
            textAboutYou.setText("About your "+profile);
            editAboutFriend.setHint("Enter about your "+profile);
        }
        physicalStatusList = regSpinnersData.getPhysicalStatusList();
        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(),physicalStatusList,regSpinnersData.PHYSICALSTATUS);
        spinnerPhysicalStatus.setAdapter(customSpinnerAdapter);

        educationList = regSpinnersData.getEducationList();
        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(),educationList,regSpinnersData.EDUCATION);
        spinnerEducation.setAdapter(customSpinnerAdapter);

        occupationList = regSpinnersData.getOccupationList();
        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(),occupationList,regSpinnersData.OCCUPATION);
        spinnerOccupation.setAdapter(customSpinnerAdapter);

        currencyList = regSpinnersData.getCurrencyList();
        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(),currencyList,regSpinnersData.CURRENCY);
        spinnerCurrencyCode.setAdapter(customSpinnerAdapter);

        familyStatusList = regSpinnersData.getFamilyStatus();
        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(),familyStatusList,regSpinnersData.FAMILYSTATUS);
        spinnerFamilyStatus.setAdapter(customSpinnerAdapter);

        familyValuesList = regSpinnersData.getFamilyValues();
        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(),familyValuesList,regSpinnersData.FAMILYVALUES);
        spinnerFamilyValues.setAdapter(customSpinnerAdapter);

        employedInList = regSpinnersData.getEmployedInList();
        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(),employedInList,regSpinnersData.EMPLOYEDIN);
        spinnerEmployedIn.setAdapter(customSpinnerAdapter);

        heightList = regSpinnersData.getHeightList();
        SpinnerAdapter spinnerAdapter1 = new SpinnerAdapter(getActivity(),heightList);
        spinnerHeight.setAdapter(spinnerAdapter1);


        setListeners();
    }

    public void setListeners() {
        spinnerHeight.setOnItemSelectedListener(this);
        spinnerPhysicalStatus.setOnItemSelectedListener(this);
        spinnerEducation.setOnItemSelectedListener(this);
        spinnerOccupation.setOnItemSelectedListener(this);
        spinnerEmployedIn.setOnItemSelectedListener(this);
        spinnerCurrencyCode.setOnItemSelectedListener(this);
        spinnerFamilyStatus.setOnItemSelectedListener(this);
        spinnerFamilyValues.setOnItemSelectedListener(this);

        radiogroupFamilyType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton = (RadioButton) view.findViewById(i);
                familyType = radioButton.getText().toString();
                if (familyType.equals("Joint")) {
                        familyTypeId = "1";
                } else if (familyType.equals("Nuclear")){
                    familyTypeId = "0";

            }
            }
        });

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAllFields();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        Spinner spinner = (Spinner) adapterView;
        switch (spinner.getId()) {
            case R.id.spinner_height:
                height = heightList.get(pos);
                break;
            case R.id.spinner_physical_status:
                physicalStatus = physicalStatusList.get(pos).get(regSpinnersData.ID);
                break;
            case R.id.spinner_education:
                education = educationList.get(pos).get(regSpinnersData.ID);
                break;
            case R.id.spinner_occupation:
                occupation = occupationList.get(pos).get(regSpinnersData.ID);
                break;
            case R.id.spinner_employed_in:
                employedIn = employedInList.get(pos).get(regSpinnersData.ID);
                break;
            case R.id.spinner_currency_code:
                currency = currencyList.get(pos).get(regSpinnersData.ID);
                break;
            case R.id.spinner_family_status:
                familyStatus = familyStatusList.get(pos).get(regSpinnersData.ID);
                break;
            case R.id.spinner_family_values:
                familyValues = familyValuesList.get(pos).get(regSpinnersData.ID);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void checkAllFields() {
        annualIncome = editAnnualIncome.getText().toString();
        aboutFriend = editAboutFriend.getText().toString();
        if (height.equalsIgnoreCase("") || height.equalsIgnoreCase(getResources().getString(R.string.select_height))) {
            Toast.makeText(getActivity(), "Select height", Toast.LENGTH_SHORT).show();
        } else {
            if (physicalStatus.equals("") || physicalStatus.equalsIgnoreCase(getResources().getString(R.string.select_physicalstatus))) {
                Toast.makeText(getActivity(), "Select physical status", Toast.LENGTH_SHORT).show();
            } else {
                if (education.equals("") || education.equalsIgnoreCase(getResources().getString(R.string.select_education))) {
                    Toast.makeText(getActivity(), "Select education", Toast.LENGTH_SHORT).show();
                } else {
                    if (occupation.equals("") || occupation.equalsIgnoreCase(getResources().getString(R.string.select_occupation))) {
                        Toast.makeText(getActivity(), "Select occupation", Toast.LENGTH_SHORT).show();
                    } else {
                        if (employedIn.equals("") || employedIn.equalsIgnoreCase("Employed in")) {
                            Toast.makeText(getActivity(), "Select employed in", Toast.LENGTH_SHORT).show();
                        } else {
                            if (currency.equals("") || currency.equals("0")|| currency.equalsIgnoreCase(getResources().getString(R.string.select_currency))) {
                                Toast.makeText(getActivity(), "Select currency", Toast.LENGTH_SHORT).show();
                            } else {
                                if (annualIncome.equalsIgnoreCase("")) {
                                    Toast.makeText(getActivity(), "Enter annual income", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (familyStatus.equals("") || familyStatus.equalsIgnoreCase(getResources().getString(R.string.select_family_status))) {
                                        Toast.makeText(getActivity(), "Select family status", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (familyType.equalsIgnoreCase("")) {
                                            Toast.makeText(getActivity(), "Select family type", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (familyValues.equals("") || familyValues.equalsIgnoreCase(getResources().getString(R.string.select_family_values))) {
                                                Toast.makeText(getActivity(), "Select family values", Toast.LENGTH_SHORT).show();
                                            } else {
                                                if (aboutFriend.equalsIgnoreCase("")) {
                                                    if(profile.equalsIgnoreCase("Myself"))
                                                    {

                                                        Toast.makeText(getActivity(), "Enter about yourself", Toast.LENGTH_SHORT).show();

                                                    }else {

                                                        Toast.makeText(getActivity(), "Enter about your "+profile, Toast.LENGTH_SHORT).show();

                                                    }

                                                } else {
                                                    try {
                                                        userRegData.regDataObject.put(userRegData.HEIGHT, height);
                                                        userRegData.regDataObject.put(userRegData.PHYSICALSTATUS, physicalStatus);
                                                        userRegData.regDataObject.put(userRegData.EDUCATION, education);
                                                        userRegData.regDataObject.put(userRegData.OCCUPATION, occupation);
                                                        userRegData.regDataObject.put(userRegData.EMPLOYEDIN, employedIn);
                                                        userRegData.regDataObject.put(userRegData.CURRENCY, currency);
                                                        userRegData.regDataObject.put(userRegData.ANNUALINCOME, annualIncome);
                                                        userRegData.regDataObject.put(userRegData.FAMILYSTATUS, familyStatus);
                                                        userRegData.regDataObject.put(userRegData.FAMILYTYPE, familyType);
                                                        userRegData.regDataObject.put(userRegData.FAMILYVALUES, familyValues);
                                                        userRegData.regDataObject.put(userRegData.ABOUT, aboutFriend);

                                                        userRegData.regDataObject.put(userRegData.LATITUDE, sharedPreference.getSharedPrefData(Constants.LATITUDE));
                                                        userRegData.regDataObject.put(userRegData.LONGITUDE, sharedPreference.getSharedPrefData(Constants.LONGITUDE));
                                                        userRegData.regDataObject.put(userRegData.LOCALITY, sharedPreference.getSharedPrefData(Constants.LOCALOTY));
                                                        userRegData.regDataObject.put(userRegData.SUBLOCALITY, sharedPreference.getSharedPrefData(Constants.SUBLOCALITY));
                                                        userRegData.regDataObject.put(userRegData.ADMINISTRATIVEAREA, sharedPreference.getSharedPrefData(Constants.ADMINISTRATIVEADDR));
                                                        userRegData.regDataObject.put(userRegData.PINCODE, sharedPreference.getSharedPrefData(Constants.PINCODE));
                                                        userRegData.regDataObject.put(userRegData.FORMATTEDADDRESS, sharedPreference.getSharedPrefData(Constants.FORMATTEDADDR));
                                                        String androidDeviceId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
                                                        userRegManager.initialize(this, getActivity());
                                                        progressLayout.setVisibility(View.VISIBLE);
                                                        userRegManager.registerUser(userRegManager.regUserInputParams(androidDeviceId,userRegData.regDataObject));
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }


                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void successCallBack(String msg, String tag) {
      /*  if (tag.equalsIgnoreCase(Constants.TAG_REGISTER_USER)) {
            progressLayout.setVisibility(View.GONE);
            //String status = userRegData.getRegStatus();
            Toast.makeText(getActivity(), "You are registered successfully.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }*/
        progressLayout.setVisibility(View.GONE);
        Toast.makeText(getActivity(),msg, Toast.LENGTH_SHORT).show();
        /*if(msg.equalsIgnoreCase("Already Registered !") || msg.equalsIgnoreCase("Successfully Registered!!"))
        {
            sharedPreference.putSharedPrefData(Constants.REGSTATUS,"1");
        }*/
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        progressLayout.setVisibility(View.GONE);
        Toast.makeText(getActivity(),msg, Toast.LENGTH_SHORT).show();
    }
}
