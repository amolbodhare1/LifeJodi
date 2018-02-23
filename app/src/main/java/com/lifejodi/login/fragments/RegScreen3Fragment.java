package com.lifejodi.login.fragments;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.login.activity.GetCurrentLocationActivity;
import com.lifejodi.login.adapter.CustomSpinnerAdapter;
import com.lifejodi.login.adapter.SpinnerAdapter;
import com.lifejodi.login.data.RegSpinnersData;
import com.lifejodi.login.data.RegSpinnersStaticData;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.login.interfaces.SetRegistrationFragment;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;
import com.lifejodi.utils.customfonts.CustomButtonBeatles;
import com.lifejodi.utils.customfonts.CustomTextBeatles;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Ajay on 07-12-2017.
 */

public class RegScreen3Fragment extends Fragment{

    @BindView(R.id.spinner_marital_status)
    Spinner spinnerMaritalStatus;
    @BindView(R.id.spinner_caste)
    Spinner spinnerCaste;
    @BindView(R.id.spinner_dosham)
    Spinner spinnerDosham;
    @BindView(R.id.radiogroup_other_communities)
    RadioGroup radiogroupOtherCommunities;
    @BindView(R.id.button_continue)
    CustomButtonBeatles buttonContinue;
    RadioButton radioButton;
    Unbinder unbinder;
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;
    @BindView(R.id.spinner_current_location)
    Spinner spinnerCurrentLocation;
    public static TextView textGetCurrentLocation;

    private boolean fragmentResume = false;
    private boolean fragmentVisible = false;
    private boolean fragmentOnCreated = false;

    String maritalStatus = "", dosham = "", country = "", cast = "", willingtoMarryOtherCommunities = "";
    String maritalStatusId = "", castId = "", willingtoMarryOtherCommunitiesId = "";
    RegSpinnersData regSpinnersData = RegSpinnersData.getInstance();
    UserRegData userRegData = UserRegData.getInstance();
    SetRegistrationFragment setRegistrationFragment;
    View view;
    SharedPreference sharedPreference;
    CustomSpinnerAdapter customSpinnerAdapter;

    ArrayList<HashMap<String, String>> maritalStatusList = new ArrayList<>();
    ArrayList<HashMap<String, String>> casteList = new ArrayList<>();
    List<String> doshamList = new ArrayList<>();
    ArrayList<HashMap<String, String>> countriesList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reg_screen3, null);
        unbinder = ButterKnife.bind(this, view);

        initialization(view);
        if (!fragmentResume && fragmentVisible) {

        }
        return view;
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

    public void initialization(View v) {
        textGetCurrentLocation = (TextView)v.findViewById(R.id.text_get_current_location);
        sharedPreference = SharedPreference.getSharedInstance();
        sharedPreference.initialize(getActivity());

        maritalStatusList = regSpinnersData.getMaritalStatusList();
        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), maritalStatusList, regSpinnersData.MARITALSTATUS);
        spinnerMaritalStatus.setAdapter(customSpinnerAdapter);

        casteList = regSpinnersData.getCastsList();
        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), casteList, regSpinnersData.CASTE);
        spinnerCaste.setAdapter(customSpinnerAdapter);

        countriesList = regSpinnersData.getCountriesList();
        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), countriesList, regSpinnersData.COUNTRY);
        spinnerCurrentLocation.setAdapter(customSpinnerAdapter);

        doshamList = Constants.getArraylistFromArray(RegSpinnersStaticData.doshamArray);
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getActivity(), doshamList);
        spinnerDosham.setAdapter(spinnerAdapter);


        setListeners();

    }

    public void setListeners() {
        radiogroupOtherCommunities.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton = (RadioButton) view.findViewById(i);
                willingtoMarryOtherCommunities = radioButton.getText().toString();
                if(willingtoMarryOtherCommunities.equalsIgnoreCase("Yes"))
                {
                    willingtoMarryOtherCommunitiesId = "1";
                }else if(willingtoMarryOtherCommunities.equalsIgnoreCase("No"))
                {
                    willingtoMarryOtherCommunities = "0";
                }
            }
        });

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAllFields();
            }
        });

        textGetCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationManager locationManager = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    //location service is enabled

                    Intent intent = new Intent(getActivity(), GetCurrentLocationActivity.class);
                    getActivity().startActivity(intent);

                } else {
                    //location service is disabled
                    Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent1);


                }

            }
        });

        spinnerMaritalStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                maritalStatus = maritalStatusList.get(pos).get(regSpinnersData.VALUE);
                maritalStatusId = maritalStatusList.get(pos).get(regSpinnersData.ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCaste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                cast = casteList.get(pos).get(regSpinnersData.NAME);
                castId = casteList.get(pos).get(regSpinnersData.ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerDosham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                dosham = doshamList.get(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCurrentLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                country = countriesList.get(pos).get(regSpinnersData.NAME);
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


    public void checkAllFields() {
        if (maritalStatus.equalsIgnoreCase("") || maritalStatus.equalsIgnoreCase(getResources().getString(R.string.select_marital_status))) {
            Toast.makeText(getActivity(), "Select marital status", Toast.LENGTH_SHORT).show();
        } else {
            if (cast.equals("") || cast.equalsIgnoreCase(getResources().getString(R.string.select_cast))) {
                Toast.makeText(getActivity(), "Select cast", Toast.LENGTH_SHORT).show();
            } else {
                if (dosham.equalsIgnoreCase("") || dosham.equalsIgnoreCase("Dosham")) {
                    Toast.makeText(getActivity(), "Select dosham", Toast.LENGTH_SHORT).show();
                } else {
                    if (willingtoMarryOtherCommunities.equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Select if willing to marry from other communities or not", Toast.LENGTH_SHORT).show();
                    } else {
                        if (country.equalsIgnoreCase("") || country.equalsIgnoreCase(getResources().getString(R.string.select_country))) {
                            Toast.makeText(getActivity(), "Select country", Toast.LENGTH_SHORT).show();
                        } else {
                            if(textGetCurrentLocation.getText().equals("") || textGetCurrentLocation.getText().equals(getResources().getString(R.string.get_current_location)))
                            {
                                Toast.makeText(getActivity(), "Set current location", Toast.LENGTH_SHORT).show();
                            }else {
                                try {
                                    userRegData.regDataObject.put(userRegData.MARITALSTATUS, maritalStatusId);
                                    userRegData.regDataObject.put(userRegData.MARITALSTATUSNAME, maritalStatus);
                                    userRegData.regDataObject.put(userRegData.CASTE, castId);
                                    userRegData.regDataObject.put(userRegData.CASTENAME, cast);
                                    userRegData.regDataObject.put(userRegData.DOSHAM, dosham);
                                    userRegData.regDataObject.put(userRegData.MARRYOTHERCASTE, willingtoMarryOtherCommunitiesId);
                                    userRegData.regDataObject.put(userRegData.COUNTRY, country);
                                    setRegistrationFragment = (SetRegistrationFragment) getActivity();
                                    setRegistrationFragment.setRegFragment(3);
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

    public static void setCurrentLocation(String text)
    {
        textGetCurrentLocation.setText(text);
    }


}
