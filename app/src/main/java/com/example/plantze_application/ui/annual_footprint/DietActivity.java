package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class DietActivity extends AppCompatActivity {

    private RadioGroup dietRadioGroup;
    private Button submitButton;
    private TextView resultTextView;
    private double transportCarbonEmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        dietRadioGroup = findViewById(R.id.dietRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        //resultTextView = findViewById(R.id.resultTextView);

        transportCarbonEmission = getIntent().getDoubleExtra("transportCarbonEmission", 0);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = dietRadioGroup.getCheckedRadioButtonId();
                int carbonEmission = 0;

                if (selectedId == R.id.radioVegetarian) {
                    carbonEmission = 1000; // kg
                } else if (selectedId == R.id.radioVegan) {
                    carbonEmission = 500; // kg
                } else if (selectedId == R.id.radioPescatarian) {
                    carbonEmission = 1500; // kg
                } else if (selectedId == R.id.radioMeatBased) {
                    carbonEmission = 0; // kg
                }

                //resultTextView.setText("Carbon Emission: " + carbonEmission + " CO₂");

                Intent intent;
                if (selectedId == R.id.radioMeatBased) {
                    intent = new Intent(DietActivity.this, BeefActivity.class);
                } else {
                    intent = new Intent(DietActivity.this, FoodWasteActivity.class);
                }

                intent.putExtra("carbonEmission", carbonEmission);
                intent.putExtra("transportCarbonEmission", transportCarbonEmission);


                startActivity(intent);
            }
        });
    }
}
