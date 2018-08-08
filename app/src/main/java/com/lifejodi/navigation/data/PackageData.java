package com.lifejodi.navigation.data;

import java.util.ArrayList;
import java.util.HashMap;

public class PackageData {

    private static PackageData packageData = new PackageData();
    public static PackageData getInstance(){return packageData;};

    ArrayList<HashMap<String,String>> allPackagesList = new ArrayList<>();
    HashMap<String,String> myPackage = new HashMap<>();

    public HashMap<String, String> getMyPackage() {
        return myPackage;
    }

    public void setMyPackage(HashMap<String, String> myPackage) {
        this.myPackage = myPackage;
    }
    // Keys getAllPackages

    public static String PACKAGE_ID = "id";
    public static String PACKAGE_NAME = "package_name";
    public static String PACKAGE_DESC = "description";
    public static String PACKAGE_AMOUNT = "amount";
    public static String PACKAGE_VALIDITY = "validity_months";

    public static String PACKAGE_ADD_DATE = "add_date";
    public static String PACKAGE_EXPIRY_DATE = "expiry_date";
    public static String STATUS = "status";

    public ArrayList<HashMap<String, String>> getAllPackagesList() {
        return allPackagesList;
    }

    public void setAllPackagesList(ArrayList<HashMap<String, String>> allPackagesList) {
        this.allPackagesList = allPackagesList;
    }
}
