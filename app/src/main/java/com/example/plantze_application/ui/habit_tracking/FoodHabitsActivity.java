package com.example.plantze_application.ui.habit_tracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class FoodHabitsActivity extends AppCompatActivity {

    private ListView foodHabitListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_habits);

        // Initialize ListView
        foodHabitListView = findViewById(R.id.foodHabitListView);

        // Define food habit options
        String[] habitOptions = {
                "Meat-Free Habit",
                "Reduced Food Waste Habit"
        };

        // Set up ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                habitOptions
        );

        // Attach adapter to ListView
        foodHabitListView.setAdapter(adapter);

        // Set click listener for ListView items
        foodHabitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // Meat-Free Habit
                        startActivity(new Intent(FoodHabitsActivity.this, MeatFreeHabitActivity.class));
                        break;
                    case 1: // Reduced Food Waste Habit
                        startActivity(new Intent(FoodHabitsActivity.this, ReducedFoodWasteHabitActivity.class));
                        break;
                }
            }
        });
    }
}
