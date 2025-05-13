package com.example.eventhub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventhub.model.EventModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    public interface OnEventActionListener {
        void onEdit(EventModel event);
        void onDelete(EventModel event);
    }

    private final Context context;
    private final List<EventModel> eventList;
    private final OnEventActionListener listener;

    public EventAdapter(Context context, List<EventModel> eventList, OnEventActionListener listener) {
        this.context = context;
        this.eventList = eventList;
        this.listener = listener;
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

        holder.eventTitle.setText(event.getTitle());
        holder.location.setText(event.getLocation());
        holder.count.setText("+" + event.getCount());

        String[] dateParts = event.getDate().split("/");
        if (dateParts.length == 3) {
            holder.day.setText(dateParts[0]);
            holder.month.setText(getMonthShortName(Integer.parseInt(dateParts[1])));
        } else {
            holder.day.setText("??");
            holder.month.setText("NA");
        }

        Glide.with(context).load(event.getImageUrl()).into(holder.cardImage);

        holder.editButton.setOnClickListener(v -> listener.onEdit(event));
        holder.deleteButton.setOnClickListener(v -> listener.onDelete(event));
    }

    private String getMonthShortName(int month) {
        String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        return (month >= 1 && month <= 12) ? months[month - 1] : "UNK";
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle, location, count, day, month;
        ImageView cardImage;
        MaterialButton editButton, deleteButton;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.card_image);
            eventTitle = itemView.findViewById(R.id.eventTitle);
            location = itemView.findViewById(R.id.location);
            count = itemView.findViewById(R.id.count);
            day = itemView.findViewById(R.id.day);
            month = itemView.findViewById(R.id.month);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
