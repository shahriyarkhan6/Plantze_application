package com.example.plantze_application.ui.home;

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

import com.example.plantze_application.MainActivity;
import com.example.plantze_application.databinding.FragmentHomeBinding;
import com.example.plantze_application.ui.annual_footprint.AnnualFootprintActivity;
import com.example.plantze_application.ui.ecotracker.CalendarActivity;
import com.example.plantze_application.ui.habit_tracking.HabitTrackingActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button getWeekButton = binding.ecoTrackerCalendarButton;
        getWeekButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CalendarActivity.class);
            startActivity(intent);
        });
        Button getMonthButton = binding.habitTrackingButton;
        getMonthButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HabitTrackingActivity.class);
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