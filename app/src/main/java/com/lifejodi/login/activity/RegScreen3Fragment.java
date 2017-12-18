package com.lifejodi.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.lifejodi.R;
import com.lifejodi.login.adapter.SpinnerAdapter;
import com.lifejodi.utils.customfonts.CustomButtonBeatles;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ajay on 07-12-2017.
 */

public class RegScreen3Fragment extends Fragment {

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
    Unbinder unbinder;

    ArrayList<String> maritalStatusList = new ArrayList<>();
    ArrayList<String> casteList = new ArrayList<>();
    ArrayList<String> doshamList = new ArrayList<>();
    ArrayList<String> livingCountryList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reg_screen3, null);
        unbinder = ButterKnife.bind(this, view);

        maritalStatusList.clear();
        casteList.clear();
        doshamList.clear();
        livingCountryList.clear();

        maritalStatusList.add("Marital Status");
        casteList.add("Caste");
        doshamList.add("Having dosham (optional)");
        livingCountryList.add("Country living in");

        spinnerMaritalStatus.setAdapter(new SpinnerAdapter(maritalStatusList));
        spinnerCaste.setAdapter(new SpinnerAdapter(casteList));
        spinnerDosham.setAdapter(new SpinnerAdapter(doshamList));
        spinnerCurrentLocation.setAdapter(new SpinnerAdapter(livingCountryList));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
