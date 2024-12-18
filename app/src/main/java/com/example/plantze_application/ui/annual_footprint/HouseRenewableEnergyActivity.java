package com.example.plantze_application.ui.annual_footprint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
//import com.example.plantze_application.ui.annual_footprint.HousingDataModel;


public class HouseRenewableEnergyActivity extends AppCompatActivity {

    private RadioGroup houseRenewableEnergyRadioGroup;
    private Button submitButton;
    private int housingCurrentEmission;
    private int currentColumnRow;
    private int currentArrayRow;
    //private int energyComparison;
    private double foodCarbonEmission;
    public double transportCarbonEmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //linking to activity_house_type xml file
        setContentView(R.layout.activity_house_renewable_energy);

        //buttons and options that link to xml files
        houseRenewableEnergyRadioGroup = findViewById(R.id.houseRenewableEnergyRadioGroup);
        submitButton = findViewById(R.id.submitButton);

        //bringing over previous category values from intent
        housingCurrentEmission = getIntent().getIntExtra("carbonEmission", 0);
        currentArrayRow = getIntent().getIntExtra("ArrayRow", 0);
        currentColumnRow = getIntent().getIntExtra("ColumnRow", 0);
        //energyComparison = getIntent().getIntExtra("EnergyComparison", 0);

        foodCarbonEmission = getIntent().getDoubleExtra("foodCarbonEmission", 0);
        transportCarbonEmission = getIntent().getDoubleExtra("transportCarbonEmission", 0);

        //button settings
        submitButton.setOnClickListener(v -> {
            int selectedId = houseRenewableEnergyRadioGroup.getCheckedRadioButtonId();

            // Check if an option is selected
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                return;
            }

            //modifying carbon emission value based on chosen setting
            if (selectedId == R.id.radioPrimarily) {
                housingCurrentEmission = housingCurrentEmission - 6000;
            } else if (selectedId == R.id.radioPartially) {
                housingCurrentEmission = housingCurrentEmission - 4000;
            }
//            else if (selectedId == R.id.radioOil) {
//                housingCurrentEmission = housingCurrentEmission;
//            }

            /*
            housing data from formula file converted into a nested array
            previous questions have been used to determine array index to find
            relevant number
             */

            int[][] HousingData = {
                    {2400, 200, 2100, 2870, 2170, 2440, 400, 5200, 4400, 2340, 2610, 1200, 6100, 5400, 2510, 2780, 1700, 7200, 6400, 2680, 3100, 2300, 8200, 7400, 3000},
                    {2600, 250, 2650, 3470, 2370, 2640, 500, 5400, 4600, 2540, 2810, 1450, 6250, 5600, 2710, 2980, 1900, 7400, 6600, 2880, 3100, 2500, 8400, 7600, 3200},
                    {2700, 380, 3200, 4370, 2670, 2940, 550, 5700, 4900, 2840, 3110, 1600, 6700, 5900, 3010, 3280, 2050, 7700, 6900, 3180, 3600, 2700, 8700, 7900, 3500},
                    {3000, 450, 3700, 5270, 2970, 3240, 600, 6000, 5200, 3140, 3410, 1800, 6950, 6200, 3310, 3580, 2200, 8000, 7200, 3480, 3900, 3000, 9000, 8200, 3800},
                    {2800, 300, 3800, 3770, 3670, 5900, 600, 5300, 3940, 3840, 6500, 1200, 6200, 7100, 4010, 7100, 1800, 7200, 4280, 4180, 8300, 2400, 8200, 4600, 4500},
                    {3000, 380, 4000, 4470, 4170, 6100, 800, 5440, 4640, 4340, 6700, 1450, 6400, 7230, 4510, 7300, 2000, 7400, 4980, 4680, 8500, 2600, 8400, 5300, 5000},
                    {3380, 500, 4700, 5670, 4870, 6400, 1050, 5800, 5740, 5040, 7000, 1600, 6700, 7400, 5210, 7600, 2250, 7700, 5985, 5380, 8800, 2800, 8700, 6350, 5750},
                    {3860, 600, 5350, 6570, 5670, 6700, 1200, 6100, 6740, 5840, 7300, 1800, 7000, 7550, 6010, 7900, 2400, 8000, 7080, 6180, 9100, 3100, 9000, 7400, 6500},
                    {2800, 320, 5400, 5570, 4170, 3000, 600, 10500, 5740, 4340, 3200, 1800, 14000, 5800, 4510, 3400, 2700, 17500, 5852, 4680, 3600, 3600, 21000, 6100, 5000},
                    {2880, 450, 6200, 6170, 4670, 3200, 900, 11000, 6340, 4840, 3400, 2100, 15500, 6410, 5010, 3600, 3100, 18100, 6560, 5180, 3800, 3800, 22000, 6840, 5500},
                    {3000, 520, 7000, 6970, 5270, 3500, 1500, 12500, 7240, 5640, 3700, 2300, 16250, 7300, 5710, 4100, 3400, 20000, 7600, 5980, 4100, 4000, 23500, 7890, 6250},
                    {3230, 675, 8900, 7970, 6170, 3800, 1800, 14000, 8140, 6340, 4000, 2700, 17500, 8230, 6510, 4400, 3600, 21000, 8300, 6680, 4400, 4200, 25000, 8710, 7000},
                    {2160, 300, 2500, 2200, 2100, 2400, 410, 2800, 2600, 3000, 2600, 1210, 3000, 2800, 3200, 2800, 1700, 3200, 3000, 3400, 3000, 2200, 3400, 3200, 3600},
                    {2349, 410, 2592, 2300, 2450, 2600, 500, 3000, 2800, 3200, 2800, 1450, 3200, 3000, 3400, 3000, 1900, 3400, 3200, 3600, 3200, 2500, 3600, 3400, 3800},
                    {2732, 500, 2680, 2450, 2700, 2900, 560, 3300, 3100, 3500, 3100, 1620, 3500, 3300, 3700, 3300, 2000, 3700, 3500, 3900, 3500, 2600, 3900, 3700, 4100},
                    {3199, 580, 2750, 2600, 3000, 3200, 600, 3600, 3400, 3800, 3400, 1820, 3800, 3600, 4000, 3600, 2200, 4000, 3800, 4200, 3800, 3000, 4200, 4000, 4400},
                    {2443, 300, 3400, 4000, 1500, 4000, 600, 4000, 5000, 2500, 4500, 1200, 6100, 7000, 4100, 5000, 1800, 8000, 9000, 5500, 6000, 2400, 10550, 12000, 7220},
                    {2727, 410, 3499, 4300, 1800, 5200, 800, 4600, 6200, 2700, 6000, 1550, 6900, 8000, 4300, 6500, 2000, 8800, 10200, 6000, 7800, 2500, 10900, 13200, 8000},
                    {3151, 550, 3599, 4700, 2100, 6800, 1050, 5100, 7300, 3500, 7800, 1700, 7300, 9100, 4850, 8800, 2250, 9200, 11000, 6800, 9800, 2800, 11200, 14100, 8600},
                    {3578, 605, 3700, 4900, 2500, 7500, 1200, 6000, 8000, 4000, 8500, 1800, 8500, 10000, 5500, 10000, 2400, 10500, 12000, 7100, 11000, 3200, 12000, 15000, 9100},
                    {2821, 300, 3820, 4370, 3970, 7500, 1200, 5500, 4540, 4140, 10000, 1800, 8500, 4710, 4310, 12500, 2400, 11000, 4880, 4480, 15000, 3000, 14000, 5200, 4800},
                    {3010, 560, 4000, 4870, 4470, 8800, 1400, 6000, 5040, 4640, 12000, 2000, 9200, 5210, 4810, 14200, 2600, 12000, 5380, 4980, 16800, 3500, 14800, 5700, 5300},
                    {3261, 890, 4307, 5670, 5270, 10800, 1650, 7200, 5840, 5340, 14000, 2300, 10200, 6010, 5610, 16000, 2820, 13500, 6180, 5780, 18200, 4000, 15500, 6500, 6150},
                    {3578, 1000, 4400, 6370, 5970, 12500, 1800, 8500, 6540, 6140, 15000, 2400, 11000, 6710, 6310, 17500, 3000, 14000, 6880, 6480, 19000, 4500, 16000, 7200, 6800},
                    {1971, 300, 2400, 1500, 2000, 2800, 500, 2800, 2500, 2800, 3000, 1000, 3600, 3000, 3000, 4000, 1600, 4500, 3700, 3330, 8000, 2000, 5000, 5800, 3500},
                    {2160, 410, 2523, 1850, 2250, 2910, 550, 3100, 2800, 3000, 3210, 1250, 3750, 3500, 3300, 5500, 1750, 4600, 4500, 3500, 9500, 2100, 5200, 6800, 3700},
                    {2500, 500, 2600, 2100, 2500, 3000, 580, 3250, 3400, 3300, 3500, 1320, 3900, 4100, 3520, 6200, 1900, 4800, 5200, 3720, 10200, 2300, 5300, 7200, 4000},
                    {2800, 550, 2720, 2500, 2600, 3200, 600, 3500, 3800, 3400, 3800, 1420, 4050, 5000, 3800, 8000, 2000, 5100, 5800, 4000, 12000, 3000, 5600, 7800, 4300},
                    {2443, 300, 2590, 3170, 1400, 4000, 550, 3800, 5600, 2400, 4300, 1200, 5000, 6000, 4000, 4800, 1700, 5350, 3680, 3800, 9500, 2500, 5370, 6000, 4000},
                    {2750, 380, 2620, 3770, 1560, 5000, 700, 4320, 5940, 2600, 5500, 1350, 5800, 6200, 4310, 6300, 1900, 5500, 4280, 3800, 10100, 2780, 5500, 6600, 4640},
                    {3111, 500, 2730, 4670, 1900, 6500, 950, 4800, 6140, 3300, 6800, 1520, 6200, 6420, 4600, 8500, 2150, 5720, 5180, 4220, 11200, 3000, 5800, 6800, 5000},
                    {3580, 590, 2800, 5570, 2200, 7300, 1100, 5500, 6340, 3800, 8340, 1700, 6100, 6500, 5100, 9000, 2400, 5900, 6080, 4400, 14000, 3500, 6200, 7400, 5430},
                    {2822, 300, 2810, 3340, 3800, 3600, 1200, 4300, 5900, 3500, 5000, 1800, 5300, 3510, 4100, 8000, 2400, 5440, 3800, 4200, 9500, 2800, 5670, 6200, 4300},
                    {3010, 560, 3000, 3940, 4070, 3840, 1380, 4900, 6330, 3930, 6200, 2000, 5690, 4110, 4500, 8300, 2500, 5600, 4500, 4640, 1010, 3000, 5800, 6900, 4700},
                    {3300, 890, 3468, 4840, 5000, 3900, 1600, 5320, 6440, 4360, 7000, 2200, 6250, 5010, 4780, 9000, 2650, 5800, 5380, 5000, 10300, 3800, 6100, 7500, 5100},
                    {3600, 1000, 3760, 5740, 5600, 5100, 1750, 5800, 6900, 5000, 8000, 2300, 6500, 5910, 5360, 9500, 2800, 6000, 6200, 5400, 11000, 4300, 6350, 7850, 5500},
                    {1680, 200, 0, 1320, 1600, 2240, 500, 0, 2100, 1800, 2800, 900, 0, 3000, 2800, 3700, 1400, 0, 3300, 3000, 5000, 1900, 0, 5700, 3500},
                    {1870, 250, 0, 1550, 1850, 2430, 550, 0, 2400, 2000, 3000, 1100, 0, 3300, 3000, 4100, 1600, 0, 3700, 3100, 7200, 2100, 0, 6000, 3600},
                    {2170, 380, 0, 1800, 2000, 2719, 580, 0, 2800, 2300, 3200, 1200, 0, 3700, 3300, 4600, 1700, 0, 4400, 3500, 8000, 2200, 0, 6600, 3900},
                    {2440, 450, 0, 2000, 2100, 2997, 600, 0, 3200, 2400, 3500, 1300, 0, 4300, 3500, 5000, 1900, 0, 5200, 3900, 10000, 2600, 0, 7000, 4200},
                    {2060, 300, 0, 1500, 1800, 2500, 550, 0, 3000, 2200, 3100, 1250, 0, 4100, 3200, 4000, 1900, 0, 4550, 3100, 5350, 2300, 0, 6000, 3900},
                    {2260, 400, 0, 1700, 2200, 2880, 670, 0, 3400, 2500, 3300, 1450, 0, 4600, 3500, 4700, 2300, 0, 4950, 3300, 7550, 2500, 0, 6300, 4200},
                    {2540, 500, 0, 2100, 2500, 3110, 780, 0, 3800, 2900, 3500, 1750, 0, 5000, 3600, 5200, 2700, 0, 5350, 3700, 8200, 2700, 0, 7000, 4500},
                    {3010, 620, 0, 2300, 2700, 3320, 900, 0, 4200, 3300, 3900, 2100, 0, 5400, 4000, 5900, 3000, 0, 5650, 4100, 10500, 3100, 0, 7400, 4700},
                    {2440, 350, 0, 1800, 2300, 2700, 610, 0, 3650, 2600, 3670, 1500, 0, 4500, 3500, 4250, 2150, 0, 5000, 3530, 6000, 2600, 0, 6500, 4100},
                    {2727, 570, 0, 2100, 2600, 3100, 690, 0, 4050, 2900, 3870, 1700, 0, 5000, 3700, 5050, 2550, 0, 5300, 3730, 7800, 3100, 0, 6800, 4400},
                    {3010, 900, 0, 2450, 2900, 3300, 820, 0, 4650, 3300, 4100, 2000, 0, 5400, 4200, 5400, 2850, 0, 5600, 4200, 8500, 3600, 0, 7400, 4900},
                    {3577, 980, 0, 2600, 3300, 3600, 980, 0, 5150, 3600, 4300, 2350, 0, 5700, 4600, 6200, 3150, 0, 6000, 4630, 11100, 4000, 0, 7800, 5100}
            };

            //finding the relevant value within the array
            int specificValue = HousingData[currentArrayRow][currentColumnRow];
            housingCurrentEmission = housingCurrentEmission + specificValue;

            //Adding Housing Carbon Data to user info on Firebase
            SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
            String userID = sharedPref.getString("USER_ID", null);

            if(userID != null){
                Map<String, Object> updatedData = new HashMap<>();
                updatedData.put("Housing Annual Carbon Emission", housingCurrentEmission);
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("users").document(userID)
                        .update(updatedData)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(HouseRenewableEnergyActivity.this, "User info updated successfully!", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {

                            Log.e("Firestore", "Error updating user info", e);
                        });


            }

            //converting hosuing carbon emission into a double data type
            double housingCarbonEmission = housingCurrentEmission;

            //linking this question to the next question and bringing relevant data
            Intent intent = new Intent(HouseRenewableEnergyActivity.this, ConsumptionActivity.class);
            intent.putExtra("foodCarbonEmission", foodCarbonEmission);
            intent.putExtra("housingCarbonEmission", housingCarbonEmission); // Pass as double
            intent.putExtra("transportCarbonEmission", transportCarbonEmission);
            startActivity(intent);


        });

    }

}
