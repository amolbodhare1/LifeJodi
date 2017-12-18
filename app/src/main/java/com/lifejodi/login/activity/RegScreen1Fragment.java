package com.lifejodi.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.lifejodi.home.activity.HomeActivity;
import com.lifejodi.R;
import com.lifejodi.login.adapter.SpinnerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Ajay on 11-11-2017.
 */

public class RegScreen1Fragment extends Fragment {


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
    Unbinder unbinder;

    ArrayList<String> profileList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reg_screen1, null);
        unbinder = ButterKnife.bind(this, view);

        profileList.clear();

        profileList.add("Profile created for");
        spinnerProfile.setAdapter(new SpinnerAdapter(profileList));

        return view;

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
                break;
            case R.id.button_next:
                startActivity(new Intent(getActivity(),HomeActivity.class));
                break;
        }
    }
}
