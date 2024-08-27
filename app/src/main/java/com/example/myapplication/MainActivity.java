// src/main/java/com/yourpackage/MainActivity.java
package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonCalculator1 = findViewById(R.id.button_calculator1);
        Button buttonCalculator2 = findViewById(R.id.button_calculator2);
        Button buttonCalculator3 = findViewById(R.id.button_calculator3);
        Button buttonCalculator4 = findViewById(R.id.button_calculator4);
        Button buttonCalculator5 = findViewById(R.id.button_calculator5);

        buttonCalculator1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Calculator1Activity.class);
                startActivity(intent);
            }
        });

        buttonCalculator2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Calculator2Activity.class);
                startActivity(intent);
            }
        });

        buttonCalculator3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Calculator3Activity.class);
                startActivity(intent);
            }
        });

        buttonCalculator4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Calculator4Activity.class);
                startActivity(intent);
            }
        });

        buttonCalculator5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Calculator5Activity.class);
                startActivity(intent);
            }
        });
    }
}
