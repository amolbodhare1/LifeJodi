package com.lifejodi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajay on 16-11-2017.
 */

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.layout_shortlist)
    LinearLayout layoutShortlist;
    @BindView(R.id.layout_call_now)
    LinearLayout layoutCallNow;
    @BindView(R.id.layout_chat_now)
    LinearLayout layoutChatNow;
    @BindView(R.id.text_dob)
    TextView textDob;
    @BindView(R.id.text_religion)
    TextView textReligion;
    @BindView(R.id.text_mother_toungue)
    TextView textMotherToungue;
    @BindView(R.id.button_yes)
    Button buttonYes;
    @BindView(R.id.button_skip)
    Button buttonSkip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        initialization();
    }

    private void initialization() {

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.layout_shortlist, R.id.layout_call_now, R.id.layout_chat_now, R.id.button_yes, R.id.button_skip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_shortlist:
                break;
            case R.id.layout_call_now:
                startActivity(new Intent(this,VideoCallActivity.class));
                break;
            case R.id.layout_chat_now:
                break;
            case R.id.button_yes:
                break;
            case R.id.button_skip:
                break;
        }
    }
}
