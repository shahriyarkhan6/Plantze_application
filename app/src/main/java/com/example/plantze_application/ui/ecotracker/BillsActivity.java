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

public class BillsActivity extends AppCompatActivity {
    private EditText billInput;
    private RadioGroup billRadioGroup;
    private Button submitButton;
    private String date, activityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_bills);
        date = getIntent().getStringExtra("date");
        activityId=getIntent().getStringExtra("activityId");

        billInput = findViewById(R.id.billInput);
        billRadioGroup = findViewById(R.id.billRadioGroup);
        submitButton = findViewById(R.id.submitButton);

        billRadioGroup.check(R.id.electricityRadio);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = billInput.getText().toString().trim();
                if(TextUtils.isEmpty(input)){
                    Toast.makeText(BillsActivity.this, "Enter input", Toast.LENGTH_SHORT).show();
                    return;
                }
                double finalEmission, d_input = Double.parseDouble(input);

                int selectedId = billRadioGroup.getCheckedRadioButtonId();
                double emission = 0;

                if (selectedId == R.id.electricityRadio) {
                    emission = 2; // kg
                } else if (selectedId == R.id.gasRadio) {
                    emission = 0.4; // kg
                } else if (selectedId == R.id.waterRadio) {
                    emission = 1; // kg
                }

                finalEmission = emission * d_input;
                Intent intent = new Intent(BillsActivity.this, ResultActivity.class);
                intent.putExtra("finalEmission", String.valueOf(finalEmission));
                intent.putExtra("category", "Consumption");
                intent.putExtra("type", "Bills");
                intent.putExtra("date",date);
                intent.putExtra("activityId",activityId);
                startActivity(intent);
            }
        });
    }
}
