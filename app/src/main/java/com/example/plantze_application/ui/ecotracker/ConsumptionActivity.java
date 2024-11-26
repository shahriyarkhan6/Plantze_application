package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class ConsumptionActivity extends AppCompatActivity {
    private Button mealButton;
    private Button clothesButton;
    private Button electronicsButton;
    private Button billsButton;
    private Button othersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_consumption);

        // Initialize buttons
        mealButton = findViewById(R.id.mealButton);
        clothesButton = findViewById(R.id.clothesButton);
        electronicsButton = findViewById(R.id.electronicsButton);
        billsButton = findViewById(R.id.billsButton);
        othersButton = findViewById(R.id.othersButton);


        // Set click listeners for buttons
        mealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsumptionActivity.this, MealActivity.class);
                startActivity(intent);
            }
        });

        clothesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsumptionActivity.this, ClothesActivity.class);
                startActivity(intent);
            }
        });

        electronicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsumptionActivity.this, ElectronicsActivity.class);
                startActivity(intent);
            }
        });

        billsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsumptionActivity.this, BillsActivity.class);
                startActivity(intent);
            }
        });

        othersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsumptionActivity.this, OthersActivity.class);
                startActivity(intent);
            }
        });
    }
}
