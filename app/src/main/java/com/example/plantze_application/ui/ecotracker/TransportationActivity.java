package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class TransportationActivity extends AppCompatActivity{
    private Button personalButton, publicButton, cycleWalkButton, flightButton;
    private String date;
    private boolean status;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_transportation);

        personalButton = findViewById(R.id.personalButton);
        publicButton = findViewById(R.id.publicButton);
        cycleWalkButton = findViewById(R.id.cycleWalkButton);
        flightButton = findViewById(R.id.flightButton);
        date = getIntent().getStringExtra("date");
        status=getIntent().getBooleanExtra("new",true);

        personalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransportationActivity.this, PersonalActivity.class);
                intent.putExtra("date",date);
                intent.putExtra("new",status);
                startActivity(intent);
            }
        });

        publicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransportationActivity.this, PublicActivity.class);
                intent.putExtra("date",date);
                intent.putExtra("new",status);
                startActivity(intent);
            }
        });

        cycleWalkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransportationActivity.this, CycleWalkActivity.class);
                intent.putExtra("date",date);
                intent.putExtra("new",status);
                startActivity(intent);
            }
        });

        flightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransportationActivity.this, FlightActivity.class);
                intent.putExtra("date",date);
                intent.putExtra("new",status);
                startActivity(intent);
            }
        });

    }
}
