package com.example.plantze_application.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.plantze_application.databinding.FragmentNotificationsBinding;
import com.example.plantze_application.ui.annual_footprint.LocationActivity;
import com.example.plantze_application.ui.annual_footprint.TransportationActivity;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button getWeekButton = binding.calculatorButton;
        getWeekButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TransportationActivity.class);
            startActivity(intent);
        });
        Button getMonthButton = binding.locationButton;
        getMonthButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LocationActivity.class);
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