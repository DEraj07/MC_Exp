package com.example.exp_10_gui_components_app;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextView tvDisplay;
    TextInputEditText etInput;
    Button btnPlus, btnMinus, btnColor, btnReset;
    float currentSize = 24f;
    int colorIndex = 0;
    int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.BLACK};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tv_display);
        etInput = findViewById(R.id.et_input);
        btnPlus = findViewById(R.id.btn_plus);
        btnMinus = findViewById(R.id.btn_minus);
        btnColor = findViewById(R.id.btn_color);
        btnReset = findViewById(R.id.btn_reset);

        // Size badhane ka logic (+)
        btnPlus.setOnClickListener(v -> {
            updateText();
            if (currentSize < 70) {
                currentSize += 5f;
                tvDisplay.setTextSize(currentSize);
            }
        });

        // Size ghatane ka logic (-)
        btnMinus.setOnClickListener(v -> {
            updateText();
            if (currentSize > 10) {
                currentSize -= 5f;
                tvDisplay.setTextSize(currentSize);
            }
        });

        // Color badalne ka logic
        btnColor.setOnClickListener(v -> {
            updateText();
            tvDisplay.setTextColor(colors[colorIndex]);
            colorIndex = (colorIndex + 1) % colors.length;
        });

        // Sab reset karne ka logic
        btnReset.setOnClickListener(v -> {
            currentSize = 24f;
            tvDisplay.setTextSize(24f);
            tvDisplay.setTextColor(Color.BLACK);
            tvDisplay.setText("Hello Jignesh!");
            etInput.setText("Hello Jignesh!");
            colorIndex = 0;
        });
    }

    private void updateText() {
        String text = etInput.getText().toString();
        if (!text.isEmpty()) {
            tvDisplay.setText(text);
        }
    }
}