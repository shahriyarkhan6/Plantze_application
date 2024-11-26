package com.example.plantze_application.ui.annual_footprint;

import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plantze_application.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LocationActivity extends AppCompatActivity {

    private Spinner locationSpinner;
    private Button submitLocationButton;

    private LinkedHashMap<String, Double> countryEmissionsMap;
    private String selectedCountry = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // Initialize UI elements
        locationSpinner = findViewById(R.id.locationSpinner);
        submitLocationButton = findViewById(R.id.submitLocationButton);

        // Initialize the country-emissions map
        initializeCountryEmissionsMap();

        // Populate the spinner with country names
        List<String> countries = new ArrayList<>(countryEmissionsMap.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);

        // Handle spinner selection
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                selectedCountry = countries.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCountry = null;
            }
        });

        // Handle submit button click
        submitLocationButton.setOnClickListener(v -> {
            if (selectedCountry == null) {
                Toast.makeText(LocationActivity.this, "Please select a location", Toast.LENGTH_SHORT).show();
                return;
            }

            Double emissions = countryEmissionsMap.get(selectedCountry);
            if (emissions != null) {
                saveLocationToFirebase(selectedCountry, emissions);
            } else {
                Toast.makeText(LocationActivity.this, "Failed to retrieve emissions data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveLocationToFirebase(String country, double emission) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = getSharedPreferences("MyAppPrefs", MODE_PRIVATE).getString("USER_ID", null);

        if (userId == null) {
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("Location", country);
        data.put("Location Emissions", emission);

        db.collection("users").document(userId).update(data)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(LocationActivity.this, "Location saved successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error saving location", e);
                    Toast.makeText(LocationActivity.this, "Failed to save location. Try again.", Toast.LENGTH_SHORT).show();
                });
    }

    private void initializeCountryEmissionsMap() {
        countryEmissionsMap = new LinkedHashMap<>();

        countryEmissionsMap.put("Afghanistan", 0.29536375);
        countryEmissionsMap.put("Africa", 0.99422127);
        countryEmissionsMap.put("Albania", 1.7432004);
        countryEmissionsMap.put("Algeria", 3.9272263);
        countryEmissionsMap.put("Andorra", 4.6171236);
        countryEmissionsMap.put("Angola", 0.45155162);
        countryEmissionsMap.put("Anguilla", 8.752724);
        countryEmissionsMap.put("Antigua and Barbuda", 6.4218745);
        countryEmissionsMap.put("Argentina", 4.2378173);
        countryEmissionsMap.put("Armenia", 2.3045583);
        countryEmissionsMap.put("Aruba", 8.133404);
        countryEmissionsMap.put("Asia", 4.611434);
        countryEmissionsMap.put("Asia (excl. China and India)", 4.017375);
        countryEmissionsMap.put("Australia", 14.985412);
        countryEmissionsMap.put("Austria", 6.8781943);
        countryEmissionsMap.put("Azerbaijan", 3.6746833);
        countryEmissionsMap.put("Bahamas", 5.1708703);
        countryEmissionsMap.put("Bahrain", 25.672274);
        countryEmissionsMap.put("Bangladesh", 0.5964455);
        countryEmissionsMap.put("Barbados", 4.3772573);
        countryEmissionsMap.put("Belarus", 6.1669006);
        countryEmissionsMap.put("Belgium", 7.6875386);
        countryEmissionsMap.put("Belize", 1.7894346);
        countryEmissionsMap.put("Benin", 0.631487);
        countryEmissionsMap.put("Bermuda", 6.9370627);
        countryEmissionsMap.put("Bhutan", 1.3489918);
        countryEmissionsMap.put("Bolivia", 1.7583066);
        countryEmissionsMap.put("Bonaire Sint Eustatius and Saba", 4.083284);
        countryEmissionsMap.put("Bosnia and Herzegovina", 6.1034565);
        countryEmissionsMap.put("Botswana", 2.838951);
        countryEmissionsMap.put("Brazil", 2.2454574);
        countryEmissionsMap.put("British Virgin Islands", 5.0039577);
        countryEmissionsMap.put("Brunei", 23.950201);
        countryEmissionsMap.put("Bulgaria", 6.8044534);
        countryEmissionsMap.put("Burkina Faso", 0.26295447);
        countryEmissionsMap.put("Burundi", 0.06194545);
        countryEmissionsMap.put("Cambodia", 1.1900775);
        countryEmissionsMap.put("Cameroon", 0.34292704);
        countryEmissionsMap.put("Canada", 14.249212);
        countryEmissionsMap.put("Cape Verde", 0.9588915);
        countryEmissionsMap.put("Central African Republic", 0.040548485);
        countryEmissionsMap.put("Chad", 0.13367727);
        countryEmissionsMap.put("Chile", 4.3041654);
        countryEmissionsMap.put("China", 7.992761);
        countryEmissionsMap.put("Colombia", 1.9223082);
        countryEmissionsMap.put("Comoros", 0.49327007);
        countryEmissionsMap.put("Congo", 1.2447897);
        countryEmissionsMap.put("Cook Islands", 3.9950094);
        countryEmissionsMap.put("Costa Rica", 1.5226681);
        countryEmissionsMap.put("Cote d'Ivoire", 0.41668788);
        countryEmissionsMap.put("Croatia", 4.348515);
        countryEmissionsMap.put("Cuba", 1.8659163);
        countryEmissionsMap.put("Curacao", 9.189007);
        countryEmissionsMap.put("Cyprus", 5.616782);
        countryEmissionsMap.put("Czechia", 9.3357525);
        countryEmissionsMap.put("Democratic Republic of Congo", 0.036375992);
        countryEmissionsMap.put("Denmark", 4.940161);
        countryEmissionsMap.put("Djibouti", 0.40418932);
        countryEmissionsMap.put("Dominica", 2.1058853);
        countryEmissionsMap.put("Dominican Republic", 2.1051137);
        countryEmissionsMap.put("East Timor", 0.49869007);
        countryEmissionsMap.put("Ecuador", 2.3117273);
        countryEmissionsMap.put("Egypt", 2.333106);
        countryEmissionsMap.put("El Salvador", 1.2174718);
        countryEmissionsMap.put("Equatorial Guinea", 3.0307202);
        countryEmissionsMap.put("Eritrea", 0.18914719);
        countryEmissionsMap.put("Estonia", 7.77628);
        countryEmissionsMap.put("Eswatini", 1.0527312);
        countryEmissionsMap.put("Ethiopia", 0.15458965);
        countryEmissionsMap.put("Europe", 6.8578663);
        countryEmissionsMap.put("Europe (excl. EU-27)", 7.886797);
        countryEmissionsMap.put("Europe (excl. EU-28)", 8.817789);
        countryEmissionsMap.put("European Union (27)", 6.1743994);
        countryEmissionsMap.put("European Union (28)", 5.983708);
        countryEmissionsMap.put("Faroe Islands", 14.084624);
        countryEmissionsMap.put("Fiji", 1.1550449);
        countryEmissionsMap.put("Finland", 6.5267396);
        countryEmissionsMap.put("France", 4.603891);
        countryEmissionsMap.put("French Polynesia", 2.8509297);
        countryEmissionsMap.put("Gabon", 2.3882635);
        countryEmissionsMap.put("Gambia", 0.2847278);
        countryEmissionsMap.put("Georgia", 2.962545);
        countryEmissionsMap.put("Germany", 7.9837584);
        countryEmissionsMap.put("Ghana", 0.6215505);
        countryEmissionsMap.put("Greece", 5.7451057);
        countryEmissionsMap.put("Greenland", 10.473997);
        countryEmissionsMap.put("Grenada", 2.7133646);
        countryEmissionsMap.put("Guatemala", 1.0756185);
        countryEmissionsMap.put("Guinea", 0.35742033);
        countryEmissionsMap.put("Guinea-Bissau", 0.15518051);
        countryEmissionsMap.put("Guyana", 4.3736935);
        countryEmissionsMap.put("Haiti", 0.21119381);
        countryEmissionsMap.put("High-income countries", 10.132565);
        countryEmissionsMap.put("Honduras", 1.0696708);
        countryEmissionsMap.put("Hong Kong", 4.081913);
        countryEmissionsMap.put("Hungary", 4.449911);
        countryEmissionsMap.put("Iceland", 9.499798);
        countryEmissionsMap.put("India", 1.9966822);
        countryEmissionsMap.put("Indonesia", 2.6456614);
        countryEmissionsMap.put("Iran", 7.7993317);
        countryEmissionsMap.put("Iraq", 4.024638);
        countryEmissionsMap.put("Ireland", 7.7211185);
        countryEmissionsMap.put("Israel", 6.208912);
        countryEmissionsMap.put("Italy", 5.726825);
        countryEmissionsMap.put("Jamaica", 2.2945588);
        countryEmissionsMap.put("Japan", 8.501681);
        countryEmissionsMap.put("Jordan", 2.0301995);
        countryEmissionsMap.put("Kazakhstan", 13.979704);
        countryEmissionsMap.put("Kenya", 0.45998666);
        countryEmissionsMap.put("Kiribati", 0.5184742);
        countryEmissionsMap.put("Kosovo", 4.830646);
        countryEmissionsMap.put("Kuwait", 25.578102);
        countryEmissionsMap.put("Kyrgyzstan", 1.4251612);
        countryEmissionsMap.put("Laos", 3.0803475);
        countryEmissionsMap.put("Latvia", 3.561689);
        countryEmissionsMap.put("Lebanon", 4.3543963);
        countryEmissionsMap.put("Lesotho", 1.3594668);
        countryEmissionsMap.put("Liberia", 0.1653753);
        countryEmissionsMap.put("Libya", 9.242238);
        countryEmissionsMap.put("Liechtenstein", 3.8097827);
        countryEmissionsMap.put("Lithuania", 4.606163);
        countryEmissionsMap.put("Luxembourg", 11.618432);
        countryEmissionsMap.put("Macao", 1.5127679);
        countryEmissionsMap.put("Madagascar", 0.14871116);
        countryEmissionsMap.put("Malawi", 0.10262384);
        countryEmissionsMap.put("Malaysia", 8.576508);
        countryEmissionsMap.put("Maldives", 3.2475724);
        countryEmissionsMap.put("Mali", 0.31153768);
        countryEmissionsMap.put("Malta", 3.1035979);
        countryEmissionsMap.put("Marshall Islands", 3.6353714);
        countryEmissionsMap.put("Mauritania", 0.957337);
        countryEmissionsMap.put("Mauritius", 3.2697906);
        countryEmissionsMap.put("Mexico", 4.0153365);
        countryEmissionsMap.put("Micronesia (country)", 1.3243006);
        countryEmissionsMap.put("Moldova", 1.6565942);
        countryEmissionsMap.put("Mongolia", 11.150772);
        countryEmissionsMap.put("Montenegro", 3.6558185);
        countryEmissionsMap.put("Montserrat", 4.8447766);
        countryEmissionsMap.put("Morocco", 1.8263615);
        countryEmissionsMap.put("Mozambique", 0.24274588);
        countryEmissionsMap.put("Myanmar", 0.6445672);
        countryEmissionsMap.put("Namibia", 1.5399038);
        countryEmissionsMap.put("Nauru", 4.1700416);
        countryEmissionsMap.put("Nepal", 0.5074035);
        countryEmissionsMap.put("Netherlands", 7.1372175);
        countryEmissionsMap.put("New Caledonia", 17.641167);
        countryEmissionsMap.put("New Zealand", 6.212154);
        countryEmissionsMap.put("Nicaragua", 0.79879653);
        countryEmissionsMap.put("Niger", 0.116688);
        countryEmissionsMap.put("Nigeria", 0.5891771);
        countryEmissionsMap.put("Niue", 3.8729508);
        countryEmissionsMap.put("North America", 10.5346775);
        countryEmissionsMap.put("North Korea", 1.9513915);
        countryEmissionsMap.put("Norway", 7.5093055);
        countryEmissionsMap.put("Pakistan", 0.84893465);
        countryEmissionsMap.put("Palau", 12.123921);
        countryEmissionsMap.put("Palestine", 0.6660658);
        countryEmissionsMap.put("Panama", 2.699258);
        countryEmissionsMap.put("Papua New Guinea", 0.77131313);
        countryEmissionsMap.put("Paraguay", 1.3299496);
        countryEmissionsMap.put("Peru", 1.7891879);
        countryEmissionsMap.put("Philippines", 1.3014648);
        countryEmissionsMap.put("Poland", 8.106886);
        countryEmissionsMap.put("Portugal", 4.050785);
        countryEmissionsMap.put("Qatar", 37.601273);
        countryEmissionsMap.put("Romania", 3.739777);
        countryEmissionsMap.put("Russia", 11.416899);
        countryEmissionsMap.put("Rwanda", 0.112346195);
        countryEmissionsMap.put("Saudi Arabia", 18.197495);
        countryEmissionsMap.put("Senegal", 0.6738352);
        countryEmissionsMap.put("Serbia", 6.024712);
        countryEmissionsMap.put("Singapore", 8.911513);
        countryEmissionsMap.put("Slovakia", 6.051555);
        countryEmissionsMap.put("South Africa", 6.7461643);
        countryEmissionsMap.put("South Korea", 11.598764);
        countryEmissionsMap.put("Spain", 5.1644425);
        countryEmissionsMap.put("Sri Lanka", 0.7936504);
        countryEmissionsMap.put("Sudan", 0.4696261);
        countryEmissionsMap.put("Suriname", 5.8029985);
        countryEmissionsMap.put("Sweden", 3.6069093);
        countryEmissionsMap.put("Switzerland", 4.0478554);
        countryEmissionsMap.put("Taiwan", 11.630868);
        countryEmissionsMap.put("Thailand", 3.7762568);
        countryEmissionsMap.put("Tunisia", 2.879285);
        countryEmissionsMap.put("Turkey", 5.1052055);
        countryEmissionsMap.put("United Arab Emirates", 25.833244);
        countryEmissionsMap.put("United Kingdom", 4.7201805);
        countryEmissionsMap.put("United States", 14.949616);
        countryEmissionsMap.put("Venezuela", 2.7168686);
        countryEmissionsMap.put("Vietnam", 3.4995174);
        countryEmissionsMap.put("World", 4.658219);
        countryEmissionsMap.put("Yemen", 0.33701748);
        countryEmissionsMap.put("Zambia", 0.44570068);
        countryEmissionsMap.put("Zimbabwe", 0.542628);
    }
}