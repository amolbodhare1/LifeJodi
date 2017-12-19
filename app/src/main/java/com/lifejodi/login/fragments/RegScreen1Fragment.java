package com.lifejodi.login.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.home.activity.HomeActivity;
import com.lifejodi.login.adapter.SpinnerAdapter;
import com.lifejodi.login.data.RegisterData;
import com.lifejodi.login.data.SpinnersRegistrationData;
import com.lifejodi.login.interfaces.SetRegistrationFragment;
import com.lifejodi.utils.Constants;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Ajay on 11-11-2017.
 */

public class RegScreen1Fragment extends Fragment implements DatePickerDialog.OnDateSetListener {


    @BindView(R.id.layout_fb_signup)
    RelativeLayout layoutFbSignup;
    @BindView(R.id.spinner_profile)
    Spinner spinnerProfile;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.radiogroup_gender)
    RadioGroup radiogroupGender;
    @BindView(R.id.text_dob)
    TextView textDob;
    @BindView(R.id.layout_select_dob)
    LinearLayout layoutSelectDob;
    @BindView(R.id.button_next)
    Button buttonNext;
    RadioButton radioButton;
    Unbinder unbinder;

    private boolean fragmentResume=false;
    private boolean fragmentVisible=false;
    private boolean fragmentOnCreated=false;

    List<String> profileList = new ArrayList<>();
    String profileFor="",name="",gender="",dob="";
    View view;

    SpinnersRegistrationData spinnersRegistrationData = SpinnersRegistrationData.getInstance();
    RegisterData registerData = RegisterData.getInstance();

    SetRegistrationFragment setRegistrationFragment;
    SpinnerAdapter spinnerAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reg_screen1, null);
        unbinder = ButterKnife.bind(this, view);
        initialization();
        if (!fragmentResume && fragmentVisible){

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
        profileList = Constants.getArraylistFromArray(SpinnersRegistrationData.profileForArray);
        spinnerAdapter = new SpinnerAdapter(getActivity(),profileList);
        spinnerProfile.setAdapter(spinnerAdapter);
        setListeners();
    }

    public void setListeners()
    {
        spinnerProfile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                profileFor = String.valueOf(pos-1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        radiogroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton = (RadioButton)view.findViewById(i);
                gender = radioButton.getText().toString();

            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.layout_fb_signup, R.id.layout_select_dob, R.id.button_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_fb_signup:
                break;
            case R.id.layout_select_dob:
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                Calendar calendar = Calendar.getInstance();
                dpd.setMaxDate(calendar);
                dpd.show(getActivity().getFragmentManager(),"");
                break;
            case R.id.button_next:
                checkAllFields();
                break;
        }
    }

    public void checkAllFields()
    {
        name = editName.getText().toString();
        dob = textDob.getText().toString();
        if(profileFor.equalsIgnoreCase("") || profileFor.equalsIgnoreCase("Profile created for")){
            Toast.makeText(getActivity(), "Select profile created for", Toast.LENGTH_SHORT).show();}else{
            if(name.equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Select name", Toast.LENGTH_SHORT).show();}else {
                if(gender.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Select gender", Toast.LENGTH_SHORT).show();}else {
                    if(dob.equalsIgnoreCase("") || dob.equalsIgnoreCase(getResources().getString(R.string.select_dob))) {
                        Toast.makeText(getActivity(), "Select date of birth", Toast.LENGTH_SHORT).show();}else {
                        try {
                            registerData.regDataObject.put(RegisterData.KEY_USERNAME,name);
                            registerData.regDataObject.put(RegisterData.KEY_FIRSTNAME,name);
                            registerData.regDataObject.put(RegisterData.KEY_LASTNAME,"");
                            registerData.regDataObject.put(RegisterData.KEY_MIDDLENAME,"");
                            registerData.regDataObject.put(RegisterData.KEY_GENDER,gender);
                            registerData.regDataObject.put(RegisterData.KEY_DOB,dob);
                            setRegistrationFragment = (SetRegistrationFragment)getActivity();
                            setRegistrationFragment.setRegFragment(1);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear+1;
        textDob.setText(dayOfMonth+"-"+month+"-"+year);
      //  Toast.makeText(getActivity(), ""+dayOfMonth+monthOfYear+year, Toast.LENGTH_SHORT).show();
    }
}
