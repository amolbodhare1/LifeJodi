package com.lifejodi.event.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.event.data.EventRegistrationData;
import com.lifejodi.event.managers.EventRegManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajay on 14-11-2017.
 */

public class EventRegistrationActivity extends AppCompatActivity implements VolleyCallbackInterface {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_firstname)
    EditText editFirstname;
    @BindView(R.id.edit_lastname)
    EditText editLastname;
    @BindView(R.id.edit_mobileno)
    EditText editMobileno;
    @BindView(R.id.checkbox_terms)
    CheckBox checkboxTerms;
    @BindView(R.id.button_confirm)
    Button buttonConfirm;
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;

    String firstName = "", lastName = "", mobNum = "", userId = "", eventId = "", androidDeviceId = "";

    SharedPreference sharedPreference;
    EventRegManager eventRegManager;
    EventRegistrationData eventRegistrationData = EventRegistrationData.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registration);
        ButterKnife.bind(this);

        initialization();
    }

    private void initialization() {

        toolbar.setTitle("Registration");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sharedPreference = SharedPreference.getSharedInstance();
        sharedPreference.initialize(this);

        userId = sharedPreference.getSharedPrefData(Constants.UID);
        androidDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    @OnClick(R.id.button_confirm)
    public void onViewClicked() {

        firstName = editFirstname.getText().toString();
        lastName = editLastname.getText().toString();
        mobNum = editMobileno.getText().toString();

        if (firstName.equals("")) {
            Toast.makeText(this, "Please enter first name", Toast.LENGTH_SHORT).show();
        } else {
            if (lastName.equals("")) {
                Toast.makeText(this, "Please enter last name", Toast.LENGTH_SHORT).show();
            } else {
                if (mobNum.equals("") || mobNum.length() < 10) {
                    Toast.makeText(this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                } else {
                    if(!checkboxTerms.isChecked())
                    {
                        Toast.makeText(this, "Please accept Terms & Conditions", Toast.LENGTH_SHORT).show();
                    }else {
                        progressLayout.setVisibility(View.VISIBLE);
                        eventRegManager = EventRegManager.getInstance();
                        eventRegManager.initialize(this, EventRegistrationActivity.this);
                        eventRegManager.regUserForEvent(eventRegManager.getRegEventParams(androidDeviceId, userId, "1", firstName, lastName, mobNum));
                    }
                }
            }
        }

    }

    @Override
    public void successCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_REGISTER_EVENT:
                progressLayout.setVisibility(View.GONE);
                String message = eventRegistrationData.getEventRegMessage();
                Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
                editFirstname.setText("");
                editLastname.setText("");
                editMobileno.setText("");
                break;
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_REGISTER_EVENT:
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
