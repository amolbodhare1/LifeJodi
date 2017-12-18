package com.lifejodi.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.lifejodi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ajay on 11-11-2017.
 */

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        viewPager.setAdapter(new CustomViewPagerAdapter(getSupportFragmentManager()));
    }


    public class CustomViewPagerAdapter extends FragmentPagerAdapter {

        public CustomViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    return new RegScreen1Fragment();
                case 1:
                    return new RegScreen2Fragment();
                case 2:
                    return new RegScreen3Fragment();
                case 3:
                    return new RegScreen4Fragment();

            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }


    }
}
