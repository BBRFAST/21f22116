package com.example.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class UserDashboardActivity extends AppCompatActivity {

    private final List<Product> cartItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        // Example: Adding sample data to cart (remove this after actual implementation)
        cartItems.add(new Product("1", "Phone", 10.0, 1));
        cartItems.add(new Product("2", "Powerbank", 8.0, 1));
    }

    public void goToCart(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        intent.putParcelableArrayListExtra("cart_items", new ArrayList<>(cartItems));
        startActivity(intent);
    }
}
