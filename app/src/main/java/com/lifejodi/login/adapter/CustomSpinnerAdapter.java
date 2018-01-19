package com.lifejodi.login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lifejodi.R;
import com.lifejodi.login.data.RegSpinnersData;
import com.lifejodi.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 12/18/2017.
 */

public class CustomSpinnerAdapter  extends BaseAdapter {

    ArrayList<HashMap<String,String>> dataList = new ArrayList<>();
    Context context;
    String hintText,tag;

    public CustomSpinnerAdapter(Context mcontext,ArrayList<HashMap<String,String>> list,String text,String mtag) {
        context = mcontext;
        dataList =  list;
        hintText = text;
        tag = mtag;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_spinner_list,null);
        TextView tv_spinner = (TextView) convertView.findViewById(R.id.spinnertext);

        HashMap<String,String> dataMap = dataList.get(position);
        if (position == 0){
            tv_spinner.setText(hintText);
            tv_spinner.setTextColor(context.getResources().getColor(R.color.light_gray));
        }else {
            if(tag.equalsIgnoreCase(Constants.TAG_GET_RELIGION)) {
                tv_spinner.setText(dataMap.get(RegSpinnersData.KEY_RELIGIONNAME));
            }else if(tag.equalsIgnoreCase(Constants.TAG_GET_CAST)) {
                tv_spinner.setText(dataMap.get(RegSpinnersData.KEY_CASTNAME));
            }else if(tag.equalsIgnoreCase(Constants.TAG_GET_MARITALSTATUS)) {
                tv_spinner.setText(dataMap.get(RegSpinnersData.KEY_MARITALSTATUSNAME));
            }
            tv_spinner.setTextColor(context.getResources().getColor(R.color.dark_grey));
        }
        tv_spinner.setPadding(10, 0, 0, 0);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_spinner_dropdown,null);
        TextView tv_spinner_drop = (TextView) convertView.findViewById(R.id.spinnertext_drop);
        HashMap<String,String> dataMap = dataList.get(position);
        //HashMap<String,String> dataMap = spinner_list.get(position);
        if(position==0)
        {
            tv_spinner_drop.setText(hintText);
            tv_spinner_drop.setPadding(10,10, 0, 0);
            tv_spinner_drop.setTextColor(context.getResources().getColor(R.color.light_gray));
            tv_spinner_drop.setTextSize(18);

        }
        else
        {
            if(tag.equalsIgnoreCase(Constants.TAG_GET_RELIGION)) {
                tv_spinner_drop.setText(dataMap.get(RegSpinnersData.KEY_RELIGIONNAME));
            }else if(tag.equalsIgnoreCase(Constants.TAG_GET_CAST)) {
                tv_spinner_drop.setText(dataMap.get(RegSpinnersData.KEY_CASTNAME));
            }else if(tag.equalsIgnoreCase(Constants.TAG_GET_MARITALSTATUS)) {
                tv_spinner_drop.setText(dataMap.get(RegSpinnersData.KEY_MARITALSTATUSNAME));
            }
            tv_spinner_drop.setTextColor(context.getResources().getColor(R.color.dark_grey));
            tv_spinner_drop.setPadding(30,0, 0, 0);

        }
        return convertView;
    }


}
