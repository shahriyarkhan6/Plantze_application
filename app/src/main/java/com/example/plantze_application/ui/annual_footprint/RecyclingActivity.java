package com.example.plantze_application.ui.annual_footprint;


import android.content.Context;
import android.content.Intent;
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


public class RecyclingActivity extends AppCompatActivity {
    private RadioGroup recyclingGroup;
    private Button nextButton;
    private TextView emissionsDisplay;
    private double currentEmissions;
    private String clothingFrequency = null;
    private String deviceFrequency = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycling);


        // Initialize UI components
        recyclingGroup = findViewById(R.id.recyclingGroup);
        nextButton = findViewById(R.id.nextButton);
        emissionsDisplay = findViewById(R.id.emissionsDisplay);


        // Retrieve current emissions from intent
        currentEmissions = getIntent().getDoubleExtra("CURRENT_EMISSIONS", 0);
        updateEmissionsDisplay();


        // Retrieve Firestore data for user choices
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
            int selectedId = recyclingGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select how often you recycle.", Toast.LENGTH_SHORT).show();
                return;
            }


            // Get the selected recycling choice
            RadioButton selectedButton = findViewById(selectedId);
            String recyclingChoice = selectedButton.getText().toString().trim();


            if (clothingFrequency == null || deviceFrequency == null) {
                Toast.makeText(this, "Failed to load user data. Please try again later.", Toast.LENGTH_SHORT).show();
                return;
            }


            // Calculate reductions
            double clothingReduction = calculateClothingReduction(clothingFrequency, recyclingChoice);
            double deviceReduction = calculateDeviceReduction(deviceFrequency, recyclingChoice);
            double totalReduction = clothingReduction + deviceReduction;


            // Apply the reduction to current emissions
            currentEmissions = Math.max(0, currentEmissions - totalReduction);


            // Update the emissions display
            updateEmissionsDisplay();


            // Save the adjusted emissions to Firestore
            db.collection("users").document(userId).update("Annual Consumption Emissions", currentEmissions)
                    .addOnSuccessListener(aVoid -> Log.d("Firestore", "Emissions updated successfully."))
                    .addOnFailureListener(e -> Log.e("Firestore", "Error updating emissions", e));


            Log.d("RecyclingActivity", "Reductions applied. Clothing: " + clothingReduction + ", Device: " + deviceReduction + ", Total: " + totalReduction);


        });
    }


    private void updateEmissionsDisplay() {
        emissionsDisplay.setText("Adjusted Emissions: " + currentEmissions + " CO₂ per year");
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

