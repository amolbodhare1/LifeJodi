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
    String tag;
    RegSpinnersData regSpinnersData = RegSpinnersData.getInstance();

    public CustomSpinnerAdapter(Context mcontext,ArrayList<HashMap<String,String>> list,String mtag) {
        context = mcontext;
        dataList =  list;
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

            switch (tag) {
                case RegSpinnersData.PROFILEFOR:
                    tv_spinner.setText(dataMap.get(RegSpinnersData.NAME));
                    break;
                case RegSpinnersData.RELIGION:
                    tv_spinner.setText(dataMap.get(RegSpinnersData.NAME));
                    break;
                case RegSpinnersData.MOTHERTOUNGUE:
                    tv_spinner.setText(dataMap.get(RegSpinnersData.NAME));
                    break;
                case RegSpinnersData.COUNTRYCODE:
                    tv_spinner.setText(dataMap.get(RegSpinnersData.COUNTRYCODE));
                    break;
                case RegSpinnersData.MARITALSTATUS:
                    tv_spinner.setText(dataMap.get(RegSpinnersData.VALUE));
                    break;
                case RegSpinnersData.CASTE:
                    tv_spinner.setText(dataMap.get(RegSpinnersData.NAME));
                    break;
                case RegSpinnersData.COUNTRY:
                    tv_spinner.setText(dataMap.get(RegSpinnersData.NAME));
                    break;
                case RegSpinnersData.PHYSICALSTATUS:
                    tv_spinner.setText(dataMap.get(RegSpinnersData.VALUE));
                    break;
                case RegSpinnersData.EDUCATION:
                    tv_spinner.setText(dataMap.get(RegSpinnersData.NAME));
                    break;
                case RegSpinnersData.OCCUPATION:
                    tv_spinner.setText(dataMap.get(RegSpinnersData.NAME));
                    break;
                case RegSpinnersData.CURRENCY:
                    tv_spinner.setText(dataMap.get(RegSpinnersData.VALUE));
                    break;
                case RegSpinnersData.FAMILYSTATUS:
                    tv_spinner.setText(dataMap.get(RegSpinnersData.VALUE));
                    break;
                case RegSpinnersData.FAMILYVALUES:
                    tv_spinner.setText(dataMap.get(RegSpinnersData.VALUE));
                    break;
            }

            tv_spinner.setTextColor(context.getResources().getColor(R.color.dark_grey));

        tv_spinner.setPadding(10, 0, 0, 0);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_spinner_dropdown,null);
        TextView tv_spinner_drop = (TextView) convertView.findViewById(R.id.spinnertext_drop);
        HashMap<String,String> dataMap = dataList.get(position);

            switch (tag)
            {
                case RegSpinnersData.PROFILEFOR:
                    tv_spinner_drop.setText(dataMap.get(RegSpinnersData.NAME));
                    break;
                case RegSpinnersData.RELIGION:
                    tv_spinner_drop.setText(dataMap.get(RegSpinnersData.NAME));
                    break;
                case RegSpinnersData.MOTHERTOUNGUE:
                    tv_spinner_drop.setText(dataMap.get(RegSpinnersData.NAME));
                    break;
                case RegSpinnersData.COUNTRYCODE:
                    tv_spinner_drop.setText(dataMap.get(RegSpinnersData.COUNTRYCODE));
                    break;
                case RegSpinnersData.MARITALSTATUS:
                    tv_spinner_drop.setText(dataMap.get(RegSpinnersData.VALUE));
                    break;
                case RegSpinnersData.CASTE:
                    tv_spinner_drop.setText(dataMap.get(RegSpinnersData.NAME));
                    break;
                case RegSpinnersData.COUNTRY:
                    tv_spinner_drop.setText(dataMap.get(RegSpinnersData.NAME));
                    break;
                case RegSpinnersData.PHYSICALSTATUS:
                    tv_spinner_drop.setText(dataMap.get(RegSpinnersData.VALUE));
                    break;
                case RegSpinnersData.EDUCATION:
                    tv_spinner_drop.setText(dataMap.get(RegSpinnersData.NAME));
                    break;
                case RegSpinnersData.OCCUPATION:
                    tv_spinner_drop.setText(dataMap.get(RegSpinnersData.NAME));
                    break;
                case RegSpinnersData.CURRENCY:
                    tv_spinner_drop.setText(dataMap.get(RegSpinnersData.VALUE));
                    break;
                case RegSpinnersData.FAMILYSTATUS:
                    tv_spinner_drop.setText(dataMap.get(RegSpinnersData.VALUE));
                    break;
                case RegSpinnersData.FAMILYVALUES:
                    tv_spinner_drop.setText(dataMap.get(RegSpinnersData.VALUE));
                    break;
            }
            tv_spinner_drop.setTextColor(context.getResources().getColor(R.color.dark_grey));
            tv_spinner_drop.setPadding(30,0, 0, 0);

      //  }
        return convertView;
    }


}
