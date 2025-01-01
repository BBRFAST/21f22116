package com.example.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    private FirebaseAuth auth;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Get references to UI elements
        EditText emailEditText = findViewById(R.id.21f22116_et_email);
        EditText passwordEditText = findViewById(R.id.21f22116_et_password);
        Button loginButton = findViewById(R.id.21f22116_btn_login);
        Button registerButton = findViewById(R.id.21f22116_btn_register);

        // Handle login button click
        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;

                                if (userId != null) {
                                    db.collection("users").document(userId).get()
                                            .addOnSuccessListener(document -> {
                                                String role = document.getString("role");
                                                if (role != null) {
                                                    switch (role) {
                                                        case "supplier":
                                                            startActivity(new Intent(this, SupplierDashboardActivity.class));
                                                            break;
                                                        case "user":
                                                            startActivity(new Intent(this, UserDashboardActivity.class));
                                                            break;
                                                        default:
                                                            Toast.makeText(this, "Role not found. Please contact support.", Toast.LENGTH_SHORT).show();
                                                            break;
                                                    }
                                                    finish(); // Close login activity
                                                }
                                            })
                                            .addOnFailureListener(e ->
                                                    Toast.makeText(this, "Failed to fetch user data.", Toast.LENGTH_SHORT).show()
                                            );
                                }
                            } else {
                                Toast.makeText(this, "Login failed: " + (task.getException() != null ? task.getException().getMessage() : "Unknown error"), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle register button click
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
