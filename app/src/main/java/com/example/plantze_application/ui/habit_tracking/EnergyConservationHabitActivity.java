package com.example.plantze_application.ui.habit_tracking;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EnergyConservationHabitActivity extends AppCompatActivity {

    private TextView energyConservationDaysTextView;
    private Button incrementEnergyConservationDaysButton;
    private int energyConservationDays = 0;
    private FirebaseFirestore db;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_conservation_habit);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        energyConservationDaysTextView = findViewById(R.id.energyConservationDaysTextView);
        incrementEnergyConservationDaysButton = findViewById(R.id.incrementEnergyConservationDaysButton);

        // Load saved energy conservation days from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        energyConservationDays = sharedPreferences.getInt("ENERGY_CONSERVATION_DAYS", 0); // Default to 0

        // Retrieve user ID from SharedPreferences
        userID = sharedPreferences.getString("USER_ID", null);

        // Update TextView
        updateEnergyConservationDaysText();

        // Set click listener for increment button
        incrementEnergyConservationDaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increment energy conservation days
                energyConservationDays++;

                // Update TextView
                updateEnergyConservationDaysText();

                // Save updated days in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("ENERGY_CONSERVATION_DAYS", energyConservationDays);
                editor.apply();

                // Update Firestore
                if (userID != null) {
                    Map<String, Object> updatedData = new HashMap<>();
                    updatedData.put("energyConservationDays", energyConservationDays);

                    db.collection("users").document(userID)
                            .update(updatedData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(EnergyConservationHabitActivity.this, "Energy conservation days updated in Firestore!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EnergyConservationHabitActivity.this, "Failed to update Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(EnergyConservationHabitActivity.this, "User ID is null. Cannot update Firestore.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateEnergyConservationDaysText() {
        String text = "You’ve practiced energy conservation for " + energyConservationDays + " days this year.";
        energyConservationDaysTextView.setText(text);
    }
}
