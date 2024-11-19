package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class PublicTransportTimeActivity extends AppCompatActivity {

    private RadioGroup timeGroup;
    private Button calculateButton;
    private TextView emissionsDisplay;
    private String frequency;
    private double carEmissions; // Received car emissions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_transport_time);

        // Retrieve frequency and car emissions from the previous activity
        Intent intent = getIntent();
        frequency = intent.getStringExtra("FREQUENCY");
        carEmissions = intent.getDoubleExtra("CAR_EMISSIONS", 0);

        // Initialize radio group, button, and emissions display
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

            double transportEmissions = calculateEmissions(frequency, timeSpent);

            double totalEmissions = carEmissions + transportEmissions;

            emissionsDisplay.setText("Estimated Emissions: " + totalEmissions + " CO₂ per year");

            Intent intent1 = new Intent(PublicTransportTimeActivity.this, ShortHaulFlightsActivity.class);
            intent1.putExtra("TOTAL_EMISSIONS", totalEmissions);  // Pass the emissions data
            startActivity(intent1);
        });
    }

    private double calculateEmissions(String frequency, String timeSpent) {
        double emissions = 0.0;

        switch (frequency) {
            case "Never":
                emissions = 0.0;
                break;
            case "Occasionally (1-2 times/week)":
                emissions = getOccasionallyEmissions(timeSpent);
                break;
            case "Frequently (3-4 times/week)":
                emissions = getFrequentlyEmissions(timeSpent);
                break;
            case "Always (5+ times/week)":
                emissions = getAlwaysEmissions(timeSpent);
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
                return 0.0;
        }
    }

    private double getFrequentlyEmissions(String timeSpent) {
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
                return 0.0;
        }
    }

    private double getAlwaysEmissions(String timeSpent) {
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
                return 0.0;
        }
    }
}
