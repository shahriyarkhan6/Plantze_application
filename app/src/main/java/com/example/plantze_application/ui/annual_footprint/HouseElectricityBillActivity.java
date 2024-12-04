package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class HouseElectricityBillActivity extends AppCompatActivity {

    private RadioGroup houseElectricityBillRadioGroup;
    private Button submitButton;
    private int currentColumnRow;
    private int currentArrayRow;
    private double foodCarbonEmission;
    private double transportCarbonEmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //linking to activity_house_type xml file
        setContentView(R.layout.activity_house_electricity_bill);

        //buttons and options that link to xml files
        houseElectricityBillRadioGroup = findViewById(R.id.houseElectricityBillRadioGroup);
        submitButton = findViewById(R.id.submitButton);

        //bringing over previous category values from intent
        currentArrayRow = getIntent().getIntExtra("ArrayRow", 0);

        foodCarbonEmission = getIntent().getDoubleExtra("foodCarbonEmission", 0);
        transportCarbonEmission = getIntent().getDoubleExtra("transportCarbonEmission", 0);

        //button settings
        submitButton.setOnClickListener(v -> {
            int selectedId = houseElectricityBillRadioGroup.getCheckedRadioButtonId();
            currentColumnRow = 0; // Use double for food waste emission values

            // Check if an option is selected
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                return;
            }

            //setting column # based on selected option
            if (selectedId == R.id.radioUnder50) {
                currentColumnRow = 0;
            } else if (selectedId == R.id.radio50to100) {
                currentColumnRow = 5;
            } else if (selectedId == R.id.radio100to150) {
                currentColumnRow = 10;
            } else if (selectedId == R.id.radio150to200) {
                currentColumnRow = 15;
            } else if (selectedId == R.id.radioOver200) {
                currentColumnRow = 20;
            }


            //linking this question to the next question and bringing relevant data
            Intent intent = new Intent(HouseElectricityBillActivity.this, HouseHeatTypeActivity.class);
            intent.putExtra("ArrayRow", currentArrayRow);
            intent.putExtra("ColumnRow", currentColumnRow);

            intent.putExtra("foodCarbonEmission", foodCarbonEmission);
            intent.putExtra("transportCarbonEmission", transportCarbonEmission);

            startActivity(intent);

        });
    }
}
