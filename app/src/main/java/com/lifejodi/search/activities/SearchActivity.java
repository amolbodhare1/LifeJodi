package com.lifejodi.search.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.login.adapter.SpinnerAdapter;
import com.lifejodi.login.data.RegSpinnersData;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.login.manager.RegSpinnersManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.search.adapter.CommonDataAdapter;
import com.lifejodi.search.adapter.SelectedAdapter;
import com.lifejodi.search.data.SearchData;
import com.lifejodi.search.manager.SearchManager;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;
import com.lifejodi.utils.customfonts.CustomEditBeatles;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajay on 18-11-2017.
 */

public class SearchActivity extends AppCompatActivity implements VolleyCallbackInterface {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    RegSpinnersManager regSpinnersManager = RegSpinnersManager.getInstance();
    RegSpinnersData regSpinnersData = RegSpinnersData.getInstance();
    JSONObject userDataObject = new JSONObject();

    SearchManager searchManager;
    SearchData searchData = SearchData.getInstance();

    String deviceId;
    @BindView(R.id.image_search_by_id)
    ImageView imageSearchById;
    @BindView(R.id.spinner_min_age)
    Spinner spinnerMinAge;
    @BindView(R.id.spinner_max_age)
    Spinner spinnerMaxAge;
    @BindView(R.id.spinner_min_height)
    Spinner spinnerMinHeight;
    @BindView(R.id.spinner_max_height)
    Spinner spinnerMaxHeight;
    @BindView(R.id.spinner_min_income)
    Spinner spinnerMinIncome;
    @BindView(R.id.spinner_max_income)
    Spinner spinnerMaxIncome;
    @BindView(R.id.layout_marital_status)
    LinearLayout layoutMaritalStatus;
    @BindView(R.id.recycler_marital_status)
    RecyclerView recyclerMaritalStatus;
    @BindView(R.id.layout_religion)
    LinearLayout layoutReligion;
    @BindView(R.id.recycler_religion)
    RecyclerView recyclerReligion;
    @BindView(R.id.layout_mother_tongue)
    LinearLayout layoutMotherTongue;
    @BindView(R.id.recycler_mother_tongue)
    RecyclerView recyclerMotherTongue;
    @BindView(R.id.layout_caste)
    LinearLayout layoutCaste;
    @BindView(R.id.recycler_caste)
    RecyclerView recyclerCaste;
    @BindView(R.id.layout_dosham)
    LinearLayout layoutDosham;
    @BindView(R.id.recycler_dosham)
    RecyclerView recyclerDosham;
    @BindView(R.id.layout_country)
    LinearLayout layoutCountry;
    @BindView(R.id.recycler_country)
    RecyclerView recyclerCountry;
    @BindView(R.id.layout_state)
    LinearLayout layoutState;
    @BindView(R.id.recycler_state)
    RecyclerView recyclerState;
    @BindView(R.id.layout_occupation)
    LinearLayout layoutOccupation;
    @BindView(R.id.recycler_occupation)
    RecyclerView recyclerOccupation;
    @BindView(R.id.layout_education)
    LinearLayout layoutEducation;
    @BindView(R.id.recycler_education)
    RecyclerView recyclerEducation;
    @BindView(R.id.layout_city)
    LinearLayout layoutCity;
    @BindView(R.id.recycler_city)
    RecyclerView recyclerCity;

    Dialog dialogCommon;
    RecyclerView recyclerViewCommon;
    TextView textTitleDialogCommon;
    Button buttonDialogCommon;

    String TAG_CURRENT;

    LinearLayoutManager layoutManagerVetical;

    ArrayList<HashMap<String, String>> maritalStatusList = new ArrayList<>();
    ArrayList<HashMap<String, String>> countryList = new ArrayList<>();
    ArrayList<HashMap<String, String>> cityList = new ArrayList<>();
    ArrayList<HashMap<String, String>> religionList = new ArrayList<>();
    ArrayList<HashMap<String, String>> casteList = new ArrayList<>();
    ArrayList<HashMap<String, String>> educationList = new ArrayList<>();
    ArrayList<HashMap<String, String>> occupationList = new ArrayList<>();
    ArrayList<HashMap<String, String>> stateList = new ArrayList<>();
    ArrayList<HashMap<String, String>> doshamList = new ArrayList<>();
    ArrayList<HashMap<String, String>> mothertongueList = new ArrayList<>();

    String userId = "", searchNow = "1", religionId = "", minAge = "", maxAge = "", minHeight = "", maxHeight = "", maritalStatusId = "",
            motherTongueId = "", casteId = "", country = "", state = "", city = "", educationId = "", occupationId = "", minIncome = "", maxIncome = "",
            dosham = "", showWithPhoto = "0", showWithHoroscope = "0", showWithPremium = "0",
            dontShowContacted = "0", dontShowShortlisted = "0", dontShowIgnored = "0", dontShowVisited = "0";

    @BindView(R.id.edit_search_by_id)
    CustomEditBeatles editSearchById;
    @BindView(R.id.checkbox_show_photo)
    CheckBox checkboxShowPhoto;
    @BindView(R.id.checkbox_show_horoscope)
    CheckBox checkboxShowHoroscope;
    @BindView(R.id.checkbox_show_premium)
    CheckBox checkboxShowPremium;
    @BindView(R.id.checkbox_dont_contacted)
    CheckBox checkboxDontContacted;
    @BindView(R.id.checkbox_dont_shortlisted)
    CheckBox checkboxDontShortlisted;
    @BindView(R.id.checkbox_dont_ignored)
    CheckBox checkboxDontIgnored;
    @BindView(R.id.checkbox_dont_visited)
    CheckBox checkboxDontVisited;
    @BindView(R.id.button_custom_search)
    Button buttonCustomSearch;


    SharedPreference sharedPreference = SharedPreference.getSharedInstance();
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_search);
        ButterKnife.bind(this);

        initialization();
    }

    @SuppressLint("MissingPermission")
    private void initialization() {

        toolbar.setTitle("Search");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        regSpinnersManager.initialize(this, this);
        sharedPreference.initialize(this);
        try {
            userDataObject = new JSONObject(sharedPreference.getSharedPrefData(Constants.USERDATA));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String userData = sharedPreference.getSharedPrefData(Constants.USERDATA);
        userId = Constants.getValue(userData, SearchData.USERID);
        deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        Log.e("DEVICEID", deviceId);

        if (deviceId != null || deviceId != "") {
            regSpinnersManager.getAllSpinnersData(regSpinnersManager.getAllSpinnersDataInputs(deviceId));
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            deviceId = telephonyManager.getDeviceId();
            regSpinnersManager.getAllSpinnersData(regSpinnersManager.getAllSpinnersDataInputs(deviceId));
        }

        dialogCommon = new Dialog(this);
        dialogCommon.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCommon.setContentView(R.layout.dialog_search_common);

        textTitleDialogCommon = (TextView) dialogCommon.findViewById(R.id.text_title_dialog_common);
        buttonDialogCommon = (Button) dialogCommon.findViewById(R.id.button_submit_dialog_common);
        recyclerViewCommon = (RecyclerView) dialogCommon.findViewById(R.id.recycler_dialog_common);

        layoutManagerVetical = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewCommon.setLayoutManager(layoutManagerVetical);

        recyclerMaritalStatus.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerCaste.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerCity.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerCountry.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerDosham.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerEducation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerOccupation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerReligion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerState.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerMotherTongue.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        buttonDialogCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecyclerView(TAG_CURRENT);
            }
        });

        checkboxShowPhoto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showWithPhoto = "1";
                } else {
                    showWithPhoto = "0";
                }
            }
        });
        checkboxShowHoroscope.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showWithHoroscope = "1";
                } else {
                    showWithHoroscope = "0";
                }
            }
        });
        checkboxShowPremium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showWithPremium = "1";
                } else {
                    showWithPremium = "0";
                }
            }
        });
        checkboxDontContacted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dontShowContacted = "1";
                } else {
                    dontShowContacted = "0";
                }
            }
        });
        checkboxDontIgnored.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dontShowIgnored = "1";
                } else {
                    dontShowIgnored = "0";
                }
            }
        });
        checkboxDontVisited.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dontShowVisited = "1";
                } else {
                    dontShowVisited = "0";
                }
            }
        });
        checkboxDontShortlisted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dontShowShortlisted = "1";
                } else {
                    dontShowShortlisted = "0";
                }
            }
        });

    }

    private void setRecyclerView(String tag) {

        switch (tag) {

            case SearchData.TAG_MARITAL_STATUS:

                maritalStatusList.clear();
                maritalStatusList.addAll(SearchData.selectedList);
                recyclerMaritalStatus.setAdapter(new SelectedAdapter(this, maritalStatusList));
                if (maritalStatusList.size() > 0) {
                    recyclerMaritalStatus.setVisibility(View.VISIBLE);
                    maritalStatusId = Constants.getString(maritalStatusList);
                } else {
                    recyclerMaritalStatus.setVisibility(View.GONE);
                    maritalStatusId = "";
                }
                dialogCommon.dismiss();

                break;

            case SearchData.TAG_RELIGION:

                religionList.clear();
                religionList.addAll(SearchData.selectedList);
                recyclerReligion.setAdapter(new SelectedAdapter(this, religionList));
                if (religionList.size() > 0) {
                    recyclerReligion.setVisibility(View.VISIBLE);
                    religionId = Constants.getString(religionList);
                } else {
                    recyclerReligion.setVisibility(View.GONE);
                    religionId = "";
                }
                dialogCommon.dismiss();

                break;

            case SearchData.TAG_CASTE:

                casteList.clear();
                casteList.addAll(SearchData.selectedList);
                recyclerCaste.setAdapter(new SelectedAdapter(this, casteList));
                if (casteList.size() > 0) {
                    recyclerCaste.setVisibility(View.VISIBLE);
                    casteId = Constants.getString(casteList);
                } else {
                    recyclerCaste.setVisibility(View.GONE);
                    casteId = "";
                }
                dialogCommon.dismiss();

                break;

            case SearchData.TAG_COUNTRY:

                countryList.clear();
                countryList.addAll(SearchData.selectedList);
                recyclerCountry.setAdapter(new SelectedAdapter(this, countryList));
                if (countryList.size() > 0) {
                    recyclerCountry.setVisibility(View.VISIBLE);
                    country = Constants.getString(countryList);
                } else {
                    recyclerCountry.setVisibility(View.GONE);
                    country = "";
                }
                dialogCommon.dismiss();

                break;

            case SearchData.TAG_CITY:


                break;

            case SearchData.TAG_EDUCATION:

                educationList.clear();
                educationList.addAll(SearchData.selectedList);
                recyclerEducation.setAdapter(new SelectedAdapter(this, educationList));
                if (educationList.size() > 0) {
                    recyclerEducation.setVisibility(View.VISIBLE);
                    educationId = Constants.getString(educationList);
                } else {
                    recyclerEducation.setVisibility(View.GONE);
                    educationId = "";
                }
                dialogCommon.dismiss();

                break;

            case SearchData.TAG_OCCUPATION:

                occupationList.clear();
                occupationList.addAll(SearchData.selectedList);
                recyclerOccupation.setAdapter(new SelectedAdapter(this, occupationList));
                if (occupationList.size() > 0) {
                    recyclerOccupation.setVisibility(View.VISIBLE);
                    occupationId = Constants.getString(occupationList);
                } else {
                    recyclerOccupation.setVisibility(View.GONE);
                    occupationId = "";
                }
                dialogCommon.dismiss();

                break;

            case SearchData.TAG_STATE:

                break;

            case SearchData.TAG_MOTHER_TONGUE:

                mothertongueList.clear();
                mothertongueList.addAll(SearchData.selectedList);
                recyclerMotherTongue.setAdapter(new SelectedAdapter(this, mothertongueList));
                if (mothertongueList.size() > 0) {
                    recyclerMotherTongue.setVisibility(View.VISIBLE);
                    motherTongueId = Constants.getString(mothertongueList);
                } else {
                    recyclerMotherTongue.setVisibility(View.GONE);
                    motherTongueId = "";
                }
                dialogCommon.dismiss();

                break;

            case SearchData.TAG_DOSHAM:

                doshamList.clear();
                doshamList.addAll(SearchData.selectedList);
                recyclerDosham.setAdapter(new SelectedAdapter(this, doshamList));
                if (doshamList.size() > 0) {
                    recyclerDosham.setVisibility(View.VISIBLE);
                    dosham = Constants.getString(doshamList);
                } else {
                    recyclerDosham.setVisibility(View.GONE);
                    dosham = "";
                }
                dialogCommon.dismiss();

                break;


        }

    }

    @OnClick({R.id.layout_marital_status, R.id.layout_religion, R.id.layout_caste, R.id.layout_country, R.id.layout_state
            , R.id.layout_city, R.id.layout_education, R.id.layout_mother_tongue, R.id.layout_occupation, R.id.layout_dosham, R.id.button_custom_search, R.id.image_search_by_id})
    void Click(View id) {

        SearchData.selectedList.clear();

        switch (id.getId()) {

            case R.id.layout_marital_status:

                TAG_CURRENT = SearchData.TAG_MARITAL_STATUS;
                SearchData.selectedList.addAll(maritalStatusList);
                textTitleDialogCommon.setText("Marital Status");
                recyclerViewCommon.setAdapter(new CommonDataAdapter(this, regSpinnersData.getMaritalStatusList()));
                dialogCommon.show();

                break;
            case R.id.layout_country:

                TAG_CURRENT = SearchData.TAG_COUNTRY;
                SearchData.selectedList.addAll(countryList);
                textTitleDialogCommon.setText("Country");
                recyclerViewCommon.setAdapter(new CommonDataAdapter(this, regSpinnersData.getCountriesList()));
                dialogCommon.show();

                break;

            case R.id.layout_city:

                TAG_CURRENT = SearchData.TAG_CITY;
                SearchData.selectedList.addAll(cityList);
                textTitleDialogCommon.setText("City");
                recyclerViewCommon.setAdapter(new CommonDataAdapter(this, regSpinnersData.getCountriesList()));
                dialogCommon.show();

                break;

            case R.id.layout_caste:

                TAG_CURRENT = SearchData.TAG_CASTE;
                SearchData.selectedList.addAll(casteList);
                textTitleDialogCommon.setText("Caste");
                recyclerViewCommon.setAdapter(new CommonDataAdapter(this, regSpinnersData.getCastsList()));
                dialogCommon.show();

                break;

            case R.id.layout_education:

                TAG_CURRENT = SearchData.TAG_EDUCATION;
                SearchData.selectedList.addAll(educationList);
                textTitleDialogCommon.setText("Education");
                recyclerViewCommon.setAdapter(new CommonDataAdapter(this, regSpinnersData.getEducationList()));
                dialogCommon.show();

                break;

            case R.id.layout_occupation:

                TAG_CURRENT = SearchData.TAG_OCCUPATION;
                SearchData.selectedList.addAll(occupationList);
                textTitleDialogCommon.setText("Occuapation");
                recyclerViewCommon.setAdapter(new CommonDataAdapter(this, regSpinnersData.getOccupationList()));
                dialogCommon.show();

                break;

            case R.id.layout_mother_tongue:

                TAG_CURRENT = SearchData.TAG_MOTHER_TONGUE;
                SearchData.selectedList.addAll(mothertongueList);
                textTitleDialogCommon.setText("Mother Tongue");
                recyclerViewCommon.setAdapter(new CommonDataAdapter(this, regSpinnersData.getMotherTongueList()));
                dialogCommon.show();

                break;

            case R.id.layout_state:

                TAG_CURRENT = SearchData.TAG_STATE;
                SearchData.selectedList.addAll(stateList);
                textTitleDialogCommon.setText("State");
                recyclerViewCommon.setAdapter(new CommonDataAdapter(this, regSpinnersData.getCountriesList()));
                dialogCommon.show();

                break;

            case R.id.layout_religion:

                TAG_CURRENT = SearchData.TAG_RELIGION;
                SearchData.selectedList.addAll(religionList);
                textTitleDialogCommon.setText("Religion");
                recyclerViewCommon.setAdapter(new CommonDataAdapter(this, regSpinnersData.getReligionsList()));
                dialogCommon.show();

                break;

            case R.id.layout_dosham:

                TAG_CURRENT = SearchData.TAG_DOSHAM;
                SearchData.selectedList.addAll(doshamList);
                textTitleDialogCommon.setText("Dosham");
                recyclerViewCommon.setAdapter(new CommonDataAdapter(this, regSpinnersData.getDoshamList()));
                dialogCommon.show();

                break;

            case R.id.button_custom_search:

                CustomSearch();

                break;

            case R.id.image_search_by_id:

                if (!editSearchById.getText().toString().equals("")) {
                    try {
                        progressLayout.setVisibility(View.VISIBLE);
                        String androidDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                        String userId = userDataObject.getString(UserRegData.USERID);
                        searchManager = SearchManager.getInstance();
                        searchManager.initialize(this, SearchActivity.this);
                        searchManager.getSearchById(searchManager.getSearchByIdInput(androidDeviceId, userId, editSearchById.getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(this, "Enter profile id", Toast.LENGTH_SHORT).show();
                }

                break;


        }

    }

    @Override
    public void successCallBack(String msg, String tag) {

        if (tag.equals(Constants.TAG_GET_MASTERS)) {
            setSearchData();
        }else if(tag.equals(Constants.TAG_SEARCH_BY_ID))
        {
            progressLayout.setVisibility(View.GONE);
            String status = searchData.getSearchByIdStatus();
            if(status.equals("1"))
            {
                Intent intent = new Intent(this,SearchByIdResultActivity.class);
                intent.putExtra("profile_id",editSearchById.getText().toString());
                startActivity(intent);

            }else {
                Toast.makeText(this, ""+searchData.getSearchByIdMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void setSearchData() {

        spinnerMinAge.setAdapter(new SpinnerAdapter(this, regSpinnersData.getAgeList()));
        spinnerMaxAge.setAdapter(new SpinnerAdapter(this, regSpinnersData.getAgeList()));
        spinnerMinHeight.setAdapter(new SpinnerAdapter(this, regSpinnersData.getHeightList()));
        spinnerMaxHeight.setAdapter(new SpinnerAdapter(this, regSpinnersData.getHeightList()));
        spinnerMinIncome.setAdapter(new SpinnerAdapter(this, regSpinnersData.getIncomeList()));
        spinnerMaxIncome.setAdapter(new SpinnerAdapter(this, regSpinnersData.getIncomeList()));

        spinnerMinAge.setSelection(5);
        spinnerMaxAge.setSelection(10);
        spinnerMinHeight.setSelection(3);
        spinnerMaxHeight.setSelection(10);
        spinnerMinIncome.setSelection(1);
        spinnerMaxIncome.setSelection(5);

        spinnerMinAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    minAge = regSpinnersData.getAgeList().get(position);
                } else {
                    minAge = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerMaxAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    maxAge = regSpinnersData.getAgeList().get(position);
                } else {
                    maxAge = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinnerMinHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    minHeight = regSpinnersData.getHeightList().get(position);
                } else {
                    minHeight = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinnerMaxHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    maxHeight = regSpinnersData.getHeightList().get(position);
                } else {
                    maxHeight = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinnerMinIncome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    minIncome = regSpinnersData.getIncomeList().get(position);
                } else {
                    minIncome = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinnerMaxIncome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    maxIncome = regSpinnersData.getIncomeList().get(position);
                } else {
                    maxIncome = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    @Override
    public void errorCallBack(String msg, String tag) {

    }

    private void CustomSearch() {

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(SearchData.USERID, userId);
            jsonObject.put(SearchData.SEARCH_NOW, searchNow);
            jsonObject.put(SearchData.MIN_AGE, minAge);
            jsonObject.put(SearchData.MAX_AGE, maxAge);
            jsonObject.put(SearchData.MIN_HEIGHT, minHeight);
            jsonObject.put(SearchData.MAX_HEIGHT, maxHeight);
            jsonObject.put(SearchData.MIN_INCOME, minIncome);
            jsonObject.put(SearchData.MAX_INCOME, maxIncome);
            jsonObject.put(SearchData.RELIGION, religionId);
            jsonObject.put(SearchData.CASTE, casteId);
            jsonObject.put(SearchData.EDUCATION, educationId);
            jsonObject.put(SearchData.OCCUPATION, occupationId);
            jsonObject.put(SearchData.DOSHAM, dosham);
            jsonObject.put(SearchData.MOTHERTONGUE, motherTongueId);
            jsonObject.put(SearchData.MARITALSTATUS, maritalStatusId);
            jsonObject.put(SearchData.COUNTRY, country);
            jsonObject.put(SearchData.STATE, state);
            jsonObject.put(SearchData.CITY, city);
            jsonObject.put(SearchData.SHOW_PROFILE_WITH_PHOTO, showWithPhoto);
            jsonObject.put(SearchData.SHOW_PROFILE_WITH_HOROSCOPE, showWithHoroscope);
            jsonObject.put(SearchData.SHOW_PROFILE_WITH_PREMIUM, showWithPremium);
            jsonObject.put(SearchData.DONT_SHOW_CONTACTED, dontShowContacted);
            jsonObject.put(SearchData.DONT_SHOW_SHORTLISTED, dontShowShortlisted);
            jsonObject.put(SearchData.DONT_SHOW_VISITED, dontShowVisited);
            jsonObject.put(SearchData.DONT_SHOW_IGNORED, dontShowIgnored);

            Intent intent = new Intent(this, SearchResultActivity.class);
            intent.putExtra("custom_search", jsonObject.toString());
            startActivity(intent);

        } catch (Exception e) {


        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SearchData.selectedList.clear();
    }
}
