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

public class Calculator1Activity extends AppCompatActivity {

    private EditText editTextBandwidth;
    private EditText editTextQuantizerBits;
    private EditText editTextSourceEncoderRate;
    private EditText editTextChannelEncoderRate;
    private EditText editTextInterleaverBits;
    private Button buttonCalculate;
    private TextView textViewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator1);

        // Enable the ActionBar back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        editTextBandwidth = findViewById(R.id.editTextBandwidth);
        editTextQuantizerBits = findViewById(R.id.editTextQuantizerBits);
        editTextSourceEncoderRate = findViewById(R.id.editTextSourceEncoderRate);
        editTextChannelEncoderRate = findViewById(R.id.editTextChannelEncoderRate);
        editTextInterleaverBits = findViewById(R.id.editTextInterleaverBits);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        textViewResults = findViewById(R.id.textViewResults);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
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
            int quantizerBits = Integer.parseInt(editTextQuantizerBits.getText().toString());
            double sourceEncoderRate = Double.parseDouble(editTextSourceEncoderRate.getText().toString());
            double channelEncoderRate = Double.parseDouble(editTextChannelEncoderRate.getText().toString());
            int interleaverBits = Integer.parseInt(editTextInterleaverBits.getText().toString());

            // 1. Calculate the sampling frequency (Fs) in kHz
            double samplingFrequency = 2 * bandwidth;

            // 2. Find the number of quantization levels (L)
            int quantizationLevels = (int) Math.pow(2, quantizerBits);

            // 3. Determine the bit rate at the output of the source encoder in kbps
            double bitRateSourceEncoder = samplingFrequency * quantizerBits * sourceEncoderRate;

            // 4. Calculate the bit rate at the output of the channel encoder in kbps
            double bitRateChannelEncoder = bitRateSourceEncoder / channelEncoderRate;

            // 5. Calculate the bit rate at the output of the interleaver in kbps
            double bitRateInterleaver = bitRateChannelEncoder;

            // Display the results
            String resultMessage = String.format(
                    "Results:\n" +
                            "Sampling Frequency: %.2f kHz\n" +
                            "Quantization Levels: %d\n" +
                            "Bit Rate (Source Encoder): %.2f kbps\n" +
                            "Bit Rate (Channel Encoder): %.2f kbps\n" +
                            "Bit Rate (Interleaver): %.2f kbps",
                    samplingFrequency, quantizationLevels, bitRateSourceEncoder, bitRateChannelEncoder, bitRateInterleaver
            );

            textViewResults.setText(resultMessage);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }
}
