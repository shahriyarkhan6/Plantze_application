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

public class OthersActivity extends AppCompatActivity {
    private RadioGroup othersRadioGroup;
    private EditText othersInput;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_others);

        othersRadioGroup = findViewById(R.id.othersRadioGroup);
        othersInput = findViewById(R.id.othersInput);
        submitButton = findViewById(R.id.submitButton);

        othersRadioGroup.check(R.id.furnitureRadio);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = othersInput.getText().toString().trim();

                if(TextUtils.isEmpty(input)){
                    Toast.makeText(OthersActivity.this, "Enter input", Toast.LENGTH_SHORT).show();
                    return;
                }
                double finalEmission, d_input = Double.parseDouble(input);

                int selectedId = othersRadioGroup.getCheckedRadioButtonId();
                double emission = 0;

                if (selectedId == R.id.furnitureRadio) {
                    emission = 1; // kg
                } else if (selectedId == R.id.appliancesRadio) {
                    emission = 2; // kg
                } else if (selectedId == R.id.otherRadio) {
                    emission = 3; // kg
                }

                finalEmission = emission * d_input;
                Intent intent = new Intent(OthersActivity.this, DateActivity.class);
                intent.putExtra("finalEmission", String.valueOf(finalEmission));
                intent.putExtra("category", "Consumption");
                intent.putExtra("type", "Others");
                startActivity(intent);
            }
        });
    }
}