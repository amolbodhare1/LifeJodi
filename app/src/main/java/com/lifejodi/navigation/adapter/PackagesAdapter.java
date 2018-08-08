package com.lifejodi.navigation.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lifejodi.R;
import com.lifejodi.navigation.activities.PackageDetailsActivity;
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
    public void onBindViewHolder(@NonNull PackageView holder, final int position) {

        if(position==0)
        {
            holder.layoutPopularPackage.setVisibility(View.VISIBLE);
            holder.layoutPaidPackages.setVisibility(View.GONE);
        }else {
            holder.layoutPopularPackage.setVisibility(View.GONE);
            holder.layoutPaidPackages.setVisibility(View.VISIBLE);

            HashMap<String,String> map = packagesList.get(position);

            holder.packageName.setText(map.get(PackageData.PACKAGE_NAME));
            holder.packageAmount.setText(map.get(PackageData.PACKAGE_AMOUNT)+"/");
            holder.packageValidity.setText(map.get(PackageData.PACKAGE_VALIDITY)+" Months");
        }

        holder.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PackageDetailsActivity.class);
                intent.putExtra("POSITION",position);
                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return packagesList.size();
    }

    public class PackageView extends RecyclerView.ViewHolder{

        TextView packageName,packageAmount,packageValidity;
        LinearLayout layoutPaidPackages,layoutPopularPackage;
        FrameLayout layoutMain;
        public PackageView(View itemView) {
            super(itemView);

            packageName = (TextView)itemView.findViewById(R.id.text_package_name);
            packageAmount = (TextView)itemView.findViewById(R.id.text_package_amount);
            packageValidity = (TextView)itemView.findViewById(R.id.text_package_validity);
            layoutPaidPackages = (LinearLayout)itemView.findViewById(R.id.layout_paid_packages);
            layoutPopularPackage = (LinearLayout)itemView.findViewById(R.id.layout_popular_package);
            layoutMain = (FrameLayout) itemView.findViewById(R.id.layout_packages);

        }
    }

}
