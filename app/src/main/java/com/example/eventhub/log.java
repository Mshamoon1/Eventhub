package com.example.eventhub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class log extends AppCompatActivity {
    private EditText et_email, et_password;
    private Button btn_login, btnSignupFromLogin;
    private TextView fPass;
    private AuthHelper authHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log); // Make sure this matches your XML file name

        authHelper = new AuthHelper(this);

        // Initialize views
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btnSignupFromLogin = findViewById(R.id.btnSignupFromLogin);
        fPass = findViewById(R.id.fPass);

        // Login button click listener
        btn_login.setOnClickListener(v -> loginUser());

        // Signup button click listener
        btnSignupFromLogin.setOnClickListener(v -> {
            startActivity(new Intent(log.this, signup.class));
            finish();
        });

        // Forgot password click listener
        fPass.setOnClickListener(v -> {
            // Add your forgot password logic here
            Toast.makeText(log.this, "Forgot password clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private void loginUser() {
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (authHelper.validateLogin(email, password)) {
            authHelper.setLoggedIn(true);
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

            // Redirect to main activity after successful login
            startActivity(new Intent(log.this, HomeActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }
}