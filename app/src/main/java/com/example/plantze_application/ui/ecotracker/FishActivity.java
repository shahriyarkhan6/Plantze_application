package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class FishActivity extends AppCompatActivity {

    private RadioGroup fishRadioGroup;
    private Button submitButton;
    private TextView resultTextView;
    private int currentEmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish);

        fishRadioGroup = findViewById(R.id.fishRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);

        currentEmission = getIntent().getIntExtra("carbonEmission", 0);

        resultTextView.setText("Current Carbon Emission: " + currentEmission + " kg");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = fishRadioGroup.getCheckedRadioButtonId();
                int fishEmission = 0;

                if (selectedId == R.id.radioDaily) {
                    fishEmission = 2100; // kg
                } else if (selectedId == R.id.radioFrequently) {
                    fishEmission = 1300; // kg
                } else if (selectedId == R.id.radioOccasionally) {
                    fishEmission = 450; // kg
                } else if (selectedId == R.id.radioNever) {
                    fishEmission = 0; // kg
                }

                currentEmission += fishEmission;

                resultTextView.setText("Total Carbon Emission: " + currentEmission + " CO₂");

                Intent intent = new Intent(FishActivity.this, FoodWasteActivity.class);
                intent.putExtra("carbonEmission", currentEmission);
                startActivity(intent);
            }
        });
    }
}
