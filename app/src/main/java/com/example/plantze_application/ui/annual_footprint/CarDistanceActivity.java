package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class CarDistanceActivity extends AppCompatActivity {

    private RadioGroup distanceGroup;
    private TextView resultTextView;
    private double carbonEmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_distance);

        // Retrieve the emission factor passed from CarTypeActivity
        carbonEmission = getIntent().getDoubleExtra("carbonEmission", 0);

        distanceGroup = findViewById(R.id.distanceGroup);
        resultTextView = findViewById(R.id.resultTextView);


        distanceGroup = findViewById(R.id.distanceGroup);

        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double distanceDriven = 0;
                int checkedId = distanceGroup.getCheckedRadioButtonId();

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
                carbonEmission = carbonEmission * distanceDriven;


                // Display the calculated emissions
                resultTextView.setText("Total Carbon Emission: " + carbonEmission + " CO₂");


                Intent intent = new Intent(CarDistanceActivity.this, PublicTransportFrequencyActivity.class);
                intent.putExtra("carbonEmission", carbonEmission);
                startActivity(intent);


            }

            });


    }
}
// testing change
// test test
