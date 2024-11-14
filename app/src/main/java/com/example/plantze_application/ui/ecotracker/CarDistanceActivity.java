package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class CarDistanceActivity extends AppCompatActivity {

    private RadioGroup distanceGroup;
    private TextView resultTextView;
    private double emissionFactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_distance);

        // Retrieve the emission factor passed from CarTypeActivity
        emissionFactor = getIntent().getDoubleExtra("emissionFactor", 0);

        distanceGroup = findViewById(R.id.distanceGroup);
        resultTextView = findViewById(R.id.resultTextView);

        // Listen for changes in the selected distance option
        distanceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                calculateAndDisplayEmissions(checkedId);
            }
        });
    }

    private void calculateAndDisplayEmissions(int checkedId) {
        double distanceDriven = 0;

        if (checkedId == R.id.radioUpTo5000) {
            distanceDriven = 5000;
        } else if (checkedId == R.id.radio5000to10000) {
            distanceDriven = 10000;
        } else if (checkedId == R.id.radio10000to15000) {
            distanceDriven = 15000;
        } else if (checkedId == R.id.radio15000to20000) {
            distanceDriven = 20000;
        } else if (checkedId == R.id.radio20000to25000) {
            distanceDriven = 25000;
        } else if (checkedId == R.id.radioMoreThan25000) {
            distanceDriven = 35000;
        }

        // Calculate emissions based on the emission factor and distance driven
        double emissions = emissionFactor * distanceDriven;

        // Display the calculated emissions
        resultTextView.setText("Estimated Emissions: " + emissions + " kg CO₂ per year");
    }
}
// testing change