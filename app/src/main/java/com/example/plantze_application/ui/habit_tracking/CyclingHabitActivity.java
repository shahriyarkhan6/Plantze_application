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

public class CyclingHabitActivity extends AppCompatActivity {

    private TextView cyclingDaysTextView;
    private Button incrementCyclingDaysButton;
    private int cyclingDays = 0;
    private FirebaseFirestore db;
    private String userID; // Retrieve from SharedPreferences or authentication

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycling_habit);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        cyclingDaysTextView = findViewById(R.id.cyclingDaysTextView);
        incrementCyclingDaysButton = findViewById(R.id.incrementCyclingDaysButton);

        // Load saved cycling days from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        cyclingDays = sharedPreferences.getInt("CYCLING_DAYS", 0); // Default to 0

        // Retrieve user ID from SharedPreferences
        userID = sharedPreferences.getString("USER_ID", null);

        // Update TextView
        updateCyclingDaysText();

        // Set click listener for increment button
        incrementCyclingDaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increment cycling days
                cyclingDays++;

                // Update TextView
                updateCyclingDaysText();

                // Save updated cycling days in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("CYCLING_DAYS", cyclingDays);
                editor.apply();

                // Update Firestore
                if (userID != null) {
                    Map<String, Object> updatedData = new HashMap<>();
                    updatedData.put("cyclingDays", cyclingDays);

                    db.collection("users").document(userID)
                            .update(updatedData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(CyclingHabitActivity.this, "Cycling days updated in Firestore!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CyclingHabitActivity.this, "Failed to update Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(CyclingHabitActivity.this, "User ID is null. Cannot update Firestore.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateCyclingDaysText() {
        String text = "Congratulations! You have spent " + cyclingDays + " days cycling instead of driving.";
        cyclingDaysTextView.setText(text);
    }
}
