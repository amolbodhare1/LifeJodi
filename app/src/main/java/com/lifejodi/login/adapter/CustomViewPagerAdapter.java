package com.lifejodi.login.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lifejodi.login.fragments.RegScreen1Fragment;
import com.lifejodi.login.fragments.RegScreen2Fragment;
import com.lifejodi.login.fragments.RegScreen3Fragment;
import com.lifejodi.login.fragments.RegScreen4Fragment;

/**
 * Created by Administrator on 12/20/2017.
 */

public class CustomViewPagerAdapter extends FragmentPagerAdapter {

    public CustomViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
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
