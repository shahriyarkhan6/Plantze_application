package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class ChickenActivity extends AppCompatActivity {

    private RadioGroup chickenRadioGroup;
    private Button submitButton;
    private TextView resultTextView;
    private int currentEmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chicken); // Ensure this XML file exists

        chickenRadioGroup = findViewById(R.id.chickenRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);

        // Get the carbon emission passed from PorkActivity
        currentEmission = getIntent().getIntExtra("carbonEmission", 0);

        // Display the current carbon emission
        resultTextView.setText("Current Carbon Emission: " + currentEmission + " kg");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = chickenRadioGroup.getCheckedRadioButtonId();
                int chickenEmission = 0;

                // Calculate additional emission based on chicken consumption
                if (selectedId == R.id.radioDaily) {
                    chickenEmission = 950; // kg
                } else if (selectedId == R.id.radioFrequently) {
                    chickenEmission = 600; // kg
                } else if (selectedId == R.id.radioOccasionally) {
                    chickenEmission = 200; // kg
                } else if (selectedId == R.id.radioNever) {
                    chickenEmission = 0; // kg
                }

                // Add the chicken emission to the existing carbon emission
                currentEmission += chickenEmission;

                // Update the display with the new total emission
                resultTextView.setText("Total Carbon Emission: " + currentEmission + " kg");

                // Pass the updated carbon emission to the next activity (FishActivity)
                Intent intent = new Intent(ChickenActivity.this, FishActivity.class);
                intent.putExtra("carbonEmission", currentEmission);
                startActivity(intent);
            }
        });
    }
}
