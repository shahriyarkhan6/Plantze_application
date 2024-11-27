package com.example.plantze_application.ui.annual_footprint;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FoodWasteActivity extends AppCompatActivity {

    private RadioGroup foodWasteRadioGroup;
    private Button submitButton;
    private TextView resultTextView;
    private int currentEmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_waste);

        foodWasteRadioGroup = findViewById(R.id.foodWasteRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);

        currentEmission = getIntent().getIntExtra("carbonEmission", 0);

        resultTextView.setText("Current Carbon Emission: " + currentEmission + " CO₂");

        submitButton.setOnClickListener(v -> {
            int selectedId = foodWasteRadioGroup.getCheckedRadioButtonId();
            double foodWasteEmission = 0.0; // Use double for food waste emission values

            if (selectedId == R.id.radioNever) {
                foodWasteEmission = 0.0;
            } else if (selectedId == R.id.radioRarely) {
                foodWasteEmission = 23.4;
            } else if (selectedId == R.id.radioOccasionally) {
                foodWasteEmission = 70.2;
            } else if (selectedId == R.id.radioFrequently) {
                foodWasteEmission = 140.4;
            }

            currentEmission += foodWasteEmission;

            resultTextView.setText("Total Carbon Emission: " + currentEmission + " CO₂");


            SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
            String userID = sharedPref.getString("USER_ID", null);

            if (userID != null) {
                Map<String, Object> updatedData = new HashMap<>();
                updatedData.put("Annual Food Emission", currentEmission); // Example data

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("users").document(userID)
                        .update(updatedData)
                        .addOnSuccessListener(aVoid -> {
                            // Update successful
                            Toast.makeText(FoodWasteActivity.this, "User info updated successfully!", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            // Handle error
                            Log.e("Firestore", "Error updating user info", e);
                        });
            }


        });
    }
}
