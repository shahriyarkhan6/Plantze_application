package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.plantze_application.R;
import com.example.plantze_application.ui.habit_tracking.HabitTrackingActivity;

import androidx.appcompat.app.AppCompatActivity;

public class EcoTrackerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_tracker);

        // Set up Habit Tracking button
        Button habitTrackingButton = findViewById(R.id.habitTrackingButton);
        habitTrackingButton.setOnClickListener(v -> {
            Intent intent = new Intent(EcoTrackerActivity.this, HabitTrackingActivity.class);
            startActivity(intent);
        });

        // Set up Eco Tracker Calendar button
        Button ecoTrackerCalendarButton = findViewById(R.id.ecoTrackerCalendarButton);
        ecoTrackerCalendarButton.setOnClickListener(v -> {
            Intent intent = new Intent(EcoTrackerActivity.this, CalendarActivity.class);
            startActivity(intent);
        });
    }
}
