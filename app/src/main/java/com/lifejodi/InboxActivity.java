package com.lifejodi;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.lifejodi.home.managers.ProfileDataManager;
import com.lifejodi.interfaces.requestCallback;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ajay on 13-11-2017.
 */

public class InboxActivity extends AppCompatActivity implements VolleyCallbackInterface , requestCallback{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    ProfileDataManager profileDataManager = ProfileDataManager.getInstance();
    int count = 0;

    FragmentPending fragmentPending = new FragmentPending();
    FragmentAccepted fragmentAccepted = new FragmentAccepted();
    FragmentRejected fragmentRejected = new FragmentRejected();

    ArrayList<Fragment> fragmentList = new ArrayList<>();

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        ButterKnife.bind(this);

        initialization();
    }

    private void initialization() {

        progressDialog= new ProgressDialog(this);

        fragmentList.add(fragmentPending);
        fragmentList.add(fragmentAccepted);
        fragmentList.add(fragmentRejected);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        getSupportActionBar().setTitle("Inbox");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        profileDataManager.initialize(this,this);

        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setTabTextColors(Color.parseColor("#FFDCDADA"), Color.parseColor("#ffffff"));
        tabs.addTab(tabs.newTab().setText("PENDING"));
        tabs.addTab(tabs.newTab().setText("ACCEPTED"));
        tabs.addTab(tabs.newTab().setText("DECLINE"));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        progressDialog.show();
        profileDataManager.getRequestStatus(profileDataManager.getRequestStatusInput());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    //    getMenuInflater().inflate(R.menu.menu_inbox, menu);
        return true;
    }

    @Override
    public void successCallBack(String msg, String tag) {

        switch (tag){

            case Constants.TAG_REQUEST_STATUS:

                progressDialog.dismiss();
                if(count==0) {
                    viewpager.setAdapter(new ViewpagerInboxAdapter(getSupportFragmentManager()));
                }else {
                    fragmentPending.setRecyclerAdapter();
                    fragmentAccepted.setRecyclerAdapter();
                    fragmentRejected.setRecyclerAdapter();
                }
                count++;

                break;

            case Constants.TAG_ACCEPT_REQUEST:

                profileDataManager.getRequestStatus(profileDataManager.getRequestStatusInput());

                break;

            case Constants.TAG_REJECT_REQUEST:

                profileDataManager.getRequestStatus(profileDataManager.getRequestStatusInput());

                break;

        }


    }

    @Override
    public void errorCallBack(String msg, String tag) {
        progressDialog.dismiss();
    }

    @Override
    public void onRequestAccept(String id) {
        progressDialog.show();
        profileDataManager.acceptRequest(profileDataManager.getAccpetRequestInput(id));
    }

    @Override
    public void onRequestReject(String id) {
        progressDialog.show();
        profileDataManager.rejectRequest(profileDataManager.getRejectRequestInput(id));
    }
}
