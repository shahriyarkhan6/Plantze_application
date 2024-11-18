package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class CarDistanceActivity extends AppCompatActivity {

    private RadioGroup distanceGroup;
    private TextView resultTextView;
    private Button nextButton;
    private double emissionFactor;
    private double emissions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_distance);

        emissionFactor = getIntent().getDoubleExtra("emissionFactor", 0);

        distanceGroup = findViewById(R.id.distanceGroup);
        resultTextView = findViewById(R.id.resultTextView);
        nextButton = findViewById(R.id.nextButton);

        nextButton.setEnabled(false);

        distanceGroup.setOnCheckedChangeListener((group, checkedId) -> {
            calculateAndDisplayEmissions(checkedId);
            nextButton.setEnabled(true); // Enable the Next button after a selection is made
        });

        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(CarDistanceActivity.this, PublicTransportFrequencyActivity.class);
            intent.putExtra("CAR_EMISSIONS", emissions);
            startActivity(intent);
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

        // Calculate emissions
        emissions = emissionFactor * distanceDriven;

        // Display the emissions
        resultTextView.setText("Current Emissions: " + emissions + " CO₂ per year");
    }
}
