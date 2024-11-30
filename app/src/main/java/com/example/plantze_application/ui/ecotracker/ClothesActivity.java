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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_clothes);

        itemsInput = findViewById(R.id.itemsInput);
        submitButton = findViewById(R.id.submitButton);

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
                Intent intent = new Intent(ClothesActivity.this, DateActivity.class);
                intent.putExtra("finalEmission", String.valueOf(finalEmission));
                intent.putExtra("category", "Consumption");
                intent.putExtra("type", "Clothes");
                startActivity(intent);
            }
        });
    }
}
