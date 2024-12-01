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

public class OthersActivity extends AppCompatActivity {
    private RadioGroup othersRadioGroup;
    private EditText othersInput;
    private Button submitButton;
    private String date;
    private boolean status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_others);

        othersRadioGroup = findViewById(R.id.othersRadioGroup);
        othersInput = findViewById(R.id.othersInput);
        submitButton = findViewById(R.id.submitButton);
        date = getIntent().getStringExtra("date");
        status=getIntent().getBooleanExtra("new",true);

        othersRadioGroup.check(R.id.furnitureRadio);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = othersInput.getText().toString().trim();

                if(TextUtils.isEmpty(input)){
                    Toast.makeText(OthersActivity.this, "Enter input", Toast.LENGTH_SHORT).show();
                    return;
                }
                double finalEmission, d_input = Double.parseDouble(input);

                int selectedId = othersRadioGroup.getCheckedRadioButtonId();
                double emission = 0;

                if (selectedId == R.id.furnitureRadio) {
                    emission = 350; // kg
                } else if (selectedId == R.id.appliancesRadio) {
                    emission = 1358; // kg, amounting for average lifetime use
                } else if (selectedId == R.id.otherRadio) {
                    emission = 100; // kg, arbitrary educated number
                }

                finalEmission = emission * d_input;
                Intent intent = new Intent(OthersActivity.this, ResultActivity.class);
                intent.putExtra("finalEmission", String.valueOf(finalEmission));
                intent.putExtra("category", "Consumption");
                intent.putExtra("type", "Others");
                intent.putExtra("date",date);
                intent.putExtra("new",status);
                startActivity(intent);
            }
        });
    }
}