package com.lifejodi.event.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifejodi.event.activity.EventDetailsActivity;
import com.lifejodi.R;
import com.lifejodi.event.data.EventsData;
import com.lifejodi.home.data.HomeFragmentsData;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ajay on 14-11-2017.
 */

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.ViewHolder> {

    Context mContext;
    ArrayList<HashMap<String,String>> dataList = new ArrayList<>();
    public EventsListAdapter(Context con, ArrayList<HashMap<String,String>> list) {

        mContext = con;
        dataList =list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardEvent;
        TextView tvEventName,tvEventFees,tvEventDate;
        ImageView ivEventImage;
        public ViewHolder(View v) {
            super(v);

            cardEvent = (CardView)v.findViewById(R.id.card_event);
            tvEventName = (TextView)v.findViewById(R.id.text_eventlist_name);
            tvEventFees = (TextView)v.findViewById(R.id.text_eventslist_reg_fees);
            tvEventDate = (TextView)v.findViewById(R.id.text_eventslist_date);
            ivEventImage = (ImageView)v.findViewById(R.id.image_event_list);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public EventsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_events_list, parent, false);
        return new EventsListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final EventsListAdapter.ViewHolder holder, final int position) {

        HashMap<String,String> dataMap = dataList.get(position);

        holder.tvEventName.setText(dataMap.get(EventsData.EVENTNAME));
        holder.tvEventFees.setText(dataMap.get(EventsData.EVENTFEES));
        holder.tvEventDate.setText(dataMap.get(EventsData.EVENTDATE));

        String imageUrl = dataMap.get(EventsData.IMAGE);
        imageUrl = imageUrl.replace("https","http");
        Picasso.with(mContext)
                .load(imageUrl)
                .placeholder(R.drawable.image_event1)
                .into(holder.ivEventImage);

        holder.cardEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsIntent = new Intent(mContext,EventDetailsActivity.class);
                detailsIntent.putExtra("POSITION",position);
                mContext.startActivity(detailsIntent);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
