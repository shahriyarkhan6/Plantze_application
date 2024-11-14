package com.example.plantze_application.ui.ecotracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class FoodWasteActivity extends AppCompatActivity {

    private RadioGroup foodWasteRadioGroup;
    private Button submitButton;
    private TextView resultTextView;
    private int currentEmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_waste);

        foodWasteRadioGroup = findViewById(R.id.foodWasteRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);

        currentEmission = getIntent().getIntExtra("carbonEmission", 0);

        resultTextView.setText("Current Carbon Emission: " + currentEmission + " CO₂");

        submitButton.setOnClickListener(v -> {
            int selectedId = foodWasteRadioGroup.getCheckedRadioButtonId();
            double foodWasteEmission = 0.0; // Use double for food waste emission values

            // Assign the emission value based on selection
            if (selectedId == R.id.radioNever) {
                foodWasteEmission = 0.0;
            } else if (selectedId == R.id.radioRarely) {
                foodWasteEmission = 23.4;
            } else if (selectedId == R.id.radioOccasionally) {
                foodWasteEmission = 70.2;
            } else if (selectedId == R.id.radioFrequently) {
                foodWasteEmission = 140.4;
            }

            double totalEmission = (double) currentEmission + foodWasteEmission; // Casting int to double

            resultTextView.setText("Total Carbon Emission: " + totalEmission + " CO₂");
        });
    }
}
