package com.example.plantze_application.ui.annual_footprint;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class LongHaulFlightsActivity extends AppCompatActivity {

    private RadioGroup flightsGroup;
    private Button calculateButton;
    private TextView emissionsDisplay;
    private double currentEmissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_haul_flights);

        currentEmissions = getIntent().getDoubleExtra("TOTAL_EMISSIONS", 0);

        flightsGroup = findViewById(R.id.flightsGroup);
        calculateButton = findViewById(R.id.calculateButton);
        emissionsDisplay = findViewById(R.id.emissionsDisplay);

        emissionsDisplay.setText("Current Emissions: " + currentEmissions + " CO2");

        calculateButton.setOnClickListener(v -> {
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

        });
    }

    private double getFlightEmissions(String flights) {
        double emissions = 0.0;

        switch (flights) {
            case "None":
                emissions = 0; // No emissions if no flights
                break;
            case "1-2 flights":
                emissions = 825; // Emissions for 1-2 flights
                break;
            case "3-5 flights":
                emissions = 2200; // Emissions for 3-5 flights
                break;
            case "6-10 flights":
                emissions = 4400; // Emissions for 6-10 flights
                break;
            case "More than 10 flights":
                emissions = 6600; // Emissions for more than 10 flights
                break;
        }

        return emissions;
    }
}
