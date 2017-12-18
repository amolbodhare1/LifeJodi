package com.lifejodi.event.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        public ViewHolder(View v) {
            super(v);

            cardEvent = (CardView)v.findViewById(R.id.card_event);
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

        holder.cardEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,EventRegistrationActivity.class));
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
