package com.example.plantze_application.ui.habit_tracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class FoodHabitsActivity extends AppCompatActivity {

    private Button meatFreeHabitButton;
    private Button reducedFoodWasteHabitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_habits);

        // Initialize buttons
        meatFreeHabitButton = findViewById(R.id.meatFreeHabitButton);
        reducedFoodWasteHabitButton = findViewById(R.id.reducedFoodWasteHabitButton);

        // Set click listener for Meat-Free Habit button
        meatFreeHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodHabitsActivity.this, MeatFreeHabitActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for Reduced Food Waste Habit button
        reducedFoodWasteHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodHabitsActivity.this, ReducedFoodWasteHabitActivity.class);
                startActivity(intent);
            }
        });
    }
}
