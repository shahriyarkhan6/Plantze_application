package com.example.plantze_application.ui.habit_tracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class EnergyHabitsActivity extends AppCompatActivity {

    private Button energyConservationHabitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_habits);

        // Initialize button
        energyConservationHabitButton = findViewById(R.id.energyConservationHabitButton);

        // Set click listener for Energy Conservation Habit button
        energyConservationHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnergyHabitsActivity.this, EnergyConservationHabitActivity.class);
                startActivity(intent);
            }
        });
    }
}
