package com.example.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SupplierDashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_dashboard);

        Button addProductButton = findViewById(R.id.21f22116_btn_add_product);
        addProductButton.setOnClickListener(view -> {
            Intent intent = new Intent(SupplierDashboardActivity.this, AddProductActivity.class);
            startActivity(intent);
        });
    }
}
