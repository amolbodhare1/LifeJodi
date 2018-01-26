package com.lifejodi.login.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
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
import android.widget.Toast;

import com.lifejodi.R;
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

/**
 * Created by Ajay on 07-12-2017.
 */

public class RegScreen3Fragment extends Fragment implements AdapterView.OnItemSelectedListener{

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

    private boolean fragmentResume = false;
    private boolean fragmentVisible = false;
    private boolean fragmentOnCreated = false;

    String maritalStatus = "", dosham = "", country = "", cast = "", willingtoMarryOtherCommunities = "";

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

        initialization();
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

    public void initialization() {
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
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getActivity(),doshamList);
        spinnerDosham.setAdapter(spinnerAdapter);


        setListeners();

    }

    public void setListeners() {
        radiogroupOtherCommunities.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton = (RadioButton) view.findViewById(i);
                willingtoMarryOtherCommunities = radioButton.getText().toString();
            }
        });

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAllFields();
            }
        });

       /* textGetCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocation();
            }
        });*/
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
                            try {
                                userRegData.regDataObject.put(userRegData.MARITALSTATUS, maritalStatus);
                                userRegData.regDataObject.put(userRegData.CASTE, cast);
                                userRegData.regDataObject.put(userRegData.DOSHAM, dosham);
                                userRegData.regDataObject.put(userRegData.MARRYOTHERCASTE, willingtoMarryOtherCommunities);
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

    public void setLocation() {
        String latitude = sharedPreference.getSharedPrefData(Constants.LATITUDE);
        String longitude = sharedPreference.getSharedPrefData(Constants.LONGITUDE);
        if (latitude != "" && longitude != "") {
            double lats = Double.valueOf(latitude);
            double longis = Double.valueOf(longitude);
            Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(lats, longis, 1);
                if (addresses.size() > 0) {
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String locality = addresses.get(0).getLocality();
                    String subLocality = addresses.get(0).getSubLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                 //   textGetCurrentLocation.setText(locality + "," + state + "," + country);

                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Error fetching location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        Spinner spinner = (Spinner) adapterView;
        switch (spinner.getId()) {
            case R.id.spinner_marital_status:
                maritalStatus = maritalStatusList.get(pos).get(regSpinnersData.VALUE);
                break;
            case R.id.spinner_caste:
                cast = casteList.get(pos).get(regSpinnersData.NAME);
                break;
            case R.id.spinner_dosham:
                dosham = doshamList.get(pos);
                break;
            case R.id.spinner_current_location:
                country = countriesList.get(pos).get(regSpinnersData.NAME);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
