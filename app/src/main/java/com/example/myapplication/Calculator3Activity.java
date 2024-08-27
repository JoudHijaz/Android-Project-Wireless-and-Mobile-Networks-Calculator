package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class Calculator3Activity extends AppCompatActivity {
    private EditText editTextLP;
    private EditText editTextFreq;
    private EditText editTextGt;
    private EditText editTextGr;
    private EditText editTextR;
    private EditText editTextLf;
    private EditText editTextOther;
    private EditText editTextLfMargin;
    private EditText editTextAr;
    private EditText editTextNf;
    private EditText editTextTemp;
    private EditText editTextM;
    private EditText editTextBER;
    private EditText editTextAt;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator3);

        // Enable the ActionBar back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        editTextLP = findViewById(R.id.editTextLP);
        editTextFreq = findViewById(R.id.editTextFreq);
        editTextGt = findViewById(R.id.editTextGt);
        editTextGr = findViewById(R.id.editTextGr);
        editTextR = findViewById(R.id.editTextR);
        editTextLf = findViewById(R.id.editTextLf);
        editTextOther = findViewById(R.id.editTextOther);
        editTextLfMargin = findViewById(R.id.editTextLfMargin);
        editTextAr = findViewById(R.id.editTextAr);
        editTextNf = findViewById(R.id.editTextNf);
        editTextTemp = findViewById(R.id.editTextTemp);
        editTextM = findViewById(R.id.editTextM);
        editTextBER = findViewById(R.id.editTextBER);
        editTextAt = findViewById(R.id.editTextAt);
        buttonCalculate = findViewById(R.id.button);
        textViewResult = findViewById(R.id.textViewResult);

        String[] options = {"db", "unitless"};
        ArrayAdapter<String> objUnitArr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        final Spinner spinnerLP = findViewById(R.id.spinnerLP);
        spinnerLP.setAdapter(objUnitArr);
        final Spinner spinnerFreq = findViewById(R.id.spinnerFreq);
        spinnerFreq.setAdapter(objUnitArr);
        final Spinner spinnerGt = findViewById(R.id.spinnerGt);
        spinnerGt.setAdapter(objUnitArr);
        final Spinner spinnerGr = findViewById(R.id.spinnerGr);
        spinnerGr.setAdapter(objUnitArr);
        final Spinner spinnerR = findViewById(R.id.spinnerR);
        spinnerR.setAdapter(objUnitArr);
        final Spinner spinnerLf = findViewById(R.id.spinnerLf);
        spinnerLf.setAdapter(objUnitArr);
        final Spinner spinnerOther = findViewById(R.id.spinnerOther);
        spinnerOther.setAdapter(objUnitArr);
        final Spinner spinnerLfMargin = findViewById(R.id.spinnerLfMargin);
        spinnerLfMargin.setAdapter(objUnitArr);
        final Spinner spinnerAr = findViewById(R.id.spinnerAr);
        spinnerAr.setAdapter(objUnitArr);
        final Spinner spinnerAt = findViewById(R.id.spinnerAt);
        spinnerAt.setAdapter(objUnitArr);
        final Spinner spinnerNf = findViewById(R.id.spinnerNf);
        spinnerNf.setAdapter(objUnitArr);
        final Spinner spinnerTemp = findViewById(R.id.spinnerTemp);
        spinnerTemp.setAdapter(objUnitArr);
        final Spinner spinnerM = findViewById(R.id.spinnerM);
        spinnerM.setAdapter(objUnitArr);
        String[] options2 = {"BPSK/QPSK", "8PSK", "16PSK"};
        ArrayAdapter<String> objModulationArr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options2);
        final Spinner spinnerModulation = findViewById(R.id.spinnerModulation);
        spinnerModulation.setAdapter(objModulationArr);

        // DO MAPPING FOR MOD TECHNIQUES
        // BER to Eb/No mapping for BPSK/QPSK
        Map<Double, Double> bpskQpskMap = new HashMap<>();
        bpskQpskMap.put(1.0e-0, 0.0);
        bpskQpskMap.put(1.0e-1, 0.0);
        bpskQpskMap.put(1.0e-2, 4.0);
        bpskQpskMap.put(1.0e-3, 7.0);
        bpskQpskMap.put(1.0e-4, 8.0);
        bpskQpskMap.put(1.0e-5, 9.7);
        bpskQpskMap.put(1.0e-6, 10.7);
        bpskQpskMap.put(1.0e-7, 11.5);
        bpskQpskMap.put(1.0e-8, 12.0);

        // BER to Eb/No mapping for 8-PSK
        Map<Double, Double> eightPskMap = new HashMap<>();
        eightPskMap.put(1.0e-0, 0.0);
        eightPskMap.put(1.0e-1, 0.0);
        eightPskMap.put(1.0e-2, 7.0);
        eightPskMap.put(1.0e-3, 10.0);
        eightPskMap.put(1.0e-4, 12.0);
        eightPskMap.put(1.0e-5, 13.0);
        eightPskMap.put(1.0e-6, 14.0);
        eightPskMap.put(1.0e-7, 14.5);
        eightPskMap.put(1.0e-8, 15.0);

        // BER to Eb/No mapping for 16-PSK
        Map<Double, Double> sixteenPskMap = new HashMap<>();
        sixteenPskMap.put(1.0e-0, 0.0);
        sixteenPskMap.put(1.0e-1, 4.0);
        sixteenPskMap.put(1.0e-2, 11.0);
        sixteenPskMap.put(1.0e-3, 14.5);
        sixteenPskMap.put(1.0e-4, 16.0);
        sixteenPskMap.put(1.0e-5, 17.5);
        sixteenPskMap.put(1.0e-6, 18.5);
        sixteenPskMap.put(1.0e-7, 19.0);
        sixteenPskMap.put(1.0e-8, 20.0);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResults(bpskQpskMap, eightPskMap, sixteenPskMap);
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
            if (selectedItem.equals("unitless")) {
                return unitlessToDbConverter(value);
            } else {
                return value;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input format", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private static double unitlessToDbConverter(double value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Value must be greater than 0");
        }
        return 10 * Math.log10(value);
    }

    private void calculateResults(Map<Double, Double> bpskQpskMap, Map<Double, Double> eightPskMap, Map<Double, Double> sixteenPskMap) {
        String modulationType = ((Spinner) findViewById(R.id.spinnerModulation)).getSelectedItem().toString();
        String berText = editTextBER.getText().toString();
        if (berText.isEmpty()) {
            Toast.makeText(this, "Please enter BER value", Toast.LENGTH_SHORT).show();
            return;
        }

        double ber;
        try {
            ber = Double.parseDouble(berText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid BER value", Toast.LENGTH_SHORT).show();
            return;
        }

        Double M = getConvertedValue(editTextM, ((Spinner) findViewById(R.id.spinnerM)).getSelectedItem().toString());
        Double Temp = getConvertedValue(editTextTemp, ((Spinner) findViewById(R.id.spinnerTemp)).getSelectedItem().toString());
        Double Nf = getConvertedValue(editTextNf, ((Spinner) findViewById(R.id.spinnerNf)).getSelectedItem().toString());
        Double Rate = getConvertedValue(editTextR, ((Spinner) findViewById(R.id.spinnerR)).getSelectedItem().toString());
        Double LP = getConvertedValue(editTextLP, ((Spinner) findViewById(R.id.spinnerLP)).getSelectedItem().toString());
        Double LfMargin = getConvertedValue(editTextLfMargin, ((Spinner) findViewById(R.id.spinnerLfMargin)).getSelectedItem().toString());
        Double Lf = getConvertedValue(editTextLf, ((Spinner) findViewById(R.id.spinnerLf)).getSelectedItem().toString());
        Double Other = getConvertedValue(editTextOther, ((Spinner) findViewById(R.id.spinnerOther)).getSelectedItem().toString());
        Double Gt = getConvertedValue(editTextGt, ((Spinner) findViewById(R.id.spinnerGt)).getSelectedItem().toString());
        Double Gr = getConvertedValue(editTextGr, ((Spinner) findViewById(R.id.spinnerGr)).getSelectedItem().toString());
        Double Ar = getConvertedValue(editTextAr, ((Spinner) findViewById(R.id.spinnerAr)).getSelectedItem().toString());
        Double At = getConvertedValue(editTextAt, ((Spinner) findViewById(R.id.spinnerAt)).getSelectedItem().toString());
        if (M == null || Temp == null || Nf == null || Rate == null || LP == null || LfMargin == null || Lf == null || Other == null || Gt == null || Gr == null || Ar == null || At == null) {
            Toast.makeText(this, "Please ensure all values are entered", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<Double, Double> selectedMap = null;
        switch (modulationType) {
            case "BPSK/QPSK":
                selectedMap = bpskQpskMap;
                break;
            case "8PSK":
                selectedMap = eightPskMap;
                break;
            case "16PSK":
                selectedMap = sixteenPskMap;
                break;
        }

        if (selectedMap != null) {
            double EbOverNo = findClosestEbNo(ber, selectedMap);
            double powerR = M - 228.6 + Temp + Nf + Rate + EbOverNo;
            double powerT = powerR + LP + LfMargin + Lf + Other - Gt - Gr - Ar - At;
            textViewResult.setText(String.format(" Power Transmitted in dB: %.2f dB\n" +
                    " Power Transmitted in dBm: %.2f dBm\n" +
                    " Power Transmitted (unitless): %.2f ", powerT, (powerT+30.0), Math.pow(10.0, (powerT/10.0))));
        } else {
            textViewResult.setText("Invalid modulation type selected.");
        }
    }

    private Double findClosestEbNo(double ber, Map<Double, Double> map) {
        Double closestKey = null;
        double minDifference = Double.MAX_VALUE;
        for (Map.Entry<Double, Double> entry : map.entrySet()) {
            double difference = Math.abs(entry.getKey() - ber);
            if (difference < minDifference) {
                minDifference = difference;
                closestKey = entry.getKey();
            }
        }
        return map.get(closestKey);
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
