package com.example.eventhub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import com.example.eventhub.databinding.ActivityLogBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends BaseActivity {

    private ActivityLogBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLogBinding.inflate(getLayoutInflater());
        setContentViewWithLoader(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        binding.btnLogin.setOnClickListener(v -> loginUser());

        binding.btnSignupFromLoginoneLogin.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            finish();
        });

        binding.fPass.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "Forgot password clicked", Toast.LENGTH_SHORT).show();
            // You can launch a password reset activity here
        });
    }

    private void loginUser() {
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check for admin credentials
        if (email.equals("admin@eventhub.com") && password.equals("admin@123")) {
            Toast.makeText(LoginActivity.this, "You logged in as admin, add event", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, AdminEventActivity.class));
            finish();
            return;
        }

        showLoader();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    hideLoader();
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}