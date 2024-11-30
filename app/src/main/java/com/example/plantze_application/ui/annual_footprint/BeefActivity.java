package com.example.plantze_application.ui.annual_footprint;

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

        currentEmission = getIntent().getIntExtra("carbonEmission", 0);

        resultTextView.setText("Current Carbon Emission: " + currentEmission + " kg");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = beefRadioGroup.getCheckedRadioButtonId();
                int beefEmission = 0;

                if (selectedId == R.id.radioDaily) {
                    beefEmission = 2500; // kg
                } else if (selectedId == R.id.radioFrequently) {
                    beefEmission = 1900; // kg
                } else if (selectedId == R.id.radioOccasionally) {
                    beefEmission = 1300; // kg
                } else if (selectedId == R.id.radioNever) {
                    beefEmission = 0; // kg
                }

                currentEmission += beefEmission;


                resultTextView.setText("Total Carbon Emission: " + currentEmission + " CO₂");

                Intent intent = new Intent(BeefActivity.this, PorkActivity.class);
                intent.putExtra("carbonEmission", currentEmission);

                startActivity(intent);
            }
        });
    }
}
