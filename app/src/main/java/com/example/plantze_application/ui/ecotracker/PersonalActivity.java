package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class PersonalActivity extends AppCompatActivity {
    private RadioGroup vehicleRadioGroup;
    private EditText distanceInput;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_personal_vehicle);

        vehicleRadioGroup = findViewById(R.id.vehicleRadioGroup);
        distanceInput = findViewById(R.id.distanceInput);
        submitButton = findViewById(R.id.submitButton);

        vehicleRadioGroup.check(R.id.gasolineRadio);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = distanceInput.getText().toString().trim();

                if(TextUtils.isEmpty(input)){
                    Toast.makeText(PersonalActivity.this, "Enter input", Toast.LENGTH_SHORT).show();
                    return;
                }
                double finalEmission, d_input = Double.parseDouble(input);

                int selectedId = vehicleRadioGroup.getCheckedRadioButtonId();
                double emission = 0;

                if (selectedId == R.id.gasolineRadio) {
                    emission = 0.24; // kg
                } else if (selectedId == R.id.dieselRadio) {
                    emission = 0.27; // kg
                } else if (selectedId == R.id.hybridRadio) {
                    emission = 0.16; // kg
                } else if (selectedId == R.id.electricRadio) {
                    emission = 0.05; // kg
                }

                finalEmission = emission * d_input;
                Intent intent = new Intent(PersonalActivity.this, DateActivity.class);
                intent.putExtra("finalEmission", String.valueOf(finalEmission));
                intent.putExtra("category", "Transportation");
                intent.putExtra("type", "Personal vehicle");
                startActivity(intent);
            }
        });
    }
}
