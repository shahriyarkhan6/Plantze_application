package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class HouseHeatWaterType extends AppCompatActivity {

    private RadioGroup houseHeatWaterTypeRadioGroup;
    private Button submitButton;
    private int housingCurrentEmission;
    private int currentColumnRow;
    private int currentArrayRow;
    private int energyComparison;
    private double foodCarbonEmission;
    public double transportCarbonEmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_heat_water_type);

        houseHeatWaterTypeRadioGroup = findViewById(R.id.houseHeatWaterTypeRadioGroup);
        submitButton = findViewById(R.id.submitButton);

        //housingCurrentEmission = getIntent().getIntExtra("carbonEmission", 0);
        currentArrayRow = getIntent().getIntExtra("ArrayRow", 0);
        currentColumnRow = getIntent().getIntExtra("ColumnRow", 0);
        energyComparison = getIntent().getIntExtra("EnergyComparison", 0);

        foodCarbonEmission = getIntent().getDoubleExtra("foodCarbonEmission", 0);
        transportCarbonEmission = getIntent().getDoubleExtra("transportCarbonEmission", 0);

        submitButton.setOnClickListener(v -> {
            int selectedId = houseHeatWaterTypeRadioGroup.getCheckedRadioButtonId();

            // Check if an option is selected
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedId == R.id.radioNaturalGas && energyComparison != 1) {
                housingCurrentEmission = housingCurrentEmission + 233;
            } else if (selectedId == R.id.radioElectricity && energyComparison != 2) {
                housingCurrentEmission = housingCurrentEmission + 233;
            } else if (selectedId == R.id.radioOil && energyComparison != 3) {
                housingCurrentEmission = housingCurrentEmission + 233;
            } else if (selectedId == R.id.radioPropane && energyComparison != 4) {
                housingCurrentEmission = housingCurrentEmission + 233;
            } else if (selectedId == R.id.radioWood && energyComparison != 5) {
                housingCurrentEmission = housingCurrentEmission + 233;
            } else if (selectedId == R.id.radioOther && energyComparison != 6) {
                housingCurrentEmission = housingCurrentEmission + 233;
            }

            Intent intent = new Intent(HouseHeatWaterType.this, HouseRenewableEnergy.class);
            intent.putExtra("ArrayRow", currentArrayRow);
            intent.putExtra("ColumnRow", currentColumnRow);
            intent.putExtra("EnergyComparison", energyComparison);
            intent.putExtra("carbonEmission", housingCurrentEmission);

            intent.putExtra("foodCarbonEmission", foodCarbonEmission);
            intent.putExtra("transportCarbonEmission", transportCarbonEmission);

            startActivity(intent);

        });
    }
}
