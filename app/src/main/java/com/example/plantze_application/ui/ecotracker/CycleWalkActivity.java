package com.example.plantze_application.ui.ecotracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;

public class CycleWalkActivity extends AppCompatActivity {
    private EditText distanceInput;
    private Button submitButton;
    private String date;
    private boolean status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_et_cycling_walking);

        distanceInput = findViewById(R.id.distanceInput);
        submitButton = findViewById(R.id.submitButton);
        date = getIntent().getStringExtra("date");
        status=getIntent().getBooleanExtra("new",true);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = distanceInput.getText().toString().trim();
                if(TextUtils.isEmpty(input)){
                    Toast.makeText(CycleWalkActivity.this, "Enter input", Toast.LENGTH_SHORT).show();
                    return;
                }
                double finalEmission = Double.parseDouble(input) * 0.05;
                Intent intent = new Intent(CycleWalkActivity.this, ResultActivity.class);
                intent.putExtra("finalEmission", String.valueOf(finalEmission));
                intent.putExtra("category", "Transportation");
                intent.putExtra("type", "Cycling or walking");
                intent.putExtra("date",date);
                intent.putExtra("new",status);
                startActivity(intent);
            }
        });
    }
}
