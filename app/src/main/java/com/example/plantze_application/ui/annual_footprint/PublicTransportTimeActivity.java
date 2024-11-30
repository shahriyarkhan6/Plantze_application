package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class PublicTransportTimeActivity extends AppCompatActivity {

    private RadioGroup timeGroup;
    private Button calculateButton;
    private TextView emissionsDisplay;
    private double carbonEmission;
    private String frequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_transport_time);

        carbonEmission = getIntent().getDoubleExtra("carbonEmission", 0);
        frequency = getIntent().getStringExtra("FREQUENCY");


        timeGroup = findViewById(R.id.timeGroup);
        calculateButton = findViewById(R.id.calculateButton);
        emissionsDisplay = findViewById(R.id.emissionsDisplay);

        calculateButton.setOnClickListener(v -> {
            int selectedTimeId = timeGroup.getCheckedRadioButtonId();

            if (selectedTimeId == -1) {
                Toast.makeText(this, "Please select a time spent option.", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedTimeButton = findViewById(selectedTimeId);
            String timeSpent = selectedTimeButton.getText().toString();

            double transportEmissions = calculateTransportEmissions(frequency, timeSpent);

            double totalEmissions = carbonEmission + transportEmissions;

            emissionsDisplay.setText("Total Emissions: " + totalEmissions + " CO₂ per year");

            Intent intent = new Intent(PublicTransportTimeActivity.this, ShortHaulFlightsActivity.class);
            intent.putExtra("carbonEmission", totalEmissions);

            startActivity(intent);
        });
    }

    private double calculateTransportEmissions(String frequency, String timeSpent) {
        double emissions = 0;

        switch (frequency) {
            case "Never":
                emissions = 0;
                break;
            case "Occasionally (1-2 times/week)":
                emissions = getOccasionallyEmissions(timeSpent);
                break;
            case "Frequently (3-4 times/week)":
            case "Always (5+ times/week)": // Same logic for "Frequently" and "Always"
                emissions = getFrequentlyAndAlwaysEmissions(timeSpent);
                break;
        }

        return emissions;
    }

    private double getOccasionallyEmissions(String timeSpent) {
        switch (timeSpent) {
            case "Under 1 hour":
                return 246;
            case "1-3 hours":
                return 819;
            case "3-5 hours":
                return 1638;
            case "5-10 hours":
                return 3071;
            case "More than 10 hours":
                return 4095;
            default:
                return 0;
        }
    }

    private double getFrequentlyAndAlwaysEmissions(String timeSpent) {
        switch (timeSpent) {
            case "Under 1 hour":
                return 573;
            case "1-3 hours":
                return 1911;
            case "3-5 hours":
                return 3822;
            case "5-10 hours":
                return 7166;
            case "More than 10 hours":
                return 9555;
            default:
                return 0;
        }
    }
}
