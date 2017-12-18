package com.lifejodi.login.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
 * Created by Ajay on 11-11-2017.
 */

public class RegScreen2Fragment extends Fragment {


    @BindView(R.id.spinner_religion)
    Spinner spinnerReligion;
    @BindView(R.id.spinner_mother_toungue)
    Spinner spinnerMotherToungue;
    @BindView(R.id.spinner_code)
    Spinner spinnerCode;
    @BindView(R.id.edit_mobile_no)
    EditText editMobileNo;
    @BindView(R.id.edit_name)
    CustomEditBeatles editName;
    @BindView(R.id.edit_password)
    CustomEditBeatles editPassword;
    @BindView(R.id.button_register)
    CustomButtonBeatles buttonRegister;
    Unbinder unbinder;

    ArrayList<String> religionList = new ArrayList<>();
    ArrayList<String> motherToungueList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reg_screen2, null);
        unbinder = ButterKnife.bind(this, view);

        religionList.clear();
        motherToungueList.clear();

        religionList.add("Religion");
        motherToungueList.add("Mother Toungue");

        spinnerReligion.setAdapter(new SpinnerAdapter(religionList));
        spinnerMotherToungue.setAdapter(new SpinnerAdapter(motherToungueList));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
