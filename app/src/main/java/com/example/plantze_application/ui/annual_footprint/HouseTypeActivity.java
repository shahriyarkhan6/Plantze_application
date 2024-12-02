package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class HouseTypeActivity extends AppCompatActivity {

    private RadioGroup houseTypeRadioGroup;
    private Button submitButton;
    private int currentArrayRow;
    private double foodCarbonEmission;
    private double transportCarbonEmission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //linking to activity_house_type xml file
        setContentView(R.layout.activity_house_type);

        //buttons and options that link to xml files
        houseTypeRadioGroup = findViewById(R.id.houseTypeRadioGroup);
        submitButton = findViewById(R.id.submitButton);

        //bringing over previous category values from intent
        foodCarbonEmission = getIntent().getDoubleExtra("foodCarbonEmission", 0);
        transportCarbonEmission = getIntent().getDoubleExtra("transportCarbonEmission", 0);

        //button settings
        submitButton.setOnClickListener(v -> {
            int selectedId = houseTypeRadioGroup.getCheckedRadioButtonId();
            currentArrayRow = 0;

            // Check if an option is selected
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                return;
            }

            //setting row # based on selected option
            if (selectedId == R.id.radioDetached) {
                currentArrayRow = 0;
            } else if (selectedId == R.id.radioSemi) {
                currentArrayRow = 12;
            } else if (selectedId == R.id.radioTownhouse) {
                currentArrayRow = 24;
            } else if (selectedId == R.id.radioCondo) {
                currentArrayRow = 36;
            } else if (selectedId == R.id.radioOther) {
                currentArrayRow = 24;
            }

            //linking this question to the next question and bringing relevant data
            Intent intent = new Intent(HouseTypeActivity.this, HouseSize.class);
            intent.putExtra("ArrayRow", currentArrayRow);

            intent.putExtra("foodCarbonEmission", foodCarbonEmission);
            intent.putExtra("transportCarbonEmission", transportCarbonEmission);
            startActivity(intent);


        });
    }
}
