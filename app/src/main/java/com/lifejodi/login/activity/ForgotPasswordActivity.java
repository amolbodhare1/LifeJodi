package com.lifejodi.login.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.login.data.LoginData;
import com.lifejodi.login.manager.LoginManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajay on 13-11-2017.
 */

public class ForgotPasswordActivity extends AppCompatActivity implements VolleyCallbackInterface {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_email)
    EditText editEmail;
    @BindView(R.id.button_submit)
    Button buttonSubmit;
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;

    LoginManager loginManager;
    LoginData loginData = LoginData.getInstance();
    String email = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        initialize();
    }

    private void initialize() {

        toolbar.setTitle("Forgot Password");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.button_submit)
    public void onViewClicked() {
        sendEmail();
    }

    public void sendEmail()
    {
        email = editEmail.getText().toString();
        if(email.equals("") || !Constants.isEmailValid(email))
        {
            Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_SHORT).show();
        }else {
            loginManager = LoginManager.getInstance();
            loginManager.initialize(this,ForgotPasswordActivity.this);
            String androidDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            progressLayout.setVisibility(View.VISIBLE);
            loginManager.getForgotPassword(loginManager.getForgotPassParams(androidDeviceId,email));
        }
    }

    @Override
    public void successCallBack(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_FORGOTPASSWORD:
                progressLayout.setVisibility(View.GONE);
                String message = loginData.getForgotPassMessage();
                String status = loginData.getForgotPassStatus();
                if(status.equals("1"))
                {
                    Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
                    finish();
                }else if(status.equals("0"))
                {
                    Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
                    editEmail.setText("");
                }

                break;
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_FORGOTPASSWORD:
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
