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

import com.example.plantze_application.MainActivity;
import com.example.plantze_application.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class RecyclingActivity extends AppCompatActivity {
    private RadioGroup recyclingGroup;
    private Button nextButton;
    private Button returnHomeButton;
    private TextView emissionsDisplay;
    private TextView comparisonMessage; // Added TextView for the comparison message
    private double currentEmissions;
    private double transportCarbonEmission;
    private double foodCarbonEmission;
    private double housingCarbonEmission;
    private String clothingFrequency = null;
    private String deviceFrequency = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycling);

        // Retrieve emissions and clothing frequency from the previous activity
        transportCarbonEmission = getIntent().getDoubleExtra("transportCarbonEmission", 0.0);
        foodCarbonEmission = getIntent().getDoubleExtra("foodCarbonEmission", 0);
        housingCarbonEmission = getIntent().getDoubleExtra("housingCarbonEmission", 0);
        currentEmissions = getIntent().getDoubleExtra("CURRENT_EMISSIONS", 0);

        // Initialize UI components
        recyclingGroup = findViewById(R.id.recyclingGroup);
        nextButton = findViewById(R.id.nextButton);
        emissionsDisplay = findViewById(R.id.emissionsDisplay);
        comparisonMessage = findViewById(R.id.comparisonMessage);
        returnHomeButton = findViewById(R.id.returnHomeButton);

        // Retrieve Firestore data
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE).getString("USER_ID", null);

        if (userId == null) {
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Load clothing and device frequency from Firestore
        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        clothingFrequency = documentSnapshot.getString("Clothing Buying Frequency");
                        deviceFrequency = documentSnapshot.getString("Electronic Devices Frequency");
                        Log.d("Firestore", "Data loaded: Clothing - " + clothingFrequency + ", Device - " + deviceFrequency);
                    } else {
                        Log.e("Firestore", "Document does not exist.");
                    }
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Failed to retrieve data", e));

        // Handle the Next button click
        nextButton.setOnClickListener(v -> {
            // Disabling the button to prevent multiple clicks
            nextButton.setEnabled(false);

            // Ensuring user selects an option
            int selectedId = recyclingGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select how often you recycle.", Toast.LENGTH_SHORT).show();
                nextButton.setEnabled(true);
                return;
            }

            // Get the selected recycling choice
            RadioButton selectedButton = findViewById(selectedId);
            String recyclingChoice = selectedButton.getText().toString().trim();

            if (clothingFrequency == null || deviceFrequency == null) {
                Toast.makeText(this, "Failed to load user data. Please try again later.", Toast.LENGTH_SHORT).show();
                nextButton.setEnabled(true);
                return;
            }

            // Calculate reductions
            double clothingReduction = calculateClothingReduction(clothingFrequency, recyclingChoice);
            double deviceReduction = calculateDeviceReduction(deviceFrequency, recyclingChoice);
            double totalReduction = clothingReduction + deviceReduction;

            // Calculate adjusted emissions after applying the reduction
            currentEmissions = Math.max(0, currentEmissions - totalReduction);
            final double adjustedEmissions = currentEmissions + foodCarbonEmission + housingCarbonEmission + transportCarbonEmission;

            final double adjustedEmissionsInTons = adjustedEmissions / 10000;
            final double foodEmissionsInTons = foodCarbonEmission / 10000;
            final double housingEmissionsInTons = housingCarbonEmission / 10000;
            final double transportEmissionsInTons = transportCarbonEmission / 10000;

            // Save the adjusted emissions to Firestore
            db.collection("users").document(userId).update("Annual Consumption Emissions", adjustedEmissionsInTons)
                    .addOnSuccessListener(aVoid -> Log.d("Firestore", "Emissions updated successfully."))
                    .addOnFailureListener(e -> Log.e("Firestore", "Error updating emissions", e));

            // Retrieve user location and compare emissions
            db.collection("users").document(userId).get()
                    .addOnSuccessListener(locationSnapshot -> {
                        String userLocation = locationSnapshot.getString("Location");
                        Double locationEmissions = locationSnapshot.getDouble("Location Emissions");

                        if (userLocation != null && locationEmissions != null) {
                            double percentageDifference = ((locationEmissions - adjustedEmissionsInTons) / locationEmissions) * 100;

                            String message;
                            if (percentageDifference < 0) {
                                message = "Your carbon footprint is " + String.format("%.2f", Math.abs(percentageDifference)) + "% above the national average for " + userLocation + ".";
                            } else {
                                message = "Your carbon footprint is " + String.format("%.2f", Math.abs(percentageDifference)) + "% below the national average for " + userLocation + ".";
                            }

                            comparisonMessage.setText(message);
                        }
                    })
                    .addOnFailureListener(e -> Log.e("Firestore", "Error loading location data", e));

            // Display the adjusted emissions
            emissionsDisplay.setText("Total carbon emissions: " + adjustedEmissionsInTons + " CO₂ tons per year\n" +
                    "Food carbon emissions: " + foodEmissionsInTons + " CO₂ tons per year\n" +
                    "Transport carbon emissions: " + transportEmissionsInTons + " CO₂ tons per year\n" +
                    "Housing carbon emissions: " + housingEmissionsInTons + " CO₂ tons per year");

            nextButton.setEnabled(true);
        });

        returnHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(RecyclingActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private double calculateClothingReduction(String clothingFrequency, String recyclingChoice) {
        if (clothingFrequency == null || recyclingChoice == null) return 0;

        switch (clothingFrequency) {
            case "Monthly":
                return getClothingReduction(recyclingChoice, 54, 108, 180);
            case "Annually":
                return getClothingReduction(recyclingChoice, 15, 30, 50);
            case "Rarely":
                return getClothingReduction(recyclingChoice, 0.75, 1.5, 2.5);
            default:
                return 0;
        }
    }

    private double getClothingReduction(String recyclingChoice, double occasional, double frequent, double always) {
        switch (recyclingChoice) {
            case "Never":
                return 0;
            case "Occasionally":
                return occasional;
            case "Frequently":
                return frequent;
            case "Always":
                return always;
            default:
                return 0;
        }
    }

    private double calculateDeviceReduction(String deviceFrequency, String recyclingChoice) {
        if (deviceFrequency == null || recyclingChoice == null) return 0;

        switch (deviceFrequency) {
            case "1 Time":
                return getDeviceReduction(recyclingChoice, 45, 60, 90);
            case "2 Times":
                return getDeviceReduction(recyclingChoice, 60, 120, 180);
            case "3 Times":
                return getDeviceReduction(recyclingChoice, 90, 180, 270);
            case "4 or More Times":
                return getDeviceReduction(recyclingChoice, 120, 240, 360);
            default:
                return 0;
        }
    }

    private double getDeviceReduction(String recyclingChoice, double occasional, double frequent, double always) {
        switch (recyclingChoice) {
            case "Never":
                return 0;
            case "Occasionally":
                return occasional;
            case "Frequently":
                return frequent;
            case "Always":
                return always;
            default:
                return 0;
        }
    }
}
