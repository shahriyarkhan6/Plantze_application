package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateActivity extends AppCompatActivity {
    private String s_date;
    private Button createButton;
    private TextView dateText;
    private LinearLayout dateActivitiesLayout;
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
    private void createLog(String emission_, String category_, String type_){
        // Create the outer layout for the log (Horizontal orientation)
        LinearLayout activityLog = new LinearLayout(this);
        activityLog.setOrientation(LinearLayout.HORIZONTAL);

        // Create the textLog layout (Vertical orientation)
        LinearLayout textLog = new LinearLayout(this);
        textLog.setOrientation(LinearLayout.VERTICAL);

        // Create the TextViews
        TextView emission = new TextView(this);
        emission.setText(emission_ + " kg of CO2");
        emission.setTextSize(20);
        TextView category = new TextView(this);
        category.setText("Category: "+category_);
        category.setTextSize(16);
        TextView type = new TextView(this);
        type.setText("Type: "+type_);
        type.setTextSize(16);

        // Add TextViews to the textLog layout
        textLog.addView(emission);
        textLog.addView(category);
        textLog.addView(type);

        // Create the button row layout (Horizontal orientation)
        LinearLayout buttonRow = new LinearLayout(this);
        buttonRow.setOrientation(LinearLayout.HORIZONTAL);

        // Create the Space to flex (allowing buttons to align right)
        Space space = new Space(this);

        // Create Edit and Delete buttons
        Button edit = new Button(this);
        edit.setText("Edit");
        Button delete = new Button(this);
        delete.setText("Delete");

        edit.setOnClickListener(v -> {
            Intent intent = new Intent(DateActivity.this, CategoryActivity.class);
            intent.putExtra("new", false);
            intent.putExtra("date",s_date);
            startActivity(intent);
        });

        // Add buttons to the button row
        buttonRow.addView(edit);
        buttonRow.addView(delete);

        // Add the textLog, space, and buttonRow to the activityLog
        activityLog.addView(textLog);

        // Set flex behavior for the space (let it grow to take up available space)
        LinearLayout.LayoutParams spaceParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
        space.setLayoutParams(spaceParams);
        activityLog.addView(space);

        activityLog.addView(buttonRow);

        // Set marginBottom to 10 for the activityLog
        LinearLayout.LayoutParams activityLogParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        activityLogParams.setMargins(0, 0, 0, 50);  // Margin at the bottom
        activityLog.setLayoutParams(activityLogParams);

        // Add the activityLog to the dateActivitiesLayout (the parent layout)
        dateActivitiesLayout.addView(activityLog);
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

        for (int i = 1; i <= 10; i++){
            createLog("10", "es","fd");
        }

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DateActivity.this, CategoryActivity.class);
                intent.putExtra("date",s_date);
                intent.putExtra("new", true);
                startActivity(intent);
            }
        });


    }
}
