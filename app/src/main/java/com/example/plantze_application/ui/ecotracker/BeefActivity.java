package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class BeefActivity extends AppCompatActivity {

    private RadioGroup beefRadioGroup;
    private Button submitButton;
    private TextView resultTextView;
    private int currentEmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beef);

        beefRadioGroup = findViewById(R.id.beefRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);

        // Get the carbon emission passed from DietActivity
        currentEmission = getIntent().getIntExtra("carbonEmission", 0);

        // Display the current carbon emission
        resultTextView.setText("Current Carbon Emission: " + currentEmission + " kg");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = beefRadioGroup.getCheckedRadioButtonId();
                int beefEmission = 0;

                // Calculate additional emission based on beef consumption
                if (selectedId == R.id.radioDaily) {
                    beefEmission = 2500; // kg
                } else if (selectedId == R.id.radioFrequently) {
                    beefEmission = 1900; // kg
                } else if (selectedId == R.id.radioOccasionally) {
                    beefEmission = 1300; // kg
                } else if (selectedId == R.id.radioNever) {
                    beefEmission = 0; // kg
                }

                // Add the beef emission to the existing carbon emission
                currentEmission += beefEmission;

                // Update the display with the new total emission
                resultTextView.setText("Total Carbon Emission: " + currentEmission + " kg");

                // Pass the updated carbon emission to the next activity (PorkActivity)
                Intent intent = new Intent(BeefActivity.this, PorkActivity.class);
                intent.putExtra("carbonEmission", currentEmission);
                startActivity(intent);
            }
        });
    }
}
