package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class HousePeopleNum extends AppCompatActivity {

    private RadioGroup housePeopleNumRadioGroup;
    private Button submitButton;
    private int currentArrayRow;
    private double foodCarbonEmission;
    private double transportCarbonEmission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_people_num);

        housePeopleNumRadioGroup = findViewById(R.id.housePeopleNumRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        currentArrayRow = getIntent().getIntExtra("ArrayRow", 0);

        foodCarbonEmission = getIntent().getDoubleExtra("foodCarbonEmission", 0);
        transportCarbonEmission = getIntent().getDoubleExtra("transportCarbonEmission", 0);

        submitButton.setOnClickListener(v -> {
            int selectedId = housePeopleNumRadioGroup.getCheckedRadioButtonId();

            // Check if an option is selected
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedId == R.id.radio1) {
                currentArrayRow = currentArrayRow + 0;
            } else if (selectedId == R.id.radio2) {
                currentArrayRow = currentArrayRow + 1;
            } else if (selectedId == R.id.radio3to4) {
                currentArrayRow = currentArrayRow + 2;
            } else if (selectedId == R.id.radio5ormore) {
                currentArrayRow = currentArrayRow + 3;
            }


            Intent intent = new Intent(HousePeopleNum.this, HouseElectricityBill.class);
            intent.putExtra("ArrayRow", currentArrayRow);
            //intent.putExtra("carbonEmission", housingCurrentEmission);

            intent.putExtra("foodCarbonEmission", foodCarbonEmission);
            intent.putExtra("transportCarbonEmission", transportCarbonEmission);
            startActivity(intent);

        });
    }
}
