package com.lifejodi.login.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.login.adapter.CustomSpinnerAdapter;
import com.lifejodi.login.adapter.SpinnerAdapter;
import com.lifejodi.login.data.RegisterData;
import com.lifejodi.login.data.SpinnersRegistrationData;
import com.lifejodi.login.manager.RegistrationManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.customfonts.CustomButtonBeatles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ajay on 07-12-2017.
 */

public class RegScreen3Fragment extends Fragment implements VolleyCallbackInterface {

    @BindView(R.id.spinner_marital_status)
    Spinner spinnerMaritalStatus;
    @BindView(R.id.spinner_caste)
    Spinner spinnerCaste;
    @BindView(R.id.spinner_dosham)
    Spinner spinnerDosham;
    @BindView(R.id.radiogroup_other_communities)
    RadioGroup radiogroupOtherCommunities;
    @BindView(R.id.spinner_current_location)
    Spinner spinnerCurrentLocation;
    @BindView(R.id.button_continue)
    CustomButtonBeatles buttonContinue;
    RadioButton radioButton;
    Unbinder unbinder;

    private boolean fragmentResume=false;
    private boolean fragmentVisible=false;
    private boolean fragmentOnCreated=false;

    ArrayList<HashMap<String,String>> maritalStatusList = new ArrayList<>();
    ArrayList<HashMap<String,String>> casteList = new ArrayList<>();
    List<String> doshamList = new ArrayList<>();
    List<String> livingCountryList = new ArrayList<>();
    String maritalStatus,dosham,country,cast,willingtoMarryOtherCommunities;

    RegisterData registerData = RegisterData.getInstance();
    RegistrationManager registrationManager = RegistrationManager.getInstance();
    SpinnerAdapter spinnerAdapter;
    CustomSpinnerAdapter customSpinnerAdapter;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_reg_screen3, null);
        unbinder = ButterKnife.bind(this, view);

        initialization();
        if (!fragmentResume && fragmentVisible){
            registrationManager.initialize(this,getActivity());
            registrationManager.getCasts();

        }
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()){   // only at fragment screen is resumed
            fragmentResume=true;
            fragmentVisible=false;
            fragmentOnCreated=true;
            registrationManager.initialize(this,getActivity());
            registrationManager.getCasts();


        }else  if (isVisibleToUser){        // only at fragment onCreated
            fragmentResume=false;
            fragmentVisible=true;
            fragmentOnCreated=true;


        }
        else if(!isVisibleToUser && fragmentOnCreated){// only when you go out of fragment screen
            fragmentVisible=false;
            fragmentResume=false;
        }
    }

    public void initialization()
    {
        doshamList = Constants.getArraylistFromArray(SpinnersRegistrationData.doshamArray);
        spinnerAdapter = new SpinnerAdapter(getActivity(),doshamList);
        spinnerDosham.setAdapter(spinnerAdapter);

        livingCountryList = Constants.getArraylistFromArray(SpinnersRegistrationData.countryLivingInArray);
        spinnerAdapter = new SpinnerAdapter(getActivity(),livingCountryList);
        spinnerCurrentLocation.setAdapter(spinnerAdapter);

        setListeners();

    }
    public void setListeners()
    {
        spinnerDosham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dosham = doshamList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCurrentLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country = livingCountryList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        radiogroupOtherCommunities.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
              radioButton = (RadioButton)view.findViewById(i);
              willingtoMarryOtherCommunities = radioButton.getText().toString();
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
        if(tag.equalsIgnoreCase(Constants.TAG_GET_CAST)) {
            setCastSpinner();
            registrationManager.initialize(this,getActivity());
            registrationManager.getMaritalStatus();
        }else if(tag.equalsIgnoreCase(Constants.TAG_GET_MARITALSTATUS)){
            setMarritalStatusSpinner();
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
    }

    public void setCastSpinner()
    {
        casteList = registerData.getCastsList();
        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(),casteList,getActivity().getResources().getString(R.string.select_cast),Constants.TAG_GET_CAST);
        spinnerCaste.setAdapter(customSpinnerAdapter);
        spinnerCaste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //HashMap<String,String> dataMap = religionList.get(i);
                cast = String.valueOf(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setMarritalStatusSpinner()
    {
        maritalStatusList = registerData.getMaritalStatusList();
        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(),maritalStatusList,getActivity().getResources().getString(R.string.select_marital_status),Constants.TAG_GET_MARITALSTATUS);
       spinnerMaritalStatus.setAdapter(customSpinnerAdapter);
       spinnerMaritalStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               maritalStatus = String.valueOf(i);
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
    }
}
