package com.example.plantze_application.ui.habit_tracking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class EnergyHabitsActivity extends AppCompatActivity {

    private ListView energyHabitListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_habits);

        // Initialize ListView
        energyHabitListView = findViewById(R.id.energyHabitListView);

        // Define energy habit options
        String[] habitOptions = {
                "Energy Conservation Habit", // Option 1
                "Renewable Energy Usage Habit" // Option 2
        };

        // Set up ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                habitOptions
        );

        // Attach adapter to ListView
        energyHabitListView.setAdapter(adapter);

        // Set click listener for ListView items
        energyHabitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("EnergyHabitsActivity", "Clicked position: " + position);
                switch (position) {
                    case 0: // Energy Conservation Habit
                        startActivity(new Intent(EnergyHabitsActivity.this, EnergyConservationHabitActivity.class));
                        break;
                    case 1: // Renewable Energy Usage Habit
                        startActivity(new Intent(EnergyHabitsActivity.this, RenewableEnergyHabitActivity.class));
                        break;
                    default:
                        Log.e("EnergyHabitsActivity", "Unexpected position: " + position);
                }
            }
        });
    }
}
