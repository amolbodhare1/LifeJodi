package com.lifejodi.navigation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lifejodi.R;
import com.lifejodi.navigation.data.PackageData;

import java.util.ArrayList;
import java.util.HashMap;

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.PackageView>{

    private Context mContext;
    private ArrayList<HashMap<String,String>> packagesList = new ArrayList<>();

    public PackagesAdapter(Context mContext, ArrayList<HashMap<String, String>> packagesList) {
        this.mContext = mContext;
        this.packagesList = packagesList;
    }

    @NonNull
    @Override
    public PackageView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_packages,parent,false);

        return new PackageView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageView holder, int position) {

        HashMap<String,String> map = packagesList.get(position);

        holder.packageName.setText(map.get(PackageData.PACKAGE_NAME));
        holder.packageAmount.setText(map.get(PackageData.PACKAGE_AMOUNT)+"/");
        holder.packageValidity.setText(map.get(PackageData.PACKAGE_VALIDITY)+" Months");


    }

    @Override
    public int getItemCount() {
        return packagesList.size();
    }

    public class PackageView extends RecyclerView.ViewHolder{

        TextView packageName,packageAmount,packageValidity;
        public PackageView(View itemView) {
            super(itemView);

            packageName = (TextView)itemView.findViewById(R.id.text_package_name);
            packageAmount = (TextView)itemView.findViewById(R.id.text_package_amount);
            packageValidity = (TextView)itemView.findViewById(R.id.text_package_validity);


        }
    }

}
