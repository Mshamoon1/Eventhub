package com.example.eventhub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AdminEventActivity extends AppCompatActivity {

    ImageView imagePreview;
    Button btnSelectImage, btnUpdate, btnDelete;
    EditText etTitle, etDate, etLocation, etCount;

    Uri selectedImageUri;
    static final int IMAGE_PICK_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_admin);

        imagePreview = findViewById(R.id.imagePreview);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        etTitle = findViewById(R.id.etTitle);
        etDate = findViewById(R.id.etDate);
        etLocation = findViewById(R.id.etLocation);
        etCount = findViewById(R.id.etCount);

        btnSelectImage.setOnClickListener(v -> pickImage());

        btnUpdate.setOnClickListener(v -> {
            // Logic to insert/update event in database
            String title = etTitle.getText().toString();
            String date = etDate.getText().toString();
            String location = etLocation.getText().toString();
            String count = etCount.getText().toString();

            if (!title.isEmpty() && selectedImageUri != null) {
                // Save event logic here (e.g. in Room DB or pass via Intent)
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("date", date);
                intent.putExtra("location", location);
                intent.putExtra("count", count);
                intent.putExtra("imageUri", selectedImageUri.toString());
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            // Add delete logic (e.g., delete from DB)
            Toast.makeText(this, "Event deleted (simulate)", Toast.LENGTH_SHORT).show();
        });
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            selectedImageUri = data.getData();
            imagePreview.setImageURI(selectedImageUri);
        }
    }
}

