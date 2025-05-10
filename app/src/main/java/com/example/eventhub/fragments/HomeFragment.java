package com.example.eventhub.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventhub.R;
import com.example.eventhub.adapters.PublicEventAdapter;
import com.example.eventhub.model.EventModel;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText searchBar;
    private ProgressBar progressBar;
    private TextView textNoEvents;

    private PublicEventAdapter adapter;
    private ArrayList<EventModel> eventList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize views
        recyclerView = view.findViewById(R.id.recyclerViewEvents);
        searchBar = view.findViewById(R.id.searchBar);
        progressBar = view.findViewById(R.id.progressBar);
        textNoEvents = view.findViewById(R.id.textNoEvents);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PublicEventAdapter(getContext(), eventList);
        recyclerView.setAdapter(adapter);

        // Load Data & Setup Search
        loadEventsFromFirebase();
        setupSearch();

        return view;
    }

    private void loadEventsFromFirebase() {
        progressBar.setVisibility(View.VISIBLE);
        textNoEvents.setVisibility(View.GONE);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("events");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    EventModel event = ds.getValue(EventModel.class);
                    if (event != null) {
                        eventList.add(event);
                    }
                }
                progressBar.setVisibility(View.GONE);
                adapter.filterList(new ArrayList<>(eventList));

                if (eventList.isEmpty()) {
                    textNoEvents.setVisibility(View.VISIBLE);
                    textNoEvents.setText("No events available.");
                } else {
                    textNoEvents.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                textNoEvents.setVisibility(View.VISIBLE);
                textNoEvents.setText("Failed to load events");
                Toast.makeText(getContext(), "Firebase error", Toast.LENGTH_SHORT).show();
                Log.e("HomeFragment", "Firebase error: " + error.getMessage());
            }
        });
    }

    private void setupSearch() {
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterEvents(s.toString());
            }
        });
    }

    private void filterEvents(String query) {
        List<EventModel> filtered = new ArrayList<>();
        for (EventModel event : eventList) {
            if (event.getTitle() != null && event.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(event);
            }
        }

        adapter.filterList(filtered);

        if (filtered.isEmpty()) {
            textNoEvents.setVisibility(View.VISIBLE);
            textNoEvents.setText("No results for \"" + query + "\"");
        } else {
            textNoEvents.setVisibility(View.GONE);
        }
    }
}
