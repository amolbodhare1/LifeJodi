package com.lifejodi.navigation.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.lifejodi.R;
import com.lifejodi.event.managers.PaymentManager;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.navigation.data.PackageData;
import com.lifejodi.navigation.manager.PackageManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PackageDetailsActivity extends AppCompatActivity implements VolleyCallbackInterface {

    @BindView(R.id.toolbar_packages_details)
    Toolbar toolbarPackagesDetails;
    @BindView(R.id.text_package_details_name)
    TextView textPackageDetailsName;

    @BindView(R.id.text_buy_package_now)
    TextView textBuyPackageNow;

    PackageData packageData = PackageData.getInstance();
    PaymentManager paymentManager = PaymentManager.getInstance();
    PackageManager packageManager = PackageManager.getInstance();
    SharedPref sharedPreference;

    ArrayList<HashMap<String, String>> packagesList = new ArrayList<>();
    int position;
    String userId = "", androidDeviceId = "", token = "", amount = "", packageId = "";
    final int REQUEST_CODE = 100;
    @BindView(R.id.text_package_details_amount)
    TextView textPackageDetailsAmount;
    @BindView(R.id.text_package_details_validity)
    TextView textPackageDetailsValidity;
    @BindView(R.id.text_package_details_desc)
    TextView textPackageDetailsDesc;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);
        ButterKnife.bind(this);

        initialization();
    }

    public void initialization() {
        toolbarPackagesDetails.setTitle("Package Details");
        toolbarPackagesDetails.setTitleTextColor(getResources().getColor(R.color.white));

        sharedPreference = SharedPref.getSharedInstance();
        sharedPreference.initialize(this);
        paymentManager.initialize(this, this);
        packageManager.initialize(this,this);

        position = getIntent().getExtras().getInt("POSITION");
        packagesList = packageData.getAllPackagesList();
        HashMap<String, String> dataMap = packagesList.get(position);

        textPackageDetailsName.setText(dataMap.get(PackageData.PACKAGE_NAME));
        if(position==0)
        {
            textPackageDetailsDesc.setText(getResources().getString(R.string.popular_package_details));
        }else {
            textPackageDetailsDesc.setText(Html.fromHtml(dataMap.get(PackageData.PACKAGE_DESC)));
        }
        textPackageDetailsAmount.setText(dataMap.get(PackageData.PACKAGE_AMOUNT)+"/");
        textPackageDetailsValidity.setText(dataMap.get(PackageData.PACKAGE_VALIDITY)+" Months");

        amount = dataMap.get(PackageData.PACKAGE_AMOUNT);
        packageId = dataMap.get(PackageData.PACKAGE_ID);

    }

    public void getDetails() {
        JSONObject dataObject = null;

        try {
            dataObject = new JSONObject(sharedPreference.getSharedPrefData(Constants.USERDATA));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            userId = dataObject.getString(UserRegData.USERID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        androidDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        paymentManager.getPaymentToken(paymentManager.paymentTokenInput(androidDeviceId, userId));

    }

    @Override
    public void successCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_REQUEST_PAYMENT_TOKEN:
                token = msg;
                DropInRequest dropInRequest = new DropInRequest().clientToken(token);
                startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
                break;
            case Constants.TAG_MAKE_PACKAGE_PAYMENT:
                packageManager.getMyPackage(packageManager.getMyPackagesInput(androidDeviceId,userId));
                break;
            case Constants.TAG_MY_PACKAGE:

                break;
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_REQUEST_PAYMENT_TOKEN:
                break;
            case Constants.TAG_MAKE_PACKAGE_PAYMENT:
                break;
        }
    }

    @OnClick(R.id.text_buy_package_now)
    public void onViewClicked() {
        getDetails();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String stringNonce = nonce.getNonce();
                Log.e("mylog", "Result: " + stringNonce);
                // Send payment price with the nonce
                // use the result to update your UI and send the payment method nonce to your server
                paymentManager.makePackagePayment(paymentManager.makePaymentPackageInput(androidDeviceId, userId, amount + "", stringNonce, packageId));
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                Log.d("mylog", "user canceled");
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("mylog", "Error : " + error.toString());
            }
        }
    }
}
