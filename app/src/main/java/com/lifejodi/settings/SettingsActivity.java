package com.lifejodi.settings;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.lifejodi.NotificationActivity;
import com.lifejodi.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 3/3/2018.
 */

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_settings)
    Toolbar toolbarSettings;
    @BindView(R.id.layout_settings_notifications)
    LinearLayout layoutSettingsNotifications;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initialization();
    }

    public void initialization() {
        toolbarSettings.setTitle("Settings");
        setSupportActionBar(toolbarSettings);
        toolbarSettings.setNavigationIcon(R.drawable.ic_back);
        toolbarSettings.setTitleTextColor(Color.WHITE);
        toolbarSettings.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @OnClick(R.id.layout_settings_notifications)
    public void onViewClicked() {
        startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
    }
}
