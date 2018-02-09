package com.lifejodi.home.adapters;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lifejodi.R;
import com.lifejodi.home.activity.ProfileListActivity;
import com.lifejodi.home.data.HomeFragmentsData;
import com.lifejodi.home.data.ShortlistData;
import com.lifejodi.home.managers.ShortListManager;
import com.lifejodi.network.VolleyCallbackInterface;
import com.lifejodi.utils.Constants;
import com.lifejodi.utils.SharedPreference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 1/27/2018.
 */

public class MatchesRecyclerAdapter extends RecyclerView.Adapter<MatchesRecyclerAdapter.MatchesHolder> implements VolleyCallbackInterface {

    Context context;
    ArrayList<HashMap<String,String>> dataList = new ArrayList<>();
    SharedPreference sharedPreference = SharedPreference.getSharedInstance();

    ShortListManager shortListManager;
    ShortlistData shortlistData = ShortlistData.getInstance();
    RelativeLayout progressLayout;

    public MatchesRecyclerAdapter(Context con,ArrayList<HashMap<String,String>> matchesList)
    {
        context =con;
        dataList = matchesList;
        sharedPreference.initialize(context);
    }
    @Override
    public MatchesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_fragment_matches,parent,false);
        MatchesHolder matchesHolder = new MatchesHolder(view);
        return matchesHolder;
    }

    @Override
    public void onBindViewHolder(final MatchesHolder holder, int position) {
        int defaultImage=0;
        final HashMap<String,String> dataMap = dataList.get(position);
        holder.tvName.setText(dataMap.get(HomeFragmentsData.FULLNAME));
        holder.tvAge.setText(dataMap.get(HomeFragmentsData.AGE)+" yrs");
        String imageUrl = dataMap.get(HomeFragmentsData.PROFILEPIC);
        if(dataMap.get(HomeFragmentsData.GENDER).equalsIgnoreCase("Male"))
        {
             defaultImage = R.drawable.picture;
        }else if(dataMap.get(HomeFragmentsData.GENDER).equalsIgnoreCase("Female"))
        {
             defaultImage = R.drawable.images;
        }
        try {
            Glide.with(context).load(imageUrl).placeholder(defaultImage).into(holder.ivProfPic);
        }catch (Exception e)
        {
            Log.e("IMAGELOAD",e.getLocalizedMessage());
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileListActivity.class);
                context.startActivity(intent);
            }
        });

        holder.fabShortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String androidDeviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                String  profId = sharedPreference.getSharedPrefData(Constants.PROFILEID);
                String userId = dataMap.get(HomeFragmentsData.ID);
                shortListManager = ShortListManager.getInstance();
                shortListManager.initialize(MatchesRecyclerAdapter.this,context);
                progressLayout.setVisibility(View.VISIBLE);
                shortListManager.shortListUser(shortListManager.getShortlistUserParams(androidDeviceId,profId,userId));
            }
        });
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
                progressLayout.setVisibility(View.GONE);
                String message = shortlistData.getShortlistingMessage();
                Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        switch (tag)
        {
            case Constants.TAG_SHORTLIST_USER:
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public class MatchesHolder extends RecyclerView.ViewHolder
    {
        FrameLayout layout;
        ImageView ivProfPic;
        FloatingActionButton fabChat,fabShortlist;
        TextView tvName,tvAge;
        public MatchesHolder(View itemView) {
            super(itemView);
            layout = (FrameLayout) itemView.findViewById(R.id.layout_home_list);
            ivProfPic = (ImageView) itemView.findViewById(R.id.image_matches_profpic);
            fabChat = (FloatingActionButton) itemView.findViewById(R.id.button_matches_chat);
            fabShortlist = (FloatingActionButton) itemView.findViewById(R.id.button_matches_shortlist);
            tvName = (TextView) itemView.findViewById(R.id.text_matches_name);
            tvAge = (TextView) itemView.findViewById(R.id.text_matches_age);
            progressLayout = (RelativeLayout)itemView.findViewById(R.id.progressLayout);
        }
    }
}
