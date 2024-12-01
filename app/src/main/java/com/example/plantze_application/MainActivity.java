package com.example.plantze_application;

import android.content.Intent;
import android.os.Bundle;

import com.example.plantze_application.ui.annual_footprint.AnnualFootprintActivity;
import com.example.plantze_application.ui.login.LoginActivity;
import com.example.plantze_application.ui.registration.RegisterActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.plantze_application.databinding.ActivityMainBinding;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        //AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                //R.id.navigation_home, R.id.navigation_dashboard)
                //.build();


        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_main_activity);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //NavigationUI.setupWithNavController(binding.navView, navController);

        // Adding the custom item selected listener
        // Handle menu item clicks
        // Manually set click listeners for each navigation item
        navView.setOnItemSelectedListener(item -> {
            String title = (String) item.getTitle();

            if ("Eco-Tracker".equals(title)) {
                // Handle Eco-Tracker navigation
                // Add code for Eco-Tracker navigation here (if needed)
                return true;

            } else if ("Eco-Gauge".equals(title)) {
                // Handle Eco-Gauge navigation
                // Add code for Eco-Gauge navigation here (if needed)
                return true;

            } else if ("Annual Footprint".equals(title)) {
                // Manually launch AnnualFootprintActivity
                Intent intent = new Intent(MainActivity.this, AnnualFootprintActivity.class);
                startActivity(intent);
                return true;
            }

            return false; // Default behavior
        });



    }

}