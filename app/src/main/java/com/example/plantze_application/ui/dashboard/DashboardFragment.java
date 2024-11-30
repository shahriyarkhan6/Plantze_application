package com.example.plantze_application.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.plantze_application.databinding.FragmentDashboardBinding;

import com.example.plantze_application.ui.annual_footprint.AnnualFootprintActivity;
import com.example.plantze_application.ui.ecotracker.CalendarActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        // Set up line chart:
        LineChart chart = binding.chart;
        ArrayList<Entry> chart_entries = new ArrayList<>();
        chart_entries.add(new Entry(5, 7));
        chart_entries.add(new Entry(8, 10));
        chart_entries.add(new Entry(12, 13));
        LineDataSet chart_data = new LineDataSet(chart_entries, "CO2 Emissions over time");
        LineData c_data = new LineData(chart_data);
        chart.setData(c_data);
        chart.invalidate();
        // Set up the Annual Emissions button click listener
        Button annualEmissionsButton = binding.annualEmissionsButton;  // Reference the annual emissions button
        annualEmissionsButton.setOnClickListener(v -> {
            // Start the AnnualFootprintActivity when the button is clicked
            Intent intent = new Intent(getActivity(), AnnualFootprintActivity.class);
            startActivity(intent);
        });

        Button ecotrackerButton = binding.ecotrackerButton;  // Reference the Ecotracker button
        ecotrackerButton.setOnClickListener(v -> {
            // Start the Ecotracker when the button is clicked
            Intent intent = new Intent(getActivity(), CalendarActivity.class);
            startActivity(intent);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
