package com.example.mydrawingapp;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingView = findViewById(R.id.drawing_view);
        ImageButton btnDelete = findViewById(R.id.btn_delete);
        ImageButton btnColor = findViewById(R.id.btn_color);
        ImageButton btnBrush = findViewById(R.id.btn_brush);

        // Delete Dialog
        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Clear canvas")
                    .setMessage("Are you sure you want to clear the canvas?")
                    .setPositiveButton("YES", (dialog, which) -> drawingView.clearCanvas())
                    .setNegativeButton("CANCEL", null)
                    .show();
        });

        // Brush Size Dialog
        btnBrush.setOnClickListener(v -> {
            String[] sizes = {"Thin", "Medium", "Thick"};
            float[] sizeValues = {10f, 25f, 40f};
            new AlertDialog.Builder(this)
                    .setTitle("Select Brush Size")
                    .setItems(sizes, (dialog, which) -> {
                        drawingView.setBrushSize(sizeValues[which]);
                    }).show();
        });

        // Color Settings Dialog (Pen + Background)
        btnColor.setOnClickListener(v -> {
            String[] options = {"Change Pen Color", "Change Background Color"};
            new AlertDialog.Builder(this)
                    .setTitle("Color Settings")
                    .setItems(options, (dialog, which) -> {
                        if (which == 0) showPenColorDialog();
                        else showBackgroundColorDialog();
                    }).show();
        });
    }

    private void showPenColorDialog() {
        String[] colors = {"Black", "Red", "Blue", "Green", "Orange"};
        int[] colorValues = {Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, Color.rgb(255, 165, 0)};
        new AlertDialog.Builder(this)
                .setTitle("Select Pen Color")
                .setItems(colors, (dialog, which) -> {
                    drawingView.setBrushColor(colorValues[which]);
                }).show();
    }

    private void showBackgroundColorDialog() {
        String[] colors = {"White", "Blue", "Gray", "Light Green"};
        int[] colorValues = {Color.WHITE, Color.rgb(3, 155, 229), Color.LTGRAY, Color.rgb(144, 238, 144)};
        new AlertDialog.Builder(this)
                .setTitle("Select Background")
                .setItems(colors, (dialog, which) -> {
                    drawingView.setBackgroundColor(colorValues[which]);
                }).show();
    }
}