package com.example.exp_5_dbms;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Connecting the UI fields to the code
        EditText etName = findViewById(R.id.et_item_name);
        EditText etPrice = findViewById(R.id.et_item_price);
        EditText etQty = findViewById(R.id.et_quantity);
        Button btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String price = etPrice.getText().toString();
            String qtyStr = etQty.getText().toString();

            if (!name.isEmpty() && !price.isEmpty() && !qtyStr.isEmpty()) {
                int qty = Integer.parseInt(qtyStr);

                // Creating the item and inserting it into the database
                InventoryItem newItem = new InventoryItem(name, price, qty);
                AppDatabase.getInstance(this).inventoryDao().insert(newItem);

                Toast.makeText(this, "Item Saved Successfully!", Toast.LENGTH_SHORT).show();
                finish(); // This goes back to the list screen
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}