package com.lifejodi.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifejodi.R;
import com.lifejodi.home.activity.ProfileDetailsActivity;
import com.lifejodi.home.data.HomeFragmentsData;
import com.lifejodi.home.data.ShortlistData;
import com.lifejodi.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JANHAVI on 2/9/2018.
 */

public class ShortListedRecyclerAdapter extends RecyclerView.Adapter<ShortListedRecyclerAdapter.ShortListedHolder> {

    Context context;
    ArrayList<HashMap<String,String>> dataList = new ArrayList<>();
    public ShortListedRecyclerAdapter(Context con,ArrayList<HashMap<String,String>> list)
    {
        context = con;
        dataList = list;
    }
    @Override
    public ShortListedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_fragment_shortlisted,parent,false);
        ShortListedHolder shortListedHolder = new ShortListedHolder(view);
        return shortListedHolder;
    }

    @Override
    public void onBindViewHolder(ShortListedHolder holder, int position) {

        int defaultImage=0;
        final HashMap<String,String> dataMap = dataList.get(position);
        holder.tvName.setText(dataMap.get(ShortlistData.FULLNAME));
        holder.tvAge.setText(dataMap.get(ShortlistData.AGE)+" yrs");
        String imageUrl = dataMap.get(ShortlistData.PROFILEPIC);
        imageUrl = imageUrl.replace("https","http");
        if(dataMap.get(ShortlistData.GENDER).equalsIgnoreCase("Male"))
        {
            defaultImage = R.drawable.image_default_man;
        }else if(dataMap.get(ShortlistData.GENDER).equalsIgnoreCase("Female"))
        {
            defaultImage = R.drawable.image_default_women;
        }
        try {
           // Glide.with(context).load(imageUrl).placeholder(defaultImage).into(holder.ivProfPic);
            Picasso.with(context).load(imageUrl).placeholder(defaultImage).into(holder.ivProfPic);
        }catch (Exception e)
        {
            Log.e("IMAGELOAD",e.getLocalizedMessage());
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileDetailsActivity.class);
                intent.putExtra(Constants.USERID,dataMap.get(ShortlistData.ID));
                intent.putExtra(Constants.PROFILEPICPATH,dataMap.get(HomeFragmentsData.PROFILEPIC));
                intent.putExtra(Constants.FULL_NAME,dataMap.get(HomeFragmentsData.FULLNAME));
                context.startActivity(intent);
            }
        });

       /* if(dataMap.get(ShortlistData.STATUS).equals("1")){
            holder.fabChat.setVisibility(View.VISIBLE);
        }else {
            holder.fabChat.setVisibility(View.GONE);
        }*/

    //    holder.fabShortlist.setVisibility(View.GONE);
        holder.fabChat.setVisibility(View.GONE);

        if(dataMap.get(ShortlistData.FEATURED_PROFILE).equals("1")){
            holder.imageFeatured.setVisibility(View.VISIBLE);
        }else {
            holder.imageFeatured.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ShortListedHolder extends RecyclerView.ViewHolder
    {

        FrameLayout layout;
        ImageView ivProfPic,imageFeatured;
        FloatingActionButton fabChat;
        TextView tvName,tvAge;
        public ShortListedHolder(View itemView) {
            super(itemView);
            layout = (FrameLayout) itemView.findViewById(R.id.layout_shortlisted_list);
            ivProfPic = (ImageView) itemView.findViewById(R.id.image_shortlisted_profpic);
            fabChat = (FloatingActionButton) itemView.findViewById(R.id.button_shortlisted_chat);
            tvName = (TextView) itemView.findViewById(R.id.text_shortlisted_name);
            tvAge = (TextView) itemView.findViewById(R.id.text_shortlisted_age);
            imageFeatured = (ImageView) itemView.findViewById(R.id.image_featured);

        }
    }
}
