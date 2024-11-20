package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class PorkActivity extends AppCompatActivity {

    private RadioGroup porkRadioGroup;
    private Button submitButton;
    private TextView resultTextView;
    private int currentEmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pork);

        porkRadioGroup = findViewById(R.id.porkRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);

        currentEmission = getIntent().getIntExtra("carbonEmission", 0);

        resultTextView.setText("Current Carbon Emission: " + currentEmission + " kg");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = porkRadioGroup.getCheckedRadioButtonId();
                int porkEmission = 0;

                if (selectedId == R.id.radioDaily) {
                    porkEmission = 1450; // kg
                } else if (selectedId == R.id.radioFrequently) {
                    porkEmission = 860; // kg
                } else if (selectedId == R.id.radioOccasionally) {
                    porkEmission = 450; // kg
                } else if (selectedId == R.id.radioNever) {
                    porkEmission = 0; // kg
                }

                currentEmission += porkEmission;

                resultTextView.setText("Total Carbon Emission: " + currentEmission + " kg");

                Intent intent = new Intent(PorkActivity.this, ChickenActivity.class);
                intent.putExtra("carbonEmission", currentEmission);
                startActivity(intent);
            }
        });
    }
}
