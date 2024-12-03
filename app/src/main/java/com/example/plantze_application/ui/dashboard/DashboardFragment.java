package com.example.plantze_application.ui.dashboard;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.plantze_application.databinding.FragmentDashboardBinding;

import com.example.plantze_application.ui.annual_footprint.AnnualFootprintActivity;
import com.example.plantze_application.ui.ecotracker.CalendarActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView textView = binding.textDashboard;
        TextView emissionText = binding.textEmissions;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        // Set up line chart:
        LineChart linechart = binding.chart;
        // Pull Data from Firebase:
        DashboardChart newChart = new DashboardChart(linechart);
        // create chart:
        newChart.collectWeek(emissionText);

        // Set up the Annual Emissions button click listener
        Button getWeekButton = binding.weekButton;  // Reference the annual emissions button
        getWeekButton.setOnClickListener(v -> {
            // Start the AnnualFootprintActivity when the button is clicked
            newChart.collectWeek(emissionText);
        });
        Button getMonthButton = binding.monthButton;  // Reference the Ecotracker button
        getMonthButton.setOnClickListener(v -> {
            // Start the Ecotracker when the button is clicked
            newChart.collectMonth(emissionText);
        });
        Button getYearButton = binding.yearButton;  // Reference the Ecotracker button
        getYearButton.setOnClickListener(v -> {
            newChart.collectYear(emissionText);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
