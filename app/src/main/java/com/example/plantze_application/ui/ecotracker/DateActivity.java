package com.example.plantze_application.ui.ecotracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

import java.time.LocalDate;

public class DateActivity extends AppCompatActivity {
    private String finalEmission, category, type, day, month, year;
    private Button continueButton;
    private EditText yearInput, monthInput, dayInput;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_date);

        finalEmission = getIntent().getStringExtra("finalEmission");
        category = getIntent().getStringExtra("category");
        type = getIntent().getStringExtra("type");

        yearInput = findViewById(R.id.yearInput);
        monthInput = findViewById(R.id.monthInput);
        dayInput = findViewById(R.id.dayInput);
        continueButton = findViewById(R.id.continueButton);

        @SuppressLint({ "NewApi", "LocalSuppress" })
        LocalDate date = LocalDate.now();
        year = String.valueOf(date.getYear());
        month = String.valueOf(date.getMonthValue());
        day = String.valueOf(date.getDayOfMonth());

        yearInput.setText(year);
        monthInput.setText(month);
        dayInput.setText(day);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year= String.valueOf(yearInput.getText());
                month= String.valueOf(monthInput.getText());
                day= String.valueOf(dayInput.getText());

                if(TextUtils.isEmpty(year) || TextUtils.isEmpty(month) || TextUtils.isEmpty(day)){
                    Toast.makeText(DateActivity.this, "Enter all the inputs", Toast.LENGTH_SHORT).show();
                    return;
                }
                String givenDate = year + "/" + month + "/" + day;
                Intent intent = new Intent(DateActivity.this, ResultActivity.class);
                intent.putExtra("finalEmission", finalEmission);
                intent.putExtra("category", category);
                intent.putExtra("type", type);
                intent.putExtra("date", givenDate);
                startActivity(intent);
            }
        });
    }
}
