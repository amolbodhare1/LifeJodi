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
import com.lifejodi.utils.SharedPref;
import com.lifejodi.utils.customfonts.CustomTextBeatles;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 1/27/2018.
 */

public class MatchesFragment extends Fragment implements VolleyCallbackInterface {

    @BindView(R.id.rec_fragment_matches)
    RecyclerView recFragmentMatches;
    Unbinder unbinder;
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;
    @BindView(R.id.text_no_data_matches)
    CustomTextBeatles textNoDataMatches;

    private boolean fragmentResume = false;
    private boolean fragmentVisible = false;
    private boolean fragmentOnCreated = false;

    GridLayoutManager gridLayoutManager;
    SharedPref sharedPreference;
    MatchesRecyclerAdapter matchesRecyclerAdapter;

    HomeFragmentsManager homeFragmentsManager;
    HomeFragmentsData homeFragmentsData = HomeFragmentsData.getInstance();

    String profId = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matches, null);
        unbinder = ButterKnife.bind(this, view);

        sharedPreference = SharedPref.getSharedInstance();
        sharedPreference.initialize(getActivity());

        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recFragmentMatches.setLayoutManager(gridLayoutManager);

        if (!fragmentResume && fragmentVisible) {
            profId = sharedPreference.getSharedPrefData(Constants.UID);
            String androidDeviceId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
            homeFragmentsManager = HomeFragmentsManager.getInstance();
            homeFragmentsManager.initialize(this, getActivity());
            progressLayout.setVisibility(View.VISIBLE);
            homeFragmentsManager.getMatchesList(homeFragmentsManager.getMatchesInputParams(androidDeviceId, profId));

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
            homeFragmentsManager.getMatchesList(homeFragmentsManager.getMatchesInputParams(androidDeviceId, profId));


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
        switch (tag) {
            case Constants.TAG_HOME_MATCHES:
                progressLayout.setVisibility(View.GONE);
                String status = homeFragmentsData.getMatchesStatus();
                String message = homeFragmentsData.getMatchesMessage();
                if (status.equals("1")) {
                    ArrayList<HashMap<String, String>> dataList = homeFragmentsData.getMatchesList();
                    if(dataList.size()>0)
                    {
                        matchesRecyclerAdapter = new MatchesRecyclerAdapter(getActivity(), dataList);
                        recFragmentMatches.setAdapter(matchesRecyclerAdapter);
                        textNoDataMatches.setVisibility(View.GONE);
                        recFragmentMatches.setVisibility(View.VISIBLE);
                    }else {
                        textNoDataMatches.setVisibility(View.VISIBLE);
                        recFragmentMatches.setVisibility(View.GONE);
                    }

                } else if (status.equals("0")) {
                    Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_HOME_MATCHES:
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
