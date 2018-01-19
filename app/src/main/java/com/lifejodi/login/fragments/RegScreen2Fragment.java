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

public class RegScreen2Fragment extends Fragment implements VolleyCallbackInterface {


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

    ArrayList<HashMap<String, String>> religionList = new ArrayList<>();
    List<String> motherToungueList = new ArrayList<>();
    List<String> countryCodesList = new ArrayList<>();
    String religion = "", motherTongue = "", mobileNumber = "", countryCode = "", email = "", password = "";

    RegSpinnersManager registrationManager = RegSpinnersManager.getInstance();
    RegSpinnersData registerData = RegSpinnersData.getInstance();
    UserRegData userRegData = UserRegData.getInstance();
    SpinnerAdapter spinnerAdapter;
    CustomSpinnerAdapter customSpinnerAdapter;
    SetRegistrationFragment setRegistrationFragment;
    SharedPreference sharedPreference = SharedPreference.getSharedInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reg_screen2, null);
        unbinder = ButterKnife.bind(this, view);
        initialization();
        if (!fragmentResume && fragmentVisible) {
            registrationManager.initialize(this, getActivity());
            progressLayout.setVisibility(View.VISIBLE);
            registrationManager.getReligions();
        }
        return view;
    }

    public void initialization() {
        sharedPreference.initialize(getActivity());
        motherToungueList = Constants.getArraylistFromArray(RegSpinnersStaticData.motherTongueArray);
        spinnerAdapter = new SpinnerAdapter(getActivity(), motherToungueList);
        spinnerMotherToungue.setAdapter(spinnerAdapter);

        countryCodesList = Constants.getArraylistFromArray(RegSpinnersStaticData.countryCodesArray);
        spinnerAdapter = new SpinnerAdapter(getActivity(), countryCodesList);
        spinnerCode.setAdapter(spinnerAdapter);
        if(sharedPreference.getSharedPrefData(Constants.FACEBOOKSIGNUP).equalsIgnoreCase("1"))
        {
            editEmail.setText(sharedPreference.getSharedPrefData(Constants.FBEMAIL));
        }
        setListeners();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {   // only at fragment screen is resumed
            fragmentResume = true;
            fragmentVisible = false;
            fragmentOnCreated = true;
            registrationManager.initialize(this, getActivity());
            progressLayout.setVisibility(View.VISIBLE);
            registrationManager.getReligions();

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

    public void setListeners() {
        spinnerCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                countryCode = countryCodesList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerMotherToungue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                motherTongue = motherToungueList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void successCallBack(String msg, String tag) {
        if (tag.equalsIgnoreCase(Constants.TAG_GET_RELIGION)) {
            setReligionSpinner();
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        progressLayout.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
    }

    public void setReligionSpinner() {
        progressLayout.setVisibility(View.GONE);
        religionList = registerData.getReligionsList();
        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), religionList, getActivity().getResources().getString(R.string.select_religion), Constants.TAG_GET_RELIGION);
        spinnerReligion.setAdapter(customSpinnerAdapter);
        spinnerReligion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //HashMap<String,String> dataMap = religionList.get(i);
                religion = String.valueOf(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void checkAllFields() {
        mobileNumber = editMobileNo.getText().toString();
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();
        if (religion.equalsIgnoreCase("") || religion.equalsIgnoreCase("0")) {
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
                                    userRegData.regDataObject.put(userRegData.KEY_EMAIL, email);
                                    userRegData.regDataObject.put(userRegData.KEY_CONTACTNUMBER, mobileNumber);
                                    userRegData.regDataObject.put(userRegData.KEY_RELIGION, religion);
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
