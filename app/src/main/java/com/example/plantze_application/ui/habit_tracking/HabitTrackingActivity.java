package com.example.plantze_application.ui.habit_tracking;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HabitTrackingActivity extends AppCompatActivity {

    private EditText searchBar;
    private ExpandableListView expandableListView;
    private HabitExpandableListAdapter adapter;

    private List<String> categories;
    private Map<String, List<String>> habitsByCategory;

    private Map<String, Class<?>> habitActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracking);

        // Initialize UI components
        searchBar = findViewById(R.id.searchBar);
        expandableListView = findViewById(R.id.habitExpandableListView);

        // Initialize categories and habits
        initializeHabits();

        // Set up adapter
        adapter = new HabitExpandableListAdapter(this, categories, habitsByCategory);
        expandableListView.setAdapter(adapter);

        // Add real-time filtering with automatic navigation
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString().toLowerCase().trim();
                Log.d("SearchQuery", "Query: " + query); // Debugging to ensure query is captured
                handleSearchQuery(query);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Set child click listener to navigate to respective activities
        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            String selectedHabit = habitsByCategory.get(categories.get(groupPosition)).get(childPosition);
            if (selectedHabit != null && habitActivities.containsKey(selectedHabit)) {
                Intent intent = new Intent(HabitTrackingActivity.this, habitActivities.get(selectedHabit));
                startActivity(intent);
            }
            return true;
        });
    }

    private void initializeHabits() {
        // Define categories
        categories = new ArrayList<>();
        categories.add("Transportation");
        categories.add("Food");
        categories.add("Energy");
        categories.add("Consumption");

        // Define habits by category
        habitsByCategory = new HashMap<>();
        habitsByCategory.put("Transportation", new ArrayList<>());
        habitsByCategory.put("Food", new ArrayList<>());
        habitsByCategory.put("Energy", new ArrayList<>());
        habitsByCategory.put("Consumption", new ArrayList<>());

        // Populate habits
        habitsByCategory.get("Transportation").add("Walking Habit");
        habitsByCategory.get("Transportation").add("Biking Habit"); // Renamed from "Cycling Habit"
        habitsByCategory.get("Food").add("Meat-Free Habit");
        habitsByCategory.get("Food").add("Reduced Food Waste Habit");
        habitsByCategory.get("Energy").add("Energy Conservation Habit");
        habitsByCategory.get("Energy").add("Renewable Energy Usage Habit");
        habitsByCategory.get("Consumption").add("Waste Recycling Habit"); // Renamed from "Recycling Habit"
        habitsByCategory.get("Consumption").add("Eco-Friendly Purchases Habit");

        // Map habit names to activity classes
        habitActivities = new HashMap<>();
        habitActivities.put("Walking Habit", WalkingHabitActivity.class);
        habitActivities.put("Biking Habit", CyclingHabitActivity.class);
        habitActivities.put("Meat-Free Habit", MeatFreeHabitActivity.class);
        habitActivities.put("Reduced Food Waste Habit", ReducedFoodWasteHabitActivity.class);
        habitActivities.put("Energy Conservation Habit", EnergyConservationHabitActivity.class);
        habitActivities.put("Renewable Energy Usage Habit", RenewableEnergyHabitActivity.class);
        habitActivities.put("Waste Recycling Habit", RecyclingHabitActivity.class);
        habitActivities.put("Eco-Friendly Purchases Habit", EcoFriendlyPurchasesHabitActivity.class);
    }

    private void handleSearchQuery(String query) {
        if (query.isEmpty()) {
            // If the query is empty, notify the user
            Toast.makeText(this, "Please enter a habit to search for.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> matchedHabits = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : habitsByCategory.entrySet()) {
            for (String habit : entry.getValue()) {
                if (habit.toLowerCase().contains(query)) {
                    matchedHabits.add(habit);
                }
            }
        }

        if (matchedHabits.size() == 1) {
            // Single match found, navigate to habit
            String matchedHabit = matchedHabits.get(0);
            if (habitActivities.containsKey(matchedHabit)) {
                Log.d("Navigation", "Navigating to: " + matchedHabit); // Debugging navigation
                Intent intent = new Intent(HabitTrackingActivity.this, habitActivities.get(matchedHabit));
                startActivity(intent);
            }
        } else if (matchedHabits.isEmpty()) {
            // No matches, notify user
            Toast.makeText(this, "No habit matches the search query.", Toast.LENGTH_SHORT).show();
        } else {
            // Multiple matches found, show a message
            Toast.makeText(this, "Multiple habits match the query. Please refine your search.", Toast.LENGTH_SHORT).show();
        }
    }
}
