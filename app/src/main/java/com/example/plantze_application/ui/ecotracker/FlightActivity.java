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

public class FlightActivity extends AppCompatActivity {
    private EditText flightsInput;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_flights);

        flightsInput = findViewById(R.id.flightsInput);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Every 1000 km, 90kg per person
                String input = flightsInput.getText().toString().trim();
                if(TextUtils.isEmpty(input)){
                    Toast.makeText(FlightActivity.this, "Enter input", Toast.LENGTH_SHORT).show();
                    return;
                }
                double finalEmission = Double.parseDouble(input) * 0.09;
                Intent intent = new Intent(FlightActivity.this, DateActivity.class);
                intent.putExtra("finalEmission", String.valueOf(finalEmission));
                intent.putExtra("category", "Transportation");
                intent.putExtra("type", "Flight");
                startActivity(intent);
            }
        });
    }
}
