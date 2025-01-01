package com.example.shopapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView totalPriceTextView;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Initialize Views
        recyclerView = findViewById(R.id.21f22116_recycler_view_cart);
        totalPriceTextView = findViewById(R.id.21f22116_tv_total_price);

        // Retrieve cart items from Intent
        List<Product> cartItems = getIntent().getParcelableArrayListExtra("cart_items");
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        // Set up RecyclerView
        cartAdapter = new CartAdapter(cartItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);

        // Calculate and display total price
        double totalPrice = 0.0;
        for (Product item : cartItems) {
            totalPrice += item.getPrice();
        }
        totalPriceTextView.setText("Total: OMR " + totalPrice);
    }
}
