package com.example.plantze_application.ui.annual_footprint;

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
    private int currentEmission;
    private double transportCarbonEmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish);

        fishRadioGroup = findViewById(R.id.fishRadioGroup);
        submitButton = findViewById(R.id.submitButton);

        currentEmission = getIntent().getIntExtra("carbonEmission", 0);

        transportCarbonEmission = getIntent().getDoubleExtra("transportCarbonEmission", 0);

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

                Intent intent = new Intent(FishActivity.this, FoodWasteActivity.class);
                intent.putExtra("carbonEmission", currentEmission);

                intent.putExtra("transportCarbonEmission", transportCarbonEmission);
                startActivity(intent);
            }
        });
    }
}
