package com.lifejodi.event.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lifejodi.event.activity.EventDetailsActivity;
import com.lifejodi.R;

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
        TextView tvFees;
        public ViewHolder(View v) {
            super(v);

            cardEvent = (CardView)v.findViewById(R.id.card_event);
            tvFees = (TextView)v.findViewById(R.id.text_events_reg_fees);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public EventsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_events_list, parent, false);
        return new EventsListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final EventsListAdapter.ViewHolder holder, final int position) {

        if(position==0)
        {
            holder.tvFees.setVisibility(View.GONE);

        }else {
            holder.tvFees.setVisibility(View.VISIBLE);
            holder.tvFees.setText("$100");
        }
        holder.cardEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,EventDetailsActivity.class));
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
