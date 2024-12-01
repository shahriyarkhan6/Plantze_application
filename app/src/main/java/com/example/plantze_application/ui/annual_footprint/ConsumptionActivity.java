package com.example.plantze_application.ui.annual_footprint;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;


public class ConsumptionActivity extends AppCompatActivity {
    private RadioGroup frequencyGroup;
    private Button nextButton;
    private TextView emissionsDisplay;
    private double currentEmissions;
    private double foodCarbonEmission;
    private double transportCarbonEmission;
    private double housingCarbonEmission;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);


        // Initialize UI elements
        currentEmissions = getIntent().getDoubleExtra("CURRENT_EMISSIONS", 0);
        frequencyGroup = findViewById(R.id.frequencyGroup);
        nextButton = findViewById(R.id.nextButton);
        emissionsDisplay = findViewById(R.id.emissionsDisplay);
        emissionsDisplay.setText("Current Emissions: " + currentEmissions + " CO₂ per year");

        foodCarbonEmission = getIntent().getDoubleExtra("foodCarbonEmission", 0);
        transportCarbonEmission = getIntent().getDoubleExtra("transportCarbonEmission", 0);
        housingCarbonEmission = getIntent().getDoubleExtra("housingCarbonEmission", 0);


        // Listen for changes in the radio group selection
        frequencyGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                RadioButton selectedButton = findViewById(checkedId);
                String frequency = selectedButton.getText().toString();
                double clothingEmissions = calculateClothingEmissions(frequency);
                double totalEmissions = currentEmissions + clothingEmissions;
                emissionsDisplay.setText("Current Emissions: " + totalEmissions + " CO₂ per year");
            }
        });


        // Handle the Next button click
        nextButton.setOnClickListener(v -> {
            int selectedFrequencyId = frequencyGroup.getCheckedRadioButtonId();
            if (selectedFrequencyId == -1) {
                Toast.makeText(this, "Please select a frequency option.", Toast.LENGTH_SHORT).show();
                return;
            }


            // Get the selected frequency and calculate emissions
            RadioButton selectedFrequencyButton = findViewById(selectedFrequencyId);
            String frequency = selectedFrequencyButton.getText().toString();
            double clothingEmissions = calculateClothingEmissions(frequency);
            double totalEmissions = currentEmissions + clothingEmissions;


            // Save the selected frequency and emissions in Firestore
            SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
            String userID = sharedPref.getString("USER_ID", null);


            if (userID != null) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> updatedData = new HashMap<>();
                updatedData.put("Clothing Buying Frequency", frequency);
                updatedData.put("Total Emissions", totalEmissions);


                db.collection("users").document(userID)
                        .update(updatedData)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(ConsumptionActivity.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();


                            // Navigate to the next activity
                            Intent intent = new Intent(ConsumptionActivity.this, EcoFriendlyProductsActivity.class);
                            intent.putExtra("CURRENT_EMISSIONS", totalEmissions);

                            intent.putExtra("foodCarbonEmission", foodCarbonEmission);
                            intent.putExtra("transportCarbonEmission", transportCarbonEmission);
                            intent.putExtra("housingCarbonEmission", housingCarbonEmission);
                            startActivity(intent);
                        })
                        .addOnFailureListener(e -> {
                            Log.e("Firestore", "Error saving data", e);
                            Toast.makeText(ConsumptionActivity.this, "Failed to save data.", Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(this, "User ID not found. Please log in again.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Method to calculate emissions based on frequency
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

