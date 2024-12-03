package com.example.plantze_application.ui.ecotracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.MainActivity;
import com.example.plantze_application.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.text.SimpleDateFormat;

public class CalendarActivity extends AppCompatActivity {
    private Calendar calendar = Calendar.getInstance();
    private TextView monthYearText, monthlyText, overallText;
    private LinearLayout firstRow, secondRow, thirdRow, fourthRow, fifthRow, currentRow, dateActivitiesLayout;
    private String[] monthNames = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };
    private int month = calendar.get(Calendar.MONTH), year = calendar.get(Calendar.YEAR);
    private String s_month = monthNames[month];
    private double monthEmissions, totalEmissions;

    private void setEmissions(){
        totalEmissions=0;
        monthEmissions=0;
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

                                        try {
                                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                            Calendar calendar = Calendar.getInstance();
                                            Date formattedDate = format.parse(date);
                                            calendar.setTime(formattedDate);
                                            int activityMonth = calendar.get(Calendar.MONTH);
                                            if (Objects.equals(month, activityMonth)) {
                                                monthEmissions+=emission;
                                                Log.d("Firestore", emission+" : "+monthEmissions);
                                                createEntry(date,emission,category,type);
                                            }
                                            totalEmissions+=emission;
                                            Log.d("Firestore", emission+" / "+totalEmissions);
                                        } catch (ParseException e) {
                                            throw new RuntimeException(e);
                                        }
                                    } else {
                                        Log.d("Firestore", "No emissions found for given date");
                                    }
                                }
                                totalEmissions = Double.parseDouble(String.format("%.2f", totalEmissions));
                                monthEmissions = Double.parseDouble(String.format("%.2f", monthEmissions));
                                overallText.setText("Overall: "+totalEmissions+ " kg");
                                monthlyText.setText("This month: "+monthEmissions+" kg");
                            } else {
                                Log.d("Firestore", "No emissions found for this user.");
                            }
                        } else {
                            Log.d("Firestore", "User document does not exist.");
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Failure - error retrieving the document
                        Log.e("Firestore", "Error retrieving emissions", e);
                        Toast.makeText(CalendarActivity.this, "Failed to load emissions", Toast.LENGTH_SHORT).show();
                    });
        }

    }
    private void createEntry(String date_, double emission_, String category_, String type_){
        LinearLayout activityEntry = new LinearLayout(this);
        activityEntry.setOrientation(LinearLayout.VERTICAL);

        TextView emission = new TextView(this);
        emission.setText(emission_ + " kg of CO2");
        emission.setTextSize(20);
        TextView date = new TextView(this);
        date.setText(date_);
        date.setTextSize(16);
        TextView category = new TextView(this);
        category.setText("Category: "+category_);
        category.setTextSize(16);
        TextView type = new TextView(this);
        type.setText("Type: "+type_);
        type.setTextSize(16);

        activityEntry.addView(emission);
        activityEntry.addView(date);
        activityEntry.addView(category);
        activityEntry.addView(type);

        LinearLayout.LayoutParams activityEntryParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        activityEntryParams.setMargins(0, 0, 0, 50);
        activityEntry.setLayoutParams(activityEntryParams);
        dateActivitiesLayout.addView(activityEntry);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_calendar);

        totalEmissions=0;
        monthEmissions=0;

        calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        overallText = findViewById(R.id.overallText);
        monthlyText = findViewById(R.id.monthlyText);
        monthYearText = findViewById(R.id.monthYearText);
        firstRow = findViewById(R.id.firstRow);
        secondRow = findViewById(R.id.secondRow);
        thirdRow = findViewById(R.id.thirdRow);
        fourthRow = findViewById(R.id.fourthRow);
        fifthRow = findViewById(R.id.fifthRow);
        dateActivitiesLayout= findViewById(R.id.dateActivitiesLayout);

        Button prevMonthButton = findViewById(R.id.prevMonthButton);
        Button nextMonthButton = findViewById(R.id.nextMonthButton);

        prevMonthButton.setOnClickListener(v -> navigateMonth(false)); // Move to previous month
        nextMonthButton.setOnClickListener(v -> navigateMonth(true)); // Move to next month

        Button returnHomeButton = findViewById(R.id.returnHomeButton);
        returnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume(){
        super.onResume();
        updateCalendarView();
    }
    private void navigateMonth(boolean forward) {
        if (forward) {
            month++;
            if (month > 11) {
                month = 0;
                year++;
            }
        } else {
            month--;
            if (month < 0) {
                month = 11;
                year--;
            }
        }
        updateCalendarView();
    }
    private void updateCalendarView() {
        s_month = monthNames[month];
        monthYearText.setText(s_month + " " + year);
        int numberOfDays = getDaysInMonth(year, month);
        setCalendarRows(numberOfDays);
        setEmissions();
    }
    private void setCalendarRows(int numberOfDays) {
        firstRow.removeAllViews();
        secondRow.removeAllViews();
        thirdRow.removeAllViews();
        fourthRow.removeAllViews();
        fifthRow.removeAllViews();
        dateActivitiesLayout.removeAllViews();

        for (int i = 1; i <= numberOfDays; i++) {
            String currentDate = s_month + " " + i + ", " + year;

            Button button = new Button(this);
            button.setText(String.valueOf(i));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(140, 120);
            button.setLayoutParams(params);

            button.setOnClickListener(v -> {
                Intent intent = new Intent(CalendarActivity.this, DateActivity.class);
                intent.putExtra("date", currentDate);
                startActivity(intent);
            });

            if (i <= 7) currentRow = firstRow;
            else if (i <= 14) currentRow = secondRow;
            else if (i <= 21) currentRow = thirdRow;
            else if (i <= 28) currentRow = fourthRow;
            else currentRow = fifthRow;

            currentRow.addView(button);
        }
    }
    private int getDaysInMonth(int year, int month) {
        calendar.set(year, month, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
