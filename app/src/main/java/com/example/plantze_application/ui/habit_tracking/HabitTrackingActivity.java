package com.example.plantze_application.ui.habit_tracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class HabitTrackingActivity extends AppCompatActivity {

    private Button ecoFriendlyTransportationButton;
    private Button ecoFriendlyFoodButton; // New button for food habits

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracking);

        // Initialize buttons
        ecoFriendlyTransportationButton = findViewById(R.id.ecoFriendlyTransportationButton);
        ecoFriendlyFoodButton = findViewById(R.id.ecoFriendlyFoodButton); // Initialize food button

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
                Intent intent = new Intent(HabitTrackingActivity.this, FoodHabitsActivity.class); // Navigates to FoodHabitsActivity
                startActivity(intent);
            }
        });
    }
}
