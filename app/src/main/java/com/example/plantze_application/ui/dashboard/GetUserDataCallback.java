package com.example.plantze_application.ui.dashboard;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;



public interface GetUserDataCallback {
    public void onCallback(ArrayList<Entry> i, ArrayList<PieEntry> p);
}
