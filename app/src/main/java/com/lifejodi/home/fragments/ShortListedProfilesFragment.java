package com.lifejodi.home.fragments;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.home.adapters.MatchesRecyclerAdapter;
import com.lifejodi.home.adapters.ShortListedRecyclerAdapter;
import com.lifejodi.home.data.HomeFragmentsData;
import com.lifejodi.home.data.ShortlistData;
import com.lifejodi.home.managers.HomeFragmentsManager;
import com.lifejodi.home.managers.ShortListManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by JANHAVI on 2/9/2018.
 */

public class ShortListedProfilesFragment extends Fragment implements VolleyCallbackInterface {

    @BindView(R.id.rec_fragment_shortlisted)
    RecyclerView recFragmentShortlisted;
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;
    Unbinder unbinder;

    private boolean fragmentResume = false;
    private boolean fragmentVisible = false;
    private boolean fragmentOnCreated = false;

    GridLayoutManager gridLayoutManager;
    SharedPreference sharedPreference;

    String profId = "";

    ShortListManager shortListManager;
    ShortlistData shortlistData = ShortlistData.getInstance();
    ShortListedRecyclerAdapter shortListedRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shortlisted_profiles, null);
        unbinder = ButterKnife.bind(this, view);

        sharedPreference = SharedPreference.getSharedInstance();
        sharedPreference.initialize(getActivity());

        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recFragmentShortlisted.setLayoutManager(gridLayoutManager);

        if (!fragmentResume && fragmentVisible) {
            profId = sharedPreference.getSharedPrefData(Constants.PROFILEID);
            String androidDeviceId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
            shortListManager = ShortListManager.getInstance();
            shortListManager.initialize(this,getActivity());
            progressLayout.setVisibility(View.VISIBLE);
            shortListManager.getShortListedUsersList(shortListManager.getShortlistedUsersListParams(androidDeviceId,profId));

        }
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {   // only at fragment screen is resumed
            fragmentResume = true;
            fragmentVisible = false;
            fragmentOnCreated = true;
            profId = sharedPreference.getSharedPrefData(Constants.PROFILEID);
            String androidDeviceId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
            shortListManager = ShortListManager.getInstance();
            shortListManager.initialize(this,getActivity());
            progressLayout.setVisibility(View.VISIBLE);
            shortListManager.getShortListedUsersList(shortListManager.getShortlistedUsersListParams(androidDeviceId,profId));


        } else if (isVisibleToUser) {        // only at fragment onCreated
            fragmentResume = false;
            fragmentVisible = true;
            fragmentOnCreated = true;


        } else if (!isVisibleToUser && fragmentOnCreated) {// only when you go out of fragment screen
            fragmentVisible = false;
            fragmentResume = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void successCallBack(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_SHORTLISTED_USERSLIST:
                progressLayout.setVisibility(View.GONE);
                String status = shortlistData.getShortListedUsersStatus();
                String message = shortlistData.getShortListedUsersMessage();
                if(status.equals("1"))
                {
                    shortListedRecyclerAdapter = new ShortListedRecyclerAdapter(getActivity(),shortlistData.getShortListedUsersList());
                    recFragmentShortlisted.setAdapter(shortListedRecyclerAdapter);
                }else {
                    Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();

                }

                break;
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_SHORTLISTED_USERSLIST:
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
