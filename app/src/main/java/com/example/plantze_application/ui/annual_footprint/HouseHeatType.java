package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class HouseHeatType extends AppCompatActivity {

    private RadioGroup houseHeatTypeRadioGroup;
    private Button submitButton;
    private int currentColumnRow;
    private int currentArrayRow;
    private int energyComparison;
    private double foodCarbonEmission;
    private double transportCarbonEmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_heat_type);

        houseHeatTypeRadioGroup = findViewById(R.id.houseHeatTypeRadioGroup);
        submitButton = findViewById(R.id.submitButton);

        //housingCurrentEmission = getIntent().getIntExtra("carbonEmission", 0);
        currentArrayRow = getIntent().getIntExtra("ArrayRow", 0);
        currentColumnRow = getIntent().getIntExtra("ColumnRow", 0);

        foodCarbonEmission = getIntent().getDoubleExtra("foodCarbonEmission", 0);
        transportCarbonEmission = getIntent().getDoubleExtra("transportCarbonEmission", 0);

        submitButton.setOnClickListener(v -> {
            int selectedId = houseHeatTypeRadioGroup.getCheckedRadioButtonId();
            energyComparison = 0;

            // Check if an option is selected
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedId == R.id.radioNaturalGas) {
                currentColumnRow = currentColumnRow;
                energyComparison = 1;
            } else if (selectedId == R.id.radioElectricity) {
                currentColumnRow = currentColumnRow + 1;
                energyComparison = 2;
            } else if (selectedId == R.id.radioOil) {
                currentColumnRow = currentColumnRow + 2;
                energyComparison = 3;
            } else if (selectedId == R.id.radioPropane) {
                currentColumnRow = currentColumnRow + 3;
                energyComparison = 4;
            } else if (selectedId == R.id.radioWood) {
                currentColumnRow = currentColumnRow + 4;
                energyComparison = 5;
            } else if (selectedId == R.id.radioOther) {
                currentColumnRow = currentColumnRow;
                energyComparison = 6;
            }


            Intent intent = new Intent(HouseHeatType.this, HouseHeatWaterType.class);
            intent.putExtra("ArrayRow", currentArrayRow);
            intent.putExtra("ColumnRow", currentColumnRow);
            intent.putExtra("EnergyComparison", energyComparison);
            //intent.putExtra("carbonEmission", housingCurrentEmission);

            intent.putExtra("foodCarbonEmission", foodCarbonEmission);
            intent.putExtra("transportCarbonEmission", transportCarbonEmission);
            startActivity(intent);

        });
    }
}
