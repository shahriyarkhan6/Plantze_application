package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class PublicTransportFrequencyActivity extends AppCompatActivity {

    private RadioGroup frequencyGroup;
    private Button nextButton;
    private double carEmissions;  // Store car emissions here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_transport_frequency);

        // Get the car emissions from the previous activity (if available)
        Intent intent = getIntent();
        carEmissions = intent.getDoubleExtra("CAR_EMISSIONS", 0.0);

        frequencyGroup = findViewById(R.id.frequencyGroup);
        nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected frequency value
                int selectedId = frequencyGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);
                String frequency = selectedRadioButton.getText().toString();

                // Pass the selected frequency and car emissions to the next activity
                Intent intent = new Intent(PublicTransportFrequencyActivity.this, PublicTransportTimeActivity.class);
                intent.putExtra("FREQUENCY", frequency);
                intent.putExtra("CAR_EMISSIONS", carEmissions);  // Pass car emissions along
                startActivity(intent);
            }
        });
    }
}
