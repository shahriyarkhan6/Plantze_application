package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class ShortHaulFlightsActivity extends AppCompatActivity {

    private RadioGroup flightsGroup;
    private Button nextButton;
    private TextView emissionsDisplay;
    private double currentEmissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_haul_flights);

        currentEmissions = getIntent().getDoubleExtra("TOTAL_EMISSIONS", 0);

        flightsGroup = findViewById(R.id.flightsGroup);
        nextButton = findViewById(R.id.nextButton);
        emissionsDisplay = findViewById(R.id.emissionsDisplay);

        emissionsDisplay.setText("Current Emissions: " + currentEmissions + " CO2");

        nextButton.setOnClickListener(v -> {
            int selectedFlightsId = flightsGroup.getCheckedRadioButtonId();

            if (selectedFlightsId == -1) {
                Toast.makeText(this, "Please select a number of flights.", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedFlightsButton = findViewById(selectedFlightsId);
            String flights = selectedFlightsButton.getText().toString();

            double flightEmissions = getFlightEmissions(flights);

            double totalEmissions = currentEmissions + flightEmissions;

            emissionsDisplay.setText("Total Emissions: " + totalEmissions + " CO2");

            Intent nextIntent = new Intent(ShortHaulFlightsActivity.this, LongHaulFlightsActivity.class);
            nextIntent.putExtra("TOTAL_EMISSIONS", totalEmissions);
            startActivity(nextIntent);
        });
    }

    private double getFlightEmissions(String flights) {
        double emissions = 0.0;

        switch (flights) {
            case "1-2 flights":
                emissions = 225; // Emissions for 1-2 short-haul flights
                break;
            case "3-5 flights":
                emissions = 600; // Emissions for 3-5 short-haul flights
                break;
            case "6-10 flights":
                emissions = 1200; // Emissions for 6-10 short-haul flights
                break;
            case "More than 10 flights":
                emissions = 1800; // Emissions for more than 10 short-haul flights
                break;
        }

        return emissions;
    }
}
