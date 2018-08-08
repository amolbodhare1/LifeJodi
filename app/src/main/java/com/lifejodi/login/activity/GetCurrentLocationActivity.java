package com.lifejodi.login.activity;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lifejodi.R;
import com.lifejodi.login.adapter.AutoCompleteAdapter;
import com.lifejodi.login.data.AutoComplete;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.login.fragments.RegScreen3Fragment;
import com.lifejodi.utils.CallbackLocation;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.LocationManager;
import com.lifejodi.utils.SharedPref;
import com.lifejodi.utils.customfonts.CustomButtonBeatles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 1/27/2018.
 */

public class GetCurrentLocationActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, CallbackLocation {

    @BindView(R.id.toolbar_get_location)
    Toolbar toolbarGetLocation;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.add_lead_search)
    ImageView addLeadSearch;
    @BindView(R.id.image_close)
    ImageView imageClose;
    @BindView(R.id.searchCard)
    CardView searchCard;
    @BindView(R.id.button_set_current_location)
    CustomButtonBeatles buttonSetCurrentLocation;
    @BindView(R.id.progress_location)
    ProgressBar progressLocation;

    GoogleMap map;
    GoogleApiClient mGoogleApiClient;
    SharedPref sharedPreference;

    UserRegData userRegData = UserRegData.getInstance();

    // private AddressResultReceiver mResultReceiver;

    LocationManager locationManager;
    AutoComplete autoComplete;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);
        locationManager = new LocationManager(this, this);
        ButterKnife.bind(this);
        initialization(savedInstanceState);
    }

    public void initialization(Bundle bundle) {

        toolbarGetLocation.setTitle("Get Location");
        toolbarGetLocation.setTitleTextColor(Color.WHITE);
        toolbarGetLocation.setNavigationIcon(R.drawable.ic_back);
        toolbarGetLocation.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sharedPreference = SharedPref.getSharedInstance();
        sharedPreference.initialize(this);

        mapView.onCreate(bundle);
        mapView.onResume();
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        autoComplete = AutoComplete.getInstance();
        autoCompleteTextView.setAdapter(new AutoCompleteAdapter(this, R.layout.autocomplete_dropdown));
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                final String selection = (String) adapterView.getItemAtPosition(position);

                imageClose.setVisibility(View.GONE);
                progressLocation.setVisibility(View.VISIBLE);

                Places.GeoDataApi.getPlaceById(mGoogleApiClient, autoComplete.getPlacesList().get(position).get(autoComplete.KEY_PLACE_ID))
                        .setResultCallback(new ResultCallback<PlaceBuffer>() {
                            @Override
                            public void onResult(PlaceBuffer places) {
                                if (places.getStatus().isSuccess() && places.getCount() > 0) {

                                    autoCompleteTextView.setText(selection);
                                    autoCompleteTextView.setFocusable(false);
                                    final Place myPlace = places.get(0);

                                    imageClose.setVisibility(View.VISIBLE);
                                    progressLocation.setVisibility(View.GONE);
                                    setLocation(myPlace.getLatLng().latitude, myPlace.getLatLng().longitude);

                                } else {
                                }
                                places.release();
                            }
                        });
            }
        });

        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoCompleteTextView.setFocusable(true);
                autoCompleteTextView.setFocusableInTouchMode(true);
            }
        });
    }

    @OnClick({R.id.image_close, R.id.searchCard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_close:
                autoCompleteTextView.setText("");
                autoCompleteTextView.setFocusable(true);
                autoCompleteTextView.setFocusableInTouchMode(true);
                break;
            case R.id.searchCard:

                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        locationManager.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        locationManager.onStop();
        super.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        /*Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation!=null) {
        double latitude = lastLocation.getLatitude();
        double longitude = lastLocation.getLongitude();
        sharedPreference.putSharedPrefData(Constants.LATITUDE, String.valueOf(latitude));
        sharedPreference.putSharedPrefData(Constants.LONGITUDE, String.valueOf(longitude));
        setLocation(latitude,longitude,lastLocation);

        }
*/

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void setLocation(double latitude, double longitude) {

        double lats = Double.valueOf(latitude);
        double longis = Double.valueOf(longitude);

        sharedPreference.putSharedPrefData(Constants.LATITUDE, String.valueOf(latitude));
        sharedPreference.putSharedPrefData(Constants.LONGITUDE, String.valueOf(longitude));

        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(lats, longis);
        markerOptions.position(latLng);

        if (map != null) {
            map.clear();
            map.addMarker(markerOptions);
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
        Geocoder gcd = new Geocoder(GetCurrentLocationActivity.this, Locale.ENGLISH);
        List<Address> addresses = new ArrayList<>();
        try {
            addresses = gcd.getFromLocation(lats, longis, 1);
        } catch (IOException e) {
            Toast.makeText(this, "service not avaolable", Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            Toast.makeText(this, "Invalid latlong", Toast.LENGTH_SHORT).show();
        }
        if (addresses == null || addresses.size() == 0) {

            Toast.makeText(this, "No address found", Toast.LENGTH_SHORT).show();

        } else {

            if (addresses.size() > 0) {

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String locality = addresses.get(0).getLocality();
                String subLocality = addresses.get(0).getSubLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                sharedPreference.putSharedPrefData(Constants.LOCALOTY, locality);
                sharedPreference.putSharedPrefData(Constants.SUBLOCALITY, subLocality);
                sharedPreference.putSharedPrefData(Constants.ADMINISTRATIVEADDR, state);
                sharedPreference.putSharedPrefData(Constants.COUNTRY, country);
                sharedPreference.putSharedPrefData(Constants.PINCODE, postalCode);

                String addr = "";
                if (subLocality != null) {
                    addr = subLocality + "," + locality + "," + state + "," + country + "," + postalCode;
                } else {
                    addr = locality + "," + state + "," + country + "," + postalCode;
                }
                sharedPreference.putSharedPrefData(Constants.FORMATTEDADDR, addr);

            }
        }

    }

    @OnClick(R.id.button_set_current_location)
    public void onViewClicked() {
        RegScreen3Fragment.setCurrentLocation(sharedPreference.getSharedPrefData(Constants.FORMATTEDADDR));
        finish();
    }

    @Override
    public void onLocationFound(Location mLastLocation) {
        setLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude());
    }

    @Override
    public void onLocationLost(String s) {

    }

   /* class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            String addr = resultData.getString(Constants.RESULT_DATA_KEY);

        }
    }*/
}
