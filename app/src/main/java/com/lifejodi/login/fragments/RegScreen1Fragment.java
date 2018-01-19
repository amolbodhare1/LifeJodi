package com.lifejodi.login.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.lifejodi.R;
import com.lifejodi.login.adapter.SpinnerAdapter;
import com.lifejodi.login.data.RegSpinnersData;
import com.lifejodi.login.data.RegSpinnersStaticData;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.login.interfaces.SetRegistrationFragment;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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
    @BindView(R.id.fb_login_button)
    LoginButton fbLoginButton;

    private boolean fragmentResume = false;
    private boolean fragmentVisible = false;
    private boolean fragmentOnCreated = false;

    List<String> profileList = new ArrayList<>();
    String profileFor = "", name = "", gender = "", dob = "";
    String fbName="",fbEmail="",fbGender="";
    View view;

    RegSpinnersStaticData spinnersRegistrationData = RegSpinnersStaticData.getInstance();
    RegSpinnersData registerData = RegSpinnersData.getInstance();
    UserRegData userRegData = UserRegData.getInstance();

    SetRegistrationFragment setRegistrationFragment;
    SpinnerAdapter spinnerAdapter;
    SharedPreference sharedPreference;

    CallbackManager callbackManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reg_screen1, null);
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
        fbLoginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        fbLoginButton.setFragment(this);
        callbackManager = CallbackManager.Factory.create();

        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                    fbName = object.getString("name");
                                    if(object.has("email")) {
                                        fbEmail = object.getString("email");
                                    }
                                    if(object.has("gender")) {
                                        fbGender = object.getString("gender"); // 01/31/1980 format
                                    }
                                    sharedPreference.putSharedPrefData(Constants.FACEBOOKSIGNUP,"1");
                                    sharedPreference.putSharedPrefData(Constants.FBNAME,fbName);
                                    sharedPreference.putSharedPrefData(Constants.FBEMAIL,fbEmail);
                                    sharedPreference.putSharedPrefData(Constants.FBGENDER,fbGender);

                                    editName.setText(fbName);
                                    if(fbGender.equalsIgnoreCase("male"))
                                    {
                                        radiogroupGender.check(R.id.radiobutton_male);
                                    }else if(fbGender.equalsIgnoreCase("female"))
                                    {
                                        radiogroupGender.check(R.id.radiobutton_female);
                                    }



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        sharedPreference = SharedPreference.getSharedInstance();
        sharedPreference.initialize(getActivity());
        profileList = Constants.getArraylistFromArray(RegSpinnersStaticData.profileForArray);
        spinnerAdapter = new SpinnerAdapter(getActivity(), profileList);
        spinnerProfile.setAdapter(spinnerAdapter);

        //check if sign up with facebook

        setListeners();
    }

    public void setListeners() {
        spinnerProfile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                profileFor = String.valueOf(pos - 1);
                sharedPreference.putSharedPrefData(Constants.PROFILEFOR, profileList.get(pos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        radiogroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton = (RadioButton) view.findViewById(i);
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
                LoginManager.getInstance().logInWithReadPermissions(this,Arrays.asList(
                        "public_profile", "email", "user_birthday", "user_friends"));
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
                dpd.show(getActivity().getFragmentManager(), "");
                break;
            case R.id.button_next:
                checkAllFields();
                break;
        }
    }

    public void checkAllFields() {
        name = editName.getText().toString();
        dob = textDob.getText().toString();
        if (profileFor.equalsIgnoreCase("") || profileFor.equalsIgnoreCase("-1")) {
            Toast.makeText(getActivity(), "Select profile created for", Toast.LENGTH_SHORT).show();
        } else {
            if (name.equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "Enter name", Toast.LENGTH_SHORT).show();
            } else {
                if (gender.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Select gender", Toast.LENGTH_SHORT).show();
                } else {
                    if (dob.equalsIgnoreCase("") || dob.equalsIgnoreCase(getResources().getString(R.string.select_dob))) {
                        Toast.makeText(getActivity(), "Select date of birth", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            sharedPreference.putSharedPrefData(Constants.USERNAME,name);
                            userRegData.regDataObject.put(userRegData.KEY_USERNAME, name);
                            userRegData.regDataObject.put(userRegData.KEY_FIRSTNAME, name);
                            userRegData.regDataObject.put(userRegData.KEY_LASTNAME, " ");
                            userRegData.regDataObject.put(userRegData.KEY_MIDDLENAME, " ");
                            userRegData.regDataObject.put(userRegData.KEY_GENDER, gender);
                            userRegData.regDataObject.put(userRegData.KEY_DOB, dob);
                            userRegData.regDataObject.put(userRegData.KEY_PROFILEFOR, profileFor);
                            setRegistrationFragment = (SetRegistrationFragment) getActivity();
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
        int month = monthOfYear + 1;
        textDob.setText(dayOfMonth + "-" + month + "-" + year);
        //  Toast.makeText(getActivity(), ""+dayOfMonth+monthOfYear+year, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }
}
