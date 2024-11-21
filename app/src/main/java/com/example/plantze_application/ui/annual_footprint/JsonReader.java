package com.example.plantze_application.ui.annual_footprint;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JsonReader {
    // Method to read JSON from file
    public static JSONObject readJsonFromFile(String filePath) throws IOException, JSONException {
        // Use BufferedReader to read the file
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }
        // Parse and return as a JSONObject
        return new JSONObject(content.toString());
    }
}