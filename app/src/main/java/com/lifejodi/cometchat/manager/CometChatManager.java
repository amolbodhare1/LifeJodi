package com.lifejodi.cometchat.manager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.inscripts.interfaces.Callbacks;
import com.inscripts.interfaces.LaunchCallbacks;
import com.lifejodi.home.activity.ProfileDetailsActivity;
import com.lifejodi.interfaces.CometCallBack;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPref;

import org.json.JSONObject;

import cometchat.inscripts.com.cometchatcore.coresdk.CometChat;

public class CometChatManager{

    private static CometChatManager ourInstance = new CometChatManager();
    public static CometChatManager getInstance(){return ourInstance;};

    private CometChat cometChat;
    Context mContext;
    CometCallBack callBack;

    SharedPref sharedPref = SharedPref.getSharedInstance();
    public void initialize(Context context, CometCallBack cometCallBack){

        this.mContext = context;
        this.callBack = cometCallBack;
        sharedPref.initialize(mContext);
        cometChat = CometChat.getInstance(context);
    }

    public void initCometChat(){

        cometChat.initializeCometChat(Constants.COMET_CHAT_URL,Constants.COMET_CHAT_LICENCE_KEY, Constants.COMETCHAT_API_KEY, false, new Callbacks() {
            @Override
            public void successCallback(JSONObject jsonObject) {
                callBack.onInitializeSuccess();
            }
            @Override
            public void failCallback(JSONObject jsonObject) {
                callBack.onInitializeFailed();
            }
        });

    }

    public void cometLogin(String username, String password){

        cometChat.login(username,password, new Callbacks() {
            @Override
            public void successCallback(JSONObject responce) {
                Log.e("tag", "Login success responce = " + responce);

                callBack.onLoginSuccess();


            }

            @Override
            public void failCallback(JSONObject responce) {
                Log.e("tag",responce.toString());
                callBack.onLoginFailed();
            }
        });

    }

    public void launchChat(String userId){

        cometChat.launchCometChat((Activity)mContext, true, userId, false, true, new LaunchCallbacks() {
                    @Override
                    public void successCallback(JSONObject jsonObject) {
                        Log.e("response",jsonObject.toString());
                        callBack.onLauchSuccess();
                    }

                    @Override
                    public void failCallback(JSONObject jsonObject) {
                        Log.e("response",jsonObject.toString());
                        callBack.onLauchFailed();
                    }

                    @Override
                    public void userInfoCallback(JSONObject jsonObject) {
                        Log.e("response",jsonObject.toString());

                    }

                    @Override
                    public void chatroomInfoCallback(JSONObject jsonObject) {
                        Log.e("response",jsonObject.toString());

                    }

                    @Override
                    public void onMessageReceive(JSONObject jsonObject) {
                        Log.e("response",jsonObject.toString());

                    }

                    @Override
                    public void error(JSONObject jsonObject) {
                        Log.e("response",jsonObject.toString());

                    }

                    @Override
                    public void onWindowClose(JSONObject jsonObject) {
                        Log.e("response",jsonObject.toString());

                    }

                    @Override
                    public void onLogout() {

                    }
                });

    }

}
