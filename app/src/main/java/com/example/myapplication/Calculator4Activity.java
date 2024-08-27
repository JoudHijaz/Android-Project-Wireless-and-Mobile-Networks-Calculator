package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Calculator4Activity extends AppCompatActivity {

    private EditText editTextDataTransmissionBandwidth;
    private EditText editTextPropagationDelay;
    private EditText editTextFrameSize;
    private EditText editTextFrameRate;
    private Spinner spinnerProtocolType;
    private Button buttonCalculate4;
    private TextView textViewResults4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator4);

        // Enable the ActionBar back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        editTextDataTransmissionBandwidth = findViewById(R.id.editTextDataTransmissionBandwidth);
        editTextPropagationDelay = findViewById(R.id.editTextPropagationDelay);
        editTextFrameSize = findViewById(R.id.editTextFrameSize);
        editTextFrameRate = findViewById(R.id.editTextFrameRate);
        spinnerProtocolType = findViewById(R.id.spinnerProtocolType);
        buttonCalculate4 = findViewById(R.id.buttonCalculate4);
        textViewResults4 = findViewById(R.id.textViewResults4);

        // Set up the spinner with protocol types
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.protocol_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProtocolType.setAdapter(adapter);

        buttonCalculate4.setOnClickListener(new View.OnClickListener() {
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
            double dataTransmissionBandwidth = Double.parseDouble(editTextDataTransmissionBandwidth.getText().toString());
            double propagationDelay = Double.parseDouble(editTextPropagationDelay.getText().toString());
            double frameSize = Double.parseDouble(editTextFrameSize.getText().toString());
            double frameRate = Double.parseDouble(editTextFrameRate.getText().toString());
            String protocolType = spinnerProtocolType.getSelectedItem().toString();

            // Convert to appropriate units
            double dataTransmissionRate = dataTransmissionBandwidth * 1000000; // Convert Mbps to bps
            double Tb = (frameSize*1000) / dataTransmissionRate; // Time for one bit
            double Tframe = frameSize * 1000 * Tb; // Time for one frame in seconds

            // G = g * T
            double G = (frameRate*1000) * Tb; // Load factor

            // α = τ / Tframe
            double alpha = (propagationDelay * Math.pow(10,-6)) / Tb; // Propagation delay in seconds divided by Tframe

            // Throughput calculation
            double throughput = 0;
            if (protocolType.equals("Unslotted Nonpersistent CSMA")) {
                throughput = (G * Math.exp(-2 * alpha * Tb)) / ((G * (1 + 2 * alpha)) + Math.exp(-alpha * G));
            } else if (protocolType.equals("Unslotted 1-persistent CSMA")) {
                throughput = (G * (1 + G + alpha * G * (1 + G / 2)) * Math.exp(-G * (1 + 2 * alpha))) /
                        (G * (1 + 2 * alpha) - (1 - Math.exp(-alpha * G)) + (1 + alpha * G) * Math.exp(-G * (1 + alpha)));
            } else if (protocolType.equals("Slotted 1-persistent CSMA")) {
                throughput = (G * (1 + alpha - Math.exp(-alpha * G)) * Math.exp(-G * (1 + alpha))) /
                        ((1 + alpha) * (1 - Math.exp(-alpha * G)) + alpha * Math.exp(-G * (1 + alpha)));
            } else if (protocolType.equals("Slotted Nonpersistent CSMA")){
                throughput = (alpha * G * Math.exp(-2 * alpha * Tb)) / ( 1 - Math.exp(-alpha * G) + alpha);
            } else if (protocolType.equals("Pure ALOHA")){
                throughput = frameRate * Tb * Math.exp(-2 * frameRate * Tb);
            } else if (protocolType.equals("Slotted ALOHA")){
                throughput = frameRate * Tb * Math.exp(-1 * frameRate * Tb);
            } else {
                //
            }

            // Convert to percentage
            double throughputPercentage = throughput * 100;

            // Display the results
            String resultMessage = String.format(
                    "Results:\n" +
                            "Protocol Type: %s\n" +
                            "Throughput: %.2f%%",
                    protocolType,throughputPercentage);

            textViewResults4.setText(resultMessage);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }
}
