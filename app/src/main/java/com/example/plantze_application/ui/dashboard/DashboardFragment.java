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
import com.example.plantze_application.ui.annual_footprint.EcoTrackerActivity;
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
