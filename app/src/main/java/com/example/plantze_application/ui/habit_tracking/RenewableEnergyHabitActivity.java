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

public class RenewableEnergyHabitActivity extends AppCompatActivity {

    private TextView renewableEnergyDaysTextView;
    private Button incrementRenewableEnergyDaysButton;
    private int renewableEnergyDays = 0;
    private FirebaseFirestore db;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renewable_energy_habit);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        renewableEnergyDaysTextView = findViewById(R.id.renewableEnergyDaysTextView);
        incrementRenewableEnergyDaysButton = findViewById(R.id.incrementRenewableEnergyDaysButton);

        // Load saved renewable energy days from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        renewableEnergyDays = sharedPreferences.getInt("RENEWABLE_ENERGY_DAYS", 0); // Default to 0

        // Retrieve user ID from SharedPreferences
        userID = sharedPreferences.getString("USER_ID", null);

        // Update TextView
        updateRenewableEnergyDaysText();

        // Set click listener for increment button
        incrementRenewableEnergyDaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increment renewable energy usage days
                renewableEnergyDays++;

                // Update TextView
                updateRenewableEnergyDaysText();

                // Save updated days in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("RENEWABLE_ENERGY_DAYS", renewableEnergyDays);
                editor.apply();

                // Update Firestore
                if (userID != null) {
                    Map<String, Object> updatedData = new HashMap<>();
                    updatedData.put("renewableEnergyDays", renewableEnergyDays);

                    db.collection("users").document(userID)
                            .update(updatedData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RenewableEnergyHabitActivity.this, "Renewable energy days updated in Firestore!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RenewableEnergyHabitActivity.this, "Failed to update Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(RenewableEnergyHabitActivity.this, "User ID is null. Cannot update Firestore.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateRenewableEnergyDaysText() {
        String text = "You’ve logged " + renewableEnergyDays + " days of renewable energy usage.";
        renewableEnergyDaysTextView.setText(text);
    }
}
