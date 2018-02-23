package com.lifejodi.event.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lifejodi.R;
import com.lifejodi.utils.customfonts.CustomButtonBeatles;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2/21/2018.
 */

public class EventDetailsActivity extends AppCompatActivity {

    @BindView(R.id.button_event_register)
    CustomButtonBeatles buttonEventRegister;
    /*@BindView(R.id.toolbar_event_details)
    Toolbar toolbarEventDetails;
*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);
        initialization();
    }

    public void initialization()
    {
       /* toolbarEventDetails.setTitle("Match Night");
        setSupportActionBar(toolbarEventDetails);
        toolbarEventDetails.setNavigationIcon(R.drawable.ic_back);
        toolbarEventDetails.setTitleTextColor(Color.WHITE);
        toolbarEventDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
*/
    }
    @OnClick(R.id.button_event_register)
    public void onViewClicked() {

        Intent eventRegIntent = new Intent(EventDetailsActivity.this, EventRegistrationActivity.class);
        startActivity(eventRegIntent);
    }
}
