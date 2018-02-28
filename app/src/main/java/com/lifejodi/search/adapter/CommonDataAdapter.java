package com.lifejodi.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lifejodi.R;
import com.lifejodi.login.data.RegSpinnersData;
import com.lifejodi.search.data.SearchData;
import com.lifejodi.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by parikshit on 23/2/18.
 */

public class CommonDataAdapter extends RecyclerView.Adapter<CommonDataAdapter.SelectableRow>{

    private Context mContext;
    private ArrayList<HashMap<String,String>> commonList = new ArrayList<>();

    public CommonDataAdapter(Context mContext , ArrayList<HashMap<String, String>> commonList) {
        this.mContext = mContext;
        this.commonList.addAll(commonList);
        this.commonList.remove(0);
    }

    @Override
    public SelectableRow onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dialog_common, parent, false);

        return new SelectableRow(v);
    }

    @Override
    public void onBindViewHolder(final SelectableRow holder, int position) {

        final HashMap<String,String> map = commonList.get(position);
        final String name;
        final String key;
        final String id;
        if(map.containsKey(RegSpinnersData.NAME)){
            key = RegSpinnersData.NAME;
            name = map.get(RegSpinnersData.NAME);
            id = map.get(RegSpinnersData.ID);
        }else{
            key = RegSpinnersData.VALUE;
            name = map.get(RegSpinnersData.VALUE);
            id = map.get(RegSpinnersData.ID);
        }

        holder.textname.setText(name);

        if(Constants.hasThisValue(SearchData.selectedList,RegSpinnersData.NAME,name)){
            holder.imageCheck.setImageResource(R.drawable.ic_check_filled);
        }else {
            holder.imageCheck.setImageResource(R.drawable.ic_check_empty);
        }

        holder.layoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String,String> newMap = new HashMap<>();
                final String newname;
                final String newid;
                if(map.containsKey(RegSpinnersData.NAME)){
                    newname = map.get(RegSpinnersData.NAME);
                    newid = map.get(RegSpinnersData.ID);
                } else {
                    newname = map.get(RegSpinnersData.VALUE);
                    newid = map.get(RegSpinnersData.ID);
                }

                newMap.put(RegSpinnersData.NAME,newname);
                newMap.put(RegSpinnersData.ID,newid);

                if(Constants.hasThisValue(SearchData.selectedList,RegSpinnersData.NAME,newname)){
                    holder.imageCheck.setImageResource(R.drawable.ic_check_empty);
                    SearchData.selectedList.remove(newMap);
                }else {
                    holder.imageCheck.setImageResource(R.drawable.ic_check_filled);
                    SearchData.selectedList.add(newMap);
                }

            }
        });

    }
    @Override
    public int getItemCount() {
        return commonList.size();
    }

    public class SelectableRow extends RecyclerView.ViewHolder{

        LinearLayout layoutParent;
        ImageView imageCheck;
        TextView textname;
        public SelectableRow(View itemView) {
            super(itemView);

            imageCheck = (ImageView)itemView.findViewById(R.id.image_check);
            layoutParent = (LinearLayout)itemView.findViewById(R.id.layout_parent);
            textname =(TextView)itemView.findViewById(R.id.text_name);

        }
    }


}
