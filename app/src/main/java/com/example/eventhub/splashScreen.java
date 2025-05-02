package com.example.eventhub;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler; // Correct import
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class splashScreen extends AppCompatActivity {
    private AuthHelper authHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authHelper = new AuthHelper(this);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        // Use android.os.Handler instead of java.util.logging.Handler
        new Handler().postDelayed(() -> {
            if (authHelper.isLoggedIn()) {
                // User is logged in, go to main activity
                startActivity(new Intent(splashScreen.this, log.class));
            } else {
                // User is not logged in, go to signup first
                startActivity(new Intent(splashScreen.this, signup.class));
            }
            finish();
        }, 3000); // 3 second delay
    }
}