package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class HouseSize extends AppCompatActivity {

    private RadioGroup houseSizeRadioGroup;
    private Button submitButton;
    private int currentArrayRow;
    private double foodCarbonEmission;
    private double transportCarbonEmission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_size);

        houseSizeRadioGroup = findViewById(R.id.houseSizeRadioGroup);
        submitButton = findViewById(R.id.submitButton);

        currentArrayRow = getIntent().getIntExtra("ArrayRow", 0);

        foodCarbonEmission = getIntent().getDoubleExtra("foodCarbonEmission", 0);
        transportCarbonEmission = getIntent().getDoubleExtra("transportCarbonEmission", 0);

        submitButton.setOnClickListener(v -> {
            int selectedId = houseSizeRadioGroup.getCheckedRadioButtonId();

            // Check if an option is selected
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedId == R.id.radioUnder1000) {
                currentArrayRow = currentArrayRow;
            } else if (selectedId == R.id.radio1000to2000) {
                currentArrayRow = currentArrayRow + 4;
            } else if (selectedId == R.id.radioOver2000) {
                currentArrayRow = currentArrayRow + 8;
            }

            Intent intent = new Intent(HouseSize.this, HousePeopleNum.class);
            intent.putExtra("ArrayRow", currentArrayRow);

            intent.putExtra("foodCarbonEmission", foodCarbonEmission);
            intent.putExtra("transportCarbonEmission", transportCarbonEmission);
            startActivity(intent);

        });
    }
}
