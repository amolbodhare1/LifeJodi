package com.lifejodi.login.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.home.activity.HomeActivity;
import com.lifejodi.utils.AppController;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    SharedPreference sharedPreference = SharedPreference.getSharedInstance();

    String userEmail="",userMobile="",userPassword="",enteredEmail="",enteredPassword="";
    AppController appController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initialization();
        generateKeyHash();
    }

    public void initialization()
    {
        sharedPreference.initialize(this);
        appController = AppController.getInstance();
        appController.initialize(this);
        if(!appController.hasPermissions(Constants.PERMISSIONS))
        {
            appController.requestPermission(Constants.PERMISSIONS);
        }
    }

    @OnClick({R.id.text_forgot_password, R.id.button_login, R.id.text_login_via_otp, R.id.layout_fb_login, R.id.text_sign_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_forgot_password:
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
                break;
            case R.id.button_login:
                checkLoginCredentials();
                break;
            case R.id.text_login_via_otp:
                Intent intentOTP = new Intent(LoginActivity.this,LoginViaOTPActivity.class);
                startActivity(intentOTP);
                break;
            case R.id.layout_fb_login:
                break;
            case R.id.text_sign_up:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void checkLoginCredentials()
    {
        enteredEmail = editEmail.getText().toString();
        enteredPassword = editPassword.getText().toString();
        userEmail = sharedPreference.getSharedPrefData(Constants.SAVEDEMAIL);
        userMobile = sharedPreference.getSharedPrefData(Constants.SAVEDMOBILE);
        userPassword = sharedPreference.getSharedPrefData(Constants.SAVEDPASSWORD);

        if(enteredEmail.equalsIgnoreCase("") ) {
            Toast.makeText(this, "Enter email id", Toast.LENGTH_SHORT).show();}else {
            if(enteredPassword.equalsIgnoreCase("")) {
                Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();}else {
                if(enteredEmail.equals(userEmail) || enteredEmail.equals(userMobile)) {
                   if(enteredPassword.equals(userPassword)) {
                        sharedPreference.putSharedPrefData(Constants.LOGINSTATUS,"1");
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                   }else {Toast.makeText(this, "Incorrect password.Try again", Toast.LENGTH_SHORT).show();}
                }else {Toast.makeText(this, "Incorrect email.Try again", Toast.LENGTH_SHORT).show();}
            }
        }
    }

    public void generateKeyHash()
    {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }
}
