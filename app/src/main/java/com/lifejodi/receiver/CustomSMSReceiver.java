package com.lifejodi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.lifejodi.interfaces.SmsListener;

/**
 * Created by Administrator on 3/31/2018.
 */

public class CustomSMSReceiver extends BroadcastReceiver {
    private static SmsListener mListener;
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle data  = intent.getExtras();

        Object[] pdus = (Object[]) data.get("pdus");

        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

            String sender = smsMessage.getDisplayOriginatingAddress();
            //You must check here if the sender is your provider and not another one with same text.
            String messageBody = smsMessage.getMessageBody();
            //Pass on the text to our listener.
            try {
                if (messageBody != null) {
                    if (messageBody.contains("OTP to login to your LIFEJODI account is")) {
                        mListener.messageReceived(messageBody);
                    }
                }
            }catch (Exception e){

            }
        }
}
    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
