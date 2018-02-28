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
import com.lifejodi.home.data.HomeFragmentsData;
import com.lifejodi.home.managers.HomeFragmentsManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by JANHAVI on 2/8/2018.
 */

public class NewMatchesFragment extends Fragment implements VolleyCallbackInterface {

    @BindView(R.id.rec_fragment_new_matches)
    RecyclerView recFragmentNewMatches;
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;
    Unbinder unbinder;

    private boolean fragmentResume = false;
    private boolean fragmentVisible = false;
    private boolean fragmentOnCreated = false;

    GridLayoutManager gridLayoutManager;
    SharedPreference sharedPreference;
    MatchesRecyclerAdapter matchesRecyclerAdapter;

    HomeFragmentsManager homeFragmentsManager;
    HomeFragmentsData homeFragmentsData = HomeFragmentsData.getInstance();

    String profId = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_matches, null);
        unbinder = ButterKnife.bind(this, view);
        sharedPreference = SharedPreference.getSharedInstance();
        sharedPreference.initialize(getActivity());

        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recFragmentNewMatches.setLayoutManager(gridLayoutManager);
        if (!fragmentResume && fragmentVisible) {
            profId = sharedPreference.getSharedPrefData(Constants.UID);
            String androidDeviceId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
            homeFragmentsManager = HomeFragmentsManager.getInstance();
            homeFragmentsManager.initialize(this, getActivity());
            progressLayout.setVisibility(View.VISIBLE);
            homeFragmentsManager.getNewMatchesList(homeFragmentsManager.getNewMatchesInputParams(androidDeviceId,profId));
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
            profId = sharedPreference.getSharedPrefData(Constants.UID);
            String androidDeviceId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
            homeFragmentsManager = HomeFragmentsManager.getInstance();
            homeFragmentsManager.initialize(this, getActivity());
            progressLayout.setVisibility(View.VISIBLE);
            homeFragmentsManager.getNewMatchesList(homeFragmentsManager.getNewMatchesInputParams(androidDeviceId,profId));

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
            case Constants.TAG_HOME_NEWMATCHES:
                progressLayout.setVisibility(View.GONE);
                String status = homeFragmentsData.getNewMatchesStatus();
                String message = homeFragmentsData.getNewMatchesMessage();
                if(status.equals("1"))
                {
                    ArrayList<HashMap<String,String>> dataList = homeFragmentsData.getNewMatchesList();
                    matchesRecyclerAdapter = new MatchesRecyclerAdapter(getActivity(),dataList);
                    recFragmentNewMatches.setAdapter(matchesRecyclerAdapter);
                }else if(status.equals("0"))
                {
                    Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_HOME_NEWMATCHES:
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
