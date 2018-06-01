package com.lifejodi;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lifejodi.home.activity.ProfileDetailsActivity;
import com.lifejodi.home.data.ProfilesData;
import com.lifejodi.interfaces.requestCallback;
import com.lifejodi.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestStatusAdapter extends RecyclerView.Adapter<RequestStatusAdapter.RequestView>{


    private Context mContext;
    private ArrayList<HashMap<String,String>> requestList = new ArrayList<>();

    requestCallback requestCallback;
    public RequestStatusAdapter(Context mContext, ArrayList<HashMap<String, String>> requestList) {
        this.mContext = mContext;
        this.requestList = requestList;
        requestCallback = (com.lifejodi.interfaces.requestCallback)mContext;
    }

    @NonNull
    @Override
    public RequestView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_request_status,parent,false);

        return new RequestView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestView holder, int position) {

        final HashMap<String,String> map = requestList.get(position);

        try {
            // Glide.with(context).load(imageUrl).placeholder(defaultImage).into(holder.ivProfPic);
            Picasso.with(mContext).load(map.get(ProfilesData.PROFILEPIC)).placeholder(R.drawable.image_default_man).into(holder.imageUser);
        }catch (Exception e)
        {
            Log.e("IMAGELOAD",e.getLocalizedMessage());
        }

        holder.textUsername.setText(map.get(ProfilesData.FULLNAME));
        holder.textAge.setText(map.get(ProfilesData.AGE));

        holder.layoutAccept.setVisibility(View.GONE);
        if(map.get(ProfilesData.STATUS).equals("0")){
            if(map.get(ProfilesData.MODE).equals("1")) {
                holder.layoutAccept.setVisibility(View.VISIBLE);
                holder.textRequestStatus.setText(map.get(ProfilesData.FULLNAME)+" wants to connect with you !!");
            }else {
                holder.textRequestStatus.setText("Request sent !!");
            }
        }else if(map.get(ProfilesData.STATUS).equals("1")){
            holder.textRequestStatus.setText("You can chat now !!");
        }else if(map.get(ProfilesData.STATUS).equals("2")){
            holder.textRequestStatus.setText("Request rejected !!");
        }

        holder.cardRequestStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileDetailsActivity.class);
                intent.putExtra(Constants.USERID,map.get(ProfilesData.ID));
                mContext.startActivity(intent);
            }
        });

        holder.textAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestCallback.onRequestAccept(map.get(ProfilesData.ID));

            }
        });

        holder.textReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestCallback.onRequestReject(map.get(ProfilesData.ID));

            }
        });

    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class RequestView extends RecyclerView.ViewHolder{

        ImageView imageUser;
        TextView textUsername,textAge,textRequestStatus,textAccept,textReject;
        LinearLayout layoutAccept;
        CardView cardRequestStatus;
        public RequestView(View itemView) {
            super(itemView);

            imageUser = (ImageView)itemView.findViewById(R.id.image_request_user);
            textUsername = (TextView)itemView.findViewById(R.id.text_request_name);
            textAge = (TextView)itemView.findViewById(R.id.text_request_age);
            textRequestStatus = (TextView)itemView.findViewById(R.id.text_request_status);
            textAccept = (TextView)itemView.findViewById(R.id.text_accept_request);
            textReject = (TextView)itemView.findViewById(R.id.text_reject_request);
            layoutAccept = (LinearLayout)itemView.findViewById(R.id.layout_accept);
            cardRequestStatus = (CardView)itemView.findViewById(R.id.card_request_status);

        }
    }

}
