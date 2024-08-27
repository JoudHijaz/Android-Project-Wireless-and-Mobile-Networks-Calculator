package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Calculator2Activity extends AppCompatActivity {

    private EditText editTextBandwidth;
    private EditText editTextSubcarrierSpacing;
    private EditText editTextOFDMSymbols;
    private EditText editTextResourceBlockDuration;
    private EditText editTextQAMBits;
    private EditText editTextParallelResourceBlocks;
    private Button buttonCalculate2;
    private TextView textViewResults2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator2);

        // Enable the ActionBar back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        editTextBandwidth = findViewById(R.id.editTextBandwidth);
        editTextSubcarrierSpacing = findViewById(R.id.editTextSubcarrierSpacing);
        editTextOFDMSymbols = findViewById(R.id.editTextOFDMSymbols);
        editTextResourceBlockDuration = findViewById(R.id.editTextResourceBlockDuration);
        editTextQAMBits = findViewById(R.id.editTextQAMBits);
        editTextParallelResourceBlocks = findViewById(R.id.editTextParallelResourceBlocks);
        buttonCalculate2 = findViewById(R.id.buttonCalculate2);
        textViewResults2 = findViewById(R.id.textViewResults2);

        buttonCalculate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResults();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle the back button press to navigate back to MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void calculateResults() {
        try {
            double bandwidth = Double.parseDouble(editTextBandwidth.getText().toString());
            double subcarrierSpacing = Double.parseDouble(editTextSubcarrierSpacing.getText().toString());
            int numberOfOFDMSymbols = Integer.parseInt(editTextOFDMSymbols.getText().toString());
            double resourceBlockDuration = Double.parseDouble(editTextResourceBlockDuration.getText().toString());
            int numberOfQAMBits = Integer.parseInt(editTextQAMBits.getText().toString());
            int numberOfParallelResourceBlocks = Integer.parseInt(editTextParallelResourceBlocks.getText().toString());

            // Calculation 1: Determine the number of bits per resource element
            int bitsPerResourceElement = (int) (Math.log(numberOfQAMBits) / Math.log(2));

            // Calculation 2: Determine the number of bits per OFDM symbol
            int numberOfResourceElements = (int) (bandwidth / subcarrierSpacing);
            int bitsPerOFDMSymbol = bitsPerResourceElement * numberOfResourceElements;

            // Calculation 3: Determine the number of bits per OFDM resource block
            int bitsPerResourceBlock = bitsPerOFDMSymbol * numberOfOFDMSymbols;

            // Calculation 4: Calculate the maximum transmission rate
            double maxTransmissionRate = (bitsPerResourceBlock / resourceBlockDuration) * numberOfParallelResourceBlocks;

            // Display the results
            String resultMessage = String.format(
                    "Results:\n" +
                            "Bits per Resource Element: %d\n" +
                            "Bits per OFDM Symbol: %d\n" +
                            "Bits per Resource Block: %d\n" +
                            "Max Transmission Rate: %.2f kbps",
                    bitsPerResourceElement, bitsPerOFDMSymbol, bitsPerResourceBlock, maxTransmissionRate
            );

            textViewResults2.setText(resultMessage);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }
}
