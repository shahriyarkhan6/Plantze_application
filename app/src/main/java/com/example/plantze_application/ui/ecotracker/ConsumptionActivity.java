package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class ConsumptionActivity extends AppCompatActivity {
    private RadioGroup frequencyGroup;
    private Button nextButton;
    private TextView emissionsDisplay;
    private double currentEmissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);

        currentEmissions = getIntent().getDoubleExtra("CURRENT_EMISSIONS", 0);
        frequencyGroup = findViewById(R.id.frequencyGroup);
        nextButton = findViewById(R.id.nextButton);
        emissionsDisplay = findViewById(R.id.emissionsDisplay);
        emissionsDisplay.setText("Current Emissions: " + currentEmissions + " CO₂ per year");

        frequencyGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                RadioButton selectedButton = findViewById(checkedId);
                String frequency = selectedButton.getText().toString();
                double clothingEmissions = calculateClothingEmissions(frequency);
                double totalEmissions = currentEmissions + clothingEmissions;
                emissionsDisplay.setText("Current Emissions: " + totalEmissions + " CO₂ per year");
            }
        });

        nextButton.setOnClickListener(v -> {
            int selectedFrequencyId = frequencyGroup.getCheckedRadioButtonId();
            if (selectedFrequencyId == -1) {
                Toast.makeText(this, "Please select a frequency option.", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedFrequencyButton = findViewById(selectedFrequencyId);
            String frequency = selectedFrequencyButton.getText().toString();
            double clothingEmissions = calculateClothingEmissions(frequency);
            double totalEmissions = currentEmissions + clothingEmissions;

            // Pass clothing frequency to next activity
            Intent intent = new Intent(ConsumptionActivity.this, EcoFriendlyProductsActivity.class);
            intent.putExtra("CURRENT_EMISSIONS", totalEmissions);
            intent.putExtra("CLOTHING_FREQUENCY", frequency); // Pass clothing frequency
            startActivity(intent);
        });
    }

    private double calculateClothingEmissions(String frequency) {
        switch (frequency) {
            case "Monthly":
                return 360;
            case "Quarterly":
                return 120;
            case "Annually":
                return 100;
            case "Rarely":
                return 5;
            default:
                return 0;
        }
    }
}
