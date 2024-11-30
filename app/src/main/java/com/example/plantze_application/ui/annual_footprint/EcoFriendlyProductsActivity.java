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

public class EcoFriendlyProductsActivity extends AppCompatActivity {
    private RadioGroup ecoFriendlyGroup;
    private Button nextButton;
    private TextView emissionsDisplay;
    private double currentEmissions;
    private String clothingFrequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_friendly_products);

        currentEmissions = getIntent().getDoubleExtra("CURRENT_EMISSIONS", 0);
        clothingFrequency = getIntent().getStringExtra("CLOTHING_FREQUENCY"); // Receive clothing frequency
        ecoFriendlyGroup = findViewById(R.id.ecoFriendlyGroup);
        nextButton = findViewById(R.id.nextButton);
        emissionsDisplay = findViewById(R.id.emissionsDisplay);
        emissionsDisplay.setText("Current Emissions: " + currentEmissions + " CO₂ per year");

        nextButton.setOnClickListener(v -> {
            int selectedId = ecoFriendlyGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an option for eco-friendly products.", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton selectedButton = findViewById(selectedId);
            String ecoFriendlyChoice = selectedButton.getText().toString();
            double reductionPercentage = calculateEcoFriendlyReduction(ecoFriendlyChoice);
            double adjustedEmissions = currentEmissions * (1 - reductionPercentage);


            // Pass updated emissions and clothing frequency to next activity
            Intent intent = new Intent(EcoFriendlyProductsActivity.this, ElectronicDevicesActivity.class);
            intent.putExtra("CURRENT_EMISSIONS", adjustedEmissions);
            intent.putExtra("CLOTHING_FREQUENCY", clothingFrequency); // Pass clothing frequency
            startActivity(intent);
        });
    }

    private double calculateEcoFriendlyReduction(String ecoFriendlyChoice) {
        switch (ecoFriendlyChoice) {
            case "Regularly":
                return 0.50;
            case "Occasionally":
                return 0.30;
            case "No":
                return 0.00;
            default:
                return 0.00;
        }
    }
}
