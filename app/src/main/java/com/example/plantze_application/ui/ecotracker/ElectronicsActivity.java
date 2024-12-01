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

public class ElectronicsActivity extends AppCompatActivity {
    private RadioGroup deviceRadioGroup;
    private EditText devicesInput;
    private Button submitButton;
    private String date, activityId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_electronics);

        deviceRadioGroup = findViewById(R.id.deviceRadioGroup);
        devicesInput = findViewById(R.id.devicesInput);
        submitButton = findViewById(R.id.submitButton);
        date = getIntent().getStringExtra("date");
        activityId=getIntent().getStringExtra("activityId");

        deviceRadioGroup.check(R.id.smartphoneRadio);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = devicesInput.getText().toString().trim();

                if(TextUtils.isEmpty(input)){
                    Toast.makeText(ElectronicsActivity.this, "Enter input", Toast.LENGTH_SHORT).show();
                    return;
                }
                double finalEmission, d_input = Double.parseDouble(input);

                int selectedId = deviceRadioGroup.getCheckedRadioButtonId();
                double emission = 0;

                if (selectedId == R.id.smartphoneRadio) {
                    emission = 55; // kg
                } else if (selectedId == R.id.computerRadio) {
                    emission = 225; // kg
                } else if (selectedId == R.id.tvRadio) {
                    emission = 500; // kg
                } else if (selectedId == R.id.otherRadio) {
                    emission = 250; // kg
                }

                finalEmission = emission * d_input;
                Intent intent = new Intent(ElectronicsActivity.this, ResultActivity.class);
                intent.putExtra("finalEmission", String.valueOf(finalEmission));
                intent.putExtra("category", "Consumption");
                intent.putExtra("type", "Electronics");
                intent.putExtra("date",date);
                intent.putExtra("activityId",activityId);
                startActivity(intent);
            }
        });
    }
}
