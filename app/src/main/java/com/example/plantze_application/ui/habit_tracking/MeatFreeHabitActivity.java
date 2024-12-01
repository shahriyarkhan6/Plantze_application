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

public class MeatFreeHabitActivity extends AppCompatActivity {

    private TextView meatFreeDaysTextView;
    private Button incrementMeatFreeDaysButton;
    private int meatFreeDays = 0;
    private FirebaseFirestore db;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meat_free_habit);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        meatFreeDaysTextView = findViewById(R.id.meatFreeDaysTextView);
        incrementMeatFreeDaysButton = findViewById(R.id.incrementMeatFreeDaysButton);

        // Load saved meat-free days from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        meatFreeDays = sharedPreferences.getInt("MEAT_FREE_DAYS", 0); // Default to 0

        // Retrieve user ID from SharedPreferences or authentication
        userID = sharedPreferences.getString("USER_ID", null);

        // Update the TextView
        updateMeatFreeDaysText();

        // Set click listener for increment button
        incrementMeatFreeDaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increment meat-free days
                meatFreeDays++;

                // Update TextView
                updateMeatFreeDaysText();

                // Save updated meat-free days in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("MEAT_FREE_DAYS", meatFreeDays);
                editor.apply();

                // Update Firestore
                if (userID != null) {
                    Map<String, Object> updatedData = new HashMap<>();
                    updatedData.put("meatFreeDays", meatFreeDays);

                    db.collection("users").document(userID)
                            .update(updatedData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MeatFreeHabitActivity.this, "Meat-free days updated in Firestore!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MeatFreeHabitActivity.this, "Failed to update Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(MeatFreeHabitActivity.this, "User ID is null. Cannot update Firestore.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateMeatFreeDaysText() {
        String text = "Congratulations! You have chosen plant-based meals for " + meatFreeDays + " days.";
        meatFreeDaysTextView.setText(text);
    }
}
