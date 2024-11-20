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

public class ElectronicDevicesActivity extends AppCompatActivity {
    private RadioGroup devicesGroup;
    private Button nextButton;
    private TextView emissionsDisplay;
    private double currentEmissions;
    private String clothingFrequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronic_devices);

        currentEmissions = getIntent().getDoubleExtra("CURRENT_EMISSIONS", 0);
        clothingFrequency = getIntent().getStringExtra("CLOTHING_FREQUENCY"); // Receive clothing frequency
        devicesGroup = findViewById(R.id.devicesGroup);
        nextButton = findViewById(R.id.nextButton);
        emissionsDisplay = findViewById(R.id.emissionsDisplay);
        emissionsDisplay.setText("Current Emissions: " + currentEmissions + " CO₂ per year");

        nextButton.setOnClickListener(v -> {
            int selectedDevicesId = devicesGroup.getCheckedRadioButtonId();
            if (selectedDevicesId == -1) {
                Toast.makeText(this, "Please select an option for electronic devices.", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton selectedDevicesButton = findViewById(selectedDevicesId);
            String frequency = selectedDevicesButton.getText().toString();
            double devicesEmissions = calculateDevicesEmissions(frequency);
            double totalEmissions = currentEmissions + devicesEmissions;

            emissionsDisplay.setText("Total Emissions: " + totalEmissions + " CO₂ per year");

            // Pass the updated emissions and clothing frequency to the next activity
            Intent intent = new Intent(ElectronicDevicesActivity.this, RecyclingActivity.class);
            intent.putExtra("CURRENT_EMISSIONS", totalEmissions);
            intent.putExtra("CLOTHING_FREQUENCY", clothingFrequency); // Pass clothing frequency
            startActivity(intent);
        });
    }

    private double calculateDevicesEmissions(String frequency) {
        switch (frequency) {
            case "None":
                return 0;
            case "1 Time":
                return 300;
            case "2 Times":
                return 600;
            case "3 or More Times":
                return 900;
            case "4 or More Times":
                return 1200;
            default:
                return 0;
        }
    }
}
