package com.lifejodi.login.manager;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.lifejodi.login.data.RegSpinnersData;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.network.VolleyRequest;
import com.lifejodi.network.VolleyResponse;
import com.lifejodi.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 12/18/2017.
 */

public class RegSpinnersManager implements VolleyResponse {

    private VolleyRequest mVolleyRequest;
    private VolleyCallbackInterface mVolleyCallbackInterface;

    RegSpinnersData registerData = RegSpinnersData.getInstance();

    private static RegSpinnersManager registrationManager = new RegSpinnersManager();

    public static RegSpinnersManager getInstance()
    {
        return registrationManager;
    }

    public RegSpinnersManager()
    {
        mVolleyRequest = new VolleyRequest(this);
    }
    public void initialize(VolleyCallbackInterface VolleyCallbackInterface, Context mContext) {
        this.mVolleyCallbackInterface = VolleyCallbackInterface;
        mVolleyRequest.setContext(mContext);
    }

    public JSONObject getAllSpinnersDataInputs(String deviceId)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(RegSpinnersData.API,"masters");
            jsonObject.put(RegSpinnersData.VERSION,"1.0");
            jsonObject.put(RegSpinnersData.DEVICEID,deviceId);
            jsonObject.put(RegSpinnersData.DATA,"");

            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public void getAllSpinnersData(JSONObject object)
    {
       mVolleyRequest.volleyJsonRequest(Request.Method.POST,Constants.URL_GET_MASTERSDATA,Constants.TAG_GET_MASTERS,object);
    }

    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {

    }

    @Override
    public void getResponse(String strResponse, String tag) {

        if(tag.equals(Constants.TAG_GET_MASTERS))
        {
            Log.e("ALLMASTERSDATA",strResponse);
            parseAllSpinnersData(strResponse,tag);
        }


    }

    @Override
    public void error(String msg, String tag) {
        mVolleyCallbackInterface.errorCallBack(msg,tag);
    }

    public void parseAllSpinnersData(String strResponse, String tag)
    {

        ArrayList<HashMap<String,String>> profileList = new ArrayList<>();
        ArrayList<HashMap<String,String>> religionList = new ArrayList<>();
        ArrayList<HashMap<String,String>> motherTongueList = new ArrayList<>();
        ArrayList<HashMap<String,String>> maritalStatusList = new ArrayList<>();
        ArrayList<HashMap<String,String>> casteList = new ArrayList<>();
        ArrayList<HashMap<String,String>> countriesList = new ArrayList<>();
        ArrayList<HashMap<String,String>> physicalStatusList = new ArrayList<>();
        ArrayList<HashMap<String,String>> educationList = new ArrayList<>();
        ArrayList<HashMap<String,String>> occupationList = new ArrayList<>();
        ArrayList<HashMap<String,String>> currencyList = new ArrayList<>();
        ArrayList<HashMap<String,String>> familyStatusList = new ArrayList<>();
        ArrayList<HashMap<String,String>> familyTypeList = new ArrayList<>();
        ArrayList<HashMap<String,String>> familyValuesList = new ArrayList<>();
        ArrayList<String> heightList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(strResponse);
            if(jsonObject.has(RegSpinnersData.PROFILEFOR))
            {

                JSONArray profileArray = jsonObject.getJSONArray(RegSpinnersData.PROFILEFOR);
                for(int i=0;i<profileArray.length();i++)
                {

                    HashMap<String,String> profMap = new HashMap<>();
                    JSONObject profObject = profileArray.getJSONObject(i);
                    profMap.put(RegSpinnersData.ID,Constants.getStringValueOfJsonObject(profObject,RegSpinnersData.ID,RegSpinnersData.ID));
                    profMap.put(RegSpinnersData.NAME,Constants.getStringValueOfJsonObject(profObject,RegSpinnersData.NAME,RegSpinnersData.NAME));
                    profMap.put(RegSpinnersData.ACTIVE,Constants.getStringValueOfJsonObject(profObject,RegSpinnersData.ACTIVE,RegSpinnersData.ACTIVE));
                    profileList.add(profMap);
                }

                RegSpinnersData.getInstance().setProfileForList(profileList);

            }
            if(jsonObject.has(RegSpinnersData.RELIGION))
            {

                JSONArray religionArray = jsonObject.getJSONArray(RegSpinnersData.RELIGION);
                for(int i=0;i<religionArray.length();i++)
                {
                    HashMap<String,String> religionMap = new HashMap<>();
                    JSONObject religionObj = religionArray.getJSONObject(i);
                    religionMap.put(RegSpinnersData.ID,Constants.getStringValueOfJsonObject(religionObj,RegSpinnersData.ID,RegSpinnersData.ID));
                    religionMap.put(RegSpinnersData.NAME,Constants.getStringValueOfJsonObject(religionObj,RegSpinnersData.NAME,RegSpinnersData.NAME));
                    religionMap.put(RegSpinnersData.ACTIVE,Constants.getStringValueOfJsonObject(religionObj,RegSpinnersData.ACTIVE,RegSpinnersData.ACTIVE));
                    religionList.add(religionMap);
                }

                RegSpinnersData.getInstance().setReligionsList(religionList);

            }
            if(jsonObject.has(RegSpinnersData.MOTHERTOUNGUE))
            {

                JSONArray motherTongueArray = jsonObject.getJSONArray(RegSpinnersData.MOTHERTOUNGUE);
                for(int i=0;i<motherTongueArray.length();i++)
                {
                    HashMap<String,String> motherTongueMap = new HashMap<>();
                    JSONObject motherTongueObj = motherTongueArray.getJSONObject(i);
                    motherTongueMap.put(RegSpinnersData.ID,Constants.getStringValueOfJsonObject(motherTongueObj,RegSpinnersData.ID,RegSpinnersData.ID));
                    motherTongueMap.put(RegSpinnersData.NAME,Constants.getStringValueOfJsonObject(motherTongueObj,RegSpinnersData.NAME,RegSpinnersData.NAME));
                    motherTongueMap.put(RegSpinnersData.ACTIVE,Constants.getStringValueOfJsonObject(motherTongueObj,RegSpinnersData.ACTIVE,RegSpinnersData.ACTIVE));
                    motherTongueList.add(motherTongueMap);
                }

                RegSpinnersData.getInstance().setMotherTongueList(motherTongueList);

            }
            if(jsonObject.has(RegSpinnersData.MARITALSTATUS))
            {

                JSONArray maritalStatusArray = jsonObject.getJSONArray(RegSpinnersData.MARITALSTATUS);
                for(int i=0;i<maritalStatusArray.length();i++)
                {
                    HashMap<String,String> maritalStatusMap = new HashMap<>();
                    JSONObject maritalStatusObj = maritalStatusArray.getJSONObject(i);
                    maritalStatusMap.put(RegSpinnersData.ID,Constants.getStringValueOfJsonObject(maritalStatusObj,RegSpinnersData.ID,RegSpinnersData.ID));
                    maritalStatusMap.put(RegSpinnersData.VALUE,Constants.getStringValueOfJsonObject(maritalStatusObj,RegSpinnersData.VALUE,RegSpinnersData.VALUE));
                    maritalStatusList.add(maritalStatusMap);
                }

                RegSpinnersData.getInstance().setMaritalStatusList(maritalStatusList);

            }
            if(jsonObject.has(RegSpinnersData.CASTE))
            {

                JSONArray casteArray = jsonObject.getJSONArray(RegSpinnersData.CASTE);
                for(int i=0;i<casteArray.length();i++)
                {
                    HashMap<String,String> casteMap = new HashMap<>();
                    JSONObject casteObj = casteArray.getJSONObject(i);
                    casteMap.put(RegSpinnersData.ID,Constants.getStringValueOfJsonObject(casteObj,RegSpinnersData.ID,RegSpinnersData.ID));
                    casteMap.put(RegSpinnersData.NAME,Constants.getStringValueOfJsonObject(casteObj,RegSpinnersData.NAME,RegSpinnersData.NAME));
                    casteMap.put(RegSpinnersData.ACTIVE,Constants.getStringValueOfJsonObject(casteObj,RegSpinnersData.ACTIVE,RegSpinnersData.ACTIVE));
                    casteMap.put(RegSpinnersData.RELIGIONID,Constants.getStringValueOfJsonObject(casteObj,RegSpinnersData.RELIGIONID,RegSpinnersData.RELIGIONID));
                    casteList.add(casteMap);
                }

                RegSpinnersData.getInstance().setCastsList(casteList);

            }
            if(jsonObject.has(RegSpinnersData.COUNTRY))
            {

                JSONArray countryArray = jsonObject.getJSONArray(RegSpinnersData.COUNTRY);
                for(int i=0;i<countryArray.length();i++)
                {
                    HashMap<String,String> countryMap = new HashMap<>();
                    JSONObject countryObj = countryArray.getJSONObject(i);
                    countryMap.put(RegSpinnersData.ID,Constants.getStringValueOfJsonObject(countryObj,RegSpinnersData.ID,RegSpinnersData.ID));
                    countryMap.put(RegSpinnersData.NAME,Constants.getStringValueOfJsonObject(countryObj,RegSpinnersData.NAME,RegSpinnersData.NAME));
                    countryMap.put(RegSpinnersData.ACTIVE,Constants.getStringValueOfJsonObject(countryObj,RegSpinnersData.ACTIVE,RegSpinnersData.ACTIVE));
                    countryMap.put(RegSpinnersData.COUNTRYCODE,Constants.getStringValueOfJsonObject(countryObj,RegSpinnersData.COUNTRYCODE,RegSpinnersData.COUNTRYCODE));
                    countriesList.add(countryMap);
                }

                RegSpinnersData.getInstance().setCountriesList(countriesList);

            }
            if(jsonObject.has(RegSpinnersData.PHYSICALSTATUS))
            {

                JSONArray physicalStatusArray = jsonObject.getJSONArray(RegSpinnersData.PHYSICALSTATUS);
                for(int i=0;i<physicalStatusArray.length();i++)
                {
                    HashMap<String,String> physicalStatusMap = new HashMap<>();
                    JSONObject physicalStatusObj = physicalStatusArray.getJSONObject(i);
                    physicalStatusMap.put(RegSpinnersData.ID,Constants.getStringValueOfJsonObject(physicalStatusObj,RegSpinnersData.ID,RegSpinnersData.ID));
                    physicalStatusMap.put(RegSpinnersData.VALUE,Constants.getStringValueOfJsonObject(physicalStatusObj,RegSpinnersData.VALUE,RegSpinnersData.VALUE));

                    physicalStatusList.add(physicalStatusMap);
                }

                RegSpinnersData.getInstance().setPhysicalStatusList(physicalStatusList);

            }
            if(jsonObject.has(RegSpinnersData.EDUCATION))
            {

                JSONArray educationArray = jsonObject.getJSONArray(RegSpinnersData.EDUCATION);
                for(int i=0;i<educationArray.length();i++)
                {
                    HashMap<String,String> educationMap = new HashMap<>();
                    JSONObject educationObj = educationArray.getJSONObject(i);
                    educationMap.put(RegSpinnersData.ID,Constants.getStringValueOfJsonObject(educationObj,RegSpinnersData.ID,RegSpinnersData.ID));
                    educationMap.put(RegSpinnersData.NAME,Constants.getStringValueOfJsonObject(educationObj,RegSpinnersData.NAME,RegSpinnersData.NAME));
                    educationMap.put(RegSpinnersData.ACTIVE,Constants.getStringValueOfJsonObject(educationObj,RegSpinnersData.ACTIVE,RegSpinnersData.ACTIVE));

                    educationList.add(educationMap);
                }

                RegSpinnersData.getInstance().setEducationList(educationList);

            }
            if(jsonObject.has(RegSpinnersData.OCCUPATION))
            {

                JSONArray occupationArray = jsonObject.getJSONArray(RegSpinnersData.OCCUPATION);
                for(int i=0;i<occupationArray.length();i++)
                {
                    HashMap<String,String> occupationMap = new HashMap<>();
                    JSONObject occupationObj = occupationArray.getJSONObject(i);
                    occupationMap.put(RegSpinnersData.ID,Constants.getStringValueOfJsonObject(occupationObj,RegSpinnersData.ID,RegSpinnersData.ID));
                    occupationMap.put(RegSpinnersData.NAME,Constants.getStringValueOfJsonObject(occupationObj,RegSpinnersData.NAME,RegSpinnersData.NAME));
                    occupationMap.put(RegSpinnersData.ACTIVE,Constants.getStringValueOfJsonObject(occupationObj,RegSpinnersData.ACTIVE,RegSpinnersData.ACTIVE));

                    occupationList.add(occupationMap);
                }

                RegSpinnersData.getInstance().setOccupationList(occupationList);

            }
            if(jsonObject.has(RegSpinnersData.CURRENCY))
            {

                JSONArray currencyArray = jsonObject.getJSONArray(RegSpinnersData.CURRENCY);
                for(int i=0;i<currencyArray.length();i++)
                {
                    HashMap<String,String> currencyMap = new HashMap<>();
                    JSONObject currencyObj = currencyArray.getJSONObject(i);
                    currencyMap.put(RegSpinnersData.ID,Constants.getStringValueOfJsonObject(currencyObj,RegSpinnersData.ID,RegSpinnersData.ID));
                    currencyMap.put(RegSpinnersData.VALUE,Constants.getStringValueOfJsonObject(currencyObj,RegSpinnersData.VALUE,RegSpinnersData.VALUE));
                    currencyList.add(currencyMap);
                }

                RegSpinnersData.getInstance().setCurrencyList(currencyList);

            }
            if(jsonObject.has(RegSpinnersData.FAMILYSTATUS))
            {

                JSONArray famStatusArray = jsonObject.getJSONArray(RegSpinnersData.FAMILYSTATUS);
                for(int i=0;i<famStatusArray.length();i++)
                {
                    HashMap<String,String> famStatusMap = new HashMap<>();
                    JSONObject famStatusObj= famStatusArray.getJSONObject(i);
                    famStatusMap.put(RegSpinnersData.ID,Constants.getStringValueOfJsonObject(famStatusObj,RegSpinnersData.ID,RegSpinnersData.ID));
                    famStatusMap.put(RegSpinnersData.VALUE,Constants.getStringValueOfJsonObject(famStatusObj,RegSpinnersData.VALUE,RegSpinnersData.VALUE));
                    familyStatusList.add(famStatusMap);
                }

                RegSpinnersData.getInstance().setFamilyStatus(familyStatusList);

            }
            if(jsonObject.has(RegSpinnersData.FAMILYTYPE))
            {

                JSONArray famTypeArray = jsonObject.getJSONArray(RegSpinnersData.FAMILYTYPE);
                for(int i=0;i<famTypeArray.length();i++)
                {
                    HashMap<String,String> famTypeMap = new HashMap<>();
                    JSONObject famTypeObj= famTypeArray.getJSONObject(i);
                    famTypeMap.put(RegSpinnersData.ID,Constants.getStringValueOfJsonObject(famTypeObj,RegSpinnersData.ID,RegSpinnersData.ID));
                    famTypeMap.put(RegSpinnersData.VALUE,Constants.getStringValueOfJsonObject(famTypeObj,RegSpinnersData.VALUE,RegSpinnersData.VALUE));
                    familyTypeList.add(famTypeMap);
                }

                RegSpinnersData.getInstance().setFamilyType(familyTypeList);

            }
            if(jsonObject.has(RegSpinnersData.FAMILYVALUES))
            {

                JSONArray famValuesArray = jsonObject.getJSONArray(RegSpinnersData.FAMILYVALUES);
                for(int i=0;i<famValuesArray.length();i++)
                {
                    HashMap<String,String> famValuesMap = new HashMap<>();
                    JSONObject famValuesObj= famValuesArray.getJSONObject(i);
                    famValuesMap.put(RegSpinnersData.ID,Constants.getStringValueOfJsonObject(famValuesObj,RegSpinnersData.ID,RegSpinnersData.ID));
                    famValuesMap.put(RegSpinnersData.VALUE,Constants.getStringValueOfJsonObject(famValuesObj,RegSpinnersData.VALUE,RegSpinnersData.VALUE));
                    familyValuesList.add(famValuesMap);
                }

                RegSpinnersData.getInstance().setFamilyValues(familyValuesList);

            }

            mVolleyCallbackInterface.successCallBack("success",tag);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
