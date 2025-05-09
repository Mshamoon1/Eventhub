package com.example.eventhub.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventhub.R;
import com.example.eventhub.model.EventModel;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context context;
    private ArrayList<EventModel> eventList;

    public EventAdapter(Context context, ArrayList<EventModel> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        EventModel event = eventList.get(position);

        holder.title.setText(event.getTitle());
        holder.location.setText(event.getLocation());
        holder.count.setText("+" + event.getCount());

        // Parse and display date
        if (event.getDate() != null && event.getDate().contains("-")) {
            String[] dateParts = event.getDate().split("-");
            if (dateParts.length >= 2) {
                holder.day.setText(dateParts[0]);
                int monthIndex = Integer.parseInt(dateParts[1]);
                String monthAbbr = new DateFormatSymbols().getShortMonths()[monthIndex - 1];
                holder.month.setText(monthAbbr.toUpperCase());
            }
        }

        // Load image with Glide
        Glide.with(context)
                .load(Uri.parse(event.getImageUri()))
                .placeholder(R.drawable.dj_image)
                .into(holder.cardImage);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView title, location, count, day, month;
        ImageView cardImage;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.card_image);
            title = itemView.findViewById(R.id.eventTitle);
            location = itemView.findViewById(R.id.location);
            count = itemView.findViewById(R.id.count);
            day = itemView.findViewById(R.id.day);
            month = itemView.findViewById(R.id.month);
        }
    }
}
