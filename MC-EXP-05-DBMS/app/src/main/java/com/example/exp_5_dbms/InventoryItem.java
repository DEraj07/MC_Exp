package com.example.exp_5_dbms;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "inventory_table")
public class InventoryItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String itemName;
    private String itemPrice;
    private int quantity;

    // Constructor
    public InventoryItem(String itemName, String itemPrice, int quantity) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }

    // Getters - Database se data lene ke liye
    public int getId() { return id; }
    public String getItemName() { return itemName; }
    public String getItemPrice() { return itemPrice; }
    public int getQuantity() { return quantity; }

    // Setters - Data update karne ke liye (Sell/Edit logic)
    public void setId(int id) { this.id = id; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setItemPrice(String itemPrice) { this.itemPrice = itemPrice; }
    public void setQuantity(int quantity) { this.quantity = quantity; } // Fixed SELL error
}