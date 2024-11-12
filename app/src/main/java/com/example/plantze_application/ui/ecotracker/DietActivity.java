package com.example.plantze_application.ui.ecotracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class DietActivity extends AppCompatActivity {

    RadioGroup dietRadioGroup;
    Button calculateButton;  // Declare the button
    TextView resultTextView;  // Declare the TextView to display results

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);  // Use the correct layout file

        dietRadioGroup = findViewById(R.id.dietRadioGroup);  // Reference to the RadioGroup
        calculateButton = findViewById(R.id.calculateButton);  // Reference to the Button
        resultTextView = findViewById(R.id.resultTextView);  // Reference to the result TextView

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = dietRadioGroup.getCheckedRadioButtonId();  // Get selected radio button id

                if (selectedId != -1) {  // Ensure a radio button is selected
                    RadioButton selectedRadioButton = findViewById(selectedId);

                    // Calculate emission based on the selected option using if-else
                    int emission = 0;  // Default value
                    if (selectedId == R.id.radioVegetarian) {
                        emission = 1000;  // Vegetarian emission value
                    } else if (selectedId == R.id.radioVegan) {
                        emission = 500;   // Vegan emission value
                    } else if (selectedId == R.id.radioPescatarian) {
                        emission = 1500;  // Pescatarian emission value
                    } else if (selectedId == R.id.radioMeatBased) {
                        emission = 0;     // Meat-based emission value
                    }

                    // Display the calculated emission in the TextView
                    resultTextView.setText("Carbon Emission: " + emission + " kg");

                    // Show a confirmation toast
                    Toast.makeText(DietActivity.this, selectedRadioButton.getText() + " selected", Toast.LENGTH_SHORT).show();
                } else {
                    // No selection made
                    Toast.makeText(DietActivity.this, "Please select a diet type", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
