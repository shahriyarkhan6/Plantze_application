package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class TransportationActivity extends AppCompatActivity {

    private RadioGroup carUsageRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);

        carUsageRadioGroup = findViewById(R.id.carUsageRadioGroup);

        // Set listener for the radio group
        carUsageRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = findViewById(checkedId);
            String selectedOption = selectedRadioButton.getText().toString();

            // Check if the user selected "Yes" or "No"

                // Redirect to CarTypeActivity for further questions
            if (selectedOption.equals("Yes")) {
                Intent intent = new Intent(TransportationActivity.this, CarTypeActivity.class);
                startActivity(intent);


            } else if (selectedOption.equals("No")) {
                // Redirect to DietActivity
                Intent intent = new Intent(TransportationActivity.this, DietActivity.class);
                startActivity(intent);
            }
        });
    }
}
