package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;
import com.example.plantze_application.ui.habit_tracking.HabitTrackingActivity;

public class AnnualFootprintActivity extends AppCompatActivity {

    private Button foodButton;
    private Button transportationButton;
    private Button consumptionButton;
    private Button housingButton;
    private Button locationButton;
    private Button habitTrackingButton; // New Habit Tracking button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annualfootprint);

        // Initialize buttons
        foodButton = findViewById(R.id.foodButton);
        transportationButton = findViewById(R.id.transportationButton);
        consumptionButton = findViewById(R.id.consumptionButton);
        housingButton = findViewById(R.id.housingButton);
        locationButton = findViewById(R.id.locationButton);
        habitTrackingButton = findViewById(R.id.habitTrackingButton); // Initialize Habit Tracking button

        // Set click listeners for buttons
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnnualFootprintActivity.this, DietActivity.class);
                startActivity(intent);
            }
        });

        transportationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnnualFootprintActivity.this, TransportationActivity.class);
                startActivity(intent);
            }
        });

        consumptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnnualFootprintActivity.this, ConsumptionActivity.class);
                startActivity(intent);
            }
        });

        housingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnnualFootprintActivity.this, HouseTypeActivity.class);
                startActivity(intent);
            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnnualFootprintActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });

        habitTrackingButton.setOnClickListener(new View.OnClickListener() { // Handle Habit Tracking button click
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnnualFootprintActivity.this, HabitTrackingActivity.class);
                startActivity(intent);
            }
        });
    }
}
