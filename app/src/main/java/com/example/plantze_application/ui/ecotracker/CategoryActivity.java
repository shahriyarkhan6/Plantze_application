package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;
import com.example.plantze_application.ui.ecotracker.ConsumptionActivity;
import com.example.plantze_application.ui.ecotracker.TransportationActivity;

public class CategoryActivity extends AppCompatActivity{
    private Button transportationButton;
    private Button consumptionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_category);

        // Initialize buttons
        transportationButton = findViewById(R.id.transportationButton);
        consumptionButton = findViewById(R.id.consumptionButton);

        // Set click listeners for buttons
        transportationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, TransportationActivity.class);
                startActivity(intent);
            }
        });

        consumptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, ConsumptionActivity.class);
                startActivity(intent);
            }
        });

    }
}
