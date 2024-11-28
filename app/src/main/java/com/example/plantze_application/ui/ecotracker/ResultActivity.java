package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class ResultActivity extends AppCompatActivity {
    private Button finishButton;
    private TextView resultText, categoryText, typeText, dateText;
    private String finalEmission, category, type, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_results);

        finalEmission = getIntent().getStringExtra("finalEmission");
        category = getIntent().getStringExtra("category");
        type = getIntent().getStringExtra("type");
        date = getIntent().getStringExtra("date");

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
                Intent intent = new Intent(ResultActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });
    }

}
