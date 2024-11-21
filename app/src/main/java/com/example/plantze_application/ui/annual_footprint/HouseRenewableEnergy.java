package com.example.plantze_application.ui.annual_footprint;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plantze_application.R;
//import com.example.plantze_application.ui.annual_footprint.HousingDataModel;


public class HouseRenewableEnergy extends AppCompatActivity {

    private RadioGroup houseRenewableEnergyRadioGroup;
    private Button submitButton;
    private TextView resultTextView;
    private int currentEmission;
    private int currentColumnRow;
    private int currentArrayRow;
    private int energyComparison;

//    private List<HousingDataModel> dataHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_renewable_energy);

        houseRenewableEnergyRadioGroup = findViewById(R.id.houseRenewableEnergyRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);

        currentEmission = getIntent().getIntExtra("carbonEmission", 0);
        currentArrayRow = getIntent().getIntExtra("ArrayRow", 0);
        currentColumnRow = getIntent().getIntExtra("ColumnRow", 0);
        energyComparison = getIntent().getIntExtra("EnergyComparison", 0);

        resultTextView.setText("Current Carbon Emission: " + currentEmission + " CO₂");

        submitButton.setOnClickListener(v -> {
            int selectedId = houseRenewableEnergyRadioGroup.getCheckedRadioButtonId();

            if (selectedId == R.id.radioPrimarily) {
                currentEmission = currentEmission - 6000;
            } else if (selectedId == R.id.radioPartially) {
                currentEmission = currentEmission - 6000;
            } else if (selectedId == R.id.radioOil) {
                currentEmission = currentEmission;
            }


            int[][] HousingData = {{2400, 200, 2100, 2870, 2170, 2440, 400, 5200, 4400, 2340, 2610, 1200, 6100, 5400, 2510, 2780, 1700, 7200, 6400, 2680, 3100, 2300, 8200, 7400, 3000}, {2600, 250, 2650, 3470, 2370, 2640, 500, 5400, 4600, 2540, 2810, 1450, 6250, 5600, 2710, 2980, 1900, 7400, 6600, 2880, 3100, 2500, 8400, 7600, 3200}, {2700, 380, 3200, 4370, 2670, 2940, 550, 5700, 4900, 2840, 3110, 1600, 6700, 5900, 3010, 3280, 2050, 7700, 6900, 3180, 3600, 2700, 8700, 7900, 3500}, {3000, 450, 3700, 5270, 2970, 3240, 600, 6000, 5200, 3140, 3410, 1800, 6950, 6200, 3310, 3580, 2200, 8000, 7200, 3480, 3900, 3000, 9000, 8200, 3800}, {2800, 300, 3800, 3770, 3670, 5900, 600, 5300, 3940, 3840, 6500, 1200, 6200, 7100, 4010, 7100, 1800, 7200, 4280, 4180, 8300, 2400, 8200, 4600, 4500}, {3000, 380, 4000, 4470, 4170, 6100, 800, 5440, 4640, 4340, 6700, 1450, 6400, 7230, 4510, 7300, 2000, 7400, 4980, 4680, 8500, 2600, 8400, 5300, 5000}, {3380, 500, 4700, 5670, 4870, 6400, 1050, 5800, 5740, 5040, 7000, 1600, 6700, 7400, 5210, 7600, 2250, 7700, 5985, 5380, 8800, 2800, 8700, 6350, 5750}, {3860, 600, 5350, 6570, 5670, 6700, 1200, 6100, 6740, 5840, 7300, 1800, 7000, 7550, 6010, 7900, 2400, 8000, 7080, 6180, 9100, 3100, 9000, 7400, 6500}, {2800, 320, 5400, 5570, 4170, 3000, 600, 10500, 5740, 4340, 3200, 1800, 14000, 5800, 4510, 3400, 2700, 17500, 5852, 4680, 3600, 3600, 21000, 6100, 5000}, {2880, 450, 6200, 6170, 4670, 3200, 900, 11000, 6340, 4840, 3400, 2100, 15500, 6410, 5010, 3600, 3100, 18100, 6560, 5180, 3800, 3800, 22000, 6840, 5500}, {3000, 520, 7000, 6970, 5270, 3500, 1500, 12500, 7240, 5640, 3700, 2300, 16250, 7300, 5710, 4100, 3400, 20000, 7600, 5980, 4100, 4000, 23500, 7890, 6250}, {3230, 675, 8900, 7970, 6170, 3800, 1800, 14000, 8140, 6340, 4000, 2700, 17500, 8230, 6510, 4400, 3600, 21000, 8300, 6680, 4400, 4200, 25000, 8710, 7000}, {2160, 300, 2500, 2200, 2100, 2400, 410, 2800, 2600, 3000, 2600, 1210, 3000, 2800, 3200, 2800, 1700, 3200, 3000, 3400, 3000, 2200, 3400, 3200, 3600}, {2349, 410, 2592, 2300, 2450, 2600, 500, 3000, 2800, 3200, 2800, 1450, 3200, 3000, 3400, 3000, 1900, 3400, 3200, 3600, 3200, 2500, 3600, 3400, 3800}, {2732, 500, 2680, 2450, 2700, 2900, 560, 3300, 3100, 3500, 3100, 1620, 3500, 3300, 3700, 3300, 2000, 3700, 3500, 3900, 3500, 2600, 3900, 3700, 4100}, {3199, 580, 2750, 2600, 3000, 3200, 600, 3600, 3400, 3800, 3400, 1820, 3800, 3600, 4000, 3600, 2200, 4000, 3800, 4200, 3800, 3000, 4200, 4000, 4400}, {2443, 300, 3400, 4000, 1500, 4000, 600, 4000, 5000, 2500, 4500, 1200, 6100, 7000, 4100, 5000, 1800, 8000, 9000, 5500, 6000, 2400, 10550, 12000, 7220}};


            if (currentArrayRow >= 0 && currentArrayRow < HousingData.length &&
                    currentColumnRow >= 0 && currentColumnRow < HousingData[currentArrayRow].length) {
                int specificValue = HousingData[currentArrayRow][currentColumnRow];
                currentEmission = currentEmission + specificValue;
                resultTextView.setText("Total Carbon Emission: " + currentEmission + " CO₂");
            } else {
                resultTextView.setText("Invalid row or column indices. Row:" + currentArrayRow + "Column: " + currentColumnRow );

            }



//            try {
//                InputStream fis = new FileInputStream("path/to/your/jsonfile.json");
//                JsonReader reader = Json.createReader(fis);
//                javax.json.JsonObject jsonObject = reader.readObject();
//                reader.close();
//
//                // Access the "Housing formula data" array
//                JsonArray housingDataArray = jsonObject.getJsonArray("Housing formula data");
//
//                // Access the inner array at the given index
//                int arrayIndex = 0; // Replace with your index
//                JsonArray innerArray = housingDataArray.getJsonArray(arrayIndex);
//
//                // Search for the target integer
//                int targetInteger = 400;
//                boolean found = innerArray.stream()
//                        .mapToInt(jsonValue -> jsonValue.asJsonNumber().intValue())
//                        .anyMatch(value -> value == targetInteger);
//
//                if (found) {
//                    System.out.println("Found the integer " + targetInteger + " in the array at index " + arrayIndex);
//                } else {
//                    System.out.println("Integer " + targetInteger + " not found in the array at index " + arrayIndex);
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }



//            try {
//
//                // Use getFilesDir() or Assets Folder
//                String filePath = "app/src/main/java/com/example/plantze_application/ui/annual_footprint/HousingData.json"; // Replace with assets logic if needed
////                File jsonFile = new File(filePath);
//
//
//                resultTextView.setText("Current working directory: " + System.getProperty("user.dir"));
//
//                @SuppressLint({"NewApi", "LocalSuppress"}) String jsonContent = new String(Files.readAllBytes(Paths.get("./app/java/com.example.plantze_application/ui/annual_footprint/HousingData.json")));
//
//                JSONArray jsonArray = new JSONArray(jsonContent);
//
//                if(jsonArray.length() != 0){
//                    resultTextView.setText("all good");
//                }
//                else{
//                    resultTextView.setText("doom");
//                }
//
////                // Check if the file exists
////                if (!jsonFile.exists()) {
////                    resultTextView.setText("JSON file not found at: " + filePath);
////                    return;
////                }
//
//                // Read the file using JsonReader
////                JSONObject jsonObject = JsonReader.readJsonFromFile(filePath);
////                JSONArray housingData = jsonObject.getJSONArray("Housing formula data");
////
////                // Access specific value
////                int specificValue = housingData.getJSONArray(currentArrayRow).getInt(currentColumnRow);
////
////                // Update and display emission value
////                currentEmission += specificValue;
////                resultTextView.setText("Current Carbon Emission: " + currentEmission + " CO₂");
//
//            } catch (Exception e) {
//                e.printStackTrace();
////                resultTextView.setText("Error reading JSON data: " + e.getMessage());
//            }

        });
    }

}
