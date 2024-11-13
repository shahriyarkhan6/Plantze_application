package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class FishActivity extends AppCompatActivity {

    private RadioGroup fishRadioGroup;
    private Button submitButton;
    private TextView resultTextView;
    private int currentEmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish); // Ensure this XML file exists

        fishRadioGroup = findViewById(R.id.fishRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);

        // Get the carbon emission passed from ChickenActivity
        currentEmission = getIntent().getIntExtra("carbonEmission", 0);

        // Display the current carbon emission
        resultTextView.setText("Current Carbon Emission: " + currentEmission + " kg");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = fishRadioGroup.getCheckedRadioButtonId();
                int fishEmission = 0;

                // Calculate additional emission based on fish consumption
                if (selectedId == R.id.radioDaily) {
                    fishEmission = 800; // kg
                } else if (selectedId == R.id.radioFrequently) {
                    fishEmission = 500; // kg
                } else if (selectedId == R.id.radioOccasionally) {
                    fishEmission = 150; // kg
                } else if (selectedId == R.id.radioNever) {
                    fishEmission = 0; // kg
                }

                // Add the fish emission to the existing carbon emission
                currentEmission += fishEmission;

                // Update the display with the new total emission
                resultTextView.setText("Total Carbon Emission: " + currentEmission + " kg");

                // Optionally, you can add logic to transition to a summary screen or complete the process
            }
        });
    }
}
