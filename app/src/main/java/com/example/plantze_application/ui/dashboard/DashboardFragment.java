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
import com.example.plantze_application.ui.ecotracker.EcoTrackerActivity;
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
        // Set up the Eco Tracker button click listener
        Button ecoTrackerButton = binding.ecoTrackerButton;  // Reference the Eco Tracker button
        ecoTrackerButton.setOnClickListener(v -> {
            // Start the EcoTrackerActivity when the button is clicked
            Intent intent = new Intent(getActivity(), EcoTrackerActivity.class);
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
