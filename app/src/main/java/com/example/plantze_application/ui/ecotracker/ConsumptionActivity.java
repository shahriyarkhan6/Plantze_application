package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class ConsumptionActivity extends AppCompatActivity {
    private Button mealButton,clothesButton,electronicsButton,billsButton,othersButton;
    private String date,activityId;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_consumption);

        mealButton = findViewById(R.id.mealButton);
        clothesButton = findViewById(R.id.clothesButton);
        electronicsButton = findViewById(R.id.electronicsButton);
        billsButton = findViewById(R.id.billsButton);
        othersButton = findViewById(R.id.othersButton);
        date = getIntent().getStringExtra("date");
        activityId=getIntent().getStringExtra("activityId");

        mealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsumptionActivity.this, MealActivity.class);
                intent.putExtra("date",date);
                intent.putExtra("activityId",activityId);
                startActivity(intent);
            }
        });

        clothesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsumptionActivity.this, ClothesActivity.class);
                intent.putExtra("date",date);
                intent.putExtra("activityId",activityId);
                startActivity(intent);
            }
        });

        electronicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsumptionActivity.this, ElectronicsActivity.class);
                intent.putExtra("date",date);
                intent.putExtra("activityId",activityId);
                startActivity(intent);
            }
        });

        billsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsumptionActivity.this, BillsActivity.class);
                intent.putExtra("date",date);
                intent.putExtra("activityId",activityId);
                startActivity(intent);
            }
        });

        othersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsumptionActivity.this, OthersActivity.class);
                intent.putExtra("date",date);
                intent.putExtra("activityId",activityId);
                startActivity(intent);
            }
        });
    }
}
