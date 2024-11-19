package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class EcoTrackerActivity extends AppCompatActivity {

    private Button foodButton;
    private Button transportationButton;
    private Button housingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecotracker);

        foodButton = findViewById(R.id.foodButton);
        transportationButton = findViewById(R.id.transportationButton);
        housingButton = findViewById(R.id.housingButton);

        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EcoTrackerActivity.this, DietActivity.class);
                startActivity(intent);
            }
        });

        transportationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EcoTrackerActivity.this, TransportationActivity.class);
                startActivity(intent);
            }
        });

        housingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EcoTrackerActivity.this, HouseTypeActivity.class);
                startActivity(intent);
            }
        });
    }
}
