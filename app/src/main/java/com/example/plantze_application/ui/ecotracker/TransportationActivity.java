package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class TransportationActivity extends AppCompatActivity{
    private Button personalButton;
    private Button publicButton;
    private Button cycleWalkButton;
    private Button flightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_transportation);

        // Initialize buttons
        personalButton = findViewById(R.id.personalButton);
        publicButton = findViewById(R.id.publicButton);
        cycleWalkButton = findViewById(R.id.cycleWalkButton);
        flightButton = findViewById(R.id.flightButton);


        // Set click listeners for buttons
        personalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransportationActivity.this, PersonalActivity.class);
                startActivity(intent);
            }
        });

        publicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransportationActivity.this, PublicActivity.class);
                startActivity(intent);
            }
        });

        cycleWalkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransportationActivity.this, CycleWalkActivity.class);
                startActivity(intent);
            }
        });

        flightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransportationActivity.this, FlightActivity.class);
                startActivity(intent);
            }
        });

    }
}
