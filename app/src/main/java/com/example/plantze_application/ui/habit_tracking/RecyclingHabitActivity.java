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

public class RecyclingHabitActivity extends AppCompatActivity {

    private TextView recyclingDaysTextView;
    private Button incrementRecyclingDaysButton;
    private int recyclingDays = 0;
    private FirebaseFirestore db;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycling_habit);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        recyclingDaysTextView = findViewById(R.id.recyclingDaysTextView);
        incrementRecyclingDaysButton = findViewById(R.id.incrementRecyclingDaysButton);

        // Load saved recycling days from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        recyclingDays = sharedPreferences.getInt("RECYCLING_DAYS", 0); // Default to 0

        // Retrieve user ID from SharedPreferences
        userID = sharedPreferences.getString("USER_ID", null);

        // Update TextView
        updateRecyclingDaysText();

        // Set click listener for increment button
        incrementRecyclingDaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increment recycling days
                recyclingDays++;

                // Update TextView
                updateRecyclingDaysText();

                // Save updated recycling days in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("RECYCLING_DAYS", recyclingDays);
                editor.apply();

                // Update Firestore
                if (userID != null) {
                    Map<String, Object> updatedData = new HashMap<>();
                    updatedData.put("recyclingDays", recyclingDays);

                    db.collection("users").document(userID)
                            .update(updatedData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RecyclingHabitActivity.this, "Recycling days updated in Firestore!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RecyclingHabitActivity.this, "Failed to update Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(RecyclingHabitActivity.this, "User ID is null. Cannot update Firestore.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateRecyclingDaysText() {
        String text = "You’ve recycled " + recyclingDays + " times this year.";
        recyclingDaysTextView.setText(text);
    }
}
