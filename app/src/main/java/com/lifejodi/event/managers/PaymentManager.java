package com.lifejodi.event.managers;

import android.content.Context;

import com.android.volley.Request;
import com.lifejodi.event.data.EventRegistrationData;
import com.lifejodi.event.data.EventsData;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;
import com.lifejodi.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentManager implements VolleyResponse {

    private VolleyRequest mVolleyRequest;
    private VolleyCallbackInterface mVolleyCallbackInterface;

    private static PaymentManager paymentManager = new PaymentManager();

    public static PaymentManager getInstance()
    {
        return paymentManager;
    }

    public PaymentManager()
    {
        mVolleyRequest = new VolleyRequest(this);
    }

    public void initialize(VolleyCallbackInterface VolleyCallbackInterface, Context mContext) {
        this.mVolleyCallbackInterface = VolleyCallbackInterface;
        mVolleyRequest.setContext(mContext);

    }

    public JSONObject paymentTokenInput(String devId,String userId)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(EventRegistrationData.API,"getClientToken");
            jsonObject.put(EventRegistrationData.DEVICE,devId);
            jsonObject.put(EventRegistrationData.VERSION,"1.0");

            JSONObject data = new JSONObject();
            data.put(UserRegData.USERID,userId);

            jsonObject.put("data",data);


            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject makePaymentInput(String devId,String userId,String amount,String nonce)
    {
        JSONObject jsonObject = new JSONObject();
        try{

            jsonObject.put(EventRegistrationData.API,"makePayment");
            jsonObject.put(EventRegistrationData.DEVICE,devId);
            jsonObject.put(EventRegistrationData.VERSION,"1.0");

            JSONObject data = new JSONObject();
            data.put(UserRegData.USERID,userId);
            data.put("amount",amount);
            data.put("payment_method_nonce",nonce);

            jsonObject.put("data",data);


        }catch (Exception e){

        }

        return jsonObject;
    }

    public JSONObject makePaymentPackageInput(String devId,String userId,String amount,String nonce,String package_id)
    {
        JSONObject jsonObject = new JSONObject();
        try{

            jsonObject.put(EventRegistrationData.API,"makePaymentPackage");
            jsonObject.put(EventRegistrationData.DEVICE,devId);
            jsonObject.put(EventRegistrationData.VERSION,"1.0");

            JSONObject data = new JSONObject();
            data.put(UserRegData.USERID,userId);
            data.put("amount",amount);
            data.put("payment_method_nonce",nonce);
            data.put("package_id",package_id);

            jsonObject.put("data",data);


        }catch (Exception e){

        }

        return jsonObject;
    }


    public void getPaymentToken(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.URL_PAYMENT,Constants.TAG_REQUEST_PAYMENT_TOKEN,jsonObject);
    }

    public void makePayment(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.URL_PAYMENT,Constants.TAG_MAKE_PAYMENT,jsonObject);
    }

    public void makePackagePayment(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.URL_PAYMENT,Constants.TAG_MAKE_PACKAGE_PAYMENT,jsonObject);
    }

    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {

    }

    @Override
    public void getResponse(String strResponse, String tag) {
        switch (tag)
        {
            case Constants.TAG_REQUEST_PAYMENT_TOKEN:
                parseTokenResponse(strResponse,tag);
                break;
            case Constants.TAG_MAKE_PAYMENT:
                parsePaymentResponse(strResponse,tag);
                break;
            case Constants.TAG_MAKE_PACKAGE_PAYMENT:

                break;
        }
    }

    private void parseTokenResponse(String strResponse, String tag) {

        try {
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.has(EventsData.DATA)) {
                JSONObject dataObject = jsonObject.getJSONObject(EventsData.DATA);
                String token = dataObject.getString("clientToken");
                mVolleyCallbackInterface.successCallBack(token,tag);
            }
        }catch (Exception e){
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }

    }

    private void parsePaymentResponse(String strResponse, String tag) {

        try {
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.getString(EventsData.STATUS).equals("1")) {
                mVolleyCallbackInterface.successCallBack("success",tag);
            }
        }catch (Exception e){
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }


    }

    @Override
    public void error(String msg, String tag) {

    }
}
