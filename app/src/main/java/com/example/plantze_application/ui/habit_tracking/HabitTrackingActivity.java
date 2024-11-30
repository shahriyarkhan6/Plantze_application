package com.example.plantze_application.ui.habit_tracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class HabitTrackingActivity extends AppCompatActivity {

    private Button ecoFriendlyTransportationButton;
    private Button ecoFriendlyFoodButton;
    private Button ecoFriendlyConsumptionButton;
    private Button ecoFriendlyEnergyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracking);

        // Initialize buttons
        ecoFriendlyTransportationButton = findViewById(R.id.ecoFriendlyTransportationButton);
        ecoFriendlyFoodButton = findViewById(R.id.ecoFriendlyFoodButton);
        ecoFriendlyConsumptionButton = findViewById(R.id.ecoFriendlyConsumptionButton);
        ecoFriendlyEnergyButton = findViewById(R.id.ecoFriendlyEnergyButton);

        // Set click listener for Eco-Friendly Transportation Habits button
        ecoFriendlyTransportationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HabitTrackingActivity.this, TransportationHabitsActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for Eco-Friendly Food Habits button
        ecoFriendlyFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HabitTrackingActivity.this, FoodHabitsActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for Eco-Friendly Consumption Habits button
        ecoFriendlyConsumptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HabitTrackingActivity.this, ConsumptionHabitsActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for Eco-Friendly Energy Habits button
        ecoFriendlyEnergyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HabitTrackingActivity.this, EnergyHabitsActivity.class);
                startActivity(intent);
            }
        });
    }
}
