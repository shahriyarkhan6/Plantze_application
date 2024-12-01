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

public class MealActivity extends AppCompatActivity {
    private RadioGroup mealRadioGroup;
    private EditText servingsInput;
    private Button submitButton;
    private String date,activityId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_meals);

        mealRadioGroup = findViewById(R.id.mealRadioGroup);
        servingsInput = findViewById(R.id.servingsInput);
        submitButton = findViewById(R.id.submitButton);
        date = getIntent().getStringExtra("date");
        activityId=getIntent().getStringExtra("activityId");

        mealRadioGroup.check(R.id.beefRadio);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = servingsInput.getText().toString().trim();

                if(TextUtils.isEmpty(input)){
                    Toast.makeText(MealActivity.this, "Enter input", Toast.LENGTH_SHORT).show();
                    return;
                }
                double finalEmission, d_input = Double.parseDouble(input);

                int selectedId = mealRadioGroup.getCheckedRadioButtonId();
                double emission = 0;

                if (selectedId == R.id.beefRadio) {
                    emission = 45; // kg
                } else if (selectedId == R.id.porkRadio) {
                    emission = 10; // kg
                } else if (selectedId == R.id.chickenRadio) {
                    emission = 5.5; // kg
                } else if (selectedId == R.id.fishRadio) {
                    emission = 4.5; // kg
                } else if (selectedId == R.id.plantRadio) {
                    emission = 0.7; // kg
                }

                finalEmission = emission * d_input;
                Intent intent = new Intent(MealActivity.this, ResultActivity.class);
                intent.putExtra("finalEmission", String.valueOf(finalEmission));
                intent.putExtra("category", "Consumption");
                intent.putExtra("type", "Meal");
                intent.putExtra("date",date);
                intent.putExtra("activityId",activityId);
                startActivity(intent);
            }
        });
    }
}
