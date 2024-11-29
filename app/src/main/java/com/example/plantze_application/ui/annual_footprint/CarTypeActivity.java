package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;

public class CarTypeActivity extends AppCompatActivity {

    private RadioGroup carTypeGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_type);

        carTypeGroup = findViewById(R.id.carTypeGroup);

        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double carbonEmission = 0;
                double transportCarbonEmission = 0;
                int selectedId = carTypeGroup.getCheckedRadioButtonId();

                if (selectedId == R.id.radioGasoline) {
                    carbonEmission = 0.24;
                } else if (selectedId == R.id.radioDiesel) {
                    carbonEmission = 0.27;
                } else if (selectedId == R.id.radioHybrid) {
                    carbonEmission = 0.16;
                } else if (selectedId == R.id.radioElectric) {
                    carbonEmission = 0.05;
                } else if (selectedId == R.id.radioDontKnow) {
                    Toast.makeText(CarTypeActivity.this, "Please select a car type to proceed.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(CarTypeActivity.this, CarDistanceActivity.class);
                intent.putExtra("carbonEmission", carbonEmission);
                intent.putExtra("transportCarbonEmission", carbonEmission);
                startActivity(intent);
            }
        });
    }
}
