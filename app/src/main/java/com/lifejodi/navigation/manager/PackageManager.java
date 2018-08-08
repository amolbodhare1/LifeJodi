package com.lifejodi.navigation.manager;

import android.content.Context;

import com.android.volley.Request;
import com.lifejodi.R;
import com.lifejodi.login.data.LoginData;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.navigation.data.PackageData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PackageManager implements VolleyResponse{

    private VolleyRequest mVolleyRequest;
    private VolleyCallbackInterface mVolleyCallbackInterface;

    private static PackageManager ourInstance = new PackageManager();
    public static PackageManager getInstance(){return ourInstance;};

    SharedPref sharedPreference = SharedPref.getSharedInstance();
    UserRegData userRegData = UserRegData.getInstance();

    PackageData packageData = PackageData.getInstance();
    Context context;

    private PackageManager(){
        mVolleyRequest = new VolleyRequest(this);
    }
    public void initialize(VolleyCallbackInterface VolleyCallbackInterface, Context mContext) {
        this.mVolleyCallbackInterface = VolleyCallbackInterface;
        mVolleyRequest.setContext(mContext);
        sharedPreference.initialize(mContext);
        context = mContext;

    }

    public JSONObject getAllPackagesInput(String deviceId)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(LoginData.API,"getPackages");
            jsonObject.put(LoginData.DEVICE,deviceId);
            jsonObject.put(LoginData.VERSION,"1.0");

            /*JSONObject dataObject = new JSONObject();
            jsonObject.put(LoginData.DATA,dataObject);*/


            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getMyPackagesInput(String deviceId,String userId)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(LoginData.API,"checkUserPackage");
            jsonObject.put(LoginData.DEVICE,deviceId);
            jsonObject.put(LoginData.VERSION,"1.0");

            JSONObject dataObject = new JSONObject();
            dataObject.put(LoginData.USERID,userId);

            jsonObject.put(LoginData.DATA,dataObject);

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;

    }

    public void getAllPackages(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.BASE_URL,Constants.TAG_ALL_PACKAGES,jsonObject);
    }

    public void getMyPackage(JSONObject jsonObject)
    {
        mVolleyRequest.volleyJsonRequest(Request.Method.POST, Constants.BASE_URL,Constants.TAG_MY_PACKAGE,jsonObject);
    }

    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {

    }

    @Override
    public void getResponse(String strResponse, String tag) {


        switch (tag){
            case Constants.TAG_ALL_PACKAGES:
                parseAllPackagesResponse(strResponse,tag);
                break;
            case Constants.TAG_MY_PACKAGE:
                parseMyPackageResponse(strResponse,tag);
                break;
        }

    }

    private void parseMyPackageResponse(String strResponse, String tag) {

        HashMap<String,String> myPackage = new HashMap<>();

        try {
            JSONObject jsonObject = new JSONObject(strResponse);

            if (jsonObject.getString(PackageData.STATUS).equals("0")) {

                JSONObject data = jsonObject.getJSONObject("data");
                myPackage.put(PackageData.PACKAGE_ADD_DATE,data.getString(PackageData.PACKAGE_ADD_DATE));
                myPackage.put(PackageData.PACKAGE_EXPIRY_DATE,data.getString(PackageData.PACKAGE_EXPIRY_DATE));

                sharedPreference.putSharedPrefData(Constants.PACKAGE_ADD_DATE,data.getString(PackageData.PACKAGE_ADD_DATE));
                sharedPreference.putSharedPrefData(Constants.PACKAGE_EXPIRATION_DATE,data.getString(PackageData.PACKAGE_EXPIRY_DATE));
                PackageData.getInstance().setMyPackage(myPackage);
                mVolleyCallbackInterface.successCallBack("success",tag);

            }
        }catch (Exception e){
            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);
        }
    }

    private void parseAllPackagesResponse(String strResponse, String tag) {

        ArrayList<HashMap<String,String>> allPackagesList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(strResponse);

            if (jsonObject.getString(LoginData.STATUS).equals("1")){

                JSONArray dataArray = jsonObject.getJSONArray("data");
                HashMap<String,String> mapPopularPackage = new HashMap<>();
                mapPopularPackage.put(PackageData.PACKAGE_ID,"0");
                mapPopularPackage.put(PackageData.PACKAGE_DESC,context.getResources().getString(R.string.popular_package_details));
                mapPopularPackage.put(PackageData.PACKAGE_AMOUNT,"Free");
                mapPopularPackage.put(PackageData.PACKAGE_NAME,"Popular Package");
                mapPopularPackage.put(PackageData.PACKAGE_VALIDITY,"1");
                allPackagesList.add(mapPopularPackage);
                for(int i=0;i<dataArray.length();i++){

                    HashMap<String,String> map = new HashMap<>();
                    JSONObject dataObject = dataArray.getJSONObject(i);

                    map.put(PackageData.PACKAGE_ID,Constants.getStringValueOfJsonObject(dataObject,PackageData.PACKAGE_ID,PackageData.PACKAGE_ID));
                    map.put(PackageData.PACKAGE_DESC,Constants.getStringValueOfJsonObject(dataObject,PackageData.PACKAGE_DESC,PackageData.PACKAGE_DESC));
                    map.put(PackageData.PACKAGE_AMOUNT,Constants.getStringValueOfJsonObject(dataObject,PackageData.PACKAGE_AMOUNT,PackageData.PACKAGE_AMOUNT));
                    map.put(PackageData.PACKAGE_NAME,Constants.getStringValueOfJsonObject(dataObject,PackageData.PACKAGE_NAME,PackageData.PACKAGE_NAME));
                    map.put(PackageData.PACKAGE_VALIDITY,Constants.getStringValueOfJsonObject(dataObject,PackageData.PACKAGE_VALIDITY,PackageData.PACKAGE_VALIDITY));

                    allPackagesList.add(map);

                }

                packageData.setAllPackagesList(allPackagesList);
                mVolleyCallbackInterface.successCallBack("success",tag);

            }

        }catch (Exception e){

            mVolleyCallbackInterface.errorCallBack(e.getLocalizedMessage(),tag);

        }

    }

    @Override
    public void error(String msg, String tag) {

        switch (tag){
                case Constants.TAG_ALL_PACKAGES:

                    mVolleyCallbackInterface.errorCallBack("No packages found",tag);

                break;

                case Constants.TAG_MY_PACKAGE:

                    mVolleyCallbackInterface.errorCallBack("No packages found",tag);

                break;
        }

    }

}
