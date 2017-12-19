package com.lifejodi.login.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.lifejodi.R;
import com.lifejodi.login.adapter.SpinnerAdapter;
import com.lifejodi.utils.customfonts.CustomButtonBeatles;
import com.lifejodi.utils.customfonts.CustomEditBeatles;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ajay on 07-12-2017.
 */

public class RegScreen4Fragment extends Fragment {

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
    Unbinder unbinder;

    ArrayList<String> heightList = new ArrayList<>();
    ArrayList<String> physicalStatusList = new ArrayList<>();
    ArrayList<String> educationList = new ArrayList<>();
    ArrayList<String> occupationList = new ArrayList<>();
    ArrayList<String> employedInList = new ArrayList<>();
    ArrayList<String> familyStatusList = new ArrayList<>();
    ArrayList<String> familyValuesList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reg_screen4, null);
        unbinder = ButterKnife.bind(this, view);

        heightList.clear();
        physicalStatusList.clear();
        educationList.clear();
        occupationList.clear();
        educationList.clear();
        familyStatusList.clear();
        familyValuesList.clear();

        heightList.add("Height");
        physicalStatusList.add("Physical Status");
        physicalStatusList.add("Normal");
        educationList.add("Education");
        occupationList.add("Occupation");
        employedInList.add("Employed in");
        employedInList.add("Government");
        employedInList.add("Defence");
        employedInList.add("Private");
        employedInList.add("Business");
        employedInList.add("Self Employed ");
        familyStatusList.add("Family Status");
        familyValuesList.add("Family Values");

     /*   spinnerHeight.setAdapter(new SpinnerAdapter(heightList));
        spinnerPhysicalStatus.setAdapter(new SpinnerAdapter(physicalStatusList));
        spinnerEducation.setAdapter(new SpinnerAdapter(educationList));
        spinnerOccupation.setAdapter(new SpinnerAdapter(occupationList));
        spinnerEmployedIn.setAdapter(new SpinnerAdapter(employedInList));
        spinnerFamilyStatus.setAdapter(new SpinnerAdapter(familyStatusList));
        spinnerFamilyValues.setAdapter(new SpinnerAdapter(familyValuesList));
*/
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
