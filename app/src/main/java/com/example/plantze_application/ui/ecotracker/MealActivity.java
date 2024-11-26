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
                String input = servingsInput.getText().toString();
                /* check that it is not null nor negative*/
                double finalEmission, servings=Double.parseDouble(input);

                int selectedId = mealRadioGroup.getCheckedRadioButtonId();
                double emission=0;

                if (selectedId == R.id.beefRadio) {
                    emission = 1; // kg
                } else if (selectedId == R.id.porkRadio) {
                    emission = 2; // kg
                } else if (selectedId == R.id.chickenRadio) {
                    emission = 3; // kg
                } else if (selectedId == R.id.plantRadio) {
                    emission = 4; // kg
                }

                finalEmission=emission*servings;
                Intent intent = new Intent(MealActivity.this, ResultActivity.class);
                intent.putExtra("finalEmission", String.valueOf(finalEmission));
                intent.putExtra("category", "Consumption");
                intent.putExtra("type", "Meal");
                startActivity(intent);
            }
        });
    }
}
