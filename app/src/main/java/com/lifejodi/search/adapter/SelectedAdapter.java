package com.lifejodi.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lifejodi.R;
import com.lifejodi.login.data.RegSpinnersData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by parikshit on 27/2/18.
 */

public class SelectedAdapter extends RecyclerView.Adapter<SelectedAdapter.SelectedView> {

    private Context mContext;
    private ArrayList<HashMap<String,String>> categoryList;

    public SelectedAdapter(Context mContext, ArrayList<HashMap<String, String>> categoryList) {
        this.mContext = mContext;
        this.categoryList = categoryList;
    }

    @Override
    public SelectedView onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_selected_category, parent, false);

        return new SelectedView(v);
    }

    @Override
    public void onBindViewHolder(SelectedView holder, int position) {

        HashMap<String,String> map = categoryList.get(position);
        holder.textCategory.setText(map.get(RegSpinnersData.NAME));

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class SelectedView extends RecyclerView.ViewHolder{

        TextView textCategory;
        public SelectedView(View itemView) {
            super(itemView);

            textCategory = (TextView)itemView.findViewById(R.id.text_category_name);

        }
    }

}
