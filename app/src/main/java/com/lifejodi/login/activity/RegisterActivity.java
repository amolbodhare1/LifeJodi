package com.lifejodi.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.facebook.FacebookSdk;
import com.lifejodi.R;
import com.lifejodi.login.adapter.CustomViewPagerAdapter;
import com.lifejodi.login.fragments.RegScreen1Fragment;
import com.lifejodi.login.fragments.RegScreen2Fragment;
import com.lifejodi.login.fragments.RegScreen3Fragment;
import com.lifejodi.login.fragments.RegScreen4Fragment;
import com.lifejodi.login.interfaces.SetRegistrationFragment;
import com.lifejodi.login.viewpagers.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ajay on 11-11-2017.
 */

public class RegisterActivity extends AppCompatActivity implements SetRegistrationFragment {

    @BindView(R.id.view_pager)
    CustomViewPager viewPager;
    CustomViewPagerAdapter customViewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        customViewPagerAdapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(customViewPagerAdapter);
    }

    @Override
    public void setRegFragment(int pos) {
        viewPager.setCurrentItem(pos);
    }


}
