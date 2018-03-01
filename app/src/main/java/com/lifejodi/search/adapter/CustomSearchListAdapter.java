package com.lifejodi.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 3/1/2018.
 */

public class CustomSearchListAdapter extends RecyclerView.Adapter<CustomSearchListAdapter.CustomSearchHolder> {

    Context context;
    ArrayList<HashMap<String,String>> dataList = new ArrayList<>();
    public CustomSearchListAdapter(Context con,ArrayList<HashMap<String,String>> list)
    {
        context = con;
        dataList = list;
    }
    @Override
    public CustomSearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CustomSearchHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class CustomSearchHolder extends RecyclerView.ViewHolder
    {

        public CustomSearchHolder(View itemView) {
            super(itemView);
        }
    }
}
