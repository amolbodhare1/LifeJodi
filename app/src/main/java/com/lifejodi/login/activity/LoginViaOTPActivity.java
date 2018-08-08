package com.lifejodi.login.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.home.activity.HomeActivity;
import com.lifejodi.interfaces.SmsListener;
import com.lifejodi.login.data.OTPLoginData;
import com.lifejodi.login.manager.OTPLoginManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.receiver.CustomSMSReceiver;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPref;
import com.lifejodi.utils.customfonts.CustomButtonBeatles;
import com.lifejodi.utils.customfonts.CustomEditBeatles;
import com.lifejodi.utils.customfonts.CustomTextBeatles;

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
    @BindView(R.id.layout_get_otp)
    LinearLayout layoutGetOtp;
    @BindView(R.id.text_resend_otp)
    CustomTextBeatles textResendOtp;
    @BindView(R.id.edit_otplogin_digit5)
    CustomEditBeatles editOtploginDigit5;
    @BindView(R.id.edit_otplogin_digit6)
    CustomEditBeatles editOtploginDigit6;


    String mobileNum = "";
    OTPLoginData otpLoginData = OTPLoginData.getInstance();
    OTPLoginManager otpLoginManager;
    SharedPref sharedPreference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_login);
        ButterKnife.bind(this);
        initialization();
    }

    public void initialization() {
        sharedPreference = SharedPref.getSharedInstance();
        sharedPreference.initialize(this);

        toolbarOtplogin.setTitle("Login via OTP");
        toolbarOtplogin.setTitleTextColor(Color.WHITE);
        toolbarOtplogin.setNavigationIcon(R.drawable.ic_back);
        toolbarOtplogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editOtploginDigit1.setEnabled(false);
        editOtploginDigit2.setEnabled(false);
        editOtploginDigit3.setEnabled(false);
        editOtploginDigit4.setEnabled(false);
        editOtploginDigit5.setEnabled(false);
        editOtploginDigit6.setEnabled(false);

        editOtploginDigit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editOtploginDigit1.getText().toString().length()==1)     //size as per your requirement
                {
                    editOtploginDigit2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editOtploginDigit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editOtploginDigit2.getText().toString().length()==1)     //size as per your requirement
                {
                    editOtploginDigit3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editOtploginDigit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editOtploginDigit3.getText().toString().length()==1)     //size as per your requirement
                {
                    editOtploginDigit4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editOtploginDigit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editOtploginDigit4.getText().toString().length()==1)     //size as per your requirement
                {
                    editOtploginDigit5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editOtploginDigit5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editOtploginDigit5.getText().toString().length()==1)     //size as per your requirement
                {
                    editOtploginDigit6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        CustomSMSReceiver.bindListener(new SmsListener() {
            @Override public void messageReceived(String messageText)
            {
            String otp = new StringBuilder(messageText).reverse().toString();
            otp = otp.substring(0,6); otp = new StringBuilder(otp).reverse().toString();
            editOtploginDigit1.setText(String.valueOf(otp.charAt(0)));
            editOtploginDigit2.setText(String.valueOf(otp.charAt(1)));
            editOtploginDigit3.setText(String.valueOf(otp.charAt(2)));
            editOtploginDigit4.setText(String.valueOf(otp.charAt(3)));
            editOtploginDigit5.setText(String.valueOf(otp.charAt(4)));
            editOtploginDigit6.setText(String.valueOf(otp.charAt(5)));

            } });
    }

    @OnClick({R.id.button_otplogin_submit, R.id.button_otplogin_verify, R.id.text_resend_otp})
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
                verifyOtp();
                break;
            case R.id.text_resend_otp:
                layoutGetOtp.setVisibility(View.VISIBLE);
                layoutVerifyOtp.setVisibility(View.GONE);
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
                layoutGetOtp.setVisibility(View.GONE);
                break;
            case Constants.TAG_LOGIN_WITH_OTP:
                progressLayout.setVisibility(View.GONE);
                String loginMsg = otpLoginData.getOtpLoginMessage();
                String loginStatus = otpLoginData.getOtpLoginSuccess();
                if(loginStatus.equals("1"))
                {
                    sharedPreference.putSharedPrefData(Constants.LOGINSTATUS,"1");
                    Toast.makeText(this, ""+loginMsg, Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(LoginViaOTPActivity.this,HomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                }else if(loginStatus.equals("0"))
                {
                    Toast.makeText(this, ""+loginMsg, Toast.LENGTH_SHORT).show();
                }
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
            case Constants.TAG_LOGIN_WITH_OTP:
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void verifyOtp() {
        if(editOtploginDigit1.getText().toString().equals("")||editOtploginDigit2.getText().toString().equals("")||editOtploginDigit3.getText().toString().equals("")
                || editOtploginDigit4.getText().toString().equals("")|| editOtploginDigit5.getText().toString().equals("")
                ||editOtploginDigit6.getText().toString().equals(""))
        {
            Toast.makeText(this, "Please enter valid otp", Toast.LENGTH_SHORT).show();
        }else {
            String otp = editOtploginDigit1.getText().toString()+editOtploginDigit2.getText().toString()+editOtploginDigit3.getText().toString()+
                    editOtploginDigit4.getText().toString()+editOtploginDigit5.getText().toString()+editOtploginDigit6.getText().toString();

            otpLoginManager = OTPLoginManager.getInstance();
            otpLoginManager.initialize(this,this);
            String androidDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            progressLayout.setVisibility(View.VISIBLE);
            otpLoginManager.loginWithOTP(otpLoginManager.getOtpLoginInputParams(otp,androidDeviceId));
        }
    }
}
