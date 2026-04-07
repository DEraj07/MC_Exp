package com.example.exp_5_dbms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI setup
        listView = findViewById(R.id.inventory_list);
        FloatingActionButton fab = findViewById(R.id.fab_add);

        // Click to Add
        fab.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddActivity.class)));

        // Click to Sell/Delete (Crash fix depends on Step 2)
        listView.setOnItemClickListener((parent, view, position, id) -> {
            InventoryItem item = (InventoryItem) parent.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra("ITEM_ID", item.getId());
            startActivity(intent);
        });

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        List<InventoryItem> items = AppDatabase.getInstance(this).inventoryDao().getAllItems();
        InventoryAdapter adapter = new InventoryAdapter(items);
        listView.setAdapter(adapter);
    }

    private class InventoryAdapter extends BaseAdapter {
        List<InventoryItem> items;
        InventoryAdapter(List<InventoryItem> items) { this.items = items; }
        @Override
        public int getCount() { return items.size(); }
        @Override
        public Object getItem(int i) { return items.get(i); }
        @Override
        public long getItemId(int i) { return items.get(i).getId(); }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) view = getLayoutInflater().inflate(R.layout.list_item, viewGroup, false);
            InventoryItem item = items.get(i);

            TextView name = view.findViewById(R.id.tv_name);
            TextView price = view.findViewById(R.id.tv_price);
            TextView qty = view.findViewById(R.id.tv_qty);

            name.setText(item.getItemName());
            price.setText("$" + item.getItemPrice());
            qty.setText("Stock: " + item.getQuantity());

            return view;
        }
    }
}