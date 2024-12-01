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

public class ReducedFoodWasteHabitActivity extends AppCompatActivity {

    private TextView foodWasteDaysTextView;
    private Button incrementFoodWasteDaysButton;
    private int foodWasteDays = 0;
    private FirebaseFirestore db;
    private String userID; // Retrieve this from SharedPreferences or authentication

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reduced_food_waste_habit);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        foodWasteDaysTextView = findViewById(R.id.foodWasteDaysTextView);
        incrementFoodWasteDaysButton = findViewById(R.id.incrementFoodWasteDaysButton);

        // Load saved food waste reduction days from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        foodWasteDays = sharedPreferences.getInt("FOOD_WASTE_DAYS", 0); // Default to 0

        // Retrieve user ID from SharedPreferences
        userID = sharedPreferences.getString("USER_ID", null);

        // Update TextView
        updateFoodWasteDaysText();

        // Set click listener for increment button
        incrementFoodWasteDaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increment food waste reduction days
                foodWasteDays++;

                // Update TextView
                updateFoodWasteDaysText();

                // Save updated food waste days in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("FOOD_WASTE_DAYS", foodWasteDays);
                editor.apply();

                // Update Firestore
                if (userID != null) {
                    Map<String, Object> updatedData = new HashMap<>();
                    updatedData.put("foodWasteDays", foodWasteDays);

                    db.collection("users").document(userID)
                            .update(updatedData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ReducedFoodWasteHabitActivity.this, "Food waste reduction days updated in Firestore!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ReducedFoodWasteHabitActivity.this, "Failed to update Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(ReducedFoodWasteHabitActivity.this, "User ID is null. Cannot update Firestore.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateFoodWasteDaysText() {
        String text = "Great job! You’ve reduced food waste for " + foodWasteDays + " days.";
        foodWasteDaysTextView.setText(text);
    }
}
