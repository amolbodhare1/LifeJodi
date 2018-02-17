package com.lifejodi.login.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lifejodi.R;
import com.lifejodi.utils.customfonts.CustomButtonBeatles;
import com.lifejodi.utils.customfonts.CustomEditBeatles;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 1/25/2018.
 */

public class LoginViaOTPActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_otplogin)
    Toolbar toolbarOtplogin;
    @BindView(R.id.edit_otplogin_mobilenum)
    CustomEditBeatles editOtploginMobilenum;
    @BindView(R.id.button_otplogin_submit)
    CustomButtonBeatles buttonOtploginSubmit;
    @BindView(R.id.edit_otplogin_digit1)
    CustomEditBeatles editOtploginDigit1;
    @BindView(R.id.edit_otplogin_digit2)
    CustomEditBeatles editOtploginDigit2;
    @BindView(R.id.edit_otplogin_digit3)
    CustomEditBeatles editOtploginDigit3;
    @BindView(R.id.edit_otplogin_digit4)
    CustomEditBeatles editOtploginDigit4;
    @BindView(R.id.button_otplogin_verify)
    CustomButtonBeatles buttonOtploginVerify;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_login);
        ButterKnife.bind(this);
        initialization();
    }

    public void initialization()
    {
        toolbarOtplogin.setTitle("Login via OTP");
        toolbarOtplogin.setTitleTextColor(Color.WHITE);
        toolbarOtplogin.setNavigationIcon(R.drawable.ic_back);
        toolbarOtplogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @OnClick({R.id.button_otplogin_submit, R.id.button_otplogin_verify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_otplogin_submit:
                break;
            case R.id.button_otplogin_verify:
                break;
        }
    }
}
