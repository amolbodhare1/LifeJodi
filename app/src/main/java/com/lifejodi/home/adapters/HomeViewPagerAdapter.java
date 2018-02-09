package com.lifejodi.home.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lifejodi.home.fragments.MatchesFragment;
import com.lifejodi.home.fragments.NewMatchesFragment;
import com.lifejodi.home.fragments.ShortListedProfilesFragment;
import com.lifejodi.login.fragments.RegScreen1Fragment;
import com.lifejodi.login.fragments.RegScreen2Fragment;
import com.lifejodi.login.fragments.RegScreen3Fragment;
import com.lifejodi.login.fragments.RegScreen4Fragment;

/**
 * Created by Administrator on 1/27/2018.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return new MatchesFragment();
            case 1:
                return new NewMatchesFragment();
            case 2:
                return new ShortListedProfilesFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
