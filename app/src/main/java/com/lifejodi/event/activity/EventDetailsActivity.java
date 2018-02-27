package com.lifejodi.event.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lifejodi.PaymentActivity;
import com.lifejodi.R;
import com.lifejodi.event.data.EventRegistrationData;
import com.lifejodi.event.managers.EventRegManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;
import com.lifejodi.utils.customfonts.CustomButtonBeatles;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2/21/2018.
 */

public class EventDetailsActivity extends AppCompatActivity implements VolleyCallbackInterface {

    @BindView(R.id.button_event_register)
    CustomButtonBeatles buttonEventRegister;
    @BindView(R.id.checkbox_terms)
    CheckBox checkboxTerms;
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;

    /*@BindView(R.id.toolbar_event_details)
    Toolbar toolbarEventDetails;
*/

    String firstName = "", lastName = "", mobNum = "", userId = "", eventId = "", androidDeviceId = "";

    EventRegManager eventRegManager;
    EventRegistrationData eventRegistrationData = EventRegistrationData.getInstance();
    SharedPreference sharedPreference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);
        initialization();
    }

    public void initialization() {
       /* toolbarEventDetails.setTitle("Match Night");
        setSupportActionBar(toolbarEventDetails);
        toolbarEventDetails.setNavigationIcon(R.drawable.ic_back);
        toolbarEventDetails.setTitleTextColor(Color.WHITE);
        toolbarEventDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
*/

        sharedPreference = SharedPreference.getSharedInstance();
        sharedPreference.initialize(this);
    }

    @OnClick(R.id.button_event_register)
    public void onViewClicked() {

      registerUser();
    }

    public void registerUser() {
        firstName = sharedPreference.getSharedPrefData(Constants.USERNAME);
        mobNum = sharedPreference.getSharedPrefData(Constants.SAVEDMOBILE);
        userId = sharedPreference.getSharedPrefData(Constants.UID);
        androidDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        if (!checkboxTerms.isChecked()) {
            Toast.makeText(this, "Please accept Terms & Conditions", Toast.LENGTH_SHORT).show();
        } else {
            progressLayout.setVisibility(View.VISIBLE);
            eventRegManager = EventRegManager.getInstance();
            eventRegManager.initialize(this, EventDetailsActivity.this);
            eventRegManager.regUserForEvent(eventRegManager.getRegEventParams(androidDeviceId, userId, "1", firstName, lastName, mobNum));
        }

    }

    @Override
    public void successCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_REGISTER_EVENT:
                progressLayout.setVisibility(View.GONE);
                String message = eventRegistrationData.getEventRegMessage();
                Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
                Intent eventRegIntent = new Intent(EventDetailsActivity.this, PaymentActivity.class);
                startActivity(eventRegIntent);
                finish();
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
