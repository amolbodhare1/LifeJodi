package com.lifejodi.login.data;

/**
 * Created by Administrator on 12/18/2017.
 */

public class RegSpinnersStaticData {

    private static RegSpinnersStaticData spinnersRegistrationData = new RegSpinnersStaticData();

    public static RegSpinnersStaticData getInstance()
    {
        return spinnersRegistrationData;
    }

    public static String [] profileForArray = {"Profile created for","Myself","Son","Daughter","Brother","Sister","Relative","Friend"};

    public static String [] motherTongueArray = {"Mother tongue","English","Hindi","Marathi","Bengali","Gujrati","Kannada","Tamil"};

    public static String [] countryCodesArray = {"+91"};

    public static String [] marritalStatusArray = {"Marital status","Never married","Widowed","Divorced","Awaiting divorce"};

    public static String [] doshamArray = {"Dosham","Yes","No","Don't know"};

    public static String [] countryLivingInArray = {"Country living in","India","United states of America","Singapore","Australia"};

    public static String [] heightArray = {"Height","4 ft 6 in","4 ft 5 in","4 ft 4 in","5 ft 5 in","6 ft 2 in"};

    public static String [] physicalStatusArray = {"Physical status","Normal","Physically challenged"};

    public static String [] educationArray = {"Education","B.Arch","BCA","BE","B.Tech","B.A","BBA"};

    public static String [] occupationArray = {"Occupation","Software professional","Manager","Officer","Clerk","Auditor"};

    public static String [] employedInArray = {"Employed in","Government","Private","Business","Self Employed"};

    public static String [] currencyArray = {"Currency","EUR","Rs","USD","AED"};

    public static String [] familyStatus = {"Family status","Middle class","Upper-middle class","Rich","Affluent"};

    public static String [] familyValues = {"Family values","Orthodox","Traditional","Moderate","Liberal"};

}
