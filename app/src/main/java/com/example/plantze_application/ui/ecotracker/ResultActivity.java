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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {
    private Button finishButton;
    private TextView resultText, categoryText, typeText, dateText;
    private String finalEmission, category, type, date;
    private boolean status;
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

        finalEmission = getIntent().getStringExtra("finalEmission");
        category = getIntent().getStringExtra("category");
        type = getIntent().getStringExtra("type");
        date = getIntent().getStringExtra("date");
        status=getIntent().getBooleanExtra("new",true);

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
                // Save the activity to Firestore and move to the next activity
                saveActivityToFirestore();
                Intent intent = new Intent(ResultActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
    }
    private void saveActivityToFirestore() {
        SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userID = sharedPref.getString("USER_ID", null);
        Log.d("ResultActivity", "User ID: " + userID);

        if (userID != null) {
            // Create a map to represent the activity
            Map<String, Object> activity = new HashMap<>();
            activity.put("Category", category);
            activity.put("Type", type);
            activity.put("Date", formatDate(date));
            activity.put("Emission", Double.valueOf(finalEmission));

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userID)
                    .update("Activities", FieldValue.arrayUnion(activity))
                    .addOnSuccessListener(aVoid -> {
                        Log.d("ResultActivity", "Activity added to list");
                        Toast.makeText(ResultActivity.this, "Activity saved successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("ResultActivity", "Error saving activity", e);
                        Toast.makeText(ResultActivity.this, "Failed to save activity", Toast.LENGTH_SHORT).show();
                    });
        }
    }}