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
import com.lifejodi.event.activity.EventRegistrationActivity;
import com.lifejodi.R;
import com.lifejodi.event.activity.EventsActivity;

/**
 * Created by Ajay on 14-11-2017.
 */

public class AdapterEventsList extends RecyclerView.Adapter<AdapterEventsList.ViewHolder> {

    Context mContext;
    public AdapterEventsList(EventsActivity eventsActivity) {

        this.mContext = eventsActivity;
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
    public AdapterEventsList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_events_list, parent, false);
        return new AdapterEventsList.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AdapterEventsList.ViewHolder holder, final int position) {

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
