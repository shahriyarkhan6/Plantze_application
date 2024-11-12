package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class EcoTrackerActivity extends AppCompatActivity {

    private Button foodButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecotracker);

        // Initialize the food button
        foodButton = findViewById(R.id.foodButton);

        // Set the OnClickListener for the food button
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EcoTrackerActivity.this, DietActivity.class);
                startActivity(intent);
            }
        });
    }
}
