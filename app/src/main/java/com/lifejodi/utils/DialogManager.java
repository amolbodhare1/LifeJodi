package com.lifejodi.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.lifejodi.R;


public class DialogManager {

    private static DialogManager ourInstance = new DialogManager();
    public static DialogManager getInstance() {
        return ourInstance;
    }

    Dialog proDialog;
    Context mContext;

    public void initialize(Context context){

        this.mContext = context;
        proDialog = new Dialog(mContext);
        proDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        proDialog.setContentView(R.layout.dialog_progress);
        proDialog.setCancelable(false);
        proDialog.setCanceledOnTouchOutside(false);

    }

    public void showDialog(String msg){

        TextView textProgress = (TextView)proDialog.findViewById(R.id.text_progress);

        if(msg.equals("")){
            textProgress.setVisibility(View.GONE);
        }else {
            textProgress.setText(msg);
            textProgress.setVisibility(View.VISIBLE);
        }
        if(!((Activity)mContext).isFinishing()){
            proDialog.show();
        }

    }
    public void dismissDialog(){
        if(!((Activity)mContext).isFinishing()){
            proDialog.dismiss();
        }

    }

}
