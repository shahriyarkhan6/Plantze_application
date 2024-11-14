package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class PublicTransportTimeActivity extends AppCompatActivity {

    private RadioGroup timeGroup;
    private Button calculateButton;
    private TextView emissionsResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_transport_time);

        timeGroup = findViewById(R.id.timeGroup);
        calculateButton = findViewById(R.id.calculateButton);
        emissionsResult = findViewById(R.id.emissionsResult);

        Intent intent = getIntent();
        String frequency = intent.getStringExtra("FREQUENCY");
        double carEmissions = intent.getDoubleExtra("CAR_EMISSIONS", 0.0);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = timeGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);
                String timeSpent = selectedRadioButton.getText().toString();

                double publicTransportEmissions = 0.0;

                if (frequency.equals("Never")) {
                    publicTransportEmissions = 0;
                } else if (frequency.equals("Occasionally")) {
                    publicTransportEmissions = calculateEmissions(timeSpent, 246, 819, 1638, 3071, 4095);
                } else if (frequency.equals("Frequently")) {
                    publicTransportEmissions = calculateEmissions(timeSpent, 573, 1911, 3822, 7166, 9555);
                } else if (frequency.equals("Always")) {
                    publicTransportEmissions = calculateEmissions(timeSpent, 573, 1911, 3822, 7166, 9555);
                }

                double totalEmissions = carEmissions + publicTransportEmissions;

                emissionsResult.setText("Total Carbon Emission: " + totalEmissions + " kg/year");
            }
        });
    }

    private double calculateEmissions(String timeSpent, int under1hr, int oneToThree, int threeToFive, int fiveToTen, int moreThanTen) {
        switch (timeSpent) {
            case "Under 1 hour":
                return under1hr;
            case "1-3 hours":
                return oneToThree;
            case "3-5 hours":
                return threeToFive;
            case "5-10 hours":
                return fiveToTen;
            case "More than 10 hours":
                return moreThanTen;
            default:
                return 0;
        }
    }
}
