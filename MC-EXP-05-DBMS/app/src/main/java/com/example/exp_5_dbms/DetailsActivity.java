package com.example.exp_5_dbms;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {
    InventoryItem currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Receive the ID passed from MainActivity
        int itemId = getIntent().getIntExtra("ITEM_ID", -1);
        currentItem = AppDatabase.getInstance(this).inventoryDao().getItemById(itemId);

        TextView tvDetails = findViewById(R.id.tv_item_details);
        Button btnSell = findViewById(R.id.btn_sell);
        Button btnDelete = findViewById(R.id.btn_delete);

        if (currentItem != null) {
            tvDetails.setText("Item: " + currentItem.getItemName() +
                    "\nPrice: $" + currentItem.getItemPrice() +
                    "\nStock: " + currentItem.getQuantity());
        }

        // Sell Button: Decreases quantity by 1
        btnSell.setOnClickListener(v -> {
            if (currentItem != null && currentItem.getQuantity() > 0) {
                currentItem.setQuantity(currentItem.getQuantity() - 1);
                AppDatabase.getInstance(this).inventoryDao().update(currentItem);
                Toast.makeText(this, "Item Sold!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Out of stock!", Toast.LENGTH_SHORT).show();
            }
        });

        // Delete Button: Removes item from database
        btnDelete.setOnClickListener(v -> {
            if (currentItem != null) {
                AppDatabase.getInstance(this).inventoryDao().delete(currentItem);
                Toast.makeText(this, "Item Deleted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}