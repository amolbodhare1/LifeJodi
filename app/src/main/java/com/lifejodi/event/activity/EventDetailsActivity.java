package com.lifejodi.event.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lifejodi.PaymentActivity;
import com.lifejodi.R;
import com.lifejodi.event.data.EventRegistrationData;
import com.lifejodi.event.data.EventsData;
import com.lifejodi.event.managers.EventRegManager;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;
import com.lifejodi.utils.customfonts.CustomButtonBeatles;
import com.lifejodi.utils.customfonts.CustomTextBeatles;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

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
    @BindView(R.id.image_eventdetails_back)
    ImageView imageEventdetailsBack;
    @BindView(R.id.text_eventdetails_name)
    CustomTextBeatles textEventdetailsName;
    @BindView(R.id.text_eventdetails_date)
    CustomTextBeatles textEventdetailsDate;
    @BindView(R.id.text_eventdetails_fees)
    CustomTextBeatles textEventdetailsFees;
    @BindView(R.id.text_eventdetails_address)
    CustomTextBeatles textEventdetailsAddress;
    @BindView(R.id.text_eventdetails_info)
    CustomTextBeatles textEventdetailsInfo;

    String firstName = "", lastName = "", mobNum = "", userId = "", eventId = "", androidDeviceId = "";
    ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
    int position = 0;

    EventRegManager eventRegManager;
    EventRegistrationData eventRegistrationData = EventRegistrationData.getInstance();
    SharedPreference sharedPreference;
    EventsData eventsData = EventsData.getInstance();
    UserRegData userRegData = UserRegData.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);
        initialization();
    }

    public void initialization() {

        sharedPreference = SharedPreference.getSharedInstance();
        sharedPreference.initialize(this);
        if (getIntent().hasExtra("POSITION")) {
            position = getIntent().getExtras().getInt("POSITION");
        }
        dataList = eventsData.getEventsList();
        HashMap<String, String> dataMap = dataList.get(position);
        textEventdetailsName.setText(dataMap.get(EventsData.EVENTNAME));
        textEventdetailsDate.setText(dataMap.get(EventsData.EVENTDATE));
        textEventdetailsFees.setText(dataMap.get(EventsData.EVENTFEES));
        textEventdetailsInfo.setText(Html.fromHtml(dataMap.get(EventsData.EVENTINFORMATION)));
        textEventdetailsAddress.setText(dataMap.get(EventsData.ADDRESS));

    }

    public void registerUser() {
        JSONObject dataObject = null;
        try {
            dataObject = new JSONObject(sharedPreference.getSharedPrefData(Constants.USERDATA));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            firstName = dataObject.getString(UserRegData.FULLNAME);
            mobNum = dataObject.getString(UserRegData.PHONENUMBER);
            userId = dataObject.getString(UserRegData.USERID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
                Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
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

    @OnClick({R.id.image_eventdetails_back, R.id.button_event_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_eventdetails_back:
                finish();
                break;
            case R.id.button_event_register:
                registerUser();
                break;
        }
    }
}
