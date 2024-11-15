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
    private double emissions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_distance);

        emissionFactor = getIntent().getDoubleExtra("emissionFactor", 0);

        distanceGroup = findViewById(R.id.distanceGroup);
        resultTextView = findViewById(R.id.resultTextView);
        nextButton = findViewById(R.id.nextButton);  // Initialize the "Next" button

        nextButton.setEnabled(false);

        distanceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                calculateAndDisplayEmissions(checkedId);
                nextButton.setEnabled(true);
            }
        });

        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(CarDistanceActivity.this, PublicTransportFrequencyActivity.class);
            intent.putExtra("CAR_EMISSIONS", emissions);  // Pass car emissions to the next activity
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

        emissions = emissionFactor * distanceDriven;

        resultTextView.setText("Estimated Emissions: " + emissions + " CO₂ per year");
    }
}
