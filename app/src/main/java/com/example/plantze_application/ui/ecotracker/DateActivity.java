package com.example.plantze_application.ui.ecotracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

import java.time.LocalDate;

public class DateActivity extends AppCompatActivity {
    private String s_date;
    private Button createButton;
    private TextView dateText;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_date);

        s_date = getIntent().getStringExtra("date");

        createButton = findViewById(R.id.createButton);
        dateText = findViewById(R.id.dateText);

        dateText.setText(s_date);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DateActivity.this, CategoryActivity.class);
                intent.putExtra("date",s_date);
                startActivity(intent);
            }
        });
    }
}
