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

public class ClothesActivity extends AppCompatActivity {
    private EditText itemsInput;
    private Button submitButton;
    private String date;
    private boolean status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_clothes);

        itemsInput = findViewById(R.id.itemsInput);
        submitButton = findViewById(R.id.submitButton);
        date = getIntent().getStringExtra("date");
        status=getIntent().getBooleanExtra("new",true);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = itemsInput.getText().toString().trim();
                if(TextUtils.isEmpty(input)){
                    Toast.makeText(ClothesActivity.this, "Enter input", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Average clothing emissions
                double emission = 6;
                double finalEmission = Double.parseDouble(input) * emission;
                Intent intent = new Intent(ClothesActivity.this, ResultActivity.class);
                intent.putExtra("finalEmission", String.valueOf(finalEmission));
                intent.putExtra("category", "Consumption");
                intent.putExtra("type", "Clothes");
                intent.putExtra("date",date);
                intent.putExtra("new",status);
                startActivity(intent);
            }
        });
    }
}
