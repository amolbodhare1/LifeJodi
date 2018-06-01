package com.lifejodi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewpagerInboxAdapter extends FragmentPagerAdapter {

    public ViewpagerInboxAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return new FragmentPending();
            case 1:
                return new FragmentAccepted();
            case 2:
                return new FragmentRejected();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
