package com.lifejodi.login.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lifejodi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ajay on 07-12-2017.
 */

public class SpinnerAdapter extends BaseAdapter {

    List<String> list = new ArrayList<>();
    Context context;

    public SpinnerAdapter(Context mcontext,List<String> dataList) {
        context = mcontext;
        list = dataList;
    }

    @Override
    public int getCount() {
        return list.size();
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

        tv_spinner.setText(list.get(position));
        if (position == 0){
            tv_spinner.setTextColor(context.getResources().getColor(R.color.light_gray));
        }else {
            tv_spinner.setTextColor(context.getResources().getColor(R.color.dark_grey));
        }
        tv_spinner.setPadding(10, 0, 0, 0);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_spinner_dropdown,null);
        TextView tv_spinner_drop = (TextView) convertView.findViewById(R.id.spinnertext_drop);

        //HashMap<String,String> dataMap = spinner_list.get(position);
        if(position==0)
        {
            tv_spinner_drop.setPadding(10,10, 0, 0);
            tv_spinner_drop.setTextColor(context.getResources().getColor(R.color.light_gray));
            tv_spinner_drop.setTextSize(18);

        }
        else
        {
            tv_spinner_drop.setTextColor(context.getResources().getColor(R.color.dark_grey));
            tv_spinner_drop.setPadding(30,0, 0, 0);

        }
        tv_spinner_drop.setText(list.get(position));

        return convertView;
    }


}
