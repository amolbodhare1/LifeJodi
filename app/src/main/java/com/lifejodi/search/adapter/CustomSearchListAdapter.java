package com.lifejodi.search.adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lifejodi.R;
import com.lifejodi.home.activity.ProfileDetailsActivity;
import com.lifejodi.home.data.ShortlistData;
import com.lifejodi.home.managers.ShortListManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.search.data.SearchData;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPref;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 3/1/2018.
 */

public class CustomSearchListAdapter extends RecyclerView.Adapter<CustomSearchListAdapter.CustomSearchHolder> implements VolleyCallbackInterface {

    Context context;
    ArrayList<HashMap<String,String>> dataList = new ArrayList<>();

    SharedPref sharedPreference;
    ShortListManager shortListManager;
    ShortlistData shortlistData = ShortlistData.getInstance();

    int pos=0;
    public CustomSearchListAdapter(Context con,ArrayList<HashMap<String,String>> list)
    {
        context = con;
        dataList = list;
        sharedPreference.initialize(context);
    }
    @Override
    public CustomSearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_fragment_matches,parent,false);
        CustomSearchHolder customSearchHolder = new CustomSearchHolder(view);

        return customSearchHolder;
    }

    @Override
    public void onBindViewHolder(final CustomSearchHolder holder, int position) {
        int defaultImage=0;

        final HashMap<String,String> dataMap = dataList.get(position);
        holder.tvName.setText(dataMap.get(SearchData.FULLNAME));
        holder.tvAge.setText(dataMap.get(SearchData.AGE)+" yrs");
        String imageUrl = dataMap.get(SearchData.PROFILEPIC);
        imageUrl = imageUrl.replace("https","http");
        if(dataMap.get(SearchData.GENDER).equalsIgnoreCase("Male"))
        {
            defaultImage = R.drawable.image_default_man;
        }else if(dataMap.get(SearchData.GENDER).equalsIgnoreCase("Female"))
        {
            defaultImage = R.drawable.image_default_women;
        }
        try {
            //  Glide.with(context).load(imageUrl).placeholder(defaultImage).into(holder.ivProfPic);
            Picasso.with(context).load(imageUrl).placeholder(defaultImage).into(holder.ivProfPic);
        }catch (Exception e)
        {
            Log.e("IMAGELOAD",e.getLocalizedMessage());
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileDetailsActivity.class);
                intent.putExtra(Constants.USERID,dataMap.get(SearchData.ID));
                context.startActivity(intent);
            }
        });

        holder.fabShortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String androidDeviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                String  userId = sharedPreference.getSharedPrefData(Constants.UID);
                String profId = dataMap.get(SearchData.ID);
                shortListManager = ShortListManager.getInstance();
                shortListManager.initialize(CustomSearchListAdapter.this,context);
                holder.progressLayout.setVisibility(View.VISIBLE);
                shortListManager.shortListUser(shortListManager.getShortlistUserParams(androidDeviceId,profId,userId));
            }
        });
        holder.progressLayout.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void successCallBack(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_SHORTLIST_USER:
                String message = shortlistData.getShortlistingMessage();
                Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_SHORTLIST_USER:
                Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
                break;
        }
    }

    public class CustomSearchHolder extends RecyclerView.ViewHolder
    {
        FrameLayout layout;
        ImageView ivProfPic;
        FloatingActionButton fabChat,fabShortlist;
        TextView tvName,tvAge;
        RelativeLayout progressLayout;
        public CustomSearchHolder(View itemView) {
            super(itemView);
            layout = (FrameLayout) itemView.findViewById(R.id.layout_home_list);
            ivProfPic = (ImageView) itemView.findViewById(R.id.image_matches_profpic);
            fabChat = (FloatingActionButton) itemView.findViewById(R.id.button_matches_chat);
            fabShortlist = (FloatingActionButton) itemView.findViewById(R.id.button_matches_shortlist);
            tvName = (TextView) itemView.findViewById(R.id.text_matches_name);
            tvAge = (TextView) itemView.findViewById(R.id.text_matches_age);
            progressLayout = (RelativeLayout)itemView.findViewById(R.id.progressLayout_shortlist_user);
        }
    }
}
