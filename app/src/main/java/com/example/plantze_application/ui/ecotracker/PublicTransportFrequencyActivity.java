package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class PublicTransportFrequencyActivity extends AppCompatActivity {

    private RadioGroup frequencyGroup;
    private Button nextButton;
    private double carEmissions;  // To hold the car emissions passed from the previous activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_transport_frequency);

        carEmissions = getIntent().getDoubleExtra("CAR_EMISSIONS", 0);

        frequencyGroup = findViewById(R.id.frequencyGroup);
        nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(v -> {
            int selectedFrequencyId = frequencyGroup.getCheckedRadioButtonId();

            if (selectedFrequencyId == -1) {
                Toast.makeText(this, "Please select a frequency option.", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedFrequencyButton = findViewById(selectedFrequencyId);
            String frequency = selectedFrequencyButton.getText().toString();

            Intent intent = new Intent(PublicTransportFrequencyActivity.this, PublicTransportTimeActivity.class);
            intent.putExtra("FREQUENCY", frequency);
            intent.putExtra("CAR_EMISSIONS", carEmissions);
            startActivity(intent);
        });
    }
}
