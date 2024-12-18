package com.example.plantze_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.plantze_application.ui.annual_footprint.AnnualFootprintActivity;
import com.example.plantze_application.ui.ecotracker.CalendarActivity;
import com.example.plantze_application.ui.home.HomeFragment;
import com.example.plantze_application.ui.login.LoginActivity;
import com.example.plantze_application.ui.registration.RegisterActivity;
import com.example.plantze_application.ui.ecotracker.EcoTrackerActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.plantze_application.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
//
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_main_activity);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Manually set click listeners for each navigation item
        navView.setOnItemSelectedListener(item -> {
            String title = (String) item.getTitle();

            if ("Eco-Tracker".equals(title)) {
                navController.navigate(R.id.navigation_home);

            } else if ("Eco-Gauge".equals(title)) {
                navController.navigate(R.id.navigation_dashboard);

            } else if ("Annual Footprint".equals(title)) {
                navController.navigate(R.id.navigation_notifications);
            }

            return true;
        });
    }
}
