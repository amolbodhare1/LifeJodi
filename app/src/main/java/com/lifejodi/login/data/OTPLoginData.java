package com.lifejodi.login.data;

/**
 * Created by Administrator on 1/25/2018.
 */

public class OTPLoginData {

    private static OTPLoginData otpLoginData = new OTPLoginData();

    public static OTPLoginData getInstance()
    {
        return otpLoginData;
    }
}
