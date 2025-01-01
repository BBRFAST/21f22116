package com.example.shopapp;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance(); // Firestore database instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Get references to UI elements
        EditText emailEditText = findViewById(R.id.21f22116_et_email);
        EditText passwordEditText = findViewById(R.id.21f22116_et_password);
        Spinner roleSpinner = findViewById(R.id.21f22116_spinner_role);
        Button registerButton = findViewById(R.id.21f22116_btn_register);
        ProgressBar progressBar = findViewById(R.id.21f22116_progressBar); // Add ProgressBar in the layout

        // Set up the role selection spinner
        String[] roles = {"Select Role", "user", "supplier"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        // Handle register button click
        registerButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String selectedRole = roleSpinner.getSelectedItem().toString();

            // Validate inputs
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }
            if ("Select Role".equals(selectedRole)) {
                Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
                return;
            }

            // Show ProgressBar
            progressBar.setVisibility(View.VISIBLE);

            // Register user in FirebaseAuth
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String userId = (auth.getCurrentUser() != null) ? auth.getCurrentUser().getUid() : null;
                            if (userId != null) {
                                // Save user role to Firestore
                                Map<String, Object> user = new HashMap<>();
                                user.put("email", email);
                                user.put("role", selectedRole);

                                db.collection("users").document(userId).set(user)
                                        .addOnSuccessListener(aVoid -> {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                            finish(); // Go back to login screen
                                        })
                                        .addOnFailureListener(e -> {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(this, "Failed to save user data.", Toast.LENGTH_SHORT).show();
                                        });
                            }
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(this, "Registration failed: " + (task.getException() != null ? task.getException().getMessage() : "Unknown error"), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
