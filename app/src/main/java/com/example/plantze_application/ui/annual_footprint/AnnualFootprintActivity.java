package com.example.plantze_application.ui.annual_footprint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;
import com.google.android.material.snackbar.Snackbar;

public class AnnualFootprintActivity extends AppCompatActivity {

    private Button transportationButton;
    private Button locationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annualfootprint);

        // Show the message for 5 seconds
        String welcomeMessage = getIntent().getStringExtra("message");
        if (welcomeMessage != null) {
            Snackbar snackbar = Snackbar.make(
                    findViewById(android.R.id.content),
                    welcomeMessage,
                    Snackbar.LENGTH_LONG
            );
            snackbar.setTextMaxLines(5);
            snackbar.setDuration(5000);
            snackbar.show();
        }

        // Initialize buttons
        transportationButton = findViewById(R.id.transportationButton);
        locationButton = findViewById(R.id.locationButton);

        // Set click listeners for buttons
        transportationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnnualFootprintActivity.this, TransportationActivity.class);
                startActivity(intent);
            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnnualFootprintActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });
    }
}
