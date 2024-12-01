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


public class ElectronicDevicesActivity extends AppCompatActivity {
    private RadioGroup devicesGroup;
    private Button nextButton;
    private TextView emissionsDisplay;
    private double currentEmissions;
    private String clothingFrequency;
    private double foodCarbonEmission;
    private double transportCarbonEmission;
    private double housingCarbonEmission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronic_devices);


        // Retrieve emissions and clothing frequency from the previous activity
        currentEmissions = getIntent().getDoubleExtra("CURRENT_EMISSIONS", 0);
        clothingFrequency = getIntent().getStringExtra("CLOTHING_FREQUENCY");


        devicesGroup = findViewById(R.id.devicesGroup);
        nextButton = findViewById(R.id.nextButton);
        emissionsDisplay = findViewById(R.id.emissionsDisplay);
        emissionsDisplay.setText("Current Emissions: " + currentEmissions + " CO₂ per year");

        foodCarbonEmission = getIntent().getDoubleExtra("foodCarbonEmission", 0);
        transportCarbonEmission = getIntent().getDoubleExtra("transportCarbonEmission", 0);
        housingCarbonEmission = getIntent().getDoubleExtra("housingCarbonEmission", 0);

        nextButton.setOnClickListener(v -> {
            int selectedDevicesId = devicesGroup.getCheckedRadioButtonId();
            if (selectedDevicesId == -1) {
                Toast.makeText(this, "Please select an option for electronic devices.", Toast.LENGTH_SHORT).show();
                return;
            }


            // Get the selected frequency and calculate emissions
            RadioButton selectedDevicesButton = findViewById(selectedDevicesId);
            String frequency = selectedDevicesButton.getText().toString();
            double devicesEmissions = calculateDevicesEmissions(frequency);
            double totalEmissions = currentEmissions + devicesEmissions;


            emissionsDisplay.setText("Total Emissions: " + totalEmissions + " CO₂ per year");

            // Pass the updated emissions and clothing frequency to the next activity
            Intent intent = new Intent(ElectronicDevicesActivity.this, RecyclingActivity.class);
            intent.putExtra("CURRENT_EMISSIONS", totalEmissions);
            intent.putExtra("CLOTHING_FREQUENCY", clothingFrequency); // Pass clothing frequency

            intent.putExtra("foodCarbonEmission", foodCarbonEmission);
            intent.putExtra("transportCarbonEmission", transportCarbonEmission);
            intent.putExtra("housingCarbonEmission", housingCarbonEmission);

            startActivity(intent);

            // Save the electronic devices frequency and emissions in Firestore
            SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
            String userID = sharedPref.getString("USER_ID", null);


            if (userID != null) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> updatedData = new HashMap<>();
                updatedData.put("Electronic Devices Frequency", frequency);
                updatedData.put("Total Emissions", totalEmissions);


                db.collection("users").document(userID)
                        .update(updatedData)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(ElectronicDevicesActivity.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();


                            // Navigate to the next activity
                            Intent intent = new Intent(ElectronicDevicesActivity.this, RecyclingActivity.class);
                            intent.putExtra("CURRENT_EMISSIONS", totalEmissions);
                            intent.putExtra("CLOTHING_FREQUENCY", clothingFrequency);
                            startActivity(intent);
                        })
                        .addOnFailureListener(e -> {
                            Log.e("Firestore", "Error saving data", e);
                            Toast.makeText(ElectronicDevicesActivity.this, "Failed to save data.", Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(this, "User ID not found. Please log in again.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Method to calculate emissions based on the frequency of device purchases
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

