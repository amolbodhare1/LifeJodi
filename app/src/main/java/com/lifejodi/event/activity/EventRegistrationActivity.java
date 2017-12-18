package com.lifejodi.event.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.lifejodi.PaymentActivity;
import com.lifejodi.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajay on 14-11-2017.
 */

public class EventRegistrationActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_firstname)
    EditText editFirstname;
    @BindView(R.id.edit_lastname)
    EditText editLastname;
    @BindView(R.id.edit_mobileno)
    EditText editMobileno;
    @BindView(R.id.checkbox_terms)
    CheckBox checkboxTerms;
    @BindView(R.id.button_confirm)
    Button buttonConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registration);
        ButterKnife.bind(this);

        initialization();
    }

    private void initialization() {

        toolbar.setTitle("Registration");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.button_confirm)
    public void onViewClicked() {
        startActivity(new Intent(this,PaymentActivity.class));
    }
}
