package com.lifejodi.login.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
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
import com.lifejodi.login.data.LoginData;
import com.lifejodi.login.manager.LoginManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.AppController;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements VolleyCallbackInterface {

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
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;

    SharedPreference sharedPreference = SharedPreference.getSharedInstance();

    String enteredEmail="",enteredPassword="";
    AppController appController;
    LoginManager loginManager;
    LoginData loginData  =LoginData.getInstance();

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
                Intent intent1 = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }

    public void checkLoginCredentials()
    {
        enteredEmail = editEmail.getText().toString();
        enteredPassword = editPassword.getText().toString();

        if(enteredEmail.equalsIgnoreCase("") || !Constants.isEmailValid(enteredEmail) ) {
            Toast.makeText(this, "Enter valid email id", Toast.LENGTH_SHORT).show();}else {
            if(enteredPassword.equalsIgnoreCase("")) {
                Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();}else {
                loginManager = LoginManager.getInstance();
                loginManager.initialize(this,LoginActivity.this);
                progressLayout.setVisibility(View.VISIBLE);
                String androidDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                JSONObject jsonObject = loginManager.getLoginDataObject(androidDeviceId,loginManager.getLoginInputs(enteredEmail,enteredPassword));
                loginManager.loginUser(jsonObject);

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

    @Override
    public void successCallBack(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_LOGIN:
                progressLayout.setVisibility(View.GONE);
                String message = loginData.getLoginmessage();
                String status = loginData.getLoginStatus();
                if(status.equals("1"))
                {
                    sharedPreference.putSharedPrefData(Constants.LOGINSTATUS,"1");
                    Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                }else if(status.equals("0"))
                {
                    Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
                    editEmail.setText("");
                    editPassword.setText("");
                }

                break;
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_LOGIN:
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
