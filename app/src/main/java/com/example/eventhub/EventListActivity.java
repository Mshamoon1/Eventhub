package com.example.eventhub;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventhub.model.EventModel;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<EventModel> eventList;
    private EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("All Events");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventList = new ArrayList<>();

        eventAdapter = new EventAdapter(this, eventList, new EventAdapter.OnEventActionListener() {
            @Override
            public void onEdit(EventModel event) {
                showEditDialog(event);
            }

            @Override
            public void onDelete(EventModel event) {
                FirebaseDatabase.getInstance().getReference("events")
                        .child(event.getId())
                        .removeValue()
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(EventListActivity.this, "Event deleted", Toast.LENGTH_SHORT).show();
                            Log.d("EventListActivity", "Deleted: " + event.getTitle());
                            loadEventsFromFirebase();
                        })
                        .addOnFailureListener(e -> {
                            Log.e("EventListActivity", "Delete failed: " + e.getMessage());
                            Toast.makeText(EventListActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                        });
            }
        });

        recyclerView.setAdapter(eventAdapter);

        loadEventsFromFirebase();
    }

    private void loadEventsFromFirebase() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("events");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                eventList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    EventModel event = ds.getValue(EventModel.class);
                    if (event != null) {
                        eventList.add(event);
                    }
                }
                eventAdapter.notifyDataSetChanged();
                Log.d("EventListActivity", "Loaded " + eventList.size() + " events");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(EventListActivity.this, "Failed to load events", Toast.LENGTH_SHORT).show();
                Log.e("EventListActivity", "Firebase error: " + error.getMessage());
            }
        });
    }

    private void showEditDialog(EventModel event) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_event, null);
        EditText title = view.findViewById(R.id.editEventTitle);
        EditText date = view.findViewById(R.id.editEventDate);
        EditText location = view.findViewById(R.id.editEventLocation);
        EditText count = view.findViewById(R.id.editEventCount);

        title.setText(event.getTitle());
        date.setText(event.getDate());
        location.setText(event.getLocation());
        count.setText(event.getCount());

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(true)
                .create();

        view.findViewById(R.id.btnUpdateEvent).setOnClickListener(v -> {
            event.setTitle(title.getText().toString());
            event.setDate(date.getText().toString());
            event.setLocation(location.getText().toString());
            event.setCount(count.getText().toString());

            FirebaseDatabase.getInstance().getReference("events")
                    .child(event.getId())
                    .setValue(event)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(EventListActivity.this, "Event updated", Toast.LENGTH_SHORT).show();
                        Log.d("EventListActivity", "Updated: " + event.getTitle());
                        loadEventsFromFirebase();
                        dialog.dismiss();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(EventListActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                        Log.e("EventListActivity", "Update failed: " + e.getMessage());
                    });
        });

        view.findViewById(R.id.btnCancelEdit).setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
