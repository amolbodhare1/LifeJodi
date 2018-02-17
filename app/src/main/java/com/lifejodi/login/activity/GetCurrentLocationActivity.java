package com.lifejodi.login.activity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lifejodi.R;
import com.lifejodi.login.data.UserRegData;
import com.lifejodi.login.fragments.RegScreen3Fragment;
import com.lifejodi.login.service.FetchAddressIntentService;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;
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

public class GetCurrentLocationActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    @BindView(R.id.toolbar_get_location)
    Toolbar toolbarGetLocation;
    @BindView(R.id.map_view)
    MapView mapView;
    GoogleMap map;
    GoogleApiClient googleApiClient;
    SharedPreference sharedPreference;

    UserRegData userRegData = UserRegData.getInstance();
    @BindView(R.id.button_set_current_location)
    CustomButtonBeatles buttonSetCurrentLocation;
   // private AddressResultReceiver mResultReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);
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

        sharedPreference = SharedPreference.getSharedInstance();
        sharedPreference.initialize(this);

        mapView.onCreate(bundle);
        mapView.onResume();
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(this);
        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
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
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation!=null) {
        double latitude = lastLocation.getLatitude();
        double longitude = lastLocation.getLongitude();
        sharedPreference.putSharedPrefData(Constants.LATITUDE, String.valueOf(latitude));
        sharedPreference.putSharedPrefData(Constants.LONGITUDE, String.valueOf(longitude));
            setLocation(latitude,longitude,lastLocation);
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void setLocation(double latitude, double longitude, Location lastLocation) {

            double lats = Double.valueOf(latitude);
            double longis = Double.valueOf(longitude);

            MarkerOptions markerOptions = new MarkerOptions();
            LatLng latLng = new LatLng(lats, longis);
            markerOptions.position(latLng);
            if (map != null) {
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
            }catch (IllegalArgumentException illegalArgumentException) {
                // Catch invalid latitude or longitude values.
                Toast.makeText(this, "Invalid latlong", Toast.LENGTH_SHORT).show();
            }
            if (addresses == null || addresses.size()  == 0) {

                Toast.makeText(this, "No address found", Toast.LENGTH_SHORT).show();

            }else {


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
                    String addr = subLocality + "," + locality + "," + state + "," + country + "," + postalCode;
                    sharedPreference.putSharedPrefData(Constants.FORMATTEDADDR, addr);


                }
            }

    }

    @OnClick(R.id.button_set_current_location)
    public void onViewClicked() {
        RegScreen3Fragment.setCurrentLocation(sharedPreference.getSharedPrefData(Constants.FORMATTEDADDR));
        finish();
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
