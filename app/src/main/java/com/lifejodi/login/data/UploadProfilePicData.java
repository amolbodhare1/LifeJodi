package com.lifejodi.login.data;

import java.util.HashMap;

/**
 * Created by Administrator on 2/23/2018.
 */

public class UploadProfilePicData {

    private static UploadProfilePicData uploadProfilePic = new UploadProfilePicData();

    public static UploadProfilePicData getInstance()
    {
        return uploadProfilePic;
    }

    public static String API = "api";
    public static String VERSION = "version";
    public static String DEVICEID = "device_id";
    public static String DATA = "data";

    public static String USERID = "user_id";
    public static String ALBUMID = "album_id";
    public static String IMAGE = "image";

    public static String STATUS = "status";
    public static String MESSAGE = "msg";
    public static String IMAGEPATH = "image_path";
    public static String FILE_NAME = "file_name";

    public static String USERPHOTOSID = "user_photos_id";

    String registerPicImage = "";

    public String getRegisterPicImage() {
        return registerPicImage;
    }

    public void setRegisterPicImage(String registerPicImage) {
        this.registerPicImage = registerPicImage;
    }

    String uploadPicStatus = "";
    public String getUploadPicStatus() {
        return uploadPicStatus;
    }

    public void setUploadPicStatus(String uploadPicStatus) {
        this.uploadPicStatus = uploadPicStatus;
    }

    String uploadPicMessage = "";
    public String getUploadPicMessage() {
        return uploadPicMessage;
    }

    public void setUploadPicMessage(String uploadPicMessage) {
        this.uploadPicMessage = uploadPicMessage;
    }

    HashMap<String,String> uploadPicResultMap = new HashMap<>();
    public HashMap<String, String> getUploadPicResultMap() {
        return uploadPicResultMap;
    }

    public void setUploadPicResultMap(HashMap<String, String> uploadPicResultMap) {
        this.uploadPicResultMap = uploadPicResultMap;
    }



}
