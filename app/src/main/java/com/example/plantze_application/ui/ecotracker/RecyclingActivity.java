package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class RecyclingActivity extends AppCompatActivity {
    private RadioGroup recyclingGroup;
    private Button nextButton;
    private TextView emissionsDisplay;
    private double currentEmissions;
    private String clothingFrequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycling);

        // Retrieve current emissions and clothing frequency from the previous activity
        currentEmissions = getIntent().getDoubleExtra("CURRENT_EMISSIONS", 0);
        clothingFrequency = getIntent().getStringExtra("CLOTHING_FREQUENCY"); // Receive clothing frequency

        // Set up the views
        recyclingGroup = findViewById(R.id.recyclingGroup);
        nextButton = findViewById(R.id.nextButton);
        emissionsDisplay = findViewById(R.id.emissionsDisplay);

        // Display the current emissions
        emissionsDisplay.setText("Current Emissions: " + currentEmissions + " CO₂ per year");

        // Set up the button to calculate the reduction when clicked
        nextButton.setOnClickListener(v -> {
            int selectedId = recyclingGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an option for recycling.", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedButton = findViewById(selectedId);
            String recyclingChoice = selectedButton.getText().toString();
            double reductionPercentage = calculateRecyclingReduction(recyclingChoice);

            // Calculate adjusted emissions after applying the reduction
            double adjustedEmissions = currentEmissions - reductionPercentage;

            // Display the adjusted emissions
            emissionsDisplay.setText("Adjusted Emissions after Recycling: " + adjustedEmissions + " CO₂ per year");

            // Optionally, you can pass the updated emissions to another activity
            // Intent intent = new Intent(RecyclingActivity.this, NextActivity.class);
            // intent.putExtra("UPDATED_EMISSIONS", adjustedEmissions);
            // startActivity(intent);
        });
    }

    private double calculateRecyclingReduction(String recyclingChoice) {
        double reduction = 0;

        // Emission reductions based on clothing frequency and recycling choice
        switch (clothingFrequency) {
            case "Monthly":
                if (recyclingChoice.equals("Occasional Recycling")) {
                    reduction = 54;
                } else if (recyclingChoice.equals("Frequent Recycling")) {
                    reduction = 108;
                } else if (recyclingChoice.equals("Always Recycling")) {
                    reduction = 180;
                }
                break;
            case "Annually":
                if (recyclingChoice.equals("Occasional Recycling")) {
                    reduction = 15;
                } else if (recyclingChoice.equals("Frequent Recycling")) {
                    reduction = 30;
                } else if (recyclingChoice.equals("Always Recycling")) {
                    reduction = 50;
                }
                break;
            case "Rarely":
                if (recyclingChoice.equals("Occasional Recycling")) {
                    reduction = 0.75;
                } else if (recyclingChoice.equals("Frequent Recycling")) {
                    reduction = 1.5;
                } else if (recyclingChoice.equals("Always Recycling")) {
                    reduction = 2.5;
                }
                break;
        }

        return reduction;
    }
}
