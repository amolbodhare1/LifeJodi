package com.lifejodi.event.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lifejodi.event.adapter.AdapterEventsList;
import com.lifejodi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ajay on 14-11-2017.
 */

public class EventsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_events)
    Toolbar toolbar;
    @BindView(R.id.recycler_events_list)
    RecyclerView recyclerEvents;

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

        recyclerEvents.setAdapter(new AdapterEventsList(this));
    }
}
