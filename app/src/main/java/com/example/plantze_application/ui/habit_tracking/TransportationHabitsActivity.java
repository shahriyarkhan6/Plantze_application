package com.example.plantze_application.ui.habit_tracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class TransportationHabitsActivity extends AppCompatActivity {

    private Button walkingHabitButton;
    private Button cyclingHabitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation_habits);

        // Initialize buttons
        walkingHabitButton = findViewById(R.id.walkingHabitButton);
        cyclingHabitButton = findViewById(R.id.cyclingHabitButton);

        // Set click listener for Walking Habit button
        walkingHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransportationHabitsActivity.this, WalkingHabitActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for Cycling Habit button
        cyclingHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransportationHabitsActivity.this, CyclingHabitActivity.class);
                startActivity(intent);
            }
        });
    }
}
