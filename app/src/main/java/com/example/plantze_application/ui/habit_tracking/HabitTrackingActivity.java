package com.example.plantze_application.ui.habit_tracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class HabitTrackingActivity extends AppCompatActivity {

    private ListView habitListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracking);

        // Initialize ListView
        habitListView = findViewById(R.id.habitListView);

        // Define habit options
        String[] habitOptions = {
                "Eco-Friendly Transportation Habits",
                "Eco-Friendly Food Habits",
                "Eco-Friendly Consumption Habits",
                "Eco-Friendly Energy Habits"
        };

        // Set up ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                habitOptions
        );

        // Attach adapter to ListView
        habitListView.setAdapter(adapter);

        // Set click listener for ListView items
        habitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // Eco-Friendly Transportation Habits
                        startActivity(new Intent(HabitTrackingActivity.this, TransportationHabitsActivity.class));
                        break;
                    case 1: // Eco-Friendly Food Habits
                        startActivity(new Intent(HabitTrackingActivity.this, FoodHabitsActivity.class));
                        break;
                    case 2: // Eco-Friendly Consumption Habits
                        startActivity(new Intent(HabitTrackingActivity.this, ConsumptionHabitsActivity.class));
                        break;
                    case 3: // Eco-Friendly Energy Habits
                        startActivity(new Intent(HabitTrackingActivity.this, EnergyHabitsActivity.class));
                        break;
                }
            }
        });
    }
}
