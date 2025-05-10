package com.example.eventhub;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventhub.model.EventModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private com.example.eventhub.EventAdapter eventAdapter;
    private ArrayList<EventModel> eventList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*eventList = new ArrayList<>();
        eventAdapter = new com.example.eventhub.EventAdapter(this, eventList);
        recyclerView.setAdapter(eventAdapter);

        fetchEventsFromFirebase();*/
    }

/*
    private void fetchEventsFromFirebase() {
        FirebaseDatabase.getInstance().getReference("Events")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        eventList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            EventModel event = dataSnapshot.getValue(EventModel.class);
                            if (event != null) {
                                eventList.add(event);
                            }
                        }
                        eventAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(HomeActivity.this, "Failed to load events.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
*/
}
