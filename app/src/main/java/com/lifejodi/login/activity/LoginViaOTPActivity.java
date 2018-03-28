package com.lifejodi.login.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.login.data.OTPLoginData;
import com.lifejodi.login.manager.OTPLoginManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.customfonts.CustomButtonBeatles;
import com.lifejodi.utils.customfonts.CustomEditBeatles;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 1/25/2018.
 */

public class LoginViaOTPActivity extends AppCompatActivity implements VolleyCallbackInterface {

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
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;
    @BindView(R.id.layout_verify_otp)
    LinearLayout layoutVerifyOtp;


    String mobileNum = "";
    OTPLoginData otpLoginData = OTPLoginData.getInstance();
    OTPLoginManager otpLoginManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_login);
        ButterKnife.bind(this);
        initialization();
    }

    public void initialization() {
        toolbarOtplogin.setTitle("Login via OTP");
        toolbarOtplogin.setTitleTextColor(Color.WHITE);
        toolbarOtplogin.setNavigationIcon(R.drawable.ic_back);
        toolbarOtplogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.button_otplogin_submit, R.id.button_otplogin_verify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_otplogin_submit:
                mobileNum = editOtploginMobilenum.getText().toString();
                if (mobileNum.equals("") || mobileNum.length() < 10) {
                    Toast.makeText(this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                } else {
                    otpLoginManager = OTPLoginManager.getInstance();
                    otpLoginManager.initialize(this, this);
                    String androidDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                    progressLayout.setVisibility(View.VISIBLE);
                    otpLoginManager.getOTP(otpLoginManager.getOtpInputParams(mobileNum, androidDeviceId));
                }
                break;
            case R.id.button_otplogin_verify:
                break;
        }
    }

    @Override
    public void successCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_OTP:
                progressLayout.setVisibility(View.GONE);
                String message = otpLoginData.getOtpMessage();
                Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
                layoutVerifyOtp.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_OTP:
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
