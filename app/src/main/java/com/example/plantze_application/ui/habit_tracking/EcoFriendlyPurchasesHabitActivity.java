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

public class EcoFriendlyPurchasesHabitActivity extends AppCompatActivity {

    private TextView ecoPurchasesTextView;
    private Button incrementEcoPurchasesButton;
    private int ecoFriendlyPurchases = 0;
    private FirebaseFirestore db;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_friendly_purchases_habit);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        ecoPurchasesTextView = findViewById(R.id.ecoPurchasesTextView);
        incrementEcoPurchasesButton = findViewById(R.id.incrementEcoPurchasesButton);

        // Load saved eco-friendly purchases count from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        ecoFriendlyPurchases = sharedPreferences.getInt("ECO_PURCHASES", 0);

        // Retrieve user ID from SharedPreferences or authentication
        userID = sharedPreferences.getString("USER_ID", null);

        // Update TextView
        updateEcoPurchasesText();

        // Set click listener for the button
        incrementEcoPurchasesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increment eco-friendly purchases
                ecoFriendlyPurchases++;

                // Update TextView
                updateEcoPurchasesText();

                // Save updated count in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("ECO_PURCHASES", ecoFriendlyPurchases);
                editor.apply();

                // Update Firestore
                if (userID != null) {
                    Map<String, Object> updatedData = new HashMap<>();
                    updatedData.put("ecoFriendlyPurchases", ecoFriendlyPurchases);

                    db.collection("users").document(userID)
                            .update(updatedData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(EcoFriendlyPurchasesHabitActivity.this, "Eco-friendly purchases updated in Firestore!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EcoFriendlyPurchasesHabitActivity.this, "Failed to update Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(EcoFriendlyPurchasesHabitActivity.this, "User ID is null. Cannot update Firestore.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateEcoPurchasesText() {
        String text = "You’ve made " + ecoFriendlyPurchases + " eco-friendly purchases this year.";
        ecoPurchasesTextView.setText(text);
    }
}
