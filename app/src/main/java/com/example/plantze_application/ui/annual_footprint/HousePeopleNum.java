package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class HousePeopleNum extends AppCompatActivity {

    private RadioGroup housePeopleNumRadioGroup;
    private Button submitButton;
    private TextView resultTextView;
    private int housingCurrentEmission;
    private int currentArrayRow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_people_num);

        housePeopleNumRadioGroup = findViewById(R.id.housePeopleNumRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);

        housingCurrentEmission = getIntent().getIntExtra("carbonEmission", 0);
        currentArrayRow = getIntent().getIntExtra("ArrayRow", 0);

        resultTextView.setText("Final value will be displayed after answering all housing questions!");

        submitButton.setOnClickListener(v -> {
            int selectedId = housePeopleNumRadioGroup.getCheckedRadioButtonId();

            if (selectedId == R.id.radio1) {
                currentArrayRow = currentArrayRow + 0;
            } else if (selectedId == R.id.radio2) {
                currentArrayRow = currentArrayRow + 1;
            } else if (selectedId == R.id.radio3to4) {
                currentArrayRow = currentArrayRow + 2;
            } else if (selectedId == R.id.radio5ormore) {
                currentArrayRow = currentArrayRow + 3;
            }

            resultTextView.setText("Total Carbon Emission: " + housingCurrentEmission + " CO₂");

            Intent intent = new Intent(HousePeopleNum.this, HouseElectricityBill.class);
            intent.putExtra("ArrayRow", currentArrayRow);
            intent.putExtra("carbonEmission", housingCurrentEmission);
            startActivity(intent);

        });
    }
}
