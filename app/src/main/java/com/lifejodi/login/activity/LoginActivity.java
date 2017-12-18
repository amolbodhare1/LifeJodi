package com.lifejodi.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lifejodi.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edit_email)
    EditText editEmail;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.text_forgot_password)
    TextView textForgotPassword;
    @BindView(R.id.button_login)
    Button buttonLogin;
    @BindView(R.id.text_login_via_otp)
    TextView textLoginViaOtp;
    @BindView(R.id.layout_fb_login)
    RelativeLayout layoutFbLogin;
    @BindView(R.id.text_sign_up)
    TextView textSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.text_forgot_password, R.id.button_login, R.id.text_login_via_otp, R.id.layout_fb_login, R.id.text_sign_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_forgot_password:
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
                break;
            case R.id.button_login:
                break;
            case R.id.text_login_via_otp:
                break;
            case R.id.layout_fb_login:
                break;
            case R.id.text_sign_up:
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                break;
        }
    }
}
