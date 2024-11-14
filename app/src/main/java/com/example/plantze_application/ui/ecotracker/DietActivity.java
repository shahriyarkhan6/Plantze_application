package com.example.plantze_application.ui.ecotracker;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        dietRadioGroup = findViewById(R.id.dietRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);

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

                resultTextView.setText("Carbon Emission: " + carbonEmission + " CO₂");

                // Check the diet choice and navigate accordingly
                Intent intent;
                if (selectedId == R.id.radioMeatBased) {
                    // If "Meat-based" diet, go to the next question (e.g., BeefActivity)
                    intent = new Intent(DietActivity.this, BeefActivity.class);
                } else {
                    // If "Vegetarian", "Vegan", or "Pescatarian", go directly to FoodWasteActivity
                    intent = new Intent(DietActivity.this, FoodWasteActivity.class);
                }

                // Pass the carbon emission value to the next activity
                intent.putExtra("carbonEmission", carbonEmission);

                // Start the activity
                startActivity(intent);
            }
        });
    }
}
