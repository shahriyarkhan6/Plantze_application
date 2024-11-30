package com.example.plantze_application.ui.habit_tracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class TransportationHabitsActivity extends AppCompatActivity {

    private ListView transportationHabitListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation_habits);

        // Initialize ListView
        transportationHabitListView = findViewById(R.id.transportationHabitListView);

        // Define transportation habit options
        String[] habitOptions = {
                "Walking Habit",
                "Cycling Habit"
        };

        // Set up ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                habitOptions
        );

        // Attach adapter to ListView
        transportationHabitListView.setAdapter(adapter);

        // Set click listener for ListView items
        transportationHabitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // Walking Habit
                        startActivity(new Intent(TransportationHabitsActivity.this, WalkingHabitActivity.class));
                        break;
                    case 1: // Cycling Habit
                        startActivity(new Intent(TransportationHabitsActivity.this, CyclingHabitActivity.class));
                        break;
                }
            }
        });
    }
}
