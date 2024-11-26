package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_meals);

        mealRadioGroup = findViewById(R.id.mealRadioGroup);
        servingsInput = findViewById(R.id.servingsInput);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int selectedId = mealRadioGroup.getCheckedRadioButtonId();
                double emission=0;
                double final_emission;

                if (selectedId == R.id.beefRadio) {
                    emission = 1; // kg
                } else if (selectedId == R.id.porkRadio) {
                    emission = 2; // kg
                } else if (selectedId == R.id.chickenRadio) {
                    emission = 3; // kg
                } else if (selectedId == R.id.plantRadio) {
                    emission = 4; // kg
                }

                String input = servingsInput.getText().toString();
                double servings=Double.parseDouble(input);
                if (!input.isEmpty()) {
                    try {
                        servings = Double.parseDouble(input);
                    } catch (NumberFormatException e) {
                        // Handle invalid input (for example, show a Toast or error message)
                        Toast.makeText(MealActivity.this, "Invalid input. Please enter a valid number.", Toast.LENGTH_SHORT).show();
                        return;  // Exit early if input is invalid
                    }
                } else {
                    // Handle empty input (for example, show a Toast or error message)
                    Toast.makeText(MealActivity.this, "Please enter a number for servings.", Toast.LENGTH_SHORT).show();
                    return;  // Exit early if input is empty
                }
                final_emission=emission*servings;
                Intent intent = new Intent(MealActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
