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
import com.google.firebase.firestore.SetOptions;


import java.util.HashMap;
import java.util.Map;

public class WalkingHabitActivity extends AppCompatActivity {


    private TextView walkingDaysTextView;
    private Button incrementDaysButton;
    private int walkingDays = 0;
    private FirebaseFirestore db;
    private String userID; // Assuming you have a way to get the user ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking_habit);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        walkingDaysTextView = findViewById(R.id.walkingDaysTextView);
        incrementDaysButton = findViewById(R.id.incrementDaysButton);

        // Load saved walking days from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        walkingDays = sharedPreferences.getInt("WALKING_DAYS", 0); // Default to 0

        // Retrieve user ID from SharedPreferences or authentication
        userID = sharedPreferences.getString("USER_ID", null);

        // Update TextView
        updateWalkingDaysText();

        // Set click listener for increment button
        incrementDaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Increment walking days
                walkingDays++;

                // Update TextView
                updateWalkingDaysText();

                // Save updated walking days in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("WALKING_DAYS", walkingDays);
                editor.apply();

                // Update Firestore
                if (userID != null) {
                    Map<String, Object> updatedData = new HashMap<>();
                    updatedData.put("walkingDays", walkingDays);

                    db.collection("users").document(userID)
                            .set(updatedData, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(WalkingHabitActivity.this, "Walking days updated in Firestore!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(WalkingHabitActivity.this, "Failed to update Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(WalkingHabitActivity.this, "User ID is null. Cannot update Firestore.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateWalkingDaysText() {
        String text = "You have spent " + walkingDays + " days walking instead of driving.";
        walkingDaysTextView.setText(text);
    }
}
