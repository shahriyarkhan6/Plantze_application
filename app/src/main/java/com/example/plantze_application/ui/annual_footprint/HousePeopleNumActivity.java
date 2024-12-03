package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class HousePeopleNumActivity extends AppCompatActivity {

    private RadioGroup housePeopleNumRadioGroup;
    private Button submitButton;
    private int currentArrayRow;
    private double foodCarbonEmission;
    private double transportCarbonEmission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //linking to activity_house_type xml file
        setContentView(R.layout.activity_house_people_num);

        //buttons and options that link to xml files
        housePeopleNumRadioGroup = findViewById(R.id.housePeopleNumRadioGroup);
        submitButton = findViewById(R.id.submitButton);

        //bringing over previous category values from intent
        currentArrayRow = getIntent().getIntExtra("ArrayRow", 0);

        foodCarbonEmission = getIntent().getDoubleExtra("foodCarbonEmission", 0);
        transportCarbonEmission = getIntent().getDoubleExtra("transportCarbonEmission", 0);

        //button settings
        submitButton.setOnClickListener(v -> {
            int selectedId = housePeopleNumRadioGroup.getCheckedRadioButtonId();

            // Check if an option is selected
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                return;
            }

            //setting even more specific row # based on selected option
            if (selectedId == R.id.radio1) {
                currentArrayRow = currentArrayRow + 0;
            } else if (selectedId == R.id.radio2) {
                currentArrayRow = currentArrayRow + 1;
            } else if (selectedId == R.id.radio3to4) {
                currentArrayRow = currentArrayRow + 2;
            } else if (selectedId == R.id.radio5ormore) {
                currentArrayRow = currentArrayRow + 3;
            }

            //linking this question to the next question and bringing relevant data
            Intent intent = new Intent(HousePeopleNumActivity.this, HouseElectricityBillActivity.class);
            intent.putExtra("ArrayRow", currentArrayRow);
            //intent.putExtra("carbonEmission", housingCurrentEmission);

            intent.putExtra("foodCarbonEmission", foodCarbonEmission);
            intent.putExtra("transportCarbonEmission", transportCarbonEmission);
            startActivity(intent);

        });
    }
}
