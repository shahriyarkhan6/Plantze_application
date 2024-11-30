package com.example.plantze_application.ui.habit_tracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class ConsumptionHabitsActivity extends AppCompatActivity {

    private Button ecoFriendlyPurchasesHabitButton;
    private Button recyclingHabitButton; // New button for Recycling Habit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption_habits);

        // Initialize buttons
        ecoFriendlyPurchasesHabitButton = findViewById(R.id.ecoFriendlyPurchasesHabitButton);
        recyclingHabitButton = findViewById(R.id.recyclingHabitButton); // Initialize Recycling Habit button

        // Set click listener for Eco-Friendly Purchases Habit button
        ecoFriendlyPurchasesHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsumptionHabitsActivity.this, EcoFriendlyPurchasesHabitActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for Recycling Habit button
        recyclingHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsumptionHabitsActivity.this, RecyclingHabitActivity.class);
                startActivity(intent);
            }
        });
    }
}
