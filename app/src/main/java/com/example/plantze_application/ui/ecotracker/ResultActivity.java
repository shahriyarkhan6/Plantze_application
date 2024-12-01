package com.example.plantze_application.ui.ecotracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ResultActivity extends AppCompatActivity {
    private Button finishButton;
    private TextView resultText, categoryText, typeText, dateText;
    private String finalEmission, category, type, date, activityId;
    private double emission;
    public static String formatDate(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("MMMM d, yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_results);

        DecimalFormat df = new DecimalFormat("#.##");
        finalEmission = getIntent().getStringExtra("finalEmission");
        emission = Double.parseDouble(finalEmission);
        emission = Double.parseDouble(df.format(emission));
        finalEmission=String.valueOf(emission);
        category = getIntent().getStringExtra("category");
        type = getIntent().getStringExtra("type");
        date = getIntent().getStringExtra("date");
        activityId=getIntent().getStringExtra("activityId");

        Log.d("ResultActivity", "Activity: " + activityId);

        resultText = findViewById(R.id.resultText);
        categoryText = findViewById(R.id.categoryText);
        typeText = findViewById(R.id.typeText);
        dateText = findViewById(R.id.dateText);
        finishButton = findViewById(R.id.finishButton);

        resultText.setText("Total: " + finalEmission + " kg");
        categoryText.setText("Category: " + category);
        typeText.setText("Type: " + type);
        dateText.setText("Date: " + date);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(activityId, "new"))
                    addActivityToFirestore();
                else
                    updateActivity();
                Intent intent = new Intent(ResultActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
    }
    private void updateActivity(){
        SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userID = sharedPref.getString("USER_ID", null);
        Log.d("ResultActivity", "User ID: " + userID);

        if (userID != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userID).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            // Retrieve the activities array from the document
                            List<Map<String, Object>> activities = (List<Map<String, Object>>) documentSnapshot.get("Activities");

                            if (activities != null && !activities.isEmpty()) {
                                // Iterate through the activities to find the one with the matching ID
                                for (int i = 0; i < activities.size(); i++) {
                                    Map<String, Object> activity = activities.get(i);

                                    // Check if the ActivityID matches the one we want to update
                                    if (activityId.equals(activity.get("ActivityID"))) {
                                        // Update the fields of the activity
                                        activity.put("Category", category);
                                        activity.put("Type", type);
                                        activity.put("Emission", emission);

                                        // Once updated, replace the activity in the list
                                        activities.set(i, activity);

                                        // Update the entire Activities array in Firestore
                                        db.collection("users").document(userID)
                                                .update("Activities", activities)
                                                .addOnSuccessListener(aVoid -> {
                                                    Log.d("Firestore", "Activity updated successfully.");
                                                    Toast.makeText(ResultActivity.this, "Activity updated!", Toast.LENGTH_SHORT).show();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Log.e("Firestore", "Error updating activity", e);
                                                    Toast.makeText(ResultActivity.this, "Failed to update activity", Toast.LENGTH_SHORT).show();
                                                });

                                        break; // Stop once we find and update the activity
                                    }
                                }
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("Firestore", "Error retrieving activities", e);
                        Toast.makeText(ResultActivity.this, "Failed to retrieve activities", Toast.LENGTH_SHORT).show();
                    });
        }


    }
    private void addActivityToFirestore() {
        SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userID = sharedPref.getString("USER_ID", null);
        Log.d("ResultActivity", "User ID: " + userID);

        if (userID != null) {
            // Create a map to represent the activity
            Map<String, Object> activity = new HashMap<>();
            activityId = UUID.randomUUID().toString();
            activity.put("ActivityID", activityId);
            activity.put("Category", category);
            activity.put("Type", type);
            activity.put("Date", formatDate(date));
            activity.put("Emission", emission);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userID)
                    .update("Activities", FieldValue.arrayUnion(activity))
                    .addOnSuccessListener(aVoid -> {
                        Log.d("ResultActivity", "Activity added to list: "+activityId);
                        Toast.makeText(ResultActivity.this, "Activity saved successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("ResultActivity", "Error saving activity", e);
                        Toast.makeText(ResultActivity.this, "Failed to save activity", Toast.LENGTH_SHORT).show();
                    });
        }
    }}
