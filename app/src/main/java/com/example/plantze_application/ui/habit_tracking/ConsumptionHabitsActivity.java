package com.example.plantze_application.ui.habit_tracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class ConsumptionHabitsActivity extends AppCompatActivity {

    private ListView consumptionHabitListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption_habits);

        // Initialize ListView
        consumptionHabitListView = findViewById(R.id.consumptionHabitListView);

        // Define consumption habit options
        String[] habitOptions = {
                "Eco-Friendly Purchases Habit",
                "Recycling Habit"
        };

        // Set up ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                habitOptions
        );

        // Attach adapter to ListView
        consumptionHabitListView.setAdapter(adapter);

        // Set click listener for ListView items
        consumptionHabitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // Eco-Friendly Purchases Habit
                        startActivity(new Intent(ConsumptionHabitsActivity.this, EcoFriendlyPurchasesHabitActivity.class));
                        break;
                    case 1: // Recycling Habit
                        startActivity(new Intent(ConsumptionHabitsActivity.this, RecyclingHabitActivity.class));
                        break;
                }
            }
        });
    }
}
