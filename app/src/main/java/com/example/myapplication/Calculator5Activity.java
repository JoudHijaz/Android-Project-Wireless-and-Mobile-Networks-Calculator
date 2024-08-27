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

public class Calculator5Activity extends AppCompatActivity {
    private EditText editTextSlots;
    private EditText editTextArea;
    private EditText editTextSubs;
    private EditText editTextCallsPerDay;
    private EditText editTextDuration;
    private EditText editTextGOS;
    private EditText editTextSIR;
    private EditText editTextDistance;
    private EditText editTextPref;
    private EditText editTextn;
    private EditText editTextRsens;
    private EditText editTextNB;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator5);

        // Enable the ActionBar back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        editTextSlots = findViewById(R.id.editTextSlots);
        editTextArea = findViewById(R.id.editTextArea);
        editTextSubs = findViewById(R.id.editTextSubs);
        editTextCallsPerDay = findViewById(R.id.editTextCallsPerDay);
        editTextDuration = findViewById(R.id.editTextDuration);
        editTextGOS = findViewById(R.id.editTextGOS);
        editTextSIR = findViewById(R.id.editTextSIR);
        editTextDistance = findViewById(R.id.editTextDistance);
        editTextPref = findViewById(R.id.editTextPref);
        editTextn = findViewById(R.id.editTextn);
        editTextRsens = findViewById(R.id.editTextRsens);
        editTextNB = findViewById(R.id.editTextNB);
        buttonCalculate = findViewById(R.id.button);
        textViewResult = findViewById(R.id.textViewResult);

        String[] options = {"mili", "micro", "Kilo", "Mega", "Giga"};
        ArrayAdapter<String> objUnitArr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        final Spinner spinnerArea = findViewById(R.id.spinnerArea);
        spinnerArea.setAdapter(objUnitArr);
        final Spinner spinnerDistance = findViewById(R.id.spinnerDistance);
        spinnerDistance.setAdapter(objUnitArr);
        final Spinner spinnerRsens = findViewById(R.id.spinnerRsens);
        spinnerRsens.setAdapter(objUnitArr);
        String[] options2 = {"db", "unitless"};
        ArrayAdapter<String> objUnit2Arr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options2);
        final Spinner spinnerSIR = findViewById(R.id.spinnerSIR);
        spinnerSIR.setAdapter(objUnit2Arr);
        final Spinner spinnerPref = findViewById(R.id.spinnerPref);
        spinnerPref.setAdapter(objUnit2Arr);
        String[] options3 = {"seconds", "minutes", "hours"};
        ArrayAdapter<String> objUnit3Arr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options3);
        final Spinner spinnerDuration = findViewById(R.id.spinnerDuration);
        spinnerDuration.setAdapter(objUnit3Arr);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResults();
            }
        });
    }

    private Double getConvertedValue(EditText editText, String selectedItem) {
        String text = editText.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return null;
        }
        try {
            double value = Double.parseDouble(text);
            switch (selectedItem) {
                case "db":
                    return dBToUnitlessConverter(value);
                case "mili":
                    return value * 1e-3;
                case "micro":
                    return value * 1e-6;
                case "Kilo":
                    return value * 1e3;
                case "Mega":
                    return value * 1e6;
                case "Giga":
                    return value * 1e9;
                case "seconds":
                    return value; // No conversion needed
                case "minutes":
                    return value * 60;
                case "hours":
                    return value * 3600;
                default:
                    return value; // No conversion needed
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input format", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public static double dBToUnitlessConverter(double dBValue) {
        return Math.pow(10, dBValue / 10);
    }

    private void calculateResults() {
        try {
            Double Slots = Double.parseDouble(editTextSlots.getText().toString());

            Double Area = getConvertedValue(editTextArea, ((Spinner) findViewById(R.id.spinnerArea)).getSelectedItem().toString());
            Double Subs = Double.parseDouble(editTextSubs.getText().toString());
            Double CallsPerDay = Double.parseDouble(editTextCallsPerDay.getText().toString());
            Double Duration = getConvertedValue(editTextDuration, ((Spinner) findViewById(R.id.spinnerDuration)).getSelectedItem().toString());
            Double GOS = Double.parseDouble(editTextGOS.getText().toString());
            Double SIR = getConvertedValue(editTextSIR, ((Spinner) findViewById(R.id.spinnerSIR)).getSelectedItem().toString());
            Double Distance = getConvertedValue(editTextDistance, ((Spinner) findViewById(R.id.spinnerDistance)).getSelectedItem().toString());
            Double Pref = getConvertedValue(editTextPref, ((Spinner) findViewById(R.id.spinnerPref)).getSelectedItem().toString());
            Double n = Double.parseDouble(editTextn.getText().toString());
            Double Rsens = getConvertedValue(editTextRsens, ((Spinner) findViewById(R.id.spinnerRsens)).getSelectedItem().toString());
            Double NB = Double.parseDouble(editTextNB.getText().toString());

            if (Area == null || Duration == null || SIR == null || Distance == null || Pref == null || Rsens == null) {
                Toast.makeText(this, "Please ensure all values are entered correctly", Toast.LENGTH_SHORT).show();
                return;
            }

            // Maximum distance between transmitter and receiver for reliable communication
            double maxDistance = Distance / Math.pow((Rsens/Pref), (1.0/n));

            // Maximum cell size assuming hexagonal cells
            double cellSize = (3 * Math.sqrt(3) / 2) * Math.pow(maxDistance, 2);

            // The number of cells in the service area
            double numCells = Area / cellSize;

            // Traffic load in the whole cellular system in Erlangs
            double totalTraffic = (Subs * CallsPerDay * Duration) / (24 * 60 * 60);

            // Traffic load in each cell in Erlangs
            double trafficLoadEachCell = totalTraffic / numCells;

            // Find the number of channels per cell from the Erlang B table
            int channelsPerCell = ErlangBTable.getChannels(GOS, trafficLoadEachCell);

            // Number of carriers per cell
            int carriersPerCell = (int) Math.ceil((double) channelsPerCell / Slots);

            // Calculate N (reuse factor) and find i and j
            double N = Math.pow((NB * SIR), 2.0 / n) / 3.0;
            int roundedN = (int) Math.ceil(N);
            double[] ijValues = findIJValues(roundedN);
            double numCellsPerCluster = ijValues[0] * ijValues[0] + ijValues[0] * ijValues[1] + ijValues[1] * ijValues[1];

            // Number of carriers in the whole system
            double totalCarriers = carriersPerCell * numCellsPerCluster;

            // Display the result
            String resultMessage = String.format(
                    "   Results:\n" +
                            "   Maximum Distance: %.2f meters\n" +
                            "   Maximum Cell Size: %.2f square meters\n" +
                            "   Number of Cells: %.2f\n" +
                            "   Traffic Load (Total): %.2f Erlangs\n" +
                            "   Traffic Load (Per Cell): %.2f Erlangs\n" +
                            "   Channels Per Cell: %d\n" +
                            "   Carriers Per Cell: %d\n" +
                            "   Number of Cells Per Cluster: %.2f (using i=%d, j=%d)\n" +
                            "   Total Carriers: %.2f",
                    maxDistance, cellSize, numCells, totalTraffic, trafficLoadEachCell, channelsPerCell, carriersPerCell, numCellsPerCluster, (int) ijValues[0], (int) ijValues[1], totalCarriers
            );

            textViewResult.setText(resultMessage);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }

    private double[] findIJValues(int N) {
        for (int n = N; ; n++) {
            for (int i = 0; i <= Math.sqrt(n); i++) {
                for (int j = 0; j <= Math.sqrt(n); j++) {
                    if (i * i + i * j + j * j == n) {
                        return new double[]{i, j};
                    }
                }
            }
        }
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
}
