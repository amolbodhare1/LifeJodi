package com.lifejodi.login.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.login.adapter.CustomSpinnerAdapter;
import com.lifejodi.login.adapter.SpinnerAdapter;
import com.lifejodi.login.data.RegSpinnersData;
import com.lifejodi.login.data.RegSpinnersStaticData;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.login.interfaces.SetRegistrationFragment;
import com.lifejodi.login.manager.RegSpinnersManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;
import com.lifejodi.utils.customfonts.CustomButtonBeatles;
import com.lifejodi.utils.customfonts.CustomEditBeatles;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Ajay on 11-11-2017.
 */

public class RegScreen2Fragment extends Fragment{


    @BindView(R.id.spinner_religion)
    Spinner spinnerReligion;
    @BindView(R.id.spinner_mother_toungue)
    Spinner spinnerMotherToungue;
    @BindView(R.id.spinner_code)
    Spinner spinnerCode;
    @BindView(R.id.edit_mobile_no)
    EditText editMobileNo;
    @BindView(R.id.edit_email_id)
    CustomEditBeatles editEmail;
    @BindView(R.id.edit_password)
    CustomEditBeatles editPassword;
    @BindView(R.id.button_register)
    CustomButtonBeatles buttonRegister;
    Unbinder unbinder;
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;

    private boolean fragmentResume = false;
    private boolean fragmentVisible = false;
    private boolean fragmentOnCreated = false;

    String religion = "", motherTongue = "", mobileNumber = "", countryCode = "", email = "", password = "";
    String religionId="",motherTongueId = "";
    ArrayList<HashMap<String,String>> religionsList = new ArrayList<>();
    ArrayList<HashMap<String,String>> motherToungueList = new ArrayList<>();
    ArrayList<HashMap<String,String>> countryCodesList = new ArrayList<>();

    RegSpinnersData regSpinnersData = RegSpinnersData.getInstance();
    UserRegData userRegData = UserRegData.getInstance();

    SetRegistrationFragment setRegistrationFragment;
    SharedPreference sharedPreference = SharedPreference.getSharedInstance();
    CustomSpinnerAdapter customSpinnerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reg_screen2, null);
        unbinder = ButterKnife.bind(this, view);
        initialization();
        if (!fragmentResume && fragmentVisible) {

        }
        return view;
    }

    public void initialization() {
        sharedPreference.initialize(getActivity());
        if(sharedPreference.getSharedPrefData(Constants.FACEBOOKSIGNUP).equalsIgnoreCase("1"))
        {
            editEmail.setText(sharedPreference.getSharedPrefData(Constants.FBEMAIL));
        }
        religionsList = regSpinnersData.getReligionsList();
        motherToungueList = regSpinnersData.getMotherTongueList();
        countryCodesList = regSpinnersData.getCountriesList();
        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(),religionsList,regSpinnersData.RELIGION);
        spinnerReligion.setAdapter(customSpinnerAdapter);
        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(),motherToungueList,regSpinnersData.MOTHERTOUNGUE);
        spinnerMotherToungue.setAdapter(customSpinnerAdapter);
        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(),countryCodesList,regSpinnersData.COUNTRYCODE);
        spinnerCode.setAdapter(customSpinnerAdapter);
        setListeners();

    }

    public void setListeners()
    {
        spinnerReligion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                religion = religionsList.get(pos).get(regSpinnersData.NAME);
                religionId = religionsList.get(pos).get(regSpinnersData.ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerMotherToungue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                motherTongue = motherToungueList.get(pos).get(regSpinnersData.NAME);
                motherTongueId = motherToungueList.get(pos).get(regSpinnersData.ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                countryCode = countryCodesList.get(pos).get(regSpinnersData.COUNTRYCODE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {   // only at fragment screen is resumed
            fragmentResume = true;
            fragmentVisible = false;
            fragmentOnCreated = true;

        } else if (isVisibleToUser) {        // only at fragment onCreated
            fragmentResume = false;
            fragmentVisible = true;
            fragmentOnCreated = true;


        } else if (!isVisibleToUser && fragmentOnCreated) {// only when you go out of fragment screen
            fragmentVisible = false;
            fragmentResume = false;
        }
    }

    @OnClick({R.id.button_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_register:
                checkAllFields();
                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void checkAllFields() {
        mobileNumber = editMobileNo.getText().toString();
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();
        if (religion.equalsIgnoreCase("") || religion.equalsIgnoreCase(getResources().getString(R.string.select_religion))) {
            Toast.makeText(getActivity(), "Select religion", Toast.LENGTH_SHORT).show();
        } else {
            if (motherTongue.equalsIgnoreCase("") || motherTongue.equalsIgnoreCase(getActivity().getResources().getString(R.string.select_mother_tongue))) {
                Toast.makeText(getActivity(), "Select mother tongue", Toast.LENGTH_SHORT).show();
            } else {
                if (countryCode.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Select country code", Toast.LENGTH_SHORT).show();
                } else {
                    if (mobileNumber.equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Enter mobile number", Toast.LENGTH_SHORT).show();
                    } else {
                        if (email.equalsIgnoreCase("") || (!Constants.isEmailValid(email))) {
                            Toast.makeText(getActivity(), "Enter valid email id", Toast.LENGTH_SHORT).show();
                        } else {
                            if (password.equalsIgnoreCase("")) {
                                Toast.makeText(getActivity(), "Enter password", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    sharedPreference.putSharedPrefData(Constants.SAVEDEMAIL,email);
                                    sharedPreference.putSharedPrefData(Constants.SAVEDPASSWORD,password);
                                    sharedPreference.putSharedPrefData(Constants.SAVEDMOBILE,mobileNumber);

                                    userRegData.regDataObject.put(userRegData.RELIGION, religionId);
                                    userRegData.regDataObject.put(userRegData.MOTHERTONGUE, motherTongueId);
                                    userRegData.regDataObject.put(userRegData.COUNTRYCODE, countryCode);
                                    userRegData.regDataObject.put(userRegData.PHONENUMBER, mobileNumber);
                                    userRegData.regDataObject.put(userRegData.EMAIL, email);
                                    userRegData.regDataObject.put(userRegData.PASSWORD, password);

                                    setRegistrationFragment = (SetRegistrationFragment) getActivity();
                                    setRegistrationFragment.setRegFragment(2);
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
