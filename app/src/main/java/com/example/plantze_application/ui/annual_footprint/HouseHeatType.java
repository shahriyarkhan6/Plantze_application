package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class HouseHeatType extends AppCompatActivity {

    private RadioGroup houseHeatTypeRadioGroup;
    private Button submitButton;
    private TextView resultTextView;
    private int currentEmission;
    private int currentColumnRow;
    private int currentArrayRow;
    private int energyComparison;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_heat_type);

        houseHeatTypeRadioGroup = findViewById(R.id.houseHeatTypeRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);

        currentEmission = getIntent().getIntExtra("carbonEmission", 0);
        currentArrayRow = getIntent().getIntExtra("ArrayRow", 0);
        currentColumnRow = getIntent().getIntExtra("ColumnRow", 0);

        resultTextView.setText("Final value will be displayed after answering all housing questions!");
        submitButton.setOnClickListener(v -> {
            int selectedId = houseHeatTypeRadioGroup.getCheckedRadioButtonId();
            energyComparison = 0;

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

            resultTextView.setText("Total Carbon Emission: " + currentEmission + " CO₂");

            Intent intent = new Intent(HouseHeatType.this, HouseHeatWaterType.class);
            intent.putExtra("ArrayRow", currentArrayRow);
            intent.putExtra("ColumnRow", currentColumnRow);
            intent.putExtra("EnergyComparison", energyComparison);
            intent.putExtra("carbonEmission", currentEmission);
            startActivity(intent);

        });
    }
}
