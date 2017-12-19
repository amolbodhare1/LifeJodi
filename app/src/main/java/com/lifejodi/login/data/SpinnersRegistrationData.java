package com.lifejodi.login.data;

import com.lifejodi.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Administrator on 12/18/2017.
 */

public class SpinnersRegistrationData {

    private static SpinnersRegistrationData spinnersRegistrationData = new SpinnersRegistrationData();

    public static SpinnersRegistrationData getInstance()
    {
        return spinnersRegistrationData;
    }

    public static String [] profileForArray = {"Profile created for","Myself","Son","Daughter","Brother","Sister","Relative","Friend"};

    public static String [] motherTongueArray = {"Mother tongue","English","Hindi","Marathi","Bengali","Gujrati","Kannada","Tamil"};

    public static String [] countryCodesArray = {"+91"};

    public static String [] marritalStatusArray = {"Marital status","Never married","Widowed","Divorced","Awaiting divorce"};

    public static String [] doshamArray = {"Dosham","Yes","No","Don't know"};

    public static String [] countryLivingInArray = {"Country living in","India","United states of America","Singapore","Australia"};

    public static String [] physicalStatusArray = {"Normal","Physically challenged"};

    public static String [] educationArray = {"B.Arch","BCA","BE","B.Tech","B.A","BBA"};

    public static String [] occupationArray = {"Software professional","Manager","Officer","Clerk","Auditor"};

    public static String [] employedInArray = {"Government","Private","Business","Self Employed"};

    public static String [] currencyArray = {"EUR","Rs","USD","AED"};

    public static String [] familyStatus = {"Middle class","Upper-middle class","Rich","Affluent"};

    public static String [] familyValues = {"Orthodox","Traditional","Moderate","Liberal"};

}
