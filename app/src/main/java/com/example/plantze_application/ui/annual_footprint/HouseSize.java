package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class HouseSize extends AppCompatActivity {

    private RadioGroup houseSizeRadioGroup;
    private Button submitButton;
    private TextView resultTextView;
    private int housingCurrentEmission;
    private int currentArrayRow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_size);

        houseSizeRadioGroup = findViewById(R.id.houseSizeRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);

        housingCurrentEmission = getIntent().getIntExtra("carbonEmission", 0);
        currentArrayRow = getIntent().getIntExtra("ArrayRow", 0);

        resultTextView.setText("Final value will be displayed after answering all housing questions!");

        submitButton.setOnClickListener(v -> {
            int selectedId = houseSizeRadioGroup.getCheckedRadioButtonId();

            if (selectedId == R.id.radioUnder1000) {
                currentArrayRow = currentArrayRow;
            } else if (selectedId == R.id.radio1000to2000) {
                currentArrayRow = currentArrayRow + 4;
            } else if (selectedId == R.id.radioOver2000) {
                currentArrayRow = currentArrayRow + 8;
            }

            resultTextView.setText("Total Carbon Emission: " + housingCurrentEmission + " CO₂");

            Intent intent = new Intent(HouseSize.this, HousePeopleNum.class);
            intent.putExtra("ArrayRow", currentArrayRow);
            intent.putExtra("carbonEmission", housingCurrentEmission);
            startActivity(intent);

        });
    }
}
