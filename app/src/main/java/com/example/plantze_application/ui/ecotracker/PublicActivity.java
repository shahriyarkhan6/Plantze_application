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

public class PublicActivity extends AppCompatActivity {
    private RadioGroup transportRadioGroup;
    private EditText hoursInput;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_public_transportation);

        transportRadioGroup = findViewById(R.id.transportRadioGroup);
        hoursInput = findViewById(R.id.hoursInput);
        submitButton = findViewById(R.id.submitButton);

        transportRadioGroup.check(R.id.busRadio);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = hoursInput.getText().toString().trim();

                if(TextUtils.isEmpty(input)){
                    Toast.makeText(PublicActivity.this, "Enter input", Toast.LENGTH_SHORT).show();
                    return;
                }
                double finalEmission, d_input = Double.parseDouble(input);

                int selectedId = transportRadioGroup.getCheckedRadioButtonId();
                double emission = 0;

                if (selectedId == R.id.busRadio) {
                    emission = 1; // kg
                } else if (selectedId == R.id.trainRadio) {
                    emission = 2; // kg
                } else if (selectedId == R.id.subwayRadio) {
                    emission = 3; // kg
                } else if (selectedId == R.id.otherRadio) {
                    emission = 4; // kg
                }

                finalEmission = emission * d_input;
                Intent intent = new Intent(PublicActivity.this, DateActivity.class);
                intent.putExtra("finalEmission", String.valueOf(finalEmission));
                intent.putExtra("category", "Transportation");
                intent.putExtra("type", "Public transportation");
                startActivity(intent);
            }
        });
    }
}
