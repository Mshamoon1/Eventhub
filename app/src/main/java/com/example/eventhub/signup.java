package com.example.eventhub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class signup extends AppCompatActivity {
    private EditText et_name, et_email, et_password, ct_password;
    private Button sign, SignupFromLogin;
    private AuthHelper authHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        authHelper = new AuthHelper(this);

        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        ct_password = findViewById(R.id.ct_password);
        sign = findViewById(R.id.sign);
        SignupFromLogin = findViewById(R.id.btnSignupFromLogin);

        sign.setOnClickListener(v -> signupUser());
        SignupFromLogin.setOnClickListener(v -> {
            startActivity(new Intent(signup.this, log.class));
            finish();
        });
    }

    private void signupUser() {
        String name = et_name.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String confirmPassword = ct_password.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save user credentials
        authHelper.saveUserCredentials(email, password);
        Toast.makeText(this, "Signup successful! Please login", Toast.LENGTH_SHORT).show();

        // Go to login activity
        startActivity(new Intent(signup.this, log.class));
        finish();
    }
}