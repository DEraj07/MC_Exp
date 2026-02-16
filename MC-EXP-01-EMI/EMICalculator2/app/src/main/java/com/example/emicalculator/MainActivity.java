package com.example.emicalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText etPrincipal, etRate, etTenure;
    Button btnCalculate;
    TextView tvResult, tvInterest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPrincipal = findViewById(R.id.etPrincipal);
        etRate = findViewById(R.id.etRate);
        etTenure = findViewById(R.id.etTenure);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);
        tvInterest = findViewById(R.id.tvInterest);

        btnCalculate.setOnClickListener(v -> calculateEMI());
    }

    private void calculateEMI() {

        String pText = etPrincipal.getText().toString().trim();
        String rText = etRate.getText().toString().trim();
        String tText = etTenure.getText().toString().trim();

        if (pText.isEmpty() || rText.isEmpty() || tText.isEmpty()) {
            tvResult.setText("Please enter all values");
            tvInterest.setText("");
            return;
        }

        double principal = Double.parseDouble(pText);
        double rate = Double.parseDouble(rText);
        int tenure = Integer.parseInt(tText);

        double monthlyRate = rate / 12 / 100;
        int months = tenure * 12;

        double emi = (principal * monthlyRate * Math.pow(1 + monthlyRate, months))
                / (Math.pow(1 + monthlyRate, months) - 1);

        double totalPayment = emi * months;
        double totalInterest = totalPayment - principal;

        tvResult.setText(
                "EMI: ₹ " +
                        String.format(Locale.getDefault(), "%.2f", emi)
        );

        tvInterest.setText(
                "Total Interest for Loan: ₹ " +
                        String.format(Locale.getDefault(), "%.2f", totalInterest)
        );
    }
}
