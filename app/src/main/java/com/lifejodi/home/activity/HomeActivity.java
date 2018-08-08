package com.lifejodi.home.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lifejodi.InboxActivity;
import com.lifejodi.R;
import com.lifejodi.event.activity.EventsActivity;
import com.lifejodi.home.adapters.HomeViewPagerAdapter;
import com.lifejodi.login.activity.LoginActivity;
import com.lifejodi.login.data.UploadProfilePicData;
import com.lifejodi.login.manager.UploadProfilePicManager;
import com.lifejodi.navigation.activities.DailyRecommActivity;
import com.lifejodi.navigation.activities.PackagesActivity;
import com.lifejodi.navigation.activities.ShowProfileDataActivity;
import com.lifejodi.navigation.data.PackageData;
import com.lifejodi.navigation.manager.PackageManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.radarsearch.activities.RadarSearchActivity;
import com.lifejodi.search.activities.SearchActivity;
import com.lifejodi.settings.SettingsActivity;
import com.lifejodi.utils.AppController;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.PickerBuilder;
import com.lifejodi.utils.SharedPref;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ajay on 11-11-2017.
 */

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, VolleyCallbackInterface {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    /*@BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;*/
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    TextView tvHeaderName,tvHeaderProfId,textMyPackage;
    ImageView ivAddProfilePic;
    CircleImageView ivUserProfilePic;
    RelativeLayout layoutAddProfPic;

    SharedPref sharedPreference = SharedPref.getSharedInstance();

    AppController appController = AppController.getInstance();
    HomeViewPagerAdapter homeViewPagerAdapter;
    UploadProfilePicData uploadProfilePicData = UploadProfilePicData.getInstance();
    UploadProfilePicManager uploadProfilePicManager ;

    public final int  PICK_IMAGE_REQUEST= 100;

    PickerBuilder pickerBuilder;
    String userId="",androidDeviceId="";

    @BindView(R.id.layout_search_bottom)
    LinearLayout layoutSearchBottom;
    @BindView(R.id.layout_mailbox_bottom)
    LinearLayout layoutMailboxBottom;
    @BindView(R.id.layout_chat_bottom)
    LinearLayout layoutChatBottom;
    @BindView(R.id.layout_radar_bottom)
    LinearLayout layoutRadarBottom;

    PackageManager packageManager = PackageManager.getInstance();
    PackageData packageData = PackageData.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        packageManager.initialize(this,this);

        initialization();
    }

    private void initialization() {
        sharedPreference.initialize(this);
        appController.initialize(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setTabTextColors(Color.parseColor("#FFDCDADA"), Color.parseColor("#ffffff"));
        tabs.addTab(tabs.newTab().setText("MATCHES"));
      //  tabs.addTab(tabs.newTab().setText("NEW MATCHES"));
        tabs.addTab(tabs.newTab().setText("SHORTLISTED"));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        View view = navView.getHeaderView(0);
        tvHeaderName = (TextView) view.findViewById(R.id.nav_header_username);
        tvHeaderName.setText(sharedPreference.getSharedPrefData(Constants.LOGINNAME));
        tvHeaderProfId = (TextView)view.findViewById(R.id.text_navigation_profid);
        textMyPackage = (TextView)view.findViewById(R.id.text_my_package);
        tvHeaderProfId.setText(sharedPreference.getSharedPrefData(Constants.PROFILEID));

        ivAddProfilePic = (ImageView)view.findViewById(R.id.image_add_profile_photo);
        ivUserProfilePic = (CircleImageView)view.findViewById(R.id.image_profile_pic);
        layoutAddProfPic = (RelativeLayout)view.findViewById(R.id.layout_add_profile_photo);

        homeViewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(homeViewPagerAdapter);


        pickerBuilder = new  PickerBuilder(HomeActivity.this, PickerBuilder.SELECT_FROM_GALLERY);
        ivAddProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openImagePicker();
            }
        });

        userId = sharedPreference.getSharedPrefData(Constants.UID);
        androidDeviceId = sharedPreference.getSharedPrefData(Constants.DEVICE_ID);

        packageManager.getMyPackage(packageManager.getMyPackagesInput(androidDeviceId,userId));
    }

    @OnClick({R.id.layout_search_bottom,R.id.layout_mailbox_bottom,R.id.layout_chat_bottom,R.id.layout_radar_bottom})
    void Click(View v){

        switch (v.getId()){

            case R.id.layout_search_bottom:
                startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                break;
            case R.id.layout_mailbox_bottom:
                startActivity(new Intent(getApplicationContext(),InboxActivity.class));
                break;
            case R.id.layout_chat_bottom:
               // startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
                break;
            case R.id.layout_radar_bottom:
                startActivity(new Intent(HomeActivity.this,RadarSearchActivity.class));
                break;
        }
    }

    public void openImagePicker()
    {
        pickerBuilder = new  PickerBuilder(HomeActivity.this, PickerBuilder.SELECT_FROM_GALLERY);
        pickerBuilder.setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
            @Override
            public void onImageReceived(Uri imageUri) {
                ivUserProfilePic.setImageURI(imageUri);
                InputStream iStream = null;
                String path = imageUri.getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();


                String imageString = "data:image/jpeg;base64,"+Base64.encodeToString(byteArray, Base64.DEFAULT);
                uploadProfilePicManager = UploadProfilePicManager.getInstance();
                uploadProfilePicManager.initialize(HomeActivity.this,HomeActivity.this);
                uploadProfilePicManager.uploadProfilePic(uploadProfilePicManager.getUploadProfPicParams("1",imageString));
            }

        }).setImageName("testImage")
                .setImageFolderName("testFolder")
                .withTimeStamp(true)
                .setCropScreenColor(Color.CYAN)
                .start();

    }
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (id == R.id.nav_my_matches) {
            // Handle the camera action
            viewPager.setCurrentItem(0);
        } else if (id == R.id.nav_upgrade_account) {
            Intent packageIntent = new Intent(this, PackagesActivity.class);
            startActivity(packageIntent);
        }
        else if (id == R.id.nav_edit_profile) {
            Intent editIntent = new Intent(HomeActivity.this, ShowProfileDataActivity.class);
            startActivity(editIntent);

        } else if (id == R.id.nav_setting) {
            Intent settingsIntent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);

        } else if (id == R.id.nav_logout) {

            showLogoutDialog();

        } else if (id == R.id.nav_daily_recomm) {
            Intent dailyRecommintent = new Intent(HomeActivity.this, DailyRecommActivity.class);
            startActivity(dailyRecommintent);
        }
        else if (id == R.id.nav_events) {
            Intent dailyRecommintent = new Intent(HomeActivity.this, EventsActivity.class);
            startActivity(dailyRecommintent);
        }

        return true;
    }

    private void showLogoutDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Are you sure you want to logout ?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sharedPreference.putBoolean(Constants.LOGINSTATUS, false);
                Intent homeIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();

    }


    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_mail:
                    startActivity(new Intent(getApplicationContext(),InboxActivity.class));
                    return true;
                case R.id.navigation_notifications:
                    startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
                    return true;
                case R.id.navigation_search:
                    startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                    return true;
                case R.id.navigation_events:
                    startActivity(new Intent(getApplicationContext(),EventsActivity.class));
                    return true;
            }
            return false;
        }
    };*/

    @Override
    protected void onResume() {
        super.onResume();

        String profPicPath = sharedPreference.getSharedPrefData(Constants.PROFILEPICPATH);
        if(profPicPath.equals(""))
        {
            layoutAddProfPic.setVisibility(View.VISIBLE);

        }else {
            layoutAddProfPic.setVisibility(View.GONE);
            Picasso.with(HomeActivity.this)
                    .load(profPicPath)
                    .placeholder(R.drawable.image_event1)
                    .into(ivUserProfilePic);
        }
        //    bottomNavigation.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawerLayout.isDrawerOpen(navView)) {
                drawerLayout.closeDrawer(navView);
            } else {
                appController.doubleTapToExit(HomeActivity.this);
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void successCallBack(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_UPLOAD_PROFILE_PIC:
                String status=uploadProfilePicData.getUploadPicStatus();
                if(status.equals("1"))
                {
                    layoutAddProfPic.setVisibility(View.GONE);
                    HashMap<String,String> dataMap = uploadProfilePicData.getUploadPicResultMap();
                    String imagePath = dataMap.get(UploadProfilePicData.IMAGEPATH);
                    sharedPreference.putSharedPrefData(Constants.PROFILEPICPATH,imagePath);
                }else {
                    Toast.makeText(this, ""+uploadProfilePicData.getUploadPicMessage(), Toast.LENGTH_SHORT).show();
                }

                break;
            case Constants.TAG_MY_PACKAGE:

                textMyPackage.setText("Package ("+Constants.getDateString(packageData.getMyPackage().get(PackageData.PACKAGE_ADD_DATE))+" - "
                +Constants.getDateString(packageData.getMyPackage().get(PackageData.PACKAGE_EXPIRY_DATE))+")");

                break;
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_UPLOAD_PROFILE_PIC:
                Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
