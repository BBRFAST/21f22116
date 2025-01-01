package com.example.shopapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance(); // Firestore instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Get references to UI elements
        EditText productNameEditText = findViewById(R.id.21f22116_et_product_name);
        EditText productPriceEditText = findViewById(R.id.21f22116_et_product_price);
        EditText productQuantityEditText = findViewById(R.id.21f22116_et_product_quantity);
        Button addProductButton = findViewById(R.id.21f22116_btn_add_product);

        // Handle Add Product button click
        addProductButton.setOnClickListener(view -> {
            String name = productNameEditText.getText().toString().trim();
            String priceText = productPriceEditText.getText().toString().trim();
            String quantityText = productQuantityEditText.getText().toString().trim();

            if (!name.isEmpty() && !priceText.isEmpty() && !quantityText.isEmpty()) {
                try {
                    double price = Double.parseDouble(priceText);
                    int quantity = Integer.parseInt(quantityText);

                    Map<String, Object> product = new HashMap<>();
                    product.put("name", name);
                    product.put("price", price);
                    product.put("quantity", quantity);

                    // Add product to Firestore
                    db.collection("products").add(product)
                            .addOnSuccessListener(documentReference -> {
                                Toast.makeText(this, "Product added successfully!", Toast.LENGTH_SHORT).show();
                                finish(); // Close the activity
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(this, "Failed to add product.", Toast.LENGTH_SHORT).show();
                            });

                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid price or quantity format.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
