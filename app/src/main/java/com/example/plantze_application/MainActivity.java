package com.example.plantze_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.plantze_application.ui.annual_footprint.AnnualFootprintActivity;
import com.example.plantze_application.ui.ecotracker.EcoTrackerActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnItemSelectedListener(item -> {
            String title = (String) item.getTitle();

            switch (title) {
                case "Eco-Tracker":
                    // Navigate to Eco-Tracker
                    Intent ecoTrackerIntent = new Intent(MainActivity.this, EcoTrackerActivity.class);
                    startActivity(ecoTrackerIntent);
                    return true;

                case "Eco-Gauge":
                    // Handle Eco-Gauge navigation
                    Log.d("MainActivity", "Eco-Gauge selected. Add functionality here.");
                    return true;

                case "Annual Footprint":
                    // Navigate to Annual Footprint
                    Intent annualFootprintIntent = new Intent(MainActivity.this, AnnualFootprintActivity.class);
                    startActivity(annualFootprintIntent);
                    return true;

                default:
                    // Handle unexpected titles
                    Log.e("MainActivity", "Unknown navigation item selected: " + title);
                    return false;
            }
        });
    }
}
