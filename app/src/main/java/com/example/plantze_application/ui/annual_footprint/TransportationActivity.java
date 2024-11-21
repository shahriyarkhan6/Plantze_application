package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class TransportationActivity extends AppCompatActivity {

    private RadioGroup carUsageRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);

        carUsageRadioGroup = findViewById(R.id.carUsageRadioGroup);

        carUsageRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = findViewById(checkedId);
            String selectedOption = selectedRadioButton.getText().toString();


            if (selectedOption.equals("Yes")) {
                Intent intent = new Intent(TransportationActivity.this, CarTypeActivity.class);
                startActivity(intent);


            } else if (selectedOption.equals("No")) {
                Intent intent = new Intent(TransportationActivity.this, PublicTransportFrequencyActivity.class);
                startActivity(intent);
            }
        });
    }
}
