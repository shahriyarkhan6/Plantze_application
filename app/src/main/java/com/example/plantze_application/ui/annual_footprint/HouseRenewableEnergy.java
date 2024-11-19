package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;
import org.json.JSONArray;
import org.json.JSONObject;


public class HouseRenewableEnergy extends AppCompatActivity {

    private RadioGroup houseRenewableEnergyRadioGroup;
    private Button submitButton;
    private TextView resultTextView;
    private int currentEmission;
    private int currentColumnRow;
    private int currentArrayRow;
    private int energyComparison;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_renewable_energy);

        houseRenewableEnergyRadioGroup = findViewById(R.id.houseRenewableEnergyRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);

        currentEmission = getIntent().getIntExtra("carbonEmission", 0);
        currentArrayRow = getIntent().getIntExtra("ArrayRow", 0);
        currentColumnRow = getIntent().getIntExtra("ColumnRow", 0);
        energyComparison = getIntent().getIntExtra("EnergyComparison", 0);

        resultTextView.setText("Current Carbon Emission: " + currentEmission + " CO₂");

        submitButton.setOnClickListener(v -> {
            int selectedId = houseRenewableEnergyRadioGroup.getCheckedRadioButtonId();

            if (selectedId == R.id.radioPrimarily) {
                currentEmission = currentEmission - 6000;
            } else if (selectedId == R.id.radioPartially) {
                currentEmission = currentEmission - 6000;
            } else if (selectedId == R.id.radioOil) {
                currentEmission = currentEmission;
            }


            try {
                // Read JSON file
                JSONObject jsonObject = JsonReader.readJsonFromFile("HousingData.json");

                // Access the "Housing formula data" array
                JSONArray housingData = jsonObject.getJSONArray("Housing formula data");

                // Access a specific value
                int specificValue = housingData.getJSONArray(currentArrayRow).getInt(currentColumnRow);

                currentEmission += specificValue;


            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

}
