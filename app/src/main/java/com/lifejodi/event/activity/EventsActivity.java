package com.lifejodi.event.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.event.adapter.EventsListAdapter;
import com.lifejodi.event.data.EventsData;
import com.lifejodi.event.managers.EventsManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ajay on 14-11-2017.
 */

public class EventsActivity extends AppCompatActivity implements VolleyCallbackInterface {

    @BindView(R.id.toolbar_events)
    Toolbar toolbar;
    @BindView(R.id.recycler_events_list)
    RecyclerView recyclerEvents;
    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;

    EventsManager eventsManager;
    EventsData eventsData = EventsData.getInstance();
    EventsListAdapter eventsListAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        ButterKnife.bind(this);

        initialization();
    }

    private void initialization() {

        toolbar.setTitle("Events");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerEvents.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerEvents.setLayoutManager(layoutManager);


        eventsManager = EventsManager.getInstance();
        eventsManager.initialize(this, this);
        String androidDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        progressLayout.setVisibility(View.VISIBLE);
        eventsManager.getEventsList(eventsManager.getEventsListParams(androidDeviceId));



    }

    @Override
    public void successCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_GET_EVENTS_LIST:
                progressLayout.setVisibility(View.GONE);
                ArrayList<HashMap<String,String>> dataList = eventsData.getEventsList();
                eventsListAdapter = new EventsListAdapter(EventsActivity.this,dataList);
                recyclerEvents.setAdapter(eventsListAdapter);
                break;
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        switch (tag) {
            case Constants.TAG_GET_EVENTS_LIST:
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
