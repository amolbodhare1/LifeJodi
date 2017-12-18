package com.lifejodi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajay on 16-11-2017.
 */

public class PaymentActivity extends AppCompatActivity {

    @BindView(R.id.image_visa)
    ImageView imageVisa;
    @BindView(R.id.image_mastercard)
    ImageView imageMastercard;
    @BindView(R.id.image_paypal)
    ImageView imagePaypal;
    @BindView(R.id.image_cc)
    ImageView imageCc;
    @BindView(R.id.edit_card_number)
    EditText editCardNumber;
    @BindView(R.id.edit_exp_date)
    EditText editExpDate;
    @BindView(R.id.edit_exp_year)
    EditText editExpYear;
    @BindView(R.id.edit_cvv)
    EditText editCvv;
    @BindView(R.id.button_paynow)
    Button buttonPaynow;
    @BindView(R.id.radio_net_banking)
    RadioButton radioNetBanking;
    @BindView(R.id.radio_door_step_payment)
    RadioButton radioDoorStepPayment;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.layout_call)
    LinearLayout layoutCall;
    @BindView(R.id.layout_chat)
    LinearLayout layoutChat;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);

        initialization();
    }

    private void initialization() {

        toolbar.setTitle("Payment");
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

    @OnClick(R.id.button_paynow)
    public void onViewClicked() {
    }
}
