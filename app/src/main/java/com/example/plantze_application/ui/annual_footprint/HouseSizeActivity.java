package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class HouseSizeActivity extends AppCompatActivity {

    private RadioGroup houseSizeRadioGroup;
    private Button submitButton;
    private int currentArrayRow;
    private double foodCarbonEmission;
    private double transportCarbonEmission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //linking to activity_house_size xml file
        setContentView(R.layout.activity_house_size);

        //buttons and options that link to xml files
        houseSizeRadioGroup = findViewById(R.id.houseSizeRadioGroup);
        submitButton = findViewById(R.id.submitButton);

        //bringing over previous category values from intent
        currentArrayRow = getIntent().getIntExtra("ArrayRow", 0);

        foodCarbonEmission = getIntent().getDoubleExtra("foodCarbonEmission", 0);
        transportCarbonEmission = getIntent().getDoubleExtra("transportCarbonEmission", 0);

        //button settings
        submitButton.setOnClickListener(v -> {
            int selectedId = houseSizeRadioGroup.getCheckedRadioButtonId();

            // Check if an option is selected
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                return;
            }

            //setting more specific row # based on selected option
            if (selectedId == R.id.radioUnder1000) {
                currentArrayRow = currentArrayRow;
            } else if (selectedId == R.id.radio1000to2000) {
                currentArrayRow = currentArrayRow + 4;
            } else if (selectedId == R.id.radioOver2000) {
                currentArrayRow = currentArrayRow + 8;
            }

            //linking this question to the next question and bringing relevant data
            Intent intent = new Intent(HouseSizeActivity.this, HousePeopleNumActivity.class);
            intent.putExtra("ArrayRow", currentArrayRow);

            intent.putExtra("foodCarbonEmission", foodCarbonEmission);
            intent.putExtra("transportCarbonEmission", transportCarbonEmission);
            startActivity(intent);

        });
    }
}