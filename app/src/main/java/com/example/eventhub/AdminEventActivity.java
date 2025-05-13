package com.example.eventhub;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AdminEventActivity extends AppCompatActivity {

    private static final int IMAGE_PICK_CODE = 1000;

    private ImageView imagePreview;
    private EditText etTitle, etDate, etLocation, etCount;
    private Button btnSelectImage, btnAddEvent,btnViewEvent;
    private ProgressDialog progressDialog;

    private Uri imageUri;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_admin);

        // Initialize Firebase
        databaseRef = FirebaseDatabase.getInstance().getReference("events");

        // Initialize UI components
        imagePreview = findViewById(R.id.imagePreview);
        etTitle = findViewById(R.id.etTitle);
        etDate = findViewById(R.id.etDate);
        etLocation = findViewById(R.id.etLocation);
        etCount = findViewById(R.id.etCount);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnAddEvent = findViewById(R.id.btnAddEvent);
        btnViewEvent = findViewById(R.id.btnViewEvent);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        // Date picker setup
        etDate.setFocusable(false);
        etDate.setOnClickListener(v -> showDatePicker());

        btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, IMAGE_PICK_CODE);
        });

        btnViewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminEventActivity.this, EventListActivity.class));

            }
        });

        btnAddEvent.setOnClickListener(v -> {
            if (!validateInputs()) return;
            if (imageUri != null) {
                uploadImageToCloudinary();
            } else {
                showToast("Please select an image");
            }
        });



    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AdminEventActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    etDate.setText(selectedDate);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private boolean validateInputs() {
        if (etTitle.getText().toString().trim().isEmpty()) {
            showToast("Please enter event title");
            return false;
        }
        if (etDate.getText().toString().trim().isEmpty()) {
            showToast("Please enter event date");
            return false;
        }
        if (etLocation.getText().toString().trim().isEmpty()) {
            showToast("Please enter event location");
            return false;
        }
        if (etCount.getText().toString().trim().isEmpty()) {
            showToast("Please enter participants count");
            return false;
        }
        return true;
    }

    private void uploadImageToCloudinary() {
        progressDialog.setMessage("Uploading image...");
        progressDialog.show();

        MediaManager.get().upload(imageUri)
                .callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {}

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {}

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        String imageUrl = (String) resultData.get("secure_url");
                        saveEventDataToFirebase(imageUrl);
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        progressDialog.dismiss();
                        showToast("Image upload failed: " + error.getDescription());
                        Log.e("Cloudinary", error.getDescription());
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {}
                })
                .dispatch();
    }

    private void saveEventDataToFirebase(String imageUrl) {
        String id = databaseRef.push().getKey();

        Map<String, Object> eventMap = new HashMap<>();
        eventMap.put("id", id);
        eventMap.put("title", etTitle.getText().toString().trim());
        eventMap.put("date", etDate.getText().toString().trim());
        eventMap.put("location", etLocation.getText().toString().trim());
        eventMap.put("count", etCount.getText().toString().trim());
        eventMap.put("imageUrl", imageUrl);

        databaseRef.child(id).setValue(eventMap)
                .addOnSuccessListener(aVoid -> {
                    progressDialog.dismiss();
                    showToast("Event added successfully!");
                    resetForm();
                    startActivity(new Intent(AdminEventActivity.this, EventListActivity.class));
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    showToast("Failed to add event: " + e.getMessage());
                    Log.e("Firebase", e.getMessage(), e);
                });
    }

    private void resetForm() {
        etTitle.setText("");
        etDate.setText("");
        etLocation.setText("");
        etCount.setText("");
        imagePreview.setImageResource(android.R.color.transparent);
        imageUri = null;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imagePreview.setImageURI(imageUri);
        }
    }
}
