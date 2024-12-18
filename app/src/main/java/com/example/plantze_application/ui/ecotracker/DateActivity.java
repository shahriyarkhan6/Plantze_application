package com.example.plantze_application.ui.ecotracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.MainActivity;
import com.example.plantze_application.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.Objects;


public class DateActivity extends AppCompatActivity {
    private String s_date;
    private Button createButton;
    private TextView dateText;
    private LinearLayout dateActivitiesLayout;
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

    private void deleteActivity(String id_){
        SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userID = sharedPref.getString("USER_ID", null);
        if (userID != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userID).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            List<Map<String, Object>> activities = (List<Map<String, Object>>) documentSnapshot.get("Activities");

                            if (activities != null && !activities.isEmpty()) {
                                List<Map<String, Object>> updatedActivities = new ArrayList<>();

                                for (Map<String, Object> activity : activities) {
                                    String activityId = (String) activity.get("ActivityID"); // Get ActivityID for comparison
                                    if (!id_.equals(activityId)) {
                                        updatedActivities.add(activity);  // Keep the activity if the ID does not match
                                    }
                                }

                                if (updatedActivities.size() < activities.size()) {
                                    db.collection("users").document(userID)
                                            .update("Activities", updatedActivities)
                                            .addOnSuccessListener(aVoid -> {
                                                Log.d("Firestore", "Activity deleted successfully.");
                                                Toast.makeText(DateActivity.this, "Activity deleted!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(DateActivity.this, CalendarActivity.class);
                                                startActivity(intent);
                                            })
                                            .addOnFailureListener(e -> {
                                                Log.e("Firestore", "Error deleting activity", e);
                                                Toast.makeText(DateActivity.this, "Failed to delete activity", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(DateActivity.this, CalendarActivity.class);
                                                startActivity(intent);
                                            });
                                } else {
                                    Log.d("Firestore", "No matching activity found to delete.");
                                    Toast.makeText(DateActivity.this, "Activity not found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("Firestore", "Error retrieving activities", e);
                        Toast.makeText(DateActivity.this, "Failed to retrieve activities", Toast.LENGTH_SHORT).show();
                    });
        }

    }
    private void createEntry(String id_, double emission_, String category_, String type_){
        LinearLayout activityEntry = new LinearLayout(this);
        activityEntry.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout textEntry = new LinearLayout(this);
        textEntry.setOrientation(LinearLayout.VERTICAL);

        TextView emission = new TextView(this);
        emission.setText(emission_ + " kg of CO2");
        emission.setTextSize(20);
        TextView category = new TextView(this);
        category.setText("Category: "+category_);
        category.setTextSize(16);
        TextView type = new TextView(this);
        type.setText("Type: "+type_);
        type.setTextSize(16);

        textEntry.addView(emission);
        textEntry.addView(category);
        textEntry.addView(type);

        LinearLayout buttonRow = new LinearLayout(this);
        buttonRow.setOrientation(LinearLayout.HORIZONTAL);

        Space space = new Space(this);

        Button edit = new Button(this);
        edit.setText("Edit");
        Button delete = new Button(this);
        delete.setText("Delete");

        edit.setOnClickListener(v -> {
            Intent intent = new Intent(DateActivity.this, CategoryActivity.class);
            intent.putExtra("activityId",id_);
            intent.putExtra("date",s_date);
            startActivity(intent);
        });

        delete.setOnClickListener(v-> {
            deleteActivity(id_);
        });

        buttonRow.addView(edit);
        buttonRow.addView(delete);

        activityEntry.addView(textEntry);

        LinearLayout.LayoutParams spaceParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
        space.setLayoutParams(spaceParams);
        activityEntry.addView(space);

        activityEntry.addView(buttonRow);

        LinearLayout.LayoutParams activityEntryParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        activityEntryParams.setMargins(0, 0, 0, 50);
        activityEntry.setLayoutParams(activityEntryParams);

        dateActivitiesLayout.addView(activityEntry);
    }

    private void displayEntries(String date_){
        SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userID = sharedPref.getString("USER_ID", null);
        Log.d("ResultActivity", "User ID: " + userID);

        if (userID != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userID).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            List<Map<String, Object>> activities = (List<Map<String, Object>>) documentSnapshot.get("Activities");
                            if (activities != null && !activities.isEmpty()) {
                                for (Map<String, Object> activity : activities) {
                                    String category = (String) activity.get("Category");
                                    String type = (String) activity.get("Type");
                                    String date = (String) activity.get("Date");
                                    String activityId = (String) activity.get("ActivityID");

                                    Object emissionObj = activity.get("Emission");
                                    Double emission = null;

                                    if (emissionObj instanceof Long) {
                                        emission = ((Long) emissionObj).doubleValue();
                                    } else if (emissionObj instanceof Double) {
                                        emission = (Double) emissionObj;
                                    }

                                    if (emission != null) {
                                        Log.d("Firestore", "Activity: "+activityId +": "+ type + ", " + category + ", " + emission + ", " + date);
                                        if (Objects.equals(date, date_))
                                            createEntry(activityId,emission,category,type);
                                    } else {
                                        Log.d("Firestore", "No activities found for given date");
                                    }
                                }
                            } else {
                                Log.d("Firestore", "No activities found for this user.");
                            }
                        } else {
                            Log.d("Firestore", "User document does not exist.");
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Failure - error retrieving the document
                        Log.e("Firestore", "Error retrieving activities", e);
                        Toast.makeText(DateActivity.this, "Failed to load activities", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_date);

        s_date = getIntent().getStringExtra("date");

        createButton = findViewById(R.id.createButton);
        dateText = findViewById(R.id.dateText);
        dateActivitiesLayout= findViewById(R.id.dateActivitiesLayout);

        dateText.setText(s_date);
        String formattedDate=formatDate(s_date);

        displayEntries(formattedDate);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DateActivity.this, CategoryActivity.class);
                intent.putExtra("date",s_date);
                intent.putExtra("activityId", "new");
                startActivity(intent);
            }
        });

        Button returnHomeButton = findViewById(R.id.returnHomeButton);
        returnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
